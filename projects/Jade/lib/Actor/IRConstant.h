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
@brief Interface of IRConstant
@author Jerome Gorin
@file IRConstant.h
@version 0.1
@date 2010/04/12
*/

//------------------------------
#ifndef IRCONSTANT_H
#define IRCONSTANT_H

#include <string>
//------------------------------

/**
*
* @class IRConstant
* @brief Constants IR fields.
*
* @author Jerome Gorin
*
*/
class IRConstant {
public:

	static const std::string KEY_ACTION_SCHED;

	static const std::string KEY_ACTIONS;

	static const std::string KEY_INITIALIZES;

	static const std::string KEY_INPUTS;

	static const std::string KEY_NAME;

	static const std::string KEY_OUTPUTS;

	static const std::string KEY_PARAMETERS;

	static const std::string KEY_PROCEDURES;

	static const std::string KEY_SOURCE_FILE;

	static const std::string KEY_STATE_VARS;
};

const std::string IRConstant::KEY_ACTION_SCHED = "action_scheduler";
const std::string IRConstant::KEY_ACTIONS= "actions";
const std::string IRConstant::KEY_INITIALIZES= "initializes";
const std::string IRConstant::KEY_INPUTS= "inputs";
const std::string IRConstant::KEY_NAME= "name";
const std::string IRConstant::KEY_OUTPUTS= "outputs";
const std::string IRConstant::KEY_PARAMETERS= "parameters";
const std::string IRConstant::KEY_PROCEDURES= "procedures";
const std::string IRConstant::KEY_SOURCE_FILE= "source_file";
const std::string IRConstant::KEY_STATE_VARS= "state_variables";

#endif
