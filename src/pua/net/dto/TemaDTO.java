package pua.net.dto;

import java.util.List;

public class TemaDTO {
	private int idTema;
	private String tema;
	private int numeroTema;
	private int idSubcompetencia;
	private List<SubtemaDTO> listaSubtema;
	
	public int getIdTema() {
		return idTema;
	}
	public void setIdTema(int idTema) {
		this.idTema = idTema;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public int getNumeroTema() {
		return numeroTema;
	}
	public void setNumeroTema(int numeroTema) {
		this.numeroTema = numeroTema;
	}
	public int getIdSubcompetencia() {
		return idSubcompetencia;
	}
	public void setIdSubcompetencia(int id_Subcompetencia) {
		this.idSubcompetencia = id_Subcompetencia;
	}
	public List<SubtemaDTO> getListaSubtema() {
		return listaSubtema;
	}
	public void setListaSubtema(List<SubtemaDTO> listaSubtema) {
		this.listaSubtema = listaSubtema;
	}
	
	
	
	
}
