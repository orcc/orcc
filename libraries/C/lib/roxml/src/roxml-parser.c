/** \file roxml-parser.c
 *  \brief command line xml parser
 *         
 * \author blunderer <blunderer@blunderer.org>
 * \date 19 Feb 2009
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

#include "roxml.h"
#include <sys/types.h>
#include <sys/stat.h>

void print_help(void)
{
	fprintf (stderr, "LGPL command line XML parser\n<blunderer@blunderer.org>\n") ;
}

void print_usage(const char *progname)
{
	fprintf (stderr, "\nusage: %s [-q|-h] <filename> [/]<node1>/<node2>/<node3>/.../<nodeN>\n", progname);
	fprintf (stderr, "-q|--quiet activate quiet mode\n-h|--help display this message\n");
}

int main(int argc, char ** argv)
{
	int optind;
	int quiet = 0 ;
	int j ,max;
	node_t *root;
	node_t *cur;
	node_t **ans;

	for(optind = 1; optind < argc; optind++) {
		int option = 0;
		if(argv[optind][0] == '-') {
			/* this is an option */
			if(argv[optind][1] == '-') {
				/* long option */
				if(strcmp(argv[optind], "--help") == 0) {
					option = 'h';
				} else if(strcmp(argv[optind], "--quiet") == 0) {
					option = 'q';
				}
			} else {
				/* short option */
				if(strcmp(argv[optind], "-h") == 0) {
					option = 'h';
				} else if(strcmp(argv[optind], "-q") == 0) {
					option = 'q';
				}
			}
		} else {
			break;
		}
		switch (option) {
			case 'q' :
				quiet = 1 ;
				break ;
			case 'h':
				print_help();
				print_usage(argv[0]);
				return EXIT_FAILURE;
				break;
			default:
				print_usage(argv[0]);
				return EXIT_FAILURE;
				break;
		}
	}

	if(argc < optind + 2) {
		fprintf(stderr,"wrong syntax\n");
		print_usage(argv[0]);
		return -1;
	}

	root = roxml_load_doc(argv[optind]);
	cur = root;
	if(root == NULL)	{
		fprintf(stdout,"no such file '%s'\n", argv[optind]);
	}

	ans = roxml_xpath(cur, argv[optind + 1],  &max);

	for(j = 0; j < max; j++)
	{
		char *c = NULL;
		c = roxml_get_content(ans[j], NULL, 0, NULL);
		if(c) {
			if(*c == 0) {
				int i = 0;
				int nb_chld = roxml_get_chld_nb(ans[j]);
				for(i = 0; i < nb_chld; i++)	{
					c = roxml_get_name(roxml_get_chld(ans[j], NULL, i), NULL, 0);
					if (! quiet)	{
						fprintf(stdout,"ans[%d]: ", j);
					}
					fprintf(stdout,"%s\n", c);
				}
			} else {
				if (! quiet)	{
					fprintf(stdout,"ans[%d]: ", j);
				}
				fprintf(stdout,"%s\n", c);
			}
		}
	}
	roxml_release(RELEASE_ALL);

	roxml_close(root);
	return 0;
}

