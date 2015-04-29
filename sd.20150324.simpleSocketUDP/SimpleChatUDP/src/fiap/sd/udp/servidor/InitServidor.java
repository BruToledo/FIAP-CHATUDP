package fiap.sd.udp.servidor;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InitServidor {
	private static final Integer PORT = 9009;

	public static void main(String[] args) {

		String hostServidor = "";
		try {
			hostServidor = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.err
					.println(">> ERRO N�O-FATAL: N�o foi poss�vel identificar o IP do Servidor.");
		}

		System.out.println("> Iniciando servidor." + hostServidor);

		(new ServerReceiver(PORT)).start();
		
		System.out.println("> Servidor iniciado com sucesso!");

	}
}
