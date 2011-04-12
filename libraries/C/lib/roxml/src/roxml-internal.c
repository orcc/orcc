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
			do {
				buffer[ret_len] = *(node->src.buf+pos+ret_len);
				ret_len++;
				size--;
			} while((buffer[ret_len-1]) && (size));
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
		n->next = NULL;
	}

#ifdef __DEBUG
	if(n->type & ROXML_ELM_NODE) _nb_node++;
	if(n->type & ROXML_ATTR_NODE) _nb_attr++;
	if(n->type & ROXML_TXT_NODE) _nb_text++;
#endif
}

void ROXML_INT roxml_free_node(node_t *n)
{
	if(n->type & ROXML_PENDING) {
		if(n->pos == 0) {
			free(n->src.buf);
		}
	}
	if(n->priv) {
		xpath_tok_t * tok = (xpath_tok_t*)n->priv;
		if(tok->id == ROXML_REQTABLE_ID) {
			xpath_tok_table_t * table = (xpath_tok_table_t*)n->priv;
			tok = table->next;
			pthread_mutex_destroy(&table->mut);
			free(table);
		}
		while(tok) {
			xpath_tok_t * to_delete = tok;
			tok = tok->next;
			free(to_delete);
		}
	}
	free(n);
}

void ROXML_INT roxml_del_tree(node_t *n)
{
	if(n == NULL)	{ return; }
	roxml_del_tree(n->chld);
	roxml_del_tree(n->text);
	roxml_del_tree(n->sibl);
	roxml_del_tree(n->attr);
	roxml_free_node(n);
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

void ROXML_INT roxml_process_begin_node(roxml_load_ctx_t *context, int position)
{
	if(context->candidat_txt)	{
#ifdef IGNORE_EMPTY_TEXT_NODES
		if(context->empty_text_node == 0) {
#endif /* IGNORE_EMPTY_TEXT_NODES */
			node_t * to_be_closed = roxml_create_node(position, context->src, ROXML_TXT_NODE | context->type);
			roxml_close_node(context->candidat_txt, to_be_closed);
#ifdef IGNORE_EMPTY_TEXT_NODES
		} else {
			roxml_del_node(context->candidat_txt);
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
	int int_len = 0;
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
	parser = roxml_append_parser_item(parser, NULL, _func_load_default);

	parser = roxml_parser_prepare(parser);

	if(file)	{ 
		context.type = ROXML_FILE;
		context.src = (void*)file;
		context.pos = 0;
		do {
			int ret = 0;
			int_len = fread(int_buffer, 1, ROXML_BULK_READ, file);
			int_buffer[int_len] = '\0';

			ret = roxml_parse_line(parser, int_buffer, int_len, &context);
			if(ret < 0) {
				error = 1;
				break;
			}
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
		roxml_del_node(context.candidat_txt);
	}
#endif /* IGNORE_EMPTY_TEXT_NODES */

	if(!error) {
		current_node = roxml_get_root(current_node);

		table->id = ROXML_REQTABLE_ID;
		table->ids[ROXML_REQTABLE_ID] = 1;
		pthread_mutex_init(&table->mut, NULL);
		current_node->priv = (void*)table;
	} else {
		roxml_close(current_node);
		return NULL;
	}

	return current_node;
}

void ROXML_INT roxml_set_type(node_t * n, int type)
{
	n->type &= ~(ROXML_ATTR_NODE|ROXML_ELM_NODE|ROXML_TXT_NODE|ROXML_CMT_NODE|ROXML_PI_NODE);
	n->type |= type;
}

xpath_node_t * ROXML_INT roxml_set_axes(xpath_node_t *node, char *axes, int *offset)
{
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
	} else if(strncmp(ROXML_L_PARENT, axes, strlen(ROXML_L_PARENT))==0) {
		// ROXML_L_PARENT
		node->axes = ROXML_ID_PARENT;
		node->name = axes+strlen(ROXML_L_PARENT);
	} else if(strncmp(ROXML_S_PARENT, axes, strlen(ROXML_S_PARENT))==0) {
		// ROXML_S_PARENT
		node->axes = ROXML_ID_PARENT;
		node->name = axes+strlen(ROXML_S_PARENT);
	} else if(strncmp(ROXML_L_SELF, axes, strlen(ROXML_L_SELF))==0) {
		// ROXML_L_SELF
		node->axes = ROXML_ID_SELF;
		node->name = axes+strlen(ROXML_L_SELF);
	} else if(strncmp(ROXML_S_SELF, axes, strlen(ROXML_S_SELF))==0) {
		// ROXML_S_SELF
		node->axes = ROXML_ID_SELF;
		node->name = axes+strlen(ROXML_S_SELF);
	} else if(strncmp(ROXML_L_ATTR, axes, strlen(ROXML_L_ATTR))==0) {
		// ROXML_L_ATTR
		node->axes = ROXML_ID_ATTR;
		node->name = axes+strlen(ROXML_L_ATTR);
	} else if(strncmp(ROXML_S_ATTR, axes, strlen(ROXML_S_ATTR))==0) {
		// ROXML_S_ATTR
		node->axes = ROXML_ID_ATTR;
		node->name = axes+strlen(ROXML_S_ATTR);
	} else if(strncmp(ROXML_L_ANC, axes, strlen(ROXML_L_ANC))==0) {
		// ROXML_L_ANC
		node->axes = ROXML_ID_ANC;
		node->name = axes+strlen(ROXML_L_ANC);
	} else if(strncmp(ROXML_L_ANC_O_SELF, axes, strlen(ROXML_L_ANC_O_SELF))==0) {
		// ROXML_L_ANC_O_SELF
		node->axes = ROXML_ID_ANC_O_SELF;
		node->name = axes+strlen(ROXML_L_ANC_O_SELF);
	} else if(strncmp(ROXML_L_NEXT_SIBL, axes, strlen(ROXML_L_NEXT_SIBL))==0) {
		// ROXML_L_NEXT_SIBL
		node->axes = ROXML_ID_NEXT_SIBL;
		node->name = axes+strlen(ROXML_L_NEXT_SIBL);
	} else if(strncmp(ROXML_L_PREV_SIBL, axes, strlen(ROXML_L_PREV_SIBL))==0) {
		// ROXML_L_PREV_SIBL
		node->axes = ROXML_ID_PREV_SIBL;
		node->name = axes+strlen(ROXML_L_PREV_SIBL);
	} else if(strncmp(ROXML_L_NEXT, axes, strlen(ROXML_L_NEXT))==0) {
		// ROXML_L_NEXT
		node->axes = ROXML_ID_NEXT;
		node->name = axes+strlen(ROXML_L_NEXT);
	} else if(strncmp(ROXML_L_PREV, axes, strlen(ROXML_L_PREV))==0) {
		// ROXML_L_PREV
		node->axes = ROXML_ID_PREV;
		node->name = axes+strlen(ROXML_L_PREV);
	} else if(strncmp(ROXML_L_NS, axes, strlen(ROXML_L_NS))==0) {
		// ROXML_L_NS is not handled
		node->axes = ROXML_ID_NS;
		node->name = axes+strlen(ROXML_L_NS);
	} else if(strncmp(ROXML_L_CHILD, axes, strlen(ROXML_L_CHILD))==0) {
		// ROXML_L_CHILD
		node->axes = ROXML_ID_CHILD;
		node->name = axes+strlen(ROXML_L_CHILD);
	} else {
		// ROXML_S_CHILD
		node->axes = ROXML_ID_CHILD;
		node->name = axes;
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
	int error = 0;
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
				status = (strcmp(sarg1, sarg2)==0);
				roxml_release(sarg1);
			}
		} else if(condition->func == ROXML_FUNC_XPATH) {
			int index = condition->func2;
			int count = 0;
			node_t *root = candidat;
			node_t **node_set;
			status = 0;

			while(root->prnt) { root = root->prnt; }

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

	xpath_tok_table_t * table = (xpath_tok_table_t*)root->priv;
	pthread_mutex_lock(&table->mut);
	for(i = 1; i < 255; i++) {
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
	xpath_tok_table_t * table = (xpath_tok_table_t*)root->priv;
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
	xpath_tok_table_t * table = (xpath_tok_table_t*)root->priv;
	for(i = 0; i < pool_len; i++) {
		roxml_del_from_pool(root, pool[i], req_id);
	}
	pthread_mutex_lock(&table->mut);
	table->ids[req_id] = 0;
	pthread_mutex_unlock(&table->mut);
}

void roxml_del_from_pool(node_t * root, node_t *n, int req_id)
{
	xpath_tok_table_t * table = (xpath_tok_table_t*)root->priv;
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
		tok = tok->next;
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
		char * name  = roxml_get_name(candidat, intern_buff, INTERNAL_BUF_SIZE);
		if(name && strcmp(name, axes) == 0)	{
			valid = 1;
		}	
		roxml_release(name);
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
			valid = (strcmp(sarg1, sarg2)==0);
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
				node_t *current = context->text;
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
			validate_node = roxml_validate_axes(root, context->prnt, ans, nb, max, xp, req_id);
			roxml_check_node(xp->next, root, context->prnt, ans, nb, max, ROXML_DIRECT, req_id);
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
			// ROXML_L_NS is not handled
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

void ROXML_INT roxml_parent_node_at(node_t *parent, node_t * n, int position)
{
	int nb_child = 0;

	if(n == NULL) { return; }
	if(parent == NULL) { return; }
	
	n->prnt = parent;

	if((n->type & ROXML_ELM_NODE)||
		(n->type & ROXML_CMT_NODE)||
		(n->type & ROXML_PI_NODE))
	{
		nb_child = roxml_get_chld_nb(parent);
	} else if(n->type & ROXML_TXT_NODE) {
		nb_child = roxml_get_text_nb(parent);
	} else if(n->type & ROXML_ATTR_NODE) {
		nb_child = roxml_get_attr_nb(parent);
	}

	if(position <= 0) { position = nb_child+1; }

	if(position == 1) {
		if((n->type & ROXML_ELM_NODE)||
			(n->type & ROXML_CMT_NODE)||
			(n->type & ROXML_PI_NODE))
		{
			node_t *first = parent->chld;
			parent->chld = n;
			n->sibl = first;
		} else if(n->type & ROXML_TXT_NODE) {
			node_t *first = parent->text;
			parent->text = n;
			n->sibl = first;
		} else if(n->type & ROXML_ATTR_NODE) {
			node_t *first = parent->attr;
			parent->attr = n;
			n->sibl = first;
		}
	} else {
		int i;
		node_t * prev = NULL;
		node_t * next = NULL;
		if((n->type & ROXML_ELM_NODE)||
			(n->type & ROXML_CMT_NODE)||
			(n->type & ROXML_PI_NODE))
		{
			prev = parent->chld;
			next = parent->chld;
		} else if(n->type & ROXML_TXT_NODE) {
			prev = parent->text;
			next = parent->text;
		} else if(n->type & ROXML_ATTR_NODE) {
			prev = parent->attr;
			next = parent->attr;
		}
		for(i = 1; (i < nb_child+1) && (i < position); i++) {
			prev = next;
			next = next->sibl;
		}
		prev->sibl = n;
		n->sibl = next;
	}
}

void ROXML_INT roxml_print_space(FILE *f, char ** buf, int * offset, int * len, int lvl)
{
	int i = 0;
	if(*buf) {
		int pos = *offset + lvl;
		if(pos >= *len) { *buf = realloc(*buf, *len+ROXML_LONG_LEN); }
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

void ROXML_INT roxml_write_string(char ** buf, FILE * f, char * str, int *offset, int * len)
{
	int pos = *offset + strlen(str);
	if((pos >= *len)&&(*buf)) { *buf = realloc(*buf, *len+ROXML_LONG_LEN); }
	if(f) { fprintf(f, "%s", str); }
	if(*buf) { strcat(*buf+(*offset), str); }
	*offset = pos;
}

void ROXML_INT roxml_write_node(node_t * n, FILE *f, char * buf, int human, int lvl, int *offset, int *len)
{
	char name[ROXML_BASE_LEN];
	roxml_get_name(n, name, ROXML_BASE_LEN);
	if(human) {
		roxml_print_space(f, &buf, offset, len, lvl);
	}
	if(roxml_get_type(n) == ROXML_ELM_NODE) {
		node_t *attr = n->attr;
		if(n->prnt) {
			roxml_write_string(&buf, f, "<", offset, len);
			roxml_write_string(&buf, f, name, offset, len);
		}
		while(attr)	{
			int status = 0;
			char *value;
			char arg[ROXML_BASE_LEN];
			char val[ROXML_BASE_LEN];
			roxml_get_name(attr, arg, ROXML_BASE_LEN);
			value = roxml_get_content(attr, val, ROXML_BASE_LEN, &status);
			if(status >= ROXML_BASE_LEN) {
				value = roxml_get_content(attr, NULL, 0, &status);
			}
			roxml_write_string(&buf, f, " ", offset, len);
			roxml_write_string(&buf, f, arg, offset, len);
			roxml_write_string(&buf, f, "=\"", offset, len);
			roxml_write_string(&buf, f, value, offset, len);
			roxml_write_string(&buf, f, "\"", offset, len);
			attr = attr->sibl;
			roxml_release(value);
		}
		if(n->chld || n->text)	{
			node_t *chld = n->chld;
			node_t *text = n->text;
			if(n->prnt) {
				roxml_write_string(&buf, f, ">", offset, len);
				if(human) {
					roxml_write_string(&buf, f, "\n", offset, len);
				}
			}
			while(chld || text)	{
				char val[ROXML_LONG_LEN];
				if(text) {
					char *value;
					int status;
					if(human) {
						roxml_print_space(f, &buf, offset, len, lvl+1);
					}
					value = roxml_get_content(text, val, ROXML_LONG_LEN, &status);
					if(status >= ROXML_LONG_LEN) {
						value = roxml_get_content(text, NULL, 0, &status);
					}
					roxml_write_string(&buf, f, value, offset, len);
					if(human) {
						roxml_write_string(&buf, f, "\n", offset, len);
					}
					text = text->sibl;
					roxml_release(value);
				}
				if(chld) {
					roxml_write_node(chld, f, buf, human, lvl+1, offset, len);
					chld = chld->sibl;
				}
			}
			if(n->prnt) {
				if(human) {
					roxml_print_space(f, &buf, offset, len, lvl);
				}
				roxml_write_string(&buf, f, "</", offset, len);
				roxml_write_string(&buf, f, name, offset, len);
				roxml_write_string(&buf, f, ">", offset, len);
				if(human) {
					roxml_write_string(&buf, f, "\n", offset, len);
				}
			}
		} else {
			roxml_write_string(&buf, f, "/>", offset, len);
			if(human) {
				roxml_write_string(&buf, f, "\n", offset, len);
			}
		}
	} else if(roxml_get_type(n) == ROXML_CMT_NODE) {
		char *value;
		int status;
		char val[ROXML_LONG_LEN];
		roxml_write_string(&buf, f, "<!--", offset, len);
		value = roxml_get_content(n, val, ROXML_LONG_LEN, &status);
		if(status >= ROXML_LONG_LEN) {
			value = roxml_get_content(n, NULL, 0, &status);
		}
		roxml_write_string(&buf, f, value, offset, len);
		roxml_release(value);
		roxml_write_string(&buf, f, "-->", offset, len);
		if(human) {
			roxml_write_string(&buf, f, "\n", offset, len);
		}
	} else if(roxml_get_type(n) == ROXML_PI_NODE) {
		char *value;
		int status;
		char val[ROXML_LONG_LEN];
		roxml_write_string(&buf, f, "<?", offset, len);
		value = roxml_get_content(n, val, ROXML_LONG_LEN, &status);
		if(status >= ROXML_LONG_LEN) {
			value = roxml_get_content(n, NULL, 0, &status);
		}
		roxml_write_string(&buf, f, value, offset, len);
		roxml_release(value);
		roxml_write_string(&buf, f, "?>", offset, len);
		if(human) {
			roxml_write_string(&buf, f, "\n", offset, len);
		}
	}
}

void ROXML_INT roxml_del_arg_node(node_t * n) 
{
	node_t *current = n->prnt->attr;
	if(current == n) {
		n->prnt->attr = n->sibl;
	} else if(current) {
		while(current->sibl && current->sibl != n) {
			current = current->sibl;
		}
		current->sibl = n->sibl;
	}
	return;
}

void ROXML_INT roxml_del_txt_node(node_t * n)
{
	node_t *current = n->prnt->text;
	if(current == n) {
		n->prnt->text = n->sibl;
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
	roxml_del_tree(n->text);
	roxml_del_tree(n->attr);
} 

node_t * roxml_parent_node(node_t *parent, node_t *n)
{
	n->prnt = parent;
	if(parent)	{
		if(roxml_get_type(n) == ROXML_ATTR_NODE)	{
			if(parent->attr) {
				parent->attr->next->sibl = n;
				parent->attr->next = n;
			} else {
				parent->attr = n;
				parent->attr->next = n;
			}
		} else if(roxml_get_type(n) == ROXML_TXT_NODE){
			if(parent->text) {
				parent->text->next->sibl = n;
				parent->text->next = n;
			} else {
				parent->text = n;
				parent->text->next = n;
			}
		} else if((roxml_get_type(n) == ROXML_ELM_NODE)||
			(roxml_get_type(n) == ROXML_CMT_NODE)||
			(roxml_get_type(n) == ROXML_PI_NODE))
		{
			if(parent->chld) {
				parent->next->sibl = n;
				parent->next->next = n;
				parent->next = n;
			} else {
				parent->chld = n;
				parent->next = n;
			}
		}
	}

	return n;
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

