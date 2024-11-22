package edu.jsu.mcis.cs310.tas_fa24;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;


public class Shift{
    private final int id;
    private final String description;
    private final DailySchedule defaultschedule;
    private HashMap<Integer, DailySchedule> dailySchedules;
    
    public Shift(int id, String description, DailySchedule defaultschedule){
        this.id = id;
        this.description = description;
        this.defaultschedule = defaultschedule;
        this.dailySchedules = new HashMap<>();
    }
/*    
    private void initializedDefaultSchedule(){
        
        for (DayOfWeek day : DayOfWeek.values()) {
            
            if (day != DayOfWeek.SUNDAY && day != DayOfWeek.SATURDAY) {  
                int dayOfWeekInt = day.getValue();
                this.dailySchedules.put(dayOfWeekInt, defaultSchedDay(day));
            }
            
        }
    }
*/    
    
    
    public LocalTime getStart(){
        return defaultschedule.start;
    }
    
    public LocalTime getStop(){
        return defaultschedule.stop;
    }
    
    public String getDescription(){
        return description;
    }

    public LocalTime getlunchStart(){
        return defaultschedule.lunchstart;
    }
    
    public LocalTime getlunchStop(){
        return defaultschedule.lunchstop;
    }    
    
    public int getlunchDuration(){
        return defaultschedule.lunchDuration;
    }    
    
    public int getshiftDuration(){
        return defaultschedule.shiftDuration;
    }
    
    public int getroundInterval(){
        return defaultschedule.roundinterval;
    }
    
    public int getgracePeriod(){
        return defaultschedule.graceperiod;
    }
    
    public int getdockPenalty(){
        return defaultschedule.dockpenalty;
    }
    
    public DailySchedule getDailyschedule(){
        return defaultschedule;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                description,
                defaultschedule.start, 
                defaultschedule.stop, 
                defaultschedule.shiftDuration, 
                defaultschedule.lunchstart, 
                defaultschedule.lunchstop, 
                defaultschedule.lunchDuration);

    }
    
    
}