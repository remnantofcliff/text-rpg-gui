package entity.characters;

import entity.Entity;
import entity.interfaces.Talker;

/**
 * Farmer in dretnos, can be talked to.
 */
public class DretnosFarmer extends Entity implements Talker {
  public DretnosFarmer() {
    name = "Creig, the farmer.";
  }

  @Override
  public String[] talk() {
    return new String[] { 
      "The only crop that grows here in the dark is mallyun", 
      "But even it is very slow to grow..." 
    };
  }
}
