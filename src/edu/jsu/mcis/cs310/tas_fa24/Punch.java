package edu.jsu.mcis.cs310.tas_fa24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Punch {
    private final int id;
    private final int terminalId;
    private final String badgeId;
    private final LocalDateTime originalTimestamp;
    private LocalDateTime adjustedTimestamp;
    private PunchAdjustmentType adjustmentType;

    // Constructor for a new punch without an ID
    public Punch(int terminalId, String badgeId) {
        this.id = -1; // -1 signifies no ID
        this.terminalId = terminalId;
        this.badgeId = badgeId;
        this.originalTimestamp = LocalDateTime.now();
    }

    // Constructor for an existing punch with an ID
    public Punch(int id, int terminalId, String badgeId, LocalDateTime originalTimestamp) {
        this.id = id;
        this.terminalId = terminalId;
        this.badgeId = badgeId;
        this.originalTimestamp = originalTimestamp;
    }

    public int getId() {
        return id;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public String getBadgeId() {
        return badgeId;
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
        return String.format("#%s CLOCK IN: %s", badgeId, timestamp);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Punch ID: #").append(id).append(" ");
        s.append("(").append(badgeId).append(") ");
        s.append("Terminal ID: ").append(terminalId).append(", ");
        s.append("Original Timestamp: ").append(originalTimestamp);

        if (adjustedTimestamp != null) {
            s.append(", Adjusted Timestamp: ").append(adjustedTimestamp);
            s.append(", Adjustment Type: ").append(adjustmentType);
        }

        return s.toString();
    }
}
