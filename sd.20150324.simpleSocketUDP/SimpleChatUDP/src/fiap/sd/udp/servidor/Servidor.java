package fiap.sd.udp.servidor;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fiap.sd.udp.chat.Comandos;
import fiap.sd.udp.chat.Mensagem;
import fiap.sd.udp.chat.Sala;
import fiap.sd.udp.chat.Usuario;

public class Servidor {
	public static List<Sala> salas;
	public static Set<Usuario> usuarios;
	public static ServerSender sender;	
	public static Map<Integer, String> _appMenu = new HashMap<Integer, String>();
	public static Map<Integer, String> _roomMenu = new HashMap<Integer, String>();
	
	static {
		salas = new ArrayList<Sala>();
		usuarios = new HashSet<Usuario>();
		sender = new ServerSender();
		
		// Menu da Aplicação
		_appMenu.put(1, "Listar Salas");
		_appMenu.put(2, "Criar Sala");
		_appMenu.put(3, "Entrar Sala");
		
		// Menu do Chat, dentro de uma sala
		_roomMenu.put(1, "Ver Usuários da sala");
		_roomMenu.put(2, "Enviar Mensagem");
		_roomMenu.put(3, "Enviar Mensagem Privada");
		_roomMenu.put(4, "Sair da Sala");
	}

	public static String menuToString(Map<Integer, String> menu) {
		String menuToString = "";
		for(Entry<Integer, String> option : menu.entrySet()) {			
		    Integer key = option.getKey();
		    String value = option.getValue();		    
		    menuToString += key + " - " + value + "\n";
		}
		
		return menuToString;
	}
	
	public static String menuToString(Map<Integer, String> menu, int keyToSkip) {
		String menuToString = "";
		for(Entry<Integer, String> option : menu.entrySet()) {			
		    Integer key = option.getKey();
		    String value = option.getValue();
		    
		    if (keyToSkip == key)
		    	continue;
		    
		    menuToString += key + " - " + value + "\n";
		}
		
		return menuToString;
	}
	
	public static void acessar(String ipUsuario) {
		Usuario usuario = new Usuario();
		usuario.setIpUsuario(ipUsuario);
		usuarios.add(usuario);
	}

	public static void registrar(String nomeUsuario, String ipOrigem) {		
		for (Usuario usuario : usuarios) {
			if (usuario.getIpUsuario().equals(ipOrigem)) {
				usuario.setNome(nomeUsuario);
				usuario.setRegistrado(true);
			}
		}	
	}

	public static Sala obterSalaPorNome(String nome) {
		for (Sala s : salas) {
			if (s != null && s.nome.equals(nome)) {
				return s;
			}
		}
		return null;

	}
	
	public static Sala obterSalaDoUsuario(String username) {
		for (Sala s : salas) {
			if (s != null) {				
				for (Usuario user : s.usuarios) {
					if (user != null && user.getNome().equals(username)) {
						return s;
					}
				}
			}
		}
		return null;
	}

	public static void entrar(String nomeUsuario, String nomeSala) {
		Sala sala = obterSalaPorNome(nomeSala);
		Usuario usuario = obterUsuarioPorNome(nomeUsuario);
		sala.usuarios.add(usuario);
			
		for (Usuario user : sala.usuarios) {
			Mensagem mensagem = new Mensagem(
					user.getIpUsuario(),
					Comandos.AVISO, 
					"CHAT > Usuário " + nomeUsuario + " entrou na sala.\n");			
			Servidor.sender.enviarMensagem(mensagem);
		}
	}

	public static void sair(String nomeUsuario, String nomeSala) {
		Sala room = obterSalaPorNome(nomeSala);
		
		for (Usuario usuario : room.usuarios) {
			if (usuario.getNome().equals(nomeUsuario)) {
				room.usuarios.remove(usuario);
			}
		}		
	}

	public static Usuario obterUsuarioPorNome(String nome) {
		for (Usuario user : usuarios) {
			if (user.getNome() != null && user.getNome().equalsIgnoreCase(nome)) {
				return user;
			}
		}
		return null;
	}

	public static String listarSalas() {
		if (salas.size() <= 0)
			return " --- Não existem salas cadastradas ---\n";
		
		String retorno = " --- Salas: ---\n";
		for (int i = 0; i < salas.size(); i++) {
			retorno +=  "(" + (i + 1) + ") " + salas.get(i).nome + "\n";
		}
		retorno += "--- ------- ----\n";
		return retorno;
	}

	public static String listarUsuariosPorSala(String nomeSala) {
		Sala sala = obterSalaPorNome(nomeSala);
		String retorno = " --- Usuários da sala " + nomeSala + ":  --- \n";
		for (int i = 0; i < sala.usuarios.size(); i++) {
			retorno +=  "(" + (i + 1) + ") " + sala.usuarios.get(i).getNome() + "\n";
		}
		return retorno;

	}

	public static String criarSala(String nomeCriador, String nomeSala,
			String descSala) {
		Usuario user = obterUsuarioPorNome(nomeCriador);
		Sala sala = new Sala();
		sala.nome = nomeSala;
		sala.descricao = descSala;
		sala.criador = user;
		salas.add(sala);
		entrar(nomeCriador, nomeSala);
		
		return "Sala criada com sucesso\n";
	}

	public static Usuario getUsuarioByIp(String ip) {
		Usuario user = null;

		for (Usuario usuario : usuarios) {
			if (usuario.getIpUsuario().equals(ip)) {
				user = usuario;
			}
		}

		return user;
	}

	/*
	 * enviarMensagem(usuárioRemetente, nomeDaSala, msg) – envia a mensagem
	 * “msg” para a sala “nomeDaSala”, garantindo o envio a todos os outros
	 * usuários. ◦ enviarMensagemPrivada(usuárioOrigem, usuárioDestino, msg) –
	 * Mensagem privada.
	 */

	public static void enviarMensagem(String remetente, String nomeSala,
			String msg) {

	}

	public static void trataMensagem(InetAddress ipOrigem, Integer portaOrigem,
			String mensagem) {

	}

}
