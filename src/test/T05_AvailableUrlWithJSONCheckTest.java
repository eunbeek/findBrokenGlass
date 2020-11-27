package test;
import static org.junit.Assert.assertTrue;

import main.ConvertJavaToJson;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

public class T05_AvailableUrlWithJSONCheckTest {
	
	@Test
	void testAvailableUrlWithJSON() {
		String goodHost = "https://www.google.com";
		String badHost = "http://google.com/nope";
		
		JSONObject expected = new JSONObject();
		expected.put("url", goodHost);
		expected.put("status", 200);
		
		ConvertJavaToJson javaToJson = new ConvertJavaToJson();
		
		JSONObject goodJsonConvert = new JSONObject();
		goodJsonConvert = javaToJson.availableURL(goodHost);
	    
	    //Result expect : Good host
	    assertTrue(expected.get("url").equals(goodJsonConvert.get("url")));
	    assertTrue(expected.get("status").equals(goodJsonConvert.get("status")));
	    
	    JSONObject badJsonConvert = new JSONObject();
	    badJsonConvert = javaToJson.availableURL(badHost);
	    
	    //Result expect : Bad host
	    assertTrue(!expected.get("url").equals(badJsonConvert.get("url")));
	    assertTrue(!expected.get("status").equals(badJsonConvert.get("status")));
	    
	}

}
