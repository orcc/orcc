#ifndef _SOCKET_H
#define _SOCKET_H

	#ifdef WIN32
		msdql
	#else
		#include <sys/types.h>
		#include <sys/socket.h>
		#include <netinet/in.h>
		#include <arpa/inet.h>
		#include <unistd.h>
		#include <netdb.h>

		typedef int SOCKET;
		typedef struct sockaddr_in SOCKADDR_IN;
		typedef struct in_addr IN_ADDR;
		typedef struct sockaddr SOCKADDR;
		#define h_addr  h_addr_list[0]
		#define closesocket(s) close(s)
	#endif
#endif
