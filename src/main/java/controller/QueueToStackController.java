package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class QueueToStackController {
    @javafx.fxml.FXML
    private TableView tViewStack;
    @javafx.fxml.FXML
    private TableColumn tColumnQueueWc;
    @javafx.fxml.FXML
    private TableColumn tColumnStackWc;
    @javafx.fxml.FXML
    private TableColumn tColumnStackPlace;
    @javafx.fxml.FXML
    private TableView tViewQueue;
    @javafx.fxml.FXML
    private TableColumn tColumnQueuePlace;
    @javafx.fxml.FXML
    private TextField tFieldPlace;
    @javafx.fxml.FXML
    private ChoiceBox choiceBoxWh;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private Button btnEnQueueOnAction;

    @javafx.fxml.FXML
    public void btnClearOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void autoEnQueueOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void btnToOnAction(ActionEvent actionEvent) {
    }
}