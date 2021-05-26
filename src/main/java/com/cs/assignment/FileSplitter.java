package com.cs.assignment;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * This file splits the large file into multiple files 
 */
public class FileSplitter {
	static Logger logger = Logger.getLogger(FileSplitter.class);

	/**
	 *
	 * @param inputFilePath
	 * @return
	 * @throws IOException
	 */
	public static List<String> split(String inputFilePath) throws IOException {
		String encoding = "UTF-8";
		BufferedReader reader = null;
		BufferedWriter writer = null;
		List<String> splitFiles = new ArrayList<String>();
		logger.debug("Splitting file started for : -> "+inputFilePath);
		try {
			//read lines from input file and split when it reaches number of max lines defined for a file 
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath), encoding));
			int count = 0;
			for (String line; (line = reader.readLine()) != null;) {
				if (count++ % EventConstants.NO_OF_RECORDS_IN_SPLIT_FILE == 0) {
					//close the writer
					closeWriter(writer);
					String newFile = EventConstants.TEMP_DIR + File.separator + "Split_"+ (count / EventConstants.NO_OF_RECORDS_IN_SPLIT_FILE) +"_"+ System.currentTimeMillis()+ ".txt";
					splitFiles.add(newFile);
					logger.debug("Split File : -> "+newFile);
					//create a new file 
					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile), encoding));
				}
				writer.write(line);
				writer.newLine();
			}
		} finally {
			closeWriter(writer);
			closeReader(reader);
		}
		logger.debug("Splitting file done.");
		logger.debug("Splitting file done, no of files created :"+splitFiles.size());
		return splitFiles;
	}

	/**
	 *
	 * @param writer
	 */
	private static void closeWriter(BufferedWriter writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				logger.error("Error Occured while closing writer : "+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param reader
	 */

	private static void closeReader(BufferedReader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				logger.error("Error Occurred while closing reader : ", e);
			}
		}
	}
}
