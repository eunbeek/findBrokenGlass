
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlCheck {
	// url set regex
		final static String regex = "(https?):\\/\\/[-a-zA-Z0-9+&@#%?=~_|!:,.;]*[-a-zA-Z0-9+&@#%=~_|\\/]*";
		
		// delimiter to get url from input file
		final static String delimiter = "[\"<>'\n\b\r]";
		
		// request url and check the response
		public static void availableURL(String host)
		{

				try {
						// request url
						URL url = new URL(host);
						URLConnection con;
						con = url.openConnection();
						// response
						HttpURLConnection exitCode = (HttpURLConnection) con;
						exitCode.setInstanceFollowRedirects(true);
						HttpURLConnection.setFollowRedirects(true);
						//exitCode.setConnectTimeout(3000);
						
						System.out.print("Url : " + host +"  Code : "+ exitCode.getResponseCode());
						
						// response code result
						if(exitCode.getResponseCode() == 200)
						{ 
							System.out.println("  Status : good");
						}
						else if(exitCode.getResponseCode() == 400 || exitCode.getResponseCode() == 404)
						{
							System.out.println("  Status : bad");
						}
						else if(exitCode.getResponseCode() == 301 || exitCode.getResponseCode() == 307 || exitCode.getResponseCode() == 308 )
						{
							System.out.println("  Stauts : Redirect");
							// redirect to new location by Recursion itself when it is 301,307,308
							String newUrl = exitCode.getHeaderField("Location");
							System.out.print("   -> Redirect ");
							availableURL(newUrl);				
						}
						else
						{
							System.out.println("  Status : unknown");						
						}

				}catch (IOException e) {
					// response fail, server is not existed
					System.out.println("Url : "+ host +"  Status : Fail");
					//e.printStackTrace();
				}catch(Exception e)
				{
					throw new RuntimeException("Exception URL : "+ host, e);
				}

		}
		
		public static void helpMessage()
		{
			System.out.println("========================================================");
			System.out.println("|               Help message                           |");
			System.out.println("========================================================");
			System.out.println("| Please Type proper argument after command            |");
			System.out.println("|                                                      |");
			System.out.println("| 1) UrlCheck <input fileName>                         |");
			System.out.println("| ex) UrlCheck index.html                              |");
			System.out.println("|     UrlCheck index.html index2.html                  |");
			System.out.println("|                                                      |");
			System.out.println("| 2) UrlCheck help                                     |");
			System.out.println("|                                                      |");
			System.out.println("| 3) UrlCheck <URL>                                    |");
			System.out.println("| : To check for archived versions of URLs             |");
			System.out.println("| ex) UrlCheck http://example.com                      |");
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
		
		public static void archiveMessage()
		{
			System.out.println("========================================================");
			System.out.println("|          ARCHIVE URL BY Wayback API                  |");
			System.out.println("========================================================");
		}
		// list up url in input file
		public static void fileUrlListUp(String fName)
		{

			Pattern pat = Pattern.compile(regex, Pattern.MULTILINE);
			Pattern deliPat = Pattern.compile(delimiter);
			
			try {
				Scanner input = new Scanner(new File(fName));
				input.useDelimiter(deliPat);
				while(input.hasNext())
				{
					String tempUrl = input.next(); 
					Matcher matcher = pat.matcher(tempUrl);
					if(matcher.find())
					{
						availableURL(tempUrl);
					}
				}
				
				input.close();
			} catch (FileNotFoundException e) {
				System.out.println("File not Found");
				//	e.printStackTrace();
			}
			
		}
		
		public static void archiveUrl(String host)
		{
		
			String apiUrl = "http://archive.org/wayback/available?url=" + host;
			StringBuilder sb = new StringBuilder();
			
			System.out.println("Url : "+ host);
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
		
		if(args.length == 0 || args[0].toLowerCase().equals("help"))
		{
			helpMessage();
		}
		else if(args[0].contains("http"))
		{
			archiveMessage();
 			archiveUrl(args[0]);
		}
		else 
		{
			startMessage();
			for(int i = 0; i < args.length; i++)
			{
				System.out.println("File :  " + args[i]);
				fileUrlListUp(args[i]);
				System.out.println("");
			}
		}
		endMessage();

	}

}
