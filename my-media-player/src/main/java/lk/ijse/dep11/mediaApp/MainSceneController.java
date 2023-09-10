package lk.ijse.dep11.mediaApp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javafx.scene.input.MouseEvent;
import java.io.File;

public class MainSceneController {
    public Button btnChooseFile;
    public Button btnPlay;
    public Button btnPause;
    public Button btnStop;
    public Button btnBack10s;
    public Button btnFront10s;
    public MediaView mediaView;
    public BorderPane borderPane;
    public Button btnSlowRate;
    public Button btnFastForward;
    public Slider pbProgress;
    public Slider volumeSlider;

    private MediaPlayer mediaPlayer;
    private Timeline timeline;

    public void initialize(){
        // Initialize the volume slider
        volumeSlider.setValue(50.0); // Set the initial volume value (e.g., 50%)

        // Add a listener to the volume slider to adjust the media player's volume
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue() / 100.0); // Adjust volume from 0.0 to 1.0
            }
        });

    }

    public void btnChooseFileOnAction(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Media File"); // Set the dialog title
        //Set desired file extensions
        FileChooser.ExtensionFilter mediaFilter = new FileChooser.ExtensionFilter("Media Files", "*.mp3", "*.mp4", "*.avi", "*.mkv");
        fileChooser.getExtensionFilters().add(mediaFilter);

        // Show the open dialog
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            String filePath = file.toURI().toString();

            // Create a Media object from the selected file
            Media media = new Media(filePath);

            // Create a MediaPlayer from the Media object
            mediaPlayer = new MediaPlayer(media);

            // Create a MediaView to display the video (for video files)
            mediaView.setMediaPlayer(mediaPlayer);

            DoubleProperty widthProp = mediaView.fitWidthProperty();
            DoubleProperty heightProp = mediaView.fitHeightProperty();

            widthProp.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            heightProp.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                    pbProgress.setValue(newValue.toSeconds());
                }
            });

            // Add an EventHandler to the Slider to handle user dragging
            pbProgress.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(pbProgress.getValue()));

                }
            });

            pbProgress.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(pbProgress.getValue()));

                }
            });

            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    Duration total = media.getDuration();
                    pbProgress.setMax(total.toSeconds());
                }
            });
        }

    }

    public void btnPlayOnAction(ActionEvent e) {
        if (mediaPlayer != null) {
            mediaPlayer.play();
            mediaPlayer.setRate(1.0);
        }
    }

    public void btnPauseOnAction(ActionEvent e) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        }
    }
    public void btnStopOnAction(ActionEvent e) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void btnSlowRateOnAction(ActionEvent e) {
        if (mediaPlayer != null) {
            // Decrease the playback rate (slow down)
            mediaPlayer.setRate(0.8);
        }
    }

    public void btnFastForwardOnAction(ActionEvent e) {
        if (mediaPlayer != null) {
            // Increase the playback rate (fast forward)
            mediaPlayer.setRate(1.5);
        }
    }

    public void btnBack10sOnAction(ActionEvent e) {
        // Get the current playback time
        double currentTime = mediaPlayer.getCurrentTime().toSeconds();

        // Calculate the new time (10 seconds backward)
        double newTime = currentTime - 10.0;

        // Ensure the new time is within valid bounds (0 to media duration)
        newTime = Math.max(newTime, 0);
        newTime = Math.min(newTime, mediaPlayer.getTotalDuration().toSeconds());

        // Seek to the new time
        mediaPlayer.seek(new javafx.util.Duration(newTime * 1000)); // Convert to milliseconds
    }

    public void btnFront10sOnAction(ActionEvent e) {
        // Get the current playback time
        double currentTime = mediaPlayer.getCurrentTime().toSeconds();

        // Calculate the new time (10 seconds forward)
        double newTime = currentTime + 10.0;

        // Ensure the new time is within valid bounds (0 to media duration)
        newTime = Math.max(newTime, 0);
        newTime = Math.min(newTime, mediaPlayer.getTotalDuration().toSeconds());

        // Seek to the new time
        mediaPlayer.seek(new javafx.util.Duration(newTime * 1000)); // Convert to milliseconds
    }




}
