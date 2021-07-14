package entity.characters;

import entity.Entity;
import entity.interfaces.Talker;

/**
 * A citizen of Dretnos.
 */
public class DretnosCitizen extends Entity implements Talker {

  public DretnosCitizen() {
    name = "Cred";
  }

  @Override
  public String[] getText() {
    return new String[] {
      "Times are though right now.",
      "I would give you something to help you but we don't really have much to give.",
      "You can exit from the eastern opening.\n",
      "Stay away from that cavern to the west.",
      "I've never been there myself but I've heard that horrid monsters once came from there."
    };
  }
  
}
