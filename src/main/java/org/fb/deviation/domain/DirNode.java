package org.fb.deviation.domain;

import java.nio.file.Path;
import java.util.*;


public class DirNode implements DNode {
    private final Map<Path, FileNode> files = new HashMap<>();
    private final List<DirNode> dirs = new ArrayList<>();
    private final Path folderRelativePath;
    private boolean existsLeft;
    private boolean existsRight;
    private boolean isRoot;

    public DirNode(Path folderRelativePath) {
        this.folderRelativePath = folderRelativePath;
    }

    public void addRightFile(Path fileName) {
        FileNode fileNode = getFileNode(fileName);
        fileNode.addRight(fileName);
    }

    public void addLeftFile(Path relativeFilePath) {
        FileNode fileNode = getFileNode(relativeFilePath);
        fileNode.addLeft(relativeFilePath);
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
        return folderRelativePath.getFileName().toString();
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

    @Override
    public Path getRelativePath() {
        return folderRelativePath;
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
