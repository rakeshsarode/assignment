package com.assignment;

import com.assignment.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 *this class populates all the related events into db, based on started and finished entry in file
 */
public class EventPopulator {
	static Logger logger = Logger.getLogger(EventPopulator.class);

	/**
	 *
	 * @param filePath
	 */
	public static void populate(String filePath){

		logger.debug("Combining Events in file : " + filePath);

		Map<String, EventEntry> ids = new HashMap<>();
		List<Event> events = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		try{
			BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8);
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {

				// JSON from file to Object
				if (line != null) {
					EventEntry eventEntry = mapper.readValue(line, EventEntry.class);

					if (ids.containsKey(eventEntry.getId())) {
						events.add(populateEvent(ids.get(eventEntry.getId()), eventEntry));
					} else {
						ids.put(eventEntry.getId(), eventEntry);
					}
				}
			}
			//clearing the ids map
			ids.clear();

			//populate these events in db
			DBUtility.insert(events);

			//clearing the events list
			events.clear();
		} catch(Exception ex) {
			logger.error("Error Occured while populating event in file "+filePath+" -> "+ex.getMessage(), ex);
		}
	}

	/**
	 *
	 * @param eventEntry1 event entry
	 * @param eventEntry2 event entry
	 * @return combined entry for an Event
	 */

	private static Event populateEvent(EventEntry eventEntry1, EventEntry eventEntry2) {
		Event event = new Event();
		event.setId(eventEntry1.getId());
		event.setDuration(Math.abs(eventEntry1.getTimestamp() - eventEntry2.getTimestamp()));
		event.setType(eventEntry1.getType());
		event.setHost(eventEntry1.getHost());
		event.setAlert(event.getDuration() > 4);
		return event;
	}
}
