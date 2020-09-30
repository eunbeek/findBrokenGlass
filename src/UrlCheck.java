
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlCheck {
		// url set regex
		final static String regex = "(https?):\\/\\/[-a-zA-Z0-9+&@#%?=~_|!:,.;]*[-a-zA-Z0-9+&@#%=~_|\\/]*";

		// delimiter to get url from input file 
		final static String delimiter = "[\\[\\]\"<>'\n\b\r]";
			
		
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
			System.out.println("| 3) Option a, s, v, m                                 |");
			System.out.println("|                                                      |");
			System.out.println("|  For Window :                                        |");
			System.out.println("|                                                      |");
			System.out.println("|  Option a : To check for archived versions of URLs   |");
			System.out.println("|                                                      |");
			System.out.println("|  UrlCheck --a <fileName>                             |");
			System.out.println("|  ex) UrlCheck --a index2.html                        |");
			System.out.println("|                                                      |");
			System.out.println("|  Option s : To request URLs with https               |");
			System.out.println("|                                                      |");
			System.out.println("|  UrlCheck --s <fileName>                             |");
			System.out.println("|  ex) UrlCheck --s index2.html                        |");
			System.out.println("|                                                      |");
			System.out.println("|  Option v : To check the version                     |");
			System.out.println("|                                                      |");
			System.out.println("|  UrlCheck --v                                        |");
			System.out.println("|  UrlCheck --version                                  |");
			System.out.println("|                                                      |");
			System.out.println("|                                                      |");
			System.out.println("|  For Mac :                                           |");
			System.out.println("|                                                      |");
			System.out.println("|  Option m : To run the tool in Mac                   |");
			System.out.println("|                                                      |");
			System.out.println("|  UrlCheck /m <fileName>                              |");
			System.out.println("|  ex) UrlCheck /m index2.html                         |");
			System.out.println("|                                                      |");
			System.out.println("|  Option a : To check for archived versions of URLs   |");
			System.out.println("|                                                      |");
			System.out.println("|  UrlCheck /a <fileName>                              |");
			System.out.println("|  ex) UrlCheck /a index2.html                         |");
			System.out.println("|                                                      |");
			System.out.println("|  Option s : To request URLs with https               |");
			System.out.println("|                                                      |");
			System.out.println("|  UrlCheck /s <fileName>                              |");
			System.out.println("|  ex) UrlCheck /s index2.html                         |");
			System.out.println("|                                                      |");
			System.out.println("|  Option v : To check the version                     |");
			System.out.println("|                                                      |");
			System.out.println("|  UrlCheck /v                                         |");
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
		public static void fileUrlListUp(String fName, boolean archived, boolean secured, boolean runMac)
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
							
							if(runMac)
							{
								UrlCheckForMac.availableURL(str);
							}
							else
							{
								UrlCheckForWindow.availableURL(str);
							}
							
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
		boolean runMac = false;
		
		if(args.length == 0 || args[0].toLowerCase().equals("help"))
		{
			helpMessage();
		}
		else 
		{
			startMessage();
			
			// command line flag a, s
			if(args[0].startsWith("--") || args[0].startsWith("/"))
			{
				if(args[0].contains("v") || args[0].contains("version")) 
				{
					System.out.println("UrlChecker v1.0.1");
				}
				else
				{
					// archive flag handle
					if(args[0].contains("a")) archived = true;
			
					// secure request flag handle
					if(args[0].contains("s")) secured = true;
					
					// secure request flag handle
					if(args[0].contains("m")) runMac = true;
					
					if(archived || secured || runMac)
					{
						for(int i = 1; i < args.length; i++)
						{
							System.out.println("File :  " + args[i]);
							fileUrlListUp(args[i],archived,secured,runMac);					
						}		
					}
				
				}	
			}
			else {
				for(int i = 0; i < args.length; i++)
				{
					System.out.println("File :  " + args[i]);
					fileUrlListUp(args[i],archived,secured,runMac);					
				}
			}
		}
		
		endMessage();

	}

}
