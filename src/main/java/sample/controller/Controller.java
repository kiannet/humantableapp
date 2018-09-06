package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.entity.Human;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private TreeTableView<Human> humanTable;

    @FXML
    private TreeTableColumn<Human, String> columnName;

    @FXML
    private TreeTableColumn<Human, Integer> columnAge;

    @FXML
    private TreeTableColumn<Human, String> columnBirthday;


    private AddEditController editController;


    private static Controller controller;
    public static Controller getInstance() {
        return controller;
    }
    public Controller() {
        controller = this;
    }


    private ObservableList<Human> humans = FXCollections.observableArrayList();



    volatile boolean checkAdd = false;
    volatile boolean checkUpdate = false;
    volatile boolean checkDelete = false;


    private void initList(){
        Human human1 = new Human("Anna", 20, "20/11/1998");
        Human human2 = new Human("Irina", 20, "05/09/1998");
        Human human3 = new Human("Wlad", 20, "21/11/1998");
        Human human4 = new Human("Ivan", 20, "25/11/1998");
        Human human5 = new Human("Nikita", 20, "20/10/1998");

        humans.add(human1);
        humans.add(human2);
        humans.add(human3);
        humans.add(human4);
        humans.add(human5);

    }

    private void setTreeView(TreeItem<Human> rootNode) {
        FXCollections.sort(humans);
        for(Human human : humans){
            rootNode.getChildren().add(new TreeItem<>(human));
        }
    }

    public void openAddWindow(ActionEvent actionEvent){
        try{
            checkAdd = true;
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/add.fxml"));
            stage.setTitle("Add a new human");
            stage.setMinHeight(150);
            stage.setMinWidth(300);
            stage.setResizable(false);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    TreeItem<Human> treeItem;
    public void openUpdateWindow(ActionEvent actionEvent){
        try {

            checkUpdate = true;
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/change.fxml"));

            AnchorPane anchorPane = loader.load();
            editController = loader.getController();
            treeItem = humanTable.getSelectionModel().getSelectedItem();

            if (treeItem != null) {
                Human selectedHuman = treeItem.getValue();
                editController.setHuman(selectedHuman);
                humans.remove(selectedHuman);
                anchorPane = editController.getAnchorPane();
                stage.setTitle("Update information");
                stage.setMinHeight(150);
                stage.setMinWidth(300);
                stage.setResizable(false);
                Scene scene = new Scene(anchorPane);
                scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
                stage.show();
            }
            else {
                showErrorDialog("No human chosen!","You haven't chosen a human to update!");
            }
            } catch(IOException e){
                e.printStackTrace();
            }


    }

    public void addHuman(Human human){
        humans.add(human);
        TreeItem<Human> rootNode = new TreeItem<>(human);
        humanTable.getRoot().getChildren().add(rootNode);
    }

    public void updateHuman(Human updatedHuman){
        humanTable.getRoot().getChildren().remove(treeItem);
        humans.add(updatedHuman);
        TreeItem<Human> rootNode = new TreeItem<>(updatedHuman);
        humanTable.getRoot().getChildren().add(rootNode);

    }


    public void deleteHuman(){
        checkDelete = true;

        TreeItem<Human> treeItem = humanTable.getSelectionModel().getSelectedItem();

        if(treeItem != null){
            Human deleteHuman = treeItem.getValue();
            humans.remove(deleteHuman);
            humanTable.getRoot().getChildren().clear();
            for(Human human : humans){
                TreeItem<Human> rootNode = new TreeItem<Human>(human);
                humanTable.getRoot().getChildren().add(rootNode);
            }
        }
        else {
            showErrorDialog("No human chosen!", "You haven't chosen a human to delete!");
        }

    }

    public void showErrorDialog(String header, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void showBirthdayDialog(Human human){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("BIRTHDAY");
        alert.setHeaderText("Happy Birthday!");
        alert.setContentText("Happy Birthday to " + human.getName() + "!");

        alert.showAndWait();
    }

    public boolean checkIfTodayIsBirthday(Human human){
        boolean birthday = false;

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int todayMonth = localDate.getMonthValue();
        int todayDay = localDate.getDayOfMonth();

        LocalDate birthdayDate = human.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = birthdayDate.getMonthValue();
        int day = birthdayDate.getDayOfMonth();

        if (todayMonth == month && todayDay == day){
            birthday = true;
        }

        return birthday;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnName.setCellValueFactory(new TreeItemPropertyValueFactory<Human, String>("name"));
        columnAge.setCellValueFactory(new TreeItemPropertyValueFactory<Human, Integer>("age"));
        columnBirthday.setCellValueFactory(new TreeItemPropertyValueFactory<Human, String>("birthdayString"));

        initList();

        TreeItem<Human> rootNode = new TreeItem<Human>(humans.get(0));

        setTreeView(rootNode);
        humanTable.setRoot(rootNode);
        humanTable.setShowRoot(false);

        humanTable.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 2)
            {
                TreeItem<Human> item = humanTable.getSelectionModel().getSelectedItem();
                if(checkIfTodayIsBirthday(item.getValue())){
                    showBirthdayDialog(item.getValue());
                }
            }
        });
    }
}
