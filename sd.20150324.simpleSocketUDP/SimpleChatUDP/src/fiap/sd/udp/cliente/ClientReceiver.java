package fiap.sd.udp.cliente;

import fiap.sd.udp.chat.Mensagem;
import fiap.sd.udp.chat.Operacoes;
import fiap.sd.udp.simplechatudp.receiver.Receiver;

public class ClientReceiver extends Receiver {

	public ClientReceiver(int port) {
		super(port);
	}

	@Override
	protected void trataMensagem(String ipOrigem, Integer portaOrigem, Mensagem mensagem) {
		
		// TODO Mudar esquema de tratamento de comandos
		
		String[] dados = mensagem.getMensagem().split("\\|");
		String operacao = dados[0].trim() + "|";
		
		switch(operacao) {
		case Operacoes.REQUISITAR_NOME_USUARIO:
			if(dados.length > 1) {
				System.out.println(dados[1]);
			}
			Cliente.solicitarInputUsuario(Operacoes.ENVIAR_NOME_USUARIO);
			break;
		case Operacoes.USUARIO_REGISTRADO_SUCESSO:
			if(dados.length > 1) {
				System.out.println(dados[1]);
			}
			Cliente.solicitarInputUsuario(Operacoes.ESCOLHE_MENU);
			break;
		default:
			
			break;
			
		}
	}

}
