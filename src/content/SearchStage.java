/**
 * Ayah Mahmoud & Arushi Sood
 * 991647463 & 991644338
 * Final Project
 * April 17, 2022
 */
package content;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchStage extends Stage {
    // Builds Labels for Search Stage
    private Label lblIntro = new Label("Search employee records by city and/or position.");
    private Label lblSearchCity = new Label("Search by city: ");
    private Label lblSearchPosition = new Label("Search by position: ");
    
    // Builds TextFields for Search Stage
    private TextField txtSearchCity = new TextField();
    private TextField txtSearchPosition = new TextField();
    
    // Builds Button for Search Stage
    private Button btnSearch = new Button("Search");
    
    // Builds Pane and Scene objects for Search Stage
    private VBox rPane = new VBox(10);
    private VBox pane = new VBox(20, getSearchGrid(), rPane);
    private Scene scene = new Scene(pane, 400, 600);
    
    private ArrayList<Employee> employeeList = new ArrayList();
    
    public SearchStage(ArrayList<Employee> employeeList, String city, String position) {
        this.employeeList = employeeList;
        
        setTitle("Search Employees");
        setScene(scene);
        scene.getStylesheets().add("/css/ProjectStyleSheet.css");
        
        btnSearch.setOnAction(new Search());
    }
    
    // EventHandler for Button for Search Stage
    public class Search implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent t) {       
            if (!txtSearchCity.getText().trim().isEmpty() || !txtSearchPosition.getText().trim().isEmpty()) {
                rPane.getChildren().clear();

                String searchCity = txtSearchCity.getText();
                String searchPosition = txtSearchPosition.getText();

                rPane.getChildren().add(new Label("Search Results: "));
                rPane.setAlignment(Pos.CENTER);
                int numSearches = 0;

                /*
                 * Loops through each Employee object in the employeeList ArrayList and passes
                 * records whose City and/or Position matches text entered in TextFields
                 * to the displayRecord() method
                 */
                for (Employee employee : employeeList) {
                    if (employee.getCity().toLowerCase().contains(searchCity.toLowerCase()) &&
                            employee.getPosition().toLowerCase().contains(searchPosition.toLowerCase())) {
                        displayRecord(employee);
                        numSearches++;
                    }
                }

                rPane.getChildren().add(new Label("Matching employee records found: " + numSearches));
            } else {
                txtSearchCity.requestFocus();
                rPane.getChildren().clear();
            }
        }
    }
    
    // Method that displays Employee record in new Label object
    private void displayRecord(Employee employee) {
        Label label = new Label();
        label.setText("Employee ID:\t" + employee.getIdNumber() + 
                "\nName:\t\t" + employee.getName() +
                "\nCity:\t\t\t" + employee.getCity() +
                "\nPosition:\t\t" + employee.getPosition());
        
        rPane.getChildren().add(label);
    }
    
    // Creates GridPane object to build Search Stage
    private GridPane getSearchGrid() {   
        GridPane pane = new GridPane();
        pane.add(lblIntro, 0, 0, 2, 1);
        pane.add(lblSearchCity, 0, 1);
        pane.add(txtSearchCity, 1, 1);
        pane.add(lblSearchPosition, 0, 2);
        pane.add(txtSearchPosition, 1, 2);
        pane.add(btnSearch, 0, 3);
        pane.setHgap(5);
        pane.setVgap(5);
        pane.setAlignment(Pos.CENTER);
        
        return pane;
    } 
}
