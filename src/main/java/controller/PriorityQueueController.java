package controller;

import domain.person.Climate;
import domain.person.Person;
import domain.queue.PriorityLinkedQueue;
import domain.queue.QueueException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import util.Utility;

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

    private List<String> personPriorities;

    private ObservableList<List<String>> data;
    @javafx.fxml.FXML
    public void initialize() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        personPriorities = new ArrayList<>();
        this.priorityQueue = new PriorityLinkedQueue();
        data = getData();
        this.cBoxPriority.setItems(util.Utility.getPriorityData());
        this.cBoxMood.setItems(util.Utility.getMoodData());

        tColName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        tColMood.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        tColAttention.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        tColPriority.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));

        try {
            this.tView.setItems(data);
        }catch (QueueException e){
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void enQueueOnAction(ActionEvent actionEvent) {
        try {
        if (tfName.getText().isEmpty() || cBoxMood.getSelectionModel().getSelectedItem() == null || cBoxPriority.getSelectionModel().getSelectedItem() == null) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Asegurese de haber llenado todos los espacios solicitados");
            alert.showAndWait();
        } else {
            String name=tfName.getText().trim();
            String mood=cBoxMood.getSelectionModel().getSelectedItem().trim();
            personPriorities.add(cBoxPriority.getSelectionModel().getSelectedItem().trim());
            int attentionTime=util.Utility.random(99);
            Person person=new Person(name,mood,attentionTime);
            priorityQueue.enQueue(person,priority);
            updateTableView();
        }
        } catch (QueueException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
        }
    }


    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        this.tView.getItems().clear();
        this.cBoxMood.getSelectionModel().clearSelection();
        this.cBoxPriority.getSelectionModel().clearSelection();
        this.tfName.clear();
        this.tArea.setText("Atenttion process:");
        priorityQueue.clear();
    }

    @javafx.fxml.FXML
    public void attentionProcessOnAction(ActionEvent actionEvent) {
        StringBuilder builder=new StringBuilder();
        builder.append(tArea.getText());
        if (!priorityQueue.isEmpty()) {
            Person person=(Person)priorityQueue.deQueue();
            builder.append("\n"+person.getName()+", mood:"+person.getMood()+" fue atendido");
        }
        updateTableView();
        tArea.setText(builder.toString());
    }

    @javafx.fxml.FXML
    public void autoEnQueueOnAction(ActionEvent actionEvent) {
        priorityQueue=Utility.generateRandomPersonsQueue();
        personPriorities.clear();
        data=getDataAutoEnQueue();
        tView.setItems(data);
    }

    private ObservableList<List<String>> getDataAutoEnQueue() {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        personPriorities.addAll(Utility.getPersonPriorities());
        try {
            PriorityLinkedQueue aux = new PriorityLinkedQueue();
            int i = 0;
            while (!priorityQueue.isEmpty() && i < personPriorities.size()) {
                Person t = (Person) priorityQueue.deQueue();
                List<String> arrayList = new ArrayList<>();
                arrayList.add(t.getName());
                arrayList.add(t.getMood());
                arrayList.add(String.valueOf(t.getAttentionTime()));
                arrayList.add(personPriorities.get(i)); // usar índice correcto
                data.add(arrayList);
                aux.enQueue(t);
                i++;
            }
            while (!aux.isEmpty()) {
                priorityQueue.enQueue(aux.deQueue());
            }
        } catch (QueueException ex) {
            ex.printStackTrace(); // O manejar como tú prefieras
        }

        return data;
    }

    private int prioritySelection(String selection){
        int selectedIndex;
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
    private String priorityString(){
        return switch (priority) {
            case 1 -> "low";
            case 2 -> "medium";
            case 3 -> "high";
            default -> " ";
        };
    }

    private void updateTableView() throws QueueException {
        this.tView.getItems().clear(); //clear table
        PriorityLinkedQueue aux=new PriorityLinkedQueue();
        if(priorityQueue!=null && !priorityQueue.isEmpty()){
               ObservableList<List<String>> data=getData();
               this.tView.setItems(data);
            }
        }

    private ObservableList<List<String>> getData() {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        personPriorities.addAll(Utility.getPersonPriorities());
        if(priorityQueue!=null &&!priorityQueue.isEmpty()){
            try {
               PriorityLinkedQueue aux = new PriorityLinkedQueue();
               int i = 0;
               while (!priorityQueue.isEmpty()){
                   Person person = (Person) priorityQueue.deQueue();
                   List<String> arrayList = new ArrayList<>();
                   arrayList.add(person.getName());
                   arrayList.add(person.getMood());
                   arrayList.add(String.valueOf(person.getAttentionTime()));
                   arrayList.add(personPriorities.get(i));
                   data.add(arrayList);
                   aux.enQueue(person);
                   i++;
               }
               while (!aux.isEmpty()){
                   priorityQueue.enQueue(aux.deQueue());
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
