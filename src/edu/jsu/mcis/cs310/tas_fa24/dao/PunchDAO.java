package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.PunchAdjustmentType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Statement; 
import java.sql.Timestamp; 
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PunchDAO {

    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";

    private final DAOFactory daoFactory;

    PunchDAO(DAOFactory daoFactory) {
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

                        Punch punchFromDB = new Punch(id, terminalid, badgeid, originalTimestamp, eventTypeId);

                        // If there is an adjusted timestamp, set it (could be null)
                        LocalDateTime adjustedTimestamp = rs.getTimestamp("timestamp") != null
                                ? rs.getTimestamp("timestamp").toLocalDateTime()
                                : null;
                        punchFromDB.setAdjustedTimestamp(adjustedTimestamp);

                        // Retrieve and set the adjustment type (if any)
                        PunchAdjustmentType adjustmentType = null;
                        String adjustmentTypeString = rs.getString("eventtypeid");
                        if (adjustmentTypeString != null) {
                            try {
                                adjustmentType = PunchAdjustmentType.valueOf(adjustmentTypeString);
                            } catch (IllegalArgumentException e) {
                                System.err.println("Invalid adjustment type: " + adjustmentTypeString);
                            }
                        }
                        punchFromDB.setAdjustmentType(adjustmentType);

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
   
    private static final String QUERY_LIST = "SELECT * FROM event WHERE badgeid = ? AND timestamp = ? ORDER BY timestamp";

    public ArrayList<Punch> list(Badge badgeid, LocalDate timestamp){
       
       
       
            ArrayList<Punch>list = new ArrayList<Punch>();
            PreparedStatement ps = null;
            ResultSet rs = null;
        
                 try {

                Connection conn = daoFactory.getConnection();

             if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_LIST);
                ps.setObject(1, badgeid.getId());
                ps.setObject(2, timestamp);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                       
                       
                       
                       
                        Punch punchObjects = null;
                        punchObjects.setAdjustedTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                       
                        list.add(punchObjects);
                       
                       
                    }

                }

            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

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

        }
        return list;
    }
    public ArrayList<Punch> list(Badge badge, LocalDate begin, LocalDate end) {
    ArrayList<Punch> punchList = new ArrayList<>();
    
    // Iterate through the range of dates from 'begin' to 'end' inclusive
    for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1)) {
        // Use the existing single-day 'list()' method to get punches for each day
        ArrayList<Punch> dailyPunches = list(badge, date);
        // Add all punches from the current day to the accumulated punch list
        punchList.addAll(dailyPunches);
    }

    // Return the accumulated list of punches
    return punchList;
}
}
