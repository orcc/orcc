/** \file roxml-internal.c
 *  \brief source for libroxml.so
 *         
 * This is the source file for lib libroxml.so internal functions
 * \author blunderer <blunderer@blunderer.org>
 * \date 11 Jan 2010
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
#include "roxml-parse-engine.h"

#ifdef __DEBUG
unsigned int _nb_node = 0;
unsigned int _nb_attr = 0;
unsigned int _nb_text = 0;
#endif

/** \brief head of memory manager */
memory_cell_t head_cell = {PTR_NONE, 0, NULL, 0, NULL, NULL};

void * ROXML_INT roxml_malloc(int size, int num, int type)
{
	memory_cell_t *cell = &head_cell;
	while(cell->next != NULL) { cell = cell->next; }
	cell->next = (memory_cell_t*)malloc(sizeof(memory_cell_t));
	if(!cell->next) { return NULL; }
	cell->next->next = NULL;
	cell->next->prev = cell;
	cell = cell->next;
	cell->type = type;
	cell->id = pthread_self();
	cell->occ = size;
	cell->ptr = calloc(num, size);
	head_cell.prev = cell;
	return cell->ptr;
}

int ROXML_INT roxml_read(int pos, int size, char * buffer, node_t * node)
{
	int ret_len = 0;

	if(size > 0 && buffer) {
		if(node->type & ROXML_FILE) {
			fseek(node->src.fil, pos, SEEK_SET);
			ret_len = fread(buffer, 1, size, node->src.fil);
		} else {
			char *r1 = buffer;
			char const *r2 = node->src.buf+pos;

			while (size-- && (*r1++ = *r2++));
			ret_len = r1 - buffer;
		}
	}
	return ret_len;
}

node_t * ROXML_INT roxml_create_node(int pos, void *src, int type)
{
	node_t *n = (node_t*)calloc(1, sizeof(node_t));
	n->type = type;
	n->src.src = src;
	n->pos = pos;
	n->end = pos;

	return n;
}

void ROXML_INT roxml_close_node(node_t *n, node_t *close)
{
	n->end = close->pos;
	free(close);

	if((n->type & ROXML_ELM_NODE) == ROXML_ELM_NODE) {
		//n->next = NULL;
	}

#ifdef __DEBUG
	if(n->type & ROXML_ELM_NODE) _nb_node++;
	if(n->type & ROXML_ATTR_NODE) _nb_attr++;
	if(n->type & ROXML_TXT_NODE) _nb_text++;
#endif
}

void ROXML_INT roxml_free_node(node_t *n)
{
	if(!n) {
		return;
	}

	if(n->type & ROXML_PENDING) {
		if(n->pos == 0) {
			free(n->src.buf);
		}
	}

	if(n->priv) {
		unsigned char id = *(unsigned char*)n->priv;

		if(id == ROXML_REQTABLE_ID) {
			xpath_tok_t * tok = (xpath_tok_t*)n->priv;
			xpath_tok_table_t * table = (xpath_tok_table_t*)n->priv;
			tok = table->next;
			pthread_mutex_destroy(&table->mut);
			free(table);
			while(tok) {
				xpath_tok_t * to_delete = tok;
				tok = tok->next;
				free(to_delete);
			}
		} else if(id == ROXML_NS_ID) {
			roxml_ns_t * ns = (roxml_ns_t*)n->priv;
			free(ns);
		}
	}
	free(n);
}

void ROXML_INT roxml_del_tree(node_t *n)
{
	if(n == NULL)	{ return; }
	roxml_del_tree(n->chld);
	roxml_del_tree(n->sibl);
	roxml_del_tree(n->attr);
	roxml_free_node(n);
}

int roxml_is_number(char *input)
{
	char *end;
	int is_number = 0;
	double r = strtod(input, &end);
	
	if((end == NULL) || 
		(roxml_is_separator(end[0])) || 
		(end[0] == '"') || 
		(end[0] == '\'') || 
		(end[0] == '\0')) 
	{
		is_number = 1;
	}

	return is_number;
}


int ROXML_INT roxml_is_separator(char sep)
{
	int i = 0;
	char separators[32] = "\t\n ";
	while(separators[i] != 0) {
		if(sep == separators[i]) {
			return 1;
		}
		i++;
	}
	return 0;
}

void ROXML_INT roxml_process_unaliased_ns(roxml_load_ctx_t *context)
{
	if(context->nsdef) {
		context->nsdef = 0;
		context->candidat_arg->type |= ROXML_NS_NODE;

		if(context->candidat_val->pos == context->candidat_val->end) {
			context->candidat_node->ns = NULL;
			context->candidat_arg->ns = NULL;
		} else {
			roxml_ns_t * ns = calloc(1, sizeof(roxml_ns_t)+1);
			ns->id = ROXML_NS_ID;
			ns->alias = (char*)ns + sizeof(roxml_ns_t);

			context->candidat_arg->priv = ns;
			context->candidat_arg->ns = context->candidat_arg;
			context->candidat_node->ns = context->candidat_arg;

			context->candidat_arg->next = context->namespaces;
			context->namespaces = context->candidat_arg;
		}
	}
}

void ROXML_INT roxml_process_begin_node(roxml_load_ctx_t *context, int position)
{
	if(context->candidat_txt)	{
#ifdef IGNORE_EMPTY_TEXT_NODES
		if(context->empty_text_node == 0) {
#endif /* IGNORE_EMPTY_TEXT_NODES */
			node_t * to_be_closed = roxml_create_node(position, context->src, ROXML_TXT_NODE | context->type);
			context->candidat_txt = roxml_parent_node(context->current_node, context->candidat_txt, 0);
			roxml_close_node(context->candidat_txt, to_be_closed);
			context->current_node = context->candidat_txt->prnt;
#ifdef IGNORE_EMPTY_TEXT_NODES
		} else {
			roxml_free_node(context->candidat_txt);
		}
#endif /* IGNORE_EMPTY_TEXT_NODES */
		context->candidat_txt = NULL;
	}
	context->candidat_node = roxml_create_node(position, context->src, ROXML_ELM_NODE | context->type);
}

node_t * ROXML_INT roxml_load(node_t *current_node, FILE *file, char *buffer)
{
	int error = 0;
	char int_buffer[ROXML_BULK_READ+1];
	roxml_load_ctx_t context;
	roxml_parser_item_t * parser = NULL;
	xpath_tok_table_t * table = (xpath_tok_table_t*)calloc(1, sizeof(xpath_tok_table_t));

	memset(&context, 0, sizeof(roxml_load_ctx_t));
	context.empty_text_node = 1;
	context.current_node = current_node;

	parser = roxml_append_parser_item(parser, " ", _func_load_white);
	parser = roxml_append_parser_item(parser, "<", _func_load_open_node);
	parser = roxml_append_parser_item(parser, ">", _func_load_close_node);
	parser = roxml_append_parser_item(parser, "/", _func_load_end_node);
	parser = roxml_append_parser_item(parser, "'", _func_load_quoted);
	parser = roxml_append_parser_item(parser, "\"", _func_load_dquoted);
	parser = roxml_append_parser_item(parser, "\t", _func_load_white);
	parser = roxml_append_parser_item(parser, "\n", _func_load_white);
	parser = roxml_append_parser_item(parser, "\r", _func_load_white);
	parser = roxml_append_parser_item(parser, "!", _func_load_open_spec_node);
	parser = roxml_append_parser_item(parser, "]", _func_load_close_cdata);
	parser = roxml_append_parser_item(parser, "-", _func_load_close_comment);
	parser = roxml_append_parser_item(parser, "?", _func_load_close_pi);
	parser = roxml_append_parser_item(parser, ":", _func_load_colon);
	parser = roxml_append_parser_item(parser, NULL, _func_load_default);

	parser = roxml_parser_prepare(parser);

	if(file)	{ 
		int circle = 0;
		int int_len = 0;
		context.type = ROXML_FILE;
		context.src = (void*)file;
		context.pos = 0;
		do {
			int ret = 0;
			int chunk_len = 0;
			int_len = fread(int_buffer+circle, 1, ROXML_BULK_READ-circle, file) + circle;
			int_buffer[int_len] = '\0';

			if(int_len == ROXML_BULK_READ) {
				chunk_len = int_len - ROXML_LONG_LEN;
			} else {
				chunk_len = int_len;
			}

			ret = roxml_parse_line(parser, int_buffer, chunk_len, &context);
			circle = int_len-ret;
			if((ret < 0)||(circle < 0)) {
				error = 1;
				break;
			}
			memmove(int_buffer, int_buffer+ret, circle);
		} while(int_len == ROXML_BULK_READ);
	} else	{
		int ret = 0;
		context.type = ROXML_BUFF;
		context.src = (void*)buffer;
		ret = roxml_parse_line(parser, buffer, 0, &context);
		if(ret < 0) {
			error = 1;
		}
	}

	roxml_parser_free(parser);

#ifdef IGNORE_EMPTY_TEXT_NODES
	if(context.empty_text_node == 1) {
		roxml_free_node(context.candidat_txt);
	}
#endif /* IGNORE_EMPTY_TEXT_NODES */

	if(!error) {
		node_t * virtroot = NULL;
		current_node = roxml_get_root(current_node);
		virtroot = current_node;
		while(virtroot->prnt) {
			virtroot = virtroot->prnt;
		}

		table->id = ROXML_REQTABLE_ID;
		table->ids[ROXML_REQTABLE_ID] = 1;
		pthread_mutex_init(&table->mut, NULL);
		virtroot->priv = (void*)table;
	} else {
		roxml_close(current_node);
		return NULL;
	}

	return current_node;
}

node_t * ROXML_INT roxml_lookup_nsdef(node_t * nsdef, char * ns)
{
	int len = 0;
	char namespace[MAX_NS_LEN];

	for(len = 0; ns[len] != '\0' && ns[len] != ':'; len++) {
		namespace[len] = ns[len];
	}
	namespace[len] = '\0';

	while(nsdef) {
		if(nsdef->priv && strcmp(namespace, ((roxml_ns_t*)nsdef->priv)->alias) == 0) {
			break;
		}
		nsdef = nsdef->next;
	}
	return nsdef;
}

void ROXML_INT roxml_set_type(node_t * n, int type)
{
	n->type &= ~(ROXML_ATTR_NODE|ROXML_ELM_NODE|ROXML_TXT_NODE|ROXML_CMT_NODE|ROXML_PI_NODE);
	n->type |= type;
}

xpath_node_t * ROXML_INT roxml_set_axes(xpath_node_t *node, char *axes, int *offset)
{
	struct _xpath_axes { 
		char id; 
		char * name; 
	};
	
	struct _xpath_axes xpath_axes[14] = {
		{ROXML_ID_PARENT, ROXML_L_PARENT},
		{ROXML_ID_PARENT, ROXML_S_PARENT},
		{ROXML_ID_SELF, ROXML_L_SELF},
		{ROXML_ID_SELF, ROXML_S_SELF},
		{ROXML_ID_ATTR, ROXML_L_ATTR},
		{ROXML_ID_ATTR, ROXML_S_ATTR},
		{ROXML_ID_ANC, ROXML_L_ANC},
		{ROXML_ID_ANC_O_SELF, ROXML_L_ANC_O_SELF},
		{ROXML_ID_NEXT_SIBL, ROXML_L_NEXT_SIBL},
		{ROXML_ID_PREV_SIBL, ROXML_L_PREV_SIBL},
		{ROXML_ID_NEXT, ROXML_L_NEXT},
		{ROXML_ID_PREV, ROXML_L_PREV},
		{ROXML_ID_NS, ROXML_L_NS},
		{ROXML_ID_CHILD, ROXML_L_CHILD},
	};

	xpath_node_t *tmp_node;
	if(axes[0] == '/') { 
		axes[0] = '\0'; 
		*offset += 1;
		axes++;
	}
	if(axes[0] == '/') {
		// ROXML_S_DESC_O_SELF
		node->axes = ROXML_ID_DESC_O_SELF;
		node->name = axes+1;
		tmp_node = (xpath_node_t*)calloc(1, sizeof(xpath_node_t));
		tmp_node->axes = ROXML_ID_CHILD;
		node->next = tmp_node;
		if(strlen(node->name) > 0) {
			tmp_node = (xpath_node_t*)calloc(1, sizeof(xpath_node_t));
			node->next->next = tmp_node;
			node = roxml_set_axes(tmp_node, axes+1, offset);
		}
	} else if(strncmp(ROXML_L_DESC_O_SELF, axes, strlen(ROXML_L_DESC_O_SELF))==0) {
		// ROXML_L_DESC_O_SELF
		node->axes = ROXML_ID_DESC_O_SELF;
		node->name = axes+strlen(ROXML_L_DESC_O_SELF);
		*offset += strlen(ROXML_L_DESC_O_SELF);
		tmp_node = (xpath_node_t*)calloc(1, sizeof(xpath_node_t));
		tmp_node->axes = ROXML_ID_CHILD;
		node->next = tmp_node;
		node = roxml_set_axes(tmp_node, axes+strlen(ROXML_L_DESC_O_SELF), offset);
	} else if(strncmp(ROXML_L_DESC, axes, strlen(ROXML_L_DESC))==0) {
		// ROXML_L_DESC
		node->axes = ROXML_ID_DESC;
		node->name = axes+strlen(ROXML_L_DESC);
		*offset += strlen(ROXML_L_DESC);
		tmp_node = (xpath_node_t*)calloc(1, sizeof(xpath_node_t));
		tmp_node->axes = ROXML_ID_CHILD;
		node->next = tmp_node;
		node = roxml_set_axes(tmp_node, axes+strlen(ROXML_L_DESC), offset);
	} else {
		int i = 0;

		// ROXML_S_CHILD is default
		node->axes = ROXML_ID_CHILD;
		node->name = axes;

		for(i = 0; i < 14; i++) {
			int len = strlen(xpath_axes[i].name);
			if(strncmp(xpath_axes[i].name, axes, len)==0) {
				node->axes = xpath_axes[i].id;
				node->name = axes+len;
				break;
			}
		}
	}
	return node;
}

int ROXML_INT roxml_get_node_internal_position(node_t *n)
{
	int idx = 1;
	node_t * prnt;
	node_t * first;
	if(n == NULL) { return 0; }

	prnt = n->prnt;
	if(!prnt) {
		return 1;
	}
	first = prnt->chld;

	while((first)&&(first != n)) {
		idx++;
		first = first->sibl;
	}

	return idx;
}

int ROXML_INT roxml_parse_xpath(char *path, xpath_node_t ** xpath, int context)
{
	int ret = 0;
	roxml_xpath_ctx_t ctx;
	roxml_parser_item_t * parser = NULL;
	ctx.pos = 0;
	ctx.nbpath = 1;
	ctx.bracket = 0;
	ctx.parenthesys = 0;
	ctx.quoted = 0;
	ctx.dquoted = 0;
	ctx.content_quoted = 0;
	ctx.is_first_node = 1;
	ctx.wait_first_node = 1;
	ctx.shorten_cond = 0;
	ctx.context = context;
	ctx.first_node = (xpath_node_t*)calloc(1, sizeof(xpath_node_t));
	ctx.new_node = ctx.first_node;
	ctx.new_cond = NULL;
	ctx.first_node->rel = ROXML_OPERATOR_OR;

	parser = roxml_append_parser_item(parser, " ", _func_xpath_ignore);
	parser = roxml_append_parser_item(parser, "\t", _func_xpath_ignore);
	parser = roxml_append_parser_item(parser, "\n", _func_xpath_ignore);
	parser = roxml_append_parser_item(parser, "\r", _func_xpath_ignore);
	parser = roxml_append_parser_item(parser, "\"", _func_xpath_dquote);
	parser = roxml_append_parser_item(parser, "\'", _func_xpath_quote);
	parser = roxml_append_parser_item(parser, "/", _func_xpath_new_node);
	parser = roxml_append_parser_item(parser, "(", _func_xpath_open_parenthesys);
	parser = roxml_append_parser_item(parser, ")", _func_xpath_close_parenthesys);
	parser = roxml_append_parser_item(parser, "[", _func_xpath_open_brackets);
	parser = roxml_append_parser_item(parser, "]", _func_xpath_close_brackets);
	parser = roxml_append_parser_item(parser, "=", _func_xpath_operator_equal);
	parser = roxml_append_parser_item(parser, ">", _func_xpath_operator_sup);
	parser = roxml_append_parser_item(parser, "<", _func_xpath_operator_inf);
	parser = roxml_append_parser_item(parser, "!", _func_xpath_operator_diff);
	parser = roxml_append_parser_item(parser, "0", _func_xpath_number);
	parser = roxml_append_parser_item(parser, "1", _func_xpath_number);
	parser = roxml_append_parser_item(parser, "2", _func_xpath_number);
	parser = roxml_append_parser_item(parser, "3", _func_xpath_number);
	parser = roxml_append_parser_item(parser, "4", _func_xpath_number);
	parser = roxml_append_parser_item(parser, "5", _func_xpath_number);
	parser = roxml_append_parser_item(parser, "6", _func_xpath_number);
	parser = roxml_append_parser_item(parser, "7", _func_xpath_number);
	parser = roxml_append_parser_item(parser, "8", _func_xpath_number);
	parser = roxml_append_parser_item(parser, "9", _func_xpath_number);
	parser = roxml_append_parser_item(parser, "+", _func_xpath_operator_add);
	parser = roxml_append_parser_item(parser, "-", _func_xpath_operator_subs);
	parser = roxml_append_parser_item(parser, ROXML_PATH_OR, _func_xpath_path_or);
	parser = roxml_append_parser_item(parser, ROXML_COND_OR, _func_xpath_condition_or);
	parser = roxml_append_parser_item(parser, ROXML_COND_AND, _func_xpath_condition_and);
	parser = roxml_append_parser_item(parser, ROXML_FUNC_POS_STR, _func_xpath_position);
	parser = roxml_append_parser_item(parser, ROXML_FUNC_FIRST_STR, _func_xpath_first);
	parser = roxml_append_parser_item(parser, ROXML_FUNC_LAST_STR, _func_xpath_last);
	parser = roxml_append_parser_item(parser, ROXML_FUNC_NSURI_STR, _func_xpath_nsuri);
	parser = roxml_append_parser_item(parser, NULL, _func_xpath_default);

	parser = roxml_parser_prepare(parser);
	ret = roxml_parse_line(parser, path, 0, &ctx);
	roxml_parser_free(parser);

	if(ret >= 0) {
		if(xpath)	{
			*xpath = ctx.first_node;
		}
		return ctx.nbpath;
	}

	roxml_free_xpath(ctx.first_node, ctx.nbpath);
	return -1;
}

void ROXML_INT roxml_free_xcond(xpath_cond_t *xcond)
{
	if(xcond->next) {
		roxml_free_xcond(xcond->next);
	}
	if(xcond->xp) {
		roxml_free_xpath(xcond->xp, xcond->func2);
	}
	free(xcond);
}

void ROXML_INT roxml_free_xpath(xpath_node_t *xpath, int nb)
{
	int i = 0;
	for(i = 0; i < nb; i++)	{
		if(xpath[i].next)	{
			roxml_free_xpath(xpath[i].next, 1);
		}
		if(xpath[i].cond)	{
			roxml_free_xcond(xpath[i].cond);
		}
		free(xpath[i].xp_cond);
	}
	free(xpath);
}

double ROXML_INT roxml_double_oper(double a, double b, int op)
{
	if(op == ROXML_OPERATOR_ADD) {
		return (a+b);
	} else if(op == ROXML_OPERATOR_SUB) {
		return (a-b);
	} else if(op == ROXML_OPERATOR_MUL) {
		return (a*b);
	} else if(op == ROXML_OPERATOR_DIV) {
		return (a/b);
	}
	return 0;
}

int ROXML_INT roxml_double_cmp(double a, double b, int op)
{
	if(op == ROXML_OPERATOR_DIFF) {
		return (a!=b);
	} else if(op == ROXML_OPERATOR_EINF) {
		return (a<=b);
	} else if(op == ROXML_OPERATOR_INF) {
		return (a<b);
	} else if(op == ROXML_OPERATOR_ESUP) {
		return (a>=b);
	} else if(op == ROXML_OPERATOR_SUP) {
		return (a>b);
	} else if(op == ROXML_OPERATOR_EQU) {
		return (a==b);
	}
	return 0;
}

int ROXML_INT roxml_string_cmp(char *sa, char *sb, int op)
{
	int result;
	
	result = strcmp(sa, sb);

	if(op == ROXML_OPERATOR_DIFF) {
		return (result != 0);
	} else if(op == ROXML_OPERATOR_EINF) {
		return (result <= 0);
	} else if(op == ROXML_OPERATOR_INF) {
		return (result < 0);
	} else if(op == ROXML_OPERATOR_ESUP) {
		return (result >= 0);
	} else if(op == ROXML_OPERATOR_SUP) {
		return (result > 0);
	} else if(op == ROXML_OPERATOR_EQU) {
		return (result == 0);
	}
	return 0;
}

int ROXML_INT roxml_validate_predicat(xpath_node_t *xn, node_t *candidat)
{
	int first = 1;
	int valid = 0;
	xpath_cond_t *condition;

	if(xn == NULL) { return 1; }

	condition = xn->cond;

	if(!condition)	{
		return 1;
	}

	while(condition) {
		int status = 0;
		double iarg1;
		double iarg2;
		char * sarg1;
		char * sarg2;

		if(condition->func == ROXML_FUNC_POS) {
			status = 0;
			iarg2 = atof(condition->arg2);
			if(xn->name[0] == '*') {
				iarg1 = roxml_get_node_internal_position(candidat);
			} else {
				iarg1 = roxml_get_node_position(candidat);
			}
			status = roxml_double_cmp(iarg1, iarg2, condition->op);
		} else if(condition->func == ROXML_FUNC_LAST) {
			status = 0;
			iarg2 = roxml_get_chld_nb(candidat->prnt);
			if(xn->name[0] == '*') {
				iarg1 = roxml_get_node_internal_position(candidat);
			} else {
				iarg1 = roxml_get_node_position(candidat);
			}
			if(condition->op > 0) {
				double operand = 0;
				operand = atof(condition->arg2);
				iarg2 = roxml_double_oper(iarg2, operand, condition->op);
			}	
			status = roxml_double_cmp(iarg1, iarg2, ROXML_OPERATOR_EQU);
		} else if(condition->func == ROXML_FUNC_FIRST) {
			status = 0;
			iarg2 = 1;
			if(xn->name[0] == '*') {
				iarg1 = roxml_get_node_internal_position(candidat);
			} else {
				iarg1 = roxml_get_node_position(candidat);
			}
			if(condition->op > 0) {
				double operand = 0;
				operand = atof(condition->arg2);
				iarg2 = roxml_double_oper(iarg2, operand, condition->op);
			}
			status = roxml_double_cmp(iarg1, iarg2, ROXML_OPERATOR_EQU);
		} else if(condition->func == ROXML_FUNC_INTCOMP) {
			char strval[ROXML_BASE_LEN];
			node_t *val = roxml_get_attr(candidat, condition->arg1+1, 0);
			status = 0;
			if(val) {
				iarg1 = atof(roxml_get_content(val, strval, ROXML_BASE_LEN, &status));
				if(status >= ROXML_BASE_LEN) {
					iarg1 = atof(roxml_get_content(val, NULL, 0, &status));
					roxml_release(RELEASE_LAST);
				}
				iarg2 = atof(condition->arg2);
				status = roxml_double_cmp(iarg1, iarg2, condition->op);
			}
		} else if(condition->func == ROXML_FUNC_NSURI) {
			char strval[ROXML_BASE_LEN];
			node_t *val = roxml_get_ns(candidat);
			status = 0;
			if(val) {
				sarg1 = roxml_get_content(val, strval, ROXML_BASE_LEN, &status);
				if(status >= ROXML_BASE_LEN) {
					sarg1 = roxml_get_content(val, NULL, 0, &status);
				}
				sarg2 = condition->arg2;
				status = roxml_string_cmp(sarg1, sarg2, condition->op);
				roxml_release(sarg1);
			} else {
				sarg2 = condition->arg2;
				status = roxml_string_cmp("", sarg2, condition->op);
			}
		} else if(condition->func == ROXML_FUNC_STRCOMP) {
			char strval[ROXML_BASE_LEN];
			node_t *val = roxml_get_attr(candidat, condition->arg1+1, 0);
			status = 0;
			if(val) {
				sarg1 = roxml_get_content(val, strval, ROXML_BASE_LEN, &status);
				if(status >= ROXML_BASE_LEN) {
					sarg1 = roxml_get_content(val, NULL, 0, &status);
				}
				sarg2 = condition->arg2;
				status = roxml_string_cmp(sarg1, sarg2, condition->op);
				roxml_release(sarg1);
			}
		} else if(condition->func == ROXML_FUNC_XPATH) {
			int index = condition->func2;
			int count = 0;
			node_t *root = roxml_get_root(candidat);
			node_t **node_set;
			status = 0;

			node_set = roxml_exec_xpath(root, candidat, condition->xp, index, &count);

			roxml_release(node_set);

			if(count > 0) { status = 1; }

		}

		if(first) {
			valid = status;
			first = 0;
		} else {
			if(condition->rel == ROXML_OPERATOR_OR) {
				valid = valid || status;
			} else if(condition->rel == ROXML_OPERATOR_AND) {
				valid = valid && status;
			}
		}
		condition = condition->next;
	}

	return valid;
}

int ROXML_INT roxml_request_id(node_t *root)
{
	int i = 0;
	xpath_tok_table_t * table = NULL;

	while(root->prnt) {
		root = root->prnt;
	}

	table = (xpath_tok_table_t*)root->priv;

	pthread_mutex_lock(&table->mut);
	for(i = ROXML_XPATH_FIRST_ID; i < 255; i++) {
		if(table->ids[i] == 0) {
			table->ids[i]++;
			pthread_mutex_unlock(&table->mut);
			return i;
		}
	}
	pthread_mutex_unlock(&table->mut);
	return -1;
}

int ROXML_INT roxml_in_pool(node_t * root, node_t *n, int req_id)
{
	xpath_tok_table_t * table = NULL;

	while(root->prnt) {
		root = root->prnt;
	}

	table = (xpath_tok_table_t*)root->priv;

	pthread_mutex_lock(&table->mut);
	if(n->priv) {
		xpath_tok_t * tok = (xpath_tok_t*)n->priv;
		if(tok->id == req_id) {
			pthread_mutex_unlock(&table->mut);
			return 1;
		} else {
			while(tok) {
				if(tok->id == req_id) {
					pthread_mutex_unlock(&table->mut);
					return 1;
				}
				tok = tok->next;
			}
		}
	}
	pthread_mutex_unlock(&table->mut);
	return 0;
}

void ROXML_INT roxml_release_id(node_t *root, node_t **pool, int pool_len, int req_id)
{
	int i = 0;
	xpath_tok_table_t * table = NULL;

	while(root->prnt) {
		root = root->prnt;
	}

	table = (xpath_tok_table_t*)root->priv;

	for(i = 0; i < pool_len; i++) {
		roxml_del_from_pool(root, pool[i], req_id);
	}
	pthread_mutex_lock(&table->mut);
	table->ids[req_id] = 0;
	pthread_mutex_unlock(&table->mut);
}

void roxml_del_from_pool(node_t * root, node_t *n, int req_id)
{
	xpath_tok_table_t * table = NULL;

	while(root->prnt) {
		root = root->prnt;
	}

	table = (xpath_tok_table_t*)root->priv;

	pthread_mutex_lock(&table->mut);
	if(n->priv) {
		xpath_tok_t * prev = (xpath_tok_t*)n->priv;
		xpath_tok_t * tok = (xpath_tok_t*)n->priv;
		if(tok->id == req_id) {
			n->priv = (void*)tok->next;
			free(tok);
		} else {
			while(tok) {
				if(tok->id == req_id) {
					prev->next = tok->next;
					free(tok);
					break;
				}
				prev = tok;
				tok = tok->next;
			}
		}
	}
	pthread_mutex_unlock(&table->mut);
}

int ROXML_INT roxml_add_to_pool(node_t *root, node_t *n, int req_id)
{
	xpath_tok_table_t * table;
	xpath_tok_t * tok;
	xpath_tok_t * last_tok = NULL;

	while(root->prnt) {
		root = root->prnt;
	}

	if(req_id == 0) { return 1; }
	table = (xpath_tok_table_t*)root->priv;


	pthread_mutex_lock(&table->mut);
	tok = (xpath_tok_t*)n->priv;

	while(tok) {
		if(tok->id == req_id) {
			pthread_mutex_unlock(&table->mut);
			return 0;
		}
		last_tok = tok;
		tok = (xpath_tok_t*)tok->next;
	}
	if(last_tok == NULL) {
		n->priv = calloc(1, sizeof(xpath_tok_t));
		last_tok = (xpath_tok_t*)n->priv;
	} else {
		last_tok->next = (xpath_tok_t*)calloc(1, sizeof(xpath_tok_t));
		last_tok = last_tok->next;
	}
	last_tok->id = req_id;
	pthread_mutex_unlock(&table->mut);

	return 1;
}

int ROXML_INT roxml_validate_axes(node_t *root, node_t *candidat, node_t ***ans, int *nb, int *max, xpath_node_t *xn, int req_id)
{
	
	int valid = 0;
	int path_end = 0;
	char * axes = NULL;
	char intern_buff[INTERNAL_BUF_SIZE];
	char intern_buff2[INTERNAL_BUF_SIZE];

	if(xn == NULL) {
		valid = 1;
		path_end = 1;
	} else {
		axes = xn->name;

		if((axes == NULL) || (strcmp("node()", axes) == 0))  { 
			valid = 1;
		} else if(strcmp("*", axes) == 0)  { 
			if(candidat->type & ROXML_ELM_NODE) { valid = 1; }
			if(candidat->type & ROXML_ATTR_NODE) { valid = 1; }
		} else if(strcmp("comment()", axes) == 0)  { 
			if(candidat->type & ROXML_CMT_NODE) { valid = 1; }
		} else if(strcmp("processing-instruction()", axes) == 0)  { 
			if(candidat->type & ROXML_PI_NODE) { valid = 1; }
		} else if(strcmp("text()", axes) == 0)  { 
			if(candidat->type & ROXML_TXT_NODE) { valid = 1; }
		} else if(strcmp("", axes) == 0)  {
			if(xn->abs) {
				candidat = root;
				valid = 1;
			}
		}
		if(!valid) {
			if(candidat->type & ROXML_PI_NODE) { return 0; }
			if(candidat->type & ROXML_CMT_NODE) { return 0; }
		}
		if(xn->next == NULL) { path_end = 1; }
		if((xn->axes == ROXML_ID_SELF)||(xn->axes == ROXML_ID_PARENT)) { valid = 1; }
	}

	if(!valid) {
		int ns_len = 0;
		char * name  = intern_buff;
		if(candidat->ns) {
			name = roxml_get_name(candidat->ns, intern_buff, INTERNAL_BUF_SIZE);
			ns_len = strlen(name);
			if(ns_len) {
				name[ns_len] = ':';
				ns_len++;
			}
		}
		roxml_get_name(candidat, intern_buff+ns_len, INTERNAL_BUF_SIZE-ns_len);
		if(name && strcmp(name, axes) == 0)	{
			valid = 1;
		}	
	}

	if(valid)	{
		valid = roxml_validate_predicat(xn, candidat);
	}

	if((valid)&&(xn->xp_cond)) {
		int status;
		char * sarg1;
		char * sarg2;
		xpath_cond_t * condition = xn->xp_cond;
		valid = 0;
		if(condition->func == ROXML_FUNC_STRCOMP) {
			char strval[ROXML_BASE_LEN];
			sarg1 = roxml_get_content(candidat, strval, ROXML_BASE_LEN, &status);
			if(status >= ROXML_BASE_LEN) {
				sarg1 = roxml_get_content(candidat, NULL, 0, &status);
			}
			sarg2 = condition->arg2;
			valid = roxml_string_cmp(sarg1, sarg2, condition->op);
			roxml_release(sarg1);
		} else if(condition->func == ROXML_FUNC_INTCOMP) {
			double iarg1;
			double iarg2;
			char strval[ROXML_BASE_LEN];
			iarg1 = atof(roxml_get_content(candidat, strval, ROXML_BASE_LEN, &status));
			if(status >= ROXML_BASE_LEN) {
				iarg1 = atof(roxml_get_content(candidat, NULL, 0, &status));
				roxml_release(RELEASE_LAST);
			}
			iarg2 = atof(condition->arg2);
			valid = roxml_double_cmp(iarg1, iarg2, condition->op);
		}
	}

	if((valid)&&(path_end)) {
		if(roxml_add_to_pool(root, candidat, req_id)) {
			if(ans) {
				if((*nb) >= (*max))	{
					int new_max = (*max)*2;
					node_t ** new_ans = roxml_malloc(sizeof(node_t*), new_max, PTR_NODE_RESULT);
					memcpy(new_ans, (*ans), *(max)*sizeof(node_t*)); 
					roxml_release(*ans);
					*ans = new_ans;
					*max = new_max;
				}
				(*ans)[*nb] = candidat;
			}
			(*nb)++;
		}
	}

	return valid;
}

void ROXML_INT roxml_check_node(xpath_node_t *xp, node_t *root, node_t *context, node_t ***ans, int *nb, int *max, int ignore, int req_id)
{
	int validate_node = 0;

	if((req_id == 0) && (*nb > 0)) {
		return;
	}

	if(!xp)	{ return; }

	// if found a "all document" axes
	if(ignore == ROXML_DESC_ONLY)	{
		node_t *current = context->chld;
		while(current)	{
			roxml_check_node(xp, root, current, ans, nb, max, ignore, req_id);
			current = current->sibl;
		}
	}

	switch(xp->axes) {
		case ROXML_ID_CHILD: {
			node_t *current = context->chld;
			while(current)	{
				validate_node = roxml_validate_axes(root, current, ans, nb, max, xp, req_id);
				if(validate_node)	{
					roxml_check_node(xp->next, root, current, ans, nb, max, ROXML_DIRECT, req_id);
				}
				current = current->sibl;
			}
			if((xp->name == NULL)||(strcmp(xp->name, "text()") == 0)||(strcmp(xp->name, "node()") == 0)) {
				node_t *current = roxml_get_txt(context, 0);
				while(current)	{
					validate_node = roxml_validate_axes(root, current, ans, nb, max, xp, req_id);
					current = current->sibl;
				}
			} 
			if((xp->name == NULL)||(strcmp(xp->name, "node()") == 0)) {
				node_t *current = context->attr;
				while(current)	{
					validate_node = roxml_validate_axes(root, current, ans, nb, max, xp, req_id);
					current = current->sibl;
				}
			}
		} break;
		case ROXML_ID_DESC: {
			xp = xp->next;
			roxml_check_node(xp, root, context, ans, nb, max, ROXML_DESC_ONLY, req_id);
		} break;
		case ROXML_ID_DESC_O_SELF: {
			xp = xp->next;
			validate_node = roxml_validate_axes(root, context, ans, nb, max, xp, req_id);
			if(validate_node) {
				roxml_check_node(xp->next, root, context, ans, nb, max, ROXML_DIRECT, req_id);
			}
			roxml_check_node(xp, root, context, ans, nb, max, ROXML_DESC_ONLY, req_id);
		} break;
		case ROXML_ID_SELF: {
			validate_node = roxml_validate_axes(root, context, ans, nb, max, xp, req_id);
			roxml_check_node(xp->next, root, context, ans, nb, max, ROXML_DIRECT, req_id);
		} break;
		case ROXML_ID_PARENT: {
			if (context->prnt) {
				validate_node = roxml_validate_axes(root, context->prnt, ans, nb, max, xp, req_id);
				roxml_check_node(xp->next, root, context->prnt, ans, nb, max, ROXML_DIRECT, req_id);
			} else {
				validate_node = 0;
			}
		} break;
		case ROXML_ID_ATTR: {
			node_t *attribute = context->attr;
			while(attribute)  {
				validate_node = roxml_validate_axes(root, attribute, ans, nb, max, xp, req_id);
				if(validate_node)	{
					roxml_check_node(xp->next, root, context, ans, nb, max, ROXML_DIRECT, req_id);
				}
				attribute = attribute->sibl;
			}
		} break;
		case ROXML_ID_ANC: {
			node_t *current = context->prnt;
			while(current)	{
				validate_node = roxml_validate_axes(root, current, ans, nb, max, xp, req_id);
				if(validate_node)	{
					roxml_check_node(xp->next, root, current, ans, nb, max, ROXML_DIRECT, req_id);
				}
				current = current->prnt;
			}
		} break;
		case ROXML_ID_NEXT_SIBL: {
			node_t *current = context->sibl;
			while(current)	{
				validate_node = roxml_validate_axes(root, current, ans, nb, max, xp, req_id);
				if(validate_node)	{
					roxml_check_node(xp->next, root, current, ans, nb, max, ROXML_DIRECT, req_id);
				}
				current = current->sibl;
			}
		} break;
		case ROXML_ID_PREV_SIBL: {
			node_t *current = context->prnt->chld;
			while(current != context)	{
				validate_node = roxml_validate_axes(root, current, ans, nb, max, xp, req_id);
				if(validate_node)	{
					roxml_check_node(xp->next, root, current, ans, nb, max, ROXML_DIRECT, req_id);
				}
				current = current->sibl;
			}
		} break;
		case ROXML_ID_NEXT: {
			node_t *current = context;
			while(current)  {
				node_t * following = current->sibl;
				while(following) {
					validate_node = roxml_validate_axes(root, following, ans, nb, max, xp, req_id);
					if(validate_node)	{
						roxml_check_node(xp->next, root, following, ans, nb, max, ROXML_DIRECT, req_id);
					} else {
						xp->axes = ROXML_ID_CHILD;
						roxml_check_node(xp, root, following, ans, nb, max, ROXML_DESC_ONLY, req_id);
						xp->axes = ROXML_ID_NEXT;
					}
					following = following->sibl;
				}
				following = current->prnt->chld;
				while(following != current) { following = following->sibl; }
				current = following->sibl;
			}
		} break;
		case ROXML_ID_PREV: {
			node_t *current = context;
			while(current && current->prnt) {
				node_t *preceding = current->prnt->chld;
				while(preceding != current)  {
					validate_node = roxml_validate_axes(root, preceding, ans, nb, max, xp, req_id);
					if(validate_node)	{
						roxml_check_node(xp->next, root, preceding, ans, nb, max, ROXML_DIRECT, req_id);
					} else {
						xp->axes = ROXML_ID_CHILD;
						roxml_check_node(xp, root, preceding, ans, nb, max, ROXML_DESC_ONLY, req_id);
						xp->axes = ROXML_ID_PREV;
					}
					preceding = preceding->sibl;
				}
				current = current->prnt;
			}
		} break;
		case ROXML_ID_NS: {
			validate_node = roxml_validate_axes(root, context->ns, ans, nb, max, xp, req_id);
			if(validate_node)	{
				roxml_check_node(xp->next, root, context, ans, nb, max, ROXML_DIRECT, req_id);
			}
		} break;
		case ROXML_ID_ANC_O_SELF: {
			node_t *current = context;
			while(current)	{
				validate_node = roxml_validate_axes(root, current, ans, nb, max, xp, req_id);
				if(validate_node)	{
					roxml_check_node(xp->next, root, current, ans, nb, max, ROXML_DIRECT, req_id);
				}
				current = current->prnt;
			}
		} break;
	}


	return;
}

node_t * ROXML_INT roxml_parent_node(node_t *parent, node_t * n, int position)
{
	int nb;

	if(n == NULL) { return NULL; }
	if(parent == NULL) { return n; }
	
	n->prnt = parent;

	if(parent->ns && ((parent->ns->type & ROXML_INVALID) != ROXML_INVALID) && ((roxml_ns_t*)parent->ns->priv)->alias[0] == '\0') {
		if(n->ns == NULL) {
			n->ns = parent->ns;
		}
	}

	if(n->type & ROXML_ATTR_NODE) {
		nb = roxml_get_attr_nb(n->prnt);
	} else {
		nb = roxml_get_nodes_nb(n->prnt, ROXML_PI_NODE | ROXML_CMT_NODE | ROXML_TXT_NODE | ROXML_ELM_NODE | ROXML_DOCTYPE_NODE);
	}

	if((position == 0)||(position > nb)) {
		position = nb+1;
		if((roxml_get_type(n) == ROXML_ELM_NODE)||
			(roxml_get_type(n) == ROXML_DOCTYPE_NODE)||
			(roxml_get_type(n) == ROXML_TXT_NODE)||
			(roxml_get_type(n) == ROXML_CMT_NODE)||
			(roxml_get_type(n) == ROXML_PI_NODE)) 
		{
			parent->next = n;
		}
	}

	if(position == 1) {
		if(n->type & ROXML_ATTR_NODE) {
			node_t *first = parent->attr;
			parent->attr = n;
			n->sibl = first;
		} else {
			node_t *first = parent->chld;
			parent->chld = n;
			n->sibl = first;
		}
	} else {
		int i;
		node_t * prev = NULL;
		node_t * next = NULL;

		if(n->type & ROXML_ATTR_NODE) {
			prev = parent->attr;
			next = parent->attr;
		} else {
			prev = parent->chld;
			next = parent->chld;
		}
		for(i = 1; i < position; i++) {
			prev = next;
			next = next->sibl;
		}
		n->sibl = next;
		prev->sibl = n;
	}
	return n;
}

void ROXML_INT roxml_print_space(FILE *f, char ** buf, int * offset, int * len, int lvl)
{
	int i = 0;

	if(lvl > 0) {
		if(buf && *buf) {
			int pos = *offset + lvl;
			if(pos >= *len) { 
				*buf = realloc(*buf, *len+ROXML_LONG_LEN); 
				memset(*buf+*len, 0, ROXML_LONG_LEN); 
				*len += ROXML_LONG_LEN;
			}
			for(; i < lvl; i++) {
				strcat(*buf, " ");
			}
			*offset = pos;
		}
		if(f) {
			for(; i < lvl; i++) {
				fwrite(" ", 1, 1, f);
			}
		}
	}
}

void ROXML_INT roxml_write_string(char ** buf, FILE * f, char * str, int *offset, int * len)
{
	int min_len = strlen(str);
	int pos = *offset + min_len;
	int appended_space = ROXML_LONG_LEN*((int)(min_len/ROXML_LONG_LEN)+1);

	if((pos >= *len) && (buf) && (*buf)) { 
		*buf = realloc(*buf, *len+appended_space); 
		memset(*buf+*len, 0, appended_space);
		*len += appended_space;
	}
	if(f) { fprintf(f, "%s", str); }
	if(buf && *buf) { strcat(*buf+(*offset), str); }
	*offset = pos;
}

void ROXML_INT roxml_write_node(node_t * n, FILE *f, char ** buf, int human, int lvl, int *offset, int *len)
{
	char name[ROXML_BASE_LEN];
	char ns[ROXML_BASE_LEN];
	roxml_get_name(n, name, ROXML_BASE_LEN);
	if(human) {
		roxml_print_space(f, buf, offset, len, lvl);
	}
	if(roxml_get_type(n) == ROXML_ELM_NODE) {
		node_t *attr = n->attr;
		if(n->prnt) {
			roxml_write_string(buf, f, "<", offset, len);
			if(n->ns) {
				roxml_get_name(n->ns, ns, ROXML_BASE_LEN);
				if(ns[0] != '\0') {
					roxml_write_string(buf, f, ns, offset, len);
					roxml_write_string(buf, f, ":", offset, len);
				}
			}
			roxml_write_string(buf, f, name, offset, len);
		}
		while(attr)	{
			int status = 0;
			char *value;
			char arg[ROXML_BASE_LEN];
			char val[ROXML_BASE_LEN];
			char arg_ns[ROXML_BASE_LEN];
			roxml_get_name(attr, arg, ROXML_BASE_LEN);
			value = roxml_get_content(attr, val, ROXML_BASE_LEN, &status);
			if(status >= ROXML_BASE_LEN) {
				value = roxml_get_content(attr, NULL, 0, &status);
			}
			roxml_write_string(buf, f, " ", offset, len);
			if(attr->type & ROXML_NS_NODE) {
				roxml_write_string(buf, f, "xmlns", offset, len);
				if(arg[0] != '\0') {
					roxml_write_string(buf, f, ":", offset, len);
				}
			}
			if(attr->ns) {
				roxml_get_name(attr->ns, arg_ns, ROXML_BASE_LEN);
				if(arg_ns[0] != '\0') {
					roxml_write_string(buf, f, arg_ns, offset, len);
					roxml_write_string(buf, f, ":", offset, len);
				}
			}
			roxml_write_string(buf, f, arg, offset, len);
			roxml_write_string(buf, f, "=\"", offset, len);
			roxml_write_string(buf, f, value, offset, len);
			roxml_write_string(buf, f, "\"", offset, len);
			attr = attr->sibl;
			roxml_release(value);
		}
		if(n->chld)	{
			node_t *chld = n->chld;
			if(n->prnt) {
				roxml_write_string(buf, f, ">", offset, len);
				if(human) {
					roxml_write_string(buf, f, "\n", offset, len);
				}
			}
			while(chld)	{
				char val[ROXML_LONG_LEN];
				if(chld->type & ROXML_TXT_NODE) {
					char *value;
					int status;
					if(human) {
						roxml_print_space(f, buf, offset, len, lvl+1);
					}
					value = roxml_get_content(chld, val, ROXML_LONG_LEN, &status);
					if(status >= ROXML_LONG_LEN) {
						value = roxml_get_content(chld, NULL, 0, &status);
					}
					if((chld->type & ROXML_CDATA_NODE) == ROXML_CDATA_NODE) {
						roxml_write_string(buf, f, "<![CDATA[", offset, len);
						roxml_write_string(buf, f, value, offset, len);
						roxml_write_string(buf, f, "]]>", offset, len);
					} else {
						char * end = value + status - 2;
						char * strip = value;
						while(roxml_is_separator(end[0])) {
							end[0] = '\0';
							end--;
						}
						while(roxml_is_separator(strip[0])) {
							strip++;
						}

						roxml_write_string(buf, f, strip, offset, len);
					}
					if(human) {
						roxml_write_string(buf, f, "\n", offset, len);
					}
					chld = chld->sibl;
					roxml_release(value);
				} else {
					roxml_write_node(chld, f, buf, human, lvl+1, offset, len);
					chld = chld->sibl;
				}
			}
			if(n->prnt) {
				if(human) {
					roxml_print_space(f, buf, offset, len, lvl);
				}
				roxml_write_string(buf, f, "</", offset, len);
				if(n->ns) {
					if(ns[0] != '\0') {
						roxml_write_string(buf, f, ns, offset, len);
						roxml_write_string(buf, f, ":", offset, len);
					}
				}
				roxml_write_string(buf, f, name, offset, len);
				roxml_write_string(buf, f, ">", offset, len);
				if(human) {
					roxml_write_string(buf, f, "\n", offset, len);
				}
			}
		} else {
			roxml_write_string(buf, f, "/>", offset, len);
			if(human) {
				roxml_write_string(buf, f, "\n", offset, len);
			}
		}
	} else {
		char *name;
		char *value;
		int status;
		char val[ROXML_LONG_LEN];
		char head[8];
		char tail[8];

		if(roxml_get_type(n) == ROXML_CMT_NODE) {
			strcpy(head, "<!--");
			strcpy(tail, "-->");
		} else if(roxml_get_type(n) == ROXML_DOCTYPE_NODE) {
			strcpy(head, "<");
			strcpy(tail, ">");
		} else if(roxml_get_type(n) == ROXML_PI_NODE) {
			strcpy(head, "<?");
			strcpy(tail, "?>");
		}

		roxml_write_string(buf, f, head, offset, len);

		name = roxml_get_name(n, val, ROXML_LONG_LEN);
		if(name[0]) {
			roxml_write_string(buf, f, name, offset, len);
		} else {
			name = NULL;
		}

		value = roxml_get_content(n, val, ROXML_LONG_LEN, &status);
		if(status >= ROXML_LONG_LEN) {
			value = roxml_get_content(n, NULL, 0, &status);
		}
		if(name && value && value[0]) {
			roxml_write_string(buf, f, " ", offset, len);
		}
		roxml_write_string(buf, f, value, offset, len);
		roxml_release(value);

		roxml_write_string(buf, f, tail, offset, len);

		if(human) {
			roxml_write_string(buf, f, "\n", offset, len);
		}
	}
}

void ROXML_INT roxml_reset_ns(node_t *n, node_t *ns)
{
	node_t * attr = NULL;
	node_t * chld = NULL;

	if(!n) {
		return;
	}

	if(n->ns == ns) {
		if(n->prnt) {
			n->ns = n->prnt->ns;
		} else {
			n->ns = NULL;
		}
	}

	chld = n->chld;
	while(chld) {
		roxml_reset_ns(chld, ns);
		chld = chld->sibl;
	}

	attr = n->attr;
	while(attr) {
		if((attr->type & ROXML_NS_NODE) == 0) {
			if(attr->ns == ns) {
				attr->ns = attr->prnt->ns;
			}
		}
		attr = attr->sibl;
	}
}

void ROXML_INT roxml_del_arg_node(node_t * n) 
{
	node_t *current = n->prnt->attr;

	if(n->type & ROXML_NS_NODE) {
		roxml_reset_ns(n->prnt, n);
	}

	if(current == n) {
		n->prnt->attr = n->sibl;
	} else if(current) {
		while(current->sibl && current->sibl != n) {
			current = current->sibl;
		}
		current->sibl = n->sibl;
	}
	roxml_del_tree(n->chld);
	return;
}

void ROXML_INT roxml_del_txt_node(node_t * n)
{
	node_t *current = n->prnt->chld;
	while(current && (current->type & ROXML_TXT_NODE) == 0) {
		current = current->sibl;
	}
	if(current == n) {
		n->prnt->chld = n->sibl;
	} else if(current) {
		while(current->sibl && current->sibl != n) {
			current = current->sibl;
		}
		current->sibl = n->sibl;
	}
} 

void ROXML_INT roxml_del_std_node(node_t * n)
{
	node_t *current = n->prnt->chld;
	if(current == n) {
		n->prnt->chld = n->sibl;
	} else if(current) {
		while(current->sibl && current->sibl != n) {
			current = current->sibl;
		}
		current->sibl = n->sibl;
	}
	roxml_del_tree(n->chld);
	roxml_del_tree(n->attr);
} 

void roxml_compute_and(node_t * root, node_t **node_set, int *count, int cur_req_id, int prev_req_id)
{
	int i = 0;
	for(i = 0; i < *count; i++) {
		if((!roxml_in_pool(root, node_set[i], cur_req_id)) || (!roxml_in_pool(root, node_set[i], prev_req_id))) {
			(*count)--;
			roxml_del_from_pool(root, node_set[i], cur_req_id);
			roxml_del_from_pool(root, node_set[i], prev_req_id);
			if(*count > 0) {
				node_set[i] = node_set[(*count)-1];
			}
		}
	}
}

void roxml_compute_or(node_t * root, node_t **node_set, int *count, int req_id, int glob_id)
{
	int i = 0;
	for(i = 0; i < *count; i++) {
		if(roxml_in_pool(root, node_set[i], req_id)) {
			roxml_add_to_pool(root, node_set[i], glob_id);
			roxml_del_from_pool(root, node_set[i], req_id);
		}
	}
}


node_t ** roxml_exec_xpath(node_t *root, node_t *n, xpath_node_t *xpath, int index, int * count)
{
	int path_id;
	int max_answers = 1;
	int glob_id = 0;
	int * req_ids = NULL;
	
	node_t **node_set = roxml_malloc(sizeof(node_t*), max_answers, PTR_NODE_RESULT);

	*count = 0;
	req_ids = calloc(index, sizeof(int));
	glob_id = roxml_request_id(root);

	// process all and xpath
	for(path_id = 0; path_id < index; path_id++)	{
		xpath_node_t *cur_xpath = NULL;
		xpath_node_t *next_xpath = NULL;
		cur_xpath = &xpath[path_id];
		if(path_id < index-1) { next_xpath = &xpath[path_id+1]; }

		if((cur_xpath->rel == ROXML_OPERATOR_AND)||((next_xpath) && (next_xpath->rel == ROXML_OPERATOR_AND))) {
			int req_id = roxml_request_id(root);

			node_t *orig = n;
			if(cur_xpath->abs)	{
				// context node is root
				orig = root;
			}
			// assign a new request ID
			roxml_check_node(cur_xpath, root, orig, &node_set, count, &max_answers, ROXML_DIRECT, req_id);
			
			if(cur_xpath->rel == ROXML_OPERATOR_AND) {
				roxml_compute_and(root, node_set,  count, req_id, req_ids[path_id-1]);
			}
			req_ids[path_id] = req_id;
		}
	}
	
	// process all or xpath
	for(path_id = 0; path_id < index; path_id++)	{
		node_t *orig = n;
		xpath_node_t *cur_xpath = &xpath[path_id];

		if(cur_xpath->rel == ROXML_OPERATOR_OR) {
			if(req_ids[path_id] == 0) {
				if(cur_xpath->abs)	{
					// context node is root
					orig = root;
				}
				// assign a new request ID
				roxml_check_node(cur_xpath, root, orig, &node_set, count, &max_answers, ROXML_DIRECT, glob_id);
			} else {
				roxml_compute_or(root, node_set, count, req_ids[path_id+1], glob_id);
				roxml_release_id(root, node_set, *count, req_ids[path_id+1]);
			}
		}
	}
	roxml_release_id(root, node_set, *count, glob_id);

	for(path_id = 0; path_id < index; path_id++)	{
		if(req_ids[path_id] != 0) {
			roxml_release_id(root, node_set, *count, req_ids[path_id]);
		}
	}

	free(req_ids);

	return node_set;
}

