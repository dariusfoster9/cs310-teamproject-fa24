package edu.jsu.mcis.cs310.tas_fa24;
 
import edu.jsu.mcis.cs310.tas_fa24.dao.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.*;
import static org.junit.Assert.*;
 
public class PunchCreateTest {
 
    private DAOFactory daoFactory;
 
    @Before
    public void setup() {
        daoFactory = new DAOFactory("tas.jdbc");
    }
 
    @Test
    public void testCreatePunch1() {
 
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
 
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
 
        
        Badge badge = badgeDAO.find("021890C0");  
        Punch p1 = new Punch(103, badge.getId(), 1);  
 
        
        LocalDateTime ots, rts;
 
        
        String badgeid = p1.getBadgeid();  
        ots = p1.getTimestamp();   
        int terminalid = p1.getTerminalid();  
        EventType eventType = p1.getEventType();  
 
        
        int punchid = punchDAO.create(p1);
 
        
        Punch p2 = punchDAO.find(punchid);
 
        
        assertEquals(badgeid, p2.getBadgeid());   
        rts = p2.getTimestamp();          
        assertEquals(terminalid, p2.getTerminalid());    

    }
}
