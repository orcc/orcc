/*
 * Copyright(c)2009 Victor Martin, Jani Boutellier
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the EPFL and University of Oulu nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY  Victor Martin, Jani Boutellier ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Victor Martin, Jani Boutellier BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.sf.orcc.backends.c.quasistatic.scheduler.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.util.BtypeChangesListener;
import net.sf.orcc.backends.c.quasistatic.scheduler.util.Constants;

/**
 * Represents the switch of the system(also called Parameter P).
 * 
 * @author vimartin
 */
public class Switch {

	
	private static String btype;
	private static List<BtypeChangesListener> listeners;
	
	public static int getSwitchValue(String newSwitch) {
		int newValue = -1;
		if (newSwitch.equals(Constants.NEWVOP))
			newValue = Integer.parseInt("100000000000", 2);
		else if (newSwitch.equals(Constants.ZEROMV))
			newValue = Integer.parseInt("000000000000", 2);
		else if (newSwitch.equals(Constants.INTER))
			newValue = Integer.parseInt("000000001000", 2);
		else if (newSwitch.equals(Constants.INTRA))
			newValue = Integer.parseInt("010000000000", 2);
		else if (newSwitch.equals(Constants.INTRA_AND_INTER))
			newValue = Integer.parseInt("000000001010", 2);
		return newValue;
	}

	public static ArrayList<String> getSwitchValues() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(Constants.INTER);
		list.add(Constants.INTRA);
		list.add(Constants.INTRA_AND_INTER);
		list.add(Constants.ZEROMV);
		list.add(Constants.NEWVOP);
		return list;
	}
	
	public static void setBtype(String btype)throws OrccException{
		Switch.btype = btype;
		btypeHasChanged();
	}
	
	private static void btypeHasChanged()throws OrccException{
		if(listeners == null){
			return;
		}
		for(BtypeChangesListener listener: listeners){
			listener.btypeHasChanged();
		}
	}
	
	public static String getBTYPE(){
		return Switch.btype;
	}
	
	public static void registerListener(BtypeChangesListener listener){
		if(listeners == null){
			listeners = new ArrayList<BtypeChangesListener>();
		}
		listeners.add(listener);
	}
}
