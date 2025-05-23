package pua.net.dto;

public class AmbienteDTO {
	private int idAmbiente;
	private String ambiente;
	private int idSubcompetencia;
	
	public int getIdSubcompetencia() {
		return idSubcompetencia;
	}
	public void setIdSubcompetencia(int idSubcompetencia) {
		this.idSubcompetencia = idSubcompetencia;
	}
	public int getIdAmbiente() {
		return idAmbiente;
	}
	public void setIdAmbiente(int idAmbiente) {
		this.idAmbiente = idAmbiente;
	}
	public String getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
	
	
	
}
