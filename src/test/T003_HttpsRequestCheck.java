package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import main.UrlCheck;
import org.junit.jupiter.api.Test;

class T003_HttpsRequestCheck {

  @Test
  @SuppressWarnings("static-access")
  void testMain() {
    String[] args = {"-s", "index.html"};

    UrlCheck urlCheck = new UrlCheck();

    try {
      urlCheck.main(args);

    } catch (IOException e) {
      e.printStackTrace();
      assertFalse(true);
    }

    assertTrue(true);
  }
}
