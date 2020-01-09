package InnovativeProduct.OSUpgrade;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.Test;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Utilities {

	AndroidDriver<AndroidElement> driver;
	public Utilities(AndroidDriver<AndroidElement> driver)
	{
	  this.driver=driver;
	}
	
	
	public void subMenu(FileOutputStream fos) throws IOException
	{
	
		/*
		java.util.List<AndroidElement> ae2=driver.findElementsByClassName("android.widget.CheckedTextView");
		java.util.List<AndroidElement> ae3=driver.findElementsByClassName("android.widget.TextView");
		java.util.List<AndroidElement> ae4=driver.findElementsByClassName("android.widget.Switch");
        ae2.addAll(ae3);
        ae2.addAll(ae4);
        */
        
        java.util.List<AndroidElement> ae2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch' or @class='android.widget.CheckedTextView']");
		
        
        
        
        
		 //java.util.List<AndroidElement> ae2= (List<AndroidElement>) driver.findElementByXPath("//*[@class='android.widget.CheckedTextView' or @class='android.widget.TextView' or @class='android.widget.Switch']");
     		for(AndroidElement submenu:ae2) {
				
				//if(submenu.isSelected()) not working . 
				// below condition done to check Wether the element is just plain text or some action can be performed on it.
				if(submenu.getAttribute("checked").equalsIgnoreCase("true"))
				{
					System.out.println("---->"+submenu.getText()+"->Checked");
					//System.out.println(submenu.getAttribute("checked"));
				}
				else
				{
					System.out.println("---->"+submenu.getText());
					//System.out.println(submenu.getAttribute("checked"));
				}
				
			fos.write(submenu.getText().getBytes());
				fos.write('\n');
			}
			
			
	}

	

	
	
	
	public void scrollToText(String text) throws IOException, InterruptedException
	{

	     driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));");
	     Thread.sleep(3000);
	}
    
	
	public void saveToExcelFile() 
	{
		
	}

	
	
	
	public void saveToTxtFile(String data) throws IOException
	{
		//char c='a';
		File f=new File("D:\\Drive E\\automation tool\\OSUpgrade\\src\\main\\java\\Resources\\CameraSettigs.txt");
		FileOutputStream fos=new FileOutputStream(f);
		//fos.write(c);	
		fos.write(data.getBytes());	
		fos.close();
	}
	
	//not working .Probably depricated or for IOS 
	public boolean scrollDown()
	{
		
		try {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		
		
		
		scrollObject.put("direction","down");
		
		
	//	js.executeScript("mobile: scroll", scrollObject);
		js.executeScript("mobile:swipe", scrollObject);
		System.out.println("Swipt down successfully done");
		return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Swipt down not done");
		}
		return false;
	}
	

	
	
	
	
	
	
	//working 
	public boolean swipeDown2()
	{
		
		try
		{
		
		List<AndroidElement> lt1=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch']");
		
		//finding last element of list
		String s1=lt1.get(lt1.size()-1).getText();
		//System.out.println("before scroll"+s1); 
		Dimension dim = driver.manage().window().getSize();
		int height = dim.getHeight();
		int width = dim.getWidth();
		//int x = width/2;
		int top_y = (int)(height*0.70);
		int bottom_y = (int)(height*0.30);
		//System.out.println("coordinates :" + x + "  "+ top_y + " "+ bottom_y);
		
		
		//scrollStart is bottom point and ScrollEnd is top point.
		//new TouchAction((PerformsTouchActions) driver).press(PointOption.point(0, scrollStart)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(0,scrollEnd)).release().perform();

		new TouchAction((PerformsTouchActions) driver).press(PointOption.point(0,top_y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(0,bottom_y)).release().perform();
	
		 Thread.sleep(2000);
		List<AndroidElement> lt2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch']");
		String s2=lt2.get(lt2.size()-1).getText();
		//System.out.println("after scroll"+s2);
		
		if(s1.equalsIgnoreCase(s2))
		return false;
		else
		return true;
		
		}
		catch(Exception e)
		{
			System.out.println("Inside swipe down exception");
			return false;
		}
		
		
	}
	
	
	
	
	public void scrollLeft() throws InterruptedException
	{
		//System.out.println("before scroll"+s1); 
		Dimension dim = driver.manage().window().getSize();

		int width = dim.getWidth();
	    int height=dim.getHeight();
		int XX = (int)(width*0.90);
		int xx = (int)(width*0.10);
		//System.out.println("coordinates :" + x + "  "+ top_y + " "+ bottom_y);
		int anchor=(int)(height*0.50);
		new TouchAction((PerformsTouchActions) driver).press(PointOption.point(xx,anchor)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(XX,anchor)).release().perform();
		 Thread.sleep(2000);
		}
	
	public void scrollRight() throws InterruptedException
	{
		//System.out.println("before scroll"+s1); 
		Dimension dim = driver.manage().window().getSize();

		int width = dim.getWidth();
	    int height=dim.getHeight();
		int XX = (int)(width*0.90);
		int xx = (int)(width*0.10);
		//System.out.println("coordinates :" + x + "  "+ top_y + " "+ bottom_y);
		int anchor=(int)(height*0.50);
		new TouchAction((PerformsTouchActions) driver).press(PointOption.point(XX,anchor)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(xx,anchor)).release().perform();
		 Thread.sleep(2000);
		}
	
	
	
	


	
}
