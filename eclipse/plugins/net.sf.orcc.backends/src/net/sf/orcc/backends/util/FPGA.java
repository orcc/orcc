/*
 * Copyright (c) 2012, IRISA
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
 * The class describes the targeted FPGA board.
 * 
 * @author Herve Yviquel
 * 
 */
public class FPGA {

	public enum Brand {
		ALTERA, OTHER, XILINX
	}

	public enum Family {
		STRATIX3, VIRTEX5, VIRTEX6, VIRTEX7, ZEDBOARD, ZC706;

		@Override
		public String toString() {
			switch (this) {
			case STRATIX3:
				return "StratixIII";
			case VIRTEX5:
				return "virtex5";
			case VIRTEX6:
				return "virtex7";
			case VIRTEX7:
				return "virtex6";
			case ZC706:
				return "zynq";
			case ZEDBOARD:
				return "zynq";
			default:
				return "Unknown serie";
			}
		}
	}

	public static FPGA builder(String name) {
		if (name.equals("Stratix III (EP3SL150F1152C2)")) {
			return new FPGA(Brand.ALTERA, Family.STRATIX3, "EP3SL150F1152C2");
		} else if (name.equals("Virtex6 (xc6vlx240t)")) {
			return new FPGA(Brand.XILINX, Family.VIRTEX6, "xc6vlx240t",
					"ff1156", "6.3");
		} else if (name.equals("Virtex7 (xc7v2000t)")) {
			return new FPGA(Brand.XILINX, Family.VIRTEX7, "xc7v2000t",
					"flg1925", "-1");
		} else if (name.equals("ZedBoard (xc7z020)")) {
			return new FPGA(Brand.XILINX, Family.ZEDBOARD, "xc7z020",
					"clg484", "7.3");
		} else if (name.equals("Zc706 (xc7z045)")) {
			return new FPGA(Brand.XILINX, Family.ZC706, "xc7z045",
					"ffg900", "7.3");
		} else {
			return new FPGA(Brand.OTHER);
		}
	}

	private Brand brand;
	private String device;
	private String pack;
	private String version;
	private Family family;

	public FPGA(Brand brand) {
		this.brand = brand;
	}

	public FPGA(Brand brand, Family serie, String model) {
		this(brand);
		this.family = serie;
		this.device = model;
	}

	public FPGA(Brand brand, Family serie, String model, String version) {
		this(brand);
		this.family = serie;
		this.device = model;
		this.version = version;
	}
	

	public FPGA(Brand brand, Family serie, String model, String pack, String version) {
		this(brand, serie, model, version);
		this.pack = pack;
	}

	public String getDevice() {
		return device;
	}

	public String getPackage() {
		return pack;
	}

	public String getVersion() {
		return version;
	}

	public Family getFamily() {
		return family;
	}

	public boolean isAltera() {
		switch (brand) {
		case ALTERA:
			return true;
		default:
			return false;
		}
	}

	public boolean isXilinx() {
		switch (brand) {
		case XILINX:
			return true;
		default:
			return false;
		}
	}

	public boolean isZedBoard() {
		switch (family) {
		case ZEDBOARD:
			return true;
		default:
			return false;
		}
	}
	
}
