## Software-II-Advanced-Java-Concepts-C195

WGU Software II: C195_Appointment_Schedule_App	

	Author: Piano Hagens	
	Contact information: phhagen@wgu.edu	
	Application version: 02	
	Date: 05/12/2021	

	IDE version: IntelliJ Community 2020.3.1 
	JDK of version 11: Java SE 11.0.2 
	JavaFX version compatible with JDK 11: JavaFX-SDK-11.0.9
	MySQL Workbench 8.0 CE https://dev.mysql.com/downloads/workbench/


The purpose of this application is to develop a GUI-based scheduling desktop application, in order to pass C195 performance assessment requirements. I named it C195_Appointment_Schedule_App.
 C195_Appointment_Schedule_App allows users to create customer records and schedule appointments for customers. This task has provided a MySQL database that does not allow to modify the database structure. All data of this app going to use that database includes user data, customer data, and appointment data.

### Directions for how to run the C195_Appointment Schedule App

	Steps:
	1. User Login =>  Home Page, Home page is the dashboard to Customer Record Page or to Schedule Appointment Page.
    To test the User login form, use following login Credentials:

            *Test case 1:
            UserName: test
            Password: test
        or
            *Test case 2:
            UserName: admin
            Password: admin

	2. Customer Record Page => On this panel of the app can perform can add/edit/delete customer records. Auto generate Customer ID, and provide Comboboxes that retrieve country data and divisions data according selected country name  from the database. Each input field has set validator according the field data type, and auto refresh page immediately.
	3. Schedule Appointment Page => This is the primary feature of the app, user can add/edit/delete appointments. On the input field, the app provide handy time option like Datapicker and Comboboxes for time slots, provide Comboboxes that retrieve users table data, contacts table data, customers table data from the database. There is a 15 minutes appointment reminder shows at the bottom of the page.
	4. Show Report Page->There are View Appointment Reports button on both Home page and the Appointment App page. Either one will take user to the report page, and then user can pull report by choosing month and type, contact, or user.

### Description (Appointment Reports):
	1.View Appointment Reports --> By selecting type and month, display the total number of customer appointments and selected type and selected month. 
	2.View Appointment Reports --> By selecting contact, display appointments that assigned to contact person. This report include appointment ID, title, type and description, start date and time, end date and time, and customer ID.
	3.View Appointment Reports --> By selecting user, display appointments that scheduled by user. This report include appointment ID, title, type and description, start date and time, end date and time, and customer ID.

	MySQL Connector driver version: mysql-connector-java-8.0.23

Lambda expressions locations:
1. homepageController.initialize
2. reportController.onUserReportCombo
