# Registration System
##
## Description
  This is a web based registration system via which a user can register and search the registrations. 
## 
  For instance, a company is conducting mandatory training classes for all of its employees; these training classes will be divided into four sessions. This application will be used at the Registration Desk to retrieve the list of attendees who have already registered for each session. There will also be a registration screen to allow last-minute attendees to register at the desk.
##
The front end is written in HTML and the server-side operations are written in Java (Java servlets) and JavaScript/jQuery (AJAX methods). When the user register, the data associated with the user namely "firstname", "lastname", "displayname", and "section number" are stored in the database using the "HTTP POST" method. When the user searches for the registrations, the data stored in the database is displayed using the "HTTP GET" method.
## 
  When the user launches this application and searches for the registrations, the webpage looks like below,
  
  ![picture](registrationdesk1.PNG)
  
  And the user registration interface looks like below,
  
  ![picture](registrationdesk3.PNG)
 
##
## Structure
  The directory structure of the source code is as follows:

    project root     (root directory of project, "registration-system")
               |
                - src             (root directory of the source code)
               |    |           
               |    - java
               |    |     |
               |    |     - jsu.edu.mcis    
               |    |     |            |
               |    |     |            - Database.java (Database class which interacts with the database)
               |    |     |            |
               |    |     |            - RegistrationServlet.java (Java Servlet which implements HTTP GET and POST methods)
               |
                - web             (root directory of the web code)
                    |
                    - index.html (Main weppage where an user can search the registrations)
                    |    
                    - register.html (Registration page where an user can register) 
                    |
                    - CSSFILE.css (CSS file which adds decoration) 
                    | 
                    - scripts
                    |       | 
                    |       - jquery-3.4.1.min.js (jQuery file)
                    |       |
                    |       - Project1.js (JavaScript file)
                    |
                    - META-INF
                    |        |
                    |        - context.xml (It adds resource for database pooling) 
                    
##
##  Important Classes and their Methods
##
### Database Class
   Database class is responsible for connecting the database (registration_db.sql). It either gets the registrations data from the database or inserts the registrations into the database. It has following methods;
   ##
   i) **public String getDataAsTable(int session)** = It retrieves all of the registrations that fall within a given session and adds those registrations to the table and return to the user as a string.
   ##
   ii) **public String putDataonDatabase(String firstname, String lastname, String displayname, int session)** = It adds the registration information: firstname, lastname, displayname, and session into the database. When the data is added in the database, the confirmation code is created and put in the JSONObject and returned to the user as a JSONString.
##
### RegistrationServlet Class
   It is responsible for displaying the registrations of the database in the webpage using HTTP GET method and for adding the new registration information entered by an user to the database using HTTP POST method.
   ##
   i) **protected void doGet(HttpServletRequest request, HttpServletResponse response)** = It displays the registrations associated with a particular session in the webpage using Database's getDataAsTable() method.
   ##
   ii) **protected void doPost(HttpServletRequest request, HttpServletResponse response)** = It adds the registrations that the user entered in the registration form to the database using Database's putDataonDatabase() method.

##
## Configuring Database for the Database Pooling
   To run this application, you'll need to import the application database (**registration_db.sql**). After importing the database, you'll need to create database account for it, and grant this account the appropriate permissions in MySQL. To do this, please run the following command from an SQL client while logged in to MySQL as root:
##
         source C:\USER\Desktop\registration_db.sql ( It assumes the location of sql file is in Desktop. It may be different for you.)
         
         create user 'db_user'@'localhost' identified by 'password';
         grant all on registration_db.* to 'db_user'@'localhost';
         flush privileges;
##
   The **"db_user"** is for the database pool.
##
## Running this Project
   To run this project, you'll need MySQL, Java (particularly Java 8), Apache Tomcat Server, and Netbeans. If you don't have these tools, you can install them as described here (https://github.com/sbanjara/RequiredTools-Installation). Once you have all these required tools, you can clone this repository by running this command,
##
            git clone https://github.com/sbanjara/registration-system
##
   After cloning this project in your workstation, you can create a web application project in Netbeans using existing project files (i.e. the cloned files).
##

