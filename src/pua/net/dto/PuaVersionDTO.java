package pua.net.dto;
import java.util.*;
public class PuaVersionDTO {
	private int idPua;
	private int puaVersion;
	private String estadoDirector;
	private String estadoCoordinador;
	private String estadoPresidente;
	private byte[] pua;
	public int getIdPua() {
		return idPua;
	}
	public int getPuaVersion() {
		return puaVersion;
	}
	public String getEstadoDirector() {
		return estadoDirector;
	}
	public String getEstadoCoordinador() {
		return estadoCoordinador;
	}
	public String getEstadoPresidente() {
		return estadoPresidente;
	}
	public byte[] getPua() {
		return pua;
	}
	public void setIdPua(int idPua) {
		this.idPua = idPua;
	}
	public void setPuaVersion(int puaVersion) {
		this.puaVersion = puaVersion;
	}
	public void setEstadoDirector(String estadoDirector) {
		this.estadoDirector = estadoDirector;
	}
	public void setEstadoCoordinador(String estadoCoordinador) {
		this.estadoCoordinador = estadoCoordinador;
	}
	public void setEstadoPresidente(String estadoPresidente) {
		this.estadoPresidente = estadoPresidente;
	}
	public void setPua(byte[] pua) {
		this.pua = pua;
	}
	
}