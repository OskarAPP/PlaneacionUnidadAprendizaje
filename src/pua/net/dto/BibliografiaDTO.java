package pua.net.dto;

import java.util.ArrayList;
import java.util.List;

public class BibliografiaDTO {
	private int idTipoBibliografia;
	private int idLibro;
	private int idPua;
	private int idSubcompetencia;
	private String tipoBibliografia;
	private String libro;
	private List<LibroDTO> listaBasica;
	private List<LibroDTO> listaComplementaria;
	private List<LibroDTO> listaSitios;
	
	public List<LibroDTO> getListaBasica() {
		return listaBasica;
	}
	public List<LibroDTO> getListaComplementaria() {
		return listaComplementaria;
	}
	public List<LibroDTO> getListaSitios() {
		return listaSitios;
	}
	public void setListaBasica(List<LibroDTO> listaBasica) {
		this.listaBasica = listaBasica;
	}
	public void setListaComplementaria(List<LibroDTO> listaComplementaria) {
		this.listaComplementaria = listaComplementaria;
	}
	public void setListaSitios(List<LibroDTO> listaSitios) {
		this.listaSitios = listaSitios;
	}
	public int getIdTipoBibliografia() {
		return idTipoBibliografia;
	}
	public void setIdTipoBibliografia(int idTipoBibliografia) {
		this.idTipoBibliografia = idTipoBibliografia;
	}
	public String getTipoBibliografia() {
		return tipoBibliografia;
	}
	public void setTipoBibliografia(String tipoBibliografia) {
		this.tipoBibliografia = tipoBibliografia;
	}
	public int getIdLibro() {
		return idLibro;
	}
	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}
	public int getIdPua() {
		return idPua;
	}
	public void setIdPua(int idPua) {
		this.idPua = idPua;
	}
	public int getIdSubcompetencia() {
		return idSubcompetencia;
	}
	public void setIdSubcompetencia(int idSubcompetencia) {
		this.idSubcompetencia = idSubcompetencia;
	}
	public String getLibro() {
		return libro;
	}
	public void setLibro(String libro) {
		this.libro = libro;
	}
}