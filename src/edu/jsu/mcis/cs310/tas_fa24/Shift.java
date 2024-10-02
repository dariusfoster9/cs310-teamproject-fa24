package edu.jsu.mcis.cs310.tas_fa24;

import java.util.Map;

public class Shift{
    private String Start,Stop,Parameters;
    private int lunchDuration,shiftDuration;
    
    public Shift(Map<String, Object> ShiftInfo){
        this.Start=(String)ShiftInfo.get("Start");
        this.Stop=(String)ShiftInfo.get("Stop");
        this.Parameters=(String)ShiftInfo.get("Parameters");
        this.lunchDuration=(int)(ShiftInfo.get("lunchDuration"));
        this.shiftDuration=(int)(ShiftInfo.get("shiftDuration"));

    }
    public String getStart(){
        return Start;
    }
    
    public String getStop(){
        return Stop;
    }
    
    public String getParameters(){
        return Parameters;
    }    
    
    public int getlunchDuration(){
        return lunchDuration;
    }    
    
    public int getshiftDuration(){
        return shiftDuration;
    }
    
    @Override
    public String toString(){
        StringBuilder s=new StringBuilder();
        
        s.append("Shift Info: ");
        s.append("Start: ").append(Start).append(", ");
        s.append("Stop: ").append(Stop).append(", ");
        s.append("Parameters: ").append(Parameters).append(", ");
        s.append("Lunch Duration: ").append(Start).append(" minutes, ");
        s.append("Shift Duration: ").append(Start).append(" minutes.");
        
        
        return s.toString();
    }
}