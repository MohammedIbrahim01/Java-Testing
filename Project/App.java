package lab_project;

public class App {
    String appName = null;
    int appSize = 0;
    String appType = null;
    
    App(String name, int size, String type) {
        appName = name;
        appSize = size; 
        appType = type;
    }
    
    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    public String getAppName() {
        return appName;
    }
    
    public void setAppSize(int appSize) {
        this.appSize = appSize;
    }
    
    public int getAppSize() {
        return appSize;
    }
    
    public void setAppType(String appType) {
        this.appType = appType;
    }
    
    public String getAppType() {
        return appType;
    }
}