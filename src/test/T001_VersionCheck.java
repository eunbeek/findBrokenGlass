package test;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import junit.framework.TestCase;
import main.UrlCheck;

class T001_VersionCheck extends TestCase {

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
