package fiap.sd.udp.cliente;

import fiap.sd.udp.simplechatudp.sender.Sender;

public class ClientSender extends Sender {
	public static final Integer PORTA_SERVIDOR = 9009;

	public ClientSender(String ipServidor) {
		super(ipServidor, PORTA_SERVIDOR); // Cliente sempre envia para a 9009
	}

}
