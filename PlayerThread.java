import java.io.*;
import java.net.*;
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
		      		
		      		String username;
		      		String password;
		      		
		      		username = msg.getUsername();
		      		password = msg.getPassword();

		      		Message msg2 = new Message();
                    msg2.setType(Type.login);

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
	      			Message msg3 = new Message();
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
                    analysis();
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

		      if(a!="")
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
				
				
				if(str.equals(username)) {;
					
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

        	File history = new File(username+".txt");
			history.createNewFile();

			pw.println(username);
			pw.println(password);
			pw.println("0");

			pw.close();
			
			System.out.println(username+","+password);
			
            message.setType(Type.register);
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
		String username = player.getUsername();
		String opponentname = player.getOpponentname();
		
	
		try {
			FileWriter fw;
			fw = new FileWriter(username+".txt",true);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println(opponentname);

			totalpieces = message.getTotalPieces();
			mycolor = player.getMyColor();
			if(((mycolor==1)&&(totalpieces[0]>totalpieces[1]))||((mycolor==2)&&(totalpieces[0]<totalpieces[1]))) {
			
				resultmsg.setResult(Result.win);	
				pw.println("勝ち");
				
				if(mycolor==1) {
					pw.println("先手");
					pw.println(totalpieces[0]+"-"+totalpieces[1]);
				}else{
					pw.println("後手");
					pw.println(totalpieces[1]+"-"+totalpieces[0]);
				}
				
				point = point + 500;

			
			}else if(totalpieces[0]==totalpieces[1]) {
			
				resultmsg.setResult(Result.draw);
				pw.println("引き分け");
				
				if(mycolor==1) {
					pw.println("先手");
					
				}else {
					
					pw.println("後手");
					
				}
				
				pw.println(totalpieces[0]+"-"+totalpieces[1]);
				
				point = point + 300;
			
			}else if(((mycolor==1)&&(totalpieces[0]<totalpieces[1]))||((mycolor==2)&&(totalpieces[0]>totalpieces[1]))) {
			
			
				resultmsg.setResult(Result.lose);
				pw.println("負け");
				
				
				if(mycolor==1) {
					pw.println("先手");
					pw.println(totalpieces[0]+"-"+totalpieces[1]);
				}else {
					pw.println("後手");
					pw.println(totalpieces[1]+"-"+totalpieces[0]);
				}
				
/*			if(point>=10000) {
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
			pointRewrite(point);
*/			
		}
			
			
		pw.close();
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		Message analysis = new Message();
		analysis.setType(Type.analysis);
		String username = player.getUsername();
		String his[][] = null;
		String str = null;
		int lineNum=0;
		int i=0,j=0;
		
		
		try {
			
			FileReader fr1 = new FileReader(username + ".txt");
			BufferedReader br1 = new BufferedReader(fr1);
			while(null!=(str = br1.readLine())) {
				lineNum++;
			}
			
			lineNum = lineNum/4;
			his = new String[lineNum][4];
			
			br1.close();
			fr1.close();
		
			FileReader fr2 = new FileReader(username + ".txt");
			BufferedReader br2 = new BufferedReader(fr2);
			
			while(null!=(str = br2.readLine())) {
				his[i][j]=str;
				if(j==3) {
					j=0;
                    i++;
                    continue;
				}
				j++;				
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		analysis.setHistory(his);
        analysis.setStatus(Status.success);
		
		sendmessage(analysis);
		
		
		
	}
	
	public void pointRewrite(int point) {
		
		try(FileReader fr = new FileReader("player.txt"); BufferedReader br = new BufferedReader(fr)){

			String str = br.readLine();
			
			while(str!=null) {
			
				
				if(str.equals(player.getUsername())) {
					str=br.readLine();
					str=br.readLine();
					
					}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}


