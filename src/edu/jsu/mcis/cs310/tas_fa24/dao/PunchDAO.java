package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.EventType; // Import EventType enum
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PunchDAO {

    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_LIST = "SELECT * FROM event WHERE badgeid = ? AND timestamp = ? ORDER BY timestamp";

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
                //rs= ps.executeQuery();

                boolean hasresults = ps.execute();

                if (hasresults) {
                    
                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                        
                       
                        int id = rs.getInt("id");
                        int terminalid = rs.getInt("terminalid");
                        int eventTypeId = rs.getInt("eventtypeid");
                        EventType eventType = EventType.values()[eventTypeId];
                        
                        Punch listForaDay = new Punch(id, terminalid, badgeid.getId(), timestamp, eventType.ordinal());
                        
                        list.add(listForaDay);
                        
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

        }  return list;
       
    }


}
