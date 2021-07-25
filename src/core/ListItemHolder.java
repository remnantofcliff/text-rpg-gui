package core;

/**
 * Holds an object as well as an the integer repre
 */
public class ListItemHolder<O> {
  private final O item;
  private final int index;

  public ListItemHolder(O item, int index) {
    this.item = item;
    this.index = index;
  }

  public O getItem() {
    return item;
  }

  public int getIndex() {
    return index;
  }
}
