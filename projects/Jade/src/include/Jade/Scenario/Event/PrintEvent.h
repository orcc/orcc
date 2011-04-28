/*
 * Copyright (c) 2009, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

/**
@brief Description of the PrintEvent class interface
@author Jerome Gorin
@file PrintEvent.h
@version 1.0
@date 26/01/2011
*/

//------------------------------
#ifndef PRINTEVENT_H
#define PRINTEVENT_H
#include "Jade/Scenario/Event.h"
//------------------------------

/**
 * @brief  This class defines a print event.
 * 
 * @author Jerome Gorin
 * 
 */
class PrintEvent : public Event {
public:
	/*!
     * @brief Create a new Print event
     *
	 * @param id : the id of the decoder to print.
	 *
	 * @param file : the file where decoder is printed.
     */
	PrintEvent(int id, std::string file) : Event(id) {
		this->file = file;
	};

	/*!
     *  @brief Destructor
     *
	 * Delete an event.
     */
	~PrintEvent(){};

	/*!
     * @brief Return true if the Event is a PrintEvent
     *
	 * @return true if Event is a PrintEvent otherwise false
     */
	bool isPrintEvent(){return true;};

	/*!
     * @brief Return the file to print the decoder.
     *
	 * @return the output file
     */
	std::string getFile(){return file;};

private:
	/** File where decoder is printed */
	std::string file;
};

#endif