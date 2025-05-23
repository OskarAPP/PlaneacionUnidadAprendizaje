package pua.net.dto;

public class FacultadDTO {
	private int idFacultad;
	private String facultad;
	private int idDirector;
	private int idSecretarioAcademico;
	private String micrositio;
	
	public String getMicrositio() {
		return micrositio;
	}
	public void setMicrositio(String micrositio) {
		this.micrositio = micrositio;
	}
	public int getIdFacultad() {
		return idFacultad;
	}
	public void setIdFacultad(int idFacultad) {
		this.idFacultad = idFacultad;
	}
	public String getFacultad() {
		return facultad;
	}
	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}
	public int getIdDirector() {
		return idDirector;
	}
	public void setIdDirector(int idDirector) {
		this.idDirector = idDirector;
	}
	public int getIdSecretarioAcademico() {
		return idSecretarioAcademico;
	}
	public void setIdSecretarioAcademico(int idSecretarioAcademico) {
		this.idSecretarioAcademico = idSecretarioAcademico;
	}
	
}
