
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.sun.jna.Library;
import com.sun.jna.Native;

interface Kernel32 extends Library {
	
	boolean SetConsoleTextAttribute(int h_ConsoleOutput, int u16_Attributes);

	int GetStdHandle(int u32_Device);

}

public class UrlCheck {
		// url set regex
		final static String regex = "(https?):\\/\\/[-a-zA-Z0-9+&@#%?=~_|!:,.;]*[-a-zA-Z0-9+&@#%=~_|\\/]*";

		// delimiter to get url from input file 
		final static String delimiter = "[\\[\\]\"<>'\n\b\r]";
		
		//text for mac
//	    public static final String RED = "\033[0;31m";
//	    public static final String GREEN = "\033[0;32m";
//	    public static final String WHITE = "\033[0;37m";
//	    public static final String BLUE ="\033[0;34m";
//	    public static final String RESET = "\033[0m";

		// text for window
		static final int GRAY = 0x7;
		static final int GREEN = 0xA;
		static final int RED = 0xC;
		static final int WHITE = 0xF;
		static final int BLUE = 0x9;
		static final int STD_OUTPUT_HANDLE = -11;

	    
		// request url and check the response
		public static void availableURL(String host)
		{
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
							availableURL(newUrl);	
							
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
						// for Mac
//						if(exitCode.getResponseCode() >= 200 && exitCode.getResponseCode() < 300)
//						{ 
//
//							System.out.println(GREEN+"["+exitCode.getResponseCode()+"] "+ host +" - Good"+RESET);							
//						
//						}
//						else if(exitCode.getResponseCode() >= 400 && exitCode.getResponseCode() < 500)
//						{
//							System.out.println(RED+"["+exitCode.getResponseCode()+"] "+ host +" - Bad"+RESET);					
//						}
//						else if(exitCode.getResponseCode() == 301 || exitCode.getResponseCode() == 307 || exitCode.getResponseCode() == 308 )
//						{
//							System.out.println(BLUE + "["+exitCode.getResponseCode()+"] "+ host +" - Redirect"+ RESET);
//							
//							// redirect to new location by Recursion itself when it is 301,307,308
//							String newUrl = exitCode.getHeaderField("Location");
//							availableURL(newUrl);	
//							
//						}
//						else
//						{
//							System.out.println(RED+"["+exitCode.getResponseCode()+"] "+ host +" - Unknown"+RESET);	
//						}
//
//				}catch (Exception e) {
//					// response fail, server is not existed
//					System.out.println(RED+"[599] "+ host +" - Fail" +RESET);
//
//				}		
						

		}
		
		public static void helpMessage()
		{
			System.out.println("========================================================");
			System.out.println("|               Help message                           |");
			System.out.println("========================================================");
			System.out.println("| Please Type proper argument                          |");
			System.out.println("|                                                      |");
			System.out.println("| 1) UrlCheck <fileName>                               |");
			System.out.println("| ex) UrlCheck index.html                              |");
			System.out.println("|     UrlCheck index.html index2.html                  |");
			System.out.println("|                                                      |");
			System.out.println("| 2) UrlCheck help                                     |");
			System.out.println("|                                                      |");
			System.out.println("| 3) UrlCheck -a <fileName>                            |");
			System.out.println("| : To check for archived versions of URLs             |");
			System.out.println("| ex) UrlCheck -a index2.html                          |");
			System.out.println("|                                                      |");
			System.out.println("| 4) UrlCheck -s <fileName>                            |");
			System.out.println("| : To request URLs with https                         |");
			System.out.println("| ex) UrlCheck -s index2.html                          |");
			System.out.println("|                                                      |");
			System.out.println("| Thanks!                                              |");
			
		}
		
		public static void startMessage()
		{
			System.out.println("========================================================");
			System.out.println("|             URL TEST RUNNING                         |");
			System.out.println("========================================================");

		}
		
		public static void endMessage()
		{
			
			System.out.println("========================================================");
		}
		

		// list up url in input file
		public static void fileUrlListUp(String fName, boolean archived, boolean secured)
		{
			String regSecure = "^(http)://";
			Pattern pat = Pattern.compile(regex, Pattern.MULTILINE);
			
			BufferedReader br;
			
			try {
				br = new BufferedReader(new FileReader(fName));
				String line = null;
				while((line = br.readLine()) != null)
				{
					String[] values = line.split(delimiter);
					for(String str : values)
					{
						Matcher matcher = pat.matcher(str);
						if(matcher.find())
						{
							// change http to https
							if(secured) str = str.replaceFirst(regSecure, "https://");	

							availableURL(str);
							
							// request archived
							if(archived) archiveUrl(str);
						}
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				
				System.out.println("File not Found");
			} catch (IOException e) {
				System.out.println("File not Found");
			}

		}
		
		// Archive API
		public static void archiveUrl(String host)
		{
		
			String apiUrl = "http://archive.org/wayback/available?url=" + host;
			StringBuilder sb = new StringBuilder();
			

			try {
				URL	url = new URL(apiUrl);
				URLConnection con;
				con = url.openConnection();
				
				
				BufferedReader brd = new BufferedReader(new InputStreamReader(con.getInputStream(),Charset.defaultCharset()));
				if(brd != null)
				{
					int cp;
					while((cp = brd.read())!= -1)
					{
						sb.append((char)cp);
					}
					brd.close();
				}
				System.out.println(sb);
				
			}catch(Exception e)
			{
				throw new RuntimeException("Exception URL : "+ host, e);
			}
		}
		
	public static void main(String[] args) {
		
		boolean archived = false;
		boolean secured = false;
		
		if(args.length == 0 || args[0].toLowerCase().equals("help"))
		{
			helpMessage();
		}
		else 
		{
			startMessage();
			
			// command line flag a, s
			if(args[0].startsWith("-"))
			{
				// archive flag handle
				if(args[0].contains("a")) archived = true;
		
				// secure request flag handle
				if(args[0].contains("s")) secured = true;
				
				for(int i = 1; i < args.length; i++)
				{
					System.out.println("File :  " + args[i]);
					fileUrlListUp(args[i],archived,secured);					
				}	
				
			}
			else {
				for(int i = 0; i < args.length; i++)
				{
					System.out.println("File :  " + args[i]);
					fileUrlListUp(args[i],archived,secured);					
				}
			}
		}
		
		endMessage();

	}

}
