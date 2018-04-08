package lab_project;

import java.util.ArrayList;

public class Company {
    String companyName = null;
    int companyNumber = 0;
    String companyAddress = null;
    
    ArrayList<App> companyApp = new ArrayList<>();
    
    Company (String name,int number,String address){
        companyName = name;
        companyNumber = number;
        companyAddress = address;
    }
    
    public void setCompanyApp(ArrayList<App> companyApp) {
        this.companyApp = companyApp;
        
    }
    
    public ArrayList<App> getCompanyApp() {
        return companyApp;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }
    
    public String getCompanyAddress() {
        return companyAddress;
    }
    
    public void setCompanyNumber(int companyNumber) {
        this.companyNumber = companyNumber;
    }
    
    public int getCompanyNumber() {
        return companyNumber;
    }
}