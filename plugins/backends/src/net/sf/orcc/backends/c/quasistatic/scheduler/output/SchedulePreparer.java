package net.sf.orcc.backends.c.quasistatic.scheduler.output;

import java.io.File;
import java.io.IOException;

import net.sf.orcc.backends.c.quasistatic.scheduler.dse.DSEScheduler;
import net.sf.orcc.backends.c.quasistatic.scheduler.exceptions.QuasiStaticSchedulerException;
import net.sf.orcc.backends.c.quasistatic.scheduler.main.Scheduler;
import net.sf.orcc.backends.c.quasistatic.scheduler.util.Constants;
import net.sf.orcc.backends.c.quasistatic.scheduler.util.FileUtilities;

public class SchedulePreparer {
	
	public static String sourceFilesPath;
	
	private static void copyInputData() throws QuasiStaticSchedulerException {
		File srcFile = new File(sourceFilesPath + File.separator + Constants.INPUT_FILE_NAME);
		if(!srcFile.exists()){
			throw new QuasiStaticSchedulerException("The file "+ Constants.INPUT_FILE_NAME + " was not found at " + sourceFilesPath );
		}
		File dstFile = new File(Scheduler.workingDirectoryPath + File.separator + Constants.INPUT_FILE_NAME);
		try {
			dstFile.createNewFile();
			FileUtilities.copyFile(srcFile, dstFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createOutputDirectories() {
		DSEScheduler.INPUT_FOLDER = Scheduler.workingDirectoryPath + File.separator + Constants.DSE_INPUT_PATH + File.separator;
		DSEScheduler.OUTPUT_FOLDER = Scheduler.workingDirectoryPath + File.separator + Constants.SCHEDULE_FILES_PATH + File.separator;
		new File(DSEScheduler.INPUT_FOLDER).mkdirs();
		new File(DSEScheduler.OUTPUT_FOLDER).mkdirs();
	}

	public static boolean needsToPrepare(){
		return !(new File(DSEScheduler.INPUT_FOLDER).exists() &&
				 new File(DSEScheduler.OUTPUT_FOLDER).exists() &&
			     new File(Scheduler.workingDirectoryPath + File.separator + Constants.INPUT_FILE_NAME).exists());
	}
	
	
	public static void prepare() throws QuasiStaticSchedulerException {
		createOutputDirectories();
		copyInputData();
	}
	
	public static void removeInputData() {
		FileUtilities.deleteFile(new File(Scheduler.workingDirectoryPath));
	}
}
