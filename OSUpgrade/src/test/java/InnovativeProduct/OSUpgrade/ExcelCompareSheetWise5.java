package InnovativeProduct.OSUpgrade;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;

/* It will Search Data of DUT in Ref.
 * Write Data of DUT & Ref into 3rd Excel.
 * Ref. data starts from 10th col. in 3rd Excel. col2=col1+10.
 * if match then ForeGround Green & Result[Col+5] is True else Red & False 
 * Also Non Traversed cell of Excel 2 Will be of not any color.Print them & Fail cases in Log file.[Fail Case : Parent->Child->Subchild]
 * Cell colored once should not be traversed again.for this if matched and already coloured then skip it.But it might be that colouring is done at the end of test so not sure
 * that it might show colored[traversed cell] in the end only.
 *Final Thought : if matched then green color in both otherwise don't fill color
 * Generate Log in Text File.
 * Add Remarks
 * change depth when enters modes .
 *Need to add code when sheets count is more in excel2.
 
 *[1->2]On/off handling done.
 *Marking Red[Fail] remaining cells of device 2 using method markRED().
 *Makring Red[Fail] remaining cells of device 1 using method markRED2(); marking submenu red,fail for which main menu is fail.
 *[2->3]Changing it to run automatically from the main program
 *[3->4]Changed main method to excel compare .It will get call from AppiumParallelExecution.
 *Added time in file name.Update the logic to compare only sheets which are in both excel1 and excel 2..
 *Added Model,OS name in output file and in row 0 of each sheet.
 *[4-5]Updating it for 4 colummn
 */



public class ExcelCompareSheetWise5 {

static String previous="";
static String excelName;
static String dName1,dName2,osVer1,osVer2;


ExcelCompareSheetWise5(String sheetName1,String sheetName2,String deviceName1,String deviceName2,String osVersion1,String osVersion2)
{
	dName1=deviceName1;
	dName2=deviceName2;
	osVer1=osVersion1;
	osVer2=osVersion2;
	excelCompare(sheetName1,sheetName2);
}


public static void excelCompare(String sheetName1,String sheetName2)
{
	try
		{
		Thread.sleep(10000);
		//File f1=new File("C:\\Users\\nayan.mittal\\Desktop\\Data\\Result\\M30 PvQ\\CameraSettings_M20Q.xlsx");
		File f1=new File(sheetName1);
		FileInputStream fin1=new FileInputStream(f1);	
		XSSFWorkbook wb1=new XSSFWorkbook(fin1);	
		int sheetCountExcel1=wb1.getNumberOfSheets();

		//File f2=new File("C:\\Users\\nayan.mittal\\Desktop\\Data\\Result\\M30 PvQ\\M30CameraSettings_PP.xlsx");
		File f2=new File(sheetName2);
		FileInputStream fin2=new FileInputStream(f2);	
		XSSFWorkbook wb2=new XSSFWorkbook(fin2);	
		int sheetCountExcel2=wb2.getNumberOfSheets();
	
		int sheetsForComparsion=sheetCountExcel1<sheetCountExcel2?sheetCountExcel1:sheetCountExcel2;
		//excelName="C:\\Users\\nayan.mittal\\Desktop\\Data\\Result\\M30 PvQ\\TestResultCamera.xlsx";
		Timestamp time=new Timestamp(System.currentTimeMillis());
		
		//excelName=AppiumParallelExecution.path+"\\"+"TestResult"+" "+AppiumParallelExecution.testItem+"_"+time+".xlsx";
		String name=dName1+"_OS"+osVer1+"_Vs_"+dName2+"_OS"+osVer2;
		excelName=AppiumParallelExecution.path+"\\"+"TestResult"+"_"+AppiumParallelExecution.testItem+"_"+name+".xlsx";
		excelData(wb1,wb2);
		System.out.println("Data Written to Test Result File");
		
		FileInputStream third_in=new FileInputStream(excelName);	
		XSSFWorkbook wb3=new XSSFWorkbook(third_in);	
		FileOutputStream third_out=new FileOutputStream(excelName);
    
		XSSFSheet sheet1;XSSFSheet sheet3;
				/*
				for(int a=0;a<sheetsForComparsion;a++)
				{	
					sheet1= wb1.getSheetAt(a);
					//XSSFSheet sheet2=wb2.getSheetAt(0);
					int lastRowN1=sheet1.getLastRowNum();  
					//int lastRowN2=sheet2.getLastRowNum(); 
	
					sheet3=wb3.getSheetAt(a);
					int lastRowN3=sheet3.getLastRowNum();
					System.out.println(sheet3.getSheetName());
					System.out.println("Last row of this page is  "+lastRowN3);
							for(int k=0;k<=lastRowN1;k++) 
							{   
							//As first row in excel 3 is Depth 0,1,2 .
							compare(k+1,0,1,lastRowN3,sheet3,wb3);
							// cell_count=row.getLastCellNum();//it will return 3 if total 3 column. 
							}
							markFail(wb3,sheet3,lastRowN3);
							markFail2(wb3,sheet3,lastRowN3);
				}
				*/
					for(int a=0;a<sheetCountExcel1;a++)
					{	
						sheet1= wb1.getSheetAt(a);
						if(wb2.getSheet(sheet1.getSheetName())==null)
							continue;
							
						//XSSFSheet sheet2=wb2.getSheetAt(0);
						int lastRowN1=sheet1.getLastRowNum();  
						//int lastRowN2=sheet2.getLastRowNum(); 
			
						sheet3=wb3.getSheetAt(a);
						int lastRowN3=sheet3.getLastRowNum();
						System.out.println(sheet3.getSheetName());
						System.out.println("Last row of this page is  "+lastRowN3);
								for(int k=0;k<=lastRowN1;k++) 
								{   
								//As first row in excel 3 is Depth 0,1,2 .
								compare(k+1,0,1,lastRowN3,sheet3,wb3);
								// cell_count=row.getLastCellNum();//it will return 3 if total 3 column. 
								}
								markFail(wb3,sheet3,lastRowN3);
								markFail2(wb3,sheet3,lastRowN3);
					}
							
				
				
				
				
				
  			wb1.close();
  			wb2.close();
  			wb3.write(third_out);
  			wb3.close();
		}
 		catch(Exception e)
		{
 			System.out.println("========Inside Main Catch of Excel =============");	
 			e.printStackTrace();
		}
}



public static void excelData(XSSFWorkbook wb1,XSSFWorkbook wb2) throws IOException
{
	FileOutputStream fout3 = null;
	XSSFWorkbook wb3 = new XSSFWorkbook();
	try
	{
			//String excelName="C:\\Users\\nayan.mittal\\Desktop\\Data\\Result\\M30 PvQ\\TestResultCamera.xlsx";
			File f3=new File(excelName);
			fout3=new FileOutputStream(f3);
			int sheetCountExcel1=wb1.getNumberOfSheets();
			int sheetCountExcel2=wb2.getNumberOfSheets();
			int totalSheets=sheetCountExcel1>sheetCountExcel2?sheetCountExcel1:sheetCountExcel2;
			XSSFSheet sheet1,sheet2;
				for(int i=0;i<sheetCountExcel1;i++)  //It will add all sheets of excel1 and matched sheet of excel 2.
				{
					sheet1=wb1.getSheetAt(i);
					sheet2=wb2.getSheet(sheet1.getSheetName()); // throws null if sheet not found. but below method can handle null.
					sheetWiseData(sheet1,sheet2,wb3);		
				}		
				
				for(int j=0;j<sheetCountExcel2;j++)  //It will add excel 2 sheets which are not in excel 1.
				{
					sheet2=wb2.getSheetAt(j);
					sheet1=wb1.getSheet(sheet2.getSheetName()); 
					if(sheet1==null)
					sheetWiseData(sheet1,sheet2,wb3);		
				}
				
    
    
				wb3.write(fout3);		
				wb3.close();
	}
	catch(Exception e)
	{
		wb3.write(fout3);		
		wb3.close();
		System.out.println("=====Inside excelData Method Catch=======");
		
	}
	
}


//copyData from 2 sheet of excel 1 & excel 2 to 3rd excel .
//public static void copyData(XSSFSheet sheet1,XSSFSheet sheet2) this method can handle null 
public static void sheetWiseData(XSSFSheet sheet1,XSSFSheet sheet2,XSSFWorkbook wb) 
{
	try
	{
		String sheetName;
		if(sheet1!=null)
			sheetName=sheet1.getSheetName();
		else
			sheetName=sheet2.getSheetName();
		
		XSSFSheet sheet=wb.createSheet(sheetName);
	    XSSFRow row=sheet.createRow(0);
	    XSSFCell cell,newCell; 
	   // XSSFCellStyle newCellStyle = wb.createCellStyle();
 
	    row.createCell(0).setCellValue("Depth 0 "+dName1+"_OS_"+osVer1);
	    row.createCell(1).setCellValue("Depth 1");
	    row.createCell(2).setCellValue("Depth 2");
	    row.createCell(3).setCellValue("Depth 3");
	    row.createCell(5).setCellValue("Test Result"); //Depth2+3
	    
	    row.createCell(9).setCellValue("Depth 0 "+dName2+"_OS_"+osVer2);
	    row.createCell(10).setCellValue("Depth 1");
	    row.createCell(11).setCellValue("Depth 2");
	    row.createCell(12).setCellValue("Depth 3");
	   //row.createCell(14).setCellValue("Test Result"); //Depth2+3

	   
	    if(sheet1!=null)
	    {
	    	XSSFRow row1;
	    	XSSFCell cell1;
	    		for(int i=0;i<=sheet1.getLastRowNum();i++)
	    		{
	    			row1=sheet1.getRow(i);
	    			row=sheet.createRow(i+1);
	    			for(int j=0;j<row1.getLastCellNum();j++)
	    			{
	    				cell1=row1.getCell(j,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	    				newCell=row.createCell(j);
	    				//row.createCell(j).setCellValue(cell1.toString();
	    				//her copy cell instead of copying value.search for copy method.	
	    				//newCell.copyCellFrom(cell1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	    				//newCellStyle.cloneStyleFrom(cell1.getCellStyle());
	    				// newCell.setCellStyle(newCellStyle);
	    		
	    					if(cell1.getCellType()==CellType.BLANK)
	    					{ newCell.setCellType(CellType.BLANK);
	    					}
	    					else if(cell1.getCellType()==CellType.STRING)
	    					{ newCell.setCellValue(cell1.getRichStringCellValue()); 
	    					}
	    			}	
	    		}
	    
	    		Thread.sleep(3000);
	    }
	    
	    
	    if(sheet2!=null)
	    {
	    	XSSFRow row2;
	    	XSSFCell cell2;
	    		for(int i=0;i<=sheet2.getLastRowNum();i++)
	    		{
	    			row2=sheet2.getRow(i);
	    	 		if(sheet1==null || (sheet2.getLastRowNum()>sheet1.getLastRowNum() && i>sheet1.getLastRowNum()))
	    				row=sheet.createRow(i+1);
	    			else
	    				row=sheet.getRow(i+1);
	    	
	    				// Not sure that we need to create row again if we have already created it for sheet1.
	    				for(int j=0;j<row2.getLastCellNum();j++)
	    				{
	    					cell2=row2.getCell(j,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	    					newCell=row.createCell(j+9);
	    		
	    					if(cell2.getCellType()==CellType.BLANK)
	    					{ newCell.setCellType(CellType.BLANK);
	    					}
	    					else if(cell2.getCellType()==CellType.STRING)
	    					{ newCell.setCellValue(cell2.getRichStringCellValue()); 
	    					}
	    		
	    				}	
	    		}
	    		Thread.sleep(3000);
	    }
	
	
	    // wb.write(fout);
	    //wb.close();
	   	 //return wb;
	}
	catch(Exception e)
	{
		System.out.println("====Inside Copy Data Catch====");
		//return wb;
	}


}


public static void compare(int rowNN,int colNN,int row_start,int row_end,XSSFSheet sheet,XSSFWorkbook wb)
{
	 	XSSFRow row = null;
	  	try
	 	{
	    int rowN,colN;
	    rowN=rowNN;
	    colN=colNN; 
	   	XSSFCell cell=null;
	   	XSSFCell cell2 = null;
		String text="";
		
		try {
		row=sheet.getRow(rowN); //current row
		cell=row.getCell(colN,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);	
		text=cell.toString(); //this throwing null pointer exception 
		//System.out.println("checkpoint");
		}
		catch(Exception e)
		{   
			//Here we can implement the code to mark red col2 and col3 data [not matched as main menu not matched so control will not go in the depth]  
			//System.out.println("flow is here");
		}
		if(text.length()!=0)    //Not printing & Counting the size of submenu's When cell is blank.
		{
				int rowSecond=findCell(sheet,colN+9,row_start,row_end,text,previous);// it will return the row number of the cell.
				//System.out.println(" checkpoint  1");
				if(rowSecond!=-1)
					{
						previous=text;
					    cell2=sheet.getRow(rowSecond).getCell(colN+9); //because cell is already created.
					    setColour(wb,cell2,"GREEN");
					    //sheet.getRow(rowSecond).createCell(14).setCellValue("Pass");
						//// here we are marking Ref. device cell Green or Red .Also Pass or Fail. 
					    
						row_start=rowSecond;
						System.out.println(text+" found in other excel at row "+rowSecond);
						setColour(wb,cell,"GREEN");
						row.createCell(5).setCellValue("Pass");
						
						int subLength,subLength2;
						//System.out.println(" checkpoint  2");
						List<String> list1=new ArrayList<String>();
						List<String> list2=new ArrayList<String>();
						List<Object> al1=count(sheet,rowN,colN,list1);
						List<Object> al2=count(sheet,rowSecond,colN+9,list2);
						subLength=(Integer) al1.get(0);
						list1=(List<String>) al1.get(1);

						if(subLength!=0) //
						{
							System.out.println("Sublist 1 Length is ->"+subLength);
							System.out.println("Sublist 1 is        ->"+list1);
							subLength2=(Integer) al2.get(0);
							list2=(List<String>) al2.get(1);
							System.out.println("Sublist 1 Length is ->"+subLength2);
							System.out.println("Sublist 2 is        ->"+list2);
							//System.out.println("Submenu length is "+subLength);  // counts excluding blanks at submenu's.
							//Go to next depth &  depth(column) should be less than 3(0,1,2).
							//System.out.println("SubMenu list of DUT 1  "+list1); // list dosn't include blank cell
							//System.out.println("SubMenu list of DUT 2  "+list2);
							rowN++;colN++;
							row_start++;row_end=row_start+subLength2-1;
							for(int i=rowN;i<(rowN+subLength);i++)
 							{
							compare(i,colN,row_start,row_end,sheet,wb);
 							////here we can change code if element matched then start matched from next element or don't include that element.
							}											
						}
					}
					else
					{
						setColour(wb,cell,"RED");
						row.createCell(5).setCellValue("Fail");
						System.out.println(cell+"-     not found    ");
						//if not found then red
					}
		}

	}
	catch(Exception e)
	{
 	System.out.println("========Inside Compare Catch=============");	
 	//e.printStackTrace();
 	}
}
	
//returns blank cell below current cell & saves sublist of current menu in list.
public static List<Object> count(XSSFSheet sheet,int row,int col,List<String> list)
{
	System.out.println("Row is  "+row+" Col is "+col);
	//System.out.println("checkpoint 0");
	int i=0;	
	XSSFCell cell;
	XSSFRow row2;
	list=new ArrayList<String>();
	try {
		    row2=sheet.getRow(++row);

			cell=row2.getCell(col,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);  //below cell .Throws null
			//System.out.println("Below Cell Is    "+cell);

			//System.out.println("Type is   "+cell.getCellType());
			
			while(cell.getCellType()==CellType.BLANK)
			{
				if(col>0 && row2.getCell(col-1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().length()!=0) //cell at below->left should be blank.
					 break;
				
				if(row2.getCell(col+1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().length()!=0) //Not adding blank cell[below-right] in the list.
				{
				list.add(row2.getCell(col+1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()); 
				//System.out.println("Below right cell is "+list.add(row2.getCell(col+1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
				}
				i++;
				row2=sheet.getRow(++row);
				//cell=sheet.getRow(row).getCell(col);
				cell=row2.getCell(col,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				
			}
			return Arrays.asList(i,list);	
	}
	catch(Exception e)
	{
	   System.out.println("========Inside Count Catch===============");
	   return Arrays.asList(i,list);	 	
	}
	finally
	{
		//System.out.println("SubMenu list is "+list); // list dosn't include blank cell
	}
}

//findCell only in the mentioned range && if is not traversed before[means we havn't chagned colour] && 
//return -1 if not found.
public static int findCell(XSSFSheet sheet,int col,int row_start,int row_end,String value,String previous)
{
	String pre;
	int i=0;
	XSSFCell cell;
	short color;
	try {
		//XSSFSheet sheet=wb.getSheetAt(0);
		for(i=row_start;i<=row_end;i++)
			{
			cell=sheet.getRow(i).getCell(col,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			color=cell.getCellStyle().getFillForegroundColor();
			
			//System.out.println("Already colored  "+color); // 17 index value is for green. 64 default
			if((color!=IndexedColors.GREEN.getIndex()) && cell.toString().equalsIgnoreCase(value))
			{
				if(value.equalsIgnoreCase("on") || value.equalsIgnoreCase("off"))
				{
					pre=sheet.getRow(i-1).getCell(col,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
					if(pre.length()==0)
					pre=sheet.getRow(i-1).getCell(col+1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
					
					if(!pre.equalsIgnoreCase(previous))
					continue;
				}
				return i;
			}
			}
		return -1;
	}
	catch(Exception e)
	{
		System.out.println("========Inside findCell Catch=========");
		return -1;
	}
}

public static void setColour(XSSFWorkbook wb,XSSFCell cell,String Colour) throws IOException
{
	
	CellStyle style=wb.createCellStyle();
	if(Colour.equalsIgnoreCase("GREEN"))
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	else
		style.setFillForegroundColor(IndexedColors.RED.getIndex());

    style.setFillPattern(FillPatternType.BIG_SPOTS);  
    cell.setCellStyle(style);

 /*
	CellStyle style=wb.createCellStyle();
	style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
    style.setFillPattern(FillPatternType.BIG_SPOTS);  
    cell.setCellStyle(style);
*/
}

//right 3 column which are not coloured will be coloured as red.
public static void markFail(XSSFWorkbook wb,XSSFSheet sheet,int lastRowNum3)
{
XSSFCell cell;
short color;
String value;
 	try 
 	{
 		for(int i=1;i<=lastRowNum3;i++)
 		{
 			for(int j=9;j<=12;j++)
 			{
 				cell=sheet.getRow(i).getCell(j,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
 				value=cell.toString();
 				if(value.length()!=0) 
 				{
 					color=cell.getCellStyle().getFillForegroundColor();
 					if(color!=IndexedColors.GREEN.getIndex())
 						setColour(wb,cell,"RED");
 				}
 			}
 		}
 	}
 	catch(Exception e)
 	{}
}

//left 3 column of sheet which are not coloured will be marked red and fail will be written in result.
public static void markFail2(XSSFWorkbook wb,XSSFSheet sheet,int lastRowNum3)
{
XSSFCell cell;
short color;
String value;
XSSFRow row;
 	try 
 	{
 		for(int i=1;i<=lastRowNum3;i++)
 		{
 			for(int j=0;j<=3;j++)
 			{
 				row=sheet.getRow(i);
 				cell=row.getCell(j,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
 				value=cell.toString();
 				if(value.length()!=0) 
 				{
 					color=cell.getCellStyle().getFillForegroundColor();
 					if(color!=IndexedColors.GREEN.getIndex() && color!=IndexedColors.RED.getIndex() )
 					{
 						setColour(wb,cell,"RED");
 						row.createCell(5).setCellValue("Fail");
 					}
 				}
 			}
 		}
 	}
 	catch(Exception e)
 	{}
}


}