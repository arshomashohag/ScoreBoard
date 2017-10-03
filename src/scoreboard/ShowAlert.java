 
package scoreboard;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

 
public class ShowAlert {
    public void ShowAlertMessage(String message){
     Stage alertStage = new Stage();
    alertStage.setResizable(true);
    Button ok=new Button("Ok");
     
    ok.setOnAction(e->{
        alertStage.close();
    });
    alertStage.initModality(Modality.APPLICATION_MODAL);
    
    VBox alertBox = new VBox();
    Label show = new Label(message);
    
    
    alertBox.getChildren().addAll(show, ok);
    alertBox.setPadding(new Insets(30));
    alertBox.setSpacing(10);
    Scene alertScene = new Scene(alertBox, 200, 100);
    alertStage.setScene(alertScene);
    alertStage.show();
    
    }
    
}
