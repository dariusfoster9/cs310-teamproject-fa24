/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24;
import edu.jsu.mcis.cs310.tas_fa24.dao.*;
import java.time.*;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;
/**
 *
 * @author droge
 */
public class PunchListFindListRangeTest {
    
    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }
    @Test
    public void testFindPunchListRange1() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamps and Badge Objects for Punch List */
        
        LocalDate start = LocalDate.of(2018, Month.AUGUST, 3); 
        LocalDate end = LocalDate.of(2018, Month.AUGUST, 6); 

        Badge b = badgeDAO.find("DFDFE648");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, start, end);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(393));
        p2.add(punchDAO.find(467));
        p2.add(punchDAO.find(504));
        p2.add(punchDAO.find(593));

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
    
     @Test
    public void testFindPunchListRange2() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamps and Badge Objects for Punch List */
        
        LocalDate start = LocalDate.of(2018, Month.SEPTEMBER, 26); 
        LocalDate end = LocalDate.of(2018, Month.SEPTEMBER, 28); 

        Badge b = badgeDAO.find("87176FD7");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, start, end);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(5945));
        p2.add(punchDAO.find(5962));
        p2.add(punchDAO.find(5966));
        p2.add(punchDAO.find(5975));
        p2.add(punchDAO.find(6089));
        p2.add(punchDAO.find(6112));
        p2.add(punchDAO.find(6118));
        p2.add(punchDAO.find(6129));
        p2.add(punchDAO.find(6241));
        p2.add(punchDAO.find(6299));
        
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
    @Test
    public void testFindPunchListRange3() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamps and Badge Objects for Punch List */
        
        LocalDate start = LocalDate.of(2018, Month.AUGUST, 1); 
        LocalDate end = LocalDate.of(2018, Month.AUGUST, 9); 

        Badge b = badgeDAO.find("4E6E296E");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, start, end);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(147));
        p2.add(punchDAO.find(237));
        p2.add(punchDAO.find(382));
        p2.add(punchDAO.find(486));
        p2.add(punchDAO.find(492));
        p2.add(punchDAO.find(583));
        p2.add(punchDAO.find(600));
        p2.add(punchDAO.find(660));
        p2.add(punchDAO.find(718));
        p2.add(punchDAO.find(807));
        p2.add(punchDAO.find(834));
        p2.add(punchDAO.find(952));
        
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
    @Test
    public void testFindPunchListRange4() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamps and Badge Objects for Punch List */
        
        LocalDate start = LocalDate.of(2018, Month.AUGUST, 1); 
        LocalDate end = LocalDate.of(2018, Month.AUGUST, 3); 

        Badge b = badgeDAO.find("398B1563");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, start, end);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(161));
        p2.add(punchDAO.find(232));
        p2.add(punchDAO.find(313));
        p2.add(punchDAO.find(350));
        p2.add(punchDAO.find(400));
        p2.add(punchDAO.find(478));
        
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
   
    @Test
    public void testFindPunchListRange5() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamps and Badge Objects for Punch List */
        
        LocalDate start = LocalDate.of(2018, Month.SEPTEMBER, 10); 
        LocalDate end = LocalDate.of(2018, Month.SEPTEMBER, 13); 

        Badge b = badgeDAO.find("29C03912");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, start, end);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(3886));
        p2.add(punchDAO.find(4004));
        p2.add(punchDAO.find(4023));
        p2.add(punchDAO.find(4144));
        p2.add(punchDAO.find(4223));
        p2.add(punchDAO.find(4293));
        p2.add(punchDAO.find(4338));
        p2.add(punchDAO.find(4437));
        
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
    
}
