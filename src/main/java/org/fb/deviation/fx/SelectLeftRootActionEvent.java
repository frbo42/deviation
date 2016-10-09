package org.fb.deviation.fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import org.fb.deviation.fx.common.Pref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.io.File;


@Component
class SelectLeftRootActionEvent implements EventHandler<ActionEvent> {

    private final Pref pref;

    @Autowired
    SelectLeftRootActionEvent(Pref pref) {
        this.pref = pref;
    }

    @Override
    public void handle(ActionEvent event) {
        File folder = chooser().showDialog(window(event.getSource()));
        if (folder != null) {
            pref.setLeftRoot(folder);
        }
    }

    private DirectoryChooser chooser() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(pref.lastLeftRoot());
        dc.setTitle("Select the Left Folder Tree Root");
        return dc;
    }

    @Nullable
    private Window window(Object source) {
        return source instanceof Node ?
                ((Node) source).getScene().getWindow()
                : null;
    }
}
