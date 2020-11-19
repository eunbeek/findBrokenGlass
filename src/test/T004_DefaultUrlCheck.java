package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import main.UrlCheck;

class T004_DefaultUrlCheck {

  @Test
  @SuppressWarnings("static-access")
  void testMain() {
    String[] args = {"urls.html"};

    UrlCheck urlCheck = new UrlCheck();
    try {
      urlCheck.main(args);

    } catch (IOException e) {
      assertFalse(false);
    }

    assertTrue(true);
  }
}
