module simulator {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports simulator.main to javafx.graphics;
    opens simulator.controller to javafx.fxml;
}