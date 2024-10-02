package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Shift;
import java.sql.*;

public class ShiftDAO {
    private static final String QUERY_FIND = "SELECT * FROM badge WHERE id = ?";
    private final DAOFactory daoFactory;
    
    ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;

    }
    
    public Shift find(String id){
        Shift shift=null;
       
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            Connection conn = daoFactory.getConnection();
            //More Code here
        }
        //More Code here
        return shift;
    }

}
