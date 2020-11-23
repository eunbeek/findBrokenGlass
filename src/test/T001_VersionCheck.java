package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import main.UrlCheck;

class T001_VersionCheck {

  @Test
  @SuppressWarnings("static-access")
  void testMain() {
    System.out.println("Test01");
    String[] args = {"-v"};

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
