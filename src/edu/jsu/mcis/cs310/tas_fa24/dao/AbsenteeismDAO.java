package edu.jsu.mcis.cs310.tas_fa24.dao;
import edu.jsu.mcis.cs310.tas_fa24.Absenteeism;
import edu.jsu.mcis.cs310.tas_fa24.Employee;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
/**
*
* @author droge
*/
public class AbsenteeismDAO {
    private static final String QUERY_FIND = "SELECT * FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
    private static final String QUERY_CREATE = "INSERT INTO absenteeism (employee, payperiod, percentage) VALUES (?, ?, ?)";
    private final DAOFactory daoFactory;
 
    AbsenteeismDAO(DAOFactory daoFactory) {
 
        this.daoFactory = daoFactory;
 
    }
 
    public Absenteeism find(Employee employee, LocalDate payperiod) {
 
        Absenteeism absenteeism = null;
 
        PreparedStatement ps = null;
        ResultSet rs = null;
 
        try {
 
            Connection conn = daoFactory.getConnection();
 
            if (conn.isValid(0)) {
 
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, employee.getFirstname());
                ps.setDate(2, java.sql.Date.valueOf(payperiod));
                boolean hasresults = ps.execute();
 
                if (hasresults) {
 
                    rs = ps.getResultSet();
 
                    while (rs.next()) {
                        BigDecimal percentage = rs.getBigDecimal("percentage");
                        absenteeism = new Absenteeism(employee, payperiod, percentage);
 
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
 
        return absenteeism;
 
    }
     public Absenteeism create (Employee employee, LocalDate payperiod) {
 
        Absenteeism absenteeism = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
 
        try {
 
            Connection conn = daoFactory.getConnection();
 
            if (conn.isValid(0)) {
 
                ps = conn.prepareStatement(QUERY_CREATE);
                ps.setString(1, employee.getFirstname());
                ps.setDate(2, java.sql.Date.valueOf(payperiod));
                int affectedRows = ps.executeUpdate();
                if (affectedRows == 1) {
                    rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        BigDecimal percentage = rs.getBigDecimal("percentage");
                        absenteeism = new Absenteeism(employee, payperiod, percentage);
 
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
 
        return absenteeism;
 
    }
}
