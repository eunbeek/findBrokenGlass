package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import main.UrlCheck;
import org.junit.jupiter.api.Test;

class T008_ArchiveUrlCheck {

  @Test
  void testArchiveUrl() {
    String AUrl = "http://archive.org/wayback/available?url=";
    String BUrl = "http://example.com?url=";
    String host = "https://github.com/eunbeek";
    String wrongHost = "";

    UrlCheck urlCheck = new UrlCheck();
    // Good Url with good/wrong host
    String goodUrlActual = urlCheck.archiveUrl(AUrl, host);
    assertTrue(goodUrlActual.contains(host));
    try {
      String badUrlActual = urlCheck.archiveUrl(BUrl, host);
    } catch (RuntimeException e) {
      assertFalse(false);
    }

    // Good Host with good/wrong url
    try {
      String badHostActual = urlCheck.archiveUrl(AUrl, wrongHost);
      assertTrue(badHostActual.contains("Error: no url parameter"));
    } catch (RuntimeException e) {
      assertFalse(true);
    }
  }
}
