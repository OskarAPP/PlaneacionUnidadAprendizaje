package pua.net.dto;

public class AcademiaDTO {
	
	private int idAcademia;
	private int idMateria;
	private String nombreAcademia;
	private int idPresidente;
	private int idSecretario;
	private int idFacultad;
	private String facultad;
	
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
	public int getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(int idMateria) {
		this.idMateria = idMateria;
	}
	
	public String getNombreAcademia() {
		return nombreAcademia;
	}
	public void setNombreAcademia(String nombreAcademia) {
		this.nombreAcademia = nombreAcademia;
	}
	public int getIdPresidente() {
		return idPresidente;
	}
	public void setIdPresidente(int idPresidente) {
		this.idPresidente = idPresidente;
	}
	public int getIdSecretario() {
		return idSecretario;
	}
	public void setIdSecretario(int idSecretario) {
		this.idSecretario = idSecretario;
	}
	public int getIdAcademia() {
		return idAcademia;
	}
	public void setIdAcademia(int idAcademia) {
		this.idAcademia = idAcademia;
	}
	

}
