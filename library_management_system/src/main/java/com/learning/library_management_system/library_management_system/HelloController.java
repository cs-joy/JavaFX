package com.learning.library_management_system.library_management_system;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.learning.library_management_system.library_management_system.application.LibraryController;

import java.io.IOException;

public class HelloController {

    @FXML
    protected void onStartButtonClick() throws IOException {
        LibraryController.changeScene();
    }
}