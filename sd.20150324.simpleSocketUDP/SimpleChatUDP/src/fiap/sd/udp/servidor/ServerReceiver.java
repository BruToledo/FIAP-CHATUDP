package fiap.sd.udp.servidor;

import fiap.sd.udp.chat.Comandos;
import fiap.sd.udp.chat.Mensagem;
import fiap.sd.udp.chat.Usuario;
import fiap.sd.udp.simplechatudp.receiver.Receiver;

public class ServerReceiver extends Receiver {

	public ServerReceiver(int port) {
		super(port);
	}

	@Override
	protected void trataMensagem(String ipOrigem, Integer portaOrigem,
			Mensagem mensagem) {

		mensagem.setIpDestino(ipOrigem);
		
		String msg = mensagem.getMensagem().trim();
		Comandos operacao = mensagem.getComando();

		switch (operacao) {
		case ACESSAR:
			System.out.println("> IP " + ipOrigem
					+ " acessando pela primeira vez");
			Servidor.acessar(ipOrigem);
			mensagem.setComando(Comandos.REQUISITAR_NOME_USUARIO);
			mensagem.setMensagem("CHAT > Digite o seu nome de usu�rio: ");
			Servidor.sender.enviarMensagem(mensagem);
			break;

		case ENVIAR_NOME_USUARIO:
			String nomeUsuario = msg;
			Usuario user = Servidor.obterUsuarioPorNome(nomeUsuario);
			if (user == null) { // usu�rio n�o existe
				Servidor.registrar(nomeUsuario);
				mensagem.setComando(Comandos.USUARIO_REGISTRADO_SUCESSO);
				mensagem.setMensagem(
						"CHAT > "
						+ nomeUsuario + " registrado com sucesso!\n"
						+ Servidor.menu);
			} else { // usu�rio j� existe
				mensagem.setComando(Comandos.REQUISITAR_NOME_USUARIO);
				mensagem.setMensagem(
						"CHAT > O usu�rio "
						+ nomeUsuario
						+ " j� est� registrado\nCHAT > Digite o seu nome de usu�rio: ");
			}

			Servidor.sender.enviarMensagem(mensagem);
			break;

		case ESCOLHE_MENU:
			System.out.println("Op��o escolhida: " + msg);
			break;

		case REGISTRAR:
			System.out.println("Registrar " + msg);
			break;
			
		default:
			mensagem.setMensagem("CHAT > Comando inv�lido!");
			Servidor.sender.enviarMensagem(mensagem);
			break;

		}
	}

}
