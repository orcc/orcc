/** \file roxml-parse-engine.h
 *  \brief header for libroxml.so
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


#ifndef ROXML_PARSE_ENGINE_H
#define ROXML_PARSE_ENGINE_H

/** \brief parser item creation function
 *
 * \fn roxml_append_parser_item(roxml_parser_item_t *head, char * key, roxml_parse_func func);
 * this function create a new parser item and append it to the parser list.
 * \param head the parser head list or NULL for first item
 * \param key the char to trigger callback for
 * \param func the function to call on match
 * \return the head item
 */
roxml_parser_item_t * ROXML_INT roxml_append_parser_item(roxml_parser_item_t *head, char * key, roxml_parse_func func);

/** \brief parser table deletion 
 *
 * \fn roxml_parser_free(roxml_parser_item_t *head);
 * this function delete a prepared parser object
 * \param head the parser object
 * \return
 */
void ROXML_INT roxml_parser_free(roxml_parser_item_t *head);

/** \brief parser list deletion
 *
 * \fn roxml_parser_clear(roxml_parser_item_t *head);
 * this function delete a parser list (when not yet prepared)
 * \param head the parser object
 * \return
 */
void ROXML_INT roxml_parser_clear(roxml_parser_item_t *head);

/** \brief line parsing function
 *
 * \fn roxml_parse_line(roxml_parser_item_t * head, char *line, int len, void * ctx);
 * this function parse a line : it calls parsing functions when key matches
 * \param head the parser object
 * \param line the string to parse
 * \param len the len of string or 0 if auto calculate len (using \0)
 * \param ctx user data passed to the callbacks
 * \return the number of bytes processed
 */
int ROXML_INT roxml_parse_line(roxml_parser_item_t * head, char *line, int len, void * ctx);

/** \brief parser preparation function
 *
 * \fn roxml_parser_prepare(roxml_parser_item_t *head);
 * this function compile a parser list into a table and calculate count variables for parsing optim
 * \param head the parser object
 * \return
 */
roxml_parser_item_t * ROXML_INT roxml_parser_prepare(roxml_parser_item_t *head);

// xpath parser functions
int _func_xpath_ignore(char * chunk, void * data);
int _func_xpath_new_node(char * chunk, void * data);
int _func_xpath_quote(char * chunk, void * data);
int _func_xpath_dquote(char * chunk, void * data);
int _func_xpath_open_parenthesys(char * chunk, void * data);
int _func_xpath_close_parenthesys(char * chunk, void * data);
int _func_xpath_open_brackets(char * chunk, void * data);
int _func_xpath_close_brackets(char * chunk, void * data);
int _func_xpath_condition_or(char * chunk, void * data);
int _func_xpath_condition_and(char * chunk, void * data);
int _func_xpath_path_or(char * chunk, void * data);
int _func_xpath_operator_equal(char * chunk, void * data);
int _func_xpath_operator_sup(char * chunk, void * data);
int _func_xpath_operator_inf(char * chunk, void * data);
int _func_xpath_operator_diff(char * chunk, void * data);
int _func_xpath_number(char * chunk, void * data);
int _func_xpath_position(char * chunk, void * data);
int _func_xpath_first(char * chunk, void * data);
int _func_xpath_last(char * chunk, void * data);
int _func_xpath_operator_add(char * chunk, void * data);
int _func_xpath_operator_subs(char * chunk, void * data);
int _func_xpath_default(char * chunk, void * data);
int _func_xpath_all(char * chunk, void * data);

// load parser functions
int _func_load_quoted(char * chunk, void * data);
int _func_load_dquoted(char * chunk, void * data);
int _func_load_open_spec_node(char * chunk, void * data);
int _func_load_close_cdata(char * chunk, void * data);
int _func_load_close_comment(char * chunk, void * data);
int _func_load_close_pi(char * chunk, void * data);
int _func_load_open_node(char * chunk, void * data);
int _func_load_close_node(char * chunk, void * data);
int _func_load_end_node(char * chunk, void * data);
int _func_load_white(char * chunk, void * data);
int _func_load_default(char * chunk, void * data);

#endif /* ROXML_PARSE_ENGINE_H */

