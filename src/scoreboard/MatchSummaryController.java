package scoreboard;
 
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MatchSummaryController {
    
    private  Connection  connection = MatchSummaryConnector.Connector();
    private  PreparedStatement preparedStatement ; 
    private  ResultSet resultSet ;
    private String query;
    private ShowAlert alert = new ShowAlert();
    
     public void CreateTable(String name){
        int i;
         
            query = "CREATE TABLE "+ name +"(Name Varchar(50), PlNo Number(4), Run Number(4), Wecket Number(4),  Over Number(2), Primary key(PlNo))";      
        try{
            
            preparedStatement = connection.prepareStatement(query);
           
            preparedStatement.execute();
             
        }catch(Exception e){
             
             alert.ShowAlertMessage("Unexpected Error Occured Creating The Team!!!");
        }
        }
    
      public ObservableList<PlayersDetails> ShowTable(String name){
      int i;
      ObservableList<PlayersDetails> list = FXCollections.observableArrayList();
      String plnam;
      int plno, run, wecket, over;
      
      
        query = "Select * from " + name;
        
       
        try {             
             preparedStatement = connection.prepareStatement(query);
             resultSet = preparedStatement.executeQuery();
     
             while(resultSet.next()){
                 plnam = resultSet.getString(1);
                 plno = Integer.parseInt(resultSet.getString(2));
                 run = Integer.parseInt(resultSet.getString(3));
                 wecket = Integer.parseInt(resultSet.getString(4));
                 over = Integer.parseInt(resultSet.getString(5));
                 
                list.add(new PlayersDetails(plnam, plno, run, wecket, over));
            }
             return list;
        } catch (Exception e) {
            alert.ShowAlertMessage("Unable To Show Table Content !!");
             return null;
        }
    }
     
     public void InsertIntoTable(String name, PlayersDetails pl){
         
        query = "INSERT INTO "+name+" values(" + "'"+pl.getName()+ "', " + pl.getPlno() +", "+ pl.getRun()+", "+ pl.getWecket() +", "+ pl.getOver()+")";
        
         
            try{
                
                preparedStatement = connection.prepareStatement(query);
                 
                preparedStatement.executeUpdate();
                preparedStatement.close();
                 
            }catch(Exception e){
                 alert.ShowAlertMessage("Insertion Failed!!");
            }
                      
        }
     
    public void UpdateTable(String name, String field, int plno, int val){
        
        query = "UPDATE "+name+" SET "+field+" = " + field + "+" + val + " WHERE PlNo="+plno ;
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            
        } catch (Exception e) {
            alert.ShowAlertMessage("Update Failed !!");
        }
        
    } 
     
}
