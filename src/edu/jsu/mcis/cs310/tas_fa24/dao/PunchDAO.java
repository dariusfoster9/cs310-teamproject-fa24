package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;           // Fix for ArrayList
import java.time.LocalDate;           // Fix for LocalDate
import java.time.LocalDateTime;       // Fix for LocalDateTime
import java.sql.Timestamp;            // Fix for Timestamp


public class PunchDAO {

    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";

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

    public ArrayList<Punch> list(Badge badge, LocalDate date) {
    ArrayList<Punch> punches = new ArrayList<>();

    // Logic to populate punches based on badge and date can be added here.

    return punches;  // Return the list of punches
}

    public int create(Punch punch) {
        int punchId = -1;  // Default value if the insert fails
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Get a connection from the DAOFactory
            Connection conn = daoFactory.getConnection();

            // Check if the connection is valid
            if (conn.isValid(0)) {

                // Prepare the SQL statement to insert a punch
                String query = "INSERT INTO punch (terminalid, badgeid, originaltimestamp) VALUES (?, ?, ?)";
                ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);  // Fix for Statement

                // Set the parameters for the query from the Punch object
                ps.setInt(1, punch.getTerminalid());  // Set terminal ID
                ps.setString(2, punch.getBadgeid());  // Set badge ID
                ps.setTimestamp(3, Timestamp.valueOf(punch.getOriginaltimestamp()));  // Fix for Timestamp

                // Execute the insert operation
                int affectedRows = ps.executeUpdate();

                // If the insert was successful, retrieve the generated punch ID
                if (affectedRows == 1) {
                    rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        punchId = rs.getInt(1);  // Get the auto-generated punch ID
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
        }

        // Return the generated punch ID or -1 if the insert failed
        return punchId;
    }
}
  
