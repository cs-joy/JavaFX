module com.learning.media_player.mediaplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.learning.media_player.mediaplayer to javafx.fxml;
    exports com.learning.media_player.mediaplayer;
}