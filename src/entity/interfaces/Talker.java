package entity.interfaces;

import java.io.Serializable;

/**
 * For characters in areas that are able to be talked to.
 */
public interface Talker extends Serializable {
  String[] getText();
}
