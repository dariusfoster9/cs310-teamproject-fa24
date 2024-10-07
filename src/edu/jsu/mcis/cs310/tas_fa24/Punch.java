package edu.jsu.mcis.cs310.tas_fa24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Punch {
    // Attributes
    private final int id; // Punch ID
    private final int terminalid; // ID of the terminal where the punch was made
    private final String badgeid; // Badge ID of the employee
    private final LocalDateTime originaltimestamp; // Original punch timestamp
    private LocalDateTime adjustedtimestamp; // Adjusted punch timestamp (if any)
    private PunchAdjustmentType adjustmentType; // Type of adjustment (if any)

    // Constructor for a new punch (without ID)
    public Punch(int terminalid, String badgeid) {
        this.id = -1; // -1 signifies no ID
        this.terminalid = terminalid;
        this.badgeid = badgeid;
        this.originaltimestamp = LocalDateTime.now(); // Set to current time
        this.adjustedtimestamp = null; // No adjusted timestamp initially
        this.adjustmentType = null; // No adjustment type initially
    }

    // Constructor for an existing punch (with ID)
    public Punch(int id, int terminalid, String badgeid, LocalDateTime originaltimestamp) {
        this.id = id;
        this.terminalid = terminalid;
        this.badgeid = badgeid;
        this.originaltimestamp = originaltimestamp;
        this.adjustedtimestamp = null; // Initialize as null
        this.adjustmentType = null; // Initialize as null
    }

    // Getters
    public int getId() {
        return id; // Return punch ID
    }

    public int getTerminalid() {
        return terminalid; // Return terminal ID
    }

    public String getBadgeid() {
        return badgeid; // Return badge ID
    }

    public LocalDateTime getOriginaltimestamp() {
        return originaltimestamp; // Return original timestamp
    }

    public LocalDateTime getAdjustedtimestamp() {
        return adjustedtimestamp; // Return adjusted timestamp
    }

    // Setters
    public void setAdjustedTimestamp(LocalDateTime adjustedtimestamp) {
        this.adjustedtimestamp = adjustedtimestamp; // Set adjusted timestamp
    }

    public PunchAdjustmentType getAdjustmentType() {
        return adjustmentType; // Return adjustment type
    }

    public void setAdjustmentType(PunchAdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType; // Set adjustment type
    }

    public String printOriginal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String timestamp = originaltimestamp.format(formatter);
        return String.format("#%s CLOCK IN: %S", badgeid, timestamp);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/DD/yyyy HH:mm:ss");
        
        // Format original timestamp for output
        String originaltime = originaltimestamp.format(formatter);
        String result = String.format("#%s: Terminal %d, Original Timestamp: %s", 
                                       badgeid, 
                                       terminalid, 
                                       originaltime);

        // Include adjusted timestamp if it exists
        if (adjustedtimestamp != null) {
            String adjustedTime = adjustedtimestamp.format(formatter);
            result += String.format("; Adjusted Timestamp: %s (%s)", adjustedTime, adjustmentType);
        }
        
        return result; // Return complete string representation
    }
}
