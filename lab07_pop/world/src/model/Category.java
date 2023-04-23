
package model;

public enum Category {
    GOLD("GOLD", 0),
    SILVER("SILVER", 1),
    BRONZE("BRONZE", 2),
    IRON("IRON", 3),
    OTHER("OTHER", 4),
    EMPTY("EMPTY", 5);

    private Category(final String name, final int ordinal) {
    }
}
