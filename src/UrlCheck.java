
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.simple.parser.*;


public class UrlCheck {

	static JSONArray list = new JSONArray();

	static int Bad = 0;
	
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
		System.out.println("| 3) Option a, s, v, m, d, j/json                      |");
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
		System.out.println("|  Option j/json : To return JSON format               |");
		System.out.println("|                                                      |");
		System.out.println("|  UrlCheck --j <fileName>                             |");
		System.out.println("|  ex) UrlCheck --json index2.html                     |");
		System.out.println("|                                                      |");
		System.out.println("|  Option v : To check the version                     |");
		System.out.println("|                                                      |");
		System.out.println("|  UrlCheck --v                                        |");
		System.out.println("|  UrlCheck --version                                  |");
		System.out.println("|                                                      |");
		System.out.println("|  Option t : To check the lastest 10 posts            |");
		System.out.println("|                                                      |");
		System.out.println("|  UrlCheck --t                                        |");
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
		System.out.println("|  Option j/json : To return JSON format               |");
		System.out.println("|                                                      |");
		System.out.println("|  UrlCheck /j <fileName>                              |");
		System.out.println("|  ex) UrlCheck /json index2.html                      |");
		System.out.println("|                                                      |");
		System.out.println("|  Option v : To check the version                     |");
		System.out.println("|                                                      |");
		System.out.println("|  UrlCheck /v                                         |");
		System.out.println("|                                                      |");
		System.out.println("|  Option --ignore/-i/                                 |");
		System.out.println("|                                                      |");
		System.out.println("|  UrlCheck /i <fileName> <ignoreFileName              |");
		System.out.println("|  ex) UrlCheck -i index.html index3.txt               |");
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
		System.out.println("End Code : " + Bad);
		System.out.println("========================================================");
	}


	// list up url in input file
	public static void fileUrlListUp(String fName, boolean archived, boolean secured, boolean runMac, boolean jsonOut) 
	{
		// url set regex
		String regex = "(https?):\\/\\/[-a-zA-Z0-9+&@#%?=~_|!:,.;]*[-a-zA-Z0-9+&@#%=~_|\\/]*";

		// delimiter to get url from input file 
		String delimiter = "[\\[\\]\"<>'\n\b\r()]";
		
		String regSecure = "^(http)://";
		Pattern pat = Pattern.compile(regex, Pattern.MULTILINE);

		try {
			File file=new File(fName);
			ArrayList<String> files =new ArrayList<String>();

			if(file.isDirectory()) {
				files = visitFileRecursive(fName);
			}
			else {
				files.add(fName);
			}

			for(String f: files) {

				System.out.println("\n\"" + f + "\" is checking...");
				
				BufferedReader br;

				br = new BufferedReader(new FileReader(f));
				String line = null;
				while((line = br.readLine()) != null)
				{
					String[] values = line.split(delimiter);
					for(String str : values)
					{
						Matcher matcher = pat.matcher(str);
						if(matcher.find())
						{
							if(jsonOut)
							{
								JSONObject convertJsonLine = new JSONObject();
								convertJsonLine = ConvertJavaToJson.availableURL(str);

								countBadUrl((int)convertJsonLine.get("status") < 200 || (int)convertJsonLine.get("status") >= 400);
								
								list.add(convertJsonLine);
							}
							else
							{					
								// change http to https
								if(secured) str = str.replaceFirst(regSecure, "https://");	
	
								if(runMac)
								{
									countBadUrl(!UrlCheckForMac.availableURL(str));
								}
								else
								{

									countBadUrl(!UrlCheckForWindow.availableURL(str));
								}
							}
							// request archived
							if(archived) System.out.println(archiveUrl("http://archive.org/wayback/available?url=",str));
							
							
						}
					}
				}
				if(jsonOut) System.out.println(list);
				br.close();
			}
		} catch (FileNotFoundException e) {

			System.out.println("File not Found");
		} catch (IOException e) {
			System.out.println("File not Found");
		}

	}

	// Archive API
	public static String archiveUrl(String iUrl,String host)
	{

		String apiUrl = iUrl + host;
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

		}catch(Exception e)
		{
			throw new RuntimeException("Exception URL : "+ host, e);
		}
		
		return sb.toString();
	}
	
	// Get Url From Telescope
	public static void getUrlFromTelescope(boolean runMac)
	{
		String apiUrl = "http://localhost:3000";
		String jsonText = archiveUrl(apiUrl+"/posts", "");
			
		String delimiter = "[\\[\\],\"\\{\\}\\:]";
		String regex = "^[/]";
		Pattern pat = Pattern.compile(regex, Pattern.MULTILINE);

		String[] values = jsonText.split(delimiter);
		
		for(String i : values)
		{
			Matcher matcher = pat.matcher(i);
			if(matcher.find())
			{
				String tempUrl = apiUrl + i;
				if(runMac)
				{
					countBadUrl(!UrlCheckForMac.availableURL(tempUrl));
				}
				else
				{
					countBadUrl(!UrlCheckForWindow.availableURL(tempUrl));
				}
			}
			
		}
	}
	


	public static void main(String[] args) throws IOException{

		boolean archived = false;
		boolean secured = false;
		boolean runMac = false;
		boolean jsonOut = false;
		boolean telescope = false;

		if(args.length == 0 || args[0].toLowerCase().equals("help"))
		{
			helpMessage();
		}
		else 
		{
			startMessage();

			// command line flag a, s, d
			if(args[0].startsWith("-") || args[0].startsWith("/") || args[0].startsWith("\\"))
			{
				if(args[0].contains("v") || args[0].contains("version")) 
				{
					System.out.println("UrlChecker v1.0.1");
				}
				else if(args[0].contains("i") || args[0].contains("ignore")) {
					String currentDir = System.getProperty("user.dir");
					String testThis = "\\" + String.valueOf(args[1]);
					String newDirectory = currentDir + testThis;

					String currentDir2 = System.getProperty("user.dir");
					String testThis2 = "\\" + String.valueOf(args[2]);
					String newDirectory2 = currentDir + testThis;

					//Open file & read through each line of html found
					try {
						File input = new File(newDirectory);
						File input2 = new File(newDirectory2);
						Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
						Document doc2 = Jsoup.parse(input2, "UTF-8", "http://example.com/");

						Elements links = doc.select("a[href]");
						Elements links2 = doc2.select("a[href]");
						for (Element link : links) {
								String test = link.attr("href");
									String test2 = links2.attr("href");
									if (test.equals(test2)){
										System.out.println("This link is being ignored");
									}else{
										URL url = new URL(test);
										HttpURLConnection conn = (HttpURLConnection) url.openConnection();
										conn.connect();

										int code = conn.getResponseCode();
										if (code == 200) {
											System.out.print("Link :  " + test);
											System.out.print(" - Code 200 - Link is good" + '\n');
										}else if(code == 404){
											System.out.print("Link :  ");
											System.out.print(" Code 404 - Link is bad" + '\n');
										}else if(code == 400){
											System.out.print("Link :  ");
											System.out.print(" Code 400 - Link is bad" + '\n');
										}
									}
							}


							} catch (FileNotFoundException e) {
								System.out.println("File not Found");
								System.exit(4);
							} catch (Exception e) {
								System.out.print("Link is not in a valid form " + '\n');
							}
					}else{
					// archive flag handle
					if(args[0].contains("a")) archived = true;

					// secure request flag handle
					if(args[0].contains("s")) secured = true;

					// secure request flag handle
					if(args[0].contains("m")) runMac = true;

					if(args[0].contains("j") || args[0].contains("json")) jsonOut = true;
					
					if(args[0].contains("t")) getUrlFromTelescope(runMac);

					if(archived || secured || runMac || jsonOut)
					{
						for(int i = 1; i < args.length; i++)
						{
							System.out.println("File/Directory :  " + args[i]);
							fileUrlListUp(args[i],archived,secured,runMac,jsonOut);
						}
					}

				}
			}else {
				for(int i = 0; i < args.length; i++)
				{
					System.out.println("File :  " + args[i]);
					fileUrlListUp(args[i],archived,secured,runMac,jsonOut);						
				}
			}
		}

		endMessage();

	}


	//recursively visit the directory/files and subfiles by Jossie
	public static ArrayList<String> visitFileRecursive(String path) throws IOException {

		ArrayList<String> files = new ArrayList<String>();

		Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

				files.add(file.toString());				
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException{
				System.err.println(exc);
				return FileVisitResult.CONTINUE;
			}
		});

		return files;
	}
	
	public static void countBadUrl(boolean badUrl)
	{
		
		if(badUrl) Bad++;
	}

}
