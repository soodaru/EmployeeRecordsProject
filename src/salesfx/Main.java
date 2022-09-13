/**
 * Ayah Mahmoud & Arushi Sood
 * 991647463 & 991644338
 * Final Project
 * April 17, 2022
 */
package salesfx;
import content.Employee;
import content.EmployeeFile;
import content.SearchStage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    // Build Label controls
    private final Label lblID = new Label("Employee ID: ");
    private final Label lblName = new Label("Name: ");
    private final Label lblCity = new Label("Location: ");
    private final Label lblPosition = new Label("Position: ");
    
    // Build TextField controls
    private final TextField txtID = new TextField();
    private final TextField txtName = new TextField();
    private final TextField txtCity = new TextField();
    private final TextField txtPosition = new TextField();
    
    // Build Button controls
    private final Button btnFirst = new Button("First");
    private final Button btnNext = new Button("Next");
    private final Button btnPrevious = new Button("Previous");
    private final Button btnLast = new Button("Last");
    private final Button btnUpdate = new Button("Update");
    private final Button btnDelete = new Button("Delete");
    private final Button btnAdd = new Button("Add");
    private final Button btnSearch = new Button("Search");
    
    // Build Panes
    private final HBox tPane = new HBox(getTextGrid());
    private final HBox bPane = new HBox(getButtonGrid());
    private final VBox pane = new VBox(10, tPane, bPane);
    
    // Build Scene
    private final Scene scene = new Scene(pane, 400, 300);
    
    // Create global objects and variables to access in local classes and methods
    private File file = new File("Employee.dat");
    private ArrayList<Employee> employeeList;
    private int index;
    private boolean addClicked = false;
    private Alert errorAlert = new Alert(AlertType.ERROR);
    private Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
    private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    
    @Override
    public void start(Stage stage) throws IOException {
        tPane.setAlignment(Pos.CENTER);
        bPane.setAlignment(Pos.CENTER);
        pane.setAlignment(Pos.CENTER);
        stage.setScene(scene);
        scene.getStylesheets().add("/css/ProjectStyleSheet.css");
        
        employeeList = EmployeeFile.getEmployees(file);

        // Basic navigation buttons
        btnFirst.setOnAction(new FirstEmployee());
        btnPrevious.setOnAction(new PreviousEmployee());
        btnNext.setOnAction(new NextEmployee());
        btnLast.setOnAction(new LastEmployee());
        
        // Sets TextFields to first Employee record when program starts
        btnFirst.fire();
        txtID.requestFocus();
        
        // Style alert dialog boxes
        setAlerts();
        
        // Add actions to buttons
        btnUpdate.setOnAction(new UpdateRecord());
        btnSearch.setOnAction(new SearchRecords());
        btnAdd.setOnAction(new AddRecord());
        btnDelete.setOnAction(new DeleteRecord());
        
        // Update sequential file when program closes
        stage.setOnCloseRequest(new EndProgram());
        
        // Ensures user can only enter positive integers for employee ID field
        txtID.setOnKeyReleased(e-> {
            if (e.getCode() != KeyCode.TAB && e.getCode() != KeyCode.BACK_SPACE) {              
                if (!e.getCode().isDigitKey()) {
                    txtID.selectBackward();
                    errorAlert.setHeaderText("Invalid Employee ID");
                    errorAlert.setContentText("Employee ID must be a positive integer.");
                    errorAlert.show();
                }       
            }
        });
        
        // Displays stage
        stage.setTitle("Employee Records");
        stage.show();
    }
    
    /*
     * Navigation classes to go to first or last record and move forwards or backwards
     * Modifies global variable index that keeps track of navigation through employeeList index
     * Calls setTextBoxes() method to fill TextFields with employeeList object data fields
     */
    public class FirstEmployee implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent t) {
            index = 0;
            setTextBoxes(index);
        }
    }
    
    public class PreviousEmployee implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent t) {    
            if (index > 0) {
                index--;
                setTextBoxes(index);         
            }
        }
    }
    
    public class NextEmployee implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent t) {    
            if (index < employeeList.size() -1) {
                index++;
                setTextBoxes(index);         
            }
        }
    }
   
    public class LastEmployee implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent t) {
            index = employeeList.size()-1;
            setTextBoxes(index);
        }
    }
    
    // EventHandler for Update button which allows user to update an existing record
    public class UpdateRecord implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent t) {
            try {
                confirmAlert.setHeaderText("Update Record?");
                confirmAlert.setContentText("Do you want to save changes to this record?");

                Optional<ButtonType> result = confirmAlert.showAndWait();
                if (result.get() == ButtonType.OK){
                    if (txtID.getText().trim().equals("") || txtName.getText().trim().equals("")
                            || txtCity.getText().trim().equals("") || txtPosition.getText().trim().equals("")) {
                        errorAlert.show();
                        setTextBoxes(index);
                        toggleDisabledButtons(false);
                    } else if (!addClicked) {
                        for (Employee employee : employeeList) {                
                            employeeList.get(index).setIdNumber(Integer.parseInt(txtID.getText()));
                            employeeList.get(index).setName(txtName.getText());
                            employeeList.get(index).setCity(txtCity.getText());
                            employeeList.get(index).setPosition(txtPosition.getText());     
                        }
                    } else if (addClicked) {
                        Employee one = new Employee(Integer.parseInt(txtID.getText()));
                        one.setName(txtName.getText());
                        one.setCity(txtCity.getText());
                        one.setPosition(txtPosition.getText());

                        employeeList.add(one);
                        index = employeeList.size()-1;

                        toggleDisabledButtons(false);

                        addClicked = false;                    
                    }   
                } else {
                    setTextBoxes(index);                              
                    toggleDisabledButtons(false);
                }
            } catch (NumberFormatException e) {
                errorAlert.setHeaderText("Invalid Employee ID");
                errorAlert.setContentText("Employee ID must be a positive integer.");
                errorAlert.show();
                setTextBoxes(index);
                toggleDisabledButtons(false);
            }
        }
    }
    
    // EventHandler for Add button which allows user to add an existing record
    public class AddRecord implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent t) {
            txtID.setText(Integer.toString(employeeList.get(employeeList.size()-1).getIdNumber()+1));
            txtName.clear();
            txtCity.clear();
            txtPosition.clear();
            
          confirmAlert.setHeaderText("Add Record?");
            confirmAlert.setContentText("Do you want to add a new record?");
            
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.get() == ButtonType.OK){
                addClicked = true;
                toggleDisabledButtons(true);
            } else {        
                setTextBoxes(index);
            } 
        }
    }
    
    // EventHandler for Delete button which allows user to delete an existing record
    public class DeleteRecord implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent t) {
            confirmAlert.setHeaderText("Delete Record?");
            confirmAlert.setContentText("Do you want to delete this record?");
            
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.get() == ButtonType.OK){       
                employeeList.remove(index);
                
                if (index >= employeeList.size()) 
                    index--;
                    
                setTextBoxes(index);       
            }
        }
    }
    
    /*
     * EventHandler for Search Button which calls SearchStage class object to displays new stage
     * SearchStage stage allows user to search existing records by city or position
     * Record search is not case sensitive and accepts substrings
     */
    public class SearchRecords implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent t) {
            SearchStage searchStage = new SearchStage(employeeList, txtCity.getText(), txtPosition.getText());
            searchStage.show();
        }
    }
    
    // EventHandler for when the program is closed
    public class EndProgram implements EventHandler<WindowEvent> {
        @Override
        public void handle(WindowEvent t) {
            try {
                // Informs user the program ended and data is saved
                EmployeeFile.updateFile(file, employeeList);
                infoAlert.setHeaderText("Program Ended");
                infoAlert.show();
                infoAlert.setContentText("Your data has been saved.");
            } catch (IOException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error: Data Not Saved");
                errorAlert.setContentText(e.toString());
                errorAlert.show();
            }
        }
    }
        
    private void setTextBoxes(int index) {
        txtID.setText(Integer.toString(employeeList.get(index).getIdNumber()));
        txtName.setText(employeeList.get(index).getName());
        txtCity.setText(employeeList.get(index).getCity());
        txtPosition.setText(employeeList.get(index).getPosition());     
    }
    
    private void toggleDisabledButtons(boolean isDisabled) {
        btnFirst.setDisable(isDisabled);
        btnPrevious.setDisable(isDisabled);
        btnNext.setDisable(isDisabled);
        btnLast.setDisable(isDisabled);
        btnAdd.setDisable(isDisabled);
        btnDelete.setDisable(isDisabled);
    }
    
    // Method to create GridPane with Labels and TextFields
    private GridPane getTextGrid() {
        GridPane pane = new GridPane();
        
        pane.add(lblID, 0, 0);
        pane.add(txtID, 1, 0);
        pane.add(lblName, 0, 1);
        pane.add(txtName, 1, 1);
        pane.add(lblCity, 0, 2);
        pane.add(txtCity, 1, 2);
        pane.add(lblPosition, 0, 3);
        pane.add(txtPosition, 1, 3);
        pane.setHgap(5);
        pane.setVgap(10);
        
        return pane;
    }
    
    // Method to create GridPane with basic navigation Buttons
    private GridPane getButtonGrid() {
        GridPane pane = new GridPane();
 
        pane.add(btnFirst, 0, 0);
        pane.add(btnPrevious, 1, 0);
        pane.add(btnNext, 2, 0);
        pane.add(btnLast, 3, 0);
        pane.add(btnUpdate, 0, 1);
        pane.add(btnAdd, 1, 1);
        pane.add(btnDelete, 2, 1);
        pane.add(btnSearch, 3, 1);
        pane.setHgap(15);
        pane.setVgap(10);
        
        // Make Button nodes the same size
        btnFirst.setMaxWidth(Double.MAX_VALUE);
        btnPrevious.setMaxWidth(Double.MAX_VALUE);
        btnNext.setMaxWidth(Double.MAX_VALUE);
        btnLast.setMaxWidth(Double.MAX_VALUE);
        btnUpdate.setMaxWidth(Double.MAX_VALUE);
        btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnDelete.setMaxWidth(Double.MAX_VALUE);
        btnSearch.setMaxWidth(Double.MAX_VALUE);
        
        return pane;
    }
    
    private void setAlerts() {
        // Set alert titles
        errorAlert.setTitle("Error!");
        confirmAlert.setTitle("Confirmation");
        infoAlert.setTitle("Information");
        
        // Style alert boxes
        DialogPane errorDialogPane = errorAlert.getDialogPane();
        errorDialogPane.getStylesheets().add(getClass().getResource("/css/ProjectStyleSheet.css").toExternalForm());
        
        DialogPane confirmDialogPane = confirmAlert.getDialogPane();
        confirmDialogPane.getStylesheets().add(getClass().getResource("/css/ProjectStyleSheet.css").toExternalForm());
        
        DialogPane infoDialogPane = infoAlert.getDialogPane();
        infoDialogPane.getStylesheets().add(getClass().getResource("/css/ProjectStyleSheet.css").toExternalForm());
    }
    
    // Main method checks if file exists and creates new one if it does not
    public static void main(String[] args) {
        try {
            File file = new File("Employee.dat");
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            System.err.println(e);
        }
        
        Application.launch(args);
    }
}