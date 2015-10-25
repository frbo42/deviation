package org.fb.deviation.model;

import java.nio.file.Path;


public class FileNode implements DNode {

    private Path leftFile;
    private Path rightFile;

    public void addLeft(Path leftFile) {
        this.leftFile = leftFile;
    }

    public void addRight(Path rightFile) {
        this.rightFile = rightFile;
    }

    @Override
    public String toString() {
        return (leftFile != null) ? leftFile.getFileName().toString() : rightFile.getFileName().toString();
    }

    @Override
    public boolean isLeftMissing() {
        return leftFile == null;
    }

    @Override
    public boolean isRightMissing() {
        return rightFile == null;
    }

    @Override
    public boolean both() {
        return isLeftMissing() ^ isRightMissing();
    }
}
