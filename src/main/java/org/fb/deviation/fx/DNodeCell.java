package org.fb.deviation.fx;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import org.fb.deviation.model.DNode;
import org.fb.deviation.model.DirNode;

abstract class DNodeCell extends TextFieldTreeCell<DNode> {

    private static final String MISSING = "missing";
    private final String rootNodeName;

    DNodeCell(String rootNodeName) {
        this.rootNodeName = rootNodeName;
    }

    @Override
    public void updateItem(DNode item, boolean empty) {
        super.updateItem(item, empty);
        setMissingStyleClass(item);
        setupContextMenu(item);
        setRootName(item);
    }

    private void setupContextMenu(DNode item) {
        if (canCopy(item)) {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem = new MenuItem("Copy");
            contextMenu.getItems().add(menuItem);

            setContextMenu(contextMenu);
        } else {
            setContextMenu(null);
        }
    }

    boolean canCopy(DNode item) {
        return false;
    }


    private void setRootName(DNode item) {
        if (item instanceof DirNode && ((DirNode) item).isRoot()) {
            setText(rootNodeName);
        }
    }

    private void setMissingStyleClass(DNode item) {
        if (isMissing(item)) {
            getStyleClass().add(MISSING);
        } else {
            getStyleClass().removeAll(MISSING);
        }
    }

    abstract boolean isMissing(DNode item);
}
