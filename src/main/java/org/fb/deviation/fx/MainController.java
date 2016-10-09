package org.fb.deviation.fx;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.fb.deviation.domain.DNode;
import org.fb.deviation.domain.DirNode;
import org.fb.deviation.fx.common.Pref;
import org.fb.deviation.fx.control.tree.FilterableTreeItem;
import org.fb.deviation.fx.control.tree.TreeItemPredicate;
import org.fb.deviation.service.copy.CopyService;
import org.fb.deviation.service.scan.ScanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;

public class MainController {

    public static final String PATH = "/main.fxml";
    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    private final BooleanProperty hide = new SimpleBooleanProperty();

    @Autowired
    private ScanService scanService;
    @Autowired
    private CopyService copyService;

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


        toggleButton.setOnAction(event -> hide.setValue(((Toggle) event.getSource()).isSelected()));
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
                        leftTree.setCellFactory(treeView -> new DNodeCell(pref.lastLeftRoot().toString()) {
                            @Override
                            protected void setupContextMenu(DNode item) {
                                if (!isMissing(item)) {
                                    ContextMenu contextMenu = new ContextMenu();
                                    MenuItem menuItem = new MenuItem("Copy");
                                    menuItem.setOnAction(event -> copy(item));
                                    contextMenu.getItems().add(menuItem);

                                    setContextMenu(contextMenu);
                                } else {
                                    setContextMenu(null);
                                }
                            }

                            @Override
                            boolean isMissing(DNode item) {
                                return item != null && item.isLeftMissing();
                    }
                        });

                        rightTree.setRoot(rootItem);
                        rightTree.setCellFactory(tree -> new DNodeCell(pref.lastRightRoot().toString()) {
                            @Override
                            protected void setupContextMenu(DNode item) {
                    }

                            @Override
                            boolean isMissing(DNode item) {
                                return item != null && item.isRightMissing();
                            }
                        });

                        rootItem.predicateProperty().bind(Bindings.createObjectBinding(() ->
                                        TreeItemPredicate.create(dNode ->
                                        !hide.get() || dNode.both())
                                , hide));
                    } catch (IOException e1) {
                        LOG.error("Failed to load tree", e1);
                    }
                }
        );
    }

    private void copy(DNode item) {
        copyService.copy(item);
    }

    public void onExit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
