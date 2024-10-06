package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Shift;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import java.sql.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ShiftDAO {
    private static final String QUERY_FIND="SELECT * FROM shift WHERE id = ?";
    private static final String QUERY_FIND_BADGE="SELECT shiftid FROM employee WHERE badgeid = ?";
    private final DAOFactory daoFactory;
    
    ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory=daoFactory;
    }
    
    public Shift find(int id) {
        Shift shift=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            Connection conn=daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps=conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);
                boolean hasResults=ps.execute();

                if (hasResults) {
                    rs=ps.getResultSet();

                    if (rs.next()) {
                        String description=rs.getString("description");
                        String start=rs.getString("shiftstart");
                        String stop=rs.getString("shiftstop");
                        String lunchstart=rs.getString("lunchstart");
                        String lunchstop=rs.getString("lunchstop");
                        //-------------------------------------------
                        LocalTime lunchStartTime=LocalTime.parse(lunchstart);
                        LocalTime lunchStopTime=LocalTime.parse(lunchstop);
                        int lunchDuration=(int)Duration.between(lunchStartTime, lunchStopTime).toMinutes();
                        //-------------------------------------------
                        
                        //-------------------------------------------
                        LocalTime shiftStartTime=LocalTime.parse(start);
                        LocalTime shiftStopTime=LocalTime.parse(stop);
                        int shiftDuration=(int)Duration.between(shiftStartTime,shiftStopTime).toMinutes();
                        //-------------------------------------------
                        
                        //int lunchDuration=rs.getInt("lunchthreshold");
                        //int shiftDuration=rs.getInt("roundinterval");
                        

                        shift=new Shift(id,description,start,stop,lunchstart,lunchstop,lunchDuration,shiftDuration);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }

        return shift;
    }

    public Shift find(Badge badge) {
        Shift shift=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND_BADGE);
                ps.setString(1, badge.getId());
                boolean hasResults=ps.execute();

                if (hasResults) {
                    rs=ps.getResultSet();

                    if (rs.next()) {
                        int shiftId=rs.getInt("shiftid");
                        shift=find(shiftId); // Call the first find() method
                    }else{
                        System.out.println("Badge ID Results: "+badge.getId());
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }

        return shift;
    }
}
