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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import java.time.temporal.ChronoField;
import java.time.format.DateTimeFormatterBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DataSetGenericFX extends Application {

    DataSetGeneric<danceClass> classes = new DataSetGeneric<>();
    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
    .parseCaseInsensitive().appendPattern("EEEE, MMMM d").parseDefaulting(ChronoField.YEAR, 2025).toFormatter();

    // MAYBE EXTRACT DATE FORMATTING TO A METHOD TAKE OUT THE NUMBER FROM EACH DAY TO SORT/MEASURABLE CLASS
    private void showClassesGroupedByDate(DataSetGeneric<danceClass> classes, VBox vBoxCenter, TextArea textArea, String studioName) {
        vBoxCenter.getChildren().clear();
        String output = "";
        if (classes != null && classes.size() > 0) {
            output = studioName + " classes loaded - " + classes.size() + "\n";
            // Group by date using a TreeMap for sorted order
            Map<LocalDate, List<danceClass>> groupedByDate = classes.stream()
                .collect(Collectors.groupingBy(dance -> LocalDate.parse(dance.getDate(), formatter),
                TreeMap::new,Collectors.toList()));
            for (Map.Entry<LocalDate, List<danceClass>> entry : groupedByDate.entrySet()) {
                String dateText = entry.getKey().format(formatter); // format the date for display
                Button dateButton = new Button("Date: " + dateText);
                dateButton.getStyleClass().add("date-button");
                dateButton.setMaxWidth(Double.MAX_VALUE);
                vBoxCenter.getChildren().add(dateButton);
                for (danceClass dance : entry.getValue()) {
                    vBoxCenter.getChildren().add(createDanceClassButton(dance, vBoxCenter));
                }
            }
        } else {
            output = "No classes available or failure loading classes for " + studioName + "\n";
        }
        textArea.setText(output);
    }

    public Button createDanceClassButton(danceClass dance, VBox vBoxCenter) {
        Button btDanceClass = new Button();
        btDanceClass.getStyleClass().add("button-result");
        btDanceClass.setMaxWidth(Double.MAX_VALUE); // This allows the button to grow
        btDanceClass.setPrefHeight(45); // Fixed height
        btDanceClass.setMinHeight(45); // Minimum height
        btDanceClass.setMaxHeight(45); // Maximum height
        VBox buttonContent = new VBox();
        buttonContent.setAlignment(Pos.CENTER);
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER);
        HBox bottomRow = new HBox();
        Label dateLabel = new Label(dance.getDate());
        bottomRow.setAlignment(Pos.CENTER);
        Label timeLabel = new Label(dance.getTime());
        timeLabel.getStyleClass().add("time-label");
        Label classLabel = new Label(" | " + dance.getClassName() + " | ");
        classLabel.getStyleClass().add("class-label");
        Label lengthLabel = new Label(dance.getLength());
        lengthLabel.getStyleClass().add("length-label");
        Label instructorLabel = new Label(" | " + dance.getInstructor() + " | ");
        instructorLabel.getStyleClass().add("instructor-label");
        topRow.getChildren().addAll(timeLabel, classLabel);
        bottomRow.getChildren().addAll(lengthLabel, instructorLabel);
        buttonContent.getChildren().addAll(topRow, bottomRow);
        btDanceClass.setGraphic(buttonContent);
        btDanceClass.setText("");
        return btDanceClass;
    }

    public void start(Stage primaryStage) {
        TextArea textArea = new TextArea(); // provided in starter code
        textArea.setWrapText(true);

        Button btLoadShowClassesMDC = new Button("Load/Show Classes MDC");
        Button btLoadShowClassesTMILLY = new Button("Load/Show Classes TMilly");
        Button btLoadShowML = new Button("Load/Show Classes ML");
        Button btLoadShowALLCLASSES = new Button("Load/Show ALL CLASSES");

        HBox hBoxTop = new HBox(btLoadShowClassesMDC, btLoadShowClassesTMILLY, btLoadShowML, btLoadShowALLCLASSES); // provided in starter code
        hBoxTop.getStyleClass().add("hbox-top");
        hBoxTop.setSpacing(10);
        hBoxTop.setAlignment(Pos.CENTER);
        hBoxTop.setPadding(new Insets(5, 5, 5, 5));

        Button btSearchByTimeRange = new Button("Search by Time Range");
        ComboBox<String> cbTimeRangeStart = new ComboBox<>();
        for (int i = 15; i < 47; i++) {
            int hour = (i / 2);
            String minutes = (i % 2 == 0) ? "00" : "30";
            String amPm = (hour < 12) ? "AM" : "PM";
            hour = (hour == 0 || hour == 12) ? 12 : hour % 12;
            cbTimeRangeStart.getItems().add(String.format("%d:%s %s", hour, minutes, amPm));
        }
        cbTimeRangeStart.setMaxWidth(93);
        ComboBox<String> cbTimeRangeEnd = new ComboBox<>();
        for (int i = 15; i < 47; i++) {
            int hour = (i / 2);
            String minutes = (i % 2 == 0) ? "00" : "30";
            String amPm = (hour < 12) ? "AM" : "PM";
            hour = (hour == 0 || hour == 12) ? 12 : hour % 12;
            cbTimeRangeEnd.getItems().add(String.format("%d:%s %s", hour, minutes, amPm));
        }
        cbTimeRangeEnd.setMaxWidth(93);
        Button btClearAnswers = new Button("Clear"); // provided in starter code
        Button btnExit = new Button("Exit");
        ComboBox<String> cbStudio = new ComboBox<>();
        cbStudio.getItems().addAll("ML", "MDC", "TMILLY");

        HBox hBoxBottom = new HBox(cbStudio, btSearchByTimeRange, cbTimeRangeStart, cbTimeRangeEnd, btClearAnswers, btnExit);
        hBoxBottom.getStyleClass().add("hbox-bottom");
        hBoxBottom.setSpacing(10);
        hBoxBottom.setAlignment(Pos.CENTER);
        hBoxBottom.setPadding(new Insets(5, 5, 5, 5));

        VBox vBoxCenter = new VBox();
        vBoxCenter.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(vBoxCenter);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportWidth(Region.USE_COMPUTED_SIZE);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        //scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        BorderPane borderPane = new BorderPane(); // provided in starter code
        borderPane.setTop(hBoxTop);
        borderPane.setBottom(hBoxBottom);
        borderPane.setCenter(scrollPane);

        btLoadShowClassesMDC.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                classes = DbConnection.ReadFromDataBaseMDC();
                scrollPane.setVvalue(0);
                showClassesGroupedByDate(classes, vBoxCenter, textArea, "MDC");
            }
        });

        btLoadShowClassesTMILLY.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                classes = DbConnection.ReadFromDataBaseTMILLY();
                scrollPane.setVvalue(0);
                showClassesGroupedByDate(classes, vBoxCenter, textArea, "TMILLY");
            }
        });

        btLoadShowML.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                classes = DbConnection.ReadFromDataBaseML();
                scrollPane.setVvalue(0);
                showClassesGroupedByDate(classes, vBoxCenter, textArea, "ML");
            }
        });

        btLoadShowALLCLASSES.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if(vBoxCenter.getChildren().size() > 0) vBoxCenter.getChildren().clear();

                HBox testHBox = new HBox();

                VBox vBoxCenterAllClassesTMilly = new VBox();
                vBoxCenterAllClassesTMilly.setAlignment(Pos.TOP_CENTER);
                ScrollPane scrollPaneTMilly = new ScrollPane(vBoxCenterAllClassesTMilly);
                scrollPaneTMilly.setFitToWidth(true);
                scrollPaneTMilly.setPrefViewportWidth(testHBox.getWidth() / 3.0);
                scrollPaneTMilly.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
                Button test1 = new Button("test1");
                vBoxCenterAllClassesTMilly.getChildren().add(test1);

                VBox vBoxCenterAllClassesMDC = new VBox();
                vBoxCenterAllClassesMDC.setAlignment(Pos.TOP_CENTER);
                ScrollPane scrollPaneMDC = new ScrollPane(vBoxCenterAllClassesMDC);
                scrollPaneMDC.setFitToWidth(true);
                scrollPaneMDC.setPrefViewportWidth(testHBox.getWidth() / 3.0);
                scrollPaneMDC.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
                Button test2 = new Button("test2");
                vBoxCenterAllClassesMDC.getChildren().add(test2);

                VBox vBoxCenterAllClassesML = new VBox();
                vBoxCenterAllClassesML.setAlignment(Pos.TOP_CENTER);
                ScrollPane scrollPaneML = new ScrollPane(vBoxCenterAllClassesML);
                scrollPaneML.setFitToWidth(true);
                scrollPaneML.setPrefViewportWidth(testHBox.getWidth() / 3.0);
                scrollPaneML.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
                Button test3 = new Button("test3");
                vBoxCenterAllClassesML.getChildren().add(test3);

                testHBox.getChildren().addAll(scrollPaneMDC,scrollPaneML,scrollPaneTMilly);
                // HBox.setHgrow(scrollPaneMDC, Priority.ALWAYS);
                // HBox.setHgrow(scrollPaneTMilly, Priority.ALWAYS);
                // HBox.setHgrow(scrollPaneML, Priority.ALWAYS);
                // Stream.of(scrollPaneMDC, scrollPaneTMilly, scrollPaneML).forEach(sp -> HBox.setHgrow(sp, Priority.ALWAYS));
                borderPane.setCenter(testHBox);

                // Load MDC Classes
                DataSetGeneric<danceClass> mdcClasses = DbConnection.ReadFromDataBaseMDC();
                if (mdcClasses != null && mdcClasses.size() > 0) {
                    showClassesGroupedByDate(mdcClasses, vBoxCenterAllClassesMDC, textArea, "MDC");
                } else {
                    System.out.println("No classes available from MDC\n");
                }
                // Load TMilly Classes
                DataSetGeneric<danceClass> tmillyClasses = DbConnection.ReadFromDataBaseTMILLY();
                if (tmillyClasses != null && tmillyClasses.size() > 0) {
                    showClassesGroupedByDate(tmillyClasses, vBoxCenterAllClassesTMilly, textArea, "TMILLY");
                } else {
                    System.out.println("No classes available from TMILLY\n");
                }
                // Load ML Classes
                DataSetGeneric<danceClass> mlClasses = DbConnection.ReadFromDataBaseML();
                if (mlClasses != null && mlClasses.size() > 0) {
                    showClassesGroupedByDate(mlClasses, vBoxCenterAllClassesML, textArea, "ML");
                } else {
                    System.out.println("No classes available from MDC\n");
                }
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
                        vBoxCenter.getChildren().addAll(createDanceClassButton(dance, vBoxCenter));
                    }
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
        Scene scene = new Scene(borderPane, 750, 470);
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