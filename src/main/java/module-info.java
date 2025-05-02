module ucr.laboratory7 {
    requires javafx.controls;
    requires javafx.fxml;


    exports ucr.laboratory7;
    opens ucr.laboratory7 to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
    exports domain.queue;
    opens domain.queue to javafx.fxml;
    exports domain.person;
    opens domain.person to javafx.fxml;
    opens domain.stack to javafx.fxml;
    exports domain.stack;
}