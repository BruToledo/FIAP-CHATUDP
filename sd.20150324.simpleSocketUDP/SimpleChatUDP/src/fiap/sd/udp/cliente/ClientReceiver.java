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
				
		String msg = mensagem.getMensagem().trim();
		Comandos operacao = mensagem.getComando();
		
		switch(operacao) {
			case AVISO:
				System.out.println(msg);
				break;	
			case REQUISITAR_NOME_USUARIO:
				System.out.println(msg);			
				Cliente.solicitarInputUsuario(Comandos.ENVIAR_NOME_USUARIO);
				break;
			case MENU_APP:
			case COMANDO_INVALIDO:
				System.out.println(msg);			
				Cliente.solicitarInputUsuario(Comandos.MENU_APP);			
				break;	
			case CRIAR_SALA:
				System.out.println(msg);			
				Cliente.solicitarInputCriarSala(Comandos.CRIAR_SALA);
				break;
			case ENTRAR_SALA:
				System.out.println(msg);			
				Cliente.solicitarInputUsuario(Comandos.ENTRAR_SALA);
				break;
			case MENU_SALA:
				System.out.println(msg);			
				Cliente.solicitarInputUsuario(Comandos.MENU_SALA);			
				break;			
			default:
				break;
		}
	}

}
