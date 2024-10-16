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
                        Map<String,Object> info=new HashMap<>();
                        info.put("id",rs.getInt("id"));
                        info.put("description",rs.getString("description"));
                        info.put("start",rs.getString("shiftstart"));
                        info.put("stop",rs.getString("shiftstop"));
                        info.put("lunchstart",rs.getString("lunchstart"));
                        info.put("lunchstop",rs.getString("lunchstop"));
                        info.put("dockpenalty",rs.getObject("dockpenalty"));
                        info.put("graceperiod",rs.getObject("graceperiod"));
                        info.put("roundinterval",rs.getObject("roundinterval"));
                        
                        //-------------------------------------------
                        LocalTime lunchStartTime=LocalTime.parse(rs.getString("lunchstart"));
                        LocalTime lunchStopTime=LocalTime.parse(rs.getString("lunchstop"));
                        int lunchDuration=(int)Duration.between(lunchStartTime, lunchStopTime).toMinutes();
                        info.put("lunchDuration",lunchDuration);
                        //-------------------------------------------
                        
                        //-------------------------------------------
                        LocalTime shiftStartTime=LocalTime.parse(rs.getString("shiftstart"));
                        LocalTime shiftStopTime=LocalTime.parse(rs.getString("shiftstop"));
                        int shiftDuration=(int)Duration.between(shiftStartTime,shiftStopTime).toMinutes();
                        info.put("shiftDuration",shiftDuration);
                        //-------------------------------------------
                        
                        

                        shift=new Shift(info);
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
