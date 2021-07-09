package utilities;

/**
 * Contains static methods for various operations.
 * 
 * <p>{@code String correctCapitalization(String string)} Turns the string into lowercase except for the first letter.
 * 
 * <p>{@code float round(float num)} Always rounds the float value down and to one decimal.
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