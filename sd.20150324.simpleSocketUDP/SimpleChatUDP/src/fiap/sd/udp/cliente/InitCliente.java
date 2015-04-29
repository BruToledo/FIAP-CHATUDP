package fiap.sd.udp.cliente;

import java.net.UnknownHostException;

import fiap.sd.udp.chat.Comandos;
import fiap.sd.udp.chat.Mensagem;

public class InitCliente {

	private static Integer PORT_LISTEN = 9010;

	public static void main(String[] args) throws UnknownHostException {
		(new ClientReceiver(PORT_LISTEN)).start();

		iniciarConexao();
	}

	private static void iniciarConexao() {
		System.out.println("> Chat iniciado");
		System.out.print("> Digite o IP do servidor: ");
		String ipServidor = Cliente.solicitarInput();

		try {
			System.out.println("> Conectando com o servidor...");
			Cliente.sender = new ClientSender(ipServidor);
		} catch (Exception e) {
			System.err.println(">> Erro fatal! Imposs�vel conectar com " + ipServidor);
			System.exit(-1);
		}

		Mensagem msg = new Mensagem();
		msg.setMensagem("");
		msg.setComando(Comandos.ACESSAR);
		Cliente.sender.enviarMensagem(msg);
	}

}
