package test;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import main.UrlCheck;

class T005_BadUrlCountCheck {

  @Test
  void testCountBadUrl() {

    UrlCheck urlCheck = new UrlCheck();

    urlCheck.countBadUrl(false);
    assertTrue(urlCheck.bad == 0);

    urlCheck.countBadUrl(true);
    assertTrue(urlCheck.bad == 1);

  }

}
