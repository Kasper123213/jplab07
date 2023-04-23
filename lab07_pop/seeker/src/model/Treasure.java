
package model;

public class Treasure extends Artifact {
    private static final long serialVersionUID = 1L;
    private final Category category;

    public Treasure(final int excavationTime, final Category category) {
        super(excavationTime);
        this.category = category;
    }

    @Override
    public Category getCategory() {
        return this.category;
    }
}
