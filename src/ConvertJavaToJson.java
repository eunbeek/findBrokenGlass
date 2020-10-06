import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;

public class ConvertJavaToJson {
	
	public static JSONObject availableURL(String host)
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
				exitCode.setConnectTimeout(1000);

				return ConvertJavaObjectToJson(host, exitCode.getResponseCode());

			}catch (Exception e) {
					
				return ConvertJavaObjectToJson(host, 599);
		}
			
	
	}
				
	public static JSONObject ConvertJavaObjectToJson(String url, int code)
	{
		JSONObject obj = new JSONObject();
		obj.put("url",url);
		obj.put("status",code);
		
		return obj;
		
	}
}
