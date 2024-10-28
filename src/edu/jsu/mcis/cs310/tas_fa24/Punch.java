package edu.jsu.mcis.cs310.tas_fa24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Punch {

    
    private final int id; 
    private final int terminalid; 
    private final String badgeid; 
    private LocalDateTime timestamp; 
    private LocalDateTime adjustedtimestamp; 
    private PunchAdjustmentType adjustmentType; 
    private int eventTypeId; 
    private EventType eventType;

    
    public Punch(int terminalid, String badgeid, int eventTypeId) {
        this.id = -1;
        this.terminalid = terminalid;
        this.badgeid = badgeid;
        this.timestamp = LocalDateTime.now();
        this.adjustedtimestamp = null;
        this.adjustmentType = null;
        this.eventTypeId = eventTypeId;
        this.eventType = getEventTypeFromId(eventTypeId); 
    }

    
    public Punch(int id, int terminalid, String badgeid, LocalDateTime timestamp, int eventTypeId) {
        this.id = id;
        this.terminalid = terminalid;
        this.badgeid = badgeid;
        this.timestamp = timestamp;
        this.adjustedtimestamp = null;
        this.adjustmentType = null;
        this.eventTypeId = eventTypeId;
        this.eventType = getEventTypeFromId(eventTypeId); 
    }
    
    private EventType getEventTypeFromId(int eventTypeId) {
        if (eventTypeId >= 0 && eventTypeId < EventType.values().length) {
            return EventType.values()[eventTypeId];
        }
        throw new IllegalArgumentException("Invalid event type ID: " + eventTypeId);
    }

    
    public int getId() {
        return id;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public String getBadgeid() {
        return badgeid;
    }

    
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public LocalDateTime getAdjustedtimestamp() {
        return adjustedtimestamp;
    }

    public int getEventTypeId() {
        return eventTypeId;
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
        String formattedTimestamp = timestamp.format(formatter);
        String punchType = eventType.toString().replace("_", " ");

        return String.format("#%s %s: %S", badgeid, punchType, formattedTimestamp);
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
}

