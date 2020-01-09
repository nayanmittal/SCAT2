package InnovativeProduct.OSUpgrade;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

 
/* Changed it to get Selected test item from UI,File Name of sheet1,sheet2 will be decided accordingly.
 * 
 */

public class AppiumParallelExecution implements Runnable {
   
	static String path;
	static String testItem;
    String port;
    String udid;
    String deviceName;
    String osVersion;
    String packageName,activityName;
    static boolean end=false;

   
    public AppiumParallelExecution(String portNumber, String udid,String deviceName,String osVersion) 
    {
        this.port = portNumber;
        this.udid = udid;
        this.deviceName=deviceName;
        this.osVersion=osVersion;
    }
   
    AndroidDriver<AndroidElement> driver;
    DesiredCapabilities capabilities = new DesiredCapabilities();
   
   
    public void openAppAndPerformSomeActions() throws MalformedURLException, IOException, InterruptedException {
        capabilities.setCapability("deviceName", "My Mobile Device");
        capabilities.setCapability("udid", udid);
        capabilities.setCapability("clearSystemFiles","true");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true); //Camera_Setting_Comparison
        if(testItem.equalsIgnoreCase("Camera_Setting_Comparison"))
        {
        	System.out.println("checkpoint 1");
        	packageName="com.sec.android.app.camera";
        	activityName="com.sec.android.app.camera.Camera";
        }
        else {
        	packageName="com.android.settings";
        	activityName="com.android.settings.Settings";
        }
        
        capabilities.setCapability("appPackage",packageName);
        capabilities.setCapability("appActivity",activityName);

        try {
            driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:"+ port +"/wd/hub"), capabilities);
            Thread.sleep(10000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       //new MessageTest16(driver).enterText("C:\\Users\\nayan.mittal\\Desktop\\Data\\Result\\",udid,deviceName,osVersion);
        System.out.println("path is  "+path);

        if(testItem.equalsIgnoreCase("Camera_Setting_Comparison"))
        	new MessageTest18(driver).enterText(path,udid,deviceName,osVersion);
        else if(testItem.equalsIgnoreCase("Setting_Comparison"))
        	  new SettingTest101(driver).enterText(path,udid,deviceName,osVersion);

    }
   
    
    
    public static void startParallelExecution(String Path,String testItem)
    {
    	try {
    	String sheetName1,sheetName2;	
    	AppiumParallelExecution.path=Path;	
    	AppiumParallelExecution.testItem=testItem;	
    	 System.out.println("path is  "+path);
    	// add code to get device information like getting adb id,imei & device number.
    	//add code to launch 2 appium  server automatically with mentioned  ports and other details.
    
		DeviceConfiguration gd = new DeviceConfiguration();
		gd.startADB();
		HashMap<String,String> details=new HashMap<String,String>();
		details=gd.getDevices();
		//gd.stopADB();
		System.out.println("Devices details are "+details);
    	

        Runnable r1 = new AppiumParallelExecution("5000",details.get("deviceID1"),details.get("deviceName1"),details.get("osVersion1")); //device id of first mobile device
        Runnable r2 = new AppiumParallelExecution("4723",details.get("deviceID2"),details.get("deviceName2"),details.get("osVersion2")); //device id of second mobile device
        sheetName1=path+"\\"+details.get("deviceName1")+"_OS_"+details.get("osVersion1")+"_"+details.get("deviceID1")+"_"+AppiumParallelExecution.testItem+".xlsx";
        sheetName2=path+"\\"+details.get("deviceName2")+"_OS_"+details.get("osVersion2")+"_"+details.get("deviceID2")+"_"+AppiumParallelExecution.testItem+".xlsx";
        		
        Thread t1=new Thread(r1);
        t1.start();
        Thread t2=new Thread(r2);
        t2.start();
        
        
        while(true)
       	{   
        if(t1.isAlive()==false && t2.isAlive()==false)
        {
        	System.out.println("Both thread terminated, Now Excel comparison going on  ");
        	
        	if(testItem.equalsIgnoreCase("Camera_Setting_Comparison"))
        	new ExcelCompareSheetWise4(sheetName1,sheetName2,details.get("deviceName1"),details.get("deviceName2"),details.get("osVersion1"),details.get("osVersion2"));
        	else if(testItem.equalsIgnoreCase("Setting_Comparison"))
        	new ExcelCompareSheetWise5(sheetName1,sheetName2,details.get("deviceName1"),details.get("deviceName2"),details.get("osVersion1"),details.get("osVersion2"));	
        	
        	System.out.println("Excel comparison done    ");
        	end=true;
        	System.out.println("value of \"end\" varilabe set to  "+end);
        	break;
        }
        }  
   
    	}
    	catch(Exception e) 
    	{
    	e.printStackTrace();}
   
    }
   
    /*
    public static void main(String args[]) throws Exception {
    	
    	DeviceConfiguration gd = new DeviceConfiguration();
		gd.startADB();
		HashMap<String,String> details=new HashMap<String,String>();
		details=gd.getDevices();
		//gd.stopADB();
		System.out.println("Devices details are "+details);

        Runnable r1 = new AppiumParallelExecution("5000",details.get("deviceID1"),details.get("deviceName1"),details.get("osVersion1")); //device id of first mobile device
        Runnable r2 = new AppiumParallelExecution("4723",details.get("deviceID2"),details.get("deviceName2"),details.get("osVersion2")); //device id of second mobile device
        new Thread(r1).start();
        new Thread(r2).start();
    }
 
    */
    
    public void run() {
        try {
			openAppAndPerformSomeActions();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}