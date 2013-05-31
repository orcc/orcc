package net.sf.orcc.simulators.runtime.system.io.item;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import net.sf.orcc.simulators.runtime.impl.SystemIO;
import net.sf.orcc.util.OrccUtil;

public class Console extends SystemIO {

	private class WindowConsole extends WindowAdapter implements
			WindowListener, ActionListener, Runnable {
		private JFrame frame;
		private JTextArea textArea;
		private boolean quit;
		private JTextField jtfInput;

		private final Deque<String> iStream = new ArrayDeque<String>();

		public WindowConsole(String name) {
			// create all components and add them
			jtfInput = new JTextField(20);
			jtfInput.addActionListener(this);
			frame = new JFrame("Console " + name);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = new Dimension((int) (screenSize.width / 3),
					(int) (screenSize.height / 2));
			int x = (int) (frameSize.width / 2);
			int y = (int) (frameSize.height / 2);
			frame.setBounds(x, y, frameSize.width, frameSize.height);

			textArea = new JTextArea();
			textArea.setEditable(false);
			DefaultCaret caret = (DefaultCaret) textArea.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

			frame.getContentPane().setLayout(new BorderLayout());
			frame.getContentPane().add(jtfInput, BorderLayout.SOUTH);
			frame.getContentPane().add(new JScrollPane(textArea),
					BorderLayout.CENTER);

			frame.setVisible(true);
			frame.addWindowListener(this);

			quit = false; // signals the Threads that they should exit

		}

		public synchronized void windowClosed(WindowEvent evt) {
			quit = true;
			this.notifyAll();
		}

		public synchronized void windowClosing(WindowEvent evt) {
			frame.setVisible(false); // default behaviour of JFrame
			frame.dispose();
		}

		public synchronized void actionPerformed(ActionEvent evt) {
			try {
				String text = jtfInput.getText();
				iStream.addFirst(text);
				jtfInput.setText("");
				this.notifyAll();
			} catch (Exception e) {
			}
		}

		public synchronized void run() {
			try {
				while (Thread.currentThread().isAlive()) {
					try {
						this.wait(100);
					} catch (InterruptedException ie) {
					}
					if (quit)
						return;
				}
			} catch (Exception e) {
				textArea.append("\nConsole reports an Internal error.");
				textArea.append("The error is: " + e);
			}
		}

		public synchronized String read() {
			iStream.clear();
			while (iStream.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			return iStream.getLast();
		}

		public synchronized void write(String v) {
			textArea.append(v);
		}

	}

	private WindowConsole wConsole;

	public Console(String id) {
		wConsole = new WindowConsole(id);
	}

	@Override
	public boolean isConsole() {
		return true;
	}

	public String read() {
		return wConsole.read();
	}

	public void write(String v) {
		String unescaped = OrccUtil.getUnescapedString(v);
		wConsole.write(unescaped);
	}

}
