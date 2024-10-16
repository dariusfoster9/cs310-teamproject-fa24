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

        // Get DAOs
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        Badge badge = badgeDAO.find("021890C0");  // Retrieve Badge object
        Punch p1 = new Punch(103, badge.getId());  // Create Punch with terminal ID and badge ID

        /* Create Timestamp Objects */
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        String badgeid = p1.getBadgeid();  // Use getBadgeid() to retrieve the badge ID
        ots = p1.getOriginaltimestamp();   // Get the original timestamp
        int terminalid = p1.getTerminalid();  // Get the terminal ID

        /* Insert Punch Into Database */
        int punchid = punchDAO.create(p1);

        /* Retrieve New Punch */
        Punch p2 = punchDAO.find(punchid);

        /* Compare Punches */
        assertEquals(badgeid, p2.getBadgeid());   // Compare badge IDs
        rts = p2.getOriginaltimestamp();          // Get the original timestamp from the retrieved punch

        assertEquals(terminalid, p2.getTerminalid());    // Compare terminal IDs
        assertEquals(ots.format(dtf), rts.format(dtf));  // Compare timestamps (formatted)
    }
}
