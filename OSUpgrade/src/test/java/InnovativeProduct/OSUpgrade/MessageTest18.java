package InnovativeProduct.OSUpgrade;

import java.awt.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.GsmCallActions;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
//import android.app.AlertDialog;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

/*Getting the Data Sheetwise.[13->15]
 * Handled message popup like cancle,mode description ,accept permission,click ok.
 * [15-16]Updated to work for multi-threading
 * added if condition that it enters for loop only when size of s3 is greater than zero.
 *  Added code to launch camera preview page[Catch block] if its fail somewhere
 * 
 * This device will get call from APPiumParallelExecution File.[It will get path,adb id,device name and OS ver]
 * changed done : when it exits from camera more modes option then handling done. Need to change it in MessageTest15.java
 * There is issue with appium or else that when we are in preview screen and if we try to get element text of of moreshootingmodes stored in already created list than it is not able to acess.
 * so first go to moreshooting modes.added acceptPermissionsWhileUsing() method.
 * [16-17]Adding front camera also.
 * Updated preview page logic.Updated acceptPermission()and added at more plcaes .
 * [17-18] Updated preview Mode
 */


public class MessageTest18
{
	AndroidDriver<AndroidElement> driver;
	//static Utilities u;
	File f;
	File fx;
	FileOutputStream fos;
	FileOutputStream fxos;
	XSSFWorkbook book;
	XSSFSheet sheet;
	
	int depth=0;
	int round=-1;
	int count=0;
	String SS="PPPPP";
	String ss="TTTTT";
	int rowCount=0;
	int columnCount=0;
	String slast="";
	String last="";
	String SCROLL_LAST="";
	String scroll_last="";
	int scroll_count=0;
	int frontCamera=0;
	
	MessageTest18(AndroidDriver<AndroidElement> driver)
	{
		this.driver=driver;
		
	}
	
	public void enterText(String path,String deviceId,String deviceName,String osVersion) throws MalformedURLException, IOException, InterruptedException
	{
		try
		{
		//f=new File("D:\\Drive E\\automation tool\\OSUpgrade\\src\\main\\java\\Resources\\"+deviceId+"_CameraSettigs.txt");
		f=new File(path+"\\"+deviceName+"_OS_"+osVersion+"_"+deviceId+"_"+AppiumParallelExecution.testItem+".txt");
		fos=new FileOutputStream(f);
		
		//String dummy=path+"\\"+deviceName+"_OS_"+osVersion+"_"+deviceId+"_CameraSettings.xlsx";
		//System.out.println(dummy);
		fx=new File(path+"\\"+deviceName+"_OS_"+osVersion+"_"+deviceId+"_"+AppiumParallelExecution.testItem+".xlsx");
		fxos=new FileOutputStream(fx);
		
		book=new XSSFWorkbook();
		pressOk();
		acceptPermissions();
		pressCancle();
		pressOk();
	
		//For front camera start from here,text : Switch to front camera,Switch camera,Switch to rear camera
		frontandbackMode();
		driver.pressKey(new KeyEvent(AndroidKey.HOME));
		driver.startActivity(new Activity("com.sec.android.app.camera","com.sec.android.app.camera.Camera"));
		Thread.sleep(2000);
		if(driver.findElementsByXPath("//*[@text='Switch to front camera' or @text='Switch camera']").size()>0) {
			driver.findElementsByXPath("//*[@text='Switch to front camera' or @text='Switch camera']").get(0).click();
			frontCamera=1;
		}
		//frontandbackMode(); //called again for back camera
		book.write(fxos);
		book.close();
		}
		catch(Exception e)
		{
			book.write(fxos);
			book.close();
			e.printStackTrace();
			System.out.println("Inside enter text method catch");
		}
	}
	

	
	public void frontandbackMode() throws InterruptedException
	{
		
		java.util.List<AndroidElement> shootingModes=driver.findElementsByClassName("ShootingModeShortcutItem");
		//System.out.println("Modes are as mention below   ");
		//for(AndroidElement smmm:shootingModes)
		//System.out.println(smmm.getText());
		//System.out.println("=======Modes Ended==========\n");
		
		//Scroll to left
		Utilities u1=new Utilities(driver);
		for(int i=0;i<6;i++)
			u1.scrollLeft();
		
		Thread.sleep(2000);
		for(AndroidElement sm:shootingModes)
		{
			//add code that if its not on camera preview page then go to camera preview page

			try {
			rowCount=0;   
			System.out.println("Printing Mode Just for testing   :"+sm.getText());
			sm.click();
			acceptPermissions();
			modeDetailsAccept();
			Thread.sleep(2000); //don't change wi
			
					//code added for new camera update .A705 
			     	if(sm.getText().equalsIgnoreCase("MORE"))
			     	{
			     		//System.out.println("checkpoint1");
			     		java.util.List<AndroidElement> moreshootingModes=driver.findElementsByClassName("MoreShootingModeShortcutItem");
			     		//System.out.println("checkpoint2");
			     			for(AndroidElement msm:moreshootingModes)
			     			{
			     				try
			     				{
			     					if(frontCamera==1) 
			     					{
			     						if(msm.getText().toString().equalsIgnoreCase("PRO") ||msm.getText().toString().equalsIgnoreCase("PANORAMA") ||msm.getText().toString().equalsIgnoreCase("FOOD")||msm.getText().toString().equalsIgnoreCase("SPORTS")||msm.getText().toString().equalsIgnoreCase("SUPER SLOW-MO")||msm.getText().toString().equalsIgnoreCase("SLOW MOTION")||msm.getText().toString().equalsIgnoreCase("MACRO"))
			     							continue;
			     					}
			     			
			     				rowCount=0;  
			     					if(frontCamera==0)
			     					sheet=book.createSheet(msm.getText());
			     					else
			     					sheet=book.createSheet("Front"+msm.getText());
			     				
			       				sm.click();
			     				modeDetailsAccept();
								//if(msm.getText().equalsIgnoreCase("PANORAMA"))	
			     				//continue;
					       		String mmodeName=msm.getText()+" Mode "+": "+"Settings"; 
					    		Thread.sleep(1000);
					    		//System.out.println(mmodeName);
					    		msm.click();
					    		acceptPermissions();
					    		modeDetailsAccept();
					    		
					    		previewMode();
					    		/*
					     		java.util.List<AndroidElement> homePageSettings1=driver.findElementsByXPath("//*[@class='GLButton' or @class='GLSelectButton']");
					     		
					     		String Duplicate1="";
					     		for(AndroidElement ae1:homePageSettings1) 
					     		{
					     			if(ae1.getText().equalsIgnoreCase(Duplicate1))
					     				continue;
						
					     			Duplicate1=ae1.getText();
					     			System.out.println(ae1.getText());
					     			fos.write(ae1.getText().getBytes());
					     			fos.write('\n');
					     			sheet.createRow(rowCount++).createCell(0).setCellValue(ae1.getText());
					     		}
					    		
					     		
					     		ArrayList<String> duplicate1=new ArrayList<String>();
					     		for(AndroidElement ae1:homePageSettings1) 
					     		{
					     			String v=ae1.getText().toString();
					     			if(duplicate1.contains(v))
					     				continue;
						
					     			duplicate1.add(v);
					     			System.out.println(v);
					     			fos.write(ae1.getText().getBytes());
					     			fos.write('\n');
					     		
					     			sheet.createRow(rowCount++).createCell(0).setCellValue(ae1.getText().toString());
					          	}
					     		*/
					    		sheet.createRow(rowCount++).createCell(0);
					    		sheet.createRow(rowCount++).createCell(0);
					    		sheet.createRow(rowCount++).createCell(0).setCellValue(mmodeName);
								
					    		/*
					     		try
					     		{driver.findElementById("LAUNCH_SETTING_ACTIVITY").click();
					     		}
					     		catch(Exception e)
					     		{
					     		driver.findElementById("MENUID_MORE_SETTING").click();	
					     		}
					     		Menu1();
					     		driver.navigate().back();
					     		*/
					  
					    		Thread.sleep(3000);
					    		driver.navigate().back();
					    		Thread.sleep(1000);
					    		sm.click();
			     				}
			     				catch(Exception e)
			     				{
			     					e.printStackTrace();
			     					driver.pressKey(new KeyEvent(AndroidKey.HOME));
			     					driver.startActivity(new Activity("com.sec.android.app.camera","com.sec.android.app.camera.Camera"));
			     				  	pressOk();
			     					acceptPermissions();
			     					pressCancle();
			     					pressOk();
			     					sm.click();
			     					Thread.sleep(1000);
			     			 	}
			     			}
		
			     	}
			
			     	else
			     	{
			     		//if(sm.getText().equalsIgnoreCase("LIVE FOCUS") || sm.getText().equalsIgnoreCase("PHOTO") || sm.getText().equalsIgnoreCase("VIDEO"))	
				     	//	continue;
			     		if(frontCamera==0)
			     		sheet=book.createSheet(sm.getText());
			     		else
			     			sheet=book.createSheet("Front"+sm.getText());	
			     		previewMode();
			     		/*
			     		java.util.List<AndroidElement> homePageSettings=driver.findElementsByXPath("//*[@class='GLButton' or @class='ShootingModeShortcutItem']");
			     		//Set<AndroidElement> homePageSettings=(Set<AndroidElement>) driver.findElementsByXPath("//*[@class='GLButton' or @class='ShootingModeShortcutItem']");
			     		//GLSelectButton & (GLButton or QuickSettingItem)
			     		
			     		String Duplicate="";
			     		for(AndroidElement ae:homePageSettings) 
			     		{
			     			if(ae.getText().equalsIgnoreCase(Duplicate))
			     				continue;
				
			     			Duplicate=ae.getText();
			     			System.out.println(ae.getText());
			     			fos.write(ae.getText().getBytes());
			     			fos.write('\n');
			     			sheet.createRow(rowCount++).createCell(0).setCellValue(ae.getText());
			     		}
						
			     		
			     		ArrayList<String> duplicate=new ArrayList<String>();
			     		for(AndroidElement ae:homePageSettings) 
			     		{
			     			String v=ae.getText().toString();
			     			if(duplicate.contains(v))
			     				continue;
				
			     			duplicate.add(v);
			     			System.out.println(v);
			     			fos.write(ae.getText().getBytes());
			     			fos.write('\n');
			     	
			     			sheet.createRow(rowCount++).createCell(0).setCellValue(ae.getText().toString());
			     		
			     		}
			     		*/
			     		String modeName=sm.getText()+" Mode "+": "+"Settings";
			     		System.out.println(modeName);
			
			     		sheet.createRow(rowCount++).createCell(0);
			     		sheet.createRow(rowCount++).createCell(0);
			     		sheet.createRow(rowCount++).createCell(0).setCellValue(modeName);
			     		/*
			     		try
			     		{driver.findElementById("LAUNCH_SETTING_ACTIVITY").click();
			     		}
			     		catch(Exception e)
			     		{
			     		driver.findElementById("MENUID_MORE_SETTING").click();	
			     		}
			     		Menu1();
			     		driver.navigate().back();
			     		
			     		*/
			       		Thread.sleep(2000);
			     		
			     	}
			}
			catch(Exception e)
			{
				System.out.println("Inside main for loop catch ");
				e.printStackTrace();
				driver.pressKey(new KeyEvent(AndroidKey.HOME));
				driver.startActivity(new Activity("com.sec.android.app.camera","com.sec.android.app.camera.Camera"));
				Thread.sleep(1000);
				pressOk();
				acceptPermissions();
				pressCancle();
				pressOk();
			}	
		}//end of for loop.
		
		
	}
	
	
	
	public void Menu1() throws InterruptedException, IOException
	{
		round++;
		Thread.sleep(1000);
		
		slast="";
		last="";
		
			/*	try 
        	{//String alertN=driver.findElementById("com.samsung.android.voc:id/alertTitle").getText();
        	
        	// In alert xpath is not working dont know why 	
        	//String alertN=driver.findElementByXPath("//*[@id='com.samsung.android.voc:id/alertTitle' or @id='com.samsung.android.voc:id/title'] ").getText().toString();
        	String alertN=driver.findElementById("com.samsung.android.voc:id/alertTitle").getText();
        	System.out.println("alert is "+alertN);
        	Thread.sleep(1000);
        	driver.findElementByClassName("android.widget.Button").click();
        	System.out.println("click on ok executed...");
		
        	//while click on ok button ClassName is working but id is not working dont know why.
        	//driver.findElementById("com.samsung.android.voc:id/button1").click();
		
		
        	//driver.findElementByXPath("//*[@text='OK']").click();
	
        	Thread.sleep(1000);
        	//driver.navigate().back();
        	}
        	catch(Exception e)
        	{
        	//System.out.println("Inside catch of alert pop-up");
        	}  
			 */   
        
        
			int i=-1;
			boolean matcher=false;
			boolean matcher2=false;
			java.util.List<AndroidElement> s2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch' or @class='android.widget.CheckedTextView']");
			int tsize=s2.size();
			String Previous="            ";
	        int flag=0;
			
	        
			//there can be scope of improvement in the remove duplicate code.
		    	try
		    	{
					for(AndroidElement aen:s2)
					{
							String nlText=aen.getText().toString();
							if(flag==0)
							{
							Previous=nlText;
							i++;
							flag=1;
							continue;
							}
				
							if(Previous.equalsIgnoreCase(SS))
							{
								//System.out.println("New List 1st Element matched is  "+Previous);
								if(nlText.equalsIgnoreCase(ss))
								{
									i++;
									//System.out.println("New List 2nd Element matched is  "+nlText);
									matcher=true;
									break;
								}
							}
				
				
							if(flag==1)
								Previous=nlText;
							i++;
			
					}
		    	}
		    	catch(Exception e)
		    	{
		    	e.printStackTrace();
				System.out.println("prblm in  Either New list 1st or 2nd ELement");
		    	}
             
		    	
		    	
		    	
		    	String Lbefore="";
		    	String FIRST="";
		    	String SECOND="";
		    	String THIRD="";
		    	try 
		    	{
		    	 Lbefore=s2.get(s2.size()-1).getText();
				 FIRST=s2.get(0).getText();
				 SECOND=s2.get(1).getText();
				 THIRD=s2.get(2).getText();
		    	}
		    	catch(Exception e)
		    	{
		    	e.printStackTrace();
		    	 //System.out.println("Less than 3 element   ");
		    	}
		    
			
		    	//System.out.println("s2 size "+tsize);
		    	//java.util.List<AndroidElement> s2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch']");
		    	//java.util.List<AndroidElement> s3 = null;
		    	java.util.List<AndroidElement> s3=new ArrayList<AndroidElement>(100);
		    	//System.out.println(s3.size());
		    	//System.out.println("checkpoint 1");
			

		    	if(matcher)
		    	{ 
				s3=s2.subList(i+1,(s2.size()-1));
				s3.add(s2.get((s2.size())-1));
				//System.out.println("Duplicate Removed     ");
		    	}
		    	else 
		    	{
				// s2=newList.subList(0,newList.size()-1);
				//	s2.add(newList.get(tsize-1));
				//Collections.copy(s3,s2);
				s3.addAll(s2);
				//System.out.println("There is no duplicate elements [last 2]  ");
				}

		    	//removing last and second last element as it not getting clicked sometime .Mostly facing this issue in Andorid Q. hardcoded.. May be need to change the value.
		    	//we can also add one more condition that it remove last 2 element when depth is 0 as its not creating problem in inner depth .
		    	//remove last element when after scrolling size is bigger.means remove only when after scroll you havn't reach End of page.
			
		    	try 
		    	{
		    		if(s3.size()>10) 
		    		{
		    			slast=s3.get(s3.size()-1).getText();
		    			s3.remove(s3.size()-1); 
		    			last=s3.get(s3.size()-1).getText();
		    			s3.remove(s3.size()-1);
		    		}
		    	}
		    	catch(Exception e)
		    	{
		    	
		    		System.out.println("Last 2 element removal problem catch ");
		    	}
			
			
		//Printing & clicking starts here
		try 
		{
		//System.out.println("------------------------");
		if(s3.size()>0)
		{	
		for(AndroidElement ae:s3)
		{
			//System.out.println("checkpoint 2");
			try 
			{   
				
				String text=ae.getText();
				
				if(ae.getAttribute("className").equalsIgnoreCase("android.widget.CheckedTextView"))
				{
					if(ae.getAttribute("checked").equalsIgnoreCase("true"))
					{
					System.out.println(ae.getText()+"--Ticked");
					text=text+"--Ticked";
					fos.write(text.getBytes());
					sheet.createRow(rowCount++).createCell(depth).setCellValue(text);
					}
					else
					{System.out.println(ae.getText());	
					fos.write(ae.getText().getBytes());
					sheet.createRow(rowCount++).createCell(depth).setCellValue(ae.getText());
					}
				}
					
				else if(ae.isEnabled() && (ae.getAttribute("resourceId").equalsIgnoreCase("android:id/title") || ae.getAttribute("resourceId").equalsIgnoreCase("com.sec.android.app.camera:id/spinner_title")))
					{//System.out.println("------------------------");
					System.out.println("\n->"+ae.getText());
					text="\n->"+text;
					fos.write(text.getBytes());
					sheet.createRow(rowCount++).createCell(depth).setCellValue(ae.getText());
					}
				else
				{
				System.out.println(ae.getText());
				fos.write(ae.getText().getBytes());
				sheet.createRow(rowCount++).createCell(depth).setCellValue(ae.getText());
				//System.out.println(submenu.getAttribute("checked"));
				}
			}
			
			catch(Exception e)
			{
				
				System.out.println(ae.getText());
				fos.write(ae.getText().getBytes());
				sheet.createRow(rowCount++).createCell(depth).setCellValue(ae.getText());
				//System.out.println("inside catch of first print block");
				
			}
		    	fos.write('\n');
		      	//Clicking starts here.
		       	if(depth<2)	
		    	{	
		    		try
		    		{
		    			// if( ae.isEnabled() && ae.getAttribute("resourceId").equalsIgnoreCase("android:id/title") && !((ae.getText()).equals("CAMERA SETTINGS")) && !((ae.getText()).equals("Tracking AF")) && !((ae.getText()).equals("Save pictures as previewed")) && !(ae.getText().equals("Face shape correction")) && !(ae.getText().equals("Motion photo")) && !(ae.getText().equals("Video stabilisation")) && !(ae.getText().equals("Location tags")) && !(ae.getText().equals("Tracking AF"))&& !(ae.getText().equals("Review pictures")) && !(ae.getText().equals("Voice control")) && !(ae.getText().equals("Floating Camera button")))			
		    			//it will enter in next loop only if its of title/spinner(we can say clickable)
		    	
		    				if(ae.getText().toString().equalsIgnoreCase("Contact us"))
		    				{
		    		
		    				}
		    				else if(ae.isEnabled() && (ae.getAttribute("resourceId").equalsIgnoreCase("android:id/title") || ae.getAttribute("resourceId").equalsIgnoreCase("com.sec.android.app.camera:id/spinner_title")))		
		    				{
		    					ae.click();
		    					Thread.sleep(1000);
		    					acceptPermissionsWhileUsing();
								try 
								{	
									if(driver.isKeyboardShown())
										driver.hideKeyboard();
								}
								catch(Exception e)
								{
									//System.out.println("Problem with keyboard ");
								}
								//going inside only if its clickable (size of list changes)
								java.util.List<AndroidElement> list2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch' or @class='android.widget.CheckedTextView']");
								// add condition where size after clicking is same so add condition to match last element .last element before and after click will be different.	
								//System.out.print("  size is   "+list2.size());
		
								boolean el_count=true;
								String ll="";
								String first="";
								String second="";
								String third="";
								try 
								{
									ll=list2.get(list2.size()-1).getText();
									first=list2.get(0).getText();
									second=list2.get(1).getText();
									third=list2.get(2).getText();
								}
								catch(Exception e)
								{//less than 3 element present on page
									//System.out.println("Less than 3 element   ");
									el_count=false;		
								}
					
										if(el_count)
										{
											if(FIRST.equals(first) && SECOND.equals(second) && THIRD.equals(third))
											{
												   //System.out.println("Depth not changed  ");
												  if(ae.getAttribute("resourceId").equalsIgnoreCase("com.sec.android.app.camera:id/spinner_title"))
											      {
													  ae.click();
													  Thread.sleep(1000);
													  depth++;
													  if(depth<3)
													  {	
														  SS="PPPPP";
														  ss="TTTTT";
														  count=0;
														  scroll_count=0;
														  Menu1();
														  scroll_count=0;
													  }		
													  driver.pressKey(new KeyEvent(AndroidKey.BACK));	
													  depth--;  
											      }
												  else 
												  {
												  TouchAction T=new TouchAction(driver);
												  T.tap(tapOptions().withElement(element(ae))).perform();
												  }
			    							}
											else
											{
												//System.out.println("Depth changed  ");
												depth++;
												//Utilities u=new Utilities(driver);l
												//u.subMenu(fos);
												//go to 3rd depth only[0,1,2] where 0th depth is camera setting page. 
												if(depth<3)
												{	
												SS="PPPPP";
												ss="TTTTT";
												count=0;
												scroll_count=0;
												Menu1();
												scroll_count=0;
												}		
												driver.pressKey(new KeyEvent(AndroidKey.BACK));	
												depth--;
											}
										}
										else
										{
											if(tsize!=list2.size())	
											{
												//System.out.println("Depth changed  ");
												depth++;
												//Utilities u=new Utilities(driver);l
												//u.subMenu(fos);
												//go to 3rd depth only[0,1,2] where 0th depth is camera setting page. 
												if(depth<3)
												{
												SS="PPPPP";
												ss="TTTTT";
												count=0;
												scroll_count=0;
												Menu1();
												scroll_count=0;
												}		
												driver.pressKey(new KeyEvent(AndroidKey.BACK));	
												depth--;
											}
											else
											{
												//System.out.println("Depth not changed  ");
												//ae.click();	   //clicking again to 
												TouchAction T=new TouchAction(driver);
												T.tap(tapOptions().withElement(element(ae))).perform();
											}
					
										}
		    				}
		    				else 
		    				{//System.out.println("Element type not defined     "+ae.getText());
		    				}

		    		}
		    	catch(Exception e)
		    		{	
		    		// it will come here when resourceId will not be there. and get Attribute method will return null.
		    		//e.printStackTrace();
		    		//System.out.println("Inside element click test catch ");
		    	    }
		    
		    
		    	}//end of if condittion  
		    	
		}	//End of for LOOP
		
		}
		
		    		try 
		    		{
		    		AndroidElement lastEE=s3.get(s3.size()-2);
		    		AndroidElement lastE=s3.get(s3.size()-1);	
		    		SS=lastEE.getText();
		    		ss=lastE.getText();
		    		//	System.out.println("2nd last element is-- "+SS);
		    		//	System.out.println("    last element is-- "+ss);
		    		}
		    		catch(Exception e)
		    		{System.out.println("either 2nd last or last element not found");
		    		}

		}	
		catch(Exception e)
		{
			System.out.println("Inside main catch    ");
			e.getMessage();	
			e.printStackTrace();
		}
		
	
					try 
					{
						while(swipeDown() && scroll_count<5)
						{
							scroll_count++;
							Menu1();
						}
					}
					catch(Exception e)
					{
					}

		
	}
	
	public void previewMode()
	{
		
 		try 
 		{
					java.util.List<AndroidElement> homePageSettings=driver.findElementsByXPath("//*[@class='ItemDataButton' or @class='ProItem']"); 		
			 		for(AndroidElement aeee:homePageSettings) 
			 		{
			 			String v=aeee.getText().toString();
			 			System.out.println(v);
			 			System.out.println("Test point 1  ");
			 			sheet.createRow(rowCount++).createCell(0).setCellValue(aeee.getText().toString());
			 		
			 			if(v.equalsIgnoreCase("Settings") || v.equalsIgnoreCase("Go to Settings"))
			 				continue;
			 			else
			 			{
				 			System.out.println("Test point 2  ");
			 				if(aeee.getText().toString().equalsIgnoreCase("Effects"))
			 				{
			 					aeee.click();
			 					Thread.sleep(1000);
			 	 				java.util.List<AndroidElement> homePageSettings1=driver.findElementsByXPath("//*[@class='ItemFilterThumbnailButton']");
			 					
			 	 				try {
									for(AndroidElement ae1:homePageSettings1)
									{
										System.out.println(ae1.getText().toString());
										sheet.createRow(rowCount++).createCell(1).setCellValue(ae1.getAttribute("contentDescription").toString());
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
			 					driver.navigate().back();
			 				}
			 				else if("ProItem".equalsIgnoreCase(aeee.getAttribute("className"))) 
			 				{
			 					aeee.click();
			 					Thread.sleep(500);
			 	 				java.util.List<AndroidElement> homePageSettings2=driver.findElementsByXPath("//*[@class='GLText']");
				 				try {
			 	 				for(AndroidElement ae2:homePageSettings2) {
				 					System.out.println(ae2.getText().toString());
				 				sheet.createRow(rowCount++).createCell(1).setCellValue(ae2.getText().toString());
				 				}
				 				}
				 				catch(Exception e) {}
				 				driver.navigate().back();
			 				}
			 				else
			 				{	
			 					aeee.click();
			 					Thread.sleep(1000);
			 	 				java.util.List<AndroidElement> homePageSettings3=driver.findElementsByXPath("//*[@class='ItemDataButton']");
				 				try {
									for(AndroidElement ae3:homePageSettings3) {
										System.out.println(ae3.getAttribute("contentDescription").toString());
										sheet.createRow(rowCount++).createCell(1).setCellValue(ae3.getAttribute("contentDescription").toString());
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				 				driver.navigate().back();
			 				}
			 				
			 				Thread.sleep(500);
			 				
			 			}
			 		
			 		}
		}
 		catch(Exception e)
 		{System.out.println("Inside previewMode() function catch");}
		
	}
	
	public boolean swipeDown()
	{
		try
		{
			
			java.util.List<AndroidElement> lt1=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch' or @class='android.widget.CheckedTextView']");
			
			// this condition is done to avoid scrolling in popup cases. 
			//System.out.println("Before scrolling size is--"+lt1.size());
			int b_size=lt1.size();
			if(lt1.size()<11)
				return false;
			
		
		//finding last 2 element of list
		String s1=lt1.get(lt1.size()-2).getText();
		String S1=lt1.get(lt1.size()-1).getText();
		//System.out.println("before scroll-"+s1); 
		Dimension dim = driver.manage().window().getSize();
		int height = dim.getHeight();
		int width = dim.getWidth();
		//int x = width/2;
		int x = 0;
		int top_y = (int)(height*0.70);
		int bottom_y = (int)(height*0.30);
		//System.out.println("coordinates :" + x + "  "+ top_y + " "+ bottom_y);
		
		
		//scrollStart is bottom point and ScrollEnd is top point.
		//new TouchAction((PerformsTouchActions) driver).press(PointOption.point(0, scrollStart)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(0,scrollEnd)).release().perform();
		new TouchAction((PerformsTouchActions) driver).press(PointOption.point(x,top_y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(x,bottom_y)).release().perform();
		 //Thread.sleep(2000);
		 java.util.List<AndroidElement> lt2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch' or @class='android.widget.CheckedTextView']");
		 String S2=lt2.get(lt2.size()-2).getText();
		 String s2=lt2.get(lt2.size()-1).getText();

         if(SCROLL_LAST.equals(S2) && scroll_last.equals(s2))
         count++;
         else
         {	 
		 SCROLL_LAST=S2;
		 scroll_last=s2;
         }
		
		//System.out.println("After scrolling size is--"+lt2.size());
		//System.out.println("after scroll-"+s2);
		//System.out.println("-------------------------------Tried Scrolling");
		
		int a_size=lt2.size();
		//if(lt1.equals(lt2))
		//this condition will work when we will reach End of list .It will print last 2 element when we remove last 2 element because they are not clearly visible.
		if(s1.equalsIgnoreCase(s2) && (b_size==a_size) && slast.length()!=0)
		{
					System.out.println("Inside true condition");
					return true;
		}
		//returing false when after scrolling last element is same && size is also same.this got fail in biometric and security ->install unkwon apps .[got fail once only during whole setting so not that critical]		
		if(s1.equalsIgnoreCase(s2) && S1.equalsIgnoreCase(S2) && (b_size==a_size) )
		return false;
		/*else if(count>0)
		{
	    count=0;
	    System.out.println("Executed   ");
		return false;			
		}
		*/
		else
		return true;
		}
		catch(Exception e)
		{
			System.out.println("Inside swipe down exception");
			return false;
		}
	
	}
	
	public void pressOk()
	{
	try {
		driver.findElementByXPath("//*[@text='OK']").click();
		System.out.println("Ok clicked  ");	
		}
		catch(Exception e)
		{
			System.out.println("Ok Exception ");
		}
	 }

	public void acceptPermissions()
	{
		while(driver.findElementsById("com.android.packageinstaller:id/permission_allow_button").size()>0)
		{
		driver.findElementsById("com.android.packageinstaller:id/permission_allow_button").get(0).click();
		//System.out.println("Permission allowed     ");
		}
		try
		{
			driver.findElementByXPath("//*[@text='Allow']").click();
			driver.findElementByXPath("//*[@text='Allow']").click();
			driver.findElementByXPath("//*[@text='Allow']").click();
			driver.findElementByXPath("//*[@text='Allow']").click();
			System.out.println("Allowed clicked  ");
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	public void acceptPermissionsWhileUsing()
	{
		while(driver.findElementsById("com.android.permissioncontroller:id/permission_allow_foreground_only_button").size()>0)
				driver.findElementsById("com.android.permissioncontroller:id/permission_allow_foreground_only_button").get(0).click();
	
			//text : Allow only while using the app
		//android:id/content pop up id
		}
	
	public void pressCancle()
	{
		try {
			driver.findElementByXPath("//*[@text='CANCEL']").click();
			System.out.println("CANCEL clicked  ");
			}
		catch(Exception e)
		{
			System.out.println("Cancle exception");
		}
		
		try {driver.findElementByXPath("//*[@text='Cancel']").click();
		System.out.println("Cancle clicked  ");
		}
		catch(Exception e)
		{}	
	}
	
	public void modeDetailsAccept() {
		
		try {
			System.out.println("Flow reached here  ");
			//driver.findElementById("Scene optimiser").click();
			driver.findElementByXPath("//*[@text='NEXT']").click();
			driver.findElementByXPath("//*[@text='GET STARTED']").click();
			}
		catch(Exception e)
		{
			System.out.println("Exception occurred in modeDetailsAccepts Method  ");
		}
				
	}
}
