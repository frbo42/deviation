package org.fb.deviation.model;


import java.nio.file.Path;

public interface DNode {
    boolean isLeftMissing();

    boolean isRightMissing();

    boolean both();

    Path getRelativePath();
}
