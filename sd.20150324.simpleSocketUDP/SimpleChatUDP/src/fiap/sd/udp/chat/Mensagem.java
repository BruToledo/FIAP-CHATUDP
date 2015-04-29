package fiap.sd.udp.chat;

public class Mensagem {
	private String ipDestino;
	private Comandos comando;
	private String mensagem;

	public String getIpDestino() {
		return ipDestino;
	}

	public void setIpDestino(String ipDestino) {
		this.ipDestino = ipDestino;
	}

	public Comandos getComando() {
		return comando;
	}

	public void setComando(Comandos comando) {
		this.comando = comando;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
