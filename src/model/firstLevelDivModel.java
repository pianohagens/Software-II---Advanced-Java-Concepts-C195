package model;


/**
 * @author Piano Hhagens
 */
public class firstLevelDivModel {
    private int division_id;
    private String diviName;
    private int country_ID;


    //  This method is to create a constructor */
    public firstLevelDivModel(int division_id, String diviName, int country_ID) {
        this.division_id = division_id;
        this.country_ID = country_ID;
        this.diviName = diviName;
    }

    //  These are getters and setters */
    public int getDivision_id(){return division_id;}
    public void setDivision_id(int division_id){this.division_id = division_id;}

    public String getDiviName(){return diviName;}
    public void setDiviName(String diviName){this.diviName = diviName;}

    public int getCountry_ID(){return country_ID;}
    public void setCountry_ID(int country_ID){this.country_ID = country_ID;}

    //  This method is how it display objects */
    @Override
    public String toString(){//this is to format the combobox
        return ( division_id + "  " + diviName);
    }
}
