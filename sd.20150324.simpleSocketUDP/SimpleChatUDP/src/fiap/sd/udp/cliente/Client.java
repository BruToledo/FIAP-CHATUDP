package fiap.sd.udp.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fiap.sd.udp.chat.Commands;
import fiap.sd.udp.chat.Message;
import fiap.sd.udp.chat.Room;

public class Client {

	public static ClientSender sender;
	

	private static final BufferedReader console = new BufferedReader(
			new InputStreamReader(System.in));
	
	public static void requestUserInput(Commands operation) {
		String message = null;
		
		Message msg = new Message();
		message = requestInput();
		
		msg.setCommand(operation);
		msg.setMessage(message);
		
		sender.sendMessage(msg);
	}
	
	public static void requestUserInputForPrivateMessage(Commands operation) {
		String message = null;
		
		Message msg = new Message();
		message = requestInput();
		
		msg.setCommand(operation);
		msg.setDestinationUser(message);
		
		System.out.println("CHAT > escreva a mensagem");
		msg.setMessage(requestInput());
		
		sender.sendMessage(msg);
	}
	
	public static void requestUserInputForRoomCreation(Commands operation) {
		String message = null;
		
		System.out.println("Digite o nome da Sala:");
		String name = requestInput();
		
		System.out.println("Digite a descricao da Sala:");
		String description = requestInput();
				
		Room room = new Room();
		room.setName(name);
		room.setDescription(description);
		message = Room.toJsonString(room);
		
		Message msg = new Message();	
		msg.setCommand(operation);
		msg.setMessage(message);
		sender.sendMessage(msg);
	}

	public static String requestInput() {
		try {
			return console.readLine();
		} catch (IOException e) {
			System.out.println("Erro na entrada do teclado.");
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}
}
