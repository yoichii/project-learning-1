import projectlearning1.*;
public class ThreadController {
	
	
	static PlayerThread playerthread1=null;
	static Message message1;
	static Message message2;
	Player pl1;
	Player pl2;
		
	public static void firstplayer(PlayerThread playerthread) {	
		
		
		if(playerthread1!=null) {	

			playerthread.opponentThread=ThreadController.threadcontroller(playerthread);

            message1 = new Message();
            message2 = new Message();
			message1.setType(Type.play);
			message2.setType(Type.play);
			message1.setStatus(Status.success);
			message2.setStatus(Status.success);
			message1.setOrder(Order.first);
			message2.setOrder(Order.passive);
			message1.setOpponentname(playerthread.player.getUsername());
			message2.setOpponentname(playerthread1.player.getUsername());
			
			playerthread1.sendmessage(message1);
			playerthread.sendmessage(message2);
			
			playerthread1.opponentThread=playerthread;
			playerthread1.player.setOpponentname(playerthread.player.getUsername());
			playerthread.player.setOpponentname(playerthread1.player.getUsername());
			playerthread.player.setMyColor(1);
			playerthread1.player.setMyColor(2);
			
			
			playerthread1=null;

			
		}else if(playerthread1==null) {
			
		playerthread1=playerthread;//player is first
		
		}
	}


	public static PlayerThread threadcontroller(PlayerThread playerthread) {
		return playerthread1;
	}
	
	

	
	
}
