package org.fb.deviation.fx;

import javafx.scene.control.cell.TextFieldTreeCell;
import org.fb.deviation.model.DNode;
import org.fb.deviation.model.DirNode;

/**
 * Created by frank on 08.10.15.
 */
abstract class DNodeCell extends TextFieldTreeCell<DNode> {
    private static final String MISSING = "missing";
    private final String rootNodeName;

    protected DNodeCell(String rootNodeName) {
        this.rootNodeName = rootNodeName;
    }

    @Override
    public void updateItem(DNode item, boolean empty) {
        super.updateItem(item, empty);
        setMissingStyleClass(item);
        setRootName(item);
    }

    private void setRootName(DNode item) {
        if (item instanceof DirNode && ((DirNode) item).isRoot()) {
            setText(rootNodeName);
        }
    }

    private void setMissingStyleClass(DNode item) {
        if (item != null && isMissing(item)) {
            getStyleClass().add(MISSING);
        } else {
            getStyleClass().removeAll(MISSING);
        }
    }

    abstract boolean isMissing(DNode item);
}
