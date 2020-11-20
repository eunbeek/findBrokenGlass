package test;

import static org.junit.Assert.assertTrue;

import main.ConvertJavaToJson;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

class T011_availableUrlWithJsonCheck {

  @Test
  void testAvailableURL() {
    String host = "https://google.com";
    String host2 = "http://example.com";

    JSONObject expected = new JSONObject();
    expected.put("url", host);
    expected.put("status", 200);

    ConvertJavaToJson javaToJson = new ConvertJavaToJson();

    // Json result with Good host
    JSONObject convertJsonLine1 = new JSONObject();
    convertJsonLine1 = javaToJson.availableURL(host);

    assertTrue(expected.get("url").equals(convertJsonLine1.get("url")));
    assertTrue(expected.get("status").equals(convertJsonLine1.get("status")));

    JSONObject convertJsonLine2 = new JSONObject();
    convertJsonLine2 = javaToJson.availableURL(host2);

    // Json result with Bad host
    assertTrue(!expected.get("url").equals(convertJsonLine2.get("url")));
    assertTrue(!expected.get("status").equals(convertJsonLine2.get("status")));
  }
}
