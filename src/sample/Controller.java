package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // Declaration of variables and components
    @FXML
    private Pane pnl_options, pnl_inv, pnl_list, pnl_idle, pnl_sideBar, pnl_idleInv, pnl_viewInv,
            pnl_inv_topBar, pnl_remPart, pnl_test;

    @FXML
    private StackPane pnl_addPart;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXButton btn_inv, btn_list, btn_home, btn_viewInv, btn_addPart, btn_revPart, btn_search;

    @FXML
    private JFXTreeTableView<Part> treeView;

    ObservableList<Part> users = FXCollections.observableArrayList();

    public void viewInventory(ActionEvent event)  {
        JFXTreeTableColumn<Part, String> makeColumn = new JFXTreeTableColumn<>("Marca");
        makeColumn.setPrefWidth(150);
        makeColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Part, String> param) {
                return param.getValue().getValue().make;
            }
        });

        JFXTreeTableColumn<Part, String> modelColumn = new JFXTreeTableColumn<>("Modelo");
        modelColumn.setPrefWidth(150);
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
        quantityColumn.setPrefWidth(100);
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

        //<editor-fold desc="Temporary Text File ">

        //</editor-fold>

        final TreeItem<Part> root = new RecursiveTreeItem<Part>(users, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(makeColumn, modelColumn, yearColumn, descriptionColumn, quantityColumn, conditionColumn);
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
        button.setStyle("-fx-background-color: #26a633;");

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
        ObservableList<String> hondaList = FXCollections.observableArrayList("CR-V", "HR-C", "Civic", "Pilot", "Otro");
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
        System.out.println(javafx.scene.text.Font.getFamilies());
        Label heading = new Label("Agregar una parte al inventario");
        int counter = 0;
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

            users.add(new Part(make, model, year, desc, quant, condition, loc));
            File file = new File("/Users/salvag/Desktop/AutoloteProgram/src/sample/Assets/inventoryInfo");
            try{
                FileWriter writer = new FileWriter(file);

                writer.write(make);
                writer.write(model);
                writer.write(year);
                writer.write(desc);
                writer.write(quant);
                writer.write(condition);
                writer.write(loc);
            } catch (IOException e){}

            rootPane.setEffect(null);
            dialog.close();
        });

        heading.setFont(Font.font("Helvetica Neue", 25));
        heading.setStyle("-fx-text-fill: #e6edf7;");

        dialogLayout.setStyle("-fx-background-color: #999999");
        dialogLayout.setHeading(heading);
        dialogLayout.setBody(dialogPanel);
        dialogLayout.setActions(button);
        dialogLayout.setPrefSize(500, 600);
        pnl_addPart.setVisible(true);
        pnl_addPart.toFront();
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1)->{
            rootPane.setEffect(null);
        });
        rootPane.setEffect(blur);
        //</editor-fold>
    }

    public void removePart(ActionEvent event){
        pnl_remPart.toFront();
        pnl_inv_topBar.toFront();
    }

    public void searchPart(ActionEvent event){

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
                users.add(new Part(tempInfo[0], tempInfo[1], tempInfo[2], tempInfo[3], tempInfo[4], tempInfo[5], tempInfo[6]));
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

        public Part(String make, String model, String year, String description, String quantity, String condition, String location){
            this.make = new SimpleStringProperty(make);
            this.model = new SimpleStringProperty(model);
            this.year = new SimpleStringProperty(year);
            this.description = new SimpleStringProperty(description);
            this.quantity = new SimpleStringProperty(quantity);
            this.condition = new SimpleStringProperty(condition);
            this.location = new SimpleStringProperty(location);
        }

    }
}
