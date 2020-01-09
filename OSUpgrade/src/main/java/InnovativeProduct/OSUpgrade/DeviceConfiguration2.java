package InnovativeProduct.OSUpgrade;

import java.util.HashMap;
import java.util.Map;

/*[1-2]Added imei and apVersion.
 * 
 */
		
public class DeviceConfiguration2 {

	
	
	
	CommandPrompt cmd = new CommandPrompt();
	HashMap<String, String> devices = new HashMap<String, String>();
	
	/**
	 * This method start adb server
	 */
	public void startADB() throws Exception{
		String output = cmd.runCommand("adb start-server");
		String[] lines = output.split("\n");
		if(lines.length==1)
			System.out.println("adb service already started");
		else if(lines[1].equalsIgnoreCase("* daemon started successfully *"))
			System.out.println("adb service started");
		else if(lines[0].contains("internal or external command")){
			System.out.println("adb path not set in system varibale");
			System.exit(0);
		}
	}
	
	/**
	 * This method stop adb server
	 */
	public void stopADB() throws Exception{
		cmd.runCommand("adb kill-server");
	}
	
	/**
	 * This method return connected devices
	 * @return hashmap of connected devices information
	 */
	public HashMap<String, String> getDevices() throws Exception	{
		
		startADB(); // start adb service
		String output = cmd.runCommand("adb devices");
		String[] lines = output.split("\n");

		if(lines.length<=1){
			System.out.println("No Device Connected");
			stopADB();
			System.exit(0);	// exit if no connected devices found
		}
		
		for(int i=1;i<lines.length;i++){
			lines[i]=lines[i].replaceAll("\\s+", "");
			
			if(lines[i].contains("device")){
				lines[i]=lines[i].replaceAll("device", "");
				String deviceID = lines[i];
				String model = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.model").replaceAll("\\s+", "");
				String brand = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.brand").replaceAll("\\s+", "");
				String osVersion = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.build.version.release").replaceAll("\\s+", "");
				String apVersion=cmd.runCommand("adb -s "+deviceID+" shell getprop ro.build.PDA").replaceAll("\\s+", "");
				//String imei=cmd.runCommand("adb -s "+deviceID+" shell service call iphonesubinfo 1 | grep -o '[0-9a-f]\\{8\\} ' | tail -n+3 | while read a; do echo -n \\\\u${a:4:4}\\\\u${a:0:4}; done").replaceAll("\\s+", "");
				String imei=cmd.runCommand("adb -s "+deviceID+"\"shell service call iphonesubinfo 1 | grep -o \'[0-9a-f]\\\\{8\\\\} \' | tail -n+3 | while read a; do echo -n \\\\\\\\u${a:4:4}\\\\\\\\u${a:0:4}; done\"").replaceAll("\\s+", "");
				
				String deviceName = model;
				
				devices.put("deviceID"+i, deviceID);
				devices.put("deviceName"+i, deviceName);
				devices.put("osVersion"+i, osVersion);
				devices.put("apVersion"+i, apVersion);
				devices.put("imei"+i, imei);
				//System.out.println("Following device is connected");
				//System.out.println(deviceID+" "+deviceName+" "+osVersion+"\n");
				//System.out.println(deviceID+"\n");
								
			}else if(lines[i].contains("unauthorized")){
				lines[i]=lines[i].replaceAll("unauthorized", "");
				String deviceID = lines[i];
				
				System.out.println("Following device is unauthorized");
				System.out.println(deviceID+"\n");
			}else if(lines[i].contains("offline")){
				lines[i]=lines[i].replaceAll("offline", "");
				String deviceID = lines[i];
				
				System.out.println("Following device is offline");
				System.out.println(deviceID+"\n");
			}
		}
		return devices;
	}
	
	public static void main(String[] args) throws Exception {
		DeviceConfiguration2 gd = new DeviceConfiguration2();
		gd.startADB();
		HashMap<String,String> details=new HashMap<String,String>();
		details=gd.getDevices();
		//gd.stopADB();
		System.out.println("Devices details are "+details);
		
		
		System.out.println(details.get("deviceID1"));
		System.out.println(details.get("deviceID2"));
	}
	
	public static void getDetails()
	{
		
		
	}
	
	
	
}
