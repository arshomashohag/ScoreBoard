 
package scoreboard;
 
public class Players {
    private String name;
    private String role;
    private String type;
    private int plNo;

    public Players(String name, String role, String type, int plNo) {
        this.name = name;
        this.role = role;
        this.type = type;
        this.plNo = plNo;
    }

    public Players( ) {
        this.name = null;
        this.role = null;
        this.type = null;
        this.plNo = -1;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getType() {
        return type;
    }

    public int getPlNo() {
        return plNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPlNo(int plNo) {
        this.plNo = plNo;
    }
    
}
