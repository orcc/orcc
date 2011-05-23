/** \file roxml-types.h
 *  \brief internal header for libroxml.so
 *         
 * This is the internal header file used by roxml.c
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

#ifndef ROXML_TYPES_H
#define ROXML_TYPES_H

#ifdef _WIN32
#include "roxml_win32_native.h"
#else
#include <pthread.h>
#endif

/** \typedef roxml_parse_func 
 *
 * \brief parser callback functions
 * 
 * This is the prototype for a parser callback function. It receive as argument
 * the chunk that matched the key, and the context as a void. It should return the 
 * number of handled bytes or 0 if doesn't want to handle this key
 */
typedef int(*roxml_parse_func)(char *chunk, void * data);

/** \struct memory_cell_t
 *
 * \brief memory cell structure
 * 
 * This is the structure for a memory cell. It contains the
 * pointer info and type. It also contains the caller id so that
 * it can free without reference to a specific pointer
 */
typedef struct memory_cell {
	int type;			/*!< pointer type from PTR_NODE, PTR_CHAR... */
	int occ;			/*!< number of element */
	void *ptr;			/*!< pointer */
	pthread_t id;			/*!< thread id of allocator */
	struct memory_cell *next;	/*!< next memory cell */
	struct memory_cell *prev;	/*!< prev memory cell */
} memory_cell_t;

/** \struct xpath_cond_t
 *
 * \brief xpath cond structure
 * 
 * This is the structure for a xpath cond. It contains the
 * node condition
 */
typedef struct _xpath_cond {
	char rel;			/*!< relation with previous */
	char axes;			/*!< axes for operator */
	char op;			/*!< operator used */
	char op2;			/*!< operator used on arg2 */
	char func;			/*!< function to process */
	char func2;			/*!< function to process in arg2 */
	char *arg1;			/*!< condition arg1 as string */
	char *arg2;			/*!< condition arg2 as string */
	struct _xpath_node *xp;		/*!< xpath that have to be resolved for condition */
	struct _xpath_cond *next;	/*!< next xpath condition pointer */
} xpath_cond_t;

/** \struct xpath_node_t
 *
 * \brief xpath node structure
 * 
 * This is the structure for a xpath node. It contains the
 * node axes and conditions
 */
typedef struct _xpath_node {
	char abs;			/*!< for first node: is path absolute */
	char rel;			/*!< relation with previous */
	char axes;			/*!< axes type */
	char *name;			/*!< axes string */
	struct _xpath_cond *xp_cond;    /*!< condition for total xpath */
	struct _xpath_cond *cond;	/*!< condition list */
	struct _xpath_node *next;	/*!< next xpath pointer */
} xpath_node_t;

/** \struct xpath_tok_t
 *
 * \brief xpath token structure
 * 
 * This is the structure for a xpath token. It contains the
 * xpath id
 */
typedef struct _xpath_tok_table {
	unsigned char id;		/*!< token id == ROXML_REQTABLE_ID */
	unsigned char ids[256];		/*!< token id table */
	pthread_mutex_t mut;		/*!< token table allocation mutex */
	struct _xpath_tok *next;	/*!< next xpath token */
} xpath_tok_table_t;

/** \struct xpath_tok_t
 *
 * \brief xpath token structure
 * 
 * This is the structure for a xpath token. It contains the
 * xpath id
 */
typedef struct _xpath_tok {
	unsigned char id;		/*!< token id */
	struct _xpath_tok *next;	/*!< next xpath token */
} xpath_tok_t;

/** \struct node_t
 *
 * \brief node_t structure
 * 
 * This is the structure for a node. This struct is very
 * little as it only contains offset for node in file and
 * tree links
 */
typedef struct node {
	unsigned char type;		/*!< document or buffer / attribute or value */
	union {
		char *buf;		/*!< buffer address */
		FILE *fil;		/*!< loaded document */
		void *src;		/*!< xml src address */
	} src;				/*!< xml tree source */
	unsigned long pos;		/*!< offset of begining of opening node in file */
	unsigned long end;		/*!< offset of begining of closing node in file */
	struct node *sibl;		/*!< ref to brother */
	struct node *chld;		/*!< ref to chld */
	struct node *prnt;		/*!< ref to parent */
	struct node *attr;		/*!< ref to attributes */
	struct node *text;		/*!< ref to content */
	struct node *next;		/*!< ref to next (internal use) */
	void *priv;			/*!< ref to xpath tok (internal use) */
} node_t;

/** \struct _roxml_load_ctx
 *
 * \brief xml parsing context
 * 
 * obscure structure that contains all the xml
 * parsing variables
 */
typedef struct _roxml_load_ctx {
	int pos;				/*!< position in file */
	int empty_text_node;			/*!< if text node is empty (only '\t' '\r' '\n' ' ' */
	int state;				/*!< state (state machine main var) */
	int previous_state;			/*!< previous state */
	int mode;				/*!< mode quoted or normal */
	int inside_node_state;			/*!< sub state for attributes*/
	int content_quoted;			/*!< content of attribute was quoted */
	int type;				/*!< source type (file or buffer) */
	void * src;				/*!< source (file or buffer) */
	node_t *candidat_node;			/*!< node being processed */
	node_t *candidat_txt;			/*!< text node being processed */
	node_t *candidat_arg;			/*!< attr node being processed */
	node_t *candidat_val;			/*!< attr value being processed */
	node_t *current_node;			/*!< current node */
} roxml_load_ctx_t;

/** \struct _roxml_xpath_ctx
 *
 * \brief xpath parsing context
 * 
 * obscure structure that contains all the xapth
 * parsing variables
 */
typedef struct _roxml_xpath_ctx {
	int pos;				/*!< position in string */
	int is_first_node;			/*!< is it the first node of xpath */
	int wait_first_node;			/*!< are we waiting for the first node of a xpath */
	int shorten_cond;			/*!< is the cond a short confition */
	int nbpath;				/*!< number of xpath in this string */
	int bracket;				/*!< are we inside two brackets */
	int parenthesys;			/*!< are we inside two parenthesys */
	int quoted;				/*!< are we quoted (') */
	int dquoted;				/*!< are we double quoted (") */
	int context;				/*!< is it an inside xpath*/
	int content_quoted;			/*!< content of val was quoted */
	xpath_node_t * first_node;		/*!< the very first node of xpath string */
        xpath_node_t * new_node;		/*!< current xpath node */
	xpath_cond_t * new_cond;		/*!< current xpath cond */
} roxml_xpath_ctx_t;

/** \struct _roxml_parser_item
 *
 * \brief the parser item struct
 * 
 * this struct contains the key and callback.
 */
typedef struct _roxml_parser_item {
	int count;				/*!< number of parser item with non null key (only for head) */
	int def_count;				/*!< total number of parser item (only for head) */
	char chunk;				/*!< key to match */
	roxml_parse_func func;			/*!< callback function */
	struct _roxml_parser_item *next;		/*!< next item */
} roxml_parser_item_t;

#endif /* ROXML_TYPES_H */

