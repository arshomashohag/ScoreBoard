 
package scoreboard;
 
public class PlayersDetails {
    private String name;
    private int plno;
    private int run;
    private int wecket;
    private int over;
    public PlayersDetails(String name, int plno, int run, int wecket, int over) {
        this.name = name;
        this.plno = plno;
        this.run = run;
        this.wecket = wecket;
        this.over = over;
        
    }

    public PlayersDetails() {
        this.name = null;
        this.plno = 0;
        this.run = 0;
        this.wecket = 0;
        this.over=0;
    }

    public String getName() {
        return name;
    }

    public int getPlno() {
        return plno;
    }

    public int getRun() {
        return run;
    }

    public int getWecket() {
        return wecket;
    }
    public int getOver(){
        return over;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlno(int plno) {
        this.plno = plno;
    }

    public void setRun(int run) {
        this.run = run;
    }

    public void setWecket(int wecket) {
        this.wecket = wecket;
    }
    
    
    
}
