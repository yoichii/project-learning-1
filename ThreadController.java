
public class ThreadController {
	
	
	static PlayerThread player=null;
	static Message message1;
	static Message message2;
	

		
	public static void firstplayer(PlayerThread playerthread) {	
		
		
		if(player!=null) {	

			playerthread.opponentThread=ThreadController.threadcontroller(playerthread);

            message1 = new Message();
            message2 = new Message();
			message1.setType(Type.play);
			message2.setType(Type.play);
			message1.setStatus(Status.success);
			message2.setStatus(Status.success);
            message1.setOpponentName("opponent2");
			message1.setOrder(Order.first);
			message2.setOrder(Order.passive);
            message2.setOpponentName("opponent1");
			
			player.sendmessage(message1);
			playerthread.sendmessage(message2);
			
			player.opponentThread=playerthread;
			
			
			player=null;

			
		}else if(player==null) {
			
		player=playerthread;//player is first
		
		}
	}


	public static PlayerThread threadcontroller(PlayerThread playerthread) {
		return player;
	}
	
	

	
	
}
