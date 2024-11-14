package edu.jsu.mcis.cs310.tas_fa24;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Absenteeism {
    private final Employee employee;
    private final LocalDate payperiod;
    private final BigDecimal percentage;
    
    public Absenteeism (Employee employee, LocalDate payperiod, BigDecimal percentage){
        this.employee = employee;
        this.payperiod = payperiod;
        this.percentage = percentage;
    }
    

    
    public Employee getEmployee(){
        return employee;
    }
    
    public LocalDate getPayperiod(){
        return payperiod;
    }
    
    public BigDecimal getPercentage(){
        return percentage;
    }
    
    @Override
    public String toString(){
       
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = payperiod.format(formatter);
        String percentageStr = String.format("%.2f", percentage.doubleValue());
    
        return "#" + employee.getId() + " (Pay Period Starting " + formattedDate + "): " + percentageStr;
       
    }
}
