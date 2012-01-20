struct FIFO_SOCKET_S(T) {
	const unsigned int size; /** size of the ringbuffer */
	T *contents; /** the memory containing the ringbuffer */

	unsigned int write_ind;
	unsigned int readers_nb; /** the number of fifo's readers */
	unsigned int* read_inds; /** the current position of the reader */

	fd_set fdset; 
	SOCKET Sock;
};

DECL int FIFO_SOCKET_GET_NUM_TOKENS(T)(struct FIFO_SOCKET_S(T) *fifo, unsigned int reader_id) {
	if(fifo->write_ind < fifo->size) {
		int ret;
		int nbBytesRead;
		struct timeval timeout = { 1, 0 };

		ret = select(fifo->Sock + 1, &(fifo->fdset), NULL, NULL, &timeout);
		if(ret < 0)
		{
			fprintf(stderr,"socket_GetData() : error.\n");
			exit(-10);
		}
		if(ret > 0) {
			nbBytesRead = recv(fifo->Sock, (char *) &(fifo->contents[fifo->write_ind]), (fifo->size - fifo->write_ind) * sizeof(T), 0);
			if(nbBytesRead == -1) {
				//will be managed when socket we will have unblocked read.
				fprintf(stderr,"socket_GetData() : error.\n");
				exit(-10);
			}
			fifo->write_ind += nbBytesRead / sizeof(T);
		}
	}
	return fifo->write_ind;
}

/*DECL void FIFO_SOCKET_READ_COPY(T)(struct FIFO_SOCKET_S(T) *fifo, T *buffer, unsigned int reader_id, unsigned int n) {
	memcpy(buffer, fifo->contents, n * sizeof(T));
}*/

DECL int FIFO_SOCKET_HAS_ROOM(T)(struct FIFO_SOCKET_S(T) *fifo, unsigned int n) {

	if(fifo->write_ind >= fifo->size/2) {
		int ret;
		int nbBytesWritten;
		struct timeval timeout = { 1, 0 };

		ret = select(fifo->Sock + 1, NULL,  &(fifo->fdset), NULL, &timeout);
		if(ret < 0)
		{
			fprintf(stderr,"socket_WriteData() : error.\n");
			exit(-10);
		}
		if(ret > 0) {
			nbBytesWritten = send(fifo->Sock, (char *) fifo->contents, (fifo->size/2) * sizeof(T), 0);
			if(nbBytesWritten == -1) {
				//will be managed when socket we will have unblocked read.
				fprintf(stderr,"socket_WriteData() : error.\n");
				exit(-10);
			}
			memmove(fifo->contents, &(fifo->contents[nbBytesWritten / sizeof(T)]), nbBytesWritten);
			fifo->write_ind -= nbBytesWritten / sizeof(T);
		}
	}
	return (fifo->size - fifo->write_ind) >=n;
}

DECL int FIFO_SOCKET_GET_ROOM(T)(struct FIFO_SOCKET_S(T) *fifo) {

	if(fifo->write_ind >= fifo->size/2) {
		int ret;
		int nbBytesWritten;
		struct timeval timeout = { 1, 0 };

		ret = select(fifo->Sock + 1, NULL,  &(fifo->fdset), NULL, &timeout);
		if(ret < 0)
		{
			fprintf(stderr,"socket_WriteData() : error.\n");
			exit(-10);
		}
		if(ret > 0) {
			nbBytesWritten = send(fifo->Sock, (char *) fifo->contents, (fifo->size/2) * sizeof(T), 0);
			if(nbBytesWritten == -1) {
				//will be managed when socket we will have unblocked read.
				fprintf(stderr,"socket_WriteData() : error.\n");
				exit(-10);
			}
			memmove(fifo->contents, &(fifo->contents[nbBytesWritten / sizeof(T)]), nbBytesWritten);
			fifo->write_ind -= nbBytesWritten / sizeof(T);
		}
	}
	return fifo->size - fifo->write_ind;
}
/*
DECL T *FIFO_SOCKET_PEEK(T)(struct FIFO_SOCKET_S(T) *fifo, T *buffer,unsigned int reader_id, unsigned int n) {
	return fifo->contents;
}


DECL T *FIFO_SOCKET_READ(T)(struct FIFO_SOCKET_S(T) *fifo, T *buffer, unsigned int reader_id, unsigned int n) {
	return fifo->contents;
}*/

DECL void FIFO_SOCKET_READ_END(T)(struct FIFO_SOCKET_S(T) *fifo, unsigned int reader_id, unsigned int n) {
	fifo->read_inds[0] += n;
	memmove(fifo->contents, &fifo->contents[fifo->read_inds[0]], (fifo->write_ind - fifo->read_inds[0]) * sizeof(T));
	fifo->write_ind -= fifo->read_inds[0];
	fifo->read_inds[0] = 0;
}


DECL T *FIFO_SOCKET_WRITE(T)(struct FIFO_SOCKET_S(T) *fifo, T *buffer, unsigned int n) {
	return &(fifo->contents[fifo->write_ind]);
}


DECL void FIFO_SOCKET_WRITE_END(T)(struct FIFO_SOCKET_S(T) *fifo, T *buffer, unsigned int n) {
	fifo->write_ind += n;
	if(fifo->write_ind >= fifo->size/2) {
		int ret;
		int nbBytesWritten;
		struct timeval timeout = { 1, 0 };

		ret = select(fifo->Sock + 1, NULL,  &(fifo->fdset), NULL, &timeout);
		if(ret < 0)
		{
			fprintf(stderr,"socket_WriteData() : error.\n");
			exit(-10);
		}
		if(ret > 0) {
			nbBytesWritten = send(fifo->Sock, (char *) fifo->contents, (fifo->size/2) * sizeof(T), 0);
			if(nbBytesWritten == -1) {
				fprintf(stderr,"socket_WriteData() : error.\n");
				exit(-10);
			}
			memmove(fifo->contents, &(fifo->contents[nbBytesWritten / sizeof(T)]), nbBytesWritten);
			fifo->write_ind -= nbBytesWritten / sizeof(T);
		}
	}
}

DECL void FIFO_SOCKET_INIT(T)(struct FIFO_SOCKET_S(T) *fifo, int NumId, int IsServer, const char* HostName, unsigned short port, int IsIpv6) {
	SOCKADDR_IN Sin;
	struct hostent *hostinfo;
	SOCKET local_sock;
	int i;
	for(i = 0;i<fifo->size ; i++) {
		fifo->contents[i] = 62;
	}
	FD_ZERO(&(fifo->fdset));
	if (IsIpv6) {
		local_sock = socket(AF_INET6, SOCK_STREAM, 0);
	} else {
		local_sock = socket(AF_INET, SOCK_STREAM, 0);
	}

	if (local_sock == -1) {
		fprintf(stderr,"socket(%s, SOCK_DGRAM, 0) failed.\n",(IsIpv6 ? "AF_INET6" : "AF_INET"));
		exit(-3);
	}

	Sin.sin_port = htons(port + NumId);
	if (IsIpv6) {
		Sin.sin_family = AF_INET6;
	} else {
		Sin.sin_family = AF_INET;
	}

	if (!IsServer) {
		hostinfo = gethostbyname(HostName);
		if (hostinfo == NULL) {
			fprintf (stderr, "Unknown host %s.\n", HostName);
			exit(-4);
		}

		Sin.sin_addr = *(IN_ADDR *) hostinfo->h_addr;

	
		while(connect(local_sock,(SOCKADDR *) &Sin, sizeof(SOCKADDR)) == -1) {
			sleep(1);
		}

		fifo->Sock = local_sock;
	} else {
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
	FD_SET(fifo->Sock, &(fifo->fdset));
}
