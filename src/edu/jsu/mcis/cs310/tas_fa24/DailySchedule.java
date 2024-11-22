/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;


public class DailySchedule {
    public final LocalTime start, stop, lunchstart, lunchstop;
    public final int lunchDuration, shiftDuration;
    public final int roundinterval, graceperiod, dockpenalty;
    
    public DailySchedule(LocalTime start, LocalTime stop, LocalTime lunchStart, LocalTime lunchStop,
                        int lunchDuration, int shiftDuration, int roundInterval, int gracePeriod, int dockPenalty){
        
        this.start = start;
        this.stop = stop;
        this.lunchstart = lunchStart;
        this.lunchstop = lunchStop;
        this.lunchDuration = lunchDuration;
        this.shiftDuration = shiftDuration;
        this.roundinterval = roundInterval;
        this.graceperiod = gracePeriod;
        this.dockpenalty = dockPenalty;
    }
    
    public LocalTime getStart(){
        return start;
    }
    
    public LocalTime getStop(){
        return stop;
    }
    
    public LocalTime getLunchStart(){
        return lunchstart;
    }
    
    public LocalTime getLunchStop(){
        return lunchstop;
    }
    
    public int getLunchDuration(){
        return lunchDuration;
    }
    
    public int getShiftDuration(){
        return shiftDuration;
    }
    
    public int getRoundInterval(){
        return roundinterval;
    }
    
    public int getGracePeriod(){
        return graceperiod;
    }
    
    public int getDockPenalty(){
        return dockpenalty;
    }
    
}
