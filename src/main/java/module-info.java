module ucr.laboratory7 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ucr.laboratory7 to javafx.fxml;
    exports ucr.laboratory7;
    exports controller;
    opens controller to javafx.fxml;
    exports domain;
    opens domain to javafx.fxml;
    exports domain.queue;
    opens domain.queue to javafx.fxml;
    exports domain.person;
    opens domain.person to javafx.fxml;
}