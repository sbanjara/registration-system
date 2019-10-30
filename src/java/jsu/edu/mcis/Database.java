package jsu.edu.mcis;

import java.io.IOException;
import java.sql.*;
import javax.naming.*;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.json.simple.*;

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
                pstatement.setInt(1,session);
            
            hasresults = pstatement.execute();

            if ( hasresults ) {

                resultset = pstatement.getResultSet();

                metadata = resultset.getMetaData();

                int numberOfColumns = metadata.getColumnCount();

                table += "<table border=\"1\">";
                tableheading = "<tr>";

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
        
        catch (Exception e) {
            e.printStackTrace();
        }
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
            if (connection != null) { connection.close(); }
            
        }
        
        return table;
        
    }
    
    public String putDataonDatabase(String firstname, String lastname, String displayname, int session) throws SQLException {
        
        int id = 0, result = 0;
        String query;
        String results = "";
      
        Connection conn = getConnection();
        PreparedStatement pstatement = null;
        ResultSet keys = null;
        JSONObject json = new JSONObject();
        
        try {
            
            query = "INSERT INTO registrations(firstname, lastname, displayname, sessionid)"
                    + "VALUES(?,?,?,?)";
            
            pstatement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            
            pstatement.setString(1,firstname);
            pstatement.setString(2, lastname);
            pstatement.setString(3, displayname);
            pstatement.setInt(4,session);
            
            result = pstatement.executeUpdate();
            
            if(result == 1) {
                
                keys = pstatement.getGeneratedKeys();
                if(keys.next()) {
                    id = keys.getInt(1);
                }
                 
            }
            
            String code = String.format("%06d",id);
            String registration_code = "R";
            registration_code += code;
       
            json.put("registration_code", registration_code);
            json.put("displayname", displayname);
            
            results = JSONValue.toJSONString(json);
            
        }
        
        catch (Exception e) {
            System.out.println(e.toString());
        }
        
        finally {
            
            if (keys != null) { try { keys.close(); keys = null; } catch (Exception e) {} }
            
            if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
            if (conn != null) { conn.close(); }
            
        }
        
        return results.trim();
    
    }
       
}