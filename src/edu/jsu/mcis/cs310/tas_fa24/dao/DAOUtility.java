package edu.jsu.mcis.cs310.tas_fa24.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.Shift;
import edu.jsu.mcis.cs310.tas_fa24.EventType;

/**
 * 
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {

     public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        
        int totalMinutes = 0;
        Punch clockIn = null;
        
        // Iterate through the punch list
        for (Punch punch : dailypunchlist) {
            
            // Handle CLOCK IN punch
            if (punch.getEventType() == EventType.CLOCK_IN) {
                clockIn = punch;
            }
            // Handle CLOCK OUT punch
            else if (punch.getEventType() == EventType.CLOCK_OUT && clockIn != null) {
                
                // Calculate minutes between CLOCK IN and CLOCK OUT
                Duration duration = Duration.between(clockIn.getAdjustedtimestamp(), punch.getAdjustedtimestamp());
                totalMinutes += duration.toMinutes();
                clockIn = null; // Reset clockIn after processing a pair
                
            }
            // Ignore TIME OUT punches (EventTypeId == 2)
        }
        
        // Apply lunch deduction if total minutes exceed the lunch threshold
        if (totalMinutes > shift.getshiftDuration()) {
            totalMinutes -= shift.getlunchDuration();
        }
        
        return totalMinutes;
    }


}