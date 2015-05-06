package fiap.sd.udp.simplechatudp.receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

import com.google.gson.Gson;

import fiap.sd.udp.chat.Mensagem;

/**
 * Implementa o lado "Ouvidor" de nosso chat UDP simples
 * 
 * @author fm
 *
 */
public abstract class Receiver extends Thread {

	// PARA PESQUISAR: Qual o tamanho maximo do buffer?
	private static int BUFSIZE = 4096;
	private DatagramSocket listenSocket;
	protected Gson gson;

	private Receiver() {
		gson = new Gson();
	}

	public Receiver(int port) {
		this();
		try {
			listenSocket = new DatagramSocket(port);
			System.out.println("> Ouvindo mensagens em "
					+ listenSocket.getLocalSocketAddress());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	protected abstract void trataMensagem(String ipOrigem, Integer portaOrigem,
			Mensagem mensagem);

	public void run() {
		String ip;
		byte[] buffer = new byte[BUFSIZE];
		while (listenSocket != null) {
			try {
				Arrays.fill(buffer, (byte) ' ');
				DatagramPacket packet = new DatagramPacket(buffer, BUFSIZE);
				listenSocket.receive(packet);
				
				ip = packet.getAddress().toString();
				if(ip.substring(0, 1).equals("/")) {
					ip = ip.substring(1);
				}

				trataMensagem(
						ip, 
						packet.getPort(),
						gson.fromJson(
								new String(
										packet.getData()), 
										Mensagem.class));

				Thread.yield();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
