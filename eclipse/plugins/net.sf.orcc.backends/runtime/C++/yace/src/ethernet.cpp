/**
 * @file
 * @author  Ghislain Roquier <ghislain.roquier@epfl.ch>
 * @version 1.0
 *
 * @section LICENSE
 *
 * Copyright (c) 2011, EPFL
 * All rights reserved.
 * 
 * @section DESCRIPTION
 *
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include<iostream>

#ifdef _WIN32
#include <winsock2.h>
#pragma comment(lib, "ws2_32.lib")
typedef int socklen_t;
#else // _WIN32
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/time.h>
#include <fcntl.h>
#include <netdb.h>
#include <unistd.h>
#endif

#include "ethernet.h" 


Ethernet::Ethernet(int type, int protocol) 
	: m_socket(0)
{
#ifdef WIN32
	WSADATA wsa;
	if(WSAStartup(0x202, &wsa) < 0)
	{
	}
#endif
	m_socket = socket(AF_INET, type, protocol);
	if(m_socket < 0)
	{
#ifdef _WIN32
		std::cerr << "socket() failed with error " << WSAGetLastError() << std::endl;
		WSACleanup();
#endif
	}
}

Ethernet::~Ethernet()
{
}


int Ethernet::recv(void* pBuf, const unsigned uNbVal)
{
	int ret = ::recv(m_socket, (char *)pBuf, uNbVal, 0);
	return ret;
}

int  Ethernet::send(void* pBuf, const unsigned uNbVal)
{
	int ret = ::send(m_socket, (const char *)pBuf, uNbVal, 0);
	return ret;
}

UdpServerSocket::UdpServerSocket(int localPort)
	: Ethernet(SOCK_DGRAM, IPPROTO_UDP)
{
	sockaddr_in me;
	memset((char *) &me, 0, sizeof(me));
	me.sin_family = AF_INET;
	me.sin_port = htons(localPort);
	me.sin_addr.s_addr = htonl(INADDR_ANY);

	if (::bind(m_socket, (sockaddr*) &me, sizeof(me)) < 0)
	{			
	}
}


UdpClientSocket::UdpClientSocket(std::string& host, int hostPort)
	: Ethernet(SOCK_DGRAM, IPPROTO_UDP)
{
	sockaddr_in other;
	memset((char *) &other, 0, sizeof(other));
	other.sin_family = AF_INET;
	other.sin_port = htons(hostPort);
	other.sin_addr.s_addr = inet_addr(host.c_str());

	if (::connect(m_socket, (sockaddr *) &other, sizeof(other)) < 0)
	{
	}
}


TcpServerSocket::TcpServerSocket(int localPort)
	: Ethernet(SOCK_STREAM, IPPROTO_TCP)
{
	struct sockaddr_in local, from;
	int sock;
	local.sin_family = AF_INET;
	local.sin_addr.s_addr = INADDR_ANY;
	local.sin_port = htons(localPort);

	sock = socket(AF_INET, SOCK_STREAM, 0);
	if (sock == 0)
	{
	}

	if(::bind(sock, (struct sockaddr *) &local, sizeof(local)) < 0)
	{
	}

	if (::listen(sock, 5) < 0)
	{
	}

	int fromlen = sizeof(from);
	m_socket = ::accept(sock, (struct sockaddr *) &from, (socklen_t *)&fromlen);
	printf("connection accepted ...\n");
	if (m_socket == 0)
	{
	}

#ifdef _WIN32
	closesocket(sock);
#else
	close(sock);
#endif
}

TcpClientSocket::TcpClientSocket(const std::string& host, int localPort)
	: Ethernet(SOCK_STREAM, IPPROTO_TCP)
{
	struct sockaddr_in sin;
	sin.sin_family = AF_INET;
	sin.sin_port = htons(localPort);
	sin.sin_addr.s_addr = inet_addr(host.c_str());

	while (::connect(m_socket, (struct sockaddr *) &sin, sizeof(sin)) < 0)
	{
		printf("wait for acceptance ...\n");
#ifdef _WIN32
		Sleep(100);
#else
		usleep(100000);
#endif
	}
}


