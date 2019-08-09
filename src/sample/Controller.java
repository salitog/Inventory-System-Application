package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.*;

public class Controller implements Initializable {

    // Declaration of variables and components
    @FXML
    private Pane pnl_options, pnl_inv, pnl_list, pnl_idle, pnl_sideBar, pnl_idleInv, pnl_viewInv,
            pnl_inv_topBar, pnl_remPart, pnl_search, pnl_extraInfo;

    @FXML
    private StackPane pnl_addPart;

    @FXML
    private AnchorPane rootPane, pnl_remove;

    @FXML
    private JFXButton btn_inv, btn_list, btn_home, minus_button, plus_button, btn_viewInv, btn_revPart, btn_search;

    @FXML
    private JFXTreeTableView<Part> treeView;

    @FXML
    private JFXTreeTableView<Part> searchTree;

    @FXML
    private JFXTreeTableView<Part> removeTree;

    @FXML
    private JFXTreeTableView<Part> treeRemove;

    @FXML
    private JFXTreeTableView<Part> testTree;

    @FXML
    private JFXTextField searchBar, txt_quantity;

    @FXML
    private Label lbl_make, lbl_model, lbl_year, lbl_description, lbl_quantity, lbl_condition, lbl_location, label_notify;

    private int tempIndex = 0;

    ObservableList<Part> users = FXCollections.observableArrayList();
    ObservableList<Part> searchResults = FXCollections.observableArrayList();
    ObservableList<Part> removeList = FXCollections.observableArrayList();
    ObservableList<Part> testList = FXCollections.observableArrayList();

    public void viewInventory(ActionEvent event){
        JFXTreeTableColumn<Part, String> makeColumn = new JFXTreeTableColumn<>("Marca");
        makeColumn.setPrefWidth(80);
        makeColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().make;
            }
        });

        JFXTreeTableColumn<Part, String> modelColumn = new JFXTreeTableColumn<>("Modelo");
        modelColumn.setPrefWidth(100);
        modelColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().model;
            }
        });

        JFXTreeTableColumn<Part, String> yearColumn = new JFXTreeTableColumn<>("Año");
        yearColumn.setPrefWidth(50);
        yearColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().year;
            }
        });

        JFXTreeTableColumn<Part, String> descriptionColumn = new JFXTreeTableColumn<>("Descripción");
        descriptionColumn.setPrefWidth(400);
        descriptionColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().description;
            }
        });

        JFXTreeTableColumn<Part, String> quantityColumn = new JFXTreeTableColumn<>("Cantidad");
        quantityColumn.setPrefWidth(70);
        quantityColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().quantity;
            }
        });

        JFXTreeTableColumn<Part, String> conditionColumn = new JFXTreeTableColumn<>("Condicion");
        conditionColumn.setPrefWidth(150);
        conditionColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().condition;
            }
        });

        JFXTreeTableColumn<Part, String> locationColumn = new JFXTreeTableColumn<>("Ubicación");
        locationColumn.setPrefWidth(150);
        locationColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().location;
            }
        });

        final TreeItem<Part> root = new RecursiveTreeItem<Part>(users, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(makeColumn, modelColumn, yearColumn, descriptionColumn, quantityColumn, conditionColumn, locationColumn);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        pnl_viewInv.toFront();
        pnl_inv_topBar.toFront();
    }

    public void addPart(ActionEvent event){
        BoxBlur blur = new BoxBlur(3, 3 , 3);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();

        JFXButton button = new JFXButton("Continuar");
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setPrefSize(200, 50);
        button.setStyle("-fx-background-color: #26a633;");
        button.relocate(10,10);

        Pane dialogPanel = new Pane();
        dialogPanel.setPrefSize(500, 600);
        dialogPanel.setStyle("-fx-background-color: #999999");

        JFXTextField makeText = new JFXTextField();
        makeText.setPromptText("Marca");
        makeText.setStyle("-jfx-label-float: true;"+ "-jfx-focus-color: #ffa929;"+ "-jfx-unfocus-color:  #0b6599;");
        makeText.setPrefWidth(120);
        makeText.relocate(10, 60);

        JFXTextField modelText = new JFXTextField();
        modelText.setPromptText("Modelo");
        modelText.setStyle("-jfx-label-float: true;"+ "-jfx-focus-color: #ffa929;"+ "-jfx-unfocus-color:  #0b6599;");
        modelText.setPrefWidth(180);
        modelText.relocate(160 ,60);

        JFXTextField yearText = new JFXTextField();
        yearText.setPromptText("Año");
        yearText.setStyle("-jfx-label-float: true;"+ "-jfx-focus-color: #ffa929;"+ "-jfx-unfocus-color:  #0b6599;");
        yearText.setPrefWidth(80);
        yearText.relocate(360 ,15);

        //<editor-fold desc="makeBox">
        JFXComboBox makeBox = new JFXComboBox();
        ObservableList<String> makeList = FXCollections.observableArrayList("Nissan", "Toyota", "Honda",
                "Jeep", "Ford", "Mazda", "Mitsubishi", "BMW", "Lexus", "Otra");
        makeBox.setItems(makeList);
        makeBox.setPromptText("Marca");
        makeBox.setStyle("-jfx-label-float: true;"+ "-jfx-focus-color: #ffa929;"+ "-jfx-unfocus-color:  #0b6599;");
        makeBox.relocate(10, 15);
        //</editor-fold>

        //<editor-fold desc="modelBox">
        ObservableList<String> nissanList = FXCollections.observableArrayList("Pathfinder", "Rogue", "Murano", "Frontier", "Armada", "Otro");
        ObservableList<String> toyotaList = FXCollections.observableArrayList("4Runner", "Rav4", "Corolla", "Yaris", "IM", "Highlander", "Otro");
        ObservableList<String> hondaList = FXCollections.observableArrayList("CR-V", "HR-V", "Civic", "Pilot", "Otro");
        ObservableList<String> jeepList = FXCollections.observableArrayList("Cherokee", "Compass", "Grand Cherokee", "Renegade", "Otro");
        ObservableList<String> fordList = FXCollections.observableArrayList("Ranger", "Explorer", "Escape", "Otro");
        ObservableList<String> mazdaList = FXCollections.observableArrayList("CX-5", "CX-7", "CX-9", "CX-3", "3", "Otro");
        ObservableList<String> mitsubishiList = FXCollections.observableArrayList("Outlander", "Outlander Sport", "Endevour", "Otro");
        ObservableList<String> bmwList = FXCollections.observableArrayList("328", "X3", "X5", "Otro");
        ObservableList<String> lexusList = FXCollections.observableArrayList("RX-350", "GX-470", "Otro");


        JFXComboBox modelBox = new JFXComboBox();
        modelBox.setPromptText("Seleccione el modelo");
        modelBox.setStyle("-jfx-label-float: true;"+ "-jfx-focus-color: #ffa929;"+ "-jfx-unfocus-color:  #0b6599;");
        modelBox.relocate(160, 15);
        //</editor-fold>

        //<editor-fold desc="yearBox">
        JFXComboBox yearBox = new JFXComboBox();
        ObservableList<String> yearList = FXCollections.observableArrayList("2020", "2019", "2018",
                "2017", "2016", "2015", "Otro");
        yearBox.setItems(yearList);
        yearBox.setPromptText("Año");
        yearBox.setPrefWidth(80);
        yearBox.setStyle("-jfx-label-float: true;"+ "-jfx-focus-color: #ffa929;"+ "-jfx-unfocus-color:  #0b6599;");
        yearBox.relocate(360, 15);
        //</editor-fold>

        //<editor-fold desc="descriptionField">
        JFXTextField descriptionField = new JFXTextField();
        descriptionField.setPromptText("Descripción");
        descriptionField.setStyle("-jfx-label-float: true;"+ "-jfx-focus-color: #ffa929;"+ "-jfx-unfocus-color:  #0b6599;");
        descriptionField.setPrefWidth(250);
        descriptionField.relocate(10, 100);
        //</editor-fold>

        //<editor-fold desc="quantityField">
        JFXTextField quantityField = new JFXTextField();
        quantityField.setPromptText("Cantidad");
        quantityField.setStyle("-jfx-label-float: true;"+
                               "-jfx-focus-color: #ffa929;"+
                               "-jfx-unfocus-color:  #0b6599;");
        quantityField.setPrefWidth(60);
        quantityField.relocate(300, 100);
        //</editor-fold>

        //<editor-fold desc="conditionField">
        JFXTextField conditionField = new JFXTextField();
        conditionField.setPromptText("Condición");
        conditionField.setStyle("-jfx-lable-float: true;" + "-jfx-focus-color: #ffa929;" + "-jfx-unfocus-color: #0b6599;");
        conditionField.relocate(10, 190);
        //</editor-fold>

        //<editor-fold desc="locationField">
        ObservableList locations = FXCollections.observableArrayList("Bodega de Autolote", "Taller de Elias");
        JFXComboBox locationBox = new JFXComboBox();
        locationBox.setItems(locations);
        locationBox.setPromptText("Ubicación");
        locationBox.setStyle("-jfx-lable-float: true;" + "-jfx-focus-color: #ffa929;" + "-jfx-unfocus-color: #0b6599;");
        locationBox.relocate(225 ,190);

        //</editor-fold>

        //<editor-fold desc="Setup">
        dialogPanel.getChildren().addAll(makeBox, modelBox, yearBox, descriptionField, quantityField, makeText, modelText,
                                         conditionField, locationBox, yearText); //Add a part to the panel
        makeText.setVisible(false);
        modelText.setVisible(false);
        yearText.setVisible(false);

        makeBox.setOnAction(e -> {
            String make = makeBox.getValue().toString();
            if (make.equals("Nissan")){
                modelBox.setDisable(false);
                modelBox.setItems(nissanList);
                makeText.setVisible(false);
                modelText.setVisible(false);
            } else if (make.equals("Toyota")){
                modelBox.setDisable(false);
                modelBox.setItems(toyotaList);
                makeText.setVisible(false);
                modelText.setVisible(false);
            } else if (make.equals("Honda")){
                modelBox.setDisable(false);
                modelBox.setItems(hondaList);
                makeText.setVisible(false);
                modelText.setVisible(false);
            } else if (make.equals("Jeep")){
                modelBox.setDisable(false);
                modelBox.setItems(jeepList);
                makeText.setVisible(false);
                modelText.setVisible(false);
            } else if (make.equals("Ford")){
                modelBox.setDisable(false);
                modelBox.setItems(fordList);
                makeText.setVisible(false);
                modelText.setVisible(false);
            } else if (make.equals("Mazda")){
                modelBox.setDisable(false);
                modelBox.setItems(mazdaList);
                makeText.setVisible(false);
                modelText.setVisible(false);
            } else if (make.equals("Mitsubishi")){
                modelBox.setDisable(false);
                modelBox.setItems(mitsubishiList);
                makeText.setVisible(false);
                modelText.setVisible(false);
            } else if (make.equals("BMW")){
                modelBox.setDisable(false);
                modelBox.setItems(bmwList);
                makeText.setVisible(false);
                modelText.setVisible(false);
            } else if (make.equals("Lexus")){
                modelBox.setDisable(false);
                modelBox.setItems(lexusList);
                makeText.setVisible(false);
                modelText.setVisible(false);
            } else if (make.equals("Otra")){
                modelBox.setDisable(true);
                makeText.setVisible(true);
                modelText.setVisible(true);
                modelText.clear();
                makeText.clear();
            }
        });

        modelBox.setOnAction(x -> {
            if (modelBox.getValue() == "Otro"){
                modelBox.setDisable(true);
                modelText.setVisible(true);
            }
        });

        yearBox.setOnAction(x -> {
            if (yearBox.getValue() == "Otro"){
                yearBox.setVisible(false);
                yearText.setVisible(true);
            }
        });

        JFXDialog dialog = new JFXDialog(pnl_addPart, dialogLayout, JFXDialog.DialogTransition.TOP);
        Label heading = new Label("Agregar una parte al inventario");
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
            String make, model, year = "", desc = "", quant = "", condition = "", loc = "";

            if (makeBox.getValue() == "Otra"){
                make = makeText.getText();
                model = modelText.getText();
            } else {
                make = makeBox.getValue().toString();
                if (modelBox.getValue() == "Otro"){
                    model = modelText.getText();
                } else {
                    model = modelBox.getValue().toString();
                }

                if (yearBox.getValue() == "Otro"){
                    year = yearText.getText();
                } else {
                    year = yearBox.getValue().toString();
                }
                desc = descriptionField.getText();
                quant = quantityField.getText();
                condition = conditionField.getText();
                loc = locationBox.getValue().toString();
            }

            if (users.size() == 0){
                users.add(new Part(make, model, year, desc, quant, condition, loc, (users.size())));
                File file = new File("/Users/salvag/Desktop/AutoloteProgram/src/sample/Assets/inventoryInfo");
                try{
                    PrintWriter log = new PrintWriter(new FileWriter(file, true));
                    log.write(make + "\n");
                    log.write(model + "\n");
                    log.write(year + "\n");
                    log.write(desc + "\n");
                    log.write(quant + "\n");
                    log.write(condition + "\n");
                    log.write(loc + "\n");
                    log.close();
                } catch (IOException e){}
            } else {
                boolean existing = false;
                for (int i = 0; i < users.size(); i++){
                    if (users.get(i).make.get().toUpperCase().equals(make.toUpperCase())){
                        if (users.get(i).model.get().toUpperCase().equals(model.toUpperCase())){
                            if (users.get(i).year.get().toUpperCase().equals(year.toUpperCase())){
                                if (users.get(i).description.get().toUpperCase().equals(desc.toUpperCase())){
                                    if (users.get(i).condition.get().toUpperCase().equals(condition.toUpperCase())){
                                        if (users.get(i).location.get().toUpperCase().equals(loc.toUpperCase())){
                                            int q = Integer.parseInt(users.get(i).quantity.get());
                                            q += Integer.parseInt(quant);
                                            users.get(i).quantity = new SimpleStringProperty(Integer.toString(q));
                                            existing = true;
                                            //TODO rewrite the file with new info
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!existing){
                    users.add(new Part(make, model, year, desc, quant, condition, loc, (users.size())));
                    File file = new File("/Users/salvag/Desktop/AutoloteProgram/src/sample/Assets/inventoryInfo");
                    try{
                        PrintWriter log = new PrintWriter(new FileWriter(file, true));
                        log.write(make + "\n");
                        log.write(model + "\n");
                        log.write(year + "\n");
                        log.write(desc + "\n");
                        log.write(quant + "\n");
                        log.write(condition + "\n");
                        log.write(loc + "\n");
                        log.close();
                    } catch (IOException e){}
                }
            }

            rootPane.setEffect(null);
            dialog.close();
        });

        heading.setFont(Font.font("Helvetica Neue", 25));
        heading.setStyle("-fx-text-fill: #e6edf7;");

        dialogLayout.setStyle("-fx-background-color: #999999");
        dialogLayout.setHeading(heading);
        dialogLayout.setBody(dialogPanel);
        dialogLayout.setActions(button);
        dialogLayout.setPrefSize(500, 450);
        pnl_addPart.setVisible(true);
        pnl_addPart.toFront();
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1)->{
            rootPane.setEffect(null);
        });
        rootPane.setEffect(blur);
        //</editor-fold>
    }

    public void remove(ActionEvent event){  // V2
        JFXTreeTableColumn<Part, String> makeC = new JFXTreeTableColumn<>("Marca");
        makeC.setPrefWidth(80);
        makeC.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().make;
            }
        });
        JFXTreeTableColumn<Part, String> modelC = new JFXTreeTableColumn<>("Modelo");
        modelC.setPrefWidth(100);
        modelC.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().model;
            }
        });
        JFXTreeTableColumn<Part, String> yearC = new JFXTreeTableColumn<>("Año");
        yearC.setPrefWidth(50);
        yearC.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().year;
            }
        });
        JFXTreeTableColumn<Part, String> descC = new JFXTreeTableColumn<>("Descripcion");
        descC.setPrefWidth(350);
        descC.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().description;
            }
        });
        JFXTreeTableColumn<Part, String> quantC = new JFXTreeTableColumn<>("Cantidad");
        quantC.setPrefWidth(70);
        quantC.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().quantity;
            }
        });
        JFXTreeTableColumn<Part, String> locC = new JFXTreeTableColumn<>("Ubicación");
        locC.setPrefWidth(130);
        locC.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().location;
            }
        });
        JFXTreeTableColumn<Part, String> condC = new JFXTreeTableColumn<>("Condición");
        condC.setPrefWidth(150);
        condC.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().condition;
            }
        });
        JFXTreeTableColumn<Part, String> removeB = new JFXTreeTableColumn<>("Others");
        removeB.setPrefWidth(55);
        Callback<TreeTableColumn<Part, String>, TreeTableCell<Part, String>> cellFactory
                = //
                new Callback<TreeTableColumn<Part, String>, TreeTableCell<Part, String>>() {
                    @Override
                    public TreeTableCell call(final TreeTableColumn<Part, String> param) {
                        final TreeTableCell<Part, String> cell = new TreeTableCell<Part, String>() {

                            final JFXButton btn = new JFXButton("-");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setPrefWidth(55);
                                    btn.setStyle("-fx-background-color:  #e63238; -fx-text-fill: White;");
                                    btn.setOnAction(event -> {

                                        removeList.remove(getIndex());
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        removeB.setCellFactory(cellFactory);


        final TreeItem<Part> root = new RecursiveTreeItem<Part>(removeList, RecursiveTreeObject::getChildren);
        treeRemove.getColumns().setAll(makeC, modelC, yearC, descC, quantC, condC, locC, removeB);
        treeRemove.setRoot(root);
        treeRemove.setShowRoot(false);
        pnl_remove.toFront();
        pnl_inv_topBar.toFront();
    }

    public void searchPart(ActionEvent event){
        searchResults.clear();
        pnl_extraInfo.setVisible(false);
        JFXTreeTableColumn<Part, String> makeColumn = new JFXTreeTableColumn<>("Marca");
        makeColumn.setPrefWidth(75);
        makeColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().make;
            }
        });

        JFXTreeTableColumn<Part, String> modelColumn = new JFXTreeTableColumn<>("Modelo");
        modelColumn.setPrefWidth(100);
        modelColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().model;
            }
        });

        JFXTreeTableColumn<Part, String> yearColumn = new JFXTreeTableColumn<>("Año");
        yearColumn.setPrefWidth(50);
        yearColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().year;
            }
        });

        JFXTreeTableColumn<Part, String> descriptionColumn = new JFXTreeTableColumn<>("Descripción");
        descriptionColumn.setPrefWidth(375);
        descriptionColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().description;
            }
        });


        searchTree.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!(searchResults.size() == 0)){
                    pnl_extraInfo.setVisible(true);
                    lbl_make.setText(searchTree.getSelectionModel().getSelectedItem().getValue().make.get());
                    lbl_model.setText(searchTree.getSelectionModel().getSelectedItem().getValue().model.get());
                    lbl_year.setText(searchTree.getSelectionModel().getSelectedItem().getValue().year.get());
                    lbl_description.setText(searchTree.getSelectionModel().getSelectedItem().getValue().description.get());
                    lbl_quantity.setText(searchTree.getSelectionModel().getSelectedItem().getValue().quantity.get());
                    lbl_condition.setText(searchTree.getSelectionModel().getSelectedItem().getValue().condition.get());
                    lbl_location.setText(searchTree.getSelectionModel().getSelectedItem().getValue().location.get());
                    tempIndex = searchTree.getSelectionModel().getFocusedIndex();
                    System.out.println(tempIndex);
                    txt_quantity.setText("1");
                }
            }
        });

        minus_button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int current = Integer.parseInt(txt_quantity.getText());
                if (!(current <= 1)){
                    current -= 1;
                    txt_quantity.setText(Integer.toString(current));
                }
            }
        });

        plus_button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int current = Integer.parseInt(txt_quantity.getText());
                if (!(Integer.parseInt(lbl_quantity.getText()) == current)){
                    current += 1;
                    txt_quantity.setText(Integer.toString(current));
                }
            }
        });

        final TreeItem<Part> root = new RecursiveTreeItem<Part>(searchResults, RecursiveTreeObject::getChildren);
        searchTree.getColumns().setAll(makeColumn, modelColumn, yearColumn, descriptionColumn);
        searchTree.setRoot(root);
        searchTree.setShowRoot(false);
        searchBar.clear();
        pnl_search.toFront();
        pnl_inv_topBar.toFront();
        /* This is for later development (search without pressing enter)
        searchBar.setOnKeyReleased(e ->{

        });
        */
    }

    public void onEnter(ActionEvent event){
        searchResults.clear();
        searchResults.remove(0, searchResults.size()- 1);
        String keywords = searchBar.getText();
        ArrayList<String> s1 = new ArrayList<>();
        for(String w:keywords.split("\\s",0)){
            s1.add(w);
        }

        ObservableList<Part> usersCopy = FXCollections.observableArrayList();
        for (int i = 0; i < users.size(); i++){
            usersCopy.add(users.get(i));
        }

        if (s1.size() > 1){

            ObservableList<Part> tempResults = FXCollections.observableArrayList();
            ArrayList<String> s2 = new ArrayList<>();

            for (int x = 0; x < usersCopy.size(); x++){
                s2.clear();
                if (usersCopy.get(x).make.get().toUpperCase().equals(s1.get(0).toUpperCase())){
                    tempResults.add(usersCopy.get(x));
                    usersCopy.remove(x);
                    x -= 1;
                } else if (usersCopy.get(x).model.get().toUpperCase().equals(s1.get(0).toUpperCase())){
                    tempResults.add(usersCopy.get(x));
                    usersCopy.remove(x);
                    x -= 1;
                } else if (usersCopy.get(x).year.get().equals(s1.get(0).toUpperCase())){
                    tempResults.add(usersCopy.get(x));
                    usersCopy.remove(x);
                    x -= 1;
                } else {
                    for(String w:usersCopy.get(x).description.get().toUpperCase().split("\\s",0)){
                        s2.add(w);
                    }
                    for (int z = 0; z < s2.size(); z++){
                        if (s2.get(z).toUpperCase().equals(s1.get(0).toUpperCase())){
                            tempResults.add(usersCopy.get(x));
                            usersCopy.remove(x);
                            x -= 1;
                            z = s2.size();
                        }
                    }
                }
            }
            for (int i = 1; i < s1.size(); i++){
                for (int x = 0; x < tempResults.size(); x++){
                    s2.clear();
                    boolean pass = false;
                    if (tempResults.get(x).make.get().toUpperCase().equals(s1.get(i).toUpperCase())){
                        pass = true;
                    } else if (tempResults.get(x).model.get().toUpperCase().equals(s1.get(i).toUpperCase())){
                        pass = true;
                    } else if (tempResults.get(x).year.get().toUpperCase().equals(s1.get(i).toUpperCase())){
                        pass = true;
                    } else {
                        for (String w:tempResults.get(x).description.get().toUpperCase().split("\\s",0)){
                            s2.add(w);
                        }
                        for (int z = 0; z < s2.size(); z++){
                            if (s2.get(z).toUpperCase().equals(s1.get(i).toUpperCase())){
                                pass = true;
                            }
                        }
                    }
                    if (!pass){
                        tempResults.remove(x);
                        x--;
                    }
                }
            }
            for (int i = 0; i < tempResults.size(); i++){
                searchResults.add(tempResults.get(i));
            }
        } else { // One Word Search
            ArrayList<String> s2 = new ArrayList<>();
            for (int x = 0; x < usersCopy.size(); x++){
                s2.clear();
                if (usersCopy.get(x).make.get().toUpperCase().equals(s1.get(0).toUpperCase())){
                    searchResults.add(usersCopy.get(x));
                    usersCopy.remove(x);
                    x -= 1;
                } else if (usersCopy.get(x).model.get().toUpperCase().equals(s1.get(0).toUpperCase())){
                    searchResults.add(usersCopy.get(x));
                    usersCopy.remove(x);
                    x -= 1;
                } else if (usersCopy.get(x).year.get().equals(s1.get(0).toUpperCase())){
                    searchResults.add(usersCopy.get(x));
                    usersCopy.remove(x);
                    x -= 1;
                } else {
                    for(String w:usersCopy.get(x).description.get().toUpperCase().split("\\s",0)){
                        s2.add(w);
                    }
                    for (int z = 0; z < s2.size(); z++){
                        if (s2.get(z).toUpperCase().equals(s1.get(0).toUpperCase())){
                            searchResults.add(usersCopy.get(x));
                            usersCopy.remove(x);
                            x -= 1;
                            z = s2.size();
                        }
                    }
                }
            }
        } // One Word Search
    }  // For searching

    public void addToCart(){
        if (Integer.parseInt(txt_quantity.getText()) == Integer.parseInt(lbl_quantity.getText())){
            System.out.println(searchResults);
            System.out.println(tempIndex);
            System.out.println(searchResults.get(tempIndex).print());
            removeList.add(users.get(searchResults.get(tempIndex).index.get()));
            removeList.get(removeList.size() - 1).full = true;
            users.remove(searchResults.get(tempIndex).index.get());
            searchResults.remove(searchTree.getSelectionModel().getSelectedIndex());
            for (int i =0; i < users.size(); i++){
                users.get(i).index.set(i);
            }
            lbl_make.setText(searchTree.getSelectionModel().getSelectedItem().getValue().make.get());
            lbl_model.setText(searchTree.getSelectionModel().getSelectedItem().getValue().model.get());
            lbl_year.setText(searchTree.getSelectionModel().getSelectedItem().getValue().year.get());
            lbl_description.setText(searchTree.getSelectionModel().getSelectedItem().getValue().description.get());
            lbl_quantity.setText(searchTree.getSelectionModel().getSelectedItem().getValue().quantity.get());
            lbl_condition.setText(searchTree.getSelectionModel().getSelectedItem().getValue().condition.get());
            lbl_location.setText(searchTree.getSelectionModel().getSelectedItem().getValue().location.get());
            label_notify.setText(Integer.toString(removeList.size()));
            System.out.println(tempIndex);
            tempIndex -= 1;
            System.out.println(tempIndex);

        } else { //lbl => la cantidad del repuesto  txt => la cantidad a quitar
            int dif = Integer.parseInt(lbl_quantity.getText()) - Integer.parseInt(txt_quantity.getText());
            boolean existing = false;
            for (int i = 0; i < removeList.size(); i++){
                if (searchTree.getSelectionModel().getSelectedItem().getValue().make == removeList.get(i).make){
                    if (searchTree.getSelectionModel().getSelectedItem().getValue().model == removeList.get(i).model){
                        if (searchTree.getSelectionModel().getSelectedItem().getValue().year == removeList.get(i).year){
                            if (searchTree.getSelectionModel().getSelectedItem().getValue().description == removeList.get(i).description){
                                if (searchTree.getSelectionModel().getSelectedItem().getValue().condition == removeList.get(i).condition){
                                    if (searchTree.getSelectionModel().getSelectedItem().getValue().location == removeList.get(i).location){
                                        removeList.get(i).quantity.set(Integer.toString(Integer.parseInt(removeList.get(i).quantity.get() + Integer.parseInt(txt_quantity.getText()))));
                                        existing = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!existing){
                removeList.add(new Part(searchResults.get(tempIndex).make.get(), searchResults.get(tempIndex).model.get(),
                        searchResults.get(tempIndex).year.get(), searchResults.get(tempIndex).description.get(), txt_quantity.getText(),
                        searchResults.get(tempIndex).condition.get(), searchResults.get(tempIndex).location.get(), searchResults.get(tempIndex).index.get()));
            }
        }
    }

    // Change the RH panel to whatever function (inventario or lista) is chose
    public void changeFunction(ActionEvent event){
        if (event.getSource() == btn_inv){
            pnl_options.setVisible(false);
            pnl_idle.setVisible(false);
            pnl_inv.toFront();
            pnl_idleInv.toFront();
            pnl_inv_topBar.toFront();
            pnl_sideBar.toFront();
        } else if (event.getSource() == btn_list){
            pnl_options.setVisible(false);
            pnl_idle.setVisible(false);
            pnl_list.toFront();
            pnl_sideBar.toFront();
        }
    }

    // Take the user back to the homepage
    public void home(ActionEvent event){
        if (event.getSource() == btn_home) {
            pnl_idle.setVisible(true);
            pnl_options.setVisible(true);
            pnl_idle.toFront();
            pnl_options.toFront();
            pnl_sideBar.toFront();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        File file = new File("/Users/salvag/Desktop/AutoloteProgram/src/sample/Assets/inventoryInfo");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int counter = 0;
            ArrayList<String> temp = new ArrayList<>();
            while ((line = reader.readLine()) != null){
                temp.add(line);
                counter++;
            }
            for (int i = 0; i < counter/7; i++){
                String[] tempInfo = new String[7];
                for (int x = 0; x < 7; x++){
                    tempInfo[x] = temp.get(x + (7 * i));
                }
                users.add(new Part(tempInfo[0], tempInfo[1], tempInfo[2], tempInfo[3], tempInfo[4], tempInfo[5], tempInfo[6], i));
            }
        } catch (IOException ex){ }



        pnl_idle.toFront();
        pnl_options.toFront();
        pnl_sideBar.toFront();
    }

    class Part extends RecursiveTreeObject<Part> {

        StringProperty make;
        StringProperty model;
        StringProperty year;
        StringProperty description;
        StringProperty quantity;
        StringProperty condition;
        StringProperty location;
        IntegerProperty index;
        boolean full = false;
        int previousIndex = 0;

        public Part(String make, String model, String year, String description, String quantity, String condition, String location, int index){
            this.make = new SimpleStringProperty(make);
            this.model = new SimpleStringProperty(model);
            this.year = new SimpleStringProperty(year);
            this.description = new SimpleStringProperty(description);
            this.quantity = new SimpleStringProperty(quantity);
            this.condition = new SimpleStringProperty(condition);
            this.location = new SimpleStringProperty(location);
            this.index = new SimpleIntegerProperty(index);
        }

        public String print(){
            return (make.get() + " " + model.get() + " " + year.get() + " " + description.get() + " " + quantity.get() + " " + condition.get() + " " + location.get() + " | " + index.get());
        }

    }
}
