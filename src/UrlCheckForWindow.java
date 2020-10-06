import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import com.sun.jna.Library;
import com.sun.jna.Native;

interface Kernel32 extends Library {
	
	boolean SetConsoleTextAttribute(int h_ConsoleOutput, int u16_Attributes);

	int GetStdHandle(int u32_Device);

} 

public class UrlCheckForWindow {
	
			// text for window
			static final int GRAY = 0x7;
			static final int GREEN = 0xA;
			static final int RED = 0xC;
			static final int WHITE = 0xF;
			static final int BLUE = 0x9;
			static final int STD_OUTPUT_HANDLE = -11;

			// request url and check the response
			public static boolean availableURL(String host)
			{
				boolean result = false;
				
				Kernel32 lib = Native.load("kernel32", Kernel32.class);

					try {
							// request url
							URL url = new URL(host);
							URLConnection con;
							con = url.openConnection();
							
							// response
							HttpURLConnection exitCode = (HttpURLConnection) con;
							exitCode.setInstanceFollowRedirects(true);
							HttpURLConnection.setFollowRedirects(true);
							exitCode.setConnectTimeout(1000);
							
							
							// response code result
							if(exitCode.getResponseCode() >= 200 && exitCode.getResponseCode() < 300)
							{ 
								result = true;
								lib.SetConsoleTextAttribute(lib.GetStdHandle(STD_OUTPUT_HANDLE), GREEN);
								System.out.println("["+exitCode.getResponseCode()+"] "+ host +" - Good");							
								lib.SetConsoleTextAttribute(lib.GetStdHandle(STD_OUTPUT_HANDLE), WHITE);
							}
							else if(exitCode.getResponseCode() >= 400 && exitCode.getResponseCode() < 500)
							{
								lib.SetConsoleTextAttribute(lib.GetStdHandle(STD_OUTPUT_HANDLE), RED);
								System.out.println("["+exitCode.getResponseCode()+"] "+ host +" - Bad");					
								lib.SetConsoleTextAttribute(lib.GetStdHandle(STD_OUTPUT_HANDLE), WHITE);
							}
							else if(exitCode.getResponseCode() == 301 || exitCode.getResponseCode() == 307 || exitCode.getResponseCode() == 308 )
							{
								lib.SetConsoleTextAttribute(lib.GetStdHandle(STD_OUTPUT_HANDLE), BLUE);
								System.out.println("["+exitCode.getResponseCode()+"] "+ host +" - Redirect");
								lib.SetConsoleTextAttribute(lib.GetStdHandle(STD_OUTPUT_HANDLE), WHITE);
								
								// redirect to new location by Recursion itself when it is 301,307,308
								String newUrl = exitCode.getHeaderField("Location");
								result = availableURL(newUrl);	
								
							}
							else
							{
								lib.SetConsoleTextAttribute(lib.GetStdHandle(STD_OUTPUT_HANDLE), GRAY);
								System.out.println("["+exitCode.getResponseCode()+"] "+ host +" - Unknown");	
								lib.SetConsoleTextAttribute(lib.GetStdHandle(STD_OUTPUT_HANDLE), WHITE);
							}

					}catch (Exception e) {
						// response fail, server is not existed
						lib.SetConsoleTextAttribute(lib.GetStdHandle(STD_OUTPUT_HANDLE), RED);
						System.out.println("[599] "+ host +" - Fail" );
						lib.SetConsoleTextAttribute(lib.GetStdHandle(STD_OUTPUT_HANDLE), WHITE);
					}
					return result;
			}
}
