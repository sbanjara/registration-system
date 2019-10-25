
package jsu.edu.mcis;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Registration", urlPatterns = {"/registration"})
public class RegistrationServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
 
        Database db = new Database();
        
        try {
            
            out.println(db.getDataAsTable(Integer.parseInt(request.getParameter("session"))));
            
        } 
        catch (SQLException ex) {
            
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Database db = new Database();
        
        try {
            
            out.println(db.putDataonDatabase(request.getParameter("firstname"), request.getParameter("lastname"),
                    request.getParameter("displayname"),Integer.parseInt(request.getParameter("session"))));
            
        } 
        catch (SQLException ex) {
            
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }

   
    @Override
    public String getServletInfo() {
        return "Servlet to handle registration.";
    }

}
