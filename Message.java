import java.io.Serializable;

class Message implements Serializable {

    private Type type = Type.none;

    private String username = "";

    private String password = "";

    private Status status = Status.none;

    private Order order = Order.none;
    
    private Result result = Result.none;

    private String opponentName = "";

    private int myColor = 0;

    private int[] put = {-1, -1};

    private int[] totalPieces = {-1, -1};
    
    



    Type getType() { return type; }

    void setType(Type type) { this.type = type; }



    String getUsername() { return username; }

    void setUsername(String username) { this.username = username; }



    String getOpponentName() { return opponentName; }

    void setOpponentName(String opponentName) { this.opponentName = opponentName; }


    String getPassword() { return password; }

    void setPassword(String password) { this.password = password; }



    Status getStatus() { return status; }

    void setStatus(Status status) { this.status = status; }



    Order getOrder() { return order; }

    void setOrder(Order order) { this.order = order; }

    
    Result getResult() { return result;  }
    void setResult(Result result) { this.result = result;  }

    int[] getPut() { return put.clone(); }

    void setPut(int[] put) { this.put[0] = put[0]; this.put[1] = put[1];}



    int[] getTotalPieces() { return totalPieces.clone(); }

    void setTotalPieces(int[] totalPieces) { this.totalPieces[0] = totalPieces[0]; this.totalPieces[1] = totalPieces[1];}
    
    int getMyColor() { return this.myColor; }
    void setMyColor(int myColor) { this.myColor = myColor; }

}
