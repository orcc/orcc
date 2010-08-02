package net.sf.orcc.backends.cpp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.TemplateGroupLoader;
import net.sf.orcc.network.Network;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class CppHeaderPrinter {

	private STGroup group;

	public CppHeaderPrinter() throws OrccException {
		group = TemplateGroupLoader.loadGroup("Cpp_Header");
	}

	public void print(String path, Network network) throws IOException {
		ST template = group.getInstanceOf("Cpp_Header");
		template.add("network", network);

		String fileName = path + File.separator + "portaddresses.h";

		byte[] b = template.render(80).getBytes();
		OutputStream os = new FileOutputStream(fileName);
		os.write(b);
		os.close();
	}

}
