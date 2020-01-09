package InnovativeProduct.OSUpgrade;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import org.testng.annotations.Test;

public class FileTest {

	@Test
	public void WriteToFile() throws IOException
	{
		String s="Test Data";
		String s1="Test Data2";
		char c='a';
		File f=new File("C:\\Users\\nayan.mittal\\Desktop\\Data\\Result\\CameraSettigs.txt");
		FileOutputStream fos=new FileOutputStream(f);
		//fos.write(c);	
		fos.write(s.getBytes());
		//fos.write();
		fos.write(s1.getBytes());	
		fos.close();
	}
	
	
	

}
