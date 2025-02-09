/*
 * Derek Trauner
 * SPC_ID: 2491481
 * Program to show multiple tables from mySQL Database, also to show min/max for each table for a certain value
 */

package com.example;
import javafx.scene.control.Separator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DataSetGenericFX extends Application {

    DataSetGeneric<danceClass> classes = new DataSetGeneric<>(); // created all lists of objects needed

    public void start(Stage primaryStage) {
        TextArea textArea = new TextArea(); // provided in starter code
        textArea.setWrapText(true);

        Button btLoadShowClassesMDC = new Button("Load/Show Classes MDC");
        Button btLoadShowClassesTMILLY = new Button("Load/Show Classes TMilly");
        Button btLoadShowML = new Button("Load/Show Classes ML");
        Button btLoadShowALLCLASSES = new Button("Load/Show ALL CLASSES");

        HBox hBoxTop = new HBox(btLoadShowClassesMDC, btLoadShowClassesTMILLY, btLoadShowML, btLoadShowALLCLASSES); // provided in starter code
        hBoxTop.setSpacing(10);
        hBoxTop.setAlignment(Pos.CENTER);
        hBoxTop.setPadding(new Insets(5, 5, 5, 5));

        Button btSearchByTimeRange = new Button("Search by Time Range");
        ComboBox<String> cbTimeRangeStart = new ComboBox<>();
        for (int i = 0; i < 48; i++) {
            int hour = (i / 2);
            String minutes = (i % 2 == 0) ? "00" : "30";
            String amPm = (hour < 12) ? "AM" : "PM";
            hour = (hour == 0 || hour == 12) ? 12 : hour % 12;
            cbTimeRangeStart.getItems().add(String.format("%d:%s %s", hour, minutes, amPm));
        }
        cbTimeRangeStart.setMaxWidth(93);
        ComboBox<String> cbTimeRangeEnd = new ComboBox<>();
        for (int i = 0; i < 48; i++) {
            int hour = (i / 2);
            String minutes = (i % 2 == 0) ? "00" : "30";
            String amPm = (hour < 12) ? "AM" : "PM";
            hour = (hour == 0 || hour == 12) ? 12 : hour % 12;
            cbTimeRangeEnd.getItems().add(String.format("%d:%s %s", hour, minutes, amPm));
        }
        cbTimeRangeEnd.setMaxWidth(93);
        Button btClearAnswers = new Button("Clear"); // provided in starter code
        Button btnExit = new Button("Exit");

        HBox hBoxBottom = new HBox(btSearchByTimeRange, cbTimeRangeStart, cbTimeRangeEnd, btClearAnswers, btnExit);
        hBoxBottom.setSpacing(10);
        hBoxBottom.setAlignment(Pos.CENTER);
        hBoxBottom.setPadding(new Insets(5, 5, 5, 5));

        VBox vBoxCenter = new VBox();
        vBoxCenter.setAlignment(Pos.TOP_CENTER);


        ScrollPane scrollPane = new ScrollPane(vBoxCenter);
        scrollPane.setFitToWidth(true); // provided in starter code
        scrollPane.setPrefViewportWidth(Region.USE_COMPUTED_SIZE);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        BorderPane borderPane = new BorderPane(); // provided in starter code
        borderPane.setTop(hBoxTop);
        borderPane.setBottom(hBoxBottom);
        borderPane.setCenter(scrollPane);

        btLoadShowClassesMDC.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if(vBoxCenter.getChildren().size() > 0) vBoxCenter.getChildren().clear();
                String output = "";
                classes = DbConnection.ReadFromDataBaseMDC();
                if (classes != null && classes.size() > 0) {
                    output = "MDC classes loaded - " + classes.size() + "\n";
                    for (danceClass dance : classes) {
                        Button btDanceClass = new Button();
                        btDanceClass.setMaxWidth(Double.MAX_VALUE); // This allows the button to grow
                        btDanceClass.setPrefHeight(42); // Fixed height
                        btDanceClass.setMinHeight(40); // Minimum height
                        btDanceClass.setMaxHeight(42); // Maximum height
                        btDanceClass.setText(String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()));
                        output += String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()) + "\n";
                        vBoxCenter.getChildren().addAll(btDanceClass);
                    }
                } else {
                    output = "No classes available or failure loading classes MDC\n";
                }
                textArea.setText(output);
            }
        });

        btLoadShowClassesTMILLY.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if(vBoxCenter.getChildren().size() > 0) vBoxCenter.getChildren().clear();
                String output = "";
                classes = DbConnection.ReadFromDataBaseTMILLY();
                if (classes != null && classes.size() > 0) {
                    output = "TMilly classes loaded - " + classes.size() + "\n";
                    for (danceClass dance : classes) {
                        Button btDanceClass = new Button();
                        btDanceClass.setMaxWidth(Double.MAX_VALUE); // This allows the button to grow
                        btDanceClass.setPrefHeight(42); // Fixed height
                        btDanceClass.setMinHeight(40); // Minimum height
                        btDanceClass.setMaxHeight(42); // Maximum height
                        btDanceClass.setText(String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()));
                        output += String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()) + "\n";
                        vBoxCenter.getChildren().addAll(btDanceClass);
                    }
                } else {
                    output = "No classes available or failure loading classes TMilly\n";
                }
                textArea.setText(output);
            }
        });

        btLoadShowML.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if(vBoxCenter.getChildren().size() > 0) vBoxCenter.getChildren().clear();
                String output = "";
                classes = DbConnection.ReadFromDataBaseML();
                if (classes != null && classes.size() > 0) {
                    output = "ML classes loaded - " + classes.size() + "\n";
                    for (danceClass dance : classes) {
                        Button btDanceClass = new Button();
                        btDanceClass.setMaxWidth(Double.MAX_VALUE); // This allows the button to grow
                        btDanceClass.setPrefHeight(42); // Fixed height
                        btDanceClass.setMinHeight(40); // Minimum height
                        btDanceClass.setMaxHeight(42); // Maximum height
                        btDanceClass.setText(String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()));
                        output += String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()) + "\n";
                        vBoxCenter.getChildren().addAll(btDanceClass);
                    }
                } else {
                    output = "No classes available or failure loading classes ML\n";
                }
                textArea.setText(output);
            }
        });

        btLoadShowALLCLASSES.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if(vBoxCenter.getChildren().size() > 0) vBoxCenter.getChildren().clear();
                String output = "";
                // Load MDC Classes
                DataSetGeneric<danceClass> mdcClasses = DbConnection.ReadFromDataBaseMDC();
                if (mdcClasses != null && mdcClasses.size() > 0) {
                    output += "MDC classes loaded - " + mdcClasses.size() + "\n";
                    for (danceClass dance : mdcClasses) {
                        Button btDanceClass = new Button();
                        btDanceClass.setMaxWidth(Double.MAX_VALUE); // This allows the button to grow
                        btDanceClass.setPrefHeight(42); // Fixed height
                        btDanceClass.setMinHeight(40); // Minimum height
                        btDanceClass.setMaxHeight(42); // Maximum height
                        btDanceClass.setText(String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()));
                        output += String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()) + "\n";
                        vBoxCenter.getChildren().addAll(btDanceClass);                    }
                } else {
                    output += "No classes available from MDC\n";
                }
                // Load TMilly Classes
                DataSetGeneric<danceClass> tmillyClasses = DbConnection.ReadFromDataBaseTMILLY();
                if (tmillyClasses != null && tmillyClasses.size() > 0) {
                    output += "\nTMilly classes loaded - " + tmillyClasses.size() + "\n";
                    for (danceClass dance : tmillyClasses) {
                        Button btDanceClass = new Button();
                        btDanceClass.setMaxWidth(Double.MAX_VALUE); // This allows the button to grow
                        btDanceClass.setPrefHeight(42); // Fixed height
                        btDanceClass.setMinHeight(40); // Minimum height
                        btDanceClass.setMaxHeight(42); // Maximum height
                        btDanceClass.setText(String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()));
                        output += String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()) + "\n";
                        vBoxCenter.getChildren().addAll(btDanceClass);                    }
                } else {
                    output += "No classes available from TMilly\n";
                }
                // Load ML Classes
                DataSetGeneric<danceClass> mlClasses = DbConnection.ReadFromDataBaseML();
                if (mlClasses != null && mlClasses.size() > 0) {
                    output += "\nML classes loaded - " + mlClasses.size() + "\n";
                    for (danceClass dance : mlClasses) {
                        Button btDanceClass = new Button();
                        btDanceClass.setMaxWidth(Double.MAX_VALUE); // This allows the button to grow
                        btDanceClass.setPrefHeight(42); // Fixed height
                        btDanceClass.setMinHeight(40); // Minimum height
                        btDanceClass.setMaxHeight(42); // Maximum height
                        btDanceClass.setText(String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()));
                        output += String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()) + "\n";
                        vBoxCenter.getChildren().addAll(btDanceClass);                    }
                } else {
                    output += "No classes available from ML\n";
                }
                
                if (output.equals("")) {
                    output = "No classes available from any studio";
                }                
                textArea.setText(output);
            }
        });

        btSearchByTimeRange.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if(vBoxCenter.getChildren().size() > 0) vBoxCenter.getChildren().clear();
                String output = "";
                String timeStart = cbTimeRangeStart.getValue();
                String timeEnd = cbTimeRangeEnd.getValue();
                classes = DbConnection.ReadFromDataBaseTimeRange2(timeStart, timeEnd);
                if (classes != null && classes.size() > 0) {
                    output = "Classes from " + timeStart + " to " + timeEnd + " loaded - " + classes.size() + "\n";
                    for (danceClass dance : classes) {
                        Button btDanceClass = new Button();
                        btDanceClass.setMaxWidth(Double.MAX_VALUE); // This allows the button to grow
                        btDanceClass.setPrefHeight(42); // Fixed height
                        btDanceClass.setMinHeight(40); // Minimum height
                        btDanceClass.setMaxHeight(42); // Maximum height
                        btDanceClass.setText(String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()));
                        output += String.format("%s\t|%s|\n%s\t|%s|", dance.getTime(), dance.getClassName(), dance.getLength(), dance.getInstructor(), dance.getDate()) + "\n";
                        vBoxCenter.getChildren().addAll(btDanceClass);                    }
                } else {
                    output = "Failure loading classes in time range\n";
                }
                textArea.setText(output);
            }
        });

        btClearAnswers.setOnAction(new EventHandler<ActionEvent>() { // provided in starter code
            @Override // Override the handle method
            public void handle(ActionEvent e) {
                vBoxCenter.getChildren().clear();
                System.out.println("cleared");
            }
        });

        btnExit.setOnAction(e -> { // provided in starter code
            DbConnection.closeConnection();
            System.exit(0);
        });

        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 750, 400);
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Window Width: " + newVal);
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Window Height: " + newVal);
        });

        primaryStage.setMinWidth(639);
        primaryStage.setMinHeight(154);
        primaryStage.setTitle("ClassConnect");
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Image myIcon = new Image(getClass().getResourceAsStream("Untitled3.png"));
        primaryStage.getIcons().add(myIcon);
        primaryStage.setScene(scene);
        primaryStage.show();
        DbConnection.getConnection();
    }

    /**
     * The main method is only needed for the IDE with limited JavaFX support. Not
     * needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static void setRoot(String string) {
        throw new UnsupportedOperationException("Unimplemented method 'setRoot'");
    }
}