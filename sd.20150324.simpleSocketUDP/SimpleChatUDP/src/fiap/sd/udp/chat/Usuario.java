package fiap.sd.udp.chat;

public class Usuario {
	private String nome;
	private String ipUsuario;
	private boolean isRegistrado = false;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIpUsuario() {
		return ipUsuario;
	}

	public void setIpUsuario(String ipUsuario) {
		this.ipUsuario = ipUsuario;
	}

	public boolean isRegistrado() {
		return isRegistrado;
	}

	public void setRegistrado(boolean isRegistrado) {
		this.isRegistrado = isRegistrado;
	}

	@Override
	public boolean equals(Object obj) {
		Usuario comp = (Usuario) obj;
		return ipUsuario.equals(comp.ipUsuario);
	}
}
