package sample.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.entity.Human;
import sample.validator.DataValidator;

public class EditController {

    @FXML
    private AnchorPane  anchorPane ;

    @FXML
    private TextField nameTextF;

    @FXML
    private TextField ageTextF;

    @FXML
    private TextField birthdayTextF;

    private Human human = new Human();

    public void actionClose(ActionEvent actionEvent){
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent actionEvent){
        DataValidator dataValidator = new DataValidator();

        if(dataValidator.validateAge(ageTextF.getText()) && dataValidator.validateDate(birthdayTextF.getText())) {
            Human human = new Human(nameTextF.getText(), Integer.parseInt(ageTextF.getText()), birthdayTextF.getText());
            Controller.getInstance().updateHuman(human);

            actionClose(actionEvent);
        } else {
            showErrorDialog("Incorrect input!", "Age must be an integer number and birthday must be in dd/mm/yyyy format.");
        }
    }

    public void setHuman(Human currentHuman){
        this.human = currentHuman;

        Platform.runLater(()->{
            nameTextF.setText(human.getName());
            ageTextF.setText(String.valueOf(human.getAge()));
            birthdayTextF.setText(String.valueOf(human.getBirthdayString()));
        });
    }

    public void showErrorDialog(String header, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
}
