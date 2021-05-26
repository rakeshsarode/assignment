package com.cs.assignment;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

//one unsorted file 
//- > split into multiple sorted files 
//-> merge them into single sorted file 
//-> split them again and find bad event in each file 
//-> get the event with max duration from outcome 
public class Main {

	static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length == 1) {

			logger.info("Processing Started!");

			createOrCleanTempDirectory();
			String inputFilePath = args[0];
			File file = new File(inputFilePath);
			if (file.exists()) {
				try {
					parseAndPopulateDB(inputFilePath);

					logger.info("Processing Done!");
				} catch (IOException e) {
					logger.error("Error Occurred :" + e.getMessage());
				}
			} else {
				logger.error("File does not exist at "+inputFilePath);
			}
		} else {
			logger.info("Please provide the full path to logfile.txt.");
		}
	}

	/**
	 * creating temp directory for the program, where all sorting merging,
	 * splitting happens
	 */
	public static void createOrCleanTempDirectory() {
		File file = new File(EventConstants.TEMP_DIR);
		if (!file.exists()) {
			if (file.mkdir()) {
				logger.info("Directory created : " + EventConstants.TEMP_DIR);
			} else {
				logger.info("Failed to create directory!");
			}
		}else {
			cleanUpTempDirectory();
		}
	}

	/**
	 * cleaning temp directory after the calculations
	 */
	public static void cleanUpTempDirectory() {
		logger.debug("Cleaning up Temp Directory :");
		File dir = new File(EventConstants.TEMP_DIR);
		for (File file : dir.listFiles()) {
			logger.debug("Deleting : " + file.getAbsolutePath());
			file.delete();
		}
	}

	/**
	 * @param inputFilePath
	 * @throws IOException
	 */
	private static void parseAndPopulateDB(String inputFilePath) throws IOException {

		//splitting the actual file in multiple files
		List<String> splitFiles = FileSplitter.split(inputFilePath);

		//sorting each splitted file and getting the list of all sorted file path
		List<String> sortedFiles = splitFiles
				.stream()
				.map(path -> FileSorter.sort(path))
				.collect(Collectors.toList());

		//merging all the sorted files so that each 2 associated events (started and finished) will be aligned together
		String sortedMergedFile = FileMerger.merge(sortedFiles);

		//spliting the sorted merged file again in multiple files
		List<String> sortedSplitFiles = FileSplitter.split(sortedMergedFile);

		//create the table if required
		DBUtility.createEventsTable();

		//populating the events in db using batch
		sortedSplitFiles
				.parallelStream()
				.forEach(path -> EventPopulator.populate(path));
	}

}