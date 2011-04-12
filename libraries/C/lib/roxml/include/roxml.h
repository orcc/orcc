/** \file roxml.h
 *  \brief header for libroxml.so
 *         
 * This is the header file used to develop some
 * softwares using the libroxml.so library.
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

/** \mainpage libroxml homepage
 *
 * \section intro_sec Introduction
 * This library is minimum, easy-to-use, C implementation for xml file parsing
 * It includes a mini shell to navigate thru a xml file as a demo.
 *
 * \section why_sec Why libroxml?
 * Because XML parsing is always hard to reinvent, and because very often xml lib are 
 * too big to fit with very little application
 *
 * \section what_sec What can do libroxml?
 * It allow you to easily:
 * - load / unload document
 * - navigate thru an xml tree
 * - read attributes and attributes' values for nodes
 * - get content of nodes
 * - create/modify xml trees and save then to a file or buffer
 *
 * \section how_sec How does it work?
 * You can refer to \ref roxml.h for documentation on all API functions
 *
 * \section list-func List of function by category?
 * there are several groups of functions : 
 * \subsection manage-xml Manage xml source
 * \ref roxml_load_fd \n
 * \ref roxml_load_doc \n
 * \ref roxml_load_buf \n
 * \ref roxml_close \n
 * 
 * \subsection navigate-xml Navigate into xml tree
 * \ref roxml_get_parent \n
 * \ref roxml_get_chld \n
 * \ref roxml_get_chld_nb \n
 * \ref roxml_get_attr \n
 * \ref roxml_get_attr_nb \n
 * \ref roxml_get_text \n
 * \ref roxml_get_text_nb \n
 * \ref roxml_xpath \n
 *
 * \subsection acess-xml Access xml content
 * \ref roxml_get_type \n
 * \ref roxml_get_node_position \n
 * \ref roxml_get_name \n
 * \ref roxml_get_content \n
 *
 * \subsection modify-xml Modify xml tree
 * \ref roxml_add_node \n
 * \ref roxml_del_node \n
 * \ref roxml_commit_changes \n
 *
 * \subsection other-xml Libroxml specifics
 * \ref roxml_release \n
 */

#ifndef ROXML_H
#define ROXML_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifdef _WIN32
#include "roxml_win32_native.h"
#else
#include <pthread.h>
#endif

/**
 * \def ROXML_API
 * 
 * part of the public API
 */
#define ROXML_API

#ifndef ROXML_INT
/** \typedef node_t
 *
 * \brief node_t structure
 * 
 * This is the structure for a node. This struct is very
 * little as it only contains offset for node in file and
 * tree links
 */
typedef struct node node_t;
#endif

/**
 * \def ROXML_ATTR_NODE
 * 
 * constant for attribute nodes
 * \see roxml_add_node
 */
#define ROXML_ATTR_NODE	0x08

/**
 * \def ROXML_STD_NODE
 * 
 * \deprecated 
 * constant for standard nodes
 *
 * \see roxml_add_node
 * 
 */
#define ROXML_STD_NODE	0x10 

/**
 * \def ROXML_ELM_NODE
 * 
 * constant for element nodes
 * \see roxml_add_node
 */
#define ROXML_ELM_NODE	0x10

/**
 * \def ROXML_TXT_NODE
 * 
 * constant for text nodes
 * \see roxml_add_node
 */
#define ROXML_TXT_NODE	0x20

/**
 * \def ROXML_CMT_NODE
 * 
 * constant for comment nodes
 * \see roxml_add_node
 */
#define ROXML_CMT_NODE	0x40

/**
 * \def ROXML_PI_NODE
 * 
 * constant for processing_intruction nodes
 * \see roxml_add_node
 */
#define ROXML_PI_NODE	0x80

/**
 * \def RELEASE_ALL
 * 
 * when used with roxml_release, release all allocated memory by current thread
 * \see roxml_release
 */
#define RELEASE_ALL	(void*)-1

/**
 * \def RELEASE_LAST
 * 
 * when used with roxml_release, release last variable allocated
 * \see roxml_release
 *
 * example:
 * \code
 * #include <stdio.h>
 * #include <roxml.h>
 *
 * int main(void)
 * {
 *	int len;
 * 	node_t * root = roxml_load_doc("/tmp/doc.xml");
 *	
 *	// roxml_get_content allocate a buffer and store the content in it if no buffer was given
 *	printf("root content = '%s'\n", roxml_get_content(root, NULL, 0, &len));
 *
 *	// release the last allocated buffer even if no pointer is maintained by the user
 *	roxml_release(RELEASE_LAST);
 *
 *	// here no memory leak can occur.
 *
 *	roxml_close(root);
 * 	return 0;
 * }
 * \endcode
 */
#define RELEASE_LAST	(void*)-2

/** \brief load function for buffers
 *
 * \fn node_t* ROXML_API roxml_load_buf(char *buffer);
 * This function load a document by parsing all the corresponding nodes.
 * The document must be contained inside the char * buffer given in parameter
 * and remain valid until the roxml_close() function is called
 * \param buffer the XML buffer to load
 * \return the root node or NULL
 * \see roxml_close
 * \see roxml_load_fd
 * \see roxml_load_doc
 */
node_t*	ROXML_API roxml_load_buf		(char *buffer);

/** \brief load function for files
 *
 * \fn node_t* ROXML_API roxml_load_doc(char *filename);
 * This function load a file document by parsing all the corresponding nodes
 * \param filename the XML document to load
 * \return the root node or NULL
 * \see roxml_close
 * \see roxml_load_fd
 * \see roxml_load_buf
 */
node_t*	ROXML_API roxml_load_doc		(char *filename);

/** \brief load function for file descriptors
 *
 * \fn node_t* ROXML_API roxml_load_fd(int *fd);
 * This function load a document by parsing all the corresponding nodes
 * \param filename the XML document to load
 * \return the root node or NULL
 * \see roxml_close
 * \see roxml_load_doc
 * \see roxml_load_buf
 */
node_t*	ROXML_API roxml_load_fd			(int fd);

/** \brief unload function
 *
 * \fn void ROXML_API roxml_close(node_t *n);
 * This function clear a document and all the corresponding nodes
 * It release all memory allocated during roxml_load_doc or roxml_load_file.
 * All nodes from the tree are not available anymore.
 * \param n is any node of the tree to be cleaned
 * \return void
 * \see roxml_load_doc
 * \see roxml_load_buf
 */
void 	ROXML_API roxml_close			(node_t *n);

/** \brief next sibling getter function
 *
 * \fn node_t* ROXML_API roxml_get_next_sibling(node_t *n);
 * This function returns the next sibling of a given node
 * \param n is one node of the tree
 * \return the next sibling node
 */
node_t*	ROXML_API roxml_get_next_sibling	(node_t *n);

/** \brief prev sibling getter function
 *
 * \fn node_t* ROXML_API roxml_get_prev_sibling(node_t *n);
 * This function returns the prev sibling of a given node
 * \param n is one node of the tree
 * \return the prev sibling node
 */
node_t*	ROXML_API roxml_get_prev_sibling	(node_t *n);

/** \brief parent getter function
 *
 * \fn node_t* ROXML_API roxml_get_parent(node_t *n);
 * This function returns the parent of a given node
 * \param n is one node of the tree
 * \return the parent node
 */
node_t*	ROXML_API roxml_get_parent		(node_t *n);

/** \brief root getter function
 *
 * \fn node_t* ROXML_API roxml_get_root(node_t *n);
 * This function returns the root of a tree containing the given node
 * \param n is one node of the tree
 * \return the root node
 */
node_t*	ROXML_API roxml_get_root		(node_t *n);

/** \brief chld getter function
 *
 * \fn node_t* ROXML_API roxml_get_chld(node_t *n, char * name, int nth);
 * This function returns a given chld of a node etheir by name, or the nth child.
 *
 * \param n is one node of the tree
 * \param name is the name of the child to get
 * \param nth is the id of the chld to get
 * \return the chld corresponding to name or id (if both are set, name is used)
 * \see roxml_get_chld_nb
 *
 * example:
 * given the following xml file
 * \verbatim
<xml>
 <item1/>
 <item2/>
 <item3/>
</xml>
\endverbatim
 * \code
 * #include <stdio.h>
 * #include <roxml.h>
 *
 * int main(void)
 * {
 * 	node_t * root = roxml_load_doc("/tmp/doc.xml");
 *	
 *	node_t * child_by_name = roxml_get_chld(root, "item2", 0);
 *	node_t * child_by_nth = roxml_get_chld(root, NULL, 2);
 *
 *	// here child_by_name == child_by_nth
 *	if(child_by_name == child_by_nth) {
 *		printf("Nodes are equal\n");
 *	} 
 *
 *	roxml_close(root);
 * 	return 0;
 * }
 * \endcode
 */
node_t*	ROXML_API roxml_get_chld		(node_t *n, char * name, int nth);

/** \brief chlds number getter function
 *
 * \fn int ROXML_API roxml_get_chld_nb(node_t *n);
 * This function return the number of chlidren for a given node
 * \param n is one node of the tree
 * \return  the number of chlildren
 */
int 	ROXML_API roxml_get_chld_nb		(node_t *n);

/** \brief name getter function
 *
 * \fn char* ROXML_API roxml_get_name(node_t *n, char * buffer, int size);
 * This function return the name of the node or fill the current buffer with it if not NULL.
 * if name is NULL, the function will allocate a buffer that user should free using
 * roxml_release when no longer needed.
 * Be carreful as if your buffer is too short for the returned string, it will be truncated
 * \param n is one node of the tree
 * \param buffer a buffer pointer or NULL if has to be auto allocated
 * \param size the size of buffer pointed by buffer if not NULL
 * \return the name of the node (return our buffer pointer if it wasn't NULL)
 * \see roxml_release
 */
char*	ROXML_API roxml_get_name		(node_t *n, char * buffer, int size);

/** \brief content getter function
 *
 * \fn char * ROXML_API roxml_get_content(node_t *n, char * buffer, int bufsize, int * size);
 *
 * This function returns a pointer with the concatenation of text content of a node (children are NOT included as text).;
 * if the returned pointer is NULL then the node was empty.
 * returned string should be freed using roxml_release when not used anymore
 * \param n is one node of the tree
 * \param buffer is the buffer where result will be written or NULL for internal allocation
 * \param bufsize the size if using custom buffer
 * \param size the actual size of copied result. returned size should be less that buffer size since roxml_get_content
 * will add the \0. if buffer was not NULL and size == buf_size, then given buffer was too small and node content was truncated
 * \return the text content
 * \see roxml_release
 */
char *	ROXML_API roxml_get_content		(node_t *n, char * buffer, int bufsize, int * size);

/** \brief number of attribute getter function
 *
 * \fn int ROXML_API roxml_get_attr_nb(node_t *n);
 * 
 * This function returns the number of attributes for a given node
 * \param n is one node of the tree
 * \return the number of attributes in node
 */
int	ROXML_API roxml_get_attr_nb		(node_t *n);

/** \brief attribute getter function
 *
 * \fn char* ROXML_API roxml_get_attr(node_t *n, char * name, int nth);
 * This function get the nth attribute of a node.
 * User should roxml_release the returned buffer when no longer needed.
 * \param n is one node of the tree
 * \param name is the name of the child to get
 * \param nth the id of attribute to read
 * \return the attribute corresponding to name or id (if both are set, name is used)
 *
 * example:
 * given the following xml file
 * \verbatim
<xml>
 <product id="42" class="item"/>
</xml>
\endverbatim
 * \code
 * #include <stdio.h>
 * #include <roxml.h>
 *
 * int main(void)
 * {
 * 	node_t * root = roxml_load_doc("/tmp/doc.xml");
 * 	node_t * item1 = roxml_get_chld(root, NULL, 0);
 * 	node_t * item2 = roxml_get_chld(item1, NULL, 0);
 *
 * 	node_t * attr_by_name = roxml_get_attr(item2, "id", 0);
 * 	node_t * attr_by_nth = roxml_get_attr(item2, NULL, 0);

 * 	// here attr_by_name == attr_by_nth
 * 	if(attr_by_name == attr_by_nth) {
 * 		printf("Nodes are equal\n");
 * 	}
 *
 * 	roxml_close(root);
 * 	return 0;
 * }
 * \endcode
 */
node_t*	ROXML_API roxml_get_attr		(node_t *n, char * name, int nth);

/** \brief exec path function
 *
 * \fn roxml_xpath(node_t *n, char * path, int *nb_ans);
 * This function return a node set (table of nodes) corresponding to a given xpath.
 * resulting node table should be roxml_release when not used anymore
 * \param n is one node of the tree 
 * \param path the xpath to use
 * \param nb_ans the number of results
 * \return the node table or NULL 
 *
 * handled xpath are
 *\verbatim
===Relative xpath:===
 * _"n0"_

 ===Absolute xpath:===
 * _"/n0"_

 ===Special nodes:===
 * _"`*`"_
 * _"node()"_
 * _"text()"_
 * _"comment()"_
 * _"processing-instruction()"_

 ===Conditions:===
 * Attribute string value: _"/n0`[`@a=value]"_
 * Attribute int value: _"/n0`[`@a=value]"_
 * Attribute int value: _"/n0`[`@a!=value]"_
 * Attribute int value: _"/n0`[`@a<value]"_
 * Attribute int value: _"/n0`[`@a>value]"_
 * Attribute int value: _"/n0`[`@a<=value]"_
 * Attribute int value: _"/n0`[`@a>=value]"_
 * Node position: _"/n0`[`first()]"_
 * Node position: _"/n0`[`first()+2]"_
 * Node position: _"/n0`[`last()]"_
 * Node position: _"/n0`[`last()-3]"_
 * Node position: _"/n0`[`position() = 0]"_
 * Node position: _"/n0`[`position() != 0]"_
 * Node position: _"/n0`[`position() > 1]"_
 * Node position: _"/n0`[`position() < 2]"_
 * Node position: _"/n0`[`position() >= 1]"_
 * Node position: _"/n0`[`position() <= 2]"_
 * Node position: _"/n0`[`2]"_
 * Other xpath: _"/n0`[`n1/@attr]"_

 ===Shorten xpath for:===
 * Children: _"/n0/n1/n2"_
 * Attributes: _"/n0/n1/@a"_
 * Descendent or self::node(): _"/n0//n5"_
 * Parent: _"/n0/n1/../n1"_
 * Self: _"/n0/n1/."_

 ===Full xpath for:===
 * Nodes: _"/n0/n1/child::a"_
 * Attributes: _"/n0/n1/attribute::a"_
 * Descendent or self: _"/n0/descendant-or-self::n5"_
 * Parent: _"/child::n0/child::n1/parent::/n1"_
 * Self: _"/child::n0/child::n1/self::"_
 * Preceding: _"preceding::n1"_
 * Following: _"following::n1"_
 * Ancestor: _"ancestor::n2"_
 * Ancestor-or-self: _"ancestor-or-self::n2"_
\endverbatim
 */
node_t ** ROXML_API roxml_xpath			(node_t *n, char * path, int *nb_ans);

/** \brief node type function
 *
 * \fn roxml_get_type(node_t *n);
 * This function tells if a node is an \ref ROXML_ATTR_NODE, \ref ROXML_TXT_NODE, \ref ROXML_PI_NODE, \ref ROXML_CMT_NODE or \ref ROXML_ELM_NODE.
 * \param n is the node to test
 * \return the node type
 */
int ROXML_API roxml_get_type			(node_t *n);

/** \brief node get position function
 *
 * \fn roxml_get_node_position(node_t *n);
 * This function tells the index of a node between all its siblings homonyns.
 * \param n is the node to test
 * \return the postion between 1 and N
 */
int ROXML_API roxml_get_node_position		(node_t *n);

/** \brief memory cleanning function
 *
 * \fn roxml_release(void * data);
 * This function release the memory pointed by pointer
 * just like free would but for memory allocated with roxml_malloc. 
 * Freeing a NULL pointer won't do
 * anything. roxml_release also allow you to remove all 
 * previously allocated datas by using RELEASE_ALL as argument.
 * You can also safely use RELEASE_LAST argument that will release the 
 * previously allocated var within the current thread (making this
 * function thread safe).
 * if using roxml_release on a variable non roxml_mallocated, nothing will happen
 * \param data the pointer to delete or NULL or RELEASE_ALL or RELEASE_LAST
 * \return void
 */
void ROXML_API roxml_release			(void * data);

/** \brief node type getter function
 *
 * \fn roxml_get_type(node_t *n);
 * this function return the node type : ROXML_ARG ROXML_VAL
 * \param n the node to return type for
 * \return the node type
 */
int roxml_get_type				(node_t *n);

/** \brief add a node to the tree
 *
 * \fn roxml_add_node(node_t * parent, int position, int type, char *name, char *value);
 * this function add a new node to the tree. This will not update de buffer or file,
 * only the RAM loaded tree
 * \param parent the parent node
 * \param position the position as a child of parent (0 for auto position at the end of children list)
 * \param type the type of node between \ref ROXML_ATTR_NODE, \ref ROXML_ELM_NODE, \ref ROXML_TXT_NODE, \ref ROXML_PI_NODE, \ref ROXML_CMT_NODE
 * \param name the name of the node (for \ref ROXML_ATTR_NODE and \ref ROXML_ELM_NODE only)
 * \param value the text content (for \ref ROXML_ELM_NODE, \ref ROXML_TXT_NODE, \ref ROXML_CMT_NODE, \ref ROXML_PI_NODE) or the attribute value (\ref ROXML_ATTR_NODE)
 * \return the new node
 * \see roxml_commit_changes
 * \see roxml_del_node
 *
 * paramaters name and value depending on node type:
 * - \ref ROXML_ELM_NODE take at least a node name. the paramter value is optional and represents the text content.
 * - \ref ROXML_TXT_NODE ignore the node name. the paramter value represents the text content.
 * - \ref ROXML_CMT_NODE ignore the node name. the paramter value represents the comment.
 * - \ref ROXML_PI_NODE ignore the node name. the paramter value represents the content of processing-instruction node.
 * - \ref ROXML_ATTR_NODE take an attribute name. and the attribute value as given by parameter value.
 * 
 * some examples to obtain this xml result file
\verbatim
<root>
 <!-- sample XML file -->
 <item id="42">
  <price> 
   24 
  </price>
 </item>
</root>
\endverbatim
 *
 * \code
 * #include <roxml.h>
 *
 * int main(void)
 * {
 * 	node_t * root = roxml_add_node(NULL, 0, ROXML_ELM_NODE, "xml", NULL);
 * 	node_t * tmp = roxml_add_node(root, 0, ROXML_CMT_NODE, NULL, "sample XML file");
 * 	tmp = roxml_add_node(root, 0, ROXML_ELM_NODE, "item", NULL);
 * 	roxml_add_node(tmp, 0, ROXML_ATTR_NODE, "id", "42");
 * 	tmp = roxml_add_node(tmp, 0, ROXML_ELM_NODE, "price", "24");
 * 	roxml_commit_changes(root, "/tmp/test.xml", NULL, 1);
 * 	return 0;
 * }
 * \endcode
 * Or also:
 * \code
 * #include <roxml.h>
 *
 * int main(void)
 * {
 * 	node_t * root = roxml_add_node(NULL, 0, ROXML_ELM_NODE, "xml", NULL);
 * 	node_t * tmp = roxml_add_node(root, 0, ROXML_CMT_NODE, NULL, "sample XML file");
 * 	tmp = roxml_add_node(root, 0, ROXML_ELM_NODE, "item", NULL);
 * 	roxml_add_node(tmp, 0, ROXML_ATTR_NODE, "id", "42");
 * 	tmp = roxml_add_node(tmp, 0, ROXML_ELM_NODE, "price", NULL);
 * 	tmp = roxml_add_node(tmp, 0, ROXML_TXT_NODE, NULL, "24");
 * 	roxml_commit_changes(root, "/tmp/test.xml", NULL, 1);
 * 	return 0;
 * }
 * \endcode
 */
node_t * ROXML_API roxml_add_node		(node_t * parent, int position, int type, char *name, char *value);

/** \brief text content getter function
 *
 * \fn roxml_get_text(node_t *n, int nth);
 * this function return the text content of a node as a TEXT nodes
 * the content of the text node can be read using the roxml_get_content function
 * \param n the node that contains text
 * \param nth the nth text node to retrieve
 * \return the text node or NULL
 * \see roxml_get_text_nb
 * \see roxml_get_content
 *
 * example:
 * given this xml file:
 * \verbatim
<xml>
  This is
  <item/>
  an example
  <item/>
  of text nodes
</xml>
\endverbatim
 * \code
 * #include <stdio.h>
 * #include <roxml.h>
 *
 * int main(void)
 * {
 *	int len;
 *	node_t * root = roxml_load_doc("/tmp/doc.xml");
 *	node_t * item = roxml_get_chld(root, NULL, 0);
 *
 *	node_t * text = roxml_get_text(item, 0);
 *	char * text_content = roxml_get_content(text, NULL, 0, &len);
 *	// HERE text_content is equal to "This is"
 *	printf("text_content = '%s'\n", text_content);
 *
 *	text = roxml_get_text(item, 1);
 *	text_content = roxml_get_content(text, NULL, 0, &len);
 *	// HERE text_content is equal to "an example"
 *	printf("text_content = '%s'\n", text_content);
 *
 *	text = roxml_get_text(item, 2);
 *	text_content = roxml_get_content(text, NULL, 0, &len);
 *	// HERE text_content is equal to "of text nodes"
 *	printf("text_content = '%s'\n", text_content);
 *
 *	roxml_close(item);
 *	return 0;
 * }
 * \endcode
 */
node_t * ROXML_API roxml_get_text		(node_t *n, int nth);

/** \brief text node number getter function
 *
 * \fn roxml_get_text_nb(node_t *n);
 * this function return the number of text nodes in 
 * a standard node
 * \param n the node to search into
 * \return the number of text node
 * \see roxml_get_text
 */
int ROXML_API roxml_get_text_nb			(node_t *n);

/** \brief node deletion function
 *
 * \fn roxml_del_node(node_t * n);
 * this function delete a node from the tree. The node is not really deleted 
 * from the file or buffer until the roxml_commit_changes is called.
 * \param n the node to delete
 * \return 
 */
void ROXML_API roxml_del_node			(node_t * n);

/** \brief sync function
 *
 * \fn roxml_commit_changes(node_t *n, char * dest, char ** buffer, int human);
 * this function sync changes to the given buffer or file in human or one-line format 
 * The tree will be processed starting with the root node 'n' and following with all its children.
 * The tree will be dumped to a file if 'dest' is not null and contains a valid path.
 * The tree will be dumped to a buffer if 'buffer' is not null the buffer is allocated by the library
 * and a pointer to it will be stored into 'buffer'. 
 * \param n the root node of the tree to write
 * \param dest the path to a file to write tree to
 * \param buffer the adress of a buffer where tre will be written
 * \param human 0 for one-line tree, or 1 for human format (using tabs, newlines...)
 * 
 * One should do:
 * \code
 * #include <roxml.h>
 *
 * int main(void)
 * {
 * 	node_t * root = roxml_add_node(NULL, 0, ROXML_ELM_NODE, "xml", NULL);
 * 	node_t * tmp = roxml_add_node(root, 0, ROXML_CMT_NODE, NULL, "sample XML file");
 * 	tmp = roxml_add_node(root, 0, ROXML_ELM_NODE, "item", NULL);
 * 	roxml_add_node(tmp, 0, ROXML_ATTR_NODE, "id", "42");
 * 	tmp = roxml_add_node(tmp, 0, ROXML_ELM_NODE, "price", "24");
 * 	roxml_commit_changes(root, "/tmp/test.xml", NULL, 1);
 * 	return 0;
 * }
 * \endcode
 * to generate the following xml bloc:
\verbatim
<root>
 <!-- sample XML file -->
 <item id="42">
  <price> 
   24 
  </price>
 </item>
</root>
\endverbatim
 * or also
 * \code
 * #include <roxml.h>
 *
 * int main(void)
 * {
 * 	node_t * root = roxml_add_node(NULL, 0, ROXML_ELM_NODE, "xml", NULL);
 * 	node_t * tmp = roxml_add_node(root, 0, ROXML_CMT_NODE, NULL, "sample XML file");
 * 	tmp = roxml_add_node(root, 0, ROXML_ELM_NODE, "item", NULL);
 * 	roxml_add_node(tmp, 0, ROXML_ATTR_NODE, "id", "42");
 * 	tmp = roxml_add_node(tmp, 0, ROXML_ELM_NODE, "price", "24");
 * 	roxml_commit_changes(root, "/tmp/test.xml", NULL, 0);
 * 	return 0;
 * }
 * \endcode
 * to generate the following xml bloc:
\verbatim
<root><!-- sample XML file --><item id="42"><price>24</price></item></root>
\endverbatim
 */
void ROXML_API roxml_commit_changes		(node_t *n, char * dest, char ** buffer, int human);


#endif /* ROXML_H */

