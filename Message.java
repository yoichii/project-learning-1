import java.io.*;

class Message implements Serializable {
    private Type type = Type.none;
    private String username = "";
    private String password = "";
    private Status status = Status.none;
    private Order order = Order.none;
    private String oponentName = "";
    private int[] put = {-1, -1};
    private int[] totalPieces = {-1, -1};

    Type getType() { return type; }
    void setType(Type type) { this.type = type; }

    String getUsername() { return username; }
    void setUsername(String username) { this.username = username; }

    String getPassword() { return password; }
    void setPassword(String password) { this.password = password; }

    Status getStatus() { return status; }
    void setStatus(Status status) { this.status = status; }

    Order getOrder() { return order; }
    void setOrder(Order order) { this.order = order; }

    int[] getPut() { return put.clone(); }
    void setPut(int[] put) { this.put[0] = put[0]; this.put[1] = put[1];}

    int[] getTotalPieces() { return totalPieces.clone(); }
    void setTotalPieces(int[] totalPieces) { this.totalPieces[0] = totalPieces[0]; this.totalPieces[1] = totalPieces[1];}
}
