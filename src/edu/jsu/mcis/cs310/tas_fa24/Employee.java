/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24;
import java.time.LocalDateTime;
/**
 *
 * @author williamsellers
 */
public class Employee {
  
    private int id;
    private String firstname;
    private String middlename;
    private String lastname;
    private LocalDateTime active;
    private Badge badge;
    private Department department;
    private Shift shift;
    private EmployeeType employeeType;

    public Employee(int id, String firstname, String middlename, String lastname, 
                    LocalDateTime active, Badge badge, Department department, 
                    Shift shift, EmployeeType employeeType) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.active = active;
        this.badge = badge;
        this.department = department;
        this.shift = shift;
        this.employeeType = employeeType;
    }

    public int getId() { return id; }
    public String getFirstname() { return firstname; }
    public String getMiddlename() { return middlename; }
    public String getLastname() { return lastname; }
    public LocalDateTime getActive() { return active; }
    public Badge getBadge() { return badge; }
    public Department getDepartment() { return department; }
    public Shift getShift() { return shift; }
    public EmployeeType getEmployeeType() { return employeeType; }

    @Override
    public String toString() {
        return String.format("ID #%d: %s, %s %s (#%s), Type: %s, Department: %s, Active: %s", 
            id, lastname, firstname, middlename, badge.getId(), employeeType.toString(), 
            department.getDescription(), active.toString());
    }
}

