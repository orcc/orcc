/** \file roxml-internal.h
 *  \brief internal header for libroxml.so
 *         
 * This is the header file used by roxml.c
 * \author blunderer <blunderer@blunderer.org>
 * \date 23 Dec 2008
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

#ifndef ROXML_INT_H
#define ROXML_INT_H

/**
 * \def ROXML_INT
 *
 * internal function: not part of the API
 */
#define ROXML_INT

#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>

#include "roxml-defines.h"
#include "roxml-types.h"
#include "roxml.h"

/** \brief internal function
 *
 * \fn void ROXML_INT roxml_reset_ns(node_t *n, node_t *ns);
 * This function remove the namespace of a node if it is the one
 * specified as argument, and replace it with its parent one
 * \param n is one node of the tree
 * \param ns is one nsdef of the tree
 * \return void
 */
void	ROXML_INT roxml_reset_ns		(node_t *n, node_t *ns);

/** \brief internal function
 *
 * \fn void ROXML_INT roxml_free_node(node_t *n);
 * This function delete a node without handling its tree
 * \param n is one node of the tree
 * \return void
 */
void	ROXML_INT roxml_free_node		(node_t *n);

/** \brief internal function
 *
 * \fn node_t* ROXML_INT roxml_create_node(int pos, void * src, int type);
 * This function allocate a new node 
 * \param pos is the beginning offset of the node in the file
 * \param src is the pointer to the buffer or file
 * \param type is the type of node between arg and val
 * \return the new node
 */
node_t* ROXML_INT roxml_create_node		(int pos, void * src, int type);

/** \brief internal function
 *
 * \fn void ROXML_INT roxml_del_tree(node_t *n);
 * This function delete a tree recursively
 * \param n is one node of the tree
 * \return void
 * see roxml_close
 */
void 	ROXML_INT roxml_del_tree		(node_t *n);

/** \brief internal function
 *
 * \fn void ROXML_INT roxml_close_node(node_t *n, node_t *close);
 * This function close the node (add the end offset) and parent the node
 * \param n is the node to close
 * \param close is the node that close node n
 * \return void
 */
void 	ROXML_INT roxml_close_node		(node_t *n, node_t *close);

/** \brief generic load function
 *
 * \fn node_t* ROXML_API roxml_load(node_t *current_node, FILE *file, char *buffer);
 * This function load a document and all the corresponding nodes
 * file and buffer params are exclusive. You usualy want to load
 * either a file OR a buffer
 * \param current_node the XML root
 * \param file file descriptor of document
 * \param buffer address of buffer that contains xml
 * \return the root node or NULL
 * see roxml_close
 */
node_t*	ROXML_INT roxml_load			(node_t *current_node, FILE *file, char *buffer);

/** \brief node relocate function
 *
 * \fn roxml_parent_node(node_t *parent, node_t *n, int position);
 * this function change the position of a node in its parent list
 * \param parent the parent node
 * \param n the node to parent
 * \param position the position, 0 means or > nb children means at the end
 * \return 
 */
node_t * ROXML_INT roxml_parent_node		(node_t *parent, node_t * n, int position);

/** \brief alloc memory function
 *
 * \fn roxml_malloc(int size, int num, int type)
 * this function allocate some memory that will be reachable at
 * any time by libroxml memory manager
 * \param size the size of memory to allocate for each elem
 * \param num the number of element
 * \param type the kind of pointer
 */
void * ROXML_INT roxml_malloc			(int size, int num, int type);

/** \brief read xml doc function
 *
 * \fn roxml_read(int pos, int size, char * buffer, node_t * node)
 * this function read inside a xml doc (file or buffer) and fill the given buffer
 * \param pos the pos in the xml document
 * \param size the size of the data to read
 * \param buffer the destination buffer
 * \param node the node that belong to the tree we want to read to
 * \return the number of bytes read
 */
int ROXML_INT roxml_read(int pos, int size, char * buffer, node_t * node);


/** \brief axes setter function
 *
 * \fn roxml_set_axes(xpath_node_t *node, char *axes, int *offset);
 * this function set the axe to a xpath node from xpath string
 * \param node the xpath node to fill
 * \param axes the string where axe is extracted from 
 * \param offset the detected offset in axe string
 * \return the filled xpath_node
 */
xpath_node_t * ROXML_INT roxml_set_axes		(xpath_node_t *node, char *axes, int *offset); 

/** \brief xpath parsing function
 *
 * \fn roxml_parse_xpath(char *path, xpath_node_t ** xpath, int context); 
 * this function convert an xpath string to a table of list of xpath_node_t
 * \param path the xpath string
 * \param xpath the parsed xpath
 * \param context 0 for a real xpath, 1 for a xpath in predicat
 * \return the number of xpath list in the table
 */
int ROXML_INT roxml_parse_xpath			(char *path, xpath_node_t ** xpath, int context); 

/** \brief xpath condition free function
 *
 * \fn roxml_free_xcond(xpath_cond_t *xcond); 
 * this function frees an xpath_cond_t cell
 * \param xcond the xcond to free
 * \return 
 */
void ROXML_INT roxml_free_xcond			(xpath_cond_t *xcond); 

/** \brief xpath free function
 *
 * \fn roxml_free_xpath(xpath_node_t *xpath, int nb); 
 * this function frees the parsed xpath structure
 * \param xpath the xpath to free
 * \param nb the number of xpath structures in the table
 * \return 
 */
void ROXML_INT roxml_free_xpath			(xpath_node_t *xpath, int nb); 

/** \brief  double comparison function
 *
 * \fn roxml_double_cmp(double a, double b, int op);
 * this function  compare two doubles using one defined operator
 * \param a first operand
 * \param b second operand
 * \param op the operator to use
 * \return 1 if comparison is ok, esle 0
 */
int ROXML_INT roxml_double_cmp			(double a, double b, int op); 

/** \brief double operation function
 *
 * \fn roxml_double_oper(double a, double b, int op);
 * this function  compare two doubles using one defined operator
 * \param a first operand
 * \param b second operand
 * \param op the operator to use
 * \return 1 if comparison is ok, esle 0
 */
double ROXML_INT roxml_double_oper(double a, double b, int op);

/** \brief  string comparison function
 *
 * \fn roxml_string_cmp(char *sa, char *sb, int op);
 * this function compare two strings using one defined operator
 * \param sa first operand
 * \param sb second operand
 * \param op the operator to use
 * \return 1 if comparison is ok, else 0
 */
int ROXML_INT roxml_string_cmp			(char *sa, char *sb, int op); 

/** \brief predicat validation function
 *
 * \fn roxml_validate_predicat(xpath_node_t *xn, node_t *candidat);
 * this function check for predicat validity. predicat is part between brackets
 * \param xn the xpath node containing the predicat to test
 * \param candidat the node to test
 * \return 1 if predicat is validated, else 0
 */
int ROXML_INT roxml_validate_predicat		(xpath_node_t *xn, node_t *candidat); 

/** \brief id reservation function
 *
 * \fn roxml_request_id(node_t *root);
 * this function request an available id for a new xpath search on the tree
 * \param root the root of the tree where id table is stored
 * \return the id between 1 - 255 or -1 if failed
 */
int ROXML_INT roxml_request_id			(node_t *root); 

/** \brief release id function
 *
 * \fn roxml_release_id(node_t *root, node_t **pool, int pool_len, int req_id); 
 * this function release a previously required id and remove all id token from the pool
 * \param root the root of the tree that was used for id request
 * \param pool the result from xpath search using this id
 * \param pool_len the number of node in pool
 * \param req_id the id to release
 * \return 
 */
void ROXML_INT roxml_release_id			(node_t *root, node_t **pool, int pool_len, int req_id); 

/** \brief add a token top node function
 *
 * \fn roxml_add_to_pool(node_t *root, node_t *n, int req_id); 
 * this function add a token to target node. This token is used to garanty
 * unicity in xpath results
 * \param root the root node
 * \param n the node to mark
 * \param req_id the id to use
 * \return 0 if already in the pool, else 1
 */
int ROXML_INT roxml_add_to_pool			(node_t *root, node_t *n, int req_id); 

/** \brief axe validation function
 *
 * \fn roxml_validate_axes(node_t *root, node_t *candidat, node_t ***ans, int *nb, int *max, xpath_node_t *xn, int req_id); 
 * this function validate if an axe is matching the current node
 * \param root the root node
 * \param candidat the node to test
 * \param ans the pointer to answers pool
 * \param nb the number of answers in pool
 * \param max the current size of the pool
 * \param xn the xpath node to test
 * \param req_id the pool id
 * \return 1 if axe is validated, else 0
 */
int ROXML_INT roxml_validate_axes		(node_t *root, node_t *candidat, node_t ***ans, int *nb, int *max, xpath_node_t *xn, int req_id); 

/** \brief real xpath validation function
 *
 * \fn roxml_check_node(xpath_node_t *xp, node_t *root, node_t *context, node_t ***ans, int *nb, int *max, int ignore, int req_id); 
 * this function perform the xpath test on a tree
 * \param xp the xpath nodes to test
 * \param root the root node
 * \param context the current context node
 * \param ans the pointer to answers pool
 * \param nb the number of answers in pool
 * \param max the current size of the pool
 * \param ignore a flag for some axes that are going thru all document
 * \param req_id the pool id
 * \return 
 */
void ROXML_INT roxml_check_node			(xpath_node_t *xp, node_t *root, node_t *context, node_t ***ans, int *nb, int *max, int ignore, int req_id); 

/** \brief space printing function
 *
 * \fn roxml_print_space(FILE *f, char ** buf, int * offset, int * len, int lvl); 
 * this function add some space to output when committing change in human format
 * \param f the file pointer if any
 * \param buf the pointer to string if any
 * \param offset the current offset in stream
 * \param len the total len of buffer if any
 * \param lvl the level in the tree
 * \return 
 */
void ROXML_INT roxml_print_space		(FILE *f, char ** buf, int * offset, int * len, int lvl); 

/** \brief string writter function
 *
 * \fn roxml_write_string(char ** buf, FILE * f, char * str, int *offset, int * len); 
 * this function write a string to output when committing change
 * \param f the file pointer if any
 * \param buf the pointer to string if any
 * \param str the string to write
 * \param offset the current offset in stream
 * \param len the total len of buffer if any
 * \return 
 */
void ROXML_INT roxml_write_string		(char ** buf, FILE * f, char * str, int *offset, int * len); 

/** \brief tree write function
 *
 * \fn roxml_write_node(node_t * n, FILE *f, char ** buf, int human, int lvl, int *offset, int *len); 
 * this function write each node of the tree to output
 * \param n the node to write
 * \param f the file pointer if any
 * \param buf the pointer to the buffer string if any
 * \param human 1 to use the human format else 0
 * \param lvl the current level in tree
 * \param offset the current offset in stream
 * \param len the total len of buffer if any
 * \return 
 */
void ROXML_INT roxml_write_node			(node_t * n, FILE *f, char ** buf, int human, int lvl, int *offset, int *len); 

/** \brief attribute node deletion function
 *
 * \fn roxml_del_arg_node(node_t * n); 
 * this function delete an attribute node
 * \param n the node to delete
 * \return 
 */
void ROXML_INT roxml_del_arg_node		(node_t * n); 

/** \brief text node deletion function
 *
 * \fn roxml_del_txt_node(node_t * n); 
 * this function delete a text node
 * \param n the node to delete
 * \return 
 */
void ROXML_INT roxml_del_txt_node		(node_t * n); 

/** \brief node deletion function
 *
 * \fn roxml_del_std_node(node_t * n); 
 * this function delete a standard node
 * \param n the node to delete
 * \return 
 */
void ROXML_INT roxml_del_std_node		(node_t * n); 

/** \brief node type setter function
 *
 * \fn roxml_set_type(node_t * n, int type); 
 * this function change the type of a node
 * \param n the node to modify
 * \param type the new type to set
 * \return 
 */
void ROXML_INT roxml_set_type			(node_t * n, int type);

/** \brief node absolute position get
 *
 * \fn roxml_get_node_internal_position(node_t *n);
 * this function returns the absolute position of the node between siblings
 * \param n the node
 * \return the absolute position starting at 1
 */
int ROXML_INT roxml_get_node_internal_position(node_t *n);

/** \brief node set and function
 *
 * \fn roxml_compute_and(node_t * root, node_t **node_set, int *count, int cur_req_id, int prev_req_id);
 * this function computes the AND of two node pools. The resulting pool will have the same ID as cur_req_id.
 * \param root the root of the tree
 * \param node_set the node set containing the 2 pools
 * \param count number of node in the node set
 * \param cur_req_id the id of the first group
 * \param prev_req_id the id of the second group
 */
void ROXML_INT roxml_compute_and(node_t * root, node_t **node_set, int *count, int cur_req_id, int prev_req_id); 

/** \brief node set or function
 *
 * \fn roxml_compute_or(node_t * root, node_t **node_set, int *count, int req_id, int glob_id);
 * this function computes the OR of two node pools. The resulting pool will have the same ID as glob_id.
 * \param root the root of the tree
 * \param node_set the node set containing the 2 pools
 * \param count number of node in the node set
 * \param req_id the id of the first group
 * \param glob_id the id of the second group
 * \return 
 */
void ROXML_INT roxml_compute_or(node_t * root, node_t **node_set, int *count, int req_id, int glob_id); 

/** \brief pool node delete function 
 *
 * \fn roxml_del_from_pool(node_t * root, node_t *n, int req_id);
 * this function remove one node from a pool
 * \param root the root of the tree
 * \param n the node to remove
 * \param req_id the pool id
 * \return 
 */
void ROXML_INT roxml_del_from_pool(node_t * root, node_t *n, int req_id);

/** \brief node pool presence tester function
 *
 * \fn roxml_in_pool(node_t * root, node_t *n, int req_id);
 * this function test is a node is in a pool
 * \param root the root of the tree
 * \param n the node to test
 * \param req_id the pool id
 * \return 
 */
int ROXML_INT roxml_in_pool(node_t * root, node_t *n, int req_id);

/** \brief real xpath execution
 *
 * \fn roxml_exec_xpath(node_t *root, node_t *n, xpath_node_t *xpath, int index, int * count);
 * this function exec a decoded xpath strcuture
 * \param root the root of the tree
 * \param n the context node
 * \param xpath the xpath structure
 * \param index the number of xpath condition in string
 * \param count the pointer to a variable that is filled with the resulting node number
 * \return  the resulting node set that have to be freed with roxml_release
 */
node_t ** ROXML_INT roxml_exec_xpath(node_t *root, node_t *n, xpath_node_t *xpath, int index, int * count);

/** \brief separator tester
 *
 * \fn roxml_is_separator(char sep);
 * This function tells if a char is a string separator
 * \param sep char to test
 * \return 1 if the char was a separator else 0
 */
int ROXML_INT roxml_is_separator(char sep);

/** \brief number tester
 *
 * \fn int roxml_is_number(char *input);
 * This function tells if a string is a number
 * \param input string to test
 * \return 1 if the string was a number else 0
 */
int roxml_is_number(char *input);

/** \brief node creation during parsing
 *
 * \fn roxml_process_begin_node(roxml_load_ctx_t *context, int position);
 * this function create a new node upon finding new opening sign. It closes previous node if necessary
 * \param context the parsing context
 * \param position the position in the file
 * \return
 */
void ROXML_INT roxml_process_begin_node(roxml_load_ctx_t *context, int position);

/** \brief namespace without alias name creation during parsing
 *
 * \fn roxml_process_unaliased_ns(roxml_load_ctx_t *context);
 * this function create a new namespace without alias (default ns or remove ns)
 * \param context the parsing context
 * \return
 */
void ROXML_INT roxml_process_unaliased_ns(roxml_load_ctx_t *context);


/** \brief name space lookup in list
 *
 * \fn roxml_lookup_nsdef(node_t * nsdef, char * ns);
 * this function look for requested name space in nsdef list
 * \param nsdef the nsdef list
 * \param ns the namespace to find
 * \return the nsdef node or NULL
 */
node_t * ROXML_INT roxml_lookup_nsdef(node_t * nsdef, char * ns);


#ifdef __DEBUG
extern unsigned int _nb_node;
extern unsigned int _nb_attr;
extern unsigned int _nb_text;
#endif

extern memory_cell_t head_cell;

#endif /* ROXML_INT_H */

