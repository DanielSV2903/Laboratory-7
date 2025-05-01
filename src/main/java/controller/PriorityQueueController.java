package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class PriorityQueueController {
    @javafx.fxml.FXML
    private TextArea tArea;
    @javafx.fxml.FXML
    private TableView tView;
    @javafx.fxml.FXML
    private TableColumn tColPriority;
    @javafx.fxml.FXML
    private ChoiceBox cBoxMood;
    @javafx.fxml.FXML
    private TextField tfName;
    @javafx.fxml.FXML
    private TableColumn tColMood;
    @javafx.fxml.FXML
    private TableColumn tColAttention;
    @javafx.fxml.FXML
    private ChoiceBox cBoxPriority;
    @javafx.fxml.FXML
    private TableColumn tColName;
    @javafx.fxml.FXML
    private BorderPane bp;

    @javafx.fxml.FXML
    public void enQueueOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
    }
}
