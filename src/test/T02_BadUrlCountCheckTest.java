package test;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import main.UrlCheck;

class T02_BadUrlCountCheckTest {

  @Test
  void testCountBadUrl() {

    UrlCheck urlCheck = new UrlCheck();
    int currentBad = urlCheck.bad;

    urlCheck.countBadUrl(true);
    assertEquals(currentBad, urlCheck.bad);

    urlCheck.countBadUrl(true);
    assertEquals(currentBad + 1, urlCheck.bad);
  }

}
