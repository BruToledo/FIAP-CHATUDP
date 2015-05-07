package fiap.sd.udp.cliente;

import fiap.sd.udp.simplechatudp.sender.Sender;

public class ClientSender extends Sender {
	public static final Integer SERVER_PORT = 9009;

	public ClientSender(String ipServer) {
		super(ipServer, SERVER_PORT); // Cliente sempre envia para a 9009
	}

}
