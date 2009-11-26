package net.sf.orcc.backends.c.quasistatic.scheduler.output;

import java.io.File;

import net.sf.orcc.backends.c.quasistatic.scheduler.dse.DSEScheduler;
import net.sf.orcc.backends.c.quasistatic.scheduler.main.Scheduler;
import net.sf.orcc.backends.c.quasistatic.scheduler.util.Constants;

public class SchedulePreparer {
	
	private static void createOutputDirectories() {
		DSEScheduler.INPUT_FOLDER = Scheduler.workingDirectoryPath + File.separator + Constants.DSE_INPUT_PATH + File.separator;
		DSEScheduler.OUTPUT_FOLDER = Scheduler.workingDirectoryPath + File.separator + Constants.SCHEDULE_FILES_PATH + File.separator;
		new File(DSEScheduler.INPUT_FOLDER).mkdirs();
		new File(DSEScheduler.OUTPUT_FOLDER).mkdirs();
	}

	private static void createConfigFiles() {
		new File(Scheduler.workingDirectoryPath + Constants.CONFIG_PATH)
				.mkdirs();
		ConfigFilesCreator.createPropertiesFiles(Scheduler.workingDirectoryPath
				+ Constants.CONFIG_PATH);
	}
	
	public static void prepare() {
		createOutputDirectories();
		createConfigFiles();
	}
	
}
