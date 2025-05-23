package pua.net.dto;
//DTO de especifica
public class EspecificaDTO {
	private int idEspecifica;
	private int idPua;
	private int idCarrera;
	private String perfilEspecifica;

	public int getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(int idCarrera) {
		this.idCarrera = idCarrera;
	}

	public int getIdEspecifica() {
		return idEspecifica;
	}
	
	public void setIdEspecifica(int idEspecifica) {
		this.idEspecifica = idEspecifica;
	}
	public String getPerfilEspecifica() {
		return perfilEspecifica;
	}
	public void setPerfilEspecifica(String perfilEspecifica) {
		this.perfilEspecifica = perfilEspecifica;
	}

	public int getIdPua() {
		return idPua;
	}

	public void setIdPua(int idPua) {
		this.idPua = idPua;
	}
}
