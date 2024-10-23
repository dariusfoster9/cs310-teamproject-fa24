package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.EventType; // Import EventType enum
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Statement; 
import java.sql.Timestamp; 
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PunchDAO {

    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    //private static final String QUERY_LIST = "SELECT * FROM event WHERE badgeid = ? AND timestamp = ? ORDER BY timestamp";
    private static final String QUERY_LIST = "SELECT *, DATE(`timestamp`) AS tsdate FROM `event` WHERE badgeid = ? HAVING tsdate = ? ORDER BY `timestamp`;";

    private final DAOFactory daoFactory;

    public PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Punch find(int id) {
        Punch punch = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);

                boolean hasResults = ps.execute();

                if (hasResults) {
                    rs = ps.getResultSet();

                    while (rs.next()) {
                        int terminalid = rs.getInt("terminalid");
                        String badgeid = rs.getString("badgeid");
                        LocalDateTime originalTimestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                        int eventTypeId = rs.getInt("eventtypeid");

                        
                        EventType eventType = EventType.values()[eventTypeId];

                        
                        Punch punchFromDB = new Punch(id, terminalid, badgeid, originalTimestamp, eventType.ordinal());

                        punch = punchFromDB;
                    }
                }
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } 
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } 
                catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } 
                catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }

        return punch;
    }
    public ArrayList<Punch> list(Badge badgeid, LocalDate date){
        
        ArrayList<Punch> list = new ArrayList<Punch>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {

            Connection conn = daoFactory.getConnection();
    // Logic to populate punches based on badge and date can be added here.

            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_LIST);
                ps.setString(1, badgeid.getId());
                ps.setDate(2, java.sql.Date.valueOf(date));
                //rs= ps.executeQuery();

        try {
            // Get a connection from the DAOFactory
            Connection conn = daoFactory.getConnection();

                if (hasresults) {
                    
                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                        int id = rs.getInt("id");
                        int terminalid = rs.getInt("terminalid");
                        int eventTypeId = rs.getInt("eventtypeid");
                        LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                        EventType eventType = EventType.values()[eventTypeId];
                        
                        Punch listForaDay = new Punch(id, terminalid, badgeid.getId(), timestamp, eventType.ordinal());
                        
                        list.add(listForaDay);
                        
                    }
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            // Close result set and prepared statement resources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            } 

        }  return list;
       
    }
    // New method to list punches over a date range

    public ArrayList<Punch> list(Badge badge, LocalDate begin, LocalDate end) {
        ArrayList<Punch> punchList = new ArrayList<>();
        
        // Iterate through the range of dates from 'begin' to 'end' inclusive
        for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1)) {
            // Use the existing single-day 'list()' method to get punches for each day
            ArrayList<Punch> dailyPunches = list(badge, date);
            // Add all punches from the current day to the accumulated punch list
            punchList.addAll(dailyPunches);
        }
      
        System.out.println(punchList);
        // Return the accumulated list of punches
        return punchList;
    }
}
  
