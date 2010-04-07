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
package net.sf.orcc.backends.c.quasistatic.scheduler.dse;

/**
 * 
 * @author vimartin
 */
public class DSESchedulerUtils {

	public static int getNoCluster(String shortActorName) {
		if (shortActorName.equals("ADDR"))
			return 0;
		if (shortActorName.equals("FBUF"))
			return 0;
		if (shortActorName.equals("INTER"))
			return 2;
		if (shortActorName.equals("ADD"))
			return 3;
		if (shortActorName.equals("INV"))
			return 4;
		if (shortActorName.equals("IDCT"))
			return 5;
		if (shortActorName.equals("IDCT_ROW"))
			return 5;
		if (shortActorName.equals("IDCT_COLUMN"))
			return 5;

		if (shortActorName.equals("TRANS"))
			return 5;
		if (shortActorName.equals("RETRANS"))
			return 5;
		if (shortActorName.equals("CLIP"))
			return 5;
		if (shortActorName.equals("IAP_8x8"))
			return 1;
		if (shortActorName.equals("IAP_16x16"))
			return 1;

		if (shortActorName.equals("DCRADDR_8x8"))
			return 1;
		if (shortActorName.equals("DCRADDR_16x16"))
			return 1;
		if (shortActorName.equals("DCRINV_8x8"))
			return 1;
		if (shortActorName.equals("DCRINV_16x16"))
			return 1;
		if (shortActorName.equals("IS"))
			return 1;
		if (shortActorName.equals("SPLIT"))
			return 1;

		return 0;
	}

}
