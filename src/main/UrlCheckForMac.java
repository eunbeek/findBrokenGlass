package main;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class UrlCheckForMac {

  // text for mac
  public static final String RED = "\033[0;31m";
  public static final String GREEN = "\033[0;32m";
  public static final String WHITE = "\033[0;37m";
  public static final String BLUE = "\033[0;34m";
  public static final String RESET = "\033[0m";

  // request url and check the response
  public static boolean availableURL(String host) {
    boolean result = false;

    try {
      // request url
      URL url = new URL(host);
      URLConnection con;
      con = url.openConnection();

      // response
      HttpURLConnection exitCode = (HttpURLConnection) con;
      exitCode.setInstanceFollowRedirects(true);
      HttpURLConnection.setFollowRedirects(true);
      exitCode.setConnectTimeout(1000);
      System.out.println("HERE");
      // for Mac
      if (exitCode.getResponseCode() >= 200 && exitCode.getResponseCode() < 300) {
        result = true;
        System.out
            .println(GREEN + "[" + exitCode.getResponseCode() + "] " + host + " - Good" + RESET);

      } else if (exitCode.getResponseCode() >= 400 && exitCode.getResponseCode() < 500) {
        System.out.println(RED + "[" + exitCode.getResponseCode() + "] " + host + " - Bad" + RESET);
      } else if (exitCode.getResponseCode() == 301 || exitCode.getResponseCode() == 307
          || exitCode.getResponseCode() == 308) {
        System.out
            .println(BLUE + "[" + exitCode.getResponseCode() + "] " + host + " - Redirect" + RESET);

        // redirect to new location by Recursion itself when it is 301,307,308
        String newUrl = exitCode.getHeaderField("Location");
        result = availableURL(newUrl);

      } else {
        System.out
            .println(RED + "[" + exitCode.getResponseCode() + "] " + host + " - Unknown" + RESET);
      }
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      // response fail, server is not existed
      System.out.println(RED + "[599] " + host + " - Fail" + RESET);
    }
    return result;
  }
}
