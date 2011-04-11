/** \file roxml-parse-engine.c
 *  \brief source for libroxml.so
 *         
 * This is the source file for lib libroxml.so internal functions
 * \author blunderer <blunderer@blunderer.org>
 * \date 20 Fev 2010
 *
 * Copyright (C) 2010 blunderer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

#include "roxml-internal.h"

//#define DEBUG_PARSING

roxml_parser_item_t *roxml_append_parser_item(roxml_parser_item_t *head, char * key, roxml_parse_func func)
{
	roxml_parser_item_t *item = head;

	if(head == NULL) {
		item = (roxml_parser_item_t*)calloc(1, sizeof(roxml_parser_item_t));
		head = item;
	} else {
		item = head;
		while(item->next) item = item->next;
		item->next = (roxml_parser_item_t*)calloc(1, sizeof(roxml_parser_item_t));
		item = item->next;
	}
	item->chunk = key?key[0]:0;
	item->func = func;

	return head;
}

void roxml_parser_free(roxml_parser_item_t *head)
{
	free(head);
}

void roxml_parser_clear(roxml_parser_item_t *head)
{
	roxml_parser_item_t *item = head;

	while(item) {
		roxml_parser_item_t * to_delete = item;
		item = item->next;
		free(to_delete);
	}

	return;
}

roxml_parser_item_t * roxml_parser_prepare(roxml_parser_item_t *head)
{
	roxml_parser_item_t *item = head;
	roxml_parser_item_t *table = NULL;
	int count = 0;

	head->count = 0;
	head->def_count = 0;
	while(item) {
		if(item->chunk != 0) { head->count++; }
		head->def_count++;
		item = item->next;
	}

	table = (roxml_parser_item_t*)malloc(sizeof(roxml_parser_item_t)*(head->def_count));

	item = head;

	while(item) {
		memcpy(&table[count], item, sizeof(roxml_parser_item_t));
		item = item->next;
		count++;
	}
	roxml_parser_clear(head);

	return table;
}

int roxml_parse_line(roxml_parser_item_t * head, char *line, int len, void * ctx)
{
	int count = head->count;
	int def_count = head->def_count;
	char * line_end = line;
	char * chunk = line;

	if(len > 0) {
		line_end = line + len;
	} else {
		line_end = line + strlen(line);
	}

	while(chunk < line_end) {
		int i = 0;
		for(; i < count; i++) {
			if(chunk[0] == head[i].chunk) { 
				int ret = head[i].func(chunk, ctx);
				if(ret > 0) { chunk += ret; break; }
				else if(ret < 0) { return -1; }
			}
		}
		for(; i >= count && i < def_count; i++) {
			int ret = head[i].func(chunk, ctx);
			if(ret > 0) { chunk += ret; break; } 
			else if(ret < 0) { return -1; }
		}
	}
	return 0;
}

int _func_xpath_ignore(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	return 1;
}

int _func_xpath_new_node(char * chunk, void * data)
{
	int cur = 0;
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	if(!ctx->quoted && !ctx->dquoted && !ctx->parenthesys && !ctx->bracket)     {
		int offset = 0;
		xpath_node_t * tmp_node = (xpath_node_t*)calloc(1, sizeof(xpath_node_t));
		if((chunk[cur] == '/')&&(ctx->is_first_node)) { 
			free(tmp_node);
			ctx->new_node = ctx->first_node;
			ctx->first_node->abs = 1;
		} else if((chunk[cur] == '/')&&(ctx->wait_first_node)) { 
			free(tmp_node);
			ctx->first_node->abs = 1;
		} else if((ctx->is_first_node)||(ctx->wait_first_node)) {
			free(tmp_node);
		} else {
			if(ctx->new_node)    { ctx->new_node->next = tmp_node; }
			ctx->new_node = tmp_node;
		}
		ctx->is_first_node = 0;
		ctx->wait_first_node = 0;
		ctx->new_node = roxml_set_axes(ctx->new_node, chunk+cur, &offset);
		cur = offset + 1;
	}
	ctx->shorten_cond = 0;
	return cur;
}

int _func_xpath_quote(char * chunk, void * data)
{
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	if(!ctx->dquoted) {
		if(ctx->quoted && ctx->content_quoted == MODE_COMMENT_QUOTE) {
			ctx->content_quoted = MODE_COMMENT_NONE;
			chunk[0] = '\0';
		}
		ctx->quoted = (ctx->quoted+1)%2;
	}
	ctx->shorten_cond = 0;
	return 1;
}

int _func_xpath_dquote(char * chunk, void * data)
{
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	if(!ctx->quoted) {
		if(ctx->dquoted && ctx->content_quoted == MODE_COMMENT_DQUOTE) {
			ctx->content_quoted = MODE_COMMENT_NONE;
			chunk[0] = '\0';
		}
		ctx->dquoted = (ctx->dquoted+1)%2;
	}
	ctx->shorten_cond = 0;
	return 1;
}

int _func_xpath_open_parenthesys(char * chunk, void * data)
{
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	if(!ctx->quoted && !ctx->dquoted) {
		ctx->parenthesys = (ctx->parenthesys+1)%2;
	}
	ctx->shorten_cond = 0;
	return 1;
}

int _func_xpath_close_parenthesys(char * chunk, void * data)
{
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	if(!ctx->quoted && !ctx->dquoted) {
		ctx->parenthesys = (ctx->parenthesys+1)%2;
	}
	ctx->shorten_cond = 0;
	return 1;
}

int _func_xpath_open_brackets(char * chunk, void * data)
{
	xpath_cond_t * tmp_cond;
	int cur = 0;
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	if(!ctx->quoted && !ctx->dquoted) {
		ctx->bracket = (ctx->bracket+1)%2;
		chunk[0] = '\0';

		ctx->shorten_cond = 1;
		tmp_cond = (xpath_cond_t*)calloc(1, sizeof(xpath_cond_t));
		ctx->new_node->cond = tmp_cond;
		ctx->new_cond = tmp_cond;
		ctx->new_cond->arg1 = chunk+cur+1;
	} else {
		ctx->shorten_cond = 0;
	}
	cur++;
	return 1;
}

int _func_xpath_close_brackets(char * chunk, void * data)
{
	int cur = 0;
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	if(!ctx->quoted && !ctx->dquoted) {
		ctx->bracket = (ctx->bracket+1)%2;
		chunk[0] = '\0';

		if(ctx->new_cond) {
			if(ctx->new_cond->func == ROXML_FUNC_XPATH) {
				xpath_node_t *xp;
				ctx->new_cond->func2 = roxml_parse_xpath(ctx->new_cond->arg1, &xp, 1);
				ctx->new_cond->xp = xp;
			}
		} else {
			return -1;
		}
	}
	cur++;
	ctx->shorten_cond = 0;
	return 1;
}

int _func_xpath_condition_or(char * chunk, void * data)
{
	xpath_node_t * tmp_node;
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	int cur = 0;
	int len = 0;
	xpath_cond_t * tmp_cond;
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */

	len = strlen(ROXML_COND_OR);

	if(strncmp(chunk, ROXML_COND_OR, len) == 0) {
		if(roxml_is_separator(*(chunk-1)) && roxml_is_separator(*(chunk+len))) {
			if(!ctx->bracket && !ctx->quoted && !ctx->dquoted) {
				if(ctx->context != 1) { return 0; }
				chunk[-1] = '\0';
				cur += strlen(ROXML_COND_OR);
				tmp_node = (xpath_node_t*)calloc(ctx->nbpath+1, sizeof(xpath_node_t));
				memcpy(tmp_node, ctx->first_node, ctx->nbpath*sizeof(xpath_node_t));
				free(ctx->first_node);
				ctx->first_node = tmp_node;
				ctx->wait_first_node = 1;
				ctx->new_node = tmp_node+ctx->nbpath;
				ctx->new_node->rel = ROXML_OPERATOR_OR;
				ctx->nbpath++;
			} else if(ctx->bracket && !ctx->quoted && !ctx->dquoted) {
				if(ctx->new_cond->func != ROXML_FUNC_XPATH) {
					chunk[-1] = '\0';
					cur += strlen(ROXML_COND_OR);
					tmp_cond = (xpath_cond_t*)calloc(1, sizeof(xpath_cond_t));
					if(ctx->new_cond) { ctx->new_cond->next = tmp_cond; }
					ctx->new_cond = tmp_cond;
					ctx->new_cond->rel = ROXML_OPERATOR_OR;
					ctx->new_cond->arg1 = chunk+cur+1;
				}
			}
	}
	}
	ctx->shorten_cond = 0;
	return cur;
}

int _func_xpath_condition_and(char * chunk, void * data)
{
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	int cur = 0;
	int len = 0;
	xpath_node_t * tmp_node;
	xpath_cond_t * tmp_cond;
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */

	len = strlen(ROXML_COND_AND);

	if(strncmp(chunk, ROXML_COND_AND, len) == 0) {
		if(roxml_is_separator(*(chunk-1)) && roxml_is_separator(*(chunk+len))) {
			if(!ctx->bracket && !ctx->quoted && !ctx->dquoted) {
				if(ctx->context != 1) { return 0; }
				chunk[-1] = '\0';
				cur += strlen(ROXML_COND_AND);
				tmp_node = (xpath_node_t*)calloc(ctx->nbpath+1, sizeof(xpath_node_t));
				memcpy(tmp_node, ctx->first_node, ctx->nbpath*sizeof(xpath_node_t));
				free(ctx->first_node);
				ctx->first_node = tmp_node;
				ctx->wait_first_node = 1;
				ctx->new_node = tmp_node+ctx->nbpath;
				ctx->new_node->rel = ROXML_OPERATOR_AND;
				ctx->nbpath++;
			} else if(ctx->bracket && !ctx->quoted && !ctx->dquoted) {
				if(ctx->new_cond->func != ROXML_FUNC_XPATH) {
					chunk[-1] = '\0';
					cur += strlen(ROXML_COND_AND);
					tmp_cond = (xpath_cond_t*)calloc(1, sizeof(xpath_cond_t));
					if(ctx->new_cond) { ctx->new_cond->next = tmp_cond; }
					ctx->new_cond = tmp_cond;
					ctx->new_cond->rel = ROXML_OPERATOR_AND;
					ctx->new_cond->arg1 = chunk+cur+1;
				}
			}
		}
	}
	ctx->shorten_cond = 0;
	return cur;
}

int _func_xpath_path_or(char * chunk, void * data)
{
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	int cur = 0;
	xpath_node_t * tmp_node;
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */

	if(!ctx->bracket && !ctx->quoted && !ctx->dquoted) {
		chunk[-1] = '\0';
		cur += strlen(ROXML_PATH_OR);
		tmp_node = (xpath_node_t*)calloc(ctx->nbpath+1, sizeof(xpath_node_t));
		memcpy(tmp_node, ctx->first_node, ctx->nbpath*sizeof(xpath_node_t));
		free(ctx->first_node);
		ctx->first_node = tmp_node;
		ctx->wait_first_node = 1;
		ctx->new_node = tmp_node+ctx->nbpath;
		ctx->new_node->rel = ROXML_OPERATOR_OR;
		ctx->nbpath++;
	}
	ctx->shorten_cond = 0;
	return cur;
}

int _func_xpath_operator_equal(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	int cur = 0;
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	if(!ctx->bracket && !ctx->quoted && !ctx->dquoted) {
		xpath_node_t *xp_root = ctx->new_node;
		xpath_cond_t * xp_cond = (xpath_cond_t*)calloc(1, sizeof(xpath_cond_t));
		xp_root->xp_cond = xp_cond;
		xp_cond->op = ROXML_OPERATOR_EQU;
		chunk[cur] = '\0';
		if(ROXML_WHITE(chunk[cur-1])) {
			chunk[cur-1] = '\0';
		}
		if(ROXML_WHITE(chunk[cur+1])) {
			cur++;
			chunk[cur] = '\0';
		}
		xp_cond->arg2 = chunk+cur+1;
		if(xp_cond->arg2[0] == '"') {
			ctx->content_quoted = MODE_COMMENT_DQUOTE;
			xp_cond->arg2++;
		} else if(xp_cond->arg2[0] == '\'') {
			ctx->content_quoted = MODE_COMMENT_QUOTE;
			xp_cond->arg2++;
		}
		if((xp_cond->arg2[0] > '9')||(xp_cond->arg2[0] < '0')) {
			xp_cond->func = ROXML_FUNC_STRCOMP;
		}
		cur++;
	} else if(ctx->bracket && !ctx->quoted && !ctx->dquoted) {
		if(ctx->new_cond->func != ROXML_FUNC_XPATH) {
			ctx->new_cond->op = ROXML_OPERATOR_EQU;
			chunk[cur] = '\0';
			if(ROXML_WHITE(chunk[cur-1])) {
				chunk[cur-1] = '\0';
			}
			if(ROXML_WHITE(chunk[cur+1])) {
				cur++;
				chunk[cur] = '\0';
			}
			ctx->new_cond->arg2 = chunk+cur+1;
			if((ctx->new_cond->arg2[0] > '9')||(ctx->new_cond->arg2[0] < '0')) {
				ctx->new_cond->func = ROXML_FUNC_STRCOMP;
			}
			cur++;
		}
	}
	ctx->shorten_cond = 0;
	return cur;
}

int _func_xpath_operator_sup(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	int cur = 0;
	if(!ctx->bracket && !ctx->quoted && !ctx->dquoted) {
		xpath_node_t *xp_root = ctx->new_node;
		xpath_cond_t * xp_cond = (xpath_cond_t*)calloc(1, sizeof(xpath_cond_t));
		xp_root->xp_cond = xp_cond;
		chunk[cur] = '\0';
		xp_cond->op = ROXML_OPERATOR_SUP;
		if(ROXML_WHITE(chunk[cur-1])) {
			chunk[cur-1] = '\0';
		}
		if(chunk[cur+1] == '=') {
			cur++;
			chunk[cur] = '\0';
			xp_cond->op = ROXML_OPERATOR_ESUP;
		}
		if(ROXML_WHITE(chunk[cur+1])) {
			cur++;
			chunk[cur] = '\0';
		}
		xp_cond->arg2 = chunk+cur+1;
		cur++;
	} else if(ctx->bracket && !ctx->quoted && !ctx->dquoted) {
		if(ctx->new_cond->func != ROXML_FUNC_XPATH) {
			chunk[cur] = '\0';
			ctx->new_cond->op = ROXML_OPERATOR_SUP;
			if(ROXML_WHITE(chunk[cur-1])) {
				chunk[cur-1] = '\0';
			}
			if(chunk[cur+1] == '=') {
				cur++;
				chunk[cur] = '\0';
				ctx->new_cond->op = ROXML_OPERATOR_ESUP;
			}
			if(ROXML_WHITE(chunk[cur+1])) {
				cur++;
				chunk[cur] = '\0';
			}
			ctx->new_cond->arg2 = chunk+cur+1;
			cur++;
		}
	}
	return cur;
	ctx->shorten_cond = 0;
}

int _func_xpath_operator_inf(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	int cur = 0;
	if(!ctx->bracket && !ctx->quoted && !ctx->dquoted) {
		xpath_node_t *xp_root = ctx->new_node;
		xpath_cond_t * xp_cond = (xpath_cond_t*)calloc(1, sizeof(xpath_cond_t));
		xp_root->xp_cond = xp_cond;
		chunk[cur] = '\0';
		xp_cond->op = ROXML_OPERATOR_INF;
		if(ROXML_WHITE(chunk[cur-1])) {
			chunk[cur-1] = '\0';
		}
		if(chunk[cur+1] == '=') {
			cur++;
			chunk[cur] = '\0';
			xp_cond->op = ROXML_OPERATOR_EINF;
		}
		if(ROXML_WHITE(chunk[cur+1])) {
			cur++;
			chunk[cur] = '\0';
		}
		xp_cond->arg2 = chunk+cur+1;
		cur++;
	} else if(ctx->bracket && !ctx->quoted && !ctx->dquoted) {
		if(ctx->new_cond->func != ROXML_FUNC_XPATH) {
			chunk[cur] = '\0';
			ctx->new_cond->op = ROXML_OPERATOR_INF;
			if(ROXML_WHITE(chunk[cur-1])) {
				chunk[cur-1] = '\0';
			}
			if(chunk[cur+1] == '=') {
				cur++;
				chunk[cur] = '\0';
				ctx->new_cond->op = ROXML_OPERATOR_EINF;
			}
			if(ROXML_WHITE(chunk[cur+1])) {
				cur++;
				chunk[cur] = '\0';
			}
			ctx->new_cond->arg2 = chunk+cur+1;
			cur++;
		}
	}
	ctx->shorten_cond = 0;
	return cur;
}

int _func_xpath_operator_diff(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	int cur = 0;
	if(chunk[1] == '=') {
		if(!ctx->bracket && !ctx->quoted && !ctx->dquoted) {
			xpath_node_t *xp_root = ctx->new_node;
			xpath_cond_t * xp_cond = (xpath_cond_t*)calloc(1, sizeof(xpath_cond_t));
			xp_root->xp_cond = xp_cond;
			chunk[cur] = '\0';
			chunk[cur+1] = '\0';
			xp_cond->op = ROXML_OPERATOR_DIFF;
			if(ROXML_WHITE(chunk[cur-1])) {
				chunk[cur-1] = '\0';
			}
			cur++;
			if(ROXML_WHITE(chunk[cur+1])) {
				cur++;
				chunk[cur] = '\0';
			}
			xp_cond->arg2 = chunk+cur+1;
			if((xp_cond->arg2[0] > '9')||(xp_cond->arg2[0] < '0')) {
				xp_cond->func = ROXML_FUNC_STRCOMP;
			}
			cur++;
		} else if(ctx->bracket && !ctx->quoted && !ctx->dquoted) {
			if(ctx->new_cond->func != ROXML_FUNC_XPATH) {
				chunk[cur] = '\0';
				chunk[cur+1] = '\0';
				ctx->new_cond->op = ROXML_OPERATOR_DIFF;
				if(ROXML_WHITE(chunk[cur-1])) {
					chunk[cur-1] = '\0';
				}
				cur++;
				if(ROXML_WHITE(chunk[cur+1])) {
					cur++;
					chunk[cur] = '\0';
				}
				ctx->new_cond->arg2 = chunk+cur+1;
				if((ctx->new_cond->arg2[0] > '9')||(ctx->new_cond->arg2[0] < '0')) {
					ctx->new_cond->func = ROXML_FUNC_STRCOMP;
				}
				cur++;
			}
		}
	}
	ctx->shorten_cond = 0;
	return cur;
}

int _func_xpath_number(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	int cur = 0;
	if(ctx->bracket && !ctx->quoted && !ctx->dquoted) {
		if((ctx->new_cond->func != ROXML_FUNC_XPATH) && (ctx->shorten_cond)){
			cur = 1;
			ctx->new_cond->func = ROXML_FUNC_POS;
			ctx->new_cond->op = ROXML_OPERATOR_EQU;
			ctx->new_cond->arg2 = chunk;
			while((chunk[cur+1] >= '0') && (chunk[cur+1] <= '9')) { cur++; }
		}
	}
	ctx->shorten_cond = 0;
	return cur;
}

int _func_xpath_position(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	int cur = 0;

	if(strncmp(chunk, ROXML_FUNC_POS_STR, strlen(ROXML_FUNC_POS_STR)) == 0) {
		if(ctx->new_cond->func != ROXML_FUNC_XPATH) {
			int func = ROXML_FUNC_POS;
			cur += strlen(ROXML_FUNC_POS_STR)-1;
			ctx->new_cond->func = func;
		}
	}
	ctx->shorten_cond = 0;
	return cur;
}

int _func_xpath_first(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	int cur = 0;
	if(strncmp(chunk, ROXML_FUNC_FIRST_STR, strlen(ROXML_FUNC_FIRST_STR)) == 0) {
		if(ctx->new_cond->func != ROXML_FUNC_XPATH) {
			int func = ROXML_FUNC_FIRST;
			cur += strlen(ROXML_FUNC_FIRST_STR)-1;
			if(ctx->new_cond->op > 0) { ctx->new_cond->func2 = func; }
			else { ctx->new_cond->func = func; }
		}
	}
	ctx->shorten_cond = 0;
	return cur;
}

int _func_xpath_last(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	int cur = 0;
	if(strncmp(chunk, ROXML_FUNC_LAST_STR, strlen(ROXML_FUNC_LAST_STR)) == 0) {
		if(ctx->new_cond->func != ROXML_FUNC_XPATH) {
			int func = ROXML_FUNC_LAST;
			cur += strlen(ROXML_FUNC_LAST_STR)-1;
			if(ctx->new_cond->op > 0) { ctx->new_cond->func2 = func; }
			else { ctx->new_cond->func = func; }
		}
	}
	ctx->shorten_cond = 0;
	return cur;
}

int _func_xpath_operator_add(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	int cur = 0;
	if(ctx->bracket && !ctx->quoted && !ctx->dquoted) {
		if(ctx->new_cond->func != ROXML_FUNC_XPATH) {
			if((ctx->new_cond->func == ROXML_FUNC_LAST)||(ctx->new_cond->func == ROXML_FUNC_FIRST)) {
				ctx->new_cond->op = ROXML_OPERATOR_ADD;
			}
			chunk[cur] = '\0';
			if(ROXML_WHITE(chunk[cur+1])) {
				cur++;
				chunk[cur] = '\0';
			}
			ctx->new_cond->arg2 = chunk+cur+1;
		}
	}
	ctx->shorten_cond = 0;
	return cur;
}

int _func_xpath_operator_subs(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	int cur = 0;
	if(ctx->bracket && !ctx->quoted && !ctx->dquoted) {
		if(ctx->new_cond->func != ROXML_FUNC_XPATH) {
			if((ctx->new_cond->func == ROXML_FUNC_LAST)||(ctx->new_cond->func == ROXML_FUNC_FIRST)) {
				ctx->new_cond->op = ROXML_OPERATOR_SUB;
			}
			chunk[cur] = '\0';
			if(ROXML_WHITE(chunk[cur+1])) {
				cur++;
				chunk[cur] = '\0';
			}
			ctx->new_cond->arg2 = chunk+cur+1;
		}
	}
	ctx->shorten_cond = 0;
	return cur;
}

int _func_xpath_default(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	int cur = 0;
	roxml_xpath_ctx_t *ctx = (roxml_xpath_ctx_t*)data;
	
	if((ctx->is_first_node)||(ctx->wait_first_node)) {
		if(!ctx->quoted && !ctx->dquoted && !ctx->parenthesys && !ctx->bracket)     {
			int offset = 0;
			xpath_node_t * tmp_node = (xpath_node_t*)calloc(1, sizeof(xpath_node_t));
			if((chunk[cur] == '/')&&(ctx->is_first_node)) { 
				free(tmp_node);
				ctx->new_node = ctx->first_node;
				ctx->first_node->abs = 1;
			} else if((chunk[cur] == '/')&&(ctx->wait_first_node)) { 
				free(tmp_node);
				ctx->first_node->abs = 1;
			} else if((ctx->is_first_node)||(ctx->wait_first_node)) {
				free(tmp_node);
			} else {
				if(ctx->new_node)    { ctx->new_node->next = tmp_node; }
				ctx->new_node = tmp_node;
			}
			ctx->is_first_node = 0;
			ctx->wait_first_node = 0;
			ctx->new_node = roxml_set_axes(ctx->new_node, chunk+cur, &offset);
			cur += offset;
		}
	} else if(ctx->bracket && !ctx->quoted && !ctx->dquoted) {
		if(ctx->new_cond->func != ROXML_FUNC_XPATH) {
			if(ctx->shorten_cond) {
				int bracket_lvl = 1;
				ctx->new_cond->func = ROXML_FUNC_XPATH;
				ctx->new_cond->arg1 = chunk+cur;
				while(bracket_lvl > 0) {
					if(chunk[cur] == '[') { bracket_lvl++; }
					else if(chunk[cur] == ']') { bracket_lvl--; }
					cur++;
				}
				cur--;
			}
		}
	}
	ctx->shorten_cond = 0;
	return cur>0?cur:1;
}

int _func_load_quoted(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	roxml_load_ctx_t *context = (roxml_load_ctx_t*)data;

	if(context->mode == MODE_COMMENT_NONE) {
		context->mode = MODE_COMMENT_QUOTE;
	} else if(context->mode == MODE_COMMENT_QUOTE) {
		context->mode = MODE_COMMENT_NONE;
	}

	return 0;
}

int _func_load_dquoted(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	roxml_load_ctx_t *context = (roxml_load_ctx_t*)data;

	if(context->mode == MODE_COMMENT_NONE) {
		context->mode = MODE_COMMENT_DQUOTE;
	} else if(context->mode == MODE_COMMENT_DQUOTE) {
		context->mode = MODE_COMMENT_NONE;
	}

	return 0;
}

int _func_load_open_node(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	int cur = 1;
	roxml_load_ctx_t *context = (roxml_load_ctx_t*)data;

	switch(context->state) {
		case STATE_NODE_CDATA:
		case STATE_NODE_COMMENT:
		break;
		default:
			context->state = STATE_NODE_BEG;
			context->previous_state = STATE_NODE_BEG;
		break;
	}

	context->pos += cur;
	return cur;
}

int _func_load_close_node(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	int cur = 1;
	roxml_load_ctx_t *context = (roxml_load_ctx_t*)data;

	switch(context->state) {
		case STATE_NODE_NAME:
			context->empty_text_node = 1;
			context->current_node = roxml_parent_node(context->current_node, context->candidat_node);
		break;
		case STATE_NODE_ATTR:
			if((context->mode != MODE_COMMENT_DQUOTE)||(context->mode != MODE_COMMENT_QUOTE)) {
				if(context->inside_node_state == STATE_INSIDE_VAL)      {
					node_t * to_be_closed = NULL;
					if(context->content_quoted) {
						context->content_quoted = 0;
						to_be_closed = roxml_create_node(context->pos-1, context->src, ROXML_ATTR_NODE | context->type);
					} else {
						to_be_closed = roxml_create_node(context->pos, context->src, ROXML_ATTR_NODE | context->type);
					}
					roxml_close_node(context->candidat_val, to_be_closed);
				}
				context->current_node = roxml_parent_node(context->current_node, context->candidat_node);
				context->inside_node_state = STATE_INSIDE_ARG_BEG;
			} else {
				context->pos++;
				return 1;
			}
		break;
		case STATE_NODE_SINGLE:
			context->empty_text_node = 1;
			context->current_node = roxml_parent_node(context->current_node, context->candidat_node);
			if(context->current_node->prnt != NULL) { context->current_node = context->current_node->prnt; } 
		break;
		case STATE_NODE_END:
			context->empty_text_node = 1;
			roxml_close_node(context->current_node, context->candidat_node);
			if(context->current_node->prnt != NULL) { context->current_node = context->current_node->prnt; }
		break;
		case STATE_NODE_CDATA:
		case STATE_NODE_CONTENT:
		default:
			context->pos++;
			return 1;
		break;
	}

	context->state = STATE_NODE_CONTENT;
	context->previous_state = STATE_NODE_CONTENT;
	context->candidat_txt = roxml_create_node(context->pos+1, context->src, ROXML_TXT_NODE | context->type);
	context->candidat_txt = roxml_parent_node(context->current_node, context->candidat_txt);
#ifdef IGNORE_EMPTY_TEXT_NODES
	while(chunk[cur] != '\0') { 
		if(chunk[cur] == '<') { break; }
		else if(!ROXML_WHITE(chunk[cur])) { context->empty_text_node = 0; break; }
		cur++; 
	}
#endif /* IGNORE_EMPTY_TEXT_NODES */
	while((chunk[cur] != '<')&&(chunk[cur] != '\0')) { cur++; }

	context->pos += cur;
	return cur;
}

int _func_load_open_spec_node(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	int cur = 1;

	roxml_load_ctx_t *context = (roxml_load_ctx_t*)data;

	if(context->state == STATE_NODE_BEG) {
		if(strncmp(chunk, "!--", 3)==0) {
			cur = 3;
			roxml_process_begin_node(context, context->pos-1);
			roxml_set_type(context->candidat_node, ROXML_CMT_NODE);
			context->state = STATE_NODE_COMMENT;
			while((chunk[cur] != '-')&&(chunk[cur] != '\0')) { cur++; }
		} else if(strncmp(chunk, "![CDATA[", 8)==0) {
#ifdef IGNORE_EMPTY_TEXT_NODES
			context->empty_text_node = 0;
#endif /* IGNORE_EMPTY_TEXT_NODES */
			context->state = STATE_NODE_CDATA;
			while((chunk[cur] != '[')&&(chunk[cur] != '\0')) { cur++; }
		} else {
			roxml_process_begin_node(context, context->pos-1);
			context->state = STATE_NODE_SINGLE;
		}
	}

	context->pos += cur;
	return cur;
}

int _func_load_close_comment(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	int cur = 1;
	roxml_load_ctx_t *context = (roxml_load_ctx_t*)data;

	if(context->state == STATE_NODE_COMMENT)     {
		if(chunk[1] == '-') {
			cur = 2;
			context->state = STATE_NODE_SINGLE;
			context->candidat_node->end = context->pos;
		}
	}

	context->pos += cur;
	return cur;
}

int _func_load_close_cdata(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	int cur = 1;
	roxml_load_ctx_t *context = (roxml_load_ctx_t*)data;

	if(context->state == STATE_NODE_CDATA)     {
		if(chunk[1] == ']') {
			cur = 2;
			context->state = STATE_NODE_CONTENT;
		}
	}

	context->pos += cur;
	return cur;
}

int _func_load_close_pi(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	int cur = 1;
	roxml_load_ctx_t *context = (roxml_load_ctx_t*)data;

	if(context->state == STATE_NODE_BEG)     {
		if(chunk[1] == ' ') {
			cur = 2;
			context->state = STATE_NODE_PI;
			context->previous_state = STATE_NODE_PI;
			roxml_process_begin_node(context, context->pos-1);
			roxml_set_type(context->candidat_node, ROXML_PI_NODE);
			while((chunk[cur] != '?')&&(chunk[cur] != '\0')) { cur++; }
		}
	} else if(context->state == STATE_NODE_PI)     {
		if(context->mode == MODE_COMMENT_NONE) {
			cur = 1;
			context->candidat_node->end = context->pos;
			context->previous_state = STATE_NODE_PI;
			context->state = STATE_NODE_SINGLE;
		}
	}

	context->pos += cur;
	return cur;
}

int _func_load_end_node(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	int cur = 1;
	roxml_load_ctx_t *context = (roxml_load_ctx_t*)data;

	switch(context->state) {
		case STATE_NODE_BEG:
			roxml_process_begin_node(context, context->pos-1);
			context->state = STATE_NODE_END;
		break;
		case STATE_NODE_NAME:
			context->state = STATE_NODE_SINGLE;
		break;
		case STATE_NODE_ATTR:
			if(context->mode != MODE_COMMENT_DQUOTE) { 
				if(context->inside_node_state == STATE_INSIDE_VAL)      {
					node_t * to_be_closed = NULL;
					if(context->content_quoted) {
						context->content_quoted = 0;
						to_be_closed = roxml_create_node(context->pos-1, context->src, ROXML_ATTR_NODE | context->type);
					} else {
						to_be_closed = roxml_create_node(context->pos, context->src, ROXML_ATTR_NODE | context->type);
					}
					roxml_close_node(context->candidat_val, to_be_closed);
				}
				context->inside_node_state = STATE_INSIDE_ARG_BEG;
				context->state = STATE_NODE_SINGLE;
			}
		break;
	}

	context->pos += cur;
	return cur;
}

int _func_load_white(char * chunk, void * data)
{
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */
	int cur = 1;
	roxml_load_ctx_t *context = (roxml_load_ctx_t*)data;

	switch(context->state) {
		case STATE_NODE_NAME:
			context->state = STATE_NODE_ATTR;
			context->inside_node_state = STATE_INSIDE_ARG_BEG;
		break;
		case STATE_NODE_ATTR:
			if(context->mode == MODE_COMMENT_NONE) {
				if(context->inside_node_state == STATE_INSIDE_VAL)   {
					node_t * to_be_closed = NULL;
					if(context->content_quoted) {
						context->content_quoted = 0;
						to_be_closed = roxml_create_node(context->pos-1, context->src, ROXML_ATTR_NODE | context->type);
					} else {
						to_be_closed = roxml_create_node(context->pos, context->src, ROXML_ATTR_NODE | context->type);
					}
					roxml_close_node(context->candidat_val, to_be_closed);
					context->inside_node_state = STATE_INSIDE_ARG_BEG;
				}
			}
		break;
	}
	context->pos += cur;
	return cur;
}

int _func_load_default(char * chunk, void * data)
{
	node_t * to_be_closed;
	int cur = 1;
	roxml_load_ctx_t *context = (roxml_load_ctx_t*)data;
#ifdef DEBUG_PARSING
	fprintf(stderr, "calling func %s chunk %c\n",__func__,chunk[0]);
#endif /* DEBUG_PARSING */

	switch(context->state) {
		case STATE_NODE_BEG:
			roxml_process_begin_node(context, context->pos-1);
			context->state = STATE_NODE_NAME;
			while(!ROXML_WHITE(chunk[cur])&&(chunk[cur] != '>')&&(chunk[cur] != '/')&&(chunk[cur] != '\0')) { cur++; }
		break;
		case STATE_NODE_ATTR:
			if(context->inside_node_state == STATE_INSIDE_ARG_BEG)  {
				context->candidat_arg = roxml_create_node(context->pos-1, context->src, ROXML_ATTR_NODE | context->type);
				context->candidat_arg = roxml_parent_node(context->candidat_node, context->candidat_arg);
				context->inside_node_state = STATE_INSIDE_ARG;
				while((chunk[cur] != '=')&&(chunk[cur] != '>')&&(chunk[cur] != '\0')) { cur++; }
			} else if(context->inside_node_state == STATE_INSIDE_VAL_BEG)  {
				if(context->mode != MODE_COMMENT_NONE)     {
					context->content_quoted = 1;
					context->candidat_val = roxml_create_node(context->pos+1, context->src, ROXML_TXT_NODE | context->type);
				} else {
					context->candidat_val = roxml_create_node(context->pos, context->src, ROXML_TXT_NODE | context->type);
				}
				context->candidat_val = roxml_parent_node(context->candidat_arg, context->candidat_val);
				context->inside_node_state = STATE_INSIDE_VAL;
			} else if((context->inside_node_state == STATE_INSIDE_ARG)&&(chunk[0] == '=')) {
				context->inside_node_state = STATE_INSIDE_VAL_BEG;
				to_be_closed = roxml_create_node(context->pos, context->src, ROXML_ATTR_NODE | context->type);
				roxml_close_node(context->candidat_arg, to_be_closed);
			}
		break;
	}

	context->pos += cur;
	return cur;
}

