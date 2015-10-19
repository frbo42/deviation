package org.fb.deviation.fx;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.fb.deviation.fx.control.tree.FilterableTreeItem;
import org.fb.deviation.fx.control.tree.TreeItemPredicate;
import org.fb.deviation.model.DNode;
import org.fb.deviation.model.DirNode;
import org.fb.deviation.scan.ScanService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by frank on 09.09.15.
 */
public class MainController {

    public static final String path = "/main.fxml";

    private final BooleanProperty hide = new SimpleBooleanProperty();

    @Autowired
    private ScanService scanService;
    @Autowired
    private Pref pref;
    @Autowired
    private SelectLeftRootActionEvent leftRootActionEvent;
    @Autowired
    private SelectRightRootActionEvent rightRootActionEvent;

    @FXML
    private Parent root;
    @FXML
    private TreeView<DNode> leftTree;
    @FXML
    private TreeView<DNode> rightTree;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;
    @FXML
    private Button compareButton;
    @FXML
    private ToggleButton toggleButton;

    public Parent getRoot() {
        return root;
    }

    @PostConstruct
    public void init() {
        leftButton.setOnAction(leftRootActionEvent);
        Platform.runLater(() ->
                leftButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN), leftButton::fire));

        rightButton.setOnAction(rightRootActionEvent);
        Platform.runLater(() ->
                rightButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN), rightButton::fire));


        toggleButton.setOnAction(event -> hide.setValue(((ToggleButton) event.getSource()).isSelected()));
        Platform.runLater(() ->
                toggleButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.H, KeyCombination.SHORTCUT_DOWN), toggleButton::fire));

        Platform.runLater(() ->
                compareButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN), compareButton::fire));
        compareButton.setOnAction(e -> {
                    try {
                        DirNode rootDirNode = scanService.scan(pref.lastLeftRoot().toPath(), pref.lastRightRoot().toPath());

                        TreeBuilder builder = new TreeBuilder(rootDirNode);

                        FilterableTreeItem<DNode> rootItem = builder.getRootItem();
                        leftTree.setRoot(rootItem);
                        leftTree.setCellFactory(treeView -> {
                            DNodeCell dNodeCell = new DNodeCell(pref.lastLeftRoot().toString()) {
                                @Override
                                boolean isMissing(DNode item) {
                                    return item.isLeftMissing();
                                }
                            };
                            dNodeCell.setContextMenu(buildContextMenu());
                            return dNodeCell;
                        });

                        rightTree.setRoot(rootItem);
                        rightTree.setCellFactory(tree -> new DNodeCell(pref.lastRightRoot().toString()) {
                            @Override
                            boolean isMissing(DNode item) {
                                return item.isRightMissing();
                            }
                        });

                        rootItem.predicateProperty().bind(Bindings.createObjectBinding(() ->
                                TreeItemPredicate.<DNode>create(dNode ->
                                        !hide.get() || dNode.both())
                                , hide));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
        );
    }

    private ContextMenu buildContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem = new MenuItem("Copy");
        contextMenu.getItems().add(menuItem);
        return contextMenu;
    }

    public void onExit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
