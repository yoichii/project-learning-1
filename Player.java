class Player {

    private String username = "";

    private String password = "";

    private int myColor = 0;

    private int myPoint = 0;


    String getUsername() { return username;}

    void setUsername(String username) { this.username = username; }



    String getPassword() { return password;}

    void setPassword(String password) { this.password = password; }



    int getMyColor() { return myColor; }
    //1:first,black  2:passive,white
    
    void setMyColor(int myColor) { this.myColor = myColor; }

    int getmyPoint() { return myPoint; }
    void setmyPoint(int myPoint) { this.myPoint = myPoint; }
    
    
}
