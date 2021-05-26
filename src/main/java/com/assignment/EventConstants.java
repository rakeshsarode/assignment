package com.assignment;

import java.io.File;
import java.nio.file.Paths;

/**
 *
 * This class holds the constants
 *
 */
public interface EventConstants {

	/**
	 * no of records each split file should contain 
	 */
	int NO_OF_RECORDS_IN_SPLIT_FILE=10000;
	
	/**
	 * temp directory for splitting , sorting and merging the files
	 */
	String TEMP_DIR = Paths.get("").toAbsolutePath().toString() + File.separator + "temp";
	
}
