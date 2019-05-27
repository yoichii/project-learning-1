package projectlearning1;
import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private Type type = Type.none;

    private String username = "";

    private String password = "";

    private Status status = Status.none;

    private Order order = Order.none;
    
    private Result result = Result.none;

    private String opponentname = "";

    private int[] put = {-1, -1};

    private int[] totalPieces = {-1, -1};
    
    private String[][] history;



    public Type getType() { return type; }

    public void setType(Type type) { this.type = type; }



    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }



    public String getOpponentname() { return opponentname; }

    public void setOpponentname(String opponentname) { this.opponentname = opponentname; }


    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }



    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }



    public Order getOrder() { return order; }

    public void setOrder(Order order) { this.order = order; }

    
    public Result getResult() { return result;  }
    public void setResult(Result result) { this.result = result;  }

    public int[] getPut() { return put.clone(); }

    public void setPut(int[] put) { this.put[0] = put[0]; this.put[1] = put[1];}



    public int[] getTotalPieces() { return totalPieces.clone(); }

    public void setTotalPieces(int[] totalPieces) { this.totalPieces[0] = totalPieces[0]; this.totalPieces[1] = totalPieces[1];}

    public String[][] getHistory() { return history; }
    public void setHistory(String[][] history) { this.history  = history; }
}
