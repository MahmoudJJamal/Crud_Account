/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import Model.Acounts;
import View.ViewManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yahya
 */
public class AccountsManagmentController implements Initializable {
        public static Acounts selectedAccountToUpdate;
        public static Stage updateStage;
    
    @FXML
    private Button updateSelectedAccountBtn;
    @FXML
    private Button createNewAccountrBtn;

    @FXML
    private Button accountsPageBtn;

    @FXML
    private Button searchAccountBtn;

    @FXML
    private Button usersManagmentPageBtn;

    @FXML
    private Button operationsPageBtn;

    @FXML
    private Button showAllAccountsBtn;

    @FXML
    private TextField accontSearchTF;

    @FXML
    private Button deleteSelectedAccountBtn;

    @FXML
    private TableView<Acounts> tableViewAccount;
    @FXML
    private TableColumn<Acounts, Integer> idAccount;
    @FXML
    private TableColumn<Acounts, Integer> userIdAccount;
    @FXML
    private TableColumn<Acounts, Integer> accountNumberAccount;
    @FXML
    private TableColumn<Acounts, String> userNameAccount;
    @FXML
    private TableColumn<Acounts, String> currencyAccount;
    @FXML
    private TableColumn<Acounts, Double> BalanceAccount;
    @FXML
    private TableColumn<Acounts, Date> creationDateAccount;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     if (idAccount != null) {
        idAccount.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
    if (userIdAccount != null) {
        userIdAccount.setCellValueFactory(new PropertyValueFactory<>("user_id"));
    }
    if (accountNumberAccount != null) {
        accountNumberAccount.setCellValueFactory(new PropertyValueFactory<>("account_number"));
    }
    if (userNameAccount != null) {
        userNameAccount.setCellValueFactory(new PropertyValueFactory<>("username"));
    }
    if (currencyAccount != null) {
        currencyAccount.setCellValueFactory(new PropertyValueFactory<>("currency"));
    }
    if (BalanceAccount != null) {
        BalanceAccount.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }
    if (creationDateAccount != null) {
        creationDateAccount.setCellValueFactory(new PropertyValueFactory<>("creation_date"));
    }
    }    

    @FXML
    private void showUsersManagmentPage(ActionEvent event) {
         ViewManager.adminPage.changeSceneToUsersManagment();
    }

    @FXML
    private void showAccountsPage(ActionEvent event) {
    }

    @FXML
    private void showOperationsPage(ActionEvent event) {
    }

    @FXML
    private void showAccountCreationPage(ActionEvent event) {
        ViewManager.adminPage.changeSceneToCreateAccount();
    }

    @FXML
    private void showAllAccounts(ActionEvent event) throws SQLException, ClassNotFoundException {
         ObservableList<Acounts> accountList =
        FXCollections.observableArrayList(Acounts.getAllAccount());
        tableViewAccount.setItems(accountList);  
    }

    @FXML
    private void updateSelectedAccount(ActionEvent event) throws IOException {
         if(tableViewAccount.getSelectionModel().getSelectedItem() != null){
        //store the selected user from the TableView in our global var user selectedUserToUpdate   
        selectedAccountToUpdate = tableViewAccount.getSelectionModel().getSelectedItem();
        //load update page fxml
        FXMLLoader loaderUpdate = new FXMLLoader(getClass().getResource("/View/AdminFXML/UpdateAccount.fxml"));
        Parent rootUpdate = loaderUpdate.load();     
        Scene updateAccountScene = new Scene(rootUpdate); 
        //store loaded fxml in our global stage updateStage and show it
        updateStage = new Stage();
        updateStage.setScene(updateAccountScene);
        updateStage.setTitle("Update Account " + selectedAccountToUpdate.getUsername() );
        updateStage.show();
        }
    }

    @FXML
    private void deleteSelectedAccount(ActionEvent event) {
          if(tableViewAccount.getSelectionModel().getSelectedItem() != null){
            //store the selected user from the TableView in new user object
            Acounts selectedUser = tableViewAccount.getSelectionModel().getSelectedItem();
            
            //show an confirmation alert and make the deletion on confirm event
            Alert deleteConfirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteConfirmAlert.setTitle("Account delete");
            deleteConfirmAlert.setContentText("Are you sure to delete this Account ?");
            deleteConfirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    //delete the selected user from database table using delete method in our User model
                    selectedUser.delete();
                } catch (SQLException ex) {
                    Logger.getLogger(UsersManagmentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(UsersManagmentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            Alert deletedSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
            deletedSuccessAlert.setTitle("Account deleted");
            deletedSuccessAlert.setContentText("Account deleted");
            deletedSuccessAlert.show();  
            }
            });
        
        }else{
        Alert warnAlert = new Alert(Alert.AlertType.WARNING);
        warnAlert.setTitle("Select an user");
        warnAlert.setContentText("Please select an user from the table view");
        warnAlert.show();  
        }
    }
    
        
    

    @FXML
    private void searchForAnAccount(ActionEvent event) {
    }
    
}
