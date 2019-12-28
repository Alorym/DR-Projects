package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.naming.Name;
import javax.sound.midi.ControllerEventListener;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.util.Observable;
import java.util.function.Predicate;

import static javafx.application.Application.launch;

public class Main extends Application {


    public static TableView<Controller> table;
    public static ObservableList<Controller> data = FXCollections.observableArrayList();


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PhoneBook");
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(15);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(70);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(15);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);

        gridPane.getRowConstraints().addAll(row1, row2, row3);
        gridPane.getColumnConstraints().addAll(col1, col2);

        //////TABLE//////
        TableColumn<Controller, String>
                nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Controller, String>
                numberColumn = new TableColumn<>("Number");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("Number"));
        TableColumn<Controller, String>
                notesColumn = new TableColumn<>("Number");
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("Number"));


        table = new TableView<>();
        table.setItems(data);
        table.getColumns().addAll(nameColumn, numberColumn, notesColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        gridPane.add(table, 0, 1, 2, 1);
        //////

        Label phonebook = new Label("PhoneBook");
        phonebook.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
        phonebook.setAlignment(Pos.CENTER);


        TextField search = new TextField("Search");
        search.setPromptText("Enter a search");
        search.setPrefSize(400, 40);

        FilteredList<Controller> filteredData = new FilteredList<>(data, e -> true);
        search.setOnKeyReleased(e -> {
            search.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Controller>) user -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return user.getName().toLowerCase().contains(lowerCaseFilter);
                });
            });
        });

        SortedList<Controller> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);


        Button helpButton = new Button("Help");
        helpButton.setStyle("-fx-base: #6495ED;");

        Button deletebutton = new Button("Delete");
        deletebutton.setPrefSize(80, 30);
        deletebutton.setMinSize(50, 30);
        deletebutton.setStyle("-fx-base: #ff0000;");
        Button addbutton = new Button("Add");
        addbutton.setPrefSize(80, 30);
        addbutton.setStyle("-fx-base: #008000");


        deletebutton.setOnAction(e -> {
            new DeleteStage();
        });

        //StackPane root = new StackPane();
        //root.getChildren().add(helpButton);
        //root.getChildren().add(deletebutton);
        //primaryStage.setScene(new Scene(root,300,250));
        //primaryStage.show();

        Scene scene = new Scene(gridPane, 550, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        HBox helpbox = new HBox(5);
        helpbox.getChildren().add(helpButton);
        helpbox.setAlignment(Pos.CENTER);
        gridPane.add(helpbox, 1, 0);

        VBox vBoxbutton = new VBox(5);
        vBoxbutton.getChildren().addAll(addbutton, deletebutton);
        vBoxbutton.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(search, vBoxbutton);
        hBox.setAlignment(Pos.CENTER);
        gridPane.add(hBox, 0, 2, 2, 2);
        gridPane.setStyle("-fx-background: #D3D3D3;");

        helpButton.setOnAction(j -> {
            new HelpStage();
        });


        addbutton.setOnAction(f -> {
            hBox.setVisible(false);
            Text name = new Text("Name: ");
            name.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 15));
            Text number = new Text("Number: ");
            number.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 15));
            Text notes = new Text("Notes: ");
            notes.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 15));


            TextField nameEntry = new TextField();
            TextField numberEntry = new TextField();
            TextField notesEntry = new TextField();

            Button entrybutton = new Button("Enter");
            entrybutton.setMinSize(80, 30);
            entrybutton.setStyle("-fx-base: #008000;");

            Button cancelbutton = new Button("Cancel");
            cancelbutton.setMinSize(80, 30);
            cancelbutton.setStyle("-fx-base: #ff0000;");

            VBox vBoxx = new VBox(5);
            vBoxx.getChildren().addAll(entrybutton, cancelbutton);

            vBoxx.setAlignment(Pos.CENTER);

            HBox boxx = new HBox(10);
            boxx.getChildren().addAll(name, nameEntry, number, numberEntry, notes, notesEntry);
            boxx.setAlignment(Pos.CENTER);
            boxx.setStyle("-fx-background: #D3D3D3;");
            gridPane.add(boxx, 0, 2, 2, 2);

            entrybutton.setOnAction(E -> {
                boxx.setVisible(false);
                hBox.setVisible(true);

                numberEntry.setText(nameEntry.getText().replaceAll("[^\\d]", ""));

            });


        });
    }




        class DeleteStage extends Stage {
            DeleteStage() {
                table.setItems(data);
                this.setTitle("Delete");
                this.initModality(Modality.APPLICATION_MODAL);

                Text text = new Text("Are You Sure");
                text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
                HBox buttonbox = new HBox(10);
                Button yesButton = new Button("Yes");
                yesButton.setStyle("-fx-base: #008000;");
                yesButton.setMinSize(50, 20);
                Button noButton = new Button("NO");
                noButton.setStyle("-fx-base: #ff0000");
                noButton.setMinSize(50, 20);
                buttonbox.getChildren().addAll(yesButton, noButton);
                buttonbox.setAlignment(Pos.CENTER);

                VBox vBox = new VBox(25);
                vBox.getChildren().addAll(text, buttonbox);
                vBox.setAlignment(Pos.CENTER);

                yesButton.setOnAction(e -> {
                    ObservableList<Controller> entrySelected, allEntrys;
                    allEntrys = table.getItems();
                    entrySelected = table.getSelectionModel().getSelectedItems();
                    entrySelected.forEach(allEntrys::remove);
                    this.close();


                    noButton.setOnAction(r -> {
                        this.close();

                        vBox.setStyle("-fx-background: #D3D3D3;");
                        this.setScene(new Scene(vBox, 150, 150));
                        this.show();
                    });
                });
            }
        }

    }
        class HelpStage extends Stage {
            HelpStage() {
                this.setTitle("Help");
                this.initModality(Modality.APPLICATION_MODAL);

                Label label = new Label("Help");
                label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
                label.setAlignment(Pos.TOP_LEFT);

                Text error = new Text("Note that errors will occur if: ");
                error.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
                error.setFill(Color.RED);

                Text errors = new Text("Invalid Name: \t First and Last names are greater than eight characters."
                        + "\nInvalid Number: \t Phone numbers are not ten digits. \nNull Field: \t\t If fields are left blank.");
                errors.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

                Text instruction = new Text("Instruction: ");
                instruction.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
                instruction.setFill(Color.BLUE);

                Text instructions = new Text("New Entry: \t Click add Button and enter name(last name, first name),number and notes."
                        + "\n Delete Entry: \t Select the Entry you want to delete and click the delete button."
                        + "\n Search: \t\t Type your Query into the Search bar.");
                instruction.setFont(Font.font("Times New Romans"));

                VBox box = new VBox(5);
                box.getChildren().addAll(label, error, errors, instruction, instructions);
                box.setAlignment(Pos.TOP_LEFT);
                box.setPadding(new Insets(2, 5, 5, 5));
                box.setStyle("-fx-background: #D3D3D3;");
                this.setScene(new Scene(box, 580, 180));
                this.show();

            }

    public static void main (String[]args){
                launch(args);

            }
        }







