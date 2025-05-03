package controller;

import domain.person.Person;
import domain.queue.PriorityLinkedQueue;
import domain.queue.QueueException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
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
    private ObservableList<List<String>> data;
    private List<String> prioritiesOrder;

    @javafx.fxml.FXML
    public void initialize() {
        prioritiesOrder = Utility.getPersonPriorities();
        alert = new Alert(Alert.AlertType.INFORMATION);
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
        String name=tfName.getText().trim();
        String mood=cBoxMood.getSelectionModel().getSelectedItem().trim();
        String selection= String.valueOf(cBoxPriority.getSelectionModel().getSelectedItem());
        priority=prioritySelection(selection);
        int attentionTime=util.Utility.random(99);
        Person person=new Person(name,mood,attentionTime);
        if (queueValidation(person)){
            priorityQueue.enQueue(person,priority);
            prioritiesOrder.add(selection);
        }else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ingrese una persona no repetida");
            alert.showAndWait();
        }
        updateTableView();
    }

    private boolean queueValidation(Person person) {
        PriorityLinkedQueue aux = new PriorityLinkedQueue();
        boolean queueAble=true;
        while (!priorityQueue.isEmpty()) {
            Person p= (Person) priorityQueue.deQueue();
            if (person.getMood().equals(p.getMood())&&person.getName().equals(p.getName())) {
                queueAble=false;
            }
            aux.enQueue(p);
        }
        while (!aux.isEmpty()) {
            Person p= (Person) aux.deQueue();
            priorityQueue.enQueue(p);
        }
        return queueAble;
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
        generateAutoEnqueue(20);
        data=getDataAutoEnQueue();
        tView.setItems(data);
    }

    private ObservableList<List<String>> getDataAutoEnQueue() {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        prioritiesOrder=Utility.getPersonPriorities();
        try {
            PriorityLinkedQueue aux = new PriorityLinkedQueue();
            int i = 0;
            while (!priorityQueue.isEmpty() && i < prioritiesOrder.size()) {
                Person t = (Person) priorityQueue.deQueue();
                List<String> arrayList = new ArrayList<>();
                arrayList.add(t.getName());
                arrayList.add(t.getMood());
                arrayList.add(String.valueOf(t.getAttentionTime()));
                sortPriorities(prioritiesOrder);
                arrayList.add(prioritiesOrder.get(i)); // usar índice correcto
                data.add(arrayList);
                aux.enQueue(t);
                i++;
            }
            while (!aux.isEmpty()) {
                priorityQueue.enQueue(aux.deQueue());
            }
        } catch (QueueException ex) {
            ex.printStackTrace();
        }

        return data;
    }

    private void sortPriorities(List<String> personPriorities) {
        personPriorities.sort((p1, p2) -> {
            return getPriorityValue(p1) - getPriorityValue(p2);
        });
    }
    private int getPriorityValue(String priority) {//invierte las prioridades para que el parametro del sort funcione
        return switch (priority.toLowerCase()) {
            case "high" -> 0;
            case "medium" -> 1;
            case "low" -> 2;
            default -> Integer.MAX_VALUE;
        };
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
        if(priorityQueue!=null &&!priorityQueue.isEmpty()){
            try {
                PriorityLinkedQueue aux = new PriorityLinkedQueue();
                int i = 0;
                while (!priorityQueue.isEmpty() && i < prioritiesOrder.size()) {
                    Person t = (Person) priorityQueue.deQueue();
                    List<String> arrayList = new ArrayList<>();
                    arrayList.add(t.getName());
                    arrayList.add(t.getMood());
                    arrayList.add(String.valueOf(t.getAttentionTime()));
                    sortPriorities(prioritiesOrder);
                    arrayList.add(prioritiesOrder.get(i));
                    data.add(arrayList);
                    aux.enQueue(t);
                    i++;
                }
                while (!aux.isEmpty()) {
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
    private void generateAutoEnqueue(int count) {
        priorityQueue.clear();
        prioritiesOrder.clear();
        for (int i = 0; i < count; i++) {
            String name = Utility.generateRandomName();
            String mood = Utility.getRandomMood();
            int attentionTime = Utility.random(99);
            String priorityStr = Utility.getRandomPriority();

            int priority = prioritySelection(priorityStr);

            Person person = new Person(name, mood, attentionTime);
            priorityQueue.enQueue(person, priority);
            prioritiesOrder.add(priorityStr);
        }
    }
}
