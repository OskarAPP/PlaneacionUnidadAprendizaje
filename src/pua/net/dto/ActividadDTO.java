package pua.net.dto;

public class ActividadDTO {
	private int idActividad;
	private String actividad;
	private String rolDeActividad;
	private int idSubcompetencia;
	
	public int getIdActividad() {
		return idActividad;
	}
	public void setIdActividad(int idActividadDocente) {
		this.idActividad = idActividadDocente;
	}
	
	public int getIdSubcompetencia() {
		return idSubcompetencia;
	}
	public void setIdSubcompetencia(int idSubcompetencia) {
		this.idSubcompetencia = idSubcompetencia;
	}
	
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getRolDeActividad() {
		return rolDeActividad;
	}
	public void setRolDeActividad(String rolDeActividad) {
		this.rolDeActividad = rolDeActividad;
	}
	
	
	
}
