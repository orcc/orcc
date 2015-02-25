/*
 * Copyright (c) 2015, INSA of Rennes
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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.backends.util;

/**
 * The class describes the targeted DSP board.
 * 
 * @author Alexandre Sanchez
 * 
 */
public class DSP {

	public enum Brand {
		TI, OTHER
	}

	public enum Family {
		C6000;

		@Override
		public String toString() {
			switch (this) {
			case C6000:
				return "C6000";
			default:
				return "Unknown serie";
			}
		}
	}
	
	public static DSP builder(String name) {
		if (name.equals("C6678")) {
			return new DSP(Brand.TI, name, Family.C6000, "TMS320C66XX", "TMS320C6678", "little");
		} else {
			return new DSP(Brand.OTHER);
		}
	}

	private Brand brand;
	private Family family;
	private String variant;
	private String endianness;
	private String device;
	private String familyVersion;
	private String deviceVersion;

	public DSP(Brand brand) {
		this.brand = brand;
	}

	public DSP(Brand brand, String device, Family serie, String familyVersion, String deviceVersion) {
		this(brand);
		this.device = device;
		this.family = serie;
		this.familyVersion = familyVersion;
		this.deviceVersion = deviceVersion;
		this.variant = familyVersion + deviceVersion;
	}

	public DSP(Brand brand, String device, Family serie, String familyVersion, String deviceVersion, String endianness) {
		this(brand, device, serie, familyVersion, deviceVersion);
		this.endianness = endianness;
	}
	
	public Brand getBrand() {
		return brand;
	}

	public String getDevice() {
		return device;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public String getFamilyVersion() {
		return familyVersion;
	}

	public String getVariant() {
		return variant;
	}

	public String getEndianness() {
		return endianness;
	}

	public Family getFamily() {
		return family;
	}
	
}
