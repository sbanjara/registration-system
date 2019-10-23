package jsu.edu.mcis;

import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Database {
    
    private Connection getConnection() {
        
        Connection conn = null;
        
        try {
            
            Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();
            
        }        
        catch (Exception e) { e.printStackTrace(); }
        
        return conn;

    }
    
    public String getDataAsTable(int session) throws ServletException, IOException, SQLException {
        
        String table = "";
        Connection connection = getConnection();
        
        String query;
        boolean hasresults;
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
       
        String tableheading;
        String tablerow; 
        String key;
        String value;
        
        try {
                       
            query = "SELECT * FROM registrations WHERE sessionid = ?";
     
            pstatement = connection.prepareStatement(query);
            if(session > 0 && session < 5)
                pstatement.setString(1, "' " + session + " '");
            
            hasresults = pstatement.execute();
            
            while ( hasresults || pstatement.getUpdateCount() != -1 ) {  
                
                if ( hasresults ) {

                    resultset = pstatement.getResultSet();

                    metadata = resultset.getMetaData();

                    int numberOfColumns = metadata.getColumnCount();

                    table += "<table border=\"1\">";
                    tableheading = "<tr>";

                    System.out.println("*** Number of Columns: " + numberOfColumns);

                    for (int i = 1; i <= numberOfColumns; i++) {

                        key = metadata.getColumnLabel(i);

                        tableheading += "<th>" + key + "</th>";

                    }

                    while (resultset.next()) {

                        tablerow = "<tr>";

                        for (int i = 1; i <= numberOfColumns; i++) {

                            value = resultset.getString(i);

                            if (resultset.wasNull()) {
                                tablerow += "<td></td>";
                            }

                            else {
                                tablerow += "<td>" + value + "</td>";
                            }

                        }

                        tablerow += "</tr>";

                        table += tablerow;

                    }

                    table += "</table><br />";

                }

            }
               
        }
        
        catch (Exception e) {
            System.out.println(e.toString());
        }
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
            if (connection != null) { connection.close(); }
            
        }
        
        return table;
        
    }
    
    public String putDataonDatabase(String firstname, String lastname, String displayname, int session) throws SQLException {
        
        int id = 0;
        String query;
        String result = "";
        boolean hasresults;
                
        Connection conn = getConnection();
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        JSONObject json = new JSONObject();
        
        try {
            
            query = "INSERT INTO registrations(firstname, lastname, displayname, sessionid)"
                    + "VALUES('" + firstname + "', '" + lastname + "', '" + displayname + "', '" + session + "')";
            pstatement = conn.prepareStatement(query);
            
            query = "SELECT* FROM registrations WHERE firstname = ?, lastname = ?, displayname = ?, sessionid = ?";
            
            pstatement = conn.prepareStatement(query);
            if(firstname != null && lastname != null && displayname != null && session > 0)
                pstatement.setString(4,"'" + firstname + "', '" + lastname + "', '" + displayname + "', '" + session + "'");
            
            hasresults = pstatement.execute();
            
            if(hasresults) {
                
                resultset = pstatement.getResultSet();
                id = resultset.getInt("id");
                 
            }
            
            String code = String.format("%07d",id);
            String registration_code = "R";
            registration_code += code;
       
            json.put("registration_code", registration_code);
            json.put("displayname", displayname);
            
            result = JSONValue.toJSONString(json);
            
        }
        
        catch (Exception e) {
            System.out.println(e.toString());
        }
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
            if (conn != null) { conn.close(); }
            
        }
        
        return result.trim();
    
    }
       
}