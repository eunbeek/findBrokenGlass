package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import main.ConvertJavaToJson;



/**
 * Tests for ConvertJavaToJson
 * 
 * @author badalsarkar date: Nov 26, 2020
 *
 */
class T06_ConvertJavaToJsonTest {

  @Test
  void testConvertJavaObjectToJson() {
    JSONObject actualObject = ConvertJavaToJson.convertJavaObjectToJson("https://google.com", 100);
    JSONObject expectedObject = new JSONObject();
    expectedObject.put("url", "https://google.com");
    expectedObject.put("status", 100);
    assertEquals(expectedObject, actualObject);
  }
}
