package fiap.sd.udp.servidor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import fiap.sd.udp.chat.Commands;
import fiap.sd.udp.chat.Message;
import fiap.sd.udp.chat.Room;
import fiap.sd.udp.chat.User;

public class Server {
	public static List<Room> rooms;
	public static Set<User> users;
	public static ServerSender sender;	
	public static Map<Integer, String> _appMenu = new HashMap<Integer, String>();
	public static Map<Integer, String> _roomMenu = new HashMap<Integer, String>();
	
	static {
		rooms = new ArrayList<Room>();
		users = new HashSet<User>();
		sender = new ServerSender();
		
		// Menu da Aplicação
		_appMenu.put(1, "Listar Salas");
		_appMenu.put(2, "Criar Sala");
		_appMenu.put(3, "Entrar Sala");
		
		// Menu do Chat, dentro de uma sala
		_roomMenu.put(1, "Ver Usuários da sala");
		_roomMenu.put(2, "Enviar Mensagem");
		_roomMenu.put(3, "Enviar Mensagem Privada");
		_roomMenu.put(4, "Sair da Sala");
		_roomMenu.put(5, "Encerrar/ Excluir a Sala");
	}

	public static String menuToString(Map<Integer, String> menu) {
		String menuToString = "";
		for(Entry<Integer, String> option : menu.entrySet()) {			
		    Integer key = option.getKey();
		    String value = option.getValue();		    
		    menuToString += key + " - " + value + "\n";
		}
		
		return menuToString;
	}
	
	public static String menuToString(Map<Integer, String> menu, int keyToSkip) {
		String menuToString = "";
		for(Entry<Integer, String> option : menu.entrySet()) {			
		    Integer key = option.getKey();
		    String value = option.getValue();
		    
		    if (keyToSkip == key)
		    	continue;
		    
		    menuToString += key + " - " + value + "\n";
		}
		
		return menuToString;
	}
	
	public static Integer getKeyByValue(Map<Integer, String> menu, String value) {
	    for (Entry<Integer, String> entry : menu.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	public static void getAccess(String userIp) {
		User user = new User();
		user.setUserIp(userIp);
		users.add(user);
	}

	public static void register(String userName, String sourceIp) {		
		for (User user : users) {
			if (user.getUserIp().equals(sourceIp)) {
				user.setName(userName);
				user.setRegistred(true);
			}
		}	
	}

	public static Room getRoomByName(String name) {
		for (Room r : rooms) {
			if (r != null && r.getName().equals(name)) {
				return r;
			}
		}
		return null;

	}
	
	public static Room getRoomByUser(String userName) {
		for (Room r : rooms) {
			if (r != null) {				
				for (User user : r.getUsers()) {
					if (user != null && user.getName().equals(userName)) {
						return r;
					}
				}
			}
		}
		return null;
	}

	public static void joinRoom(String userName, String roomName) {
		Room room = getRoomByName(roomName);
		User user = getUserByName(userName);
		room.getUsers().add(user);
			
		for (User u : room.getUsers()) {
			Message mensagem = new Message(
					u.getUserIp(),
					Commands.NOTIFICATION, 
					"CHAT > Usuário " + userName + " entrou na sala.\n");			
			Server.sender.sendMessage(mensagem);
		}
	}

	public static void leftRoom(String userName, String roomName) {
		Room room = getRoomByName(roomName);	
		Iterator<User> iterUser = room.getUsers().iterator();	
		
		while (iterUser.hasNext()) {			
			User usuario = iterUser.next();			
			if (usuario.getName().equals(userName)) {
				iterUser.remove();
			}
			
			Message mensagem = new Message(
					usuario.getUserIp(),
					Commands.NOTIFICATION, 
					"CHAT > Usuário " + userName + " saiu da sala.\n");			
			Server.sender.sendMessage(mensagem);	
		}
	}
	
	public static void deleteRoom(String userName, String roomName) {
		Room room = getRoomByName(roomName);
		User user = getUserByName(userName);
		
		if(room.getOwner().equals(user)){				
			rooms.remove(room);			
			
			Iterator<User> iterUser = room.getUsers().iterator();			
			while (iterUser.hasNext()) {			
				User usuario = iterUser.next();			

				Message mensagem = new Message(
						usuario.getUserIp(),
						Commands.NOTIFICATION, 
						"CHAT > Você foi removido da sala " + room.getName() + ", pois a sala foi deletada pelo administrador.\n");			
				Server.sender.sendMessage(mensagem);	
			}			
		}
		else
		{
			Message mensagem = new Message(
					user.getUserIp(),
					Commands.NOTIFICATION, 
					"CHAT > A sala somente pode ser deletada pelo administrador.\n");			
			Server.sender.sendMessage(mensagem);	
		}		
	}

	public static User getUserByName(String name) {
		for (User user : users) {
			if (user.getName() != null && user.getName().equalsIgnoreCase(name)) {
				return user;
			}
		}
		return null;
	}

	public static String roomsToString() {
		if (rooms.size() <= 0)
			return " --- Não existem salas cadastradas ---\n";
		
		String formatedString = " --- Salas: ---\n";
		for (int i = 0; i < rooms.size(); i++) {
			formatedString +=  "(" + (i + 1) + ") " + rooms.get(i).getName() + "\n";
		}
		formatedString += "--- ------- ----\n";
		return formatedString;
	}

	public static String usersInRoomToString(String roomName) {
		Room room = getRoomByName(roomName);
		String formatedString = " --- Usuários da sala " + roomName + ":  --- \n";
		for (int i = 0; i < room.getUsers().size(); i++) {
			formatedString +=  "(" + (i + 1) + ") " + room.getUsers().get(i).getName() + "\n";
		}
		return formatedString;

	}

	public static String createRoom(String ownerName, String roomName, String roomDescription) {
		User user = getUserByName(ownerName);
		Room newRoom = new Room(roomName, roomDescription, user);
		rooms.add(newRoom);
		newRoom.getUsers().add(user);
		
		return "Sala criada com sucesso\n";
	}

	public static User getUserByIp(String ip) {
		User user = null;

		for (User usuario : users) {
			if (usuario.getUserIp().equals(ip)) {
				user = usuario;
			}
		}

		return user;
	}

	/*
	 * enviarMensagem(usuárioRemetente, nomeDaSala, msg) – envia a mensagem
	 * “msg” para a sala “nomeDaSala”, garantindo o envio a todos os outros
	 * usuários. ◦ enviarMensagemPrivada(usuárioOrigem, usuárioDestino, msg) –
	 * Mensagem privada.
	 */

	public static void sendMessage(String remetente, String nomeSala,
			String msg) {
		
		for (User u : getRoomByName(nomeSala).getUsers()) {
			Message message = new Message();
			message.setDestinationIp(u.getUserIp());
			message.setCommand(Commands.MENU_ROOM);
			message.setMessage("CHAT > Usuário " + remetente + ": " + msg + "\n");			
			Server.sender.sendMessage(message);
		}
	}
	
	public static void sendPrivateMessage(String remetente, String destino, String nomeSala,
			String msg) {
		
		try{
			Room r = getRoomByUser(destino);
			User u = getUserByName(destino);
			Message message = new Message();
			if(r != null){				
				message.setDestinationIp(u.getUserIp());
				message.setCommand(Commands.MENU_ROOM);
				message.setMessage("CHAT > Usuário " + remetente + ": " + msg + "\n");	
			}
			else{
				message.setCommand(Commands.MENU_ROOM);
				message.setMessage("CHAT > Usuário"+ destino+ " não estava na sala \n");			
			}
			Server.sender.sendMessage(message);
		}
		catch(Exception e){
			Message message = new Message();
			message.setCommand(Commands.MENU_ROOM);
			message.setMessage("CHAT > erro ao enviar mensagem \n");	
			Server.sender.sendMessage(message);
		}
		
		
	}
}
