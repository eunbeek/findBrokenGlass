
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class UrlCheck {
	// url set regex
	final static String regex = "(https?):\\/\\/[-a-zA-Z0-9+&@#%?=~_|!:,.;]*[-a-zA-Z0-9+&@#%=~_|\\/]*";

	// delimiter to get url from input file 
	final static String delimiter = "[\\[\\]\"<>'\n\b\r]";

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
								JSONObject temp = new JSONObject();
								temp = ConvertJavaToJson.availableURL(str);
								if((int)temp.get("status") < 200 || (int)temp.get("status") >= 400)
								{
									Bad++;
								}
								list.add(temp);
							}
							else
							{					
								// change http to https
								if(secured) str = str.replaceFirst(regSecure, "https://");	
	
								if(runMac)
								{
									if(!UrlCheckForMac.availableURL(str))
									{
										Bad++;
									}
								}
								else
								{
									if(!UrlCheckForWindow.availableURL(str))
									{
										Bad++;
									}
								}
							}
							// request archived
							if(archived) archiveUrl(str);
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
		boolean jsonOut = false;

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
				else
				{
					// archive flag handle
					if(args[0].contains("a")) archived = true;

					// secure request flag handle
					if(args[0].contains("s")) secured = true;

					// secure request flag handle
					if(args[0].contains("m")) runMac = true;

					if(args[0].contains("j") || args[0].contains("json")) jsonOut = true;
					
					if(archived || secured || runMac || jsonOut)
					{
						for(int i = 1; i < args.length; i++)
						{
							System.out.println("File/Directory :  " + args[i]);
							fileUrlListUp(args[i],archived,secured,runMac,jsonOut);						
						}		
					}

				}	
			}
			else {
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

}
