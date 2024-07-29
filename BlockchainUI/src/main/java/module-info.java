module com.learning.blockchain_ui.blockchainui {
    requires javafx.controls;
    requires javafx.fxml;

    //requires core;
    //requires utils;
    requires crypto;
    requires java.desktop;
    requires abi;
    requires core;
    requires utils;
    requires tuples;
    requires io.reactivex.rxjava2;


    opens com.learning.blockchain_ui.blockchainui to javafx.fxml;
    exports com.learning.blockchain_ui.blockchainui;
}