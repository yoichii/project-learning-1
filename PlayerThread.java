

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

	  Player player;
  
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
                            
                            String username;
                            String password;
                            String status;
                            
                            username = msg.getUsername();
                            password = msg.getPassword();
                            Message msg2= new Message();		
                            
                            if(username!=null && password!=null) {
                                

                                msg2=log(username, password);
                                                        
                            
                            }else if(username==null && password==null) {
                                
                            //	status = "invalidUsername and invalidPassword";
                                msg2.setStatus(Status.invalidUsername);

                                
                            }else if(username==null) {
                                
                            //	status = "invalidUsername";
                                msg2.setStatus(Status.invalidUsername);

                                
                            }else if(password==null) {
                                
                            //	status = "invalidPassword";
                                msg2.setStatus(Status.invalidPassword);
                                

                            }else {
                                
                                msg2.setStatus(Status.unknownError);

                                status = "unknownError";
                            }
                            
                            sendmessage(msg2);

                            
                            break;
                            
                        case register:
                            
                            String username2;
                            String password2;
                            String status2;
                            
                            username2 = msg.getUsername();
                            password2 = msg.getPassword();
                            Message msg3= new Message();
                            
                            if(username2!=null && password2!=null) {
                                msg3=register(username2, password2);
                                
                                
                            }else if(username2==null && password2==null) {
                                
                                status2 = "invalidUsername and invalidPassword";
                            //	networkOut.write(status2); ;
                                
                            }else if(username2==null) {
                                
                                status2 = "invalidUsername";
                            //	networkOut.write(status2); ;
                                
                            }else if(password2==null) {
                                
                                status2 = "invalidPassword";
                            //	networkOut.write(status2);

                            }else {
                                status2 = "unknownError";
                            //	networkOut.write(status2);
                            }
                            
                            sendmessage(msg3);

                            
                            break;
                            
                        case play:
                            threadcontroller.firstplayer(this);
                            
                            break;
                            
                        case put:
                            
                            Message putmessage = new Message();
                            //put[]を受け取る
                            int a[];
                            a = msg.getPut();
                            
                            putmessage.setPut(a);
                            
                            opponentThread.sendmessage(putmessage);
                            
                            //playerThreadのopponentThreadにわたす
                            
                            break;
                            
                            
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
		      System.out.println("切断されました "
		                         + socket.getRemoteSocketAddress());
		    }
		
	}
	
	
 /*login可能かどうかの判断を行う, 結果をstatusでreturn　loginしたとき、その記録をファイルに書き込む*/	
private Message log(String username, String password) {
		
	
		Message message= new Message();
		String status = null;

		try(FileReader fr = new FileReader("player.txt"); BufferedReader br = new BufferedReader(fr)){

            String str = br.readLine();				

			while(str!=null) {
				if(str.equals(username)) {
					str=br.readLine();

						if(str.equals(password)) {
							
							FileWriter fw = new FileWriter("log.txt",true);
							PrintWriter pw = new PrintWriter(fw);
					        Date date = new Date();
							pw.println(date.toString()+","+username+",login");

							pw.close();
							
							message.setStatus(Status.success);
							message.setUsername(username);
							message.setPassword(password);
							//status="success";							
							break;
						}
					
					message.setStatus(Status.invalidPassword);
					
				//	status="invalidPassword";
					break;
				}
				
				str = br.readLine();
				str = br.readLine();
			}
			
            if(str == null)
			    message.setStatus(Status.invalidUsername);
			//status="invalidUsername";
			
	
			
			
		}catch ( IOException e ) {
			e.printStackTrace();
		}
		
			return message;
	}

	
	private Message register(String username,String password) {
		
		String status = null;
		Message message = new Message();
		try(FileReader fr = new FileReader("player.txt"); BufferedReader br = new BufferedReader(fr)){

			String str="1";
			
			while(str!=null) {

				str = br.readLine();				
				
				if(str.equals(username)) {
					
//					status="invalidUsername";
					message.setStatus(Status.invalidUsername);
					
					break;
				}
				

				str = br.readLine();
			}
			
			fr.close();
			
			FileWriter fw = new FileWriter("player.txt",true);
			PrintWriter pw = new PrintWriter(fw);

			pw.println(username);
			pw.println(password);

			pw.close();
			
			message.setUsername(username);
			message.setUsername(username);
			
			//status="success";
			message.setStatus(Status.success);

			
		}catch ( IOException e ) {
			e.printStackTrace();
		}
		
		
		
		return message;
		
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
	
}

