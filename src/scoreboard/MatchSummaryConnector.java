 
package scoreboard;

import java.sql.Connection;
import java.sql.DriverManager;

 
public class MatchSummaryConnector {
    public static Connection Connector(){
   
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:MatchSummary.sqlite");
            return con;
        } catch (Exception e) {
            return null;
        }
}
}
