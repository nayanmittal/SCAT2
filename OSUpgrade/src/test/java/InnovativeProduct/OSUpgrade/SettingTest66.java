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
import org.apache.xmlbeans.impl.tool.Diff;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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


/*Handled message popup like cancle,mode description ,accept permission,click ok.
 * [4-5] Adding classes like more options in wifi[content-desc : More options]
 * [5-7]Changing it to non static from static.
 * android.widget.ImageView with no id , no text can use if images count>0 way. We can give count of image here.More option,+ Icon,refresh icon,tick icon
 * Added android.widget.Button . Don't click button ,seach icon [search icon have content-desc Search apps somewhere]is also button but without any id,text .Just print this class for now.
 * https://findmymobile.samsung.com with id  . its in Biometric->find my class
 * 
 * Changed Mobile tethering option.
 * 
 * 
 */

public class SettingTest66 extends BaseSetting2
{
	
	AndroidDriver<AndroidElement> driver;
	//static Utilities u;
	File f,ff;
	File fx;
	FileOutputStream fos,ffos;
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
	
	
	@Test
	public void enterText() throws MalformedURLException, IOException, InterruptedException
	{
		try
		{
				//u=new Utilities(driver);
			f=new File("C:\\Users\\nayan.mittal\\Desktop\\Data\\MenuTreeSettigs.txt");
			fos=new FileOutputStream(f);
		
			//ff=new File("D:\\Drive E\\automation tool\\OSUpgrade\\src\\main\\java\\Resources\\MenuTreeSettigsFormated.txt");
			//ffos=new FileOutputStream(ff);
		
			fx=new File("C:\\Users\\nayan.mittal\\Desktop\\Data\\MenuTreeSetting_A715_Q.xlsx");
			fxos=new FileOutputStream(fx);
		
			book=new XSSFWorkbook();
			sheet=book.createSheet("Settings");

			driver=capabilities();
			acceptPermissions();
			
			//driver.pressKeyCode(AndroidKeyCode.HOME);
			//driver.pressKey(new KeyEvent(AndroidKey.HOME));
			//driver.startActivity(new Activity("com.sec.android.app.camera","com.sec.android.app.camera.Camera"));
      		System.out.println("===Inside Setting Menu of Settings app===");
	
			//java.util.List<AndroidElement> s2=driver.findElements(By.xpath("//android.widget.FrameLayout//android.widget.RelativeLayout//android.widget.TextView"));
			Menu1();	
	
			book.write(fxos);
			book.close();
	 
		}
		catch(Exception e)
		{
			book.write(fxos);
			book.close();
			System.out.println("Inside catch of enter text method  ");
			e.printStackTrace();
		}
		
	} 
	


	public void Menu1() throws InterruptedException, IOException
	{
		//System.out.println("Depth is  "+depth);
		
		round++;
		Thread.sleep(1000);
		slast=""; // it will contain end elements of the list to be removed.
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
			java.util.List<AndroidElement> s2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch' or @class='android.widget.CheckedTextView' or @class='android.widget.Button']");
			int tsize=s2.size();
			String Previous="            ";
	        int flag=0;
			
			//there can be scope of improvmenet in the remove duplicate code.
		    try{
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
				System.out.println("prblm in  Either New list 1st or 2nd ELement");
				e.printStackTrace();
			}
			
			
	
			//System.out.println("Before click/New Depth size is "+tsize);
			//System.out.println("Before click/New Depth last element is  "+Lbefore);
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
				     //System.out.println("Before click elements are  "+FIRST+"  "+SECOND);
				     //java.util.List<AndroidElement> s2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch']");
				     //java.util.List<AndroidElement> s3 = null;
				     java.util.List<AndroidElement> s3=new ArrayList<AndroidElement>(100);
				     //System.out.println(s3.size());
		
		
			
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
				     //System.out.println("checkpoint 2");
			
			
			
				     //removing last and second last element as it not getting clicked sometime .Mostly facing this issue in Andorid Q. hardcoded.. May be need to change the value.
				     //we can also add one more condition that it remove last 2 element when depth is 0 as its not creating problem in inner depth .
				     // when increasing size then its getting fail.we can change here it with s2.size()>13 and then remove from s3.
				     try {
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
				    	 System.out.println("Last element not getting click problem ");
				     }
			
		
			
		//Printing & clicking starts here
		try 
		{
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
					
					else if(ae.isEnabled() && (ae.getAttribute("resourceId").equalsIgnoreCase("android:id/title") || ae.getAttribute("resourceId").equalsIgnoreCase("com.sec.android.app.camera:id/spinner_title") ))
					{
					//System.out.println("------------------------");
					System.out.println("\n->"+ae.getText());
					text="\n"+text;
					fos.write(text.getBytes());
					sheet.createRow(rowCount++).createCell(depth).setCellValue(ae.getText());
					}
					
					else
					{
				    System.out.println(ae.getText());
				    //System.out.println(submenu.getAttribute("checked"));
					fos.write(ae.getText().getBytes());
					sheet.createRow(rowCount++).createCell(depth).setCellValue(ae.getText());
					}
					/*
					if(text.length()!=0) //printing when text is not empty.
					{
					fos.write(text.getBytes());
					formattedWrite(text);
					sheet.createRow(rowCount++).createCell(depth).setCellValue(text);
					}
					*/
			}
			catch(Exception e)
			{
				System.out.println(ae.getText());
				if(ae.getText().length()!=0)
				{
				fos.write(ae.getText().getBytes());
				//formattedWrite(ae.getText());
				sheet.createRow(rowCount++).createCell(depth).setCellValue(ae.getText());
				}
				//System.out.println("inside catch of first print block");
			}
			
		    	fos.write('\n');
		    	//ffos.write('\n');
		    
		    	//clicking starts here
		    	if(depth<2)   
		    	{
		    		try
		    		{	 
		    		//if(ae.getText().toString().equalsIgnoreCase("A software update is available.")||ae.getText().toString().equalsIgnoreCase("Connections")||ae.getText().toString().equalsIgnoreCase("Display")||  ae.getText().toString().equalsIgnoreCase("Accessibility")|| ae.getText().toString().equalsIgnoreCase("Accounts and backup")||ae.getText().toString().equalsIgnoreCase("Biometrics and security")||ae.getText().toString().equalsIgnoreCase("Lock screen")|| ae.getText().toString().equalsIgnoreCase("Game Launcher")|| ae.getText().toString().equalsIgnoreCase("Full screen apps") || ae.getText().toString().equalsIgnoreCase("Font style") || ae.getText().toString().equalsIgnoreCase("Google Location History")|| ae.getText().toString().equalsIgnoreCase("Google Location Sharing")|| ae.getText().toString().equalsIgnoreCase("Clock style")|| ae.getText().toString().equalsIgnoreCase("Contact information")|| ae.getText().toString().equalsIgnoreCase("Screen lock type")  || ae.getText().toString().equalsIgnoreCase("Notifications") ||ae.getText().toString().equalsIgnoreCase("Sounds and vibration")||ae.getText().toString().equalsIgnoreCase("Contact us") ||  ae.getText().toString().equalsIgnoreCase("Developer options") || ae.getText().toString().equalsIgnoreCase("Apps") || ae.getText().toString().equalsIgnoreCase("Mobile Hotspot and Tethering") || ae.getText().toString().equalsIgnoreCase("Download and install")|| ae.getText().toString().equalsIgnoreCase("System WebView licences")||ae.getText().toString().equalsIgnoreCase("Open source licences")|| ae.getText().toString().equalsIgnoreCase("Tips and user manual") || ae.getText().toString().equalsIgnoreCase("Samsung legal") ||ae.getText().toString().equalsIgnoreCase("Privacy Policy")|| ae.getText().toString().equalsIgnoreCase("Safety information") )
		    		if( ae.getText().toString().equalsIgnoreCase("Hide apps")||ae.getText().toString().equalsIgnoreCase("Game Launcher")|| ae.getText().toString().equalsIgnoreCase("Google Location History")|| ae.getText().toString().equalsIgnoreCase("Clock style")|| ae.getText().toString().equalsIgnoreCase("Contact information")|| ae.getText().toString().equalsIgnoreCase("Screen lock type")||ae.getText().toString().equalsIgnoreCase("Full screen apps") || ae.getText().toString().equalsIgnoreCase("Font style") || ae.getText().toString().equalsIgnoreCase("Notifications") ||ae.getText().toString().equalsIgnoreCase("Contact us") ||  ae.getText().toString().equalsIgnoreCase("Developer options") || ae.getText().toString().equalsIgnoreCase("Apps") || ae.getText().toString().equalsIgnoreCase("Mobile Hotspot") ||ae.getText().toString().equalsIgnoreCase("Bluetooth tethering")||ae.getText().toString().equalsIgnoreCase("USB tethering")|| ae.getText().toString().equalsIgnoreCase("Download and install")|| ae.getText().toString().equalsIgnoreCase("System WebView licences")||ae.getText().toString().equalsIgnoreCase("Open source licences")|| ae.getText().toString().equalsIgnoreCase("Tips and user manual") || ae.getText().toString().equalsIgnoreCase("Samsung legal") ||ae.getText().toString().equalsIgnoreCase("Privacy Policy")|| ae.getText().toString().equalsIgnoreCase("Safety information") )  
		    		{
		    		}
		    		// if getAttribute method dosn't find any value then it return null and if you perform any operation on that then null pointer excecption is thrown .
		    		else if(ae.isEnabled() && (ae.getAttribute("resourceId").equalsIgnoreCase("android:id/title")))		
				    {
		    		
					ae.click();
		    	 	//int index=s2.indexOf(ae);
		    		//TouchAction t=new TouchAction(driver);
		    		//t.tap(tapOptions().withElement(element(ae))).perform();
		    		Thread.sleep(1500);
		    		//add condition to hide keyboard
		    		try {	
		    			if(driver.isKeyboardShown())
		    	 		driver.hideKeyboard();
		    			}
		    		catch(Exception e)	{
		    			//System.out.println("Problem with keyboard ");
		    		}
		    		
		    		        
		    					
					//going inside only if its clickable (size of list changes)
		    	
		    		java.util.List<AndroidElement> list2=new ArrayList<AndroidElement>();
					try {
		    		list2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch' or @class='android.widget.CheckedTextView' or @class='android.widget.Button']");
					}
					catch(Exception e)
					{
					//System.out.println("not able to get element after click");	
					}
					// add condition where size after clicking is same so add condition to match last element .last element before and after click will be different.	
						
					/*boolean n_depth=s3.equals(list2);
						if(n_depth)
							System.out.println("collections same after clicking so depth not changed ");
						else
							System.out.println("collections different after clicking so depth changed ");	
						//if(!n_depth)
					*/	
					
					
					/*
					String LL=s3.get(s3.size()-1).getText();
					
					System.out.println("last element before click "+LL);
					System.out.println("last element after click "+ll);
					
					System.out.println("\n-----------------------Printing for testing purpose");
					for(AndroidElement aenn:s3)
					System.out.println(aenn.getText());
					System.out.println("---------------------------------------------------");
					*/
					
				
					boolean el_count=true;
					String ll="";
					String first="";
					String second="";
					String third="";
					try {
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
					
			
					//System.out.println("After click elements are  "+first+"  "+second);
				
					/*		if(index==(tsize-1))
					{
						if(ll.equalsIgnoreCase("On"))
							ll="Off";
						else if(ll.equalsIgnoreCase("Off"))
						    ll="On";
					}
					
					 */		
					
					
					
					//System.out.println("after click size is   "+list2.size());
					//System.out.println("After click last element is  "+ll);
					//hardcoded tsize here becauase its required.
					//if(tsize!=list2.size()) condition changed
					
					
					//if size is Diff. but last element is same then depth not changed.
							if(el_count)
							{
								if(FIRST.equals(first) && SECOND.equals(second) && THIRD.equals(third))
								{
								//System.out.println("Depth not changed  ");
								TouchAction T=new TouchAction(driver);
					    		T.tap(tapOptions().withElement(element(ae))).perform();
					    	
								}
								else
								{
									//System.out.println("Depth changed  ");
								depth++;
								//Utilities u=new Utilities(driver);l
								//u.subMenu(fos);
								//go to 3rd depth only[0,1,2] where 0th depth is camera setting page. 
										if(depth<3)
										{	SS="PPPPP";
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
									{	SS="PPPPP";
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
		    	else {
		    		//it will come here when attribute id is not as mentioned in else if condition.
		    		//System.out.println("Not clickable  "+ae.getText());
		    		}
	
		    		
		    	
		    	
		     }
		    catch(Exception e)
		    {
		    	// it will come here when resourceId will not be there. and get Attribute method will return null.
		    	//e.printStackTrace();
		    	//System.out.println("Inside element click test catch ");
		    }
		    
		    	} //end of depth check condition .
		    
		    
		}  //End of for LOOP
		
		}
		
				try {
						AndroidElement lastEE=s3.get(s3.size()-2);
						AndroidElement lastE=s3.get(s3.size()-1);	
						SS=lastEE.getText();
						ss=lastE.getText();
						//System.out.println("2nd last element is-- "+SS);
						//System.out.println("    last element is-- "+ss);
					}
				catch(Exception e)
					{//System.out.println("either 2nd last or last element not found");
					}
		
	   }
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Inside main catch    ");
			
		}
		
		
		/*   //working but setting in edit camera are not coming .. dont know why
		if(round<=depth)
		{
		try {
		while(swipeDown())
			Menu1();
		}
		catch(Exception e)
		{}
		}
		round--;
		*/
		
		
			try 
			{
			while(swipeDown() && scroll_count<5)
			{
				scroll_count++;
				Menu1();
			}
			}
			catch(Exception e)
			{}
			//scroll_count=0;

	}
	
/*
	public static void formattedWrite(String text) {
		// TODO Auto-generated method stub
		if(depth==0)
		{
			text="00"+text;
		}
		else if(depth==1)
		{
			text="11"+text;	
		}
		else
		{
			text="22"+text;
		}
		
		try {
			ffos.write(text.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Some problem while writting text file..");
		}
	}

*/



	public boolean swipeDown()
	{
		try
		{
			
			java.util.List<AndroidElement> lt1=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch' or @class='android.widget.CheckedTextView' or @class='android.widget.Button']");
			
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
		 Thread.sleep(2000);
		 java.util.List<AndroidElement> lt2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch' or @class='android.widget.CheckedTextView' or @class='android.widget.Button']");
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
		System.out.println("Permission allowed     ");
		}	
		
	}
	
	public void pressCancle()
	{
		try {
			//A715 force stop, com.android.settings:id/parentPanel for Cancel,android:id/content
			//M30 popup id: com.sec.android.app.camera:id/parentPanel 
			//ok pop up com.samsung.android.voc:id/parentPanel,android:id/content, package :com.samsung.android.voc
			if(driver.findElementsById("android:id/parentPanel").size()>0)
				driver.findElementByXPath("//*[@text='CANCEL']").click();
			System.out.println("Cancle clicked  ");
			}
		catch(Exception e)
		{
			System.out.println("Cancle exception");
			
			
		}
		
		
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
