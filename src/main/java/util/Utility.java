package util;
import domain.queue.LinkedQueue;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Utility {
    private static  Random random;
    private static LinkedQueue queue;

    //constructor estatico, inicializador estatico
    static {
        // semilla para el random
        long seed = System.currentTimeMillis();
        random = new Random(seed);
    }
    public static int random(int bound){
        //return (int)Math.floor(Math.random()*bound); //forma 1
        return 1+random.nextInt(bound);
    }
    public static int random(int bound1,int bound2){
        //return (int)Math.floor(Math.random()*bound); //forma 1
        return 1+random.nextInt(bound1,bound2);
    }

    public static void fill(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = random(99);
        }
    }

    public static String format(long n) {
        return new DecimalFormat("###,###,###.##").format(n);
    }

    public static int min(int x, int y) {
        return x<y ? x : y;
    }

    public static int max(int x, int y) {
        return x>y ? x : y;
    }

    public static String show(int[] a) {
        String result="";
        for(int item : a){
            if(item == 0) break; //si es cero es xq no hay mas elementos
            result+=item + " ";
        }
        return result;
    }

    public static int compare(Object a, Object b) {
        switch(instanceOf(a, b)){
            case "Integer":
                Integer int1 = (Integer)a; Integer int2 = (Integer)b;
                return int1 < int2 ? -1 : int1 > int2 ? 1 : 0;

            case "String":
                String str1 = (String)a; String str2 = (String)b;
                return str1.compareTo(str2) < 0 ? -1 : str1.compareTo(str2) > 0 ? 1 : 0;

            case "Character":
                Character ch1 = (Character) a; Character ch2 = (Character) b;
                return ch1.compareTo(ch2) < 0 ? -1 : ch1.compareTo(ch2) > 0 ? 1 : 0;

//            case "Employee":
//                Employee st1 = (Employee) a; Employee st2 = (Employee) b;
//                return st1.getId()<st2.getId() ? -1 :  st1.getId()>st2.getId()  ? 1 : 0;
//
//            case "JobPosition":
//                JobPosition jp1 = (JobPosition) a; JobPosition jp2 = (JobPosition) b;
//                return jp1.getId()<jp2.getId() ? -1 :  jp1.getId()>jp2.getId()  ? 1 : 0;
//
//            case "Staffing":
//                Staffing stf1 = (Staffing) a; Staffing stf2 = (Staffing) b;
//                return stf1.getId()<stf2.getId() ? -1 : stf1.getId() > stf2.getId() ? 1 : 0;
        }
        return 2; //Unknown
    }

    public static String instanceOf(Object a, Object b){
        if(a instanceof Integer && b instanceof Integer) return "Integer";
        if(a instanceof String && b instanceof String) return "String";
        if(a instanceof Character && b instanceof Character) return "Character";
//        if(a instanceof Employee && b instanceof Employee) return "Employee";
//        if(a instanceof JobPosition && b instanceof JobPosition) return "JobPosition";
//        if(a instanceof Staffing && b instanceof Staffing) return "Staffing";
        return "Unknown";
    }
    private static void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error de validación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    public static String dateFormat(Date birthDay) {
        return new SimpleDateFormat("d/M/yyyy").format(birthDay);
    }
    public static String date_Hours_Format(LocalDateTime birthDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm:ss");
        return birthDay.format(formatter);
    }
    public static int getAge(Date birthDay) {
        LocalDate birth=birthDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now=LocalDate.now();
        return Period.between(birth,now).getYears();
    }

    public static boolean validarEntradasEmployee(TextField lastnameTextField, TextField firstnameTextField, TextField idTextField, DatePicker birthdayDatePicker, TextField titleTextField) {
//        //Validar nombre: solo letras y espacios
        String nombre = firstnameTextField.getText().trim();
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            mostrarAlerta("El nombre solo debe contener letras y espacios.");
            return false;
        }
        String apellido = lastnameTextField.getText().trim();
        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            mostrarAlerta("El apellido solo debe contener letras y espacios.");
            return false;
        }
        String title = lastnameTextField.getText().trim();
        if (!title.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            mostrarAlerta("El titulo solo debe contener letras y espacios.");
            return false;
        }
        String idTexto = idTextField.getText().trim();
        if (!idTexto.matches("\\d{9}")) {
            mostrarAlerta("El formato del ID es incorrecto. Debe ser de 9 caracteres (letras y/o números).");
            idTextField.requestFocus();
            return false;
        }
        try {
            String fechaTexto = birthdayDatePicker.getEditor().getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate localDate = LocalDate.parse(fechaTexto, formatter);

            ZoneId defaultZoneId = ZoneId.systemDefault();
            Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
            int edad = getAge(date);
            if (edad < 18 || edad > 120) {
                mostrarAlerta("La edad ingresada no es válida. (Rango: 18-120 años).");
                return false;
            }

        } catch (DateTimeParseException e) {
            mostrarAlerta("La fecha ingresada debe tener el formato d/M/yyyy (por ejemplo: 5/4/2000).");
            return false;
        }
        return true;
    }

    public static boolean validarEntradasQueueToStack(TextField tFieldPlace, ChoiceBox choiceBoxWh) {
        String place = tFieldPlace.getText().trim();

        if (!place.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            mostrarAlerta("El lugar solo debe contener letras y espacios.");
            return false;
        }

        // Validar que se haya seleccionado un clima
        if (choiceBoxWh.getValue() == null) {
            mostrarAlerta("Debe seleccionar un clima.");
            return false;
        }

        return true;
    }


    public static boolean validarEntradasJobPos(TextField tfDescription, TextField tfHourlyWage) {
        String description = tfDescription.getText().trim();
        if (!description.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            mostrarAlerta("La descripcion solo debe contener letras y espacios.");
            return false;
        }
        double wage=Double.parseDouble(tfHourlyWage.getText().trim());
        if (wage<0){
            mostrarAlerta("El hourly wage no puede ser menor que 0.");
            return false;
        }
        return true;
    }



    private static Object determinarLetra(int value) {
        switch (value) {
            case 10:return "A";
            case 11:return "B";
            case 12:return "C";
            case 13:return "D";
            case 14:return "E";
            case 15:return "F";
            default:return "0";
        }
    }

    public static String randomGetPlace(){
        String places[] = {"San José", "Ciudad Quesada", "Paraíso",
                "Turrialba", "Limón", "Liberia", "Puntarenas", "San Ramón", "Puerto Viejo", "Volcán Irazú", "Pérez Zeledón",
                "Palmares", "Orotina", "El coco", "Ciudad Neilly", "Sixaola", "Guápiles","Siquirres"
                , "El Guarco", "Cartago", "Santa Bárbara", "Jacó", "Manuel Antonio", "Quepos", "Santa Cruz",
                "Nicoya"};

        return places[random(places.length-1)];
    }

    public static String randomGetWeather(){
        String weather[] = {"rainy", "thunderstorm", "sunny", "cloudy", "foggy"};

        return weather[random(weather.length-1)];
    }

    public static String[] getPlace(){
        String places[] = {"San José", "Ciudad Quesada", "Paraíso",
                "Turrialba", "Limón", "Liberia", "Puntarenas", "San Ramón", "Puerto Viejo", "Volcán Irazú", "Pérez Zeledón",
                "Palmares", "Orotina", "El coco", "Ciudad Neilly", "Sixaola", "Guápiles","Siquirres"
                , "El Guarco", "Cartago", "Santa Bárbara", "Jacó", "Manuel Antonio", "Quepos", "Santa Cruz",
                "Nicoya"};

        return places;
    }

    public static String[] getWeather(){
        String weather[] = {"rainy", "thunderstorm", "sunny", "cloudy", "foggy"};

        return weather;
    }

    public static Object getLinkedQueue(){
        return queue;
    }

    public static void setLinkedQueue(LinkedQueue queue) {
        Utility.queue = queue;
    }

}
//    private ObservableList<List<String>> getEmployeeList() {
//        ObservableList<List<String>> data = FXCollections.observableArrayList();
//        if(employeeList!=null &&!employeeList.isEmpty()){
//            try {
//                for (int i = 1; i <= employeeList.size(); i++) {
//                    Employee employee = (Employee) employeeList.getNode(i).data;
//                    List<String> arrayList = new ArrayList<>();
//                    arrayList.add(String.valueOf(employee.getId()));
//                    arrayList.add(employee.getName());
//                    arrayList.add(employee.getTitle());
//                    arrayList.add(String.valueOf(employee.getAge()));
//                    data.add(arrayList);
//                }
//            } catch (ListException ex) {
//                alert.setAlertType(Alert.AlertType.ERROR);
//                alert.setContentText("There was an error in the process");
//                alert.showAndWait();
//
//        }
//        return data;
//        }
