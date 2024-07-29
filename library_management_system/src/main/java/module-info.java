module com.learning.library_management_system.library_management_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    opens com.learning.library_management_system.library_management_system to javafx.fxml;
    exports com.learning.library_management_system.library_management_system;
    exports com.learning.library_management_system.library_management_system.application;
    opens com.learning.library_management_system.library_management_system.application to javafx.fxml;
}