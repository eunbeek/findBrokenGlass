package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import main.UrlCheckForMac;

class T04_AvailableUrlMacCheckTest {

  @Test
  void testAvailableURL() {

    String goodHost = "http://google.com";
    String badHost = "http://google.com/nope";

    boolean expected = true;

    UrlCheckForMac urlCheck = new UrlCheckForMac();

    assertEquals(expected, urlCheck.availableURL(goodHost));
    assertEquals(!expected, urlCheck.availableURL(badHost));


  }

}
