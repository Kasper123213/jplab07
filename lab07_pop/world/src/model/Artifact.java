
package model;

import java.io.Serializable;

public abstract class Artifact implements Serializable {
    private static final long serialVersionUID = 1L;
    final int excavationTime;

    public Artifact(final int excavationTime) {
        this.excavationTime = excavationTime;
    }

    public int getExcavationTime() {
        return this.excavationTime;
    }

    public abstract Category getCategory();
}
