package model;

import java.util.List;
/**
 * @author Piano Hhagens
 */
public class customerModel{

    protected List<String> custmoerDBcolumns;

    protected int customer_id;
    protected int diviID;
    protected int countryID;
    protected String division;
    protected String postalCode;
    protected String phone;
    protected String Customer_Name;
    protected String address;
    protected String country;

    //  This method is to create a constructor */
    public customerModel(int customer_id, String Customer_Name, String phone, String address, String postalCode, String division, String country) {
        this.customer_id = customer_id;
        this.Customer_Name = Customer_Name;
        this.phone = phone;
        this.address = address;
        this.postalCode = postalCode;
        this.division = division;
        this.country = country;
  }

    //  These are getters and setters */
    public int getCustomer_id() { return customer_id; }
    public void setCustomer_id(int customer_id) {this.customer_id = customer_id; }

    public String getCustomerName() {return Customer_Name;}
    public void setCustomerName(String customerName) { this.Customer_Name = customerName; }

    public String getPhone() {return phone;}
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPostalCode() {return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public int getDiviID() {return diviID; }

    public String getDivision() { return division; }

    public void setDiviID(int diviID) { this.diviID = diviID;}
    public void setDivision(String division) {this.division = division;}

    public void setCountry(String country) { this.country = country;  }

    public int getCountryID() {return countryID; }
    public void setCountryID(int countryID) {this.countryID = countryID; }
    public String getCountry() { return country;  }

    // This method is how it display objects */
    @Override
    public String toString(){//this is to giving space between id/country to the country combobox
        return (customer_id + " " + Customer_Name);
    }
}
