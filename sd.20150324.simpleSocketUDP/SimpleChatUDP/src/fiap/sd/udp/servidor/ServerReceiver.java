package fiap.sd.udp.servidor;

import fiap.sd.udp.chat.Comandos;
import fiap.sd.udp.chat.Mensagem;
import fiap.sd.udp.chat.Sala;
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
		Usuario currentUser = Servidor.getUsuarioByIp(ipOrigem);
		
		String msg = mensagem.getMensagem().trim();
		Comandos operacao = mensagem.getComando();

		switch (operacao) {
		case ACESSAR:
			System.out.println("> IP " + ipOrigem
					+ " acessando pela primeira vez");
			Servidor.acessar(ipOrigem);
			mensagem.setComando(Comandos.REQUISITAR_NOME_USUARIO);
			mensagem.setMensagem("CHAT > Digite o seu nome de usuário: ");
			Servidor.sender.enviarMensagem(mensagem);
			break;

		case ENVIAR_NOME_USUARIO:
			String nomeUsuario = msg;
			Usuario user = Servidor.obterUsuarioPorNome(nomeUsuario);
			if (user == null) { // usuário não existe
				Servidor.registrar(nomeUsuario, ipOrigem);
				mensagem.setComando(Comandos.MENU_APP);
				mensagem.setMensagem(
						"CHAT > "
						+ nomeUsuario + " registrado com sucesso!\n"
						+ Servidor.menuToString(Servidor._appMenu));
			} else { // usuário já existe
				mensagem.setComando(Comandos.REQUISITAR_NOME_USUARIO);
				mensagem.setMensagem(
						"CHAT > O usuário "
						+ nomeUsuario
						+ " já está registrado\nCHAT > Digite o seu nome de usuário: ");
			}

			Servidor.sender.enviarMensagem(mensagem);
			break;

		case MENU_APP:			
			int menuAppKey = Integer.parseInt(msg);
			System.out.println("[Menu App] Opção escolhida: " + menuAppKey);
			switch(menuAppKey)
			{
				case 1:
					mensagem.setComando(Comandos.MENU_APP);
					mensagem.setMensagem(
							"CHAT > \n" + Servidor.listarSalas()
							+ "Escolha uma nova opção:\n"
							+ Servidor.menuToString(Servidor._appMenu, 1)
							);
					break;
				
				case 2:
					mensagem.setComando(Comandos.CRIAR_SALA);
					mensagem.setMensagem(
							"CHAT > Criando uma nova sala...\n"
							);					
					break;
					
				case 3:
					mensagem.setComando(Comandos.ENTRAR_SALA);
					mensagem.setMensagem(
							"CHAT > Digite o nome da sala que deseja entrar:\n"
							);
					break;
				
				default:
					mensagem.setComando(Comandos.COMANDO_INVALIDO);
					mensagem.setMensagem(
							"CHAT > O menu selecionado é invalido, tente novamente! \n"
							+ Servidor.menuToString(Servidor._appMenu));
					break;
			}
			Servidor.sender.enviarMensagem(mensagem);
			break;
		
		case CRIAR_SALA:			
			if (currentUser == null || !currentUser.isRegistrado()) {
				mensagem.setComando(Comandos.REQUISITAR_NOME_USUARIO);
				mensagem.setMensagem(
						"CHAT > Usuário não identificado.\n"
						+ "Digite o seu nome de usuário: ");
			}
			else {
				Sala newRoom = gson.fromJson(msg, Sala.class);
				String result = Servidor.criarSala(currentUser.getNome(), newRoom.getNome(), newRoom.getDescricao());
				mensagem.setComando(Comandos.MENU_SALA);
				mensagem.setMensagem(
						"CHAT > " + result +
						"CHAT > Bem vindo à sala " + newRoom.getNome() + ".\n"+
						"Oque deseja fazer?\n"
						+ Servidor.menuToString(Servidor._roomMenu));
			}
			
			Servidor.sender.enviarMensagem(mensagem);
			break;
			
		case ENTRAR_SALA:			
			String roomName = msg;
			Sala room = Servidor.obterSalaPorNome(roomName);
			if (room == null)
			{
				mensagem.setComando(Comandos.COMANDO_INVALIDO);
				mensagem.setMensagem(
						"CHAT > Sala não encontrada, tente novamente! \n"
						+ Servidor.menuToString(Servidor._appMenu));
			}
			else {
				Servidor.entrar(currentUser.getNome(), room.getNome());				
				mensagem.setComando(Comandos.MENU_SALA);
				mensagem.setMensagem(
						"CHAT > Bem vindo à sala " + room.getNome() + ".\n"+
						"Oque deseja fazer?\n"
						+ Servidor.menuToString(Servidor._roomMenu));		
			}			
				
			Servidor.sender.enviarMensagem(mensagem);
			break;
			
		case MENU_SALA:
			Sala currentRoom = Servidor.obterSalaDoUsuario(currentUser.getNome());
			if (currentRoom != null)
			{				
				int menuRoomKey = Integer.parseInt(msg);
				System.out.println("[Menu Sala] Opção escolhida: " + menuRoomKey);
				switch(menuRoomKey)
				{
					case 1:
						mensagem.setComando(Comandos.MENU_SALA);
						mensagem.setMensagem(
								"CHAT > \n" + Servidor.listarUsuariosPorSala(currentRoom.getNome())
								+ "Escolha uma nova opção:\n"
								+ Servidor.menuToString(Servidor._roomMenu, 1)
								);
						break;
					
					case 2:
						
						break;	
						
					case 3:
						
						break;
						
					case 4:
						Servidor.sair(currentUser.getNome(), currentRoom.getNome());				
						mensagem.setComando(Comandos.MENU_APP);
						mensagem.setMensagem(
								"CHAT > Oque deseja fazer?\n"
								+ Servidor.menuToString(Servidor._appMenu));		
						break;	
						
					default:
						mensagem.setComando(Comandos.COMANDO_INVALIDO);
						mensagem.setMensagem(
								"CHAT > O menu selecionado é invalido, tente novamente! \n"
								+ Servidor.menuToString(Servidor._roomMenu));
						break;
				}
				Servidor.sender.enviarMensagem(mensagem);
			}
			else				
			{
				mensagem.setComando(Comandos.COMANDO_INVALIDO);
				mensagem.setMensagem(
					"CHAT > Sala não encontrada, tente novamente! \n"
					+ Servidor.menuToString(Servidor._appMenu));				
			}
			break;
						
		default:
			mensagem.setComando(Comandos.AVISO);
			mensagem.setMensagem("CHAT > Comando inválido!\n");		
			Servidor.sender.enviarMensagem(mensagem);
			break;			

		}
	}

}
