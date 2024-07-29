package com.learning.media_player.mediaplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class MediaPlayerController {

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnStop;

    @FXML
    private Label lblDuration;

    @FXML
    private MediaView mediaView;

    @FXML
    private Slider slider;

    private Media media;

    private MediaPlayer mediaPlayer;

    private boolean isPlayed = false;

    @FXML
    void btnPlay(MouseEvent event) {
        if(!isPlayed) {
            btnPlay.setText("Pause");
            mediaPlayer.play();
            isPlayed = true;
        } else {
            btnPlay.setText("Play");
            mediaPlayer.pause();
            isPlayed = false;
        }

    }

    @FXML
    void btnStop(MouseEvent event) {
        btnPlay.setText("Play");
        mediaPlayer.stop();
        isPlayed = false;
    }

    // time
    int hours = 0;
    int minutes = 0;
    int seconds = 0;

    @FXML
    void selectMedia(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Media");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String url = selectedFile.toURI().toString();

            media = new Media(url);
            mediaPlayer = new MediaPlayer(media);

            mediaView.setMediaPlayer(mediaPlayer);



            mediaPlayer.currentTimeProperty().addListener((observableValue, duration, t1) -> {
                slider.setValue(t1.toSeconds());

                hours = (int)media.getDuration().toSeconds() / 3600;
                minutes = ((int)media.getDuration().toSeconds() % 3600) / 60;
                seconds = (int)media.getDuration().toSeconds() % 60;

                System.out.println((int)slider.getValue());

                //lblDuration.setText("Duration: " + (int)slider.getValue() + " / " + (int)media.getDuration().toSeconds());
                lblDuration.setText("Duration: " + (int)slider.getValue() + " / " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
            });

            mediaPlayer.setOnReady(() -> {
                Duration totalDuration = media.getDuration();
                slider.setMax(totalDuration.toSeconds());
                lblDuration.setText("Duration: 00 / " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                //lblDuration.setText(timeString);
            });

            Scene scene = mediaView.getScene();
            mediaView.fitWidthProperty().bind(scene.widthProperty());
            mediaView.fitHeightProperty().bind(scene.heightProperty());

            //mediaPlayer.setAutoPlay(true);
        }
    }

    @FXML
    private void sliderPress(MouseEvent event) {
        mediaPlayer.seek(Duration.seconds(slider.getValue()));
    }

}
