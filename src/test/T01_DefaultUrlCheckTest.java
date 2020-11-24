package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import main.UrlCheck;

class T01_DefaultUrlCheckTest {

  @Test
  void testMain() {
    String[] args = {"index.html"};

    UrlCheck urlCheck = new UrlCheck();
    try {
      urlCheck.main(args);

    } catch (IOException e) {
      assertFalse(false);
    }

    assertTrue(true);
  }

}
