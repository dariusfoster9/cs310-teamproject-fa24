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
    private LocalDateTime timestamp; 
    private LocalDateTime adjustedtimestamp; 
    private PunchAdjustmentType adjustmentType; 
    private EventType eventType;
    private Badge badge;

    // Constructor for a new punch (without ID)
    public Punch(int terminalid, Badge badge, int eventTypeId) {
        this.id = -1;
        this.terminalid = terminalid;
        this.badge = badge;
        this.timestamp = LocalDateTime.now();
        this.adjustedtimestamp = null;
        this.adjustmentType = null;

        this.eventType = getEventTypeFromId(eventTypeId); 
    }

    
    public Punch(int id, int terminalid, Badge badge, LocalDateTime timestamp, int eventTypeId) {
        this.id = id;
        this.terminalid = terminalid;
        this.badge = badge;
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
    
    public Badge getBadge(){
        return badge;
    }

    public void setAdjustedTimestamp(LocalDateTime adjustedtimestamp) {
        this.adjustedtimestamp = adjustedtimestamp;
    }

    public void setAdjustmentType(PunchAdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType;
    }
    
    
    public String printOriginal() {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String formattedTimestamp = timestamp.format(formatter);
        String punchType = eventType.toString().replace("_", " ");
        return String.format("#%s %s: %S", badge.getId(), punchType, formattedTimestamp);

    }
    
    public String printAdjusted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        if (adjustedtimestamp!=null){
            String adjustedTime=adjustedtimestamp.format(formatter).toUpperCase();
            String punchType=eventType.toString().replace("_"," ");
            return String.format("#%s %s: %s (%s)", badge.getId(),punchType,adjustedTime,adjustmentType);
        }else{
            return("No Adjustments made");
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String originaltime = timestamp.format(formatter);
        String result = String.format(" Original Timestamp: %s, #%s: Terminal %d,", 
                                        originaltime,
                                       badge.getId(), 
                                       terminalid 
                                       );
 
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
                adjustedtimestamp=roundToNearestInterval(timestamp, roundinterval);                
                adjustmentType=PunchAdjustmentType.INTERVAL_ROUND;                 
                
            }else{
                adjustedtimestamp= roundToNearestInterval(timestamp, roundinterval);                
                adjustmentType=PunchAdjustmentType.NONE;                              
            }
            
        }else if(eventType==EventType.CLOCK_OUT){        

            if (punchTime.isAfter(stop) && punchTime.isBefore(stop.plusMinutes(graceperiod))){
                adjustedtimestamp=timestamp.with(stop);
                adjustmentType=PunchAdjustmentType.SHIFT_STOP;
                
            }else if(punchTime.isAfter(stop.minusMinutes(graceperiod))&&punchTime.isBefore(stop)){
                adjustedtimestamp=timestamp.with(stop);
                adjustmentType=PunchAdjustmentType.SHIFT_STOP;
                
            } else if((punchTime.isBefore(stop) && punchTime.isAfter(stop.minusMinutes(dockpenalty)))){
                adjustedtimestamp=timestamp.with(stop);
                adjustmentType=PunchAdjustmentType.SHIFT_DOCK;
                
            }else if (punchTime.equals(stop.minusMinutes(dockpenalty))) {
                adjustedtimestamp = timestamp;
                adjustmentType = PunchAdjustmentType.SHIFT_DOCK;
            
            } else if (punchTime.isAfter(stop.minusMinutes(roundinterval)) && punchTime.isBefore(stop.plusMinutes(roundinterval))) {
                adjustedtimestamp = timestamp.with(stop);
                adjustmentType = PunchAdjustmentType.SHIFT_STOP;
    
            }else if(punchTime.isBefore(stop.minusMinutes(roundinterval))){
                adjustedtimestamp= roundToNearestInterval(timestamp, roundinterval);
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
        return time.truncatedTo(ChronoUnit.HOURS).plusMinutes(roundedMinutes).withSecond(0).withNano(0);
    }

    

}
