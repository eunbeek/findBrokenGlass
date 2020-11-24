package test;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import main.UrlCheck;

class T02_BadUrlCountCheckTest {

  @Test
  void testCountBadUrl() {

    UrlCheck urlCheck = new UrlCheck();

    int currentBad = urlCheck.bad;

    urlCheck.countBadUrl(false);
    assertTrue(urlCheck.bad == currentBad);

    urlCheck.countBadUrl(true);
    assertTrue(urlCheck.bad == currentBad + 1);
  }

}
