package com.assignment;

import com.assignment.model.Event;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class DBUtility {

    static Logger logger = Logger.getLogger(DBUtility.class);

    /**
     *
     * @param events events list that needs to be persisted in db
     */
    public static void insert(List<Event> events){
        try {
            Connection connection = getHSQLConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO EVENTS (id, duration, type, host, alert) VALUES(?,?,?,?,?)");
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);
                preparedStatement.setString(1, event.getId());
                preparedStatement.setLong(2, event.getDuration());
                preparedStatement.setString(3, event.getType());
                preparedStatement.setString(4, event.getHost());
                preparedStatement.setBoolean(5, event.isAlert());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Could not insert records.", e);
        }
    }

    /**
     * creates an events table in db, if it does not exist
     */
    public static void createEventsTable(){
        try {
            Connection con = getHSQLConnection();
            Statement stmt = con.createStatement();

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS EVENTS (id varchar(10), duration BIGINT , type varchar(100), host varchar(50), alert boolean)");
            con.commit();

            logger.info("Table created/already exists.");
        }catch (Exception e) {
            logger.error("Could not create table.", e);
        }
    }

    private static Connection getHSQLConnection() throws Exception {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        String url = "jdbc:hsqldb:file:./data/db/events;shutdown=true";
        Connection connection = DriverManager.getConnection( url, "SA", "");
        return connection;
    }
}
