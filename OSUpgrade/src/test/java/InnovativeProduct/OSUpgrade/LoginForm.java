package InnovativeProduct.OSUpgrade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.OpenType;

public class LoginForm extends JFrame {
 JLabel l1;
 JTextField tf1;
 //btn1-start button
 JButton btn1=new JButton("Start");
 //upload exel button
 JButton btn2;
 //module Name
 JLabel mName;
 //upload notepad button 
 //JButton btn3;
 //reset button
 JButton btn4;
 String testItem="";
 //JTextField p1;
	Choice language;
 JFrame frame;
 JProgressBar progressBar=new JProgressBar();
 JLabel message=new JLabel();//Crating a JLabel for displaying the message
 String fileName;
 LoginForm() throws InterruptedException {
	 createGUI();
	 //addProgressBar();
	 addMessage();
  setMsg(); 
 }
 
 public void createGUI(){
	 frame = new JFrame("Setting Comparison Automation Tool[SCAT]");
	 l1 = new JLabel("SCAT Tool");
	  l1.setForeground(Color.blue);
	  l1.setFont(new Font("Serif", Font.BOLD, 20));

	 
	  tf1 = new JTextField();
	  //p1 = new JTextField();
	  //btn1 = new JButton("Start");
	  btn4 = new JButton("Reset");
	  btn2 = new JButton("Excel Path");
	  mName = new JLabel("Select Test");
	  //btn3 = new JButton("Notepad Path");
	  l1.setBounds(100, 30, 400, 30);
		language=new Choice();
	  
   	this.addWindowListener(new WindowAdapter() {
 		@Override
 		public void windowClosing(WindowEvent e) {
 	                // Dispose the window after the close button is clicked.
 			 			dispose();
 			
 		}
 	});
	  
	  
	  
	  
	  btn2.setBounds(80, 70, 200, 30);
	  btn2.setBackground(Color.LIGHT_GRAY);
	  btn2.setForeground(Color.BLACK);
	  btn2.addActionListener(new ActionListener(){  
		  public void actionPerformed(ActionEvent e){  
			  final JFileChooser fc=new JFileChooser();
	            //String fileName;
	            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            int response=fc.showOpenDialog(LoginForm.this);
	            if(response==JFileChooser.APPROVE_OPTION) {
	            	tf1.setText(fc.getSelectedFile().toString());
	            	fileName=fc.getSelectedFile().toString();
	            	System.out.println(fileName);
	            }else {
	            	tf1.setText("The file open operation was cancelled");
	            	fileName="The file open operation was cancelled";
	            };   
		  }  
		  });  
	 /*
	  btn3.setBounds(80, 110, 200, 30);;
	  btn3.setBackground(Color.LIGHT_GRAY);
	  btn3.setForeground(Color.BLACK);
	  btn3.addActionListener(new ActionListener(){  
		  public void actionPerformed(ActionEvent e){  
		            final JFileChooser fc=new JFileChooser();
		            String fileName;
		            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		            int response=fc.showOpenDialog(LoginForm.this);
		            if(response==JFileChooser.APPROVE_OPTION) {
		            	p1.setText(fc.getSelectedFile().toString());
		            	fileName=fc.getSelectedFile().toString();
		            }else {
		            	p1.setText("The file open operation was cancelled");
		            	fileName="The file open operation was cancelled";
		            };
		  }  
		  });
	  */
	  
	  btn4.addActionListener(new ActionListener(){  
		  public void actionPerformed(ActionEvent e){  
		          tf1.setText("");
		          //p1.setText("");
		  }  
		  });
	  tf1.setBounds(300, 70, 200, 30);
	  language.setBounds(300,110,200,50);
	  mName.setBounds(150, 110, 140, 25);
	  mName.setForeground(Color.black);
	  mName.setFont(new Font("Serif", Font.BOLD, 18));
	  //p1.setBounds(300, 110, 200, 30);
	  btn1.setBounds(150, 160, 100, 30);
	  btn1.addActionListener(new ActionListener(){  
		  public void actionPerformed(ActionEvent e){  
			  //runningPBar();
			  //message.setText("Tool Started");
			  testItem=language.getSelectedItem();
			   System.out.println(testItem);
			AppiumParallelExecution.startParallelExecution(fileName,testItem);

			//Thread.sleep(10000);
			 //message.setText("Tool Completed    ");
		  }  
		  }); 
	  btn4.setBounds(250, 160, 100, 30);
	  frame.add(l1);
	
	  frame.add(tf1);
	 
	  //frame.add(p1);
	  frame.add(btn1);
	  frame.add(btn2);
	  //frame.add(btn3);
	  frame.add(btn4);
	  frame.add(mName);
	  frame.add(language);
   	language.add("Setting_Comparison");
   	language.add("Camera_Setting_Comparison");
     frame.setSize(600,400);
  
     frame.setBackground(Color.MAGENTA);
     frame.setLayout(null);
     frame.setVisible(true);
     frame.setResizable(false);
     setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
 }

public void addProgressBar(){
	//progressBar=new JProgressBar();
    progressBar.setBounds(100,280,400,30);
    progressBar.setBorderPainted(true);
    progressBar.setStringPainted(true);
    progressBar.setBackground(Color.WHITE);
    progressBar.setForeground(Color.BLACK);
    //progressBar.setValue(0);
    frame.add(progressBar);
}
public void addMessage()
{      
	//message=new JLabel();
    message.setBounds(100,310,250,50);//Setting the size and location of the label
    message.setForeground(Color.black);//Setting foreground Color
    message.setFont(new Font("arial",Font.BOLD,20));//Setting font properties
    message.setText("Tool Started  ");
    frame.add(message);//adding label to the frame
}

public void setMsg() throws InterruptedException
{
	Thread.sleep(5000);
	message.setText("Test Ongoing   ");
    while(true)
    {
    	Thread.sleep(5000);
    	//System.out.println("while loop running");
    if(AppiumParallelExecution.end)	
    	{
    	System.out.println("value of end is true ");
    	message.setText("Executed successfully");
    	message.setForeground(Color.GREEN);
    	Thread.sleep(20000);
    	System.exit(0);
    	}
    }

}

public void windowwClosing(WindowEvent e) {
	this.dispose();
}

public void runningPBar() throws InterruptedException{
    int i=0;//Creating an integer variable and intializing it to 0
    		progressBar.setIndeterminate(true);
    // here we will change it to false when our test gets executed instead of 5 second
    //Thread.sleep(10000);
    //progressBar.setIndeterminate(false);
   // message.setText("Tool is running ");
    while( i<=100)
    {
        try{
            Thread.sleep(50);//Pausing execution for 50 milliseconds
            //progressBar.setValue(i);//Setting value of Progress Bar
            //message.setText("LOADING "+Integer.toString(i)+"%");//Setting text of the message JLabel
            i++;
            if(i>=100)
            	{
            	//progressBar.setVisible(false);
            	progressBar.setIndeterminate(false);
                 message.setText("Executed successfully");
            	  message.setForeground(Color.GREEN);
            	}
                
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}}
