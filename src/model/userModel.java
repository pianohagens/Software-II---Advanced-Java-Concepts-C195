package model;

/**
 * @author Piano Hhagens
 */

public class userModel {
    protected int user_ID;
    protected String user_Name;

    //  This method is to create a constructor */
    public userModel(int user_ID, String user_Name) {
        this.user_ID = user_ID;
        this.user_Name = user_Name;
    }

    //  These are getters and setters */
    public int getUser_ID(){return user_ID;}
    public void setUser_ID(int Contact_ID){this.user_ID = user_ID;}

    public String getUser_Name(){return user_Name;}
    public void setUser_Name(String user_Name){this.user_Name = user_Name;}

    protected static int loggedUserID = 1;
    public static int getLoggedUserID(){
        return loggedUserID;
    }
    public static void setLoggedUserID(int User_ID){
        loggedUserID = User_ID;
    }

    protected static String loggedUser = "loadUser";
    public static String getLoggedUser(){
        return loggedUser;
    }
    public static void setLoggedUser(String user){
        loggedUser = user;
    }

    //  This method is how it display objects */
    @Override
    public String toString(){//this is to giving space between id/country to the country combobox
        return (user_ID + " " + user_Name);
    }
}
