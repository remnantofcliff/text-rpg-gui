package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Contains static methods for reading files.
 */
public class FileHandler {
  private FileHandler(){}
  /**
   * Reads text files for the game. Adds the lines to a list and returns it.

   * @param path Path to file (String)
   * @return (List)
   */
  public static String[] readText(String path) {
    File file = new File(path);
    List<String> list = new LinkedList<>();
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        list.add(scanner.nextLine());
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return list.toArray(new String[0]);
  }
}
