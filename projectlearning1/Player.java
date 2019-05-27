package projectlearning1;
public class Player {

    private String username = "";

    private String password = "";

    private int myColor = 0;

    private int myPoint = 0;
    private int opponentPoint = 0;

    private String opponentname  = "";


    public String getUsername() { return username;}

    public void setUsername(String username) { this.username = username; }



    public String getPassword() { return password;}

    public void setPassword(String password) { this.password = password; }



    public int getMyColor() { return myColor; }
    //1:first,black  2:passive,white
    
    public void setMyColor(int myColor) { this.myColor = myColor; }

    public int getmyPoint() { return myPoint; }
    public void setmyPoint(int myPoint) { this.myPoint = myPoint; }
    
    public String getOpponentname() { return opponentname; }
    public void setOpponentname(String opponentname) { this.opponentname = opponentname; }
    
    public int getOpponentPoint() { return opponentPoint; }
    public void setOpponentPoint(int opponentPoint) { this.opponentPoint = opponentPoint; }
}
