package entity;

/**
 * Abstract item-class implements Comparable as well as overriding equals- and hashCode-methods to those of its name.
 */
public abstract class Item extends Entity implements Comparable<Item> {
  @Override
  public boolean equals(Object obj) {
    if (((Item) obj) == null || this.getClass() != obj.getClass()) {
      return false;
    }
    return name.equals(((Item) obj).getName());
  }

  @Override
  public int compareTo(Item item) {
    return name.compareTo(item.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}