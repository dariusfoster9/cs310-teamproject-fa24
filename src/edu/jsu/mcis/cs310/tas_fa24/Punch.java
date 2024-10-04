/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
Author: BentM
*/

package edu.jsu.mcis.cs310.tas_fa24;
import java.time.LocalDateTime;

public class Punch {
    private Integer id; // Unique identifier for the punch
    private int terminalId; 
    private Badge badge;
    private LocalDateTime originalTimestamp; 
    public LocalDateTime adjustedTimestamp; 
    public PunchAdjustmentType adjustmentType;
    private EventType eventType; 

    // Constructor for a new Punch (no ID)
    public Punch(int terminalId, Badge badge, EventType eventType) {
        this.terminalId = terminalId;
        this.badge = badge;
        this.originalTimestamp = LocalDateTime.now(); // Set to current time
        this.eventType = eventType; 
    }

    // Constructor for an existing Punch (with ID)
    public Punch(int id, int terminalId, Badge badge, LocalDateTime originalTimestamp, EventType eventType) {
        this.id = id;
        this.terminalId = terminalId;
        this.badge = badge;
        this.originalTimestamp = originalTimestamp;
        this.eventType = eventType; 
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public Badge getBadge() {
        return badge;
    }

    public LocalDateTime getOriginalTimestamp() {
        return originalTimestamp;
    }

    public LocalDateTime getAdjustedTimestamp() {
        return adjustedTimestamp;
    }

    public PunchAdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

    public EventType getEventType() {
        return eventType;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('#').append(id).append(' ');
        s.append('(').append(badge).append(')');
        s.append(", Terminal ID: ").append(terminalId);
        s.append(", Original Timestamp: ").append('(').append(originalTimestamp).append(')');
        
        if (adjustedTimestamp != null) {
            s.append(", Adjusted Timestamp: ").append(adjustedTimestamp);
        }
        
        if (adjustmentType != null) {
            s.append(", Adjustment Type: ").append(adjustmentType);
        }
        
        s.append(", Event Type: ").append(eventType); 
        
        return s.toString();
    }
}
