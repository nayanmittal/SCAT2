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
//

/*Handled message popup like cancle,mode description ,accept permission,click ok.
 * [4-5] Adding classes like more options in wifi[content-desc : More options]
 * [5-7]Changing it to non static from static.
 * android.widget.ImageView with no id , no text can use if images count>0 way. We can give count of image here.More option,+ Icon,refresh icon,tick icon
 * Added android.widget.Button . Don't click button ,seach icon [search icon have content-desc Search apps somewhere]is also button but without any id,text .Just print this class for now.
 * https://findmymobile.samsung.com with id  . its in Biometric->find my class
 * 
 *Changed Mobile tethering option,Lock type.
 * Changing Not enter depth option . Created a loop & used skipMenu string array.
 * Added pressok option.Removing ,"Contact us","Tips and user manual","Notifications" for now.
 * Google & Samsung Account added .ok code changed.(Dont update pressOk in camera because there we are not usign this method inside menu method.)
 * Added Cancel handler, if on pressning cancel if depth not changed
 * increasing wait if its theme.
 * [7-9] adding button descrption.adding more image class for more option.changed swipeDown time to 1 second from 2 second.
 * made printFunction(). Contact us added in skip menu as sometime causing problem.
 * handle remove duplicate [compare 3 element] or correct scroll logic.
 * [9-10] *[9-10] handle remove duplicate [compare 3 element] or correct scroll logic.ss,SS should be diff. for each round.
 * work on calculating remove duplicate .
 */

public class SettingTest11 extends BaseSetting2
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
	String SSS="NNNNN";
	String SS="PPPPP";
	String ss="TTTTT";
	int rowCount=0;
	int columnCount=0;
	String slastt=""; // third last element to be removed if the list size is greater than 10.
	 String slast=""; // second last element if the list size is greater than 10
	 String last="";  // last element if list size is greater than 10
	 String SCROLL_LAST="";
	 String scroll_last="";
	 int scroll_count=0;
	 int s3size=0;
	 //String[] skipMenu={"Hide apps","Game Launcher","Google Location History","Clock style","Contact information","Swipe","Pattern","PIN","Password","None","Intelligent Scan","Face","Iris","Fingerprints","Full screen apps","Font style","Developer options","Apps","Mobile Hotspot","Bluetooth tethering","USB tethering","Download and install","System WebView licences","Open source licences","Samsung legal","Privacy Policy","Safety information","Security update"};
	//,"Contact us","Tips and user manual","Notifications" ,"Biometrics and security","Wallpaper","Wallpaper and themes","Accounts and backup","Lock screen","Privacy","Connections","Sounds and vibration","Notifications","Display",
	 String[] skipMenu={"Connections","Sounds and vibration","Notifications","Display" ,"Wallpaper","Accounts and backup","Lock screen","Wallpapers and themes","Wallpaper and themes","User Manual","Contact us","Themes","Biometrics security patch","Auto-fill service from Google","Hide apps","Game Launcher","Google Location History","Clock style","Contact information","Swipe","Pattern","PIN","Password","None","Intelligent Scan","Face","Iris","Fingerprints","Full screen apps","Font style","Developer options","Apps","Mobile Hotspot","Bluetooth tethering","USB tethering","Download and install","System WebView licences","Open source licences","Samsung legal","Privacy Policy","Safety information","Security update","App preview messages","Device phone number","Firebase App Indexing"};
	//Auto-fill service from Google required 2 back sometimes so skipping
	 //Depth 3 ,inside App menu is long so avodinig for now. Need to update that don't go to depth 3 inside App.
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
		
			fx=new File("C:\\Users\\nayan.mittal\\Desktop\\Data\\MenuTreeSetting_T545_Test.xlsx");
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
		slastt="";
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
			java.util.List<AndroidElement> s2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch' or @class='android.widget.CheckedTextView' or @class='android.widget.Button' or @class='android.widget.ImageView']"); // 
			int tsize=s2.size();
			String Previous2="            ";
			String Previous="           ";
	        int flag=0;
			
			//Removing Duplicated. there can be scope of improvmenet in the remove duplicate code.
	        try{
					for(AndroidElement aen:s2)
					{
						String nlText=aen.getText().toString();
						/*
						if("android.widget.ImageView".equalsIgnoreCase(aen.getAttribute("className")))
							nlText="image";
						else if("android.widget.Button".equalsIgnoreCase(aen.getAttribute("className")))
							nlText="button";
						*/	
					//ae.getAttribute("className")
						if(flag==0)
						{
							Previous2=nlText;
							i++;
							flag=1;
							continue;
						}
						if(flag==1)
						{
							Previous=nlText;
							i++;
							flag=2;
							continue;
		
						}
				
						if(Previous2.equalsIgnoreCase(SSS))
						{
		
								if(Previous.equalsIgnoreCase(SS))
								{
									if(nlText.equalsIgnoreCase(ss))
									{
										i++;
										System.out.println("New List 1st Element matched is  "+Previous2);
										System.out.println("New List 1st Element matched is  "+Previous);
										System.out.println("New List 2nd Element matched is  "+nlText);
										matcher=true;
										break;
									}
								}
						}
				
				
						if(flag==2) {
							Previous2=Previous;
							Previous=nlText;
						}
							i++;
			
					}
			}
			catch(Exception e)
			{
				System.out.println("prblm in  Either New list 1st or 2nd ELement");
				e.printStackTrace();
			}
	        
		   /*
	        try{
					for(AndroidElement aen:s2)
					{
						String nlText=aen.getText().toString();
						
						if("android.widget.ImageView".equalsIgnoreCase(aen.getAttribute("className")))
							nlText="image";
						else if("android.widget.Button".equalsIgnoreCase(aen.getAttribute("className")))
							nlText="button";
						
					//ae.getAttribute("className")
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
			*/
			
	
			//System.out.println("Before click/New Depth size is "+tsize);
			//System.out.println("Before click/New Depth last element is  "+Lbefore);
		    
		    		//Getting last & first 3 element for verification that page has been changed or not
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
		
		
			
				     try
				     {
				    	 if(matcher)
				    	 { 
				    	 //s3=s2.subList(i+1,(s2.size()-1));
				    	 //s3.add(s2.get((s2.size())-1));
				    	 s3=s2.subList(i+1,s2.size());
				     	 System.out.println("Duplicate Removed     ");
				    	 }
				    	 else 
				    	 {
				    	 // s2=newList.subList(0,newList.size()-1);
				    	 //	s2.add(newList.get(tsize-1));
				    	 //Collections.copy(s3,s2);
				    	 s3.addAll(s2);
				    	 System.out.println("There is no duplicate elements [last 2]  ");
				    	 }
				    	 s3size=s3.size();
				     }
				     catch(Exception e)
				     {
				    	 s3size=s3.size();
				    	 System.out.println("remove duplicate exception ... ");
				     }
				     //System.out.println("checkpoint 2");
			
			
			
				     //removing last and second last element as it not getting clicked sometime .Mostly facing this issue in Andorid Q. hardcoded.. May be need to change the value.
				     //we can also add one more condition that it remove last 2 element when depth is 0 as its not creating problem in inner depth .
				     // when increasing size then its getting fail.we can change here it with s2.size()>13 and then remove from s3.
				     try {
				    	 if(s3.size()>10) 
				    	 {
				    		 slastt=s3.get(s3.size()-1).getText();
				    		 s3.remove(s3.size()-1);
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
	
				printFunction(ae); 
		    	fos.write('\n');
		    	//ffos.write('\n');
		    
		    	//clicking starts here
		    	if(depth<2)   
		    	{
		    		boolean cond=false;
		    	 	try
		    		{	 
		    		//if( ae.getText().toString().equalsIgnoreCase("Hide apps")||ae.getText().toString().equalsIgnoreCase("Game Launcher")|| ae.getText().toString().equalsIgnoreCase("Google Location History")|| ae.getText().toString().equalsIgnoreCase("Clock style")|| ae.getText().toString().equalsIgnoreCase("Contact information")|| ae.getText().toString().equalsIgnoreCase("Screen lock type")||ae.getText().toString().equalsIgnoreCase("Full screen apps") || ae.getText().toString().equalsIgnoreCase("Font style") || ae.getText().toString().equalsIgnoreCase("Notifications") ||ae.getText().toString().equalsIgnoreCase("Contact us") ||  ae.getText().toString().equalsIgnoreCase("Developer options") || ae.getText().toString().equalsIgnoreCase("Apps") || ae.getText().toString().equalsIgnoreCase("Mobile Hotspot") ||ae.getText().toString().equalsIgnoreCase("Bluetooth tethering")||ae.getText().toString().equalsIgnoreCase("USB tethering")|| ae.getText().toString().equalsIgnoreCase("Download and install")|| ae.getText().toString().equalsIgnoreCase("System WebView licences")||ae.getText().toString().equalsIgnoreCase("Open source licences")|| ae.getText().toString().equalsIgnoreCase("Tips and user manual") || ae.getText().toString().equalsIgnoreCase("Samsung legal") ||ae.getText().toString().equalsIgnoreCase("Privacy Policy")|| ae.getText().toString().equalsIgnoreCase("Safety information") )  
		    		//change it instead of for loop use ArrayUtils. contains method or use ArrayList contains method.
		    	 	for(String skip:skipMenu)
		    		{
		    			if(ae.getText().toString().equalsIgnoreCase(skip))
		    			{
		    				cond=true;
		    				break;
		    			}
		    		}
		    		// if getAttribute method dosn't find any value then it return null and if you perform any operation on that then null pointer excecption is thrown .
		    		if(!cond && ae.isEnabled() && (ae.getAttribute("resourceId").equalsIgnoreCase("android:id/title")))		
				    {
		    		ae.click();
		       	 	//int index=s2.indexOf(ae);
		    		//TouchAction t=new TouchAction(driver);
		    		//t.tap(tapOptions().withElement(element(ae))).perform();
		    		Thread.sleep(2000); // don't change this time otherwise pressOk() will cause problem.
		      		//Since on clicking ok or cancel depth dont  change so it will got clicked again and then pop up will come again so these method executed again after second click.
		    		pressCancel();
		    		if(pressOk()==1)
		    			continue;
		        	pressLater();
		        	handleKeyboard();
		        	acceptPermissions();
					//going inside only if its clickable (size of list changes).list2 is the list after click
		       		java.util.List<AndroidElement> list2=new ArrayList<AndroidElement>();
					try {
		    		list2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch' or @class='android.widget.CheckedTextView' or @class='android.widget.Button' or @class='android.widget.ImageView']"); // 
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
					    		Thread.sleep(500);// this wait is very important .Don't remove it otherwise cancel will not get clicked.
					    		pressCancel();
					    		//pressOk();
					    		pressLater();
					    		}
								else
								{
									System.out.println("Depth changed  ");
								depth++;
								//Utilities u=new Utilities(driver);l
								//u.subMenu(fos);
								//go to 3rd depth only[0,1,2] where 0th depth is camera setting page. 
										if(depth<3)
										{	
										SSS="NNNNN";	
										SS="PPPPP";
										ss="TTTTT";
										count=0;
										scroll_count=0;
										Menu1();
										scroll_count=0;
										}		
								driver.pressKey(new KeyEvent(AndroidKey.BACK));	
								pressCancel(); // sometime popup comes while coming back from wallpepers and themes menu.
								System.out.println("came back ");
								 s3size=100;
								depth--;
								}
							}
							else
							{
									if(tsize!=list2.size())	
									{
										System.out.println("Depth changed  ");
									depth++;
									//Utilities u=new Utilities(driver);l
									//u.subMenu(fos);
									//go to 3rd depth only[0,1,2] where 0th depth is camera setting page. 
									if(depth<3)
									{	
										SSS="NNNNN";
										SS="PPPPP";
										ss="TTTTT";
										count=0;
										scroll_count=0;
										Menu1();
										scroll_count=0;
									}		
									driver.pressKey(new KeyEvent(AndroidKey.BACK));	
									pressCancel();
									System.out.println("came back ");
									 s3size=100;
									depth--;
									}
									else
									{
									//System.out.println("Depth not changed  ");
									//ae.click();	   //clicking again to 
									TouchAction T=new TouchAction(driver);
						    		T.tap(tapOptions().withElement(element(ae))).perform();
						    		Thread.sleep(500);
						    		pressCancel();
						    		//pressOk();
						    		pressLater();
									}
							
							}
						 
				    }
		    		else if(!cond)
		    		{
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
						if(s3.size()>0) {
						AndroidElement lastEEE=s3.get(s3.size()-3);
						AndroidElement lastEE=s3.get(s3.size()-2);
						AndroidElement lastE=s3.get(s3.size()-1);
						SSS=lastEEE.getText();
						SS=lastEE.getText();
						ss=lastE.getText();
						System.out.println("3rd last element is-- "+SSS);
						System.out.println("2nd last element is-- "+SS);
						System.out.println("    last element is-- "+ss);
						}
					}
					catch(Exception e)
					{System.out.println("Among last 3,one or more element not found");
					
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
			if(s3.size()>0) //we can change to cover all module in only one if that s3.size()>0 rather can covering it separately.
			{
				while(swipeDown() && scroll_count<5)
				{
					//if(depth==3 && scroll_count==2)
						//break;
					if(s3size==0)
						break;
					scroll_count++;
					//System.out.println("Scroll done...");
					Menu1();
				}
			}
		//System.out.println("Can not be scrolled more...");
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
			
			java.util.List<AndroidElement> lt1=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch' or @class='android.widget.CheckedTextView' or @class='android.widget.Button' ]"); //
			
			// this condition is done to avoid scrolling in popup cases. 
			//System.out.println("Before scrolling size is--"+lt1.size());
			int b_size=lt1.size();
			if(lt1.size()<10) {
			System.out.println("size small than 10 so couln't scrolled..");
				return false;
			}
		
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
		 Thread.sleep(1000); //changed scroll time t0 1 second from 2 second
		 java.util.List<AndroidElement> lt2=driver.findElementsByXPath("//*[@class='android.widget.TextView' or @class='android.widget.Switch' or @class='android.widget.CheckedTextView' or @class='android.widget.Button']"); //
		 //here S2,s2 are reverse order compare to s1,S1 . it will cause scroll to be true. as s1,S1 & s2,S2  will never be equal.
		 //when we correct s2,S2 then its getting fail sometime . need to correct it. after changing it time taken is very less as it not scrolling unnessasary.
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
		//slast will have any value when last 2 element have been removed otherwise slast="";
		if(s1.equalsIgnoreCase(s2) && (b_size==a_size) && slast.length()!=0)
		{
					System.out.println("Inside true condition");
					return true;
		}
		//returing false when after scrolling last elements is same && size is also same.this got fail in biometric and security ->install unkwon apps .[got fail once only during whole setting so not that critical]		
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
	
	public int pressOk()
	{
	try {
		driver.findElementByXPath("//*[@text='OK']").click();
		System.out.println("Ok clicked  ");
		Thread.sleep(1000);
		return 1;		
		}
		catch(Exception e)
		{
		return 0;
			//System.out.println("Ok Exception ");
		}
	 }

	public void acceptPermissions()
	{
		while(driver.findElementsById("com.android.packageinstaller:id/permission_allow_button").size()>0)
		{
		driver.findElementsById("com.android.packageinstaller:id/permission_allow_button").get(0).click();
		//System.out.println("Permission allowed     ");
		}
		while(driver.findElementsByXPath("//*[@text='Allow']").size()>0)
		driver.findElementsByXPath("//*[@text='Allow']").get(0).click();
		/*
		try
		{
			driver.findElementByXPath("//*[@text='Allow']").click();
			System.out.println("Allowed clicked  ");
		}
		catch(Exception e)
		{	} */
	}

	public void pressCancel()
	{
		try {
			driver.findElementByXPath("//*[@text='CANCEL']").click();
			System.out.println("CANCEL clicked  ");
			}
		catch(Exception e)
		{
			System.out.println("Cancel exception");
		}
		
		try {driver.findElementByXPath("//*[@text='Cancel']").click();
		System.out.println("Cancel clicked  ");
		}
		catch(Exception e)
		{}	
	}
	
	public void pressLater()
	{
		try {
			driver.findElementByXPath("//*[@text='Later']").click();
			System.out.println("Later clicked  ");
			}
		catch(Exception e)
		{
			//System.out.println("Later exception");
		}
		

	}
	
	public void handleKeyboard()
	{
		
		try {	
			if(driver.isKeyboardShown())
	 		driver.hideKeyboard();
			}
		catch(Exception e)	{
			//System.out.println("Problem with keyboard ");
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
	
	public void printFunction(AndroidElement ae) throws IOException
	{
						//System.out.println("checkpoint 2");
					try 
					{   
						//getAttribute(resourceid) throws null if not availabe. but ae.getText() dosn't throw null when not available.
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
							if(ae.getText().length()!=0) 
							{	
							//System.out.println("printing in block 1 ");
						    System.out.println(ae.getText());
						    //System.out.println(submenu.getAttribute("checked"));
							fos.write(ae.getText().getBytes());
							sheet.createRow(rowCount++).createCell(depth).setCellValue(ae.getText());
							}
							else if(!ae.getAttribute("contentDescription").equalsIgnoreCase(", Header") && ae.getAttribute("contentDescription").length()!=0) // this null comparison have some problem .because its basically allow all cont-descp whether it blank(may be it is returning blank not null) or not
							{
								//System.out.println("printing in block 3 length is "+ae.getAttribute("contentDescription").length());
								System.out.println(ae.getAttribute("contentDescription"));
								sheet.createRow(rowCount++).createCell(depth).setCellValue(ae.getAttribute("contentDescription"));
							}
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
					//if("More options".equalsIgnoreCase(ae.getAttribute("contentDescription")))
					if(ae.getText().length()!=0)
					{
					//System.out.println("printing in block 2 ");
					System.out.println(ae.getText());
					fos.write(ae.getText().getBytes());
					//formattedWrite(ae.getText());
					sheet.createRow(rowCount++).createCell(depth).setCellValue(ae.getText());
					}
					//else if(ae.getAttribute("contentDescription")!=null && !ae.getAttribute("contentDescription").equalsIgnoreCase(", Header") && ae.getAttribute("contentDescription").length()!=0) // this null comparison have some problem .because its basically allow all cont-descp whether it blank(may be it is returning blank not null) or not
					else if(!ae.getAttribute("contentDescription").equalsIgnoreCase(", Header") && ae.getAttribute("contentDescription").length()!=0) // this null comparison have some problem .because its basically allow all cont-descp whether it blank(may be it is returning blank not null) or not
					{
						//System.out.println("printing in block 3 length is "+ae.getAttribute("contentDescription").length());
						System.out.println(ae.getAttribute("contentDescription"));
						sheet.createRow(rowCount++).createCell(depth).setCellValue(ae.getAttribute("contentDescription"));
					}
				
					//System.out.println("inside catch of first print block");
				}
	}  //done
}
