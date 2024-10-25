package edu.jsu.mcis.cs310.tas_fa24;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Shift{
    private final int id;
    private final String description;
    private final LocalTime start,stop,lunchstart,lunchstop;
    private final int lunchDuration,shiftDuration;
    private final int roundinterval,graceperiod,dockpenalty;
    
    public Shift(Map<String,Object> info){
        this.id=(int)info.get("id");
        this.description=(String)info.get("description");
        this.dockpenalty=(int)info.get("dockpenalty");
        this.graceperiod=(int)info.get("graceperiod");
        this.roundinterval=(int)info.get("roundinterval");
        this.start=LocalTime.parse((String)info.get("start"));
        this.stop=LocalTime.parse((String)info.get("stop"));
        this.lunchstart=LocalTime.parse((String)info.get("lunchstart"));
        this.lunchstop=LocalTime.parse((String)info.get("lunchstop"));
        this.lunchDuration=(int)info.get("lunchDuration");
        this.shiftDuration=(int)info.get("shiftDuration");
        
        
        
    }
    
    public LocalTime getStart(){
        return start;
    }
    
    public LocalTime getStop(){
        return stop;
    }
    
    public String getDescription(){
        return description;
    }

    public LocalTime getlunchStart(){
        return lunchstart;
    }
    
    public LocalTime getlunchStop(){
        return lunchstop;
    }    
    
    public int getlunchDuration(){
        return lunchDuration;
    }    
    
    public int getshiftDuration(){
        return shiftDuration;
    }
    
    public int getroundInterval(){
        return roundinterval;
    }
    
    public int getgracePeriod(){
        return graceperiod;
    }
    
    public int getdockPenalty(){
        return dockpenalty;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                description,
                start, 
                stop, 
                shiftDuration, 
                lunchstart, 
                lunchstop, 
                lunchDuration);

    }
}