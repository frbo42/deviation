package org.fb.deviation.scan;

import org.fb.deviation.model.DirNode;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by frank on 08.09.15.
 */
class Collector {
    private static final Path ROOT = Paths.get("");
    private final Map<Path, DirNode> folderNodeMap = new HashMap<>();

    public DirNode getRoot() {
        return folderNodeMap.get(ROOT);
    }

    private void registerDirNode(Path folder) {
        if (!folderNodeMap.containsKey(folder)) {
            folderNodeMap.put(folder, new DirNode(folder.getFileName().toString()));
        }
    }

    public void addLeftFile(Path leftFile, Path parentDirPath) {
        folderNodeMap.get(parentDirPath).addLeftFile(leftFile);
    }

    public void addRightFile(Path rightFile, Path parentDirPath) {
        folderNodeMap.get(parentDirPath).addRightFile(rightFile);
    }

    @Override
    public String toString() {
        return folderNodeMap.toString();
    }

    private void addDir(Path relativeDir) {
        registerDirNode(relativeDir);
        addToParent(relativeDir);
        if (isRootNode(relativeDir)) {
            folderNodeMap.get(relativeDir).registerAsRoot();
        }
    }

    private void addToParent(Path relativeDir) {
        if (!isRootNode(relativeDir)) {
            Path parent = (relativeDir.getParent() == null) ? ROOT : relativeDir.getParent();
            folderNodeMap.get(parent).addDir(folderNodeMap.get(relativeDir));
        }
    }

    private boolean isRootNode(Path relativeDir) {
        return ROOT.equals(relativeDir);
    }

    public void addLeftDir(Path relativeDir) {
        addDir(relativeDir);
        folderNodeMap.get(relativeDir).registerLeft();
    }

    public void addRightDir(Path relativeDir) {
        addDir(relativeDir);
        folderNodeMap.get(relativeDir).registerRight();
    }
}
