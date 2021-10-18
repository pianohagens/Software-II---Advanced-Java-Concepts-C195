package model;


/**
 * @author Piano Hhagens
 */
public class contactModel {
    protected int Contact_ID;
    protected String Contact_Name;
    protected String Email;

    //  This method is to create a constructor */
    public contactModel(int Contact_ID, String Contact_Name, String Email) {
        this.Contact_ID = Contact_ID;
        this.Contact_Name = Contact_Name;
        this.Email = Email;
    }

    //  These are getters and setters */
    public int getContact_ID(){return Contact_ID;}
    public void setContact_ID(int Contact_ID){this.Contact_ID = Contact_ID;}

    public String getContact_Name(){return Contact_Name;}
    public void setContact_Name(String Contact_Name){this.Contact_Name = Contact_Name;}

    public String getEmail(){return Email;}
    public void setEmail(String Email){this.Email = Email;}

    // This method is how it display objects */
    @Override
    public String toString(){//this is to giving space between id/country to the country combobox
        return (Contact_ID + " " + Contact_Name);
    }
}
