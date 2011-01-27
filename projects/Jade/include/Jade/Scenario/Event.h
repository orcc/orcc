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
@brief Description of the Event class interface
@author Jerome Gorin
@file Event.h
@version 1.0
@date 26/01/2011
*/

//------------------------------
#ifndef EVENT_H
#define EVENT_H
//------------------------------

/**
 * @brief  This class defines an abstract event.
 * 
 * @author Jerome Gorin
 * 
 */
class Event {
public:
	/*!
     *  @brief Constructor
     *
	 * Creates a new event .
     */
	Event(int id){
		this->id = id;
	};

	/*!
     *  @brief Destructor
     *
	 * Delete an event.
     */
	~Event(){};

	/*!
     * @brief Return true if the Event is a LoadEvent
     *
	 * @return true if Event is a LoadEvent otherwise false
     */
	virtual bool isLoadEvent(){return false;};

	/*!
     * @brief Return true if the Event is a StartEvent
     *
	 * @return true if Event is a StartEvent otherwise false
     */
	virtual bool isStartEvent(){return false;};

	/*!
     * @brief Return true if the Event is a StopEvent
     *
	 * @return true if Event is a StopEvent otherwise false
     */
	virtual bool isStopEvent(){return false;};

	/*!
     * @brief Return true if the Event is a SetEvent
     *
	 * @return true if Event is a SetEvent otherwise false
     */
	virtual bool isSetEvent(){return false;};

	/*!
     * @brief Return true if the Event is a WaitEvent
     *
	 * @return true if Event is a WaitEvent otherwise false
     */
	virtual bool isWaitEvent(){return false;};

	/*!
     * @brief Return true if the Event is a PauseEvent
     *
	 * @return true if Event is a PauseEvent otherwise false
     */
	virtual bool isPauseEvent(){return false;};

	/*!
     * @brief Return the id of the decoder
	 *
	 *  Return the id of the decoder this event is pointing on
     *
	 * @return integer id of the decoder
     */
	int getId(){return id;};

protected:
	/** id of the decoder */
	int id;
};

#endif