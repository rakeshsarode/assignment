package com.cs.assignment;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * 
 * This class is helper class for merging the files 
 *
 */
public class FileMerger {
	
	static Logger logger = Logger.getLogger(FileMerger.class);

	/**
	 *
	 * @param sortedFiles
	 * @return
	 * @throws IOException
	 */
	public static String merge(List<String> sortedFiles) throws IOException {

		logger.debug("Merging files started.\n" + sortedFiles);
		Map<String, BufferedReader> map = new HashMap<String, BufferedReader>();
		List<BufferedReader> readers = new ArrayList<BufferedReader>();
		String mergedFilePath = EventConstants.TEMP_DIR + File.separator + "MergeFile_"+System.currentTimeMillis() + ".txt";
		BufferedWriter writer = null;
		try {
			writer = Files.newBufferedWriter(Paths.get(mergedFilePath));
			for (int i = 0; i < sortedFiles.size() && sortedFiles.get(i) != null; i++) {
				BufferedReader reader = Files.newBufferedReader(Paths.get(sortedFiles.get(i)));
				readers.add(reader);
				String line = reader.readLine();
				if (line != null) {
					map.put(line, readers.get(i));
				}
			}
			List<String> topEntryFromEachFile = new LinkedList<String>(map.keySet());
			while (map.size() > 0) {
				topEntryFromEachFile.sort(Comparator.comparing(String::toString));
				String line = topEntryFromEachFile.remove(0);
				writer.write(line + "\n");
				BufferedReader reader = map.remove(line);
				String nextLine = reader.readLine();
				if (nextLine != null) {
					map.put(nextLine, reader);
					topEntryFromEachFile.add(nextLine);
				}
			}
			logger.debug("Merging files done.");
			logger.debug("Merged all the files into - " + mergedFilePath);
		} catch (IOException io) {
			throw io;
		} finally {
			readers.forEach(reader -> wrapReader(reader));
			writer.close();
		}
		return mergedFilePath;
	}

	/**
	 *
	 * @param reader
	 */
	private static void wrapReader(BufferedReader reader) {

		try {
			reader.close();
		} catch (IOException e) {
			logger.error("Exception Occurred : ", e);
		}
	}
}
