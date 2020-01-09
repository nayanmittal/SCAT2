package InnovativeProduct.OSUpgrade;


import java.io.IOException;


import org.testng.annotations.Test;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class PrintFormat extends Base{

	static AndroidDriver<AndroidElement> driver;
	
	@Test
	public void pBold() throws IOException, InterruptedException
	{

		
	/*
	Runtime r=Runtime.getRuntime();
	r.exec("Notepad");
	r.exec("adb shell pm list packages");
	Thread.sleep(3000);
	//r.exec("adb reboot");
	System.out.println(r.exec("D:\\Drive E\\automation tool\\OSUpgrade\\src\\main\\java\\InnovativeProduct\\OSUpgrade\\packagesList.bat"));
    Thread.sleep(3000);
	*/
		
		driver=capabilities();
	
		Thread.sleep(3000);
		if(driver.findElementsByXPath("//*[@text='Switch to front camera' or @text='Switch camera']").size()>0) {
			driver.findElementsByXPath("//*[@text='Switch to front camera' or @text='Switch camera']").get(0).click();
		
		}
		Thread.sleep(3000);
		driver.pressKey(new KeyEvent(AndroidKey.HOME));
		Thread.sleep(2000);
		driver.startActivity(new Activity("com.sec.android.app.camera","com.sec.android.app.camera.Camera"));
	
	
	}

}
