package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import main.UrlCheckForWindow;
import org.junit.jupiter.api.Test;

class T010_availableUrlForWindowCheck {

  @Test
  void testAvailableURL() {
    String goodHost = "http://google.com";
    String badHost = "https://en.wikipedia.org/wiki/Hackergotchi";

    UrlCheckForWindow urlCheck = new UrlCheckForWindow();

    // Good Host
    assertTrue(urlCheck.availableURL(goodHost));

    // Good Host
    assertTrue(!urlCheck.availableURL(badHost));
  }
}
