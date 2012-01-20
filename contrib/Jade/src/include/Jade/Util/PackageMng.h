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
@brief Description of the PackageMng class interface
@author Jerome Gorin
@file PackageMng.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef PACKAGEMNG_H
#define PACKAGEMNG_H

#include "Jade/Core/Actor.h"
#include "Jade/Core/Package.h"
//------------------------------

/**
 * @class PackageMng
 *
 * @brief This class contains methods for managing package.
 *
 * @author Jerome Gorin
 * 
 */
class PackageMng {
public:
	/**
     * @brief Get folder of an actor
	 *
	 * @param actor : the actor to get the folder from
	 *
	 * @return the corresponding forlder
	 *
     */
	static std::string getFolder(Actor* actor);

	/**
     * @brief Get folder from a package
	 *
	 * @param package : the name of the package
	 *
	 * @return the corresponding folder
	 *
     */
	static std::string getFolder(std::string package);

	/**
     * @brief Get first folser from a folder
	 *
	 * @param folder : the folder to get the first
	 *
	 * @return the corresponding first folder
	 *
     */
	static std::string getFirstFolder(std::string folder);

	/**
     * @brief Get all packages name from the given actor
	 *
	 * @param actor : the Actor to get the package from
	 *
	 * @return the corresponding packages name
	 *
     */
	static std::string getPackagesName(Actor* actor);

	/**
     * @brief Get all packages name from the name of an actor
	 *
	 * @param name : string of the actor
	 *
	 * @return the corresponding packages name
	 *
     */
	static std::string getPackagesName(std::string name);

	/**
     * @brief Get a list of package string from the given actor
	 *
	 * @param actor : the actor to get the package from
	 *
	 * @return the corresponding list of package
	 *
     */
	static std::list<std::string> getPackageListName(Actor* actor);

	/**
     * @brief Get a list of package string from the name of an actor
	 *
	 * @param name : name of the actor
	 *
	 * @return the corresponding list of package
	 *
     */
	static std::list<std::string> getPackageListName(std::string name);


	/**
     * @brief Get first package from the given actor
	 *
	 * @param actor : the actor to get the first package from
	 *
	 * @return the corresponding packages
	 *
     */
	static std::string getFirstPackageName(Actor* actor);

	/**
     * @brief Get first package from the name of an actor
	 *
	 * @param name : name of the actor
	 *
	 * @return the corresponding first package
	 *
     */
	static std::string getFirstPackageName(std::string name);

	/**
     * @brief Get the simple name of the given actor
	 *
	 * @param actor : the actor to get the simple name from
	 *
	 * @return the corresponding simple name
	 *
     */
	static std::string getSimpleName(Actor* actor);

	/**
     * @brief Get the simple name from the name of an actor
	 *
	 * @param name : name of the actor
	 *
	 * @return the corresponding simple name
	 *
     */
	static std::string getSimpleName(std::string name);

	/**
     * @brief Get package from the given package name
	 *
	 *	If the package does not existe, create a map 
	 *  of package representing the dependance of name
	 *
	 * @param name : the required package name
	 *
	 * @return the corresponding requiered package
	 *
     */
	static Package* getPackage(std::string name);

	/**
     * @brief Set a map of packages
	 *
	 *	Create a map of package representing the dependance of a list of actors
	 *
	 * @param actors : a map of actor
	 *
	 * @return the corresponding requiered package
	 *
     */
	static std::map<std::string, Package*>* setPackages(std::map<std::string, Actor*>* actors);

	/**
     * @brief Set archive of package
	 *
	 *	Set archive for all parents of given package
	 *
	 * @param package : the package containing the archive
	 *
     */
	static void setArchive(Package* package);

	/**
     * @brief Set actor in the required package
	 *
	 * @param actor : the actor to set in package
	 *
     */
	static void setActor(Actor* actor);

private:
	/** Package preloaded */
	static std::map<std::string, Package*>* packages;

};

#endif