package model;

/**
 * @author Piano Hhagens
 */

public class countriesModel {
    private int country_id;
    private String countryName;
    private int division_ID;
    private String diviName;

    //  This method is to create a constructor */
    public countriesModel(int country_id, String countryName, int division_ID, String diviName) {
        this.country_id = country_id;
        this.countryName = countryName;
        this.division_ID = division_ID;
        this.diviName = diviName;
    }

    //  These are getters and setters */
    public int getCountry_id(){return country_id;}
    public String getCountryName(){return countryName;}

    public int getDivision_ID(){return division_ID;}
    public String getDiviName(){return diviName;}

    //  This method is how it display objects */
    @Override
    public String toString(){
        return (country_id + " " + countryName);
    }
}
