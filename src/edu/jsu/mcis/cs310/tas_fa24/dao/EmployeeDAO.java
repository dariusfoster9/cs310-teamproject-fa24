/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24.dao;

/**
 *
 * @author williamsellers
 */
import java.sql.*;
import java.time.LocalDateTime;
import edu.jsu.mcis.cs310.tas_fa24.*;

public class EmployeeDAO {
     
    private Connection conn;

    public EmployeeDAO(Connection conn) {
        this.conn = conn;
    }

    public Employee find(int id) {

        Employee employee = null;
        
        try {
            String query = "SELECT * FROM employee WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                
                String firstname = rs.getString("firstname");
                String middlename = rs.getString("middlename");
                String lastname = rs.getString("lastname");
                LocalDateTime active = rs.getTimestamp("active").toLocalDateTime();
                Badge badge = new BadgeDAO(conn).find(rs.getString("badgeid"));
                Department department = new DepartmentDAO(conn).find(rs.getInt("departmentid"));
                Shift shift = new ShiftDAO(conn).find(rs.getInt("shiftid"));
                EmployeeType employeeType = EmployeeType.values()[rs.getInt("employeetype")];
                
                employee = new Employee(id, firstname, middlename, lastname, active, badge, department, shift, employeeType);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return employee;
    }

    public Employee find(Badge badge) {
        Employee employee = null;

        try {
            String query = "SELECT id FROM employee WHERE badgeid = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, badge.getId());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                employee = find(id); 
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }
}


    
   

