package edu.carleton.expensetracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //test();
        primaryStage.setTitle("Personal Expense Tracker");
        //Scene createScene = createExpenseScene();
        Scene viewScene = viewExpenseScene();
        // set the default window size
        primaryStage.setScene(viewScene);
        primaryStage.show();
    }

    /*
     * This function handles the create expense scene.
     */
    public Scene createExpenseScene(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/create.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Scene(root, 900, 600);
    }

    /*
     * This function handles the view expense scene.
     */
    public Scene viewExpenseScene(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/view.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Scene(root, 900, 600);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
