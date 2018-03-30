/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlitepersonexample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author blj0011
 */
public class FXMLDocumentController implements Initializable
{

    @FXML
    private Label lblLastAction;
    @FXML
    private TableColumn<Person, String> tcFirstName, tcLastName;
    @FXML
    private TableColumn<Person, Integer> tcId, tcAge;
    @FXML
    private TableView<Person> tvMain;
    @FXML
    private Spinner<Integer> spAge;
    @FXML
    private TextField tfFirstName, tfLastName;

    DBHandler dBHandler;

    @FXML
    private void handleBtnAddPerson(ActionEvent actionEvent)
    {
        Person person = new Person(tfFirstName.getText().trim(), tfLastName.getText().trim(), spAge.getValue());
        if (dBHandler.addNewPerson(person)) {
            //Update TableView
            person.setId(dBHandler.getLastIDFromPersonTable());//Update the Person's ID to the AutoIncrement ID in the Database
            tvMain.getItems().add(person);//Add new Person to the TableView
            lblLastAction.setText(person.getFirstName() + " added!");
        }
        else {
            lblLastAction.setText(person.getFirstName() + " failed to add!");
        }

    }

    @FXML
    private void handleBtnDeletePerson(ActionEvent actionEvent)
    {
        Person person = tvMain.getSelectionModel().getSelectedItem();

        if (dBHandler.deletePerson(person)) {
            //Update TableView
            tvMain.getItems().remove(person);//Remove Person from the TableView
            lblLastAction.setText(person.getFirstName() + " deleted!");
        }
        else {
            lblLastAction.setText(person.getFirstName() + " failed to delete!");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        tcId.setCellValueFactory(new PropertyValueFactory("id"));
        tcFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        tcLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        tcAge.setCellValueFactory(new PropertyValueFactory("age"));

        dBHandler = new DBHandler();
        tvMain.getItems().setAll(dBHandler.getDBData());

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 125, 25);
        spAge.setValueFactory(valueFactory);
    }

}
