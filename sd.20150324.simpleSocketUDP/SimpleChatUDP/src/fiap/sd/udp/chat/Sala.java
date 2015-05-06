package fiap.sd.udp.chat;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Sala {
	public String nome;
	public String descricao;
	public Usuario criador;
	public List<Usuario> usuarios;
	private static Gson gson;
	
	static
	{
		gson = new Gson();
	}
	
	public Sala()
	{
		this.criador = new Usuario();
		this.usuarios = new ArrayList<Usuario>();
		
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Usuario getCriador() {
		return criador;
	}
	public void setCriador(Usuario criador) {
		this.criador = criador;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public static String toJsonString(Sala room)
	{
		return gson.toJson(room);
	}
}