module com.learning.execute_system.exe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.learning.execute_system.exe to javafx.fxml;
    exports com.learning.execute_system.exe;
}