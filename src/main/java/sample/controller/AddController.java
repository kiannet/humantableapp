package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.entity.Human;
import sample.validator.DataValidator;

import java.net.URL;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField ageTextField;

    @FXML
    private TextField birthdayTextField;

    public void addHuman(ActionEvent actionEvent){

        DataValidator dataValidator = new DataValidator();

        if(dataValidator.validateAge(ageTextField.getText()) && dataValidator.validateDate(birthdayTextField.getText())){
            Human human = new Human(nameTextField.getText(), Integer.parseInt(ageTextField.getText()), birthdayTextField.getText());

            Controller.getInstance().addHuman(human);

            actionClose(actionEvent);}
        else {
            showErrorDialog("Incorrect input!", "Age must be an integer number and birthday must be in dd/mm/yyyy format.");
        }
    }

    public void actionClose(ActionEvent actionEvent){
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void showErrorDialog(String header, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
