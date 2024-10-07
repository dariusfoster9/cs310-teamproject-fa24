package edu.jsu.mcis.cs310.tas_fa24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Punch {
    private final int id;
    private final int terminalid;
    private final String badgeid;
    private final LocalDateTime originalTimestamp;
    private LocalDateTime adjustedTimestamp;
    private PunchAdjustmentType adjustmentType;

    // Constructor for a new punch without an ID
    public Punch(int terminalid, String badgeid) {
        this.id = -1; // -1 signifies no ID
        this.terminalid = terminalid;
        this.badgeid = badgeid;
        this.originalTimestamp = LocalDateTime.now();
    }

    // Constructor for an existing punch with an ID
    public Punch(int id, int terminalid, String badgeid, LocalDateTime originalTimestamp) {
        this.id = id;
        this.terminalid = terminalid;
        this.badgeid = badgeid;
        this.originalTimestamp = originalTimestamp;
    }

    public int getid() {
        return id;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public String getBadgeid() {
        return badgeid;
    }

    public LocalDateTime getOriginalTimestamp() {
        return originalTimestamp;
    }

    public LocalDateTime getAdjustedTimestamp() {
        return adjustedTimestamp;
    }

    public void setAdjustedTimestamp(LocalDateTime adjustedTimestamp) {
        this.adjustedTimestamp = adjustedTimestamp;
    }

    public PunchAdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(PunchAdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    public String printOriginal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String timestamp = originalTimestamp.format(formatter);
        return String.format("#%s CLOCK IN: %s", badgeid, timestamp);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Punch ID: #").append(id).append(" ");
        s.append("(").append(badgeid).append(") ");
        s.append("Terminal ID: ").append(terminalid).append(", ");
        s.append("Original Timestamp: ").append(originalTimestamp);

        if (adjustedTimestamp != null) {
            s.append(", Adjusted Timestamp: ").append(adjustedTimestamp);
            s.append(", Adjustment Type: ").append(adjustmentType);
        }

        return s.toString();
    }
}
