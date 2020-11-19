package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import main.UrlCheckForMac;
import org.junit.jupiter.api.Test;

class T009_availableUrlForMacCheck {

  @Test
  void testAvailableURL() {

    String goodHost = "http://google.com";
    String badHost = "https://en.wikipedia.org/wiki/Hackergotchi";

    UrlCheckForMac urlCheck = new UrlCheckForMac();

    // Good Host
    assertTrue(urlCheck.availableURL(goodHost));

    // Good Host
    assertTrue(!urlCheck.availableURL(badHost));
  }
}
