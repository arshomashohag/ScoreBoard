package scoreboard;

 
 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
 
public class MainWindow {
     private HBox hbox = new HBox();
     private  Button play=new Button("Play");
     private Button editPlayer=new Button("Edit Player");
     private Button editTeam=new Button("Edit Team");
     private BorderPane borderPane = new BorderPane();
     private String battingTeam = new String();
     private String  bowlingTeam = new String();
    
     private TextField nameField = new TextField();
     private TextField typeField = new TextField();
     private TextField roleField = new TextField();
     private TextField plNoField = new TextField();
     
     private int  totalRunI=0, numberOfOver=0, numberOfBall=0, totalwecket=0, bolIn, strIn, nonStrIn;
     private int iningsOver=0, iningsRun[]=new int[5];
     private int runs[][][] = new int[4][20][4];
     private int over[][][] = new int[4][20][4];
     private int inings, temp;
     private String bat[] = new String[20];
     private String ball[] = new String[20];
     
     private String forUse = new String();
     private String StrikeEndBatsman = new String(), NonStrikeEndBatsman=new String(), Bowler=new String();
     private String tempStrikeEndBatsman = new String(), tempNonStrikeEndBatsman=new String();
     
     private Text showStartupMessage = new Text();
     
     private SQLiteController controller = new SQLiteController();
     private MatchSummaryController matcontroller = new MatchSummaryController();
     private ShowAlert Alert = new ShowAlert();
     private Players players;
     private  ObservableList<String> list = FXCollections.observableArrayList();
    
     
    public void StartingWindow(Stage window){
        createStartPage();
        setActions();
         
        Scene scene= new Scene(borderPane, 600, 500);
        
        scene.getStylesheets().addAll(this.getClass().getResource("myStyle.css").toExternalForm());
       
        window.setScene(scene);      
    }
    
   private void createStartPage(){
      showStartupMessage.setText("Set Team Name And Assign Player\nThen Start Playing");
             
            
        hbox.getChildren().addAll(play, editTeam);
         borderPane.setTop(hbox);
         borderPane.setCenter(showStartupMessage);
          borderPane.setId("pane");
        hbox.getStyleClass().add("Hbox");
        showStartupMessage.setFont(new Font("Arial", 25));
        showStartupMessage.setId("font");
        
   }

    private void setActions() {
        
        play.setOnAction(e->{
           
            if(controller.numberOfTeam()<2){
                showStartupMessage.setText("Enter At Least Two Teams First");
                borderPane.setCenter(showStartupMessage);                
            }
            
            else {
                
                PlayGame();
            }
            
        });
               
        editTeam.setOnAction(e->{
            editTeamList();
        });
        
      }

    private void PlayGame(){
        
        borderPane.setLeft(null);
        
         ComboBox<String> selectteam1 = new ComboBox();
         ComboBox<String> selectteam2 = new ComboBox();
         
         
         selectteam1.setPromptText("Select Team 1");
         selectteam2.setPromptText("Select Team 2");
         selectteam1.setItems(controller.getTeams());
         selectteam2.setItems(controller.getTeams());
         
         selectteam1.setMinWidth(150);
         selectteam2.setMinWidth(150);
         
         
         
         Label Vs = new Label("VS");
         
          GridPane gridPane = new GridPane();
          gridPane.setPadding(new Insets(50, 10, 10, 100));
          gridPane.setHgap(10);
          gridPane.setVgap(10);
          
         
          Label Toss = new Label("Toss");
           
          Label overNumber = new Label("Number Of Overs");
          TextField getOver = new TextField();
          Button overOk = new Button("Ok");
          
          Toss.setFont(new Font(24));
          RadioButton team1 = new RadioButton("Team 1");
          team1.setFont(new Font(20));
          ToggleGroup group = new ToggleGroup();
          team1.setToggleGroup(group);
            
          
          RadioButton team2 = new RadioButton("Team 2");
          team2.setFont(new Font(20));
          
          selectteam1.setOnAction(e->{
             team1.setText(selectteam1.getValue());
         });
         selectteam2.setOnAction(e->{
             team2.setText(selectteam2.getValue());
         });
          team2.setToggleGroup(group);
          
          overOk.setOnAction(e->{
              
             inings=0;
              
              if( (team1.isSelected() || team2.isSelected()) && (selectteam1.getValue()!=null && selectteam2.getValue()!=null && !( (selectteam1.getValue()).equalsIgnoreCase(selectteam2.getValue())))){
                  
                  forUse = getOver.getText();
                   iningsOver =  convertToInt(forUse);
                   
              
                   if(iningsOver>0){
                       borderPane.setCenter(null);
                         if(team2.isSelected()){
                             battingTeam = team2.getText();
                             bowlingTeam = team1.getText();
                             
                             GameWindow();
                         }
                         else{
                             bowlingTeam = team2.getText();
                             battingTeam = team1.getText();
                              
                             GameWindow();
                         }
                   }
                   
                }
                
               else{
                   Alert.ShowAlertMessage("Please Select A Team");
               }
          });
            
          gridPane.add(selectteam1,0, 0);
          gridPane.add(Vs,1, 0);
          gridPane.add(selectteam2,2, 0);
          gridPane.add(Toss, 0, 1);
          gridPane.add(team1, 0, 2);
          gridPane.add(team2, 0, 3);
          gridPane.add(overNumber, 0, 4);
          gridPane.add(getOver, 0, 5);
          gridPane.add(overOk, 0, 6);
          
          
          borderPane.setCenter(gridPane);
        
           }
    
    
    private void editTeamList() {
        
        borderPane.setLeft(null);
        borderPane.setCenter(null);
        ListView<String> listView = new ListView();
       
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(2.5, 2.5, 2.5, 2.5));
        vbox.setSpacing(2.5);
        
        TextField name = new TextField();
        name.setPromptText("Team Name");
        name.setMinWidth(30);
        Button add = new Button("Add");
        
       
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(2.5, 2.5, 2.5, 2.5));
        hbox.setSpacing(10);
        
        
        hbox.getChildren().addAll(name, add);
        
        vbox.getChildren().addAll(listView, hbox);
        
        list = controller.getTeams();
        
        listView.setItems(list);
        
         add.setOnAction(new EventHandler<ActionEvent>() {
             
            public void handle(ActionEvent e) {
                addTeams(name.getText());
                name.clear();
            }
        });
         
        listView.setOnMouseClicked(e->{
        
        listItemSelected(listView.getSelectionModel().getSelectedItem());    
        });
        borderPane.setLeft(vbox);
    }
 
    private void GameWindow(){
        
          int i;
          inings ++;
          
          if(inings>2)
          {
              ShowResult();
              return ;
          }
          totalRunI=0;
          numberOfOver=0;
          numberOfBall=0;
          totalwecket=0;
          bolIn=-1;
          strIn=-1;
          nonStrIn=-1;
          ObservableList<String> list = FXCollections.observableArrayList();
         
       
        GridPane gamePane = new GridPane();
        VBox  selection = new VBox();
        VBox mainScene = new VBox();
        
        Label iningsNumber = new Label();
        if(inings==1){
            iningsNumber.setText("First Inings");
        }
        else{
            iningsNumber.setText("Second Inings");
        }
        selection.setPadding(new Insets(2.5));
        selection.setSpacing(5.5);
        
        gamePane.setHgap(5.5);
        gamePane.setVgap(5.5);
        gamePane.setPadding(new Insets(30, 30, 30, 30));
        
        Label selectBatsman = new Label("Select Batsman");
        Label selectBowler = new Label("Select Bowler");
        
        ComboBox<String> getBatsman = new ComboBox();
        getBatsman.setPromptText("On Strike");
         list = controller.getPlayers(battingTeam);
         i=0;
         for(String st:list){
             bat[i++]=st;
         }
        getBatsman.setItems(list);
        
        ComboBox<String> getNonStrikeBat = new ComboBox();
        getNonStrikeBat.setPromptText("Non Strike");
        
        getNonStrikeBat.setItems(list);
        
         ComboBox<String> getBowler = new ComboBox();
         getBowler.setPromptText("Select Bowler");
         list = controller.getPlayers(bowlingTeam);
          i=0;
         for(String st:list){
             ball[i++]=st;
         }
         getBowler.setItems(list);
        
        
        Button sceneOk = new Button("Ok");
        
       
        Label StrikeEnd = new Label("Player 1 :-");
        Label NonStrikeEnd = new Label("Player 2 :-");
        Label bowlerL = new Label("Bowler :- ");
        Label overCount = new Label("0");
        
        getBatsman.setOnAction(e->{ 
            if(StrikeEndBatsman.isEmpty()){
                
                if(NonStrikeEndBatsman.isEmpty() || !NonStrikeEndBatsman.equals(getBatsman.getValue()))
                {
                    
                StrikeEndBatsman = getBatsman.getValue(); 
                  
                strIn = GetBatIn(StrikeEndBatsman);
               
                StrikeEnd.setText(StrikeEndBatsman);
                }
            }
        });
        getNonStrikeBat.setOnAction(e->{
             if(NonStrikeEndBatsman.isEmpty()) {
                if(StrikeEndBatsman.isEmpty() || !StrikeEndBatsman.equals(getNonStrikeBat.getValue()))
                {
                 NonStrikeEndBatsman = getNonStrikeBat.getValue();
                nonStrIn = GetBatIn(NonStrikeEndBatsman);
                NonStrikeEnd.setText(NonStrikeEndBatsman);
                }
            }
        });
        
        getBowler.setOnAction(e->{ 
            
            if(Bowler.isEmpty()){
                bowlerL.setText(getBowler.getValue()+" :- ");
                Bowler = getBowler.getValue();
                bolIn = getBallIndex();
            }
        });
        
        Label strkrun = new Label("0");
        Label nonstrkrun = new Label("0");
        
        Label Status = new Label("Status");
        
        
        Label totalRun = new Label("Total Run :- ");
        Label run = new Label("0");
        Label fallOfWecket = new Label("Wicket :- ");
        Label noOfWecket = new Label("0");
         
        Label overl = new Label("Over :- ");
        Label overc = new Label("0");
        
         
        ToggleGroup nRuns = new ToggleGroup();
        ToggleGroup biRuns = new ToggleGroup();
        ToggleGroup wecket = new ToggleGroup();
        RadioButton zero = new RadioButton("0");
        RadioButton one = new RadioButton("1");
        RadioButton two = new RadioButton("2");
        RadioButton three = new RadioButton("3");
        RadioButton four = new RadioButton("4");
        RadioButton six = new RadioButton("6");
        zero.setToggleGroup(nRuns);
        one.setToggleGroup(nRuns);
        two.setToggleGroup(nRuns);
        three.setToggleGroup(nRuns);
        four.setToggleGroup(nRuns);
        six.setToggleGroup(nRuns);
         
        RadioButton strwecket = new RadioButton("Striker Out");
        RadioButton nonstrwecket = new RadioButton("Non Striker Out");
        RadioButton wide = new RadioButton("Wide");
        RadioButton no = new RadioButton("No");
        RadioButton dead = new RadioButton("Dead");
        
        wide.setToggleGroup(biRuns);
        no.setToggleGroup(biRuns);
        dead.setToggleGroup(biRuns);
        strwecket.setToggleGroup(wecket);
        nonstrwecket.setToggleGroup(wecket);
        
        
        selection.getChildren().addAll(selectBatsman, getBatsman, getNonStrikeBat, selectBowler, getBowler);
         
       
        gamePane.add(totalRun, 0, 0);
        gamePane.add(run, 1, 0);
        gamePane.add(fallOfWecket, 2, 0);
        gamePane.add(noOfWecket, 3, 0);
        
        gamePane.add(StrikeEnd, 0, 1);
        gamePane.add(strkrun, 1, 1);
        gamePane.add(bowlerL, 2, 1);
        gamePane.add(overCount, 3, 1);
        gamePane.add(NonStrikeEnd, 0, 2);
        gamePane.add(nonstrkrun, 1, 2);
        gamePane.add(overl, 2, 2);
        gamePane.add(overc, 3, 2);
        gamePane.add(Status, 0, 3);
        
        
        gamePane.add(zero, 0, 4);
        gamePane.add(one, 1, 4);
        gamePane.add(two, 0, 5);
        gamePane.add(three, 1, 5);
        gamePane.add(four, 0, 6);
        gamePane.add(six, 1, 6);
        gamePane.add(strwecket, 0, 7);
        
        gamePane.add(nonstrwecket, 1, 7);
        gamePane.add(wide, 2, 7);
        gamePane.add(no, 0, 8);
        gamePane.add(dead, 1, 8);
        gamePane.add(sceneOk, 0, 9);
        
         sceneOk.setOnAction(e->{
            if(StrikeEndBatsman.isEmpty() || NonStrikeEndBatsman.isEmpty() || Bowler.isEmpty()){
                Alert.ShowAlertMessage("Bowler Or Batsman not set !!");
            }
            else{
            if(zero.isSelected()){
                 
                 if(!strwecket.isSelected() && !nonstrwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                     numberOfBall+=1;
                     runs[inings][strIn][1]+=1;
                 }
                 else if(strwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                    totalwecket +=1 ; 
                    StrikeEndBatsman="";
                    numberOfBall+=1;
                    runs[inings][strIn][1]+=1;
                     over[inings][bolIn][1]+=1;
                }
                else if(nonstrwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                     totalwecket +=1 ; 
                     NonStrikeEndBatsman="";
                     numberOfBall+=1;
                     runs[inings][strIn][1]+=1;
                      over[inings][bolIn][1]+=1;
                }
                else if(wide.isSelected() && !nonstrwecket.isSelected() && !strwecket.isSelected()){
                    totalRunI+=1;
                    
                }
                else if(no.isSelected() && !nonstrwecket.isSelected() && !strwecket.isSelected()){
                    totalRunI+=1;
                    
                }
               
            }
            
             else if(one.isSelected()){
                      if(!strwecket.isSelected() && !nonstrwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                     numberOfBall+=1;
                     totalRunI +=1;
                     System.out.println(strIn);
                     runs[inings][strIn][0]+=1;
                     runs[inings][strIn][1]+=1;
                     temp=strIn; 
                     strIn=nonStrIn;
                     nonStrIn=temp;
                     
                 }
                 else if(strwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                    totalwecket +=1 ; 
                    totalRunI+=1;
                    StrikeEndBatsman="";
                      numberOfBall+=1;
                     runs[inings][strIn][0]+=1;
                     runs[inings][strIn][1]+=1;
                     temp=strIn; 
                     strIn=nonStrIn;
                     nonStrIn=temp;
                      over[inings][bolIn][1]+=1;
                }
                else if(nonstrwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                      
                    totalwecket +=1 ; 
                    totalRunI+=1;
                    NonStrikeEndBatsman="";
                    numberOfBall+=1;
                     runs[inings][strIn][0]+=1;
                     runs[inings][strIn][1]+=1;
                     temp=strIn; 
                     strIn=nonStrIn;
                     nonStrIn=temp;
                      over[inings][bolIn][1]+=1;
                }
                else if(wide.isSelected() && !nonstrwecket.isSelected() && !strwecket.isSelected()){ 
                    totalRunI+=2;
                    temp=strIn; 
                    strIn=nonStrIn;
                    nonStrIn=temp;
                }
                else if(no.isSelected() && !nonstrwecket.isSelected() && !strwecket.isSelected()){ 
                    totalRunI+=2;
                    temp=strIn; 
                    strIn=nonStrIn;
                    nonStrIn=temp;
                    
                }
               
           }
             else if(two.isSelected()){
                 
                  if(!strwecket.isSelected() && !nonstrwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                     numberOfBall+=1;
                     totalRunI +=2;
                     
                     runs[inings][strIn][0]+=2;
                     runs[inings][strIn][1]+=1;
                                         
                 }
                 else if(strwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                    totalwecket +=1 ; 
                    totalRunI+=2;
                    StrikeEndBatsman="";
                    numberOfBall+=1;
                    runs[inings][strIn][0]+=2;
                    runs[inings][strIn][1]+=1; 
                     over[inings][bolIn][1]+=1;
                }
                else if(nonstrwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                      
                    totalwecket +=1 ; 
                    totalRunI+=2;
                    NonStrikeEndBatsman="";
                    numberOfBall+=1;
                     runs[inings][strIn][0]+=2;
                     runs[inings][strIn][1]+=1;
                      over[inings][bolIn][1]+=1;
                      
                }
                else if(wide.isSelected() && !nonstrwecket.isSelected() && !strwecket.isSelected()){ 
                    totalRunI+=3;
                     
                }
                else if(no.isSelected() && !nonstrwecket.isSelected() && !strwecket.isSelected()){ 
                    totalRunI+=3;
                }
                 
             }
             else if(three.isSelected()){
                        if(!strwecket.isSelected() && !nonstrwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                     numberOfBall+=1;
                     totalRunI +=3;
                     
                     runs[inings][strIn][0]+=3;
                     runs[inings][strIn][1]+=1;
                     temp=strIn; 
                     strIn=nonStrIn;
                     nonStrIn=temp;
                     
                 }
                 else if(strwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                    totalwecket +=1 ; 
                    totalRunI+=3;
                    StrikeEndBatsman="";
                      numberOfBall+=1;
                     runs[inings][strIn][0]+=3;
                     runs[inings][strIn][1]+=1;
                     temp=strIn; 
                     strIn=nonStrIn;
                     nonStrIn=temp;
                      over[inings][bolIn][1]+=1;
                }
                else if(nonstrwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                      
                    totalwecket +=1; 
                    totalRunI+=3;
                    NonStrikeEndBatsman="";
                    numberOfBall+=1;
                     runs[inings][strIn][0]+=3;
                     runs[inings][strIn][1]+=1;
                     temp=strIn; 
                     strIn=nonStrIn;
                     nonStrIn=temp;
                      over[inings][bolIn][1]+=1;
                }
                else if(wide.isSelected() && !nonstrwecket.isSelected() && !strwecket.isSelected()){ 
                    totalRunI+=4;
                    temp=strIn; 
                    strIn=nonStrIn;
                    nonStrIn=temp;
                    
                }
                else if(no.isSelected() && !nonstrwecket.isSelected() && !strwecket.isSelected()){ 
                    totalRunI+=4;
                    temp=strIn; 
                    strIn=nonStrIn;
                    nonStrIn=temp;
                    
                }
            
             }
            else if(four.isSelected()){
                if(!strwecket.isSelected() && !nonstrwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                    totalRunI+=4;
                    
                    numberOfBall+=1;
                    
                     runs[inings][strIn][0]+=4;
                     runs[inings][strIn][1]+=1;
                }
                else if(!strwecket.isSelected() && !nonstrwecket.isSelected() && (wide.isSelected() || no.isSelected()) && !dead.isSelected()){
                    totalRunI+=5;
                    if(no.isSelected()){
                         runs[inings][strIn][0]+=4;   
                    }
                }
            }
            
            else if(six.isSelected()){
                 if(!strwecket.isSelected() && !nonstrwecket.isSelected() && !wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                    totalRunI+=6;
                    
                    numberOfBall+=1;
                    
                     runs[inings][strIn][0]+=6;
                     runs[inings][strIn][1]+=1;
                }
                else if(!strwecket.isSelected() && !nonstrwecket.isSelected() && (wide.isSelected() || no.isSelected()) && !dead.isSelected()){
                    totalRunI+=7;
                    if(no.isSelected()){
                         runs[inings][strIn][0]+=6;   
                    }
                }
            }
            
            else if(strwecket.isSelected()){
                if(!wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                    totalwecket +=1 ; 
                    StrikeEndBatsman="";
                    numberOfBall+=1;
                    runs[inings][strIn][1]+=1;
                    over[inings][bolIn][1]+=1;
                }
                 
            }
            else if(nonstrwecket.isSelected()){
                if(!wide.isSelected() && !no.isSelected() && !dead.isSelected()){
                    totalwecket +=1 ; 
                    NonStrikeEndBatsman="";
                    numberOfBall+=1;
                    runs[inings][strIn][1]+=1;
                     over[inings][bolIn][1]+=1;
                }
            }
            
            else if(wide.isSelected() || no.isSelected()){
                totalRunI+=1;
            }
            
            
            if(numberOfBall==6)
            {
                numberOfBall=0;
                numberOfOver+=1;
                over[inings][bolIn][0]+=1;
                temp=strIn;
                strIn = nonStrIn;
                nonStrIn=temp;
                Bowler="";
            }
            
            run.setText(Integer.toString(totalRunI));
            noOfWecket.setText(Integer.toString(totalwecket));
            overc.setText(Integer.toString(numberOfOver)+"."+Integer.toString(numberOfBall));
            overCount.setText(Integer.toString(over[inings][bolIn][0])+"."+Integer.toString(numberOfBall));
            
            if(numberOfOver==iningsOver ){
                iningsRun[inings-1]=totalRunI;
                String tempS = battingTeam;
                battingTeam=bowlingTeam;
                bowlingTeam=tempS;
                StrikeEndBatsman="";
                NonStrikeEndBatsman="";
                GameWindow(); 
                return;
            }
            
            tempStrikeEndBatsman=bat[strIn];
            tempNonStrikeEndBatsman=bat[nonStrIn];
            
             if(!StrikeEndBatsman.isEmpty()){
                 StrikeEndBatsman=tempStrikeEndBatsman;
                 StrikeEnd.setText(StrikeEndBatsman);
                 strkrun.setText(Integer.toString(runs[inings][strIn][0]));
             }
             else{
                 StrikeEnd.setText("*");
                 strkrun.setText("0"); 
             }
             if(!NonStrikeEndBatsman.isEmpty()){
                 NonStrikeEndBatsman=tempNonStrikeEndBatsman;
                 NonStrikeEnd.setText(NonStrikeEndBatsman);
                 nonstrkrun.setText(Integer.toString(runs[inings][nonStrIn][0]));
             }
             else{
                  NonStrikeEnd.setText("*");
                 nonstrkrun.setText("0"); 
             }
         }
            zero.setSelected(false);
            one.setSelected(false);
            two.setSelected(false);
            three.setSelected(false);
            four.setSelected(false);
            six.setSelected(false);
            strwecket.setSelected(false);
            nonstrwecket.setSelected(false);
            wide.setSelected(false);
            no.setSelected(false);
            dead.setSelected(false);
          
            
        });
        
        mainScene.getChildren().addAll(iningsNumber, gamePane);
        mainScene.setPadding(new Insets(2.5, 2.5, 2.5, 2.5));
        mainScene.setSpacing(10);
        borderPane.setCenter(mainScene);
        borderPane.setLeft(selection);
        
    }

    private int convertToInt(String number) {
    
         int i, l = number.length();
         int ret=0;
         if(l>2)
         {
             Alert.ShowAlertMessage("Number Is Too Big");
             return 0;
         }
         else if(l<1){
             Alert.ShowAlertMessage("Enter A Number");
             return 0;
         }
        for(i=0; i<l; i++){
            if(number.charAt(i)<'0' || number.charAt(i)>'9'){
                Alert.ShowAlertMessage("Not A Valid Number");
                return 0;
            }
            ret+=ret*10+(number.charAt(i)-'0');
        }
        
        return ret;
    }

    private void listItemSelected(String name) {
     VBox vbox = new VBox();
    TableView<Players> table = new TableView(); 
             
       
      int pln;
      Players player = new  Players();
       
        HBox hbox = new HBox();
        
        Button add = new Button("Add"), delete = new Button("Delete");
        
        
       Label label  = new Label("( * ) marked fields are needed !!");
       
        nameField.setPromptText("Name*");
        
      
        typeField.setPromptText("Type");
        
        
        roleField.setPromptText("Role");
        
        plNoField.setPromptText("Player's No*");
        
         
        
        
        TableColumn<Players, String> nameColumn = new TableColumn<>("Player's Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Players, String>("name"));
         
        TableColumn<Players, String> typeColumn = new TableColumn<>("Player's Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<Players, String>("type"));
        
        TableColumn<Players, String> roleColumn = new TableColumn<>("Player's Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<Players, String>("role"));
         
        TableColumn<Players, Integer> plNoColumn = new TableColumn<>("Player's Number");
        plNoColumn.setCellValueFactory(new PropertyValueFactory<Players, Integer>("plNo"));
         
      
        table.setItems(controller.ShowTable(name));
        
        
        table.getColumns().addAll(nameColumn, roleColumn, typeColumn, plNoColumn);
        
        
          add.setOnAction(e->{
            
              if( ! (nameField.getText()).isEmpty()  && !(plNoField.getText()).isEmpty() && Check(plNoField.getText()))
              {
                  
                String plname = nameField.getText();
               String type = typeField.getText();
               String role = roleField.getText();
               int plno = Integer.parseInt(plNoField.getText());
               table.getItems().add(new Players(plname, role, type, plno));
               nameField.clear();
               typeField.clear();
               roleField.clear();
               plNoField.clear();
                
                OnAddition(name, new Players(plname, role, type,  plno));
              
                 
              }
              else{
               Alert.ShowAlertMessage("Invalid Input");
                }
            
        });
        
        delete.setOnAction(e->{
             ObservableList<Players> allItems, selectedItems;
       
           allItems = table.getItems();
            selectedItems = table.getSelectionModel().getSelectedItems();
           
          
            
             players = table.getSelectionModel().getSelectedItem();
             if(players!=null)
             {
                 OnDeletion(name, players.getPlNo());
                 selectedItems.forEach(allItems::remove);
             }
             
        });
        
        hbox.getChildren().addAll(nameField, typeField, roleField, plNoField, add, delete);
        hbox.setPadding(new Insets(2.5, 2.5, 2.5, 2.5));
        hbox.setSpacing(10);
      
          
        vbox.getChildren().addAll(table, hbox, label);
        borderPane.setCenter(vbox);
    
    }

    private void OnAddition(String name, Players p) {
     
        controller.InsertIntoTable(name, p);
    }

    private void OnDeletion(String name, int plno) {
         controller.DeleteFromTable(name, plno);
    }

    private boolean Check(String text) {
         int i, l=text.length();
         for(i=0; i<l; i++){
             if(text.charAt(i)<'0' || text.charAt(i)>'9')
                 return false;
         }
         return true;
    }

    
    private void addTeams(String text) {
    boolean flag=true;
    
    if(!text.isEmpty())
    {
        for(String s:list){
        if(s.equalsIgnoreCase(text))
        {
            flag=false;
            break;
        }
    }
     if(flag){
         list.add(text);
         controller.CreateTable(text);
         controller.InsertTeams(text);
     }
     else{
         Alert.ShowAlertMessage("Team Already Exists !!");
     }
    }
  }
    private void ShowResult() {
        if(iningsRun[0]<iningsRun[1]){
            Alert.ShowAlertMessage(battingTeam+" Wins");
        }
        else if(iningsRun[0]>iningsRun[1]){
             Alert.ShowAlertMessage(bowlingTeam+" Wins");
        }
        else{
             Alert.ShowAlertMessage("It's A Tie");
        }
    }

    private int getBallIndex() {
         int i=0;
         
         for(String st: ball){
             if(st.equalsIgnoreCase(Bowler))
                 return i;
             i++;
         }
         return 0;
    }

    private int GetBatIn(String bt) {
         int i=0;
        for(String st: bat){
             if(st.equalsIgnoreCase(bt))
                 return i;
             i++;
         }
        return 0;
    }
    
}
