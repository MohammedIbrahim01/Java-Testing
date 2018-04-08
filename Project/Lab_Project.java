package lab_project;

import java.util.ArrayList;
import java.util.Scanner;
public class Lab_Project {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Company> Companies = new ArrayList<>();
        
        System.out.println("Choose a number from 1 to 4");
        System.out.println("Enter 1 to add a company");
        System.out.println("Enter 2 to add an app");
        System.out.println("Enter 3 to search for a company");
        System.out.println("Enter 4 to search for an app");
        System.out.println("Enter any other number to exit");
        
        do {
            int number = input.nextInt();
            if (number == 1) {
                for (int i=0; i<30; i++)
                System.out.println();
                
                System.out.println("Enter name of Company to add");                
                String name = input.next();
                
                System.out.println("Enter address of Company to add");
                String address=input.next();
                
                System.out.println("Enter phone of Company to add");
                int num =input.nextInt();
                
                Companies.add(new Company(name,num,address));
                System.out.println("Company App: " +name+ "\nCompany Phone: " +num+ "\nCompany Address: " +address);
                System.out.println(name+ " added successfully ");
            }
            else if (number == 2) {    
                for (int i=0; i<30; i++)
                System.out.println();
                
                System.out.println("Enter name of Company and name of the App to add");
                
                   for(Company C : Companies){      
                   System.out.println(C.companyName);
            }
                String compName = input.next();
                
                System.out.println("Enter name of app to add");
                String appName = input.next();
                
                System.out.println("Enter size of app to add");
                int appSize = input.nextInt();
                
                System.out.println("Enter type of app to add");
                String appType = input.next();
                boolean found = false;
                
                for (int i = 0; i < Companies.size(); i++) {
                    if (compName.equals(Companies.get(i).getCompanyName())) {
                        Companies.get(i).getCompanyApp().add(new App(appName,appSize,appType));
                        found = true;
                        System.out.println("App Name: " +appName+ "\nApp Size: " +appSize+ "\nApp Type: " +appType);
                        System.out.println(appName+ " added successfully to " +compName+ " company ");
                        break;
                    }
                }
                if (!found) {
                    System.out.println("We don't have company in that name so we didn't add the app, Try again");
                }
            }
            else if (number == 3) {
                for (int i=0; i<30; i++)
                System.out.println();
                
                System.out.println("Enter the company to search about its apps");
                String companyname = input.next();
                boolean Done = false;
                
                for (Company company : Companies) {
                    if (companyname.equals(company.getCompanyName())) {
                        Done = true;
                        System.out.println("Company Name: " + company.getCompanyName());
                        for (int j = 0; j < company.getCompanyApp().size(); j++) {
                            System.out.println("App No." + (j+1) + ": " + company.getCompanyApp().get(j).getAppName());
                        }
                        break;
                    }
                }
                if (!Done) {
                    System.out.println("We didn't find this company");
                }
            }
            else if (number == 4) {
                for (int i=0; i<30; i++)
                System.out.println();
                
                System.out.println("Enter the app to search about its company");
                String appName = input.next();
                boolean appFound = false;
                
                for (Company company : Companies) {
                    for (App app : company.getCompanyApp()) {
                        if (appName.equals(app.getAppName())) {
                            appFound = true;
                            System.out.println("We found " +appName+ " in " +company.getCompanyName()+ " company");
                        }
                    }
                }
                if (!appFound) {
                    System.out.println("Sorry, there is no app in that name in our companies");
                }
            }
            else {
                break;
            }
            System.out.println("Choose a number from 1 to 4");
            System.out.println("Enter 1 to add a company");
            System.out.println("Enter 2 to add an app");
            System.out.println("Enter 3 to search for a company");
            System.out.println("Enter 4 to search for an app");
            System.out.println("Enter any other number to exit");
        }
            while (input.hasNextInt());
            System.out.println();
    }
}