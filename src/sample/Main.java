package sample;
/**
 * Created by Kristiyan Ayvazov on 04/12/2015.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.image.*;
import javafx.collections.ObservableList;
import java.time.LocalDate;


public class Main extends Application {
    SQL conn = null;
    LocalDate ld;
    Stage window;
    Scene scene, scene2, scene3, scene4, scene5;
    Button cnlBtn, dBtn, aDBtn, aCnlBtn, aBtn, adminBackButton;
    TableView<Shift> table, table2;
    TextField positionInput, dateInput, wageInput, quantityInput;
    String fullEmployeeName="";


    public static void main(String[] args) {
        launch(args);
        }
        public Main(){
            //Connecting to Database
            conn = new SQL();
        }


    @Override
    public void start(Stage primaryStage) throws Exception {
        //Assigning the primary stage
        window = primaryStage;
        window.setTitle("Log IN");
        //Insert company logo
        Image image = new Image("Image.png");
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        iv1.setFitWidth(185);
        iv1.setFitHeight(100);
        GridPane.setConstraints(iv1, 2, 5);
        //GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setStyle("-fx-background: #909090 ;");
        //Employees Login Name Input
        TextField nameInput = new TextField();
        nameInput.setPromptText("Username");
        GridPane.setConstraints(nameInput, 1, 15);

        //Employees Login Password Input
        TextField passInput = new TextField();
        passInput.setPromptText("Password");
        GridPane.setConstraints(passInput, 1, 16);

        //Employees Login button
        Button loginBtn = new Button("Log In");
        GridPane.setConstraints(loginBtn, 2, 16);
        loginBtn.setOnAction(e -> {
            fullEmployeeName= conn.getEmployees( nameInput.getText(),passInput.getText());
            if(fullEmployeeName!="no"){
                window.setScene(scene4);
                nameInput.clear();
                passInput.clear();
            }else{
                //Put the employee alert here
            }
        });

        //Sign Up
        Button signUpBtn = new Button("Sign Up");
        GridPane.setConstraints(signUpBtn, 4, 16);
        signUpBtn.setOnAction(event -> window.setScene(scene2) );
        //Admin
        aBtn = new Button("Admin");
        GridPane.setConstraints(aBtn, 5, 16);
        aBtn.setOnAction(event -> window.setScene(scene3));

        //Add everything to grid
        grid.getChildren().addAll(nameInput,passInput, loginBtn, signUpBtn, aBtn, iv1);

        scene = new Scene(grid, 610, 350);

        //Scene2
        GridPane grid1 = new GridPane();
        grid1.setPadding(new Insets(10, 10, 10, 10));
        grid1.setVgap(8);
        grid1.setHgap(10);
        grid1.setStyle("-fx-background: #909090 ;");
        //Name Input
        TextField uName = new TextField();
        uName.setPromptText("Username");
        GridPane.setConstraints(uName, 5, 1);
        //Password Input
        TextField pass = new TextField();
        pass.setPromptText("Password");
        GridPane.setConstraints(pass, 5, 2);
        //Full Name
        TextField fullname = new TextField();
        fullname.setPromptText("Fullname");
        GridPane.setConstraints(fullname, 5, 3);
        //Sign up button
        dBtn = new Button("Done");
        GridPane.setConstraints(dBtn,5,6);
        dBtn.setOnAction(event1 -> {
            conn.addEmployee(uName.getText(),pass.getText(),fullname.getText());
            uName.clear();
            pass.clear();
            fullname.clear();
        });
        //Cancel button
        cnlBtn = new Button("Cancel");
        GridPane.setConstraints(cnlBtn,6,6);
        cnlBtn.setOnAction(event -> window.setScene(scene));


        grid1.getChildren().addAll(uName,pass, fullname, dBtn, cnlBtn);
        scene2 = new Scene(grid1, 400, 250);

        //GridPane with 10px padding around edge
        GridPane grid2 = new GridPane();
        grid2.setPadding(new Insets(10, 10, 10, 10));
        grid2.setVgap(8);
        grid2.setHgap(10);
        grid2.setStyle("-fx-background: #909090 ;");
        //Admin Name Input
        TextField aRName = new TextField();
        aRName.setPromptText("Username");
        GridPane.setConstraints(aRName, 3, 1);
        //Admin Password Input
        TextField aRPass = new TextField();
        aRPass.setPromptText("Password");
        GridPane.setConstraints(aRPass, 3, 2);
        //Full Name
        TextField aRFName = new TextField();
        aRFName.setPromptText("Fullname");
        GridPane.setConstraints(aRFName, 3, 3);
        //Sign up button
        aDBtn = new Button("Done");
        GridPane.setConstraints(aDBtn,3,6);
        aDBtn.setOnAction(event2 -> {
            conn.addAdmin( aRName.getText(),aRPass.getText(),aRFName.getText());
            aRFName.clear();
            aRName.clear();
            aRPass.clear();
        });
        //Cancel button
        aCnlBtn = new Button("Cancel");
        GridPane.setConstraints(aCnlBtn,10,6);
        aCnlBtn.setOnAction(event -> window.setScene(scene));
        //Admin Name Input
        TextField aNameInput = new TextField();
        aNameInput.setPromptText("Admin");
        GridPane.setConstraints(aNameInput, 10, 1);

        // Admin Password Input
        TextField aPassInput = new TextField();
        aPassInput.setPromptText("Password");
        GridPane.setConstraints(aPassInput, 10, 2);

        // Admin Login
        Button adminLoginButton = new Button("Log In");
        GridPane.setConstraints(adminLoginButton, 10, 3);
        adminLoginButton.setOnAction(event -> {
            if(conn.getAdmins( aNameInput.getText(),aPassInput.getText())){
                window.setScene(scene5);
                aNameInput.clear();
                aPassInput.clear();
            }else{
                //Put the admin alert here
            }
        });
        grid2.getChildren().addAll(aRName, aRPass, aRFName, aDBtn, aCnlBtn, adminLoginButton, aNameInput, aPassInput);
        scene3 = new Scene(grid2, 530, 250);



        //DatePicker
        DatePicker dp = new DatePicker();
        dp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ld = dp.getValue();
                System.out.println("Date selected " + ld);
            }
        });
        //DatePicker2
        DatePicker dp2 = new DatePicker();
        dp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ld = dp2.getValue();
                System.out.println("Date selected " + ld);
            }
        });


        //Position Column
        TableColumn<Shift, String> positionColumn = new TableColumn<>("Position");
        positionColumn.setMinWidth(200);
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        //Date Column
        TableColumn<Shift, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(200);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        //Wage Column
        TableColumn<Shift, Integer> wageColumn = new TableColumn<>("Wage per hour");
        wageColumn.setMinWidth(200);
        wageColumn.setCellValueFactory(new PropertyValueFactory<>("wage"));

        //Quantity Column
        TableColumn<Shift, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        //Position Column
        TableColumn<Shift, String> positionColumn2 = new TableColumn<>("Position");
        positionColumn2.setMinWidth(200);
        positionColumn2.setCellValueFactory(new PropertyValueFactory<>("position"));

        //Date Column
        TableColumn<Shift, String> dateColumn2 = new TableColumn<>("Date");
        dateColumn2.setMinWidth(200);
        dateColumn2.setCellValueFactory(new PropertyValueFactory<>("date"));

        //Wage Column
        TableColumn<Shift, Integer> wageColumn2 = new TableColumn<>("Wage per hour");
        wageColumn2.setMinWidth(200);
        wageColumn2.setCellValueFactory(new PropertyValueFactory<>("wage"));

        //Quantity Column
        TableColumn<Shift, Integer> quantityColumn2 = new TableColumn<>("Quantity");
        quantityColumn2.setMinWidth(100);
        quantityColumn2.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        //Position input
        positionInput = new TextField();
        positionInput.setPromptText("Position");

        //Date input
        dateInput = new TextField();
        dateInput.setPromptText("Date");

        //Wage input
        wageInput = new TextField();
        wageInput.setPromptText("Wage");

        //Quantity input
        quantityInput = new TextField();
        quantityInput.setPromptText("Quantity");


        //Table Buttons
        Button addButton = new Button("Add");
        addButton.setOnAction(event2 -> {addButtonClicked();
        });
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());
        Button employeeBackButton = new Button("Back");
        employeeBackButton.setOnAction(e -> window.setScene(scene));
        adminBackButton = new Button("Back");
        adminBackButton.setOnAction(event -> window.setScene(scene));

        //apply button
        Button applyBtn = new Button("Apply");
        applyBtn.setOnAction(e -> applyButtonClicked());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(positionInput, dateInput, wageInput, quantityInput, addButton, deleteButton);

        HBox hBoxEmplTable = new HBox();
        hBoxEmplTable.setPadding(new Insets(10,10,10,10));
        hBoxEmplTable.setSpacing(10);
        hBoxEmplTable.getChildren().addAll(dp , employeeBackButton, applyBtn);

        HBox hBoxAdminTable = new HBox();
        hBoxAdminTable.setPadding(new Insets(10,10,10,10));
        hBoxAdminTable.setSpacing(10);
        hBoxAdminTable.getChildren().addAll(dp2, adminBackButton);





        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox);

        table = new TableView<>();
        table.setItems(getShift());
        table.getColumns().addAll(positionColumn, dateColumn, wageColumn, quantityColumn);


        table2 = new TableView<>();
        table2.setItems(getShift());
        table2.getColumns().addAll(positionColumn2, dateColumn2, wageColumn2, quantityColumn2);

        //Admin border pane
        BorderPane aBorderPane = new BorderPane();
        aBorderPane.setPadding(new Insets(10, 10, 10, 10));
        aBorderPane.setStyle("-fx-background: #909090 ;");
        aBorderPane.setTop(hBoxAdminTable);
        aBorderPane.setBottom(vBox);
        aBorderPane.setCenter(table2);

        //BackButton
        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> window.setScene(scene));



        employeeBackButton = new Button("Back");
        employeeBackButton.setOnAction(event -> window.setScene(scene));

        scene5 = new Scene(aBorderPane, 950, 600);
        //Employee border pane
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setStyle("-fx-background: #909090 ;");
        borderPane.setTop(hBoxEmplTable);
        borderPane.setCenter(table);

        scene4 = new Scene(borderPane, 950, 600);

        window.setScene(scene);
        window.show();
    }
    //Delete button clicked
    public void deleteButtonClicked() {
        ObservableList<Shift> shiftSelected, allShifts;
        allShifts = table2.getItems();
        shiftSelected = table2.getSelectionModel().getSelectedItems();
        shiftSelected.forEach(allShifts::remove);
        table.getColumns().get(0).setVisible(false);
        table.getColumns().get(0).setVisible(true);
    }
    //Add button clicked
    public void addButtonClicked(){
        Shift shift = new Shift();
        shift.setPosition(positionInput.getText());
        shift.setDate(dateInput.getText());
        shift.setWage(Integer.parseInt(wageInput.getText()));
        shift.setQuantity(Integer.parseInt(quantityInput.getText()));
        table2.getItems().add(shift);
        table.getItems().add(shift);
        positionInput.clear();
        dateInput.clear();
        wageInput.clear();
        quantityInput.clear();
    }

    //apply button clicked
    public void applyButtonClicked(){
            String whatToSend = "";
            try{
                whatToSend=fullEmployeeName+" applied for the position of "+table.getSelectionModel().getSelectedItem().getPosition();
                SendEmailUsingGMailSMTP sendEmail = new SendEmailUsingGMailSMTP(whatToSend);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Application sent sucesfully ");
                alert.setContentText("The admin will be in touch with you soon");

                alert.showAndWait();
            }catch(Exception e){
                System.out.println(e);
            }

    }

    //get all of the shifts
    public ObservableList<Shift> getShift(){
        ObservableList<Shift> shifts = FXCollections.observableArrayList();
        shifts.add(new Shift("Bartender","11 of November", 110, 6));
        shifts.add(new Shift("Dishwaser","15 of November", 100, 4));
        shifts.add(new Shift("Chef","12 of November", 150, 10));
        shifts.add(new Shift("Runner","13 of November", 110, 8));
        return shifts;
    }
}