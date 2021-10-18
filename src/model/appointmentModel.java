package model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Piano Hhagens
 */
public class appointmentModel{
    protected int Appointment_ID;
    protected int Customer_ID;
    protected int Contact_ID;
    protected int User_ID;
    protected String Title;
    protected String Description;
    protected String Location;
    protected String Type;
    protected LocalDateTime Start;
    protected LocalDateTime End;
    protected LocalTime busHours;


    //  This method is to create a constructor for appointment app page */
    public appointmentModel(int Appointment_ID, String Title,  String Description, String Location, String Type, LocalDateTime Start, LocalDateTime End, int Customer_ID, int Contact_ID, int User_ID){
        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        this.Start = Start;
        this.End = End;
        this.Customer_ID = Customer_ID;
        this.Contact_ID = Contact_ID;
        this.User_ID =  User_ID;
    }

    // This method is to create a constructor for report page */
    public appointmentModel(int appointment_id, String title, String type, String description, LocalDateTime start, LocalDateTime end, int customer_id) {
        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Type = Type;
        this.Description = Description;
        this.Start = Start;
        this.End = End;
        this.Customer_ID = Customer_ID;
    }

    //  These are getters and setters */
    public LocalDate getDate(){return Start.toLocalDate();}
    public LocalTime getStartTime(){return Start.toLocalTime();}

    public LocalTime getEndTime(){return End.toLocalTime();}
    public int getAppointments_id(){return this.Appointment_ID;}

    public String getTitle(){return this.Title;}

    public String getDescription(){return this.Description;}

    public String getLocation(){return this.Location;}
    public String getType(){return this.Type;}

    public LocalDateTime getStart(){return this.Start;}
    public LocalDateTime getEnd(){return this.End;}

    public String getStartHH(){return Start.format(DateTimeFormatter.ofPattern("hh"));}
    public String getStartMM(){return Start.format(DateTimeFormatter.ofPattern("mm"));}
    public String getStartA(){return Start.format(DateTimeFormatter.ofPattern("a"));}

    public String getEndHH(){return End.format(DateTimeFormatter.ofPattern("hh"));}
    public String getEndMM(){return End.format(DateTimeFormatter.ofPattern("mm"));}
    public String getEndA(){return End.format(DateTimeFormatter.ofPattern("a"));}

    public int getCustomer_id(){return this.Customer_ID;}
    public int getContact_ID(){return this.Contact_ID;}
    public int getUser_ID(){return this.User_ID;}
}
