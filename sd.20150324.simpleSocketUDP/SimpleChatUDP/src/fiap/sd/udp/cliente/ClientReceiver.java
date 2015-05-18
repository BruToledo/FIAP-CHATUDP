package fiap.sd.udp.cliente;

import fiap.sd.udp.chat.Commands;
import fiap.sd.udp.chat.Message;
import fiap.sd.udp.simplechatudp.receiver.Receiver;

public class ClientReceiver extends Receiver {

	public ClientReceiver(int port) {
		super(port);
	}

	@Override
	protected void treatMessage(String sourceIp, Integer sourcePort, Message message) {
				
		String msg = message.getMessage().trim();
		Commands operation = message.getCommand();
		
		switch(operation) {
			case NOTIFICATION:
				System.out.println(msg);
				break;	
			case REQUEST_USER_NAME:
				System.out.println(msg);			
				Client.requestUserInput(Commands.SEND_USER_NAME);
				break;
			case MENU_APP:
			case INVALID_COMMAND:
				System.out.println(msg);			
				Client.requestUserInput(Commands.MENU_APP);			
				break;	
			case REQUEST_CREATE_ROOM:
				System.out.println(msg);			
				Client.requestUserInputForRoomCreation(Commands.SEND_CREATE_ROOM);
				break;
			case REQUEST_JOIN_ROOM:
				System.out.println(msg);			
				Client.requestUserInput(Commands.SEND_JOIN_ROOM);
				break;
			case MENU_ROOM:
				System.out.println(msg);			
				Client.requestUserInput(Commands.MENU_ROOM);			
				break;		
			case REQUEST_MESSAGE:
				System.out.println(msg);
				Client.requestUserInput(Commands.SEND_MESSAGE);
				break;
			case REQUEST_PRIVATE_USER:
				//System.out.println(msg);
				Client.requestUserInput(Commands.SEND_PRIVATE_USER);
			case REQUEST_MESSAGE_PRIVATE:
				System.out.println(msg);
				Client.requestUserInputForPrivateMessage(Commands.SEND_MESSAGE_PRIVATE);
			default:
				break;
		}
	}

}
