package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.EventType; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PunchDAO {

    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
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
                        String badge = rs.getString("badgeid");
                        LocalDateTime originalTimestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                        int eventTypeId = rs.getInt("eventtypeid");

                        EventType eventType = EventType.values()[eventTypeId];

                        Punch punchFromDB = new Punch(id, terminalid, badge, originalTimestamp, eventType.ordinal());

                        punch = punchFromDB;
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

        return punch;
    }
    public ArrayList<Punch> list(Badge badge, LocalDate date){
        
        ArrayList<Punch>list = new ArrayList<Punch>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_LIST);
                ps.setString(1, badge.getId());
                ps.setDate(2, java.sql.Date.valueOf(date));


                rs = ps.executeQuery();

                    while (rs.next()) {
                        
                        int id = rs.getInt("id");
                        int terminalid = rs.getInt("terminalid");
                        int eventTypeId = rs.getInt("eventtypeid");
                        LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                        EventType eventType = EventType.values()[eventTypeId];

                        Punch listForaDay = new Punch(id, terminalid, badge.getId(), timestamp, eventType.ordinal());
                        list.add(listForaDay);
                     
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
 
        
        for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1)) {
            ArrayList<Punch> dailyPunches = list(badge, date);
            punchList.addAll(dailyPunches);
        }
 
        return punchList;
    }

    public int create(Punch punch) {
        
        int punchId = -1;  
        PreparedStatement ps = null;    ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();


            if (conn.isValid(0)) {
                String query = "INSERT INTO event (terminalid, badgeid, timestamp) VALUES (?, ?, ?)";
                ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);


                ps.setInt(1, punch.getTerminalid());  
                ps.setString(2, punch.getBadgeid());  
                ps.setTimestamp(3, Timestamp.valueOf(punch.getTimestamp()));


                int affectedRows = ps.executeUpdate();


                if (affectedRows == 1) {
                    rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        punchId = rs.getInt(1);  
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

            return punchId;
        }

}


