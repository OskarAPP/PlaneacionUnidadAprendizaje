package pua.net.dto;

import java.util.List;

public class DocenteDTO {
	private int idDocente;
	private String prefijo;
	private String nombre;
	private String apellidoMaterno;
	private String apellidoPaterno;
	private String correo;
	private int idPua;
	private int idTipo;
	private int idFacultad;
	private String tipo;
	private List<FacultadDTO> listaFacultad;
	private List<TipoDTO> listaTipo;
	private List<AccesoDTO> listaAcceso;
	private List<MateriaDTO> listaMaterias;
	
	public List<AccesoDTO> getListaAcceso() {
		return listaAcceso;
	}
	public void setListaAcceso(List<AccesoDTO> listaAcceso) {
		this.listaAcceso = listaAcceso;
	}
	public List<TipoDTO> getListaTipo() {
		return listaTipo;
	}
	public void setListaTipo(List<TipoDTO> listaTipo) {
		this.listaTipo = listaTipo;
	}
	public List<FacultadDTO> getListaFacultad() {
		return listaFacultad;
	}
	public void setListaFacultad(List<FacultadDTO> listaFacultad) {
		this.listaFacultad = listaFacultad;
	}
	public int getIdFacultad() {
		return idFacultad;
	}
	public void setIdFacultad(int idFacultad) {
		this.idFacultad = idFacultad;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}
	public int getIdDocente() {
		return idDocente;
	}
	public void setIdDocente(int idDocente) {
		this.idDocente = idDocente;
	}
	public String getPrefijo() {
		return prefijo;
	}
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public int getIdPua() {
		return idPua;
	}
	public void setIdPua(int idPua) {
		this.idPua = idPua;
	}
	public List<MateriaDTO> getListaMaterias() {
		return listaMaterias;
	}
	public void setListaMaterias(List<MateriaDTO> listaMaterias) {
		this.listaMaterias = listaMaterias;
	}
	
}
