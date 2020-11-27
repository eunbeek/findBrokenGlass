package test;

import static com.apisimulator.embedded.SuchThat.contains;
import static com.apisimulator.embedded.SuchThat.isEqualTo;
import static com.apisimulator.embedded.http.HttpApiSimulation.httpApiSimulation;
import static com.apisimulator.embedded.http.HttpApiSimulation.httpRequest;
import static com.apisimulator.embedded.http.HttpApiSimulation.httpResponse;
import static com.apisimulator.embedded.http.HttpApiSimulation.simlet;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.apisimulator.embedded.http.HttpApiSimulation;
import main.UrlCheckForMac;

class T04_AvailableUrlMacCheckTest {
  private static HttpApiSimulation mockServer = null;

  @BeforeAll
  public static void setUp() {
    mockServer = httpApiSimulation("my-mock-server");

    mockServer
        .add(
            simlet("test-google-good")
                .when(httpRequest().whereMethod("GET").whereUriPath(isEqualTo("/return/200"))
                    .whereHeader("Host", contains("google.com")))
                .then(httpResponse().withStatus(200)));

    mockServer.add(simlet("test-google-bad")
        .when(httpRequest().whereMethod("GET").whereUriPath(isEqualTo("/return/404"))
            .whereHeader("Host", contains("google.com")))
        .then(httpResponse().withStatus(404).withHeader("Content-Type", "application/text")));
  }

  @AfterAll
  public static void tearDown() {
    if (mockServer != null) {
      mockServer.stop();
    }
  }

  @Test
  void testGoodURLAvailable() {
    try {
      System.setProperty("http.proxyHost", "localhost");
      System.setProperty("http.proxyPort", "6090");

      String goodHost = "http://www.google.com/return/200";

      boolean expected = true;

      UrlCheckForMac urlCheck = new UrlCheckForMac();
      System.out.println(httpResponse().withStatus(200));
      assertEquals(expected, urlCheck.availableURL(goodHost));

    } finally {
      System.clearProperty("http.proxyHost");
      System.clearProperty("http.proxyPort");
    }
  }

  @Test
  void testBadURLAvailable() {
    try {
      System.setProperty("http.proxyHost", "localhost");
      System.setProperty("http.proxyPort", "6090");

      String badHost = "http://google.com/return/404";

      boolean expected = false;

      UrlCheckForMac urlCheck = new UrlCheckForMac();

      assertEquals(expected, urlCheck.availableURL(badHost));

    } finally {
      System.clearProperty("http.proxyHost");
      System.clearProperty("http.proxyPort");
    }
  }
}
