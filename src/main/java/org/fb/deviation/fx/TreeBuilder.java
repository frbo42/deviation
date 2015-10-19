package org.fb.deviation.fx;

import org.fb.deviation.fx.control.tree.FilterableTreeItem;
import org.fb.deviation.model.DNode;
import org.fb.deviation.model.DirNode;
import org.fb.deviation.model.FileNode;

import java.util.Collection;
import java.util.List;

/**
 * Created by frank on 11.10.15.
 */
class TreeBuilder {
    private final FilterableTreeItem<DNode> rootItem;

    TreeBuilder(DirNode rootDir) {
        rootItem = new FilterableTreeItem<>(rootDir);
        rootItem.setExpanded(true);
        addNodes(rootItem, rootDir);
    }

    private void addNodes(FilterableTreeItem<DNode> parentItem, DirNode dirNode) {
        addFiles(parentItem, dirNode.getFiles());
        addDirs(parentItem, dirNode.getDirs());
    }

    private void addDirs(FilterableTreeItem<DNode> parentItem, List<DirNode> dirsInDir) {
        dirsInDir.forEach(dir -> {
            FilterableTreeItem<DNode> item = new FilterableTreeItem<>(dir);

            parentItem.getInternalChildren().add(item);
            addNodes(item, dir);
        });
    }

    private void addFiles(FilterableTreeItem<DNode> parentItem, Collection<FileNode> filesInDir) {
        filesInDir.forEach(file -> parentItem.getInternalChildren().add(new FilterableTreeItem<>(file)));
    }

    public FilterableTreeItem<DNode> getRootItem() {
        return rootItem;
    }
}
