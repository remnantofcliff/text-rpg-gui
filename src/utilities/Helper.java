package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Contains static methods.
 */
public class Helper {
  private Helper(){}
  /**
   * Turns a number into the numeral representation. Only works for numbers 0-9.

   * @param number (int)
   * @return (String)
   */
  public static String intToNumeral(int number) {
    switch (number) {
      case 0: return "zero";
      case 1: return "one";
      case 2: return "two";
      case 3: return "three";
      case 4: return "four";
      case 5: return "five";
      case 6: return "six";
      case 7: return "seven";
      case 8: return "eight";
      case 9: return "nine";
      default: return null;
    }
  }

  /**
   * Reads text files for the game. Adds the lines to a list and returns it.

   * @param path Path to file (String)
   * @return (List)
   */
  public static String[] readText(String path) {
    List<String> list = new LinkedList<>();
    try (var scanner = new Scanner(new File(path))) {
      while (scanner.hasNextLine()) {
        list.add(scanner.nextLine());
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return list.toArray(new String[0]);
  }

  /**
   * Reads a single line of a text-file.

   * @param path Path of file (String)
   * @return (String)
   */
  public static String readLine(String path) {
    try (var scanner = new Scanner(new File(path))) {
      if (scanner.hasNextLine()) {
        return scanner.nextLine();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
}
