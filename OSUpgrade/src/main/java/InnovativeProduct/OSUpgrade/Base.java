package InnovativeProduct.OSUpgrade;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class Base {
	
	
	public static AndroidDriver<AndroidElement> capabilities() throws MalformedURLException,IOException
	{
		

	AndroidDriver<AndroidElement>  driver;

		// TODO Auto-generated method stub
	 //File appDir = new File("src");
     //File app = new File(appDir, "ApiDemos-debug.apk");
     DesiredCapabilities capabilities = new DesiredCapabilities();
     
     capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
    // capabilities.setCapability(MobileCapabilityType.APP,"D:\\Drive E\\automation tool\\OSUpgrade\\src\\main\\java\\ApiDemos-debug.apk");
   //  capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
     capabilities.setCapability("clearSystemFiles","true");
     capabilities.setCapability(MobileCapabilityType.NO_RESET, true);   //No reset true will not clear the data ,false will clear the cache data.
     capabilities.setCapability("appPackage", "com.sec.android.app.camera");
     capabilities.setCapability("appActivity", "com.sec.android.app.camera.Camera");
     //capabilities.setCapability("autoAcceptAlerts", true); //it works only when no reset is set to false.Default is also false.so to handle it we will 
     
    
     
     driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	    
	return driver;
	}
	
	

}