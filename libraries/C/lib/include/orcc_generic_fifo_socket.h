#include <stdio.h>

#include "socket.h"

#include "orcc.h"
#include "orcc_fifo.h"
#include "orcc_util.h"

struct FIFO_SOCKET_S(T) {
	int size; /** size of the ringbuffer */
	T *contents; /** the memory containing the ringbuffer */
	int fill_count;

	SOCKET Sock;
};

DECL int FIFO_SOCKET_HAS_TOKENS(T)(struct FIFO_SOCKET_S(T) *fifo, int n) {
	if(fifo->fill_count < n)
	{
		if(recv(fifo->Sock, &(fifo->contents[fifo->fill_count]), (n - fifo->fill_count) * sizeof(T), 0) == -1)
		{
			//will be managed when socket we will have unblocked read.
			fprintf(stderr,"socket_GetData() : error.\n");
			exit(-10);
		}
		else
		{
			fifo->fill_count = n;
			return 1;
		}
	}
	return 1;
}

DECL int FIFO_SOCKET_GET_NUM_TOKENS(T)(struct FIFO_SOCKET_S(T) *fifo) {
	//Read socket
	//return fifo->fill_count;
	return 1024;
}

DECL int FIFO_SOCKET_HAS_ROOM(T)(struct FIFO_SOCKET_S(T) *fifo, int n) {
	//Will be managed when we will use unblocked write in socket.
	return (fifo->size - fifo->fill_count) >=n;
}


DECL T *FIFO_SOCKET_PEEK(T)(struct FIFO_SOCKET_S(T) *fifo, T *buffer, int n) {
	return fifo->contents;
}


DECL T *FIFO_SOCKET_READ(T)(struct FIFO_SOCKET_S(T) *fifo, T *buffer, int n) {
	return fifo->contents;
}

DECL void FIFO_SOCKET_READ_END(T)(struct FIFO_SOCKET_S(T) *fifo, int n) {
	memmove(fifo->contents, &fifo->contents[n], (fifo->fill_count - n) * sizeof(T));
	fifo->fill_count -= n;
}


DECL T *FIFO_SOCKET_WRITE(T)(struct FIFO_SOCKET_S(T) *fifo, T *buffer, int n) {
	return &fifo->contents[fifo->fill_count];
}


DECL void FIFO_SOCKET_WRITE_END(T)(struct FIFO_SOCKET_S(T) *fifo, T *buffer, int n) {
	int err;

	err = send(fifo->Sock, fifo->contents , n * sizeof(T), 0); 
	if(err == -1)
	{
		fprintf(stderr,"socket_TransmitData() : error.\n");
		exit(-10);
	}
	else
	{
		fifo->fill_count =0;
	}
}

DECL void FIFO_SOCKET_INIT(T)(struct FIFO_SOCKET_S(T) *fifo, int IsServer, const char* HostName, unsigned short port, int IsIpv6) {
	SOCKADDR_IN Sin;
	struct hostent *hostinfo;
	SOCKET local_sock;

	if(IsIpv6)
		local_sock = socket(AF_INET6, SOCK_STREAM, 0);
	else
		local_sock = socket(AF_INET, SOCK_STREAM, 0);

	if(local_sock == -1)
	{
		fprintf(stderr,"socket(%s, SOCK_DGRAM, 0) failed.\n",(IsIpv6 ? "AF_INET6" : "AF_INET"));
		exit(-3);
	}

	Sin.sin_port = htons(port);
	if(IsIpv6)
		Sin.sin_family = AF_INET6;
	else
		Sin.sin_family = AF_INET;

	if(!IsServer)
	{
		hostinfo = gethostbyname(HostName);
		if (hostinfo == NULL)
		{
			fprintf (stderr, "Unknown host %s.\n", HostName);
			exit(-4);
		}

		Sin.sin_addr = *(IN_ADDR *) hostinfo->h_addr;

	
		while(connect(local_sock,(SOCKADDR *) &Sin, sizeof(SOCKADDR)) == -1)
		{
			sleep(1);
		}
		fifo->Sock = local_sock;
	}
	else
	{
		socklen_t sock_len;
		SOCKADDR cust_sin;

		Sin.sin_addr.s_addr = htonl(INADDR_ANY);
		if(bind(local_sock, (SOCKADDR*)&Sin, sizeof(SOCKADDR_IN)) == -1)
		{
			fprintf(stderr,"Socket error\n");
			exit(-9);
		}
		if(listen(local_sock,1) == -1)
		{
			fprintf(stderr,"Socket error\n");
			exit(-9);
		}
		printf("Waiting for a client ...\n");
		fifo->Sock = accept(local_sock, &cust_sin, &sock_len);
		printf("Client connected\n");
		closesocket(local_sock);
	}
}
