package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;
import main.UrlCheck;

public class T01_VersionCheck {

  @Test
  public void testMain() {
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
