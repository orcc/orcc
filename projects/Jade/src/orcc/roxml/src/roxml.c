/** \file roxml.c
 *  \brief source for libroxml.so
 *         
 * This is the source file for lib libroxml.so
 * \author blunderer <blunderer@blunderer.org>
 * \date 23 Dec 2008
 *
 * Copyright (C) 2009 blunderer
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

void ROXML_API roxml_release(void * data)
{
	memory_cell_t *ptr = &head_cell;
	memory_cell_t *to_delete = NULL;

	if(data == RELEASE_LAST)	{
		while((ptr->prev != NULL)&&(ptr->prev->id != pthread_self())) { ptr = ptr->prev; } 
		if(ptr->prev == NULL)	{ return; }

		to_delete = ptr->prev;

		if(to_delete->next) { 
			to_delete->prev->next = to_delete->next;
			to_delete->next->prev = to_delete->prev;
		} else {
			if(to_delete->prev != &head_cell)	{
				head_cell.prev = to_delete->prev;
			} else {
				head_cell.prev = NULL;
			}
			to_delete->prev->next = NULL;
		}

		if(PTR_IS_STAR(to_delete))	{
			int i = 0;
			for(i = 0; i < to_delete->occ; i++) { free(((void**)(to_delete->ptr))[i]); }
		}
		if(to_delete->type != PTR_NONE)	{
			free(to_delete->ptr);
			to_delete->type = PTR_NONE;
			free(to_delete);
		}
	} else if(data == RELEASE_ALL) {
		head_cell.prev = NULL;
		while((head_cell.next != NULL)) { 
			to_delete = head_cell.next;
			if(to_delete->next) { to_delete->next->prev = &head_cell; }
			head_cell.next = to_delete->next;

			if(PTR_IS_STAR(to_delete))	{
				int i = 0;
				for(i = 0; i < to_delete->occ; i++) { free(((void**)(to_delete->ptr))[i]); }
			}
			free(to_delete->ptr);
			to_delete->ptr = NULL;
			to_delete->type = PTR_NONE;
			free(to_delete);
		}
	} else	{
		while((ptr->next != NULL)&&(ptr->next->ptr != data)) { ptr = ptr->next; }
		if(ptr->next == NULL)	{ 
			return;
		}

		to_delete = ptr->next;
		if(to_delete->next) {
			to_delete->next->prev = ptr; 
		} else {
			if(ptr == &head_cell)	{
				head_cell.prev = NULL;
			} else {
				head_cell.prev = to_delete->prev;
			}
		}
		ptr->next = to_delete->next;
		if(PTR_IS_STAR(to_delete))	{
			int i = 0;
			for(i = 0; i < to_delete->occ; i++) { free(((void**)(to_delete->ptr))[i]); }
		}
		free(to_delete->ptr);
		to_delete->type = PTR_NONE;
		free(to_delete);
	}
	if(head_cell.next == &head_cell) { head_cell.next = NULL; }
	if(head_cell.prev == &head_cell) { head_cell.prev = NULL; }
}

char * ROXML_API roxml_get_content(node_t *n, char * buffer, int bufsize, int *size)
{
	node_t * ptr;
	int total = 0;
	char * content = buffer;
	
	if(n == NULL)	{
		if(size) {
			*size = 0;
		}
		if(buffer)	{
			strcpy(buffer, "");
			return buffer;
		}
		return NULL;
	}

	if(n->type & ROXML_ELM_NODE)	{
		ptr = n->text;
		while(ptr)	{
			total += ptr->end - ptr->pos;
			ptr = ptr->sibl;
		}
		
		if(content == NULL) {
			content = roxml_malloc(sizeof(char), total+1, PTR_CHAR);
			bufsize = total+1;
		}
		if(content == NULL) { return NULL; }

		total = 0;
		ptr = n->text;
		while(ptr)	{
			int ret_len = 0;
			int read_size = ptr->end - ptr->pos;

			if(total+read_size > bufsize-1) {
				read_size = bufsize - total - 1;
			}
			ret_len += roxml_read(ptr->pos, read_size, content+total, ptr);

			total += ret_len;
			ptr = ptr->sibl;
		}
	} else if(n->type & ROXML_TXT_NODE)	{
		int ret_len = 0;
		int read_size = 0;
		total = n->end - n->pos;
		if(content == NULL) {
			content = roxml_malloc(sizeof(char), total+1, PTR_CHAR);
			bufsize = total+1;
		}
		if(content == NULL) { return NULL; }
		read_size = total;
		if(read_size > bufsize-1) {
			read_size = bufsize-1;
		}
		total = roxml_read(n->pos, read_size, content, n);
	} else if(n->type & ROXML_CMT_NODE)	{
		int ret_len = 0;
		int read_size = 0;
		total = n->end - n->pos - 4;
		if(content == NULL) {
			content = roxml_malloc(sizeof(char), total+1, PTR_CHAR);
			bufsize = total+1;
		}
		if(content == NULL) { return NULL; }
		read_size = total;
		if(read_size > bufsize-1) {
			read_size = bufsize-1;
		}
		total = roxml_read(n->pos+4, read_size, content, n);
	} else if(n->type & ROXML_PI_NODE)	{
		int ret_len = 0;
		int read_size = 0;
		total = n->end - n->pos - 2;
		if(content == NULL) {
			content = roxml_malloc(sizeof(char), total+2, PTR_CHAR);
			bufsize = total+2;
		}
		if(content == NULL) { return NULL; }
		read_size = total;
		if(read_size > bufsize-1) {
			read_size = bufsize-1;
		}
		total = roxml_read(n->pos+2, read_size, content, n);
	} else if(n->type & ROXML_ATTR_NODE)	{
		int ret_len = 0;
		int read_size = 0;
		node_t *ptr = n->text;
		total = ptr->end - ptr->pos;
		if(content == NULL) {
			content = roxml_malloc(sizeof(char), total+1, PTR_CHAR);
			bufsize = total+1;
		}
		if(content == NULL) { return NULL; }
		read_size = total;
		if(read_size > bufsize-1) {
			read_size = bufsize-1;
		}
		total = roxml_read(ptr->pos, read_size, content, ptr);
	}

	content[total] = '\0';
	if(size) {
		*size = total+1;
	}
	return content;
}

char * ROXML_API roxml_get_name(node_t *n, char * buffer, int size)
{
	int count = 0;
	char tmp_name[INTERNAL_BUF_SIZE];

	memset(tmp_name, 0, INTERNAL_BUF_SIZE*sizeof(char));

	if(buffer) {
		memset(buffer, 0, size*sizeof(char));
	}

	if(n == NULL)	{
		if(buffer)	{
			strcpy(buffer, "");
		}
		return buffer;
	}

	if(n->prnt == NULL)	{
		strcpy(tmp_name, "documentRoot");
	} else if(n->type & ROXML_ATTR_NODE)	{
		int offset = 0;
		int ret_len = 0;
		char *internal_ptr;
		char internal_buf[INTERNAL_BUF_SIZE];

		ret_len += roxml_read(n->pos, INTERNAL_BUF_SIZE, internal_buf, n);
		internal_ptr = internal_buf;

		while(ROXML_WHITE(internal_ptr[offset])) { offset++; }
		while(count<INTERNAL_BUF_SIZE) { 
			if(internal_ptr[offset] == '=') { break; }
			if(internal_ptr[offset] == '/') { break; }
			if(internal_ptr[offset] == '>') { break; }
			tmp_name[count] = internal_ptr[offset];
			offset++;
			count++;
		}
	} else if(n->type & ROXML_ELM_NODE)	{
		int offset = 0;
		int ret_len = 0;
		char *internal_ptr;
		char internal_buf[INTERNAL_BUF_SIZE];

		ret_len += roxml_read(n->pos, INTERNAL_BUF_SIZE, internal_buf, n);
		internal_ptr = internal_buf;

		while((internal_ptr[offset] == '<')||(ROXML_WHITE(internal_ptr[offset]))) { offset++; }
		while(count<INTERNAL_BUF_SIZE) { 
			if(internal_ptr[offset] == '/') { break; }
			if(internal_ptr[offset] == '>') { break; }
			if(ROXML_WHITE(internal_ptr[offset])) { break; }
			tmp_name[count] = internal_ptr[offset];
			offset++;
			count++;
		}
	} else if(n->type & ROXML_TXT_NODE) {
		if(buffer) { strcpy(buffer, ""); return buffer; }
		return NULL;
	} else if(n->type & ROXML_CMT_NODE) {
		if(buffer) { strcpy(buffer, ""); return buffer; }
		return NULL;
	} else if(n->type & ROXML_PI_NODE) {
		if(buffer) { strcpy(buffer, ""); return buffer; }
		return NULL;
	}

	if(buffer == NULL)	{
		buffer = (char*)roxml_malloc(sizeof(char), strlen(tmp_name)+1, PTR_CHAR);
		strcpy(buffer, tmp_name);
	} else	{
		if(strlen(tmp_name) < (unsigned int)size) {
			size = strlen(tmp_name);
		}
		strncpy(buffer, tmp_name, size);
	}
	return buffer;
}

int ROXML_API roxml_get_text_nb(node_t *n)
{
	node_t *ptr = n;
	int nb = -1;
	if(ptr) {
		nb = 0;
		if(ptr->text)	{
			ptr = ptr->text;
			nb++;
			while(ptr->sibl)	{
				nb++;
				ptr = ptr->sibl;
			}
		}
	}
	return nb;
}

node_t * ROXML_API roxml_get_text(node_t *n, int nth)
{
	int count = 0;
	node_t *ptr = n;
	
	if(n == NULL)	{
		return NULL;
	}

	ptr = n->text;
	
	if(ptr == NULL)	{
		return NULL;
	}

	if(nth == 0)	{
		return ptr;
	}
	while((ptr->sibl)&&(nth > count)) {
		count++;
		ptr = ptr->sibl;
	}
	if(nth > count)	{ return NULL; }
	return ptr;
}

int ROXML_API roxml_get_attr_nb(node_t *n)
{
	node_t *ptr = n;
	int nb = -1;
	if(ptr) {
		nb = 0;
		if(ptr->attr)	{
			ptr = ptr->attr;
			nb++;
			while(ptr->sibl)	{
				nb++;
				ptr = ptr->sibl;
			}
		}
	}
	return nb;
}

node_t * ROXML_API roxml_get_attr(node_t *n, char * name, int nth)
{
	node_t *ptr = n;

	if(ptr == NULL)	{
		return NULL;
	}

	ptr = n->attr;
	
	if(ptr == NULL)	{
		return NULL;
	}

	if(name == NULL)	{
		int count = 0;
		if(nth == 0)	{
			return ptr;
		}
		while((ptr->sibl)&&(nth > count)) {
			count++;
			ptr = ptr->sibl;
		}
		if(nth > count)	{ return NULL; }
	} else	{
		while(ptr) {
			int ans = strcmp(roxml_get_name(ptr, NULL, 0), name);
			roxml_release(RELEASE_LAST);
			if(ans == 0)	{
				return ptr;
			}
			ptr = ptr->sibl;
		}
	}
	return ptr;
}

void ROXML_API roxml_close(node_t *n)
{
	node_t *root = n;
	if(root == NULL)	{
		return;
	}
	while(root->prnt != NULL)	{
		root = root->prnt;
	}

	roxml_del_tree(root->chld);
	roxml_del_tree(root->text);
	roxml_del_tree(root->sibl);
	roxml_del_tree(root->attr);
	if((root->type & ROXML_FILE) == ROXML_FILE)	{
		fclose(root->src.fil);
	}
	roxml_free_node(root);
}

int ROXML_API roxml_get_chld_nb(node_t *n)
{
	node_t *ptr = n;
	int nb = -1;
	if(n)	{ 
		nb = 0;
		if(ptr->chld)	{
			ptr = ptr->chld;
			nb++;
			while(ptr->sibl)	{
				nb++;
				ptr = ptr->sibl;
			}
		}
	}
	return nb;
}

node_t * ROXML_API roxml_get_chld(node_t *n, char * name, int nth)
{
	node_t *ptr = n;
	
	if(ptr == NULL) {
		return NULL;
	}
	
	ptr = n->chld;
	if(name == NULL)	{
		int count = 0;
		if(nth == 0)	{
			return ptr;
		}
		while((ptr->sibl)&&(nth > count)) {
			count++;
			ptr = ptr->sibl;
		}
		if(nth > count)	{ return NULL; }
	} else	{
		while(ptr) {
			int ans = strcmp(roxml_get_name(ptr, NULL, 0), name);
			roxml_release(RELEASE_LAST);
			if(ans == 0)	{
				return ptr;
			}
			ptr = ptr->sibl;
		}
	}
	return ptr;
}

node_t * ROXML_API roxml_get_prev_sibling(node_t *n)
{
	node_t * prev = NULL;
	if(n)	{
		prev = n->prnt;
		if(n->prnt) {
			prev = prev->chld;
			while(prev && prev->sibl != n) prev = prev->sibl;
		}
	}
	return prev;
}

node_t * ROXML_API roxml_get_next_sibling(node_t *n)
{
	if(n)	{
		return n->sibl;
	}
	return NULL;
}

node_t * ROXML_API roxml_get_parent(node_t *n)
{
	if(n) {
		if(n->prnt == NULL)	{
			return n;
		} else	{
			return n->prnt;
		}
	}
	return NULL;
}

node_t * ROXML_API roxml_get_root(node_t *n)
{
	node_t * root = NULL;
	if(n)	{
		root = n;
		while(root->prnt) root = root->prnt;
		if(root->chld) {
			int lone_elm = 0;
			char root_name[16];
			node_t * lone_elm_node = 0;
			if(strcmp(roxml_get_name(root->chld, root_name, 16), "?xml") == 0) {
				if(root->chld->chld) {
					node_t * ptr = root->chld->chld;
					while(ptr) {
						if(ptr->type & ROXML_ELM_NODE) {
							lone_elm_node = ptr;
							lone_elm++;
						}
						ptr = ptr->sibl;
					}
				}
			}
			if(lone_elm == 1) {
				root = lone_elm_node;
			}
		}
	}
	return root;
}

int ROXML_API roxml_get_type(node_t *n)
{
	if(n) {
		return (n->type & (ROXML_ATTR_NODE | ROXML_ELM_NODE | ROXML_TXT_NODE | ROXML_PI_NODE | ROXML_CMT_NODE));
	}
	return 0;
}

int ROXML_API roxml_get_node_position(node_t *n)
{
	int idx = 1;
	char name[256];
	node_t * prnt;
	node_t * first;

	if(n == NULL) { return 0; }
	
	roxml_get_name(n, name, 256);

	prnt = n->prnt;
	if(!prnt) {
		return 1;
	}
	first = prnt->chld;

	while((first)&&(first != n)) {
		char twin[256];
		roxml_get_name(first, twin, 256);
		if(strcmp(name, twin) == 0) { idx++; }
		first = first->sibl;
	}

	return idx;
}

node_t * ROXML_API roxml_load_fd(int fd)
{
	FILE * file = NULL;
	node_t *current_node = NULL;
	if(fd < 0)	{
		return NULL;
	}
	file = fdopen(fd, "r");
	if(file == NULL)	{
		return NULL;
	}
	current_node = roxml_create_node(0, file, ROXML_ELM_NODE | ROXML_FILE);
	current_node = roxml_parent_node(NULL, current_node);
	return roxml_load(current_node,  file, NULL);
}

node_t * ROXML_API roxml_load_doc(char *filename)
{
	node_t *current_node = NULL;
	FILE* file = fopen(filename, "r");
	if(file == NULL)	{
		return NULL;
	}
	current_node = roxml_create_node(0, file, ROXML_ELM_NODE | ROXML_FILE);
	current_node = roxml_parent_node(NULL, current_node);
	return roxml_load(current_node,  file, NULL);
}

node_t * ROXML_API roxml_load_buf(char *buffer)
{
	node_t *current_node = NULL;
	if(buffer == NULL)	{ return NULL; }
	current_node = roxml_create_node(0, buffer, ROXML_ELM_NODE | ROXML_BUFF);
	current_node = roxml_parent_node(NULL, current_node);
	return roxml_load(current_node, NULL, buffer);
}

node_t ** ROXML_API roxml_xpath(node_t *n, char * path, int *nb_ans)
{
	int index = 0;
	int count = 0;
	xpath_node_t * xpath = NULL;
	node_t ** node_set = NULL;
	node_t * root = n;
	char * full_path_to_find;
	char * path_to_find;

	if(n == NULL)	{ 
		if(nb_ans) { *nb_ans = 0; }
		return NULL; 
	}

	root = roxml_get_root(n);

	full_path_to_find = strdup(path);
	path_to_find = full_path_to_find;

	index = roxml_parse_xpath(path_to_find, &xpath, 0);

	if(index >= 0) {
		node_set = roxml_exec_xpath(root, n, xpath, index, &count);

		roxml_free_xpath(xpath, index);
		free(full_path_to_find);

		if(count == 0)	{
			roxml_release(node_set);
			node_set = NULL;
		}
	}
	if(nb_ans)	{
		*nb_ans = count;
	}

	return node_set;
}

void ROXML_API roxml_del_node(node_t * n) 
{
	if(n == NULL) return;
	if(n->type & ROXML_ELM_NODE) {
		roxml_del_std_node(n);
	} else if(n->type & ROXML_ATTR_NODE) {
		roxml_del_arg_node(n);
	} else if(n->type & ROXML_PI_NODE) {
		roxml_del_std_node(n);
	} else if(n->type & ROXML_CMT_NODE) {
		roxml_del_std_node(n);
	} else if(n->type & ROXML_TXT_NODE) {
		roxml_del_txt_node(n);
	}
	roxml_free_node(n);
}

void ROXML_API roxml_commit_changes(node_t *n, char * dest, char ** buffer, int human)
{
	int size = 0;
	int len = ROXML_LONG_LEN;
	FILE *fout = NULL;
	char * buf = NULL;

	if(n) {
		if(dest) { 
			fout = fopen(dest, "w");
		}
		if(buffer) {
			buf = (char*)malloc(ROXML_LONG_LEN);
			*buffer = buf;
		}
		roxml_write_node(n, fout, buf, human, -1, &size, &len);
		if(fout) {
			fclose(fout);
		}
	}
}

node_t * roxml_add_node(node_t * parent, int position, int type, char *name, char *value) 
{
	int name_l = 0;
	int end_node = 0;
	int content_l = 0;
	int content_pos = 0;
	int end_content = 0;
	char * buffer = NULL;
	node_t * new_node;

	if(parent && !(parent->type & ROXML_ELM_NODE)) {
		return NULL;
	}
	if(value) {
		content_l = strlen(value);
	}
	if(name) {
		name_l = strlen(name);
	}

	if(type & ROXML_ATTR_NODE) {
		if(!name || !value) { return NULL; }
		buffer = (char*)malloc(sizeof(char)*(name_l+content_l+2));
		sprintf(buffer,"%s=%s",name, value);
		content_pos = name_l+1;
		end_node = name_l + 1;
		end_content = name_l + content_l + 2;
	} else if(type & ROXML_CMT_NODE) {
		if(!value) { return NULL; }
		buffer = (char*)malloc(sizeof(char)*(content_l+8));
		sprintf(buffer,"<!--%s-->", value);
		content_pos = 0;
		end_node = content_l + 4;
		end_content = content_l + 4;
	} else if(type & ROXML_PI_NODE) {
		if(!value) { return NULL; }
		buffer = (char*)malloc(sizeof(char)*(content_l+7));
		sprintf(buffer,"<? %s ?>", value);
		content_pos = 0;
		end_node = content_l + 4;
		end_content = content_l + 4;
	} else if(type & ROXML_TXT_NODE) {
		if(!value) { return NULL; }
		buffer = (char*)malloc(sizeof(char)*(content_l+1));
		sprintf(buffer,"%s", value);
		content_pos = 0;
		end_node = content_l + 1;
		end_content = content_l + 1;
	} else if(type & ROXML_ELM_NODE) {
		if(!name) { return NULL; }
		if(content_l)	{
			if(!value) { return NULL; }
			buffer = (char*)malloc(sizeof(char)*(name_l*2+content_l+6));
			sprintf(buffer,"<%s>%s</%s>",name, value, name);
			content_pos = name_l+2;
			end_node = name_l + content_l + 2;
			end_content = end_node;
		} else {
			buffer = (char*)malloc(sizeof(char)*(name_l+5));
			sprintf(buffer,"<%s />",name);
		}
	}

	new_node = roxml_create_node(0, buffer, type | ROXML_PENDING | ROXML_BUFF);
	new_node->end = end_node;

	if(content_l && name_l) {
		node_t *new_txt = roxml_create_node(content_pos, buffer, ROXML_TXT_NODE | ROXML_PENDING | ROXML_BUFF);
		roxml_parent_node(new_node, new_txt);
		new_txt->end = end_content;
	}

	if(parent == NULL) {
		parent = roxml_create_node(0, NULL, ROXML_ELM_NODE | ROXML_PENDING | ROXML_BUFF);
		parent->end = 1;
		roxml_parent_node(parent, new_node);
	} else {
		roxml_parent_node_at(parent, new_node, position);
	}
	return new_node;
}

