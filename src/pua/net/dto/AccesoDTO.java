package pua.net.dto;
public class AccesoDTO {
	private int idAcceso;
	private int idDocente;
	private String correo;
	private int estado;
	private String pass;
	private String rol;
	public int getIdAcceso() {
		return idAcceso;
	}
	public void setIdAcceso(int idAcceso) {
		this.idAcceso = idAcceso;
	}
	public int getIdDocente() {
		return idDocente;
	}
	public String getCorreo() {
		return correo;
	}
	public int getEstado() {
		return estado;
	}
	public String getPass() {
		return pass;
	}
	public String getRol() {
		return rol;
	}
	public void setIdDocente(int idDocente) {
		this.idDocente = idDocente;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
}
