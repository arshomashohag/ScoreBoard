package scoreboard;

 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

 
public class SQLiteController {
    
    private  Connection  connection = SQLiteConnector.Connector();
    private  PreparedStatement preparedStatement ; 
    private  ResultSet resultSet ;
    private String query;
    private ShowAlert alert = new ShowAlert();
    
    public void CreateTable(String name){
        int i;
         
            query = "CREATE TABLE "+ name +"(Name Varchar(50), Role Varchar(40), Type Varchar(40), PlNo Number(4), Primary Key (PlNo))";      
        try{
            
            preparedStatement = connection.prepareStatement(query);
           
            preparedStatement.execute();
             
        }catch(Exception e){
             
             alert.ShowAlertMessage("Unexpected Error Occured Creating The Team!!!");
        }
        }
    
    
    public ObservableList<Players> ShowTable(String name){
      int i;
      ObservableList<Players> list = FXCollections.observableArrayList();
      
        query = "Select * from " + name;
       
        try {             
             preparedStatement = connection.prepareStatement(query);
             resultSet = preparedStatement.executeQuery();
     
             while(resultSet.next()){
                list.add(new Players(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), Integer.parseInt(resultSet.getString(4))));
            }
             return list;
        } catch (Exception e) {
            alert.ShowAlertMessage("Unable To Show Table Content !!");
             return null;
        }
    }
    
    public ObservableList<String> getPlayers(String teamName){
        ObservableList<String> list = FXCollections.observableArrayList();
        
        query = "SELECT * FROM "+teamName;
        
        try{
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                list.add(resultSet.getString(1));
            }
            return list;
        }catch(Exception e){
            alert.ShowAlertMessage("Problems Occured Getting Players !!");
            return null;
        }
        
    }
    
    public ObservableList<String> getTeams(){
        ObservableList<String> list = FXCollections.observableArrayList();
        query = "SELECT * FROM Teams";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                list.add(resultSet.getString(1));
     
            }
            return list;
        } catch (Exception e) {
            alert.ShowAlertMessage("Error occured while getting from tables");
            return null;
        }
        
    }
    
    
    public void InsertIntoTable(String name, Players pl){
         
        query = "INSERT INTO "+name+" values(" + "'"+pl.getName()+ "', " + "'" + pl.getRole() + "', " + "'" + pl.getType() + "', " + pl.getPlNo() + ")";
        
         
            try{
                
                preparedStatement = connection.prepareStatement(query);
                 
                preparedStatement.executeUpdate();
                preparedStatement.close();
                
                query = "UPDATE Teams SET Player = Player+1 WHERE Name = "+"'"+name+"'";
                preparedStatement = connection.prepareStatement(query);
                 
                preparedStatement.executeUpdate();
                preparedStatement.close();
                
                 
            }catch(Exception e){
                 alert.ShowAlertMessage("Insertion Failed");
            }
                      
        }
    
    public void InsertTeams(String name){
        query = "INSERT INTO Teams values('"+name+"', 0)";
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        } catch (Exception e) {
        alert.ShowAlertMessage("Team is not added to database");
        
        }
    }  
        
    
    
    public void DeleteFromTable(String name, int plno){
        
        query = "DELETE FROM "+name+" WHERE PlNo = "+plno;
         
        try {
            preparedStatement = connection.prepareStatement(query);
            
             preparedStatement.executeUpdate();
             preparedStatement.close();
            
        } catch (Exception e) {
            alert.ShowAlertMessage(e.toString());
        }

        
    }
    
    public void DropTable(String name){
        query = "DROP TABLE "+name;
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            alert.ShowAlertMessage("Can't Drop Table !!");
        }
    }

    int numberOfTeam() {
         query = "SELECT COUNT(*) FROM Teams";
         int ret=0;
         try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
             resultSet.next();
              ret = resultSet.getInt(1);
             return ret;
        } catch (Exception e) {
            alert.ShowAlertMessage("Team Counting Error");
            return 0;
        }

         
    }
    
   
}
