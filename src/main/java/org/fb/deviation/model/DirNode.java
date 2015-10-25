package org.fb.deviation.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DirNode implements DNode {
    private final String dirName;
    private final Map<Path, FileNode> files = new HashMap<>();
    private final List<DirNode> dirs = new ArrayList<>();
    private boolean existsLeft;
    private boolean existsRight;
    private boolean isRoot;

    public DirNode(String dirName) {
        this.dirName = dirName;
    }

    public void addRightFile(Path fileName) {
        FileNode fileNode = getFileNode(fileName);
        fileNode.addRight(fileName);
    }

    public void addLeftFile(Path fileName) {
        FileNode fileNode = getFileNode(fileName);
        fileNode.addLeft(fileName);
    }

    private FileNode getFileNode(Path fileName) {
        if (!files.containsKey(fileName)) {
            files.put(fileName, new FileNode());
        }
        return files.get(fileName);
    }

    public void addDir(DirNode dirNode) {
        if (!dirs.contains(dirNode)) {
            dirs.add(dirNode);
        }
    }

    public List<DirNode> getDirs() {
        return dirs;
    }

    public Collection<FileNode> getFiles() {
        return files.values();
    }

    @Override
    public String toString() {
        return dirName;
    }

    @Override
    public boolean isLeftMissing() {
        return !existsLeft;
    }

    @Override
    public boolean isRightMissing() {
        return !existsRight;
    }

    @Override
    public boolean both() {
        return isLeftMissing() ^ isRightMissing();
    }

    public void registerLeft() {
        existsLeft = true;
    }

    public void registerRight() {
        existsRight = true;
    }

    public void registerAsRoot() {
        isRoot = true;
    }

    public boolean isRoot() {
        return isRoot;
    }
}
