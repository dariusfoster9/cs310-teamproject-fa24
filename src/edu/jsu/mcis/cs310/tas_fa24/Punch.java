package edu.jsu.mcis.cs310.tas_fa24;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Punch {

    // Attributes
    private final int id; 
    private final int terminalid; 
    private final String badgeid; 
    private LocalDateTime timestamp; 
    private LocalDateTime adjustedtimestamp; 
    private PunchAdjustmentType adjustmentType; 
    private EventType eventType;

    // Constructor for a new punch (without ID)
    public Punch(int terminalid, String badgeid, int eventTypeId) {
        this.id = -1;
        this.terminalid = terminalid;
        this.badgeid = badgeid;
        this.timestamp = LocalDateTime.now();
        this.adjustedtimestamp = null;
        this.adjustmentType = null;

        this.eventType = getEventTypeFromId(eventTypeId); 
    }

    
    public Punch(int id, int terminalid, String badgeid, LocalDateTime timestamp, int eventTypeId) {
        this.id = id;
        this.terminalid = terminalid;
        this.badgeid = badgeid;
        this.timestamp = timestamp;
        this.eventType = getEventTypeFromId(eventTypeId); 
        this.adjustedtimestamp = null;
        this.adjustmentType = null;

    }

    
    private EventType getEventTypeFromId(int eventTypeId) {
        if (eventTypeId >= 0 && eventTypeId < EventType.values().length) {
            return EventType.values()[eventTypeId];
        }
        throw new IllegalArgumentException("Invalid event type ID: " + eventTypeId);
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public String getBadgeid() {
        return badgeid;
    }
    public LocalDateTime getTimestamp(){
        return timestamp;
    }
    
    public LocalDateTime getAdjustedtimestamp() {
        return adjustedtimestamp;
    }
    
    public PunchAdjustmentType getadjustmentType(){
        return adjustmentType;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setAdjustedTimestamp(LocalDateTime adjustedtimestamp) {
        this.adjustedtimestamp = adjustedtimestamp;
    }

    public void setAdjustmentType(PunchAdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType;
    }
    
    
    public String printOriginal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

        String punchType = eventType.toString().replace("_", " ");

        return String.format("#%s %s: %S", badgeid, punchType, timestamp);
    }
    
    public String printAdjusted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        if (adjustedtimestamp!=null){
            String adjustedTime=adjustedtimestamp.format(formatter).toUpperCase();
            String punchType=eventType.toString().replace("_"," ");
            return String.format("#%s %s: %s (%s)",badgeid,punchType,adjustedTime,adjustmentType);
        }else{
            return("No Adjustments made");
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String originaltime = timestamp.format(formatter);
        String result = String.format("#%s: Terminal %d, Original Timestamp: %s", 
                                       badgeid, 
                                       terminalid, 
                                       originaltime);

        if (adjustedtimestamp != null) {
            String adjustedTime = adjustedtimestamp.format(formatter);
            result += String.format("; Adjusted Timestamp: %s (%s)", adjustedTime, adjustmentType);
        }
        return result;
    }
    public void adjust(Shift s){
        LocalTime punchTime=timestamp.toLocalTime();
        LocalTime start=s.getStart();
        LocalTime stop=s.getStop();
        LocalTime lunchstart=s.getlunchStart();
        LocalTime lunchstop=s.getlunchStop();
        int roundinterval=s.getroundInterval();
        int graceperiod=s.getgracePeriod();
        int dockpenalty=s.getdockPenalty();
                
    DayOfWeek day=timestamp.getDayOfWeek();

    if(day==DayOfWeek.SATURDAY||day==DayOfWeek.SUNDAY){
        adjustedtimestamp=roundToNearestInterval(timestamp,roundinterval);
        adjustmentType=PunchAdjustmentType.INTERVAL_ROUND;
        return;
    }        
        
        
        
        if(eventType==EventType.CLOCK_IN){
            if(punchTime.isBefore(start)){
                adjustedtimestamp=timestamp.with(start);
                adjustmentType=PunchAdjustmentType.SHIFT_START;
                
            }else if(punchTime.isBefore(start.plusMinutes(graceperiod))){
                adjustedtimestamp=timestamp.with(start);
                adjustmentType=PunchAdjustmentType.SHIFT_START;
                
            }else if (punchTime.isAfter(start)&&punchTime.isBefore(start.plusMinutes(dockpenalty))) {
                adjustedtimestamp=timestamp.with(start.plusMinutes(dockpenalty));
                adjustmentType=PunchAdjustmentType.SHIFT_DOCK;      
                
        }else if(punchTime.isBefore(start.minusMinutes(roundinterval))){
                adjustedtimestamp=roundToNearestInterval(timestamp,roundinterval);                
                adjustmentType=PunchAdjustmentType.INTERVAL_ROUND;                 
                
            }else{
                adjustedtimestamp=timestamp;                
                adjustmentType=PunchAdjustmentType.NONE;                              
            }
            
        }else if(eventType==EventType.CLOCK_OUT){
            if (punchTime.isAfter(stop)&&punchTime.isBefore(stop.plusMinutes(graceperiod))){
                adjustedtimestamp=timestamp.with(stop);
                adjustmentType=PunchAdjustmentType.SHIFT_STOP;
                
        }else if(punchTime.isAfter(stop.minusMinutes(dockpenalty))&&punchTime.isBefore(stop)){
                adjustedtimestamp=timestamp.with(stop);
                adjustmentType=PunchAdjustmentType.SHIFT_DOCK;                
                
        }else if(punchTime.isAfter(stop.minusMinutes(graceperiod))&&punchTime.isBefore(stop)){
                adjustedtimestamp=timestamp.with(stop);
                adjustmentType=PunchAdjustmentType.SHIFT_STOP;
                                                                       
        }else if(punchTime.isBefore(stop.minusMinutes(roundinterval))){
                adjustedtimestamp=timestamp.with(stop).withMinute(15);
                adjustmentType=PunchAdjustmentType.INTERVAL_ROUND;        
            }else{
                adjustedtimestamp=roundToNearestInterval(timestamp,roundinterval);
                adjustmentType=PunchAdjustmentType.NONE;
            }
        }
        if(punchTime.isAfter(lunchstart)&&punchTime.isBefore(lunchstop)){
            if (eventType==EventType.CLOCK_IN){
                adjustedtimestamp=timestamp.with(lunchstop);
                adjustmentType=PunchAdjustmentType.LUNCH_STOP;
            }else if(eventType==EventType.CLOCK_OUT){
                adjustedtimestamp=timestamp.with(lunchstart);
                adjustmentType=PunchAdjustmentType.LUNCH_START;
            }
        }
    }
    private LocalDateTime roundToNearestInterval(LocalDateTime time,int intervalMinutes) {
        long minutes=time.getMinute();
        long quotient=minutes/intervalMinutes;
        long remainder=minutes%intervalMinutes;
        long roundedMinutes=(remainder<intervalMinutes/2)?
                quotient*intervalMinutes:(quotient+1)*intervalMinutes;
        return time.truncatedTo(ChronoUnit.HOURS).plusMinutes(roundedMinutes);
    }

}
