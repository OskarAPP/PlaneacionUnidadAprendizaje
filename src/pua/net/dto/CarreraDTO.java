package pua.net.dto;

public class CarreraDTO {
	private int idCarrera;
	private int idFacultad;
	private String nombreCarrera;
	private int planEstudios;
	private int idCoordinador;
	
	public int getIdCarrera() {
		return idCarrera;
	}
	public void setIdCarrera(int idCarrera) {
		this.idCarrera = idCarrera;
	}
	public int getIdFacultad() {
		return idFacultad;
	}
	public void setIdFacultad(int idFacultad) {
		this.idFacultad = idFacultad;
	}
	public String getNombreCarrera() {
		return nombreCarrera;
	}
	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}
	public int getPlanEstudios() {
		return planEstudios;
	}
	public void setPlanEstudios(int planEstudios) {
		this.planEstudios = planEstudios;
	}
	public int getIdCoordinador() {
		return idCoordinador;
	}
	public void setIdCoordinador(int idCoordinador) {
		this.idCoordinador = idCoordinador;
	}
}