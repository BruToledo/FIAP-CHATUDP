package fiap.sd.udp.servidor;

import fiap.sd.udp.chat.Commands;
import fiap.sd.udp.chat.Message;
import fiap.sd.udp.chat.Room;
import fiap.sd.udp.chat.User;
import fiap.sd.udp.simplechatudp.receiver.Receiver;

public class ServerReceiver extends Receiver {

	public ServerReceiver(int port) {
		super(port);
	}

	@Override
	protected void treatMessage(String sourceIp, Integer sourcePort,
			Message message) {

		message.setDestinationIp(sourceIp);
		User currentUser = Server.getUserByIp(sourceIp);
		
		String msg = message.getMessage().trim();
		Commands operation = message.getCommand();

		switch (operation) {
		case ACCESS:
			System.out.println("> IP " + sourceIp
					+ " acessando pela primeira vez");
			Server.getAccess(sourceIp);
			message.setCommand(Commands.REQUEST_USER_NAME);
			message.setMessage("CHAT > Digite o seu nome de usuário: ");
			Server.sender.sendMessage(message);
			break;

		case SEND_USER_NAME:
			String userName = msg;
			User user = Server.getUserByName(userName);
			if (user == null) { // usuário não existe
				Server.register(userName, sourceIp);
				message.setCommand(Commands.MENU_APP);
				message.setMessage(
						"CHAT > "
						+ userName + " registrado com sucesso!\n"
						+ Server.menuToString(Server._appMenu));
			} else { // usuário já existe
				message.setCommand(Commands.REQUEST_USER_NAME);
				message.setMessage(
						"CHAT > O usuário "
						+ userName
						+ " já está registrado\nCHAT > Digite o seu nome de usuário: ");
			}

			Server.sender.sendMessage(message);
			break;

		case MENU_APP:			
			int menuAppKey = Integer.parseInt(msg);
			System.out.println("[Menu App] Opção escolhida: " + menuAppKey);
			switch(menuAppKey)
			{
				case 1:
					message.setCommand(Commands.MENU_APP);
					message.setMessage(
							"CHAT > \n" + Server.roomsToString()
							+ "Escolha uma nova opção:\n"
							+ Server.menuToString(Server._appMenu, 1)
							);
					break;
				
				case 2:
					message.setCommand(Commands.REQUEST_CREATE_ROOM);
					message.setMessage(
							"CHAT > Criando uma nova sala...\n"
							);					
					break;
					
				case 3:
					message.setCommand(Commands.REQUEST_JOIN_ROOM);
					message.setMessage(
							"CHAT > Digite o nome da sala que deseja entrar:\n"
							);
					break;
				
				default:
					message.setCommand(Commands.INVALID_COMMAND);
					message.setMessage(
							"CHAT > O menu selecionado é invalido, tente novamente! \n"
							+ Server.menuToString(Server._appMenu));
					break;
			}
			Server.sender.sendMessage(message);
			break;
		
		case SEND_CREATE_ROOM:			
			if (currentUser == null || !currentUser.isRegistred()) {
				message.setCommand(Commands.REQUEST_USER_NAME);
				message.setMessage(
						"CHAT > Usuário não identificado.\n"
						+ "Digite o seu nome de usuário: ");
			}
			else {
				Room newRoom = gson.fromJson(msg, Room.class);
				String result = Server.createRoom(currentUser.getName(), newRoom.getName(), newRoom.getDescription());
				message.setCommand(Commands.MENU_ROOM);
				message.setMessage(
						"CHAT > " + result +
						"CHAT > Bem vindo à sala " + newRoom.getName() + ".\n"+
						"Oque deseja fazer?\n"
						+ Server.menuToString(Server._roomMenu));
			}
			
			Server.sender.sendMessage(message);
			break;
			
		case SEND_JOIN_ROOM:			
			String roomName = msg;
			Room room = Server.getRoomByName(roomName);
			if (room == null)
			{
				message.setCommand(Commands.INVALID_COMMAND);
				message.setMessage(
						"CHAT > Sala não encontrada, tente novamente! \n"
						+ Server.menuToString(Server._appMenu));
			}
			else {
				Server.joinRoom(currentUser.getName(), room.getName());				
				message.setCommand(Commands.MENU_ROOM);
				message.setMessage(
						"CHAT > Bem vindo à sala " + room.getName() + ".\n"+
						"Oque deseja fazer?\n"
						+ Server.menuToString(Server._roomMenu));		
			}			
				
			Server.sender.sendMessage(message);
			break;
			
		case MENU_ROOM:
			Room currentRoom = Server.getRoomByUser(currentUser.getName());
			if (currentRoom != null)
			{				
				int menuRoomKey = Integer.parseInt(msg);
				System.out.println("[Menu Sala] Opção escolhida: " + menuRoomKey);
				switch(menuRoomKey)
				{
					//ver usuários da sala
					case 1:
						message.setCommand(Commands.MENU_ROOM);
						message.setMessage(
								"CHAT > \n" + Server.usersInRoomToString(currentRoom.getName())
								+ "Escolha uma nova opção:\n"
								+ Server.menuToString(Server._roomMenu, 1)
								);
						break;
					
					case 2:
						message.setCommand(Commands.REQUEST_MESSAGE);
						message.setMessage("CHAT > escreva a mensagem \n");
						
						break;	
						
					case 3:
						
						break;
						
					// Sair da Sala
					case 4:
						Server.leftRoom(currentUser.getName(), currentRoom.getName());				
						message.setCommand(Commands.MENU_APP);
						message.setMessage(
								"CHAT > Oque deseja fazer?\n"
								+ Server.menuToString(Server._appMenu));		
						break;	
						
					// Excluir a Sala	
					case 5:
						Server.deleteRoom(currentUser.getName(), currentRoom.getName());				
						message.setCommand(Commands.MENU_APP);
						message.setMessage(
								"CHAT > Oque deseja fazer?\n"
								+ Server.menuToString(Server._appMenu));		
						break;	
						
					default:
						message.setCommand(Commands.INVALID_COMMAND);
						message.setMessage(
								"CHAT > O menu selecionado é invalido, tente novamente! \n"
								+ Server.menuToString(Server._roomMenu));
						break;
				}
				Server.sender.sendMessage(message);
			}
			else				
			{
				message.setCommand(Commands.INVALID_COMMAND);
				message.setMessage(
					"CHAT > Sala não encontrada, tente novamente! \n"
					+ Server.menuToString(Server._appMenu));				
			}
			break;
			
		case SEND_MESSAGE:
			Room cRum = Server.getRoomByUser(currentUser.getName());
			message.setCommand(Commands.MENU_ROOM);
			Server.sendMessage(currentUser.getName(), cRum.getName(), message.getMessage());
			
			
		default:
			message.setCommand(Commands.NOTIFICATION);
			message.setMessage("CHAT > Comando inválido!\n");		
			Server.sender.sendMessage(message);
			break;			

		}
	}

}
