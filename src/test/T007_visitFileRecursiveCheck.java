package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import main.UrlCheck;
import org.junit.jupiter.api.Test;

class T007_visitFileRecursiveCheck {

  @Test
  void testVisitFileRecursive() {
    UrlCheck urlCheck = new UrlCheck();

    String goodPath = "";
    String wrongPath1 = "./testq1w2e3r4/";
    String wrongPath2 = "./testq1w2e3r4/";

    ArrayList<String> actual = null;
    int expected = 0;
    try {
      actual = urlCheck.visitFileRecursive(goodPath);
    } catch (IOException e) {
      // error happend (wrong character ex. ?^*)
      assertFalse(true);
    }

    assertTrue(actual.size() >= expected);

    // wrongPath1
    try {
      actual = urlCheck.visitFileRecursive(wrongPath1);
    } catch (IOException e) {
      // error happend
      assertFalse(true);
    }

    assertTrue(actual.size() == expected);

    // wrongPath2
    try {
      actual = urlCheck.visitFileRecursive(wrongPath2);
    } catch (IOException e) {
      // error happend
      assertFalse(true);
    }

    assertTrue(actual.size() == expected);
  }
}
