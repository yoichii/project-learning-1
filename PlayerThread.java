import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;


class PlayerThread extends Thread{

	  private Socket socket;
	  public ThreadController threadcontroller;
	  public PlayerThread opponentThread;
	  ObjectOutputStream out=null;
      ObjectInputStream in = null;
      Player player = new Player();

      
  
	public PlayerThread(Socket socket,ThreadController threadcontroller) {
		this.socket=socket;
		this.threadcontroller=threadcontroller;
		
	}

	public void run() {
		 try {
	            in = new ObjectInputStream(socket.getInputStream());
    			out = new ObjectOutputStream(socket.getOutputStream());

    	while(true) {
	            Message msg = (Message)(in.readObject());
	            Type type = msg.getType();
		      
		      
		      switch(type) {
		      	case login:
		      		
                    System.out.println("login");
		      		String username;
		      		String password;
		      		
		      		username = msg.getUsername();
		      		password = msg.getPassword();

		      		Message msg2= new Message();

		      		if(username!=null && password!=null) {
		      			

		      			msg2=log(username, password);
		      					      			
		      		
		      		}else if(username==null && password==null) {
		      			
						msg2.setStatus(Status.invalidUsername);

		      			
		      		}else if(username==null) {
		      			
						msg2.setStatus(Status.invalidUsername);

		      			
		      		}else if(password==null) {
		      			
						msg2.setStatus(Status.invalidPassword);
						

		      		}else {
		      			
						msg2.setStatus(Status.unknownError);

		      		}
		      		
	    			sendmessage(msg2);

		      		
		      		break;
		      		
		      	case register:
		      		
		      		String username2;
		      		String password2;
		      		
		      		username2 = msg.getUsername();
		      		password2 = msg.getPassword();
	      			Message msg3= new Message();
		      		msg3.setType(Type.register);
		      		
		      		if(username2!=null && password2!=null) {
		      			msg3=register(username2, password2);
		      			
		      			
		      		}else if(username2==null && password2==null) {
		      			
		      			msg3.setStatus(Status.invalidUsername);

		      		}else if(username2==null) {
		      			
		      			msg3.setStatus(Status.invalidUsername);
		      			
		      		}else if(password2==null) {
		      			
		      			msg3.setStatus(Status.invalidPassword);

		      		}
	    			sendmessage(msg3);

		      		
		      		break;
		      		
		      	case play:
		      		threadcontroller.firstplayer(this);
		      		
		      		break;
		      		
                case analysis:
                    break;

		      	case put:
		      		
		      		Message putmessage = new Message();
		      		//put[]を受け取る
		      		int a[];
		      		
		      		a = msg.getPut();
		      		
                    putmessage.setType(Type.put);
                    putmessage.setStatus(Status.success);
		      		putmessage.setPut(a);
		      		
		      		opponentThread.sendmessage(putmessage);
		      		
		      		//playerThreadのopponentThreadにわたす
		      		
		      		break;
		      		
		      	case finish:
		      				     		
		      		
		      		sendmessage(showResult(msg));
		      		
		      		
		      	case none:
		      		
		      		break;
		    
		      
			default:
				break;
		      
		      }
    	}
		      

		    } catch (IOException e) {
		      e.printStackTrace();
		    } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
		      try {
		        if (socket != null) {
		          socket.close();
		        }
		      } catch (IOException e) {}
		      
		      String a;
		      a = player.getUsername();

		      System.out.println("切断されました "
		                         + socket.getRemoteSocketAddress());

		      if(a!=null)
		        logout();
		    }
		
	}
	
	
 /*login可能かどうかの判断を行う, 結果をstatusでreturn　loginしたとき、その記録をファイルに書き込む*/	
	private Message log(String username, String password) {
		
		int point;
		Message message= new Message();
        message.setType(Type.login);
		
		try(FileReader fr = new FileReader("player.txt"); BufferedReader br = new BufferedReader(fr)){

			String str = br.readLine();
			
			while(str!=null) {
			
				
				if(str.equals(username)) {
					str=br.readLine();
						if(str.equals(password)) {
							
							str=br.readLine();
							point=Integer.parseInt(str);//キャストする
							player.setmyPoint(point);
							
							FileWriter fw = new FileWriter("log.txt",true);
							PrintWriter pw = new PrintWriter(fw);
					        Date date = new Date();
							pw.println(date.toString()+","+username+",login");

							pw.close();
							
							message.setStatus(Status.success);
							message.setUsername(username);
							message.setPassword(password);
							

							player.setPassword(password);
							player.setUsername(username);
							
				
							break;
						}
					
					message.setStatus(Status.invalidPassword);
					
					break;
				}
				
				str = br.readLine();
				str = br.readLine();
				str = br.readLine();
			}

			if(str==null)
			    message.setStatus(Status.invalidUsername);
	
		}catch ( IOException e ) {
			e.printStackTrace();
		}
		
			return message;
	}


	private Message register(String username,String password) {
		
		
		String status = null;
		Message message = new Message();
		System.out.println(username+", "+password);
		
		try(FileReader fr = new FileReader("player.txt"); BufferedReader br = new BufferedReader(fr)){

			String str= br.readLine();
			
			while(str!=null) {
				
				
				if(str.equals(username)) {
					
					message.setStatus(Status.invalidUsername);
					
					break;
				}
				

				str = br.readLine();
				str = br.readLine();
				str = br.readLine();
			}
			
			if(str==null) {
			fr.close();
			
			FileWriter fw = new FileWriter("player.txt",true);
			PrintWriter pw = new PrintWriter(fw);

			pw.println(username);
			pw.println(password);
			pw.println("0");

			pw.close();
			
			System.out.println(username+","+password);
			
			message.setUsername(username);
			message.setStatus(Status.success);
			
			}

			
		}catch ( IOException e ) {
			e.printStackTrace();
		}
		
		
		
		return message;
		
	}
	
	public Message showResult(Message message) {
		
		int totalpieces[];
		int mycolor;
		Message resultmsg = new Message();
		resultmsg.setType(Type.finish);
		int point = player.getmyPoint();
		
		totalpieces = message.getTotalPieces();
		mycolor = player.getMyColor();
		if(((mycolor==0)&&(totalpieces[0]>totalpieces[1]))||((mycolor==1)&&(totalpieces[0]<totalpieces[1]))) {
			
			resultmsg.setResult(Result.win);	

				point = point + 500;

			
		}else if(totalpieces[0]==totalpieces[1]) {
			
			resultmsg.setResult(Result.draw);
			
			point = point + 300;
			
		}else if(((mycolor==0)&&(totalpieces[0]<totalpieces[1]))||((mycolor==1)&&(totalpieces[0]>totalpieces[1]))) {
			
			
			resultmsg.setResult(Result.lose);

			if(point>=10000) {
				point = point - 500;
			}else if(point<10000&&point>=7000) {
				point = point - 500;
			}else if(point<7000&&point>=4000) {
				
			}else if(point<4000&&point>=1000) {
				point = point + 100;
			}else if(point<1000) {
				point = point + 200;
			}
			
			player.setmyPoint(point);
			
		}

		return resultmsg;
		
	}
	
	
	public void sendmessage(Message message){

		try {
			//out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(message);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//send only
	}
	
	public void logout() {
        String username = player.getUsername();

        FileWriter fw;
        try {
                fw = new FileWriter("log.txt",true);
                PrintWriter pw = new PrintWriter(fw);
                Date date = new Date();
                pw.println(date.toString()+","+username+",logout");
                pw.close();
                
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	}
	
	public void analysis() {
		String username;
		int x;
		
		username = player.getUsername();
		
	}
	

	
}


