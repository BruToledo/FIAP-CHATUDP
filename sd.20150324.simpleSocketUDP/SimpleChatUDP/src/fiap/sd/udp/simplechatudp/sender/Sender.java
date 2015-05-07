package fiap.sd.udp.simplechatudp.sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.google.gson.Gson;

import fiap.sd.udp.chat.Mensagem;

/**
 * Implementa o lado "Falador" de nosso chat UDP simples
 * 
 * @author fm
 *
 */
public class Sender {
	private DatagramSocket speakSocket;
	private Integer PORTA;
	private String IP_DESTINO;
	private Gson gson;

	private Sender() {
		try {
			this.speakSocket = new DatagramSocket();
			this.gson = new Gson();
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

	public Sender(int porta) {
		this();
		PORTA = porta;
	}

	public Sender(String ipDestino, int porta) {
		this();
		PORTA = porta;
		IP_DESTINO = ipDestino;
	}

	public synchronized void enviarMensagem(Mensagem msg) {

		try {
			DatagramPacket packet = null;
			String jsonMsg = gson.toJson(msg);

			if (IP_DESTINO != null) {
				packet = new DatagramPacket(jsonMsg.getBytes(),
						jsonMsg.length(), InetAddress.getByName(IP_DESTINO),
						PORTA);

			} else {
				packet = new DatagramPacket(jsonMsg.getBytes(),
						jsonMsg.length(), InetAddress.getByName(msg.getIpDestino()),
						PORTA);
			}

			speakSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
