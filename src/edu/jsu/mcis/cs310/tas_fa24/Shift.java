package edu.jsu.mcis.cs310.tas_fa24;

import java.time.LocalTime;
import java.util.Map;

public class Shift{
    private final int id;
    private final String description;
    private final LocalTime start,stop,lunchstart,lunchstop;
    private final int lunchDuration,shiftDuration;
    
    public Shift(int id, String description, String start, String stop, String lunchstart, 
            String lunchstop,int lunchDuration, int shiftDuration)
        {this.start=LocalTime.parse(start);
        this.stop=LocalTime.parse(stop);
        this.lunchstart=LocalTime.parse(lunchstart);
        this.lunchstop=LocalTime.parse(lunchstop);
        this.id=id;
        this.description=description;
        this.lunchDuration=lunchDuration;
        this.shiftDuration=shiftDuration;

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
    
    public int getlunchDuration(){
        return lunchDuration;
    }    
    
    public int getshiftDuration(){
        return shiftDuration;
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