package fiap.sd.udp.servidor;

import fiap.sd.udp.simplechatudp.sender.Sender;


public class ServerSender extends Sender {

	public ServerSender() {
		super(9010); // Servidor sempre envia pela 9010
	}
	


}
