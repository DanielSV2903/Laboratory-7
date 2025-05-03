package controller;

import domain.person.Climate;
import domain.person.Place;
import domain.person.Weather;
import domain.queue.LinkedQueue;
import domain.queue.QueueException;
import domain.stack.LinkedStack;
import domain.stack.StackException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Alert alert;
    private LinkedQueue queue;
    private ObservableList<Climate> climateObservableList;
    private LinkedStack stack;
    private boolean showingQueue;

    @javafx.fxml.FXML
    public void initialize() {
        this.queue = new LinkedQueue();
        this.stack = new LinkedStack();
        ObservableList<String> weatherData = FXCollections.observableArrayList(util.Utility.getWeather());
        climateObservableList = FXCollections.observableArrayList();
        tViewQueue.setItems(climateObservableList);
        this.choiceBoxWh.setItems(weatherData);
        tColumnQueuePlace.setCellValueFactory(new PropertyValueFactory<>("place"));
        tColumnQueueWc.setCellValueFactory(new PropertyValueFactory<>("weather"));
        tColumnStackPlace.setCellValueFactory(new PropertyValueFactory<>("place"));
        tColumnStackWc.setCellValueFactory(new PropertyValueFactory<>("weather"));
        this.showingQueue = true;




    }

    @javafx.fxml.FXML
    public void btnClearOnAction(ActionEvent actionEvent) {
        this.tFieldPlace.clear();
        this.choiceBoxWh.getSelectionModel().clearSelection();
        queue.clear();
        updateTableViewQueue();


    }

    @javafx.fxml.FXML
    public void autoEnQueueOnAction(ActionEvent actionEvent) {
        climateObservableList.clear();
        queue.clear();

        ObservableList<Climate> weatherData = FXCollections.observableArrayList(util.Utility.generateRandomClimateQueue());

        try {
            for (Climate climate : weatherData) {
                queue.enQueue(climate);
                climateObservableList.add(climate);
            }

            mostrarAlerta("Se agregaron 20 climas", Alert.AlertType.INFORMATION);
        } catch (QueueException e) {
            mostrarAlerta("Hubo un error", Alert.AlertType.ERROR);
        }

    }

    @javafx.fxml.FXML
    public void btnToOnAction(ActionEvent actionEvent) {
        try {
            if (showingQueue) {
                //queue to stack
                stack = new LinkedStack();
                LinkedQueue auxQueue = new LinkedQueue();


                while (!queue.isEmpty()) {
                    Climate climate = (Climate) queue.deQueue();
                    stack.push(climate);//se pasa el queue a stack
                    auxQueue.enQueue(climate);//se guarda la lista og
                }

                //restaurar cola og
                while (!auxQueue.isEmpty())
                    queue.enQueue(auxQueue.deQueue());


                updateTableViewQueue();
                showingQueue = false;
            } else {
                //stack a queue
                queue = new LinkedQueue();
                LinkedStack auxStack = new LinkedStack();

                while (!stack.isEmpty()) {
                    Climate climate = (Climate) stack.pop();
                    stack.push(climate);
                }

                while (!auxStack.isEmpty()) {
                    Climate climate = (Climate) auxStack.pop();
                    queue.enQueue(climate);
                    stack.push(climate);
                }

                updateTableViewQueue();
                showingQueue = true;
            }
        } catch (Exception e) {
            mostrarAlerta("Error al transferir de queue a stack", Alert.AlertType.ERROR);
        }
    }


    @javafx.fxml.FXML
    public void enQueueOnAction(ActionEvent actionEvent) {
        if (!validation()) {
            mostrarAlerta("Asegurese de llenar todas las casillas", Alert.AlertType.ERROR);
        } else {
            if (util.Utility.validarEntradasQueueToStack(tFieldPlace, choiceBoxWh)){
                String placeText = tFieldPlace.getText();
                String weatherText = (String) choiceBoxWh.getSelectionModel().getSelectedItem();

                Place place = new Place(placeText);
                Weather weather = new Weather(weatherText);
                Climate climate = new Climate(place, weather);
                queue.enQueue(climate);
                climateObservableList.add(climate);

                mostrarAlerta("Entrada exitosamente", Alert.AlertType.INFORMATION);
            }
        }
    }

    private ObservableList<String> getWeather() {
        ObservableList<String> weatherData = FXCollections.observableArrayList(util.Utility.getWeather());
        return weatherData;
    }

    private ObservableList<String> getPlace() {
        ObservableList<String> placeData = FXCollections.observableArrayList(util.Utility.getPlace());
        return placeData;
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Mensaje");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private boolean validation(){
        return (!tFieldPlace.getText().isEmpty() && choiceBoxWh.getValue() != null);
    }

    private void updateTableViewQueue() {
        tViewQueue.getItems().clear();

        if (queue != null && !queue.isEmpty()) {
            try {
                LinkedQueue aux = new LinkedQueue();


                while (!queue.isEmpty()) {
                    Climate climate = (Climate) queue.deQueue();
                    tViewQueue.getItems().add(climate);
                    aux.enQueue(climate);
                }

                while (!aux.isEmpty()) {
                    queue.enQueue(aux.deQueue());
                }

            } catch (QueueException e) {
                mostrarAlerta("Error al actualizar la tabla", Alert.AlertType.ERROR);
            }
        }
    }

    private void updateTableViewStack() {
        tViewStack.getItems().clear();

        if (stack != null && !stack.isEmpty()) {
            try{
                LinkedStack aux = new LinkedStack();
                while (!stack.isEmpty()) {
                    Climate climate = (Climate) stack.pop();
                    tViewStack.getItems().add(climate);
                    aux.push(climate);
                }

                while (!aux.isEmpty()) {
                    stack.push(aux.pop());
                }
            } catch (StackException e) {
                mostrarAlerta("Error al actualizar la tabla", Alert.AlertType.ERROR);
            }
        }
    }


}