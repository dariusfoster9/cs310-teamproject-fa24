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
    private LocalDateTime adjustedTimestamp; 
    private PunchAdjustmentType adjustmentType; 

    // Constructor for a new Punch (no ID)
    public Punch(int terminalId, Badge badge, EventType punchType) {
        this.terminalId = terminalId;
        this.badge = badge;
        this.originalTimestamp = LocalDateTime.now(); // Set to current time
      
    }

    // Constructor for an existing Punch (with ID)
    public Punch(int id, int terminalId, Badge badge, LocalDateTime originalTimestamp, EventType punchType) {
        this.id = id;
        this.terminalId = terminalId;
        this.badge = badge;
        this.originalTimestamp = originalTimestamp;
        
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

    @Override
    public String toString() {
        
         StringBuilder s = new StringBuilder();
        s.append('#').append(id).append(' ');
        s.append('(').append(badge).append(')');
        s.append(',').append(' ');
        s.append("Terminal ID: ");
        s.append(terminalId);
        return s.toString();
    }
}
