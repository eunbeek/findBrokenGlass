package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import main.UrlCheck;

public class T02_GetUrlFromTelescopeCheck {

  @Test
  public void testGetUrlFromTelescope() {
    String apiUrl = "https://telescope.cdot.systems";
    String brokenUrl = "http://example.com";
    UrlCheck urlCheck = new UrlCheck();

    // Window Url Check
    urlCheck.getUrlFromTelescope(false, apiUrl);
    assertTrue(true);

    try {
      urlCheck.getUrlFromTelescope(false, brokenUrl);
    } catch (RuntimeException e) {
      assertFalse(false);
    }

    // Mac Url Check
    urlCheck.getUrlFromTelescope(true, apiUrl);
    assertTrue(true);

    try {
      urlCheck.getUrlFromTelescope(true, brokenUrl);
    } catch (RuntimeException e) {
      assertFalse(false);
    }
  }

}
