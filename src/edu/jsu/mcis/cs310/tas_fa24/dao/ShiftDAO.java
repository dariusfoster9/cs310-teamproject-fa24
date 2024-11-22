package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Shift;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.DailySchedule;
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
                        int scheduleId = rs.getInt("id");
                        String description = rs.getString("description");
                        LocalTime start = LocalTime.parse(rs.getString("shiftstart"));
                        LocalTime stop = LocalTime.parse(rs.getString("shiftstop"));
                        LocalTime lunchStart = LocalTime.parse(rs.getString("lunchstart"));
                        LocalTime lunchStop = LocalTime.parse(rs.getString("lunchstop"));
                        int dockPenalty = rs.getInt("dockpenalty");
                        int gracePeriod = rs.getInt("graceperiod");
                        int roundInterval = rs.getInt("roundinterval");
                        //-------------------------------------------
                        int lunchDuration = (int) Duration.between(lunchStart, lunchStop).toMinutes();
                        int shiftDuration = (int) Duration.between(start, stop).toMinutes();
                        //-------------------------------------------
                        
                        DailySchedule dailySchedule = new DailySchedule(
                        start,
                        stop,
                        lunchStart,
                        lunchStop,
                        lunchDuration,
                        shiftDuration,
                        roundInterval,
                        gracePeriod,
                        dockPenalty
                        );

                        shift = new Shift(scheduleId, description, dailySchedule);
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
                        shift=find(shiftId);
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
