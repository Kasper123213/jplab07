
package model;

import java.io.Serializable;

public class Report implements Serializable {
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;
    private Artifact artifact;

    public Report(int x, int y, Artifact artifact) {
        this.x = x;
        this.y = y;
        this.artifact = artifact;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Artifact getArtifact() {
        return this.artifact;
    }
}