package net.sf.orcc.backends.cpp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.c.CMakePrinter;
import net.sf.orcc.network.Network;
import net.sf.orcc.util.OrccUtil;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class CppConfigPrinter {

	private STGroup group;

	public CppConfigPrinter() throws OrccException {
		group = OrccUtil.loadGroup("Cpp_Codesign", "net/sf/orcc/templates/",
				CMakePrinter.class.getClassLoader());
	}

	public void print(String path, Network network) throws IOException {
		ST template = group.getInstanceOf("Cpp_Header");
		template.add("network", network);

		String fileName = path + File.separator + "portaddresses.h";

		byte[] b = template.render(80).getBytes();
		OutputStream os = new FileOutputStream(fileName);
		os.write(b);
		os.close();

		template = group.getInstanceOf("AdaptorConfig");
		template.add("network", network);

		fileName = path + File.separator + "AdaptorConfig.h";

		b = template.render(80).getBytes();
		os = new FileOutputStream(fileName);
		os.write(b);
		os.close();

	}

}
