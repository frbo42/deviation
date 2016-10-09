package org.fb.deviation.domain;


import java.nio.file.Path;

public interface DNode {
    boolean isLeftMissing();

    boolean isRightMissing();

    boolean both();

    Path getRelativePath();
}
