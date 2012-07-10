/*
 * Copyright (c) 2011, Ecole Polytechnique Fédérale de Lausanne
 * All rights reserved.
 */
#ifndef __ETHERNET_H__
#define __ETHERNET_H__

#include "interface.h"
#include <string>

#ifdef WIN32
#include <winsock.h>         // For socket(), connect(), send(), and recv()
#else
#include <sys/types.h>       // For data types
#include <sys/socket.h>      // For socket(), connect(), send(), and recv()
#include <netdb.h>           // For gethostbyname()
#include <arpa/inet.h>       // For inet_addr()
#include <unistd.h>          // For close()
#include <netinet/in.h>      // For sockaddr_in
typedef void raw_type;       // Type used for raw data on this platform
#endif

class Ethernet : public Interface
{
public:
	Ethernet(int, int);
	~Ethernet();

	int recv(void* pBuf, const unsigned uNbVal);
	int send(void* pBuf, const unsigned uNbVal);

protected:
	int m_socket;
};

class UdpServerSocket : public Ethernet
{
public:
	UdpServerSocket(int localPort);
};

class UdpClientSocket : public Ethernet
{
public:
	UdpClientSocket(std::string& localAddr, int localPort);
};

class TcpServerSocket : public Ethernet
{
public:
	TcpServerSocket(int localPort);

};

class TcpClientSocket : public Ethernet
{
public:
	TcpClientSocket(const std::string& localAddr, int localPort);
};

#endif
