/*
 * Copyright (c) 2012, IETR/INSA of Rennes
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
package net.sf.orcc.util;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Define the singleton managing Orcc loggers. When using this helper class,
 * your debug, info, warning and errors messages will be displayed in the right
 * eclipse console. If no Eclipse GUI plugin is loaded (i.e. executing a job in
 * command line), all the messages will be sent to the system console.
 * 
 * @author Antoine Lorence
 * 
 */
public class OrccLogger {

	public final static Level SEVERE = Level.SEVERE;
	public final static Level WARNING = Level.WARNING;
	public final static Level NOTICE = Level.INFO;
	public final static Level TRACE = Level.FINE;
	public final static Level DEBUG = Level.FINER;
	public final static Level ALL = Level.ALL;

	/**
	 * Define how text must be printed to logger (Eclipse or System console)
	 * 
	 * @author Antoine Lorence
	 * 
	 */
	private static class DefaultOrccFormatter extends Formatter {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
		 */
		@Override
		public String format(LogRecord record) {

			String output = "";

			if (!(record.getParameters() != null
					&& record.getParameters().length > 0 && record
						.getParameters()[0].equals("-raw"))) {
				Date date = new Date(record.getMillis());
				DateFormat df = DateFormat.getTimeInstance();

				output += df.format(date);
				// Default printing for warn & severe
				if (record.getLevel().intValue() > NOTICE.intValue()) {
					output += " " + record.getLevel();
				} else if (record.getLevel().intValue() == NOTICE.intValue()) {
					output += " NOTICE";
				} else if (record.getLevel().intValue() == DEBUG.intValue()) {
					output += " DEBUG";
				}
				output += " : ";
			}
			output += record.getMessage();
			return output;
		}

	}

	private static Logger logger;

	/**
	 * Ensure class can't be instantiated
	 */
	private OrccLogger() {
	}

	/**
	 * Register specific log Handler to display messages sent threw OrccLogger.
	 * If this method is never called, the default Handler will be
	 * {@link java.util.logging.ConsoleHandler}
	 * 
	 * @param handler
	 */
	public static void configureLoggerWithHandler(Handler handler) {
		logger = null;

		Logger newLog = Logger.getAnonymousLogger();
		newLog.addHandler(handler);
		newLog.setUseParentHandlers(false);
		handler.setFormatter(new DefaultOrccFormatter());

		logger = newLog;

		setLevel(TRACE);
	}

	/**
	 * Return the current logger, or a newly created one if it doesn't exists.
	 * If it is created here, a default ConsoleHandler is used as Logger's
	 * Handler.
	 * 
	 * @return
	 */
	private static Logger getLogger() {
		if (logger == null) {
			configureLoggerWithHandler(new ConsoleHandler());
		}
		return logger;
	}

	/**
	 * Set the minimum level displayed. By default, OrccLogger display messages
	 * from INFO level and highest. Call this method with DEBUG or ALL as
	 * argument to display debug messages.
	 * 
	 * @param level
	 */
	public static void setLevel(Level level) {
		getLogger().setLevel(level);
		for (Handler handler : getLogger().getHandlers()) {
			handler.setLevel(level);
		}
	}

	/**
	 * Display a debug message to current console.
	 * 
	 * @param message
	 */
	public static void debug(String message) {
		getLogger().log(DEBUG, message);
	}

	/**
	 * Display a debug message to current console, appended with a line
	 * separator character.
	 * 
	 * @param message
	 */
	public static void debugln(String message) {
		debug(message + System.getProperty("line.separator"));
	}

	/**
	 * Display a debug message on the current console, without any prepended
	 * string (time or level info).
	 * 
	 * @param message
	 */
	public static void debugRaw(String message) {
		LogRecord record = new LogRecord(DEBUG, message);
		record.setParameters(new Object[] { "-raw" });
		getLogger().log(record);
	}

	/**
	 * Display an information message on current console.
	 * 
	 * @param message
	 */
	public static void trace(String message) {
		getLogger().log(TRACE, message);
	}

	/**
	 * Display an information message on current console. The message will be
	 * appended with a line separator character.
	 * 
	 * @param message
	 */
	public static void traceln(String message) {
		trace(message + System.getProperty("line.separator"));
	}

	/**
	 * Display an information message on the current console, without any
	 * prepended string (time or level info).
	 * 
	 * @param message
	 */
	public static void traceRaw(String message) {
		LogRecord record = new LogRecord(TRACE, message);
		record.setParameters(new Object[] { "-raw" });
		getLogger().log(record);
	}

	/**
	 * Display a notice message to current console.
	 * 
	 * @param message
	 */
	public static void notice(String message) {
		getLogger().log(NOTICE, message);
	}

	/**
	 * Display a notice message to current console, appended with a line
	 * separator character.
	 * 
	 * @param message
	 */
	public static void noticeln(String message) {
		notice(message + System.getProperty("line.separator"));
	}

	/**
	 * Display a notice message on the current console, without any prepended
	 * string (time or level info).
	 * 
	 * @param message
	 */
	public static void noticeRaw(String message) {
		LogRecord record = new LogRecord(NOTICE, message);
		record.setParameters(new Object[] { "-raw" });
		getLogger().log(record);
	}

	/**
	 * Display a warning line on the current console.
	 * 
	 * @param message
	 */
	public static void warn(String message) {
		getLogger().log(WARNING, message);
	}

	/**
	 * Display a warning line on the current console, appended with a line
	 * separator character.
	 * 
	 * @param message
	 */
	public static void warnln(String message) {
		warn(message + System.getProperty("line.separator"));
	}

	/**
	 * Display a warning line on the current console, without any prepended
	 * string (time or level info).
	 * 
	 * @param message
	 */
	public static void warnRaw(String message) {
		LogRecord record = new LogRecord(WARNING, message);
		record.setParameters(new Object[] { "-raw" });
		getLogger().log(record);
	}

	/**
	 * Display an error line on the current console.
	 * 
	 * @param message
	 */
	public static void severe(String message) {
		getLogger().log(SEVERE, message);
	}

	/**
	 * Display an error line on the current console, appended with a line
	 * separator character.
	 * 
	 * @param message
	 */
	public static void severeln(String message) {
		severe(message + System.getProperty("line.separator"));
	}

	/**
	 * Display an error line on the current console, without any prepended
	 * string (time or level info).
	 * 
	 * @param message
	 */
	public static void severeRaw(String message) {
		LogRecord record = new LogRecord(SEVERE, message);
		record.setParameters(new Object[] { "-raw" });
		getLogger().log(record);
	}
}
