package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import main.UrlCheck;

class T002_ArchievedCheck {

  @Test
  @SuppressWarnings("static-access")
  void testMain() {
    String[] args = {"-a", "index.html"};

    UrlCheck urlCheck = new UrlCheck();

    try {
      urlCheck.main(args);

    } catch (IOException e) {
      e.printStackTrace();
      assertFalse(false);
    }
    assertTrue(true);
  }

}
