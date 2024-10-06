package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.PunchAdjustmentType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 *
 * author BentM
 */
public class PunchDAO {

    private static final String QUERY_FIND = "SELECT * FROM punch WHERE id = ?";
    
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
                        int terminalId = rs.getInt("terminalid");
                        String badgeId = rs.getString("badgeid");

                        LocalDateTime originalTimestamp = rs.getTimestamp("originaltimestamp") != null 
                                                           ? rs.getTimestamp("originaltimestamp").toLocalDateTime()
                                                           : null;
                        
                        LocalDateTime adjustedTimestamp = rs.getTimestamp("adjustedtimestamp") != null 
                                                            ? rs.getTimestamp("adjustedtimestamp").toLocalDateTime()
                                                            : null;
                        
                        PunchAdjustmentType adjustmentType = null;
                        String adjustmentTypeString = rs.getString("adjustmenttype");
                        if (adjustmentTypeString != null) {
                            try {
                                adjustmentType = PunchAdjustmentType.valueOf(adjustmentTypeString);
                            } catch (IllegalArgumentException e) {
                                
                                System.err.println("Invalid adjustment type: " + adjustmentTypeString);
                            }
                        }

                        punch = new Punch(id, terminalId, badgeId, originalTimestamp);
                        punch.setAdjustedTimestamp(adjustedTimestamp);
                        punch.setAdjustmentType(adjustmentType);
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
}