package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardInfo {
    String[] boardSizes = {"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};

    @FXML
    private ComboBox<String> boardSize;

    public void setSize () {
        boardSize.setItems(FXCollections.observableArrayList(boardSizes));
    }

    public void switchToMainGame (ActionEvent event) throws IOException {
        if (boardSize.getValue() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            controller.setScene(Integer.parseInt(boardSize.getValue()));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Board Size");
            alert.setContentText("You need to select board size before start a game!");
            alert.show();
        }
    }

}
