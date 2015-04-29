package fiap.sd.udp.cliente;

import fiap.sd.udp.chat.Comandos;
import fiap.sd.udp.chat.Mensagem;
import fiap.sd.udp.simplechatudp.receiver.Receiver;

public class ClientReceiver extends Receiver {

	public ClientReceiver(int port) {
		super(port);
	}

	@Override
	protected void trataMensagem(String ipOrigem, Integer portaOrigem, Mensagem mensagem) {
		
		// TODO Mudar esquema de tratamento de comandos
		
		String msg = mensagem.getMensagem().trim();
		Comandos operacao = mensagem.getComando();
		
		switch(operacao) {
		case REQUISITAR_NOME_USUARIO:
			System.out.println(msg);			
			Cliente.solicitarInputUsuario(Comandos.ENVIAR_NOME_USUARIO);
			break;
		case USUARIO_REGISTRADO_SUCESSO:
			System.out.println(msg);			
			Cliente.solicitarInputUsuario(Comandos.ESCOLHE_MENU);
			break;
		default:
			
			break;
			
		}
	}

}
