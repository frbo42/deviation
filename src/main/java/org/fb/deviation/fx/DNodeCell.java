package org.fb.deviation.fx;

import javafx.scene.control.cell.TextFieldTreeCell;
import org.fb.deviation.domain.DNode;
import org.fb.deviation.domain.DirNode;

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

    abstract void setupContextMenu(DNode item);

    abstract boolean isMissing(DNode item);
}
