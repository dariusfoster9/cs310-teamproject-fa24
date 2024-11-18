package edu.jsu.mcis.cs310.tas_fa24.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.Shift;
import edu.jsu.mcis.cs310.tas_fa24.EventType;
import java.math.BigDecimal;

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
     
    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s){
         
        int totalMinutes=0;
        int scheduledMinutes=s.getshiftDuration()*10;

        Map<LocalDate, ArrayList<Punch>>dailyPunches=new HashMap<>();

        for (Punch punch:punchlist){
            LocalDate date=punch.getTimestamp().toLocalDate();
            dailyPunches.putIfAbsent(date,new ArrayList<>());
            dailyPunches.get(date).add(punch);
            }
        for (ArrayList<Punch>dailyPunchList:dailyPunches.values()){
        totalMinutes+=calculateTotalMinutes(dailyPunchList,s);
            }   
        int missedMinutes=scheduledMinutes-totalMinutes;
        BigDecimal absenteeismPercentage=BigDecimal.valueOf((double) missedMinutes/scheduledMinutes*100).setScale(2,BigDecimal.ROUND_HALF_UP);
        return absenteeismPercentage;
    }
    
    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist) {
        
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();

        for (Punch punch : dailypunchlist) {
            
            HashMap<String, String> punchData = new HashMap<>();

            punchData.put("id", String.valueOf(punch.getId()));
            punchData.put("badgeid", punch.getBadge().getId());
            punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
            punchData.put("punchtype", punch.getEventType().toString());


            punchData.put("adjustmenttype", punch.getadjustmentType().toString().replace("_", " "));

            String original = punch.printOriginal().replaceAll("#.*?: ", "").trim();
            punchData.put("originaltimestamp", original);
            
            String adjusted = punch.printAdjusted();
            if (!adjusted.equals("No Adjustments made")) {
                
                adjusted = adjusted.replaceAll("#.*?: ", "").replaceAll(" \\(.*?\\)", "").trim();
                
                punchData.put("adjustedtimestamp", adjusted);
                
            } else {
                punchData.put("adjustedtimestamp", "No Adjustments made");
            }
            
            jsonData.add(punchData);
    }
 
    return Jsoner.serialize(jsonData);
}
}