package com.assignment;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class is helper class for sorting the file
 *
 */
public class FileSorter {
	
	static Logger logger = Logger.getLogger(FileSorter.class);

	/**
	 *
	 * @param filePath
	 * @return
	 */
	public static String sort(String filePath) {
		String sortedFilePath = filePath + "sort";

		logger.debug("Sorting started for file : " + filePath);
		try {
			BufferedReader reader = Files.newBufferedReader(Paths.get(filePath));
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(sortedFilePath));
			reader.lines().sorted().forEach(str -> wrapWriter(writer, str));
			reader.close();
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("Sorting done for file : " + filePath);
		return sortedFilePath;
	}

	/**
	 *
	 * @param writer
	 * @param str
	 */
	private static void wrapWriter(BufferedWriter writer, String str) {

		try {
			writer.write(str + "\n");
		} catch (IOException e) {
			logger.error("Exception Occurred : ", e);
		}
	}


}
