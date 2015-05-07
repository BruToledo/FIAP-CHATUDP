package fiap.sd.udp.cliente;

import java.net.UnknownHostException;

import fiap.sd.udp.chat.Commands;
import fiap.sd.udp.chat.Message;

public class InitClient {

	private static Integer PORT_LISTEN = 9010;

	public static void main(String[] args) throws UnknownHostException {
		(new ClientReceiver(PORT_LISTEN)).start();

		initConnection();
	}

	private static void initConnection() {
		System.out.println("> Chat iniciado");
		System.out.print("> Digite o IP do servidor: ");
		String ipServer = Client.requestInput();

		try {
			System.out.println("> Conectando com o servidor...");
			Client.sender = new ClientSender(ipServer);
		} catch (Exception e) {
			System.err.println(">> Erro fatal! Impossível conectar com " + ipServer);
			System.exit(-1);
		}

		Message msg = new Message();
		msg.setMessage("");
		msg.setCommand(Commands.ACCESS);
		Client.sender.sendMessage(msg);
	}

}
