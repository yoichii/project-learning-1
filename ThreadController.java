
public class ThreadController {
	
	
	static PlayerThread player=null;
	static Message message1;
	static Message message2;
	

		
	public static void firstplayer(PlayerThread playerthread) {	
		
		
		if(playerthread!=null) {	

			playerthread.opponentThread=ThreadController.threadcontroller(playerthread);

			message1.setType(Type.play);
			message2.setType(Type.play);
			message1.setOrder(Order.first);
			message2.setOrder(Order.passive);
			
			player.sendmessage(message1);
			playerthread.sendmessage(message2);
			
			player.opponentThread=playerthread;
			
			
			player=null;

			
		}else if(playerthread==null) {
			
		player=playerthread;//player is first
		
		}
	}


	public static PlayerThread threadcontroller(PlayerThread playerthread) {
		return player;
	}
	
	

	
	
}
