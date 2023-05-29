/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import Model.Acounts;
import View.ViewManager;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Mahmoud
 */
public class UpdateAccountController implements Initializable {

    private Acounts oldAccount;

    @FXML
    private TextField userIdTF;

    @FXML
    private TextField date;

    @FXML
    private TextField usernameTF;

    @FXML
    private TextField balance;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveNewAccountBtn;

    @FXML
    private TextField currency;

    @FXML
    private TextField accountNumber;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.oldAccount = Controller.Admin.AccountsManagmentController.selectedAccountToUpdate;

        //set text field's data the same as updatedUser data
        userIdTF.setText(String.valueOf(oldAccount.getUser_id()));
        accountNumber.setText(String.valueOf(oldAccount.getAccount_number()));
        usernameTF.setText(oldAccount.getUsername());
        currency.setText(oldAccount.getCurrency());
        balance.setText(String.valueOf(oldAccount.getBalance()));
        date.setText(String.valueOf(oldAccount.getCreation_date()));

    }

    @FXML
    void saveNewAccount(ActionEvent event) throws SQLException, ClassNotFoundException {
        String user_id = userIdTF.getText();
        int User_ID_Int = Integer.parseInt(user_id);

        String account_number = accountNumber.getText();
        int Account_Number_Int = Integer.parseInt(account_number);

        String username = usernameTF.getText();

        String currencyy = currency.getText();

        String balance_string = balance.getText();
        double balance_Int = 0.0; // Initialize the balance_Int variable

        try {
            balance_Int = Double.parseDouble(balance_string); // Convert the string to a double
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Handle the exception if the string is not a valid double
        }

      String create_date = date.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date utilCreationDate = null;
        try {
            utilCreationDate = dateFormat.parse(create_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        java.sql.Date sqlCreationDate = new java.sql.Date(utilCreationDate.getTime());

        // Create an Account object with the retrieved data
        Acounts account = new Acounts(User_ID_Int, Account_Number_Int, username, currencyy, balance_Int, sqlCreationDate);
        account.setId(oldAccount.getId());
        account.update();

        //after saving should return to the back scene and show an alert
        Controller.Admin.AccountsManagmentController.updateStage.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account inserted");
        alert.setContentText("Account inserted");
        alert.showAndWait();
    }

    @FXML
    void cancelAccountCreation(ActionEvent event) {
        Controller.Admin.AccountsManagmentController.updateStage.close();
    }

}
