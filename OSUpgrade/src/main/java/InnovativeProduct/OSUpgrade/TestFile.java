package InnovativeProduct.OSUpgrade;

import java.awt.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.GsmCallActions;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
//import android.app.AlertDialog;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class TestFile extends Base {
	
	static AndroidDriver<AndroidElement> driver;
	//static Utilities u;
	File f;
	
	static FileOutputStream fos;
	
	@Test
	public void enterText() throws MalformedURLException, IOException, InterruptedException
	{
		
	//u=new Utilities(driver);
		f=new File("D:\\Drive E\\automation tool\\OSUpgrade\\src\\main\\java\\Resources\\CameraSettigs.txt");
		fos=new FileOutputStream(f);
		
		
		driver=capabilities();
		//driver.pressKeyCode(AndroidKeyCode.HOME);
		driver.pressKey(new KeyEvent(AndroidKey.HOME));
		

		driver.startActivity(new Activity("com.sec.android.app.camera","com.sec.android.app.camera.Camera"));

       

		
		System.out.print("");
		
		
		System.out.println(driver.findElementById("LAUNCH_SETTING_ACTIVITY").getAttribute("className"));
		String s3=driver.findElementById("LAUNCH_SETTING_ACTIVITY").getAttribute("resourceId");
		System.out.println(s3);
		
		
		//Utilities u4=new Utilities(driver);
		//for(int i=0;i<4;i++)
		//u4.scrollLeft();
		
		
		
		
		//String s="Night";  //not working
		//driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+s+"\"));").click();
		
		/*
		//printing all modes supported on launch screen
		java.util.List<AndroidElement> Modes=driver.findElementsByXPath("//*[@class='ShootingModeShortcutItem']");
		for(AndroidElement ae: Modes)
		{
		System.out.println("Modes are -->"+ae.getText());
		
		
		
		
		if(ae.getText().equalsIgnoreCase("Pro") || ae.getText().equalsIgnoreCase("Live focus") || ae.getText().equalsIgnoreCase("Photo") || ae.getText().equalsIgnoreCase("Video") || ae.getText().equalsIgnoreCase("Super Slow-mo")) 
		{
			try{
			 //System.out.println("check point 1");
			 driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+ae.getText()+"\"));").click();
			 //System.out.println("check point 2");
			 Thread.sleep(3000);
			 //driver.navigate().back();
			}
			catch(Exception e)
			{}
			
		}
	
		String s=ae.getText();
		
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+s+"\"));").click();
		
		Thread.sleep(3000);
		}
		*/
		
		
		
		
		driver.findElementById("LAUNCH_SETTING_ACTIVITY").click();
		System.out.println("====Inside Settings of camera=====");
		
		//java.util.List<AndroidElement> s1=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch']");
		
	
		//java.util.List<AndroidElement> s1=driver.findElements(By.xpath("//android.widget.FrameLayout//android.widget.RelativeLayout//android.widget.TextView"));
		
		//Query to get all the child of a node
		//java.util.List<AndroidElement> s1=driver.findElements(By.xpath("//android.widget.FrameLayout//android.widget.LinearLayout//*"));
		
		//query to get specific type of child .Used or query here(not working)
		//java.util.List<AndroidElement> s1=driver.findElementsByXPath("//android.widget.FrameLayout//android.widget.LinearLayout//android.widget.TextView" or "//android.widget.FrameLayout//android.widget.LinearLayout//android.widget.Switch"); need to check this query
		
		//Query to get particular type of elements 
		java.util.List<AndroidElement> s1=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch']");
		//java.util.List<AndroidElement> s1=driver.findElementsByXPath("//*[@class=\"android.widget.FrameLayout//android.widget.LinearLayout//android.widget.TextView\" or @class=\"android.widget.FrameLayout//android.widget.LinearLayout//android.widget.Switch\"]");
		AndroidElement lastEE=s1.get(s1.size()-2);
		AndroidElement lastE=s1.get(s1.size()-1);
		
		   //System.out.println(s1.indexOf(lastE));
		//System.out.println("first element is "+s1.get(0).getText());
		
		String SS=lastEE.getText();
		String ss=lastE.getText();
		//System.out.println("last element is +++"+lastE.getText());
		
				for(AndroidElement ae:s1) 
				System.out.println(ae.getText());
			 

	
		
	try {
	
		while(swipeDown())
		{
			int i=-1;
			//System.out.println("scroll done successfully");
			java.util.List<AndroidElement> newList=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch']");
			
			//for(AndroidElement aen:newList)
				//System.out.println("Origionally Element after scroll---"+aen.getText());
			
			//String ss=lastE.getText().toString(); // getting wrong value of laseE .
			
			//System.out.println("checkpoint 1 ");
			//System.out.println("checkpoint 1 "+ss);
			
			boolean matcher=false;
			boolean matcher2=false;
			for(AndroidElement aen:newList)
			{
				String nlText=aen.getText().toString();
				if(nlText.equalsIgnoreCase(SS))
				{
				i++;
				//System.out.println("1st Element matched");
				matcher=true;
				}
				else if(matcher && nlText.equalsIgnoreCase(ss))
				{
				i++;
				//System.out.println("2nd Element matched");
				matcher2=true;
				break;
				}
				
				else 
				i++;
				
				
			}
			
			//System.out.println("checkpoint 2 ");
			
			
			
			//System.out.println("Index is "+i);
			int tsize=newList.size();
			
			//java.util.List<AndroidElement> s2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch']");
			java.util.List<AndroidElement> s2 = null;
			
		
			if(matcher && matcher2)
			{ 
				
		  s2=newList.subList(i+1,newList.size()-1);
				s2.add(newList.get(tsize-1));
				
			}
			else {
				// s2=newList.subList(0,newList.size()-1);
				//	s2.add(newList.get(tsize-1));
				Collections.copy(s2,newList);
			}
			
			  
			
			
			
			// System.out.println("first element is "+s2.get(0));
			
			for(AndroidElement aen:s2)
				 System.out.println(aen.getText());
			Thread.sleep(2000);
			
			lastEE=s2.get(s2.size()-2);
			lastE=s2.get(s2.size()-1);
			SS=lastEE.getText();
			ss=lastE.getText();
	   // System.out.println("last element is +++"+lastEE.getText());
		//System.out.println("last element is +++"+lastE.getText());
		}
		
	}
	catch(Exception e)
	{
		System.out.println("Element not found exception occurred. ");
	}

	
	
	}

	
	public static void Menu() throws InterruptedException, IOException
	{
		java.util.List<AndroidElement> s2=driver.findElements(By.id("android:id/title"));
		for(AndroidElement ae:s2)
			 {
		    System.out.println(ae.getText());
		    fos.write(ae.getText().getBytes());
		    
		    if( ae.isEnabled() && ae.getAttribute("resourceId").equalsIgnoreCase("android:id/title") && !((ae.getText()).equals("Tracking AF")) && !((ae.getText()).equals("Save pictures as previewed")) && !(ae.getText().equals("Face shape correction")) && !(ae.getText().equals("Motion photo")) && !(ae.getText().equals("Video stabilisation")) && !(ae.getText().equals("Location tags")) && !(ae.getText().equals("Tracking AF"))&& !(ae.getText().equals("Review pictures")) && !(ae.getText().equals("Voice control")) && !(ae.getText().equals("Floating Camera button")))		
		    {
			ae.click();
			Thread.sleep(5000);
			Utilities u=new Utilities(driver);
            u.subMenu(fos);
          
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
				
			}
			}
		
		
	}
	
	
	
	public boolean swipeDown()
	{
		
		try
		{
		
			java.util.List<AndroidElement> lt1=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch']");
		
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
		 java.util.List<AndroidElement> lt2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch']");
		String s2=lt2.get(lt2.size()-1).getText();
		//System.out.println("after scroll"+s2);
		
		
		//if text of last element before and after scroll is same then return false
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
	
}
