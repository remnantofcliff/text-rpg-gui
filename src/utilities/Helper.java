package utilities;

/**
 * Contains static methods to help with operations.
 */
public class Helper {
  /**
   * Turns a number into the numeral representation. Only works for numbers 0-9.

   * @param number (int)
   * @return (String)
   */
  public static String intToNumeral(int number) {
    switch (number) {
      case 0:
        return "zero";
      case 1:
        return "one";
      case 2:
        return "two";
      case 3:
        return "three";
      case 4:
        return "four";
      case 5:
        return "five";
      case 6:
        return "six";
      case 7:
        return "seven";
      case 8:
        return "eight";
      case 9:
        return "nine";
      default:
        return null;
    }
  }
}
