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
@brief Description of the Package class interface
@author Jerome Gorin
@file Package.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef PACKAGE_H
#define PACKAGE_H

namespace llvm{
	class Archive;
}

#include "Jade/Core/Actor.h"

//------------------------------

/**
 * @class Package
 *
 * This class defines a package of actors.
 *
 * @author Jerome Gorin
 * 
 */
class Package{
public:

	/*!
     *  @brief Constructor
     *
	 * Creates a new package with no parent.
	 *
	 * @param name : name of the Package
     */
	Package(std::string name){
		this->name = name;
		this->parent = NULL;
		this->archive = NULL;
	};

	/*!
     *  @brief Constructor
     *
	 * Creates a new package from a parent.
	 *
	 * @param name : name of the Package
	 *
	 * @param parent : parent of the current Package
     */
	Package(std::string name, Package* parent);

	~Package(){};

	/*!
     *  @brief Insert an underneath actor.
     *
     *  Insert an underneath actor of this package
	 *   
	 *  @param actor : actor contains by the package
     */
	void insertUnderneath(Actor* actor){
		actors.insert(std::pair<std::string, Actor*>(actor->getName(), actor)); 
	};

	/*!
     *  @brief Insert an underneath package.
     *
     *  Insert an underneath package of this package
	 *   
	 *  @param package : Package contains by the package
     */
	void insertUnderneath(Package* package){
		childs.insert(std::pair<std::string, Package*>(package->getName(), package));
	};

	/*!
     *  @brief Package is represented as an archive.
     *
     *  Insert an archive of this package
	 *   
	 *  @param actor : archive contains by the package
     */
	void setArchive(llvm::Archive* archive){
		this->archive = archive; 
	};

	/*!
     *  @brief Get all underneath actors.
     *
     *  Get all actors contained in this package and its child package.
	 *   
	 *  @param underneathActors : a list of Actors where to contains underneath actors
     */
	void getAllUnderneathActors(std::map<std::string, Actor*>* underneathActors);

	/**
     *  @brief Get actors contained in this package
	 *
	 *  Get actors contained in this package
	 *
	 *  @return a list of Actors contains in this package
	 *
     */
	std::map<std::string, Actor*>* getUnderneathActors(){return &actors;}

	/**
     *  @brief Get archive contained in this package
	 *
	 *  Get archive contained in this package
	 *
	 *  @return the archive contains in this package
	 *
     */
	llvm::Archive* getArchive(){return archive;}

	/**
     *  @brief Getter of package name
	 *
	 *  Return the string name of the package
	 *
	 *  @return name of the package
	 *
     */
	std::string getName(){return name;};

	/**
     *  @brief Getter of package directory
	 *
	 *  Return the string directory of the package
	 *
	 *  @return directory of the package
	 *
     */
	std::string getDirectory(){return directory;};

	/**
     *  @brief Getter of parent package
	 *
	 *  Return the parent of this package
	 *
	 *  @return the package containing this package
	 *
     */
	Package* getParent(){return parent;};
	
	/**
     *  @brief Getter of child package
	 *
	 *  Return the child of this package
	 *
	 *  @return a list of packages contains in this package
	 *
     */
	std::map<std::string, Package*>* getChilds(){return &childs;};

	/**
     *  @brief Return true if this package is represented as an archive.
	 *
	 *  @return true if the package is an archive, otherwise false.
	 *
     */
	bool isArchive(){ return archive;};

private:
	/** Name of the package */
	std::string name;

	/** Indicate the directory of the package*/
	std::string directory;

	/** Indicate the archive of the package*/
	llvm::Archive* archive;

	/** List of actors contains in this package */
	std::map<std::string, Actor*> actors;

	/** Child packages */
	std::map<std::string, Package*> childs;

	/** Parent package */
	Package* parent;
};

#endif