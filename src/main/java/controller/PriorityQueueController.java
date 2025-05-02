package controller;

import domain.person.Person;
import domain.queue.PriorityLinkedQueue;
import domain.queue.QueueException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;


public class PriorityQueueController {
    @javafx.fxml.FXML
    private TextArea tArea;
    @javafx.fxml.FXML
    private TableView tView;
    @javafx.fxml.FXML
    private TableColumn<List<String>,String> tColPriority;
    @javafx.fxml.FXML
    private ChoiceBox<String> cBoxMood;
    @javafx.fxml.FXML
    private TextField tfName;
    @javafx.fxml.FXML
    private TableColumn<List<String>,String> tColMood;
    @javafx.fxml.FXML
    private TableColumn<List<String>,String> tColAttention;
    @javafx.fxml.FXML
    private ChoiceBox<String> cBoxPriority;
    @javafx.fxml.FXML
    private TableColumn<List<String>,String> tColName;
    @javafx.fxml.FXML
    private BorderPane bp;
    private PriorityLinkedQueue priorityQueue;
    private Alert alert;
    private int priority;
    private ObservableList<List<String>> data;
    @javafx.fxml.FXML
    public void initialize() {
        this.priorityQueue = new PriorityLinkedQueue();
        data = getData();

    }

    @javafx.fxml.FXML
    public void enQueueOnAction(ActionEvent actionEvent) {
        String name=tfName.getText().trim();
        String mood=cBoxMood.getSelectionModel().getSelectedItem().trim();
        priority=prioritySelection();
        int attentionTime=util.Utility.random(90);
        Person person=new Person(name,mood,attentionTime);
        priorityQueue.enQueue(person,priority);
        updateTableView();
    }


    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        this.tView.getItems().clear();
        this.cBoxMood.getSelectionModel().clearSelection();
        this.cBoxPriority.getSelectionModel().clearSelection();
        this.tfName.clear();
        this.tArea.setText("Atenttion process:");
    }

    @javafx.fxml.FXML
    public void attentionProcessOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void autoEnQueueOnAction(ActionEvent actionEvent) {
    }
    private int prioritySelection(){
        int selectedIndex;
        String selection=cBoxPriority.getSelectionModel().getSelectedItem();
        switch(selection){
            case "high":
                selectedIndex=3;
                break;
            case "medium":
                selectedIndex=2;
                break;
            case "low":
                selectedIndex=1;
                break;
            default:
                selectedIndex=0;
                break;
        }
        return selectedIndex;
    };

    private void updateTableView() {
       //Hacer el metodo
    }
    private ObservableList<List<String>> getData() {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        if(priorityQueue!=null &&!priorityQueue.isEmpty()){
            try {
                PriorityLinkedQueue aux = new PriorityLinkedQueue();
               while (!priorityQueue.isEmpty()){
                   Person person = (Person) priorityQueue.deQueue();
                   List<String> arrayList = new ArrayList<>();
                   arrayList.add(String.valueOf(person.getName()));
                   arrayList.add(person.getMood());
                   arrayList.add(String.valueOf(person.getAttentionTime()));
                   arrayList.add(String.valueOf(priority));
                   data.add(arrayList);
                   aux.enQueue(person);
               }
               while (!aux.isEmpty()){
                   Person person = (Person) aux.deQueue();
                   priorityQueue.enQueue(person);
               }
            } catch (QueueException ex) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("There was an error in the process");
                alert.showAndWait();

            }
        }
        return data;
    }
}
