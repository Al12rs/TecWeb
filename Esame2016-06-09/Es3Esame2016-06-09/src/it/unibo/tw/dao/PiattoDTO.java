package it.unibo.tw.dao;

public class PiattoDTO {
	
	private int id;
	private String nomePiatto;
	private String tipo;
	
	public PiattoDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomePiatto() {
		return nomePiatto;
	}

	public void setNomePiatto(String nomePiatto) {
		this.nomePiatto = nomePiatto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "PiattoDTO [id=" + id + ", nomePiatto=" + nomePiatto + ", tipo=" + tipo + "]";
	}
	
	

}
