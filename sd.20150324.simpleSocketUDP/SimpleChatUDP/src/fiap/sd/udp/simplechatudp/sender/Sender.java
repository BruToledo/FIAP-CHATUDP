package fiap.sd.udp.simplechatudp.sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.google.gson.Gson;

import fiap.sd.udp.chat.Message;

/**
 * Implementa o lado "Falador" de nosso chat UDP simples
 */
public class Sender {
	private DatagramSocket speakSocket;
	private Integer PORT;
	private String DESTINATION_IP;
	private Gson gson;

	private Sender() {
		try {
			this.speakSocket = new DatagramSocket();
			this.gson = new Gson();
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

	public Sender(int port) {
		this();
		PORT = port;
	}

	public Sender(String destinationIp, int port) {
		this();
		PORT = port;
		DESTINATION_IP = destinationIp;
	}

	public synchronized void sendMessage(Message message) {

		try {
			DatagramPacket packet = null;
			String jsonMsg = gson.toJson(message);

			if (DESTINATION_IP != null) {
				packet = new DatagramPacket(jsonMsg.getBytes(),
						jsonMsg.length(), InetAddress.getByName(DESTINATION_IP),
						PORT);

			} else {
				packet = new DatagramPacket(jsonMsg.getBytes(),
						jsonMsg.length(), InetAddress.getByName(message.getDestinationIp()),
						PORT);
			}

			speakSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
