package test;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import main.UrlCheck;

class T03_GetUrlFromTelescopeCheckTest {

  @Test
  void testGetUrlFromTelescope() {
    String apiUrl = "https://telescope.cdot.systems";
    String brokenUrl = "http://example.com";

    UrlCheck urlCheck = new UrlCheck();
    int currentBad = UrlCheck.bad;

    // Window Url Check
    urlCheck.getUrlFromTelescope(false, apiUrl);
    assertEquals(currentBad, urlCheck.bad);

    try {
      urlCheck.getUrlFromTelescope(false, brokenUrl);

    } catch (RuntimeException e) {
      assertEquals(currentBad, urlCheck.bad);
    }

    // Mac Url Check
    urlCheck.getUrlFromTelescope(true, apiUrl);
    assertEquals(currentBad, urlCheck.bad);

    try {
      urlCheck.getUrlFromTelescope(true, brokenUrl);
    } catch (RuntimeException e) {
      assertEquals(currentBad, urlCheck.bad);
    }
  }
}
