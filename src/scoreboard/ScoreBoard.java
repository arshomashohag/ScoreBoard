 
package scoreboard;

import javafx.application.Application;
import javafx.stage.Stage;

 
public class ScoreBoard extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        MainWindow mainWindow = new MainWindow();        
        
        mainWindow.StartingWindow(primaryStage);
        primaryStage.setTitle("Score Board");
         
        primaryStage.setMinWidth(500);
         
        primaryStage.setMinHeight(300);
        primaryStage.show();
        
    }

     
    public static void main(String[] args) {
        launch(args);
    }
    
}
