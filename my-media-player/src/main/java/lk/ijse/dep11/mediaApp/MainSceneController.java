package lk.ijse.dep11.mediaApp;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import java.io.File;

public class MainSceneController {
    public Button btnChooseFile;
    public Button btnPlay;
    public Button btnPause;
    public Button btnStop;
    public Button btnBackward;
    public Button btnForward;
    public Button btnBack10s;
    public Button btnFront10s;
    public MediaView mediaView;
    public BorderPane borderPane;

    private MediaPlayer mediaPlayer;

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

            mediaPlayer.play();
        }


    }

    public void btnPlayOnAction(ActionEvent e) {
        
    }

    public void btnPauseOnAction(ActionEvent actionEvent) {
    }

    public void btnBackwardOnAction(ActionEvent actionEvent) {
    }

    public void btnStopOnAction(ActionEvent actionEvent) {
    }

    public void btnForwardOnAction(ActionEvent actionEvent) {
    }

    public void btnBack10sOnAction(ActionEvent actionEvent) {
    }

    public void btnFront10sOnAction(ActionEvent actionEvent) {
    }
}
