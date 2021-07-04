package utilities;

/**
 * Contains static methods for various operations.
 */
public class Utilities {
  private Utilities(){}

  public static String correctCapitalization(String string) {
    return string.substring(0, 1) + string.substring(1, string.length()).toLowerCase();
  }

  public static float round(float num) {
    return ((int) (num * 10)) / 10f;
  }
}