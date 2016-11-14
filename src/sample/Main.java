package sample;
/**
 * Created by Kristiyan Ayvazov on 04/12/2015.
 */
//imports
import javafx.application.Application;
import javafx.collections.FXCollections;
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
import java.util.ArrayList;

public class Main extends Application {
    //attributes
    SQL conn = null;
    LocalDate ld;
    Stage window;
    Scene mainScene, employeeSignUPScene, adminRegistrationScene, employeeScene, adminScene;
    Button cancelButton, submitButton, adminSubmitButton, adminCancelButton, adminButton, adminBackButton;
    TableView<Shift> employeeTable, adminTable;
    TextField positionInput, dateInput, wageInput, quantityInput;
    String fullEmployeeName="";
    // launching args in order to start GUI
    public static void main(String[] args) {
        launch(args);
        }
        //constructor that connects to database
        public Main(){
            //Connecting to Database
            conn = new SQL();
        }
    //overriden method where we put GUI elements
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Assigning the primary stage
        window = primaryStage;
        window.setTitle("KriBeKo");
        //Insert company logo
        Image image = new Image("Image.png");
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        iv1.setFitWidth(155);
        iv1.setFitHeight(180);
        GridPane.setConstraints(iv1, 2, 5);
        //GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        //Employees Login Name Input
        TextField nameInput = new TextField();
        nameInput.setPromptText("Username");
        GridPane.setConstraints(nameInput, 1, 15);
        //Employees Login Password Input
        PasswordField passInput = new PasswordField();
        passInput.setPromptText("Password");
        GridPane.setConstraints(passInput, 1, 16);

        //Employees Login button which uses the password/account check from SQL class. Lambda expression used.
        Button loginBtn = new Button("Log In");
        GridPane.setConstraints(loginBtn, 2, 16);
        loginBtn.setOnAction(e -> {
           fullEmployeeName = conn.getEmployees( nameInput.getText(),passInput.getText());
            if(!fullEmployeeName.equals("no")){
                window.setScene(employeeScene);
                //clears nameinput and passinput fields after we click the login button
                nameInput.clear();
                passInput.clear();
            }else{
                AlertBox.display("Error!", "Username already exist!");
            }
        });

        //Sign Up button
        Button signUpBtn = new Button("Sign Up");
        //gridpane where signupbtn is placed
        GridPane.setConstraints(signUpBtn, 4, 16);
        signUpBtn.setOnAction(event -> window.setScene(employeeSignUPScene) );
        //Admin button
        adminButton = new Button("Admin");
        GridPane.setConstraints(adminButton, 5, 16);
        adminButton.setOnAction(event -> window.setScene(adminRegistrationScene));

        //Add buttons(childrens) to grid(parent)
        grid.getChildren().addAll(nameInput,passInput, loginBtn, signUpBtn, adminButton, iv1);

        mainScene = new Scene(grid, 580, 400);

        //Scene2
        GridPane grid1 = new GridPane();
        grid1.setPadding(new Insets(10, 10, 10, 10));
        grid1.setVgap(8);
        grid1.setHgap(10);
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
        submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton,5,6);
        submitButton.setOnAction(event1 -> {
            conn.addEmployee(uName.getText(),pass.getText(),fullname.getText());
            uName.clear();
            pass.clear();
            fullname.clear();
        });
        //Cancel button
        cancelButton = new Button("Cancel");
        GridPane.setConstraints(cancelButton,6,6);
        cancelButton.setOnAction(event -> window.setScene(mainScene));


        grid1.getChildren().addAll(uName,pass, fullname, submitButton, cancelButton);
        employeeSignUPScene = new Scene(grid1, 400, 250);

        //GridPane with 10px padding around edge
        GridPane grid2 = new GridPane();
        grid2.setPadding(new Insets(10, 10, 10, 10));
        grid2.setVgap(8);
        grid2.setHgap(10);
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
        adminSubmitButton = new Button("Submit");
        GridPane.setConstraints(adminSubmitButton,3,6);
        adminSubmitButton.setOnAction(event2 -> {
            conn.addAdmin( aRName.getText(),aRPass.getText(),aRFName.getText());
            aRFName.clear();
            aRName.clear();
            aRPass.clear();
        });
        //Cancel button
        adminCancelButton = new Button("Cancel");
        GridPane.setConstraints(adminCancelButton,10,6);
        adminCancelButton.setOnAction(event -> window.setScene(mainScene));
        //Admin Name Input
        TextField aNameInput = new TextField();
        aNameInput.setPromptText("Admin");
        GridPane.setConstraints(aNameInput, 10, 1);

        // Admin Password Input
        PasswordField aPassInput = new PasswordField();
        aPassInput.setPromptText("Password");
        GridPane.setConstraints(aPassInput, 10, 2);

        // Admin Login
        Button adminLoginButton = new Button("Log In");
        GridPane.setConstraints(adminLoginButton, 10, 3);
        adminLoginButton.setOnAction(event -> {
            if(conn.getAdmins( aNameInput.getText(),aPassInput.getText())){
                window.setScene(adminScene);
                aNameInput.clear();
                aPassInput.clear();
            }else{
                AlertBox.display("Error!", "Username already exist!");
            }
        });
        grid2.getChildren().addAll(aRName, aRPass, aRFName, adminSubmitButton, adminCancelButton, adminLoginButton, aNameInput, aPassInput);
        adminRegistrationScene = new Scene(grid2, 530, 250);



        //DatePicker
        DatePicker dp = new DatePicker();
        dp.setOnAction(event -> {
            ld = dp.getValue();
            System.out.println("Date selected " + ld);
        });
        //DatePicker2
        DatePicker dp2 = new DatePicker();
        dp.setOnAction(event -> {
            ld = dp2.getValue();
            System.out.println("Date selected " + ld);
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
        TableColumn<Shift, Integer> quantityColumn = new TableColumn<>("Hours");
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
        TableColumn<Shift, Integer> quantityColumn2 = new TableColumn<>("Hours");
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
        addButton.setOnAction(e -> addButtonClicked());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());
        Button employeeBackButton = new Button("Back");
        employeeBackButton.setOnAction(e -> window.setScene(mainScene));
        adminBackButton = new Button("Back");
        adminBackButton.setOnAction(event -> window.setScene(mainScene));
        //apply button
        Button applyBtn = new Button("Apply");
        applyBtn.setOnAction(e -> applyButtonClicked());
        //Horizontal Box
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

        //Vertical Box
        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox);
        //TableViews
        employeeTable = new TableView<>();
        employeeTable.setItems(getShift());
        employeeTable.getColumns().addAll(positionColumn, dateColumn, wageColumn, quantityColumn);
        adminTable = new TableView<>();
        adminTable.setItems(getShift());
        adminTable.getColumns().addAll(positionColumn2, dateColumn2, wageColumn2, quantityColumn2);

        //Admin border pane
        BorderPane aBorderPane = new BorderPane();
        aBorderPane.setPadding(new Insets(10, 10, 10, 10));
        aBorderPane.setTop(hBoxAdminTable);
        aBorderPane.setBottom(vBox);
        aBorderPane.setCenter(adminTable);

        //BackButton
        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> window.setScene(mainScene));
        employeeBackButton = new Button("Back");
        employeeBackButton.setOnAction(event -> window.setScene(mainScene));

        adminScene = new Scene(aBorderPane, 950, 600);
        //Employee border pane
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setTop(hBoxEmplTable);
        borderPane.setCenter(employeeTable);
        employeeScene = new Scene(borderPane, 950, 600);
        window.setScene(mainScene);
        window.show();
    }
    //Delete button clicked: method that is connected to database and deletes selected row from it.
    public void deleteButtonClicked() {
        ObservableList<Shift> shiftSelected, shiftSelected1, allShifts, allShifts1;
        String p = adminTable.getSelectionModel().getSelectedItem().getPosition();
        String d = adminTable.getSelectionModel().getSelectedItem().getDate();
        int w = adminTable.getSelectionModel().getSelectedItem().getWage();
        int q = adminTable.getSelectionModel().getSelectedItem().getQuantity();
        System.out.println(p);
        SQL sql = new SQL();
        sql.deleteFromDB(p, d ,w ,q);
        allShifts = employeeTable.getItems();
        allShifts1 = adminTable.getItems();
        shiftSelected = adminTable.getSelectionModel().getSelectedItems();
        shiftSelected1 = adminTable.getSelectionModel().getSelectedItems();
        shiftSelected.forEach(allShifts::remove);
        shiftSelected1.forEach(allShifts1::remove);
        //TableView refresh workaround
        employeeTable.getColumns().get(0).setVisible(false);
        employeeTable.getColumns().get(0).setVisible(true);
        adminTable.getColumns().get(0).setVisible(false);
        adminTable.getColumns().get(0).setVisible(true);
    }
    //Add button clicked: method connected to database that inserts information to it
    public void addButtonClicked(){
        Shift shift = new Shift("", "", 1, 1);
        shift.setPosition(positionInput.getText());
        shift.setDate(dateInput.getText());
        shift.setWage(Integer.parseInt(wageInput.getText()));
        shift.setQuantity(Integer.parseInt(quantityInput.getText()));
        conn.addShift(positionInput.getText(), dateInput.getText(), wageInput.getText(), quantityInput.getText());
        adminTable.getItems().add(shift);
        employeeTable.getItems().add(shift);
        positionInput.clear();
        dateInput.clear();
        wageInput.clear();
        quantityInput.clear();
    }

    //method: apply button clicked. The method sends an email to the admin/manager that employee has applied for job position.
    public void applyButtonClicked(){
            String whatToSend;
            try{
                whatToSend=fullEmployeeName+" applied for the position of "+employeeTable.getSelectionModel().getSelectedItem().getPosition();
                SendEmailUsingGMailSMTP sendEmail = new SendEmailUsingGMailSMTP(whatToSend);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Application sent sucesfully ");
                alert.setContentText("The admin will be in touch with you soon");
                alert.showAndWait();
                deleteButtonClicked();
            }catch(Exception e){
                System.out.println(e);
            }
    }

    //get all of the shifts from getshifts arraylist in SQL class
    public ObservableList<Shift> getShift() {
        ObservableList<Shift> shifts = FXCollections.observableArrayList();
        ArrayList<String> shiftsList;
        shiftsList=conn.getShifts();
        for(int i=0;i<shiftsList.size();i++){
            String[] s= shiftsList.get(i).split("qwqw");
            shifts.add(new Shift(s[1], s[2],Integer.parseInt(s[3]),Integer.parseInt(s[4])));
        }
        return shifts;
    }
}
