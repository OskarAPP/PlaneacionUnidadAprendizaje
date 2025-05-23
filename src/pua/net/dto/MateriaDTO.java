package pua.net.dto;

public class MateriaDTO {
	private int idMateria; 
	private int idArea;
	private int idNucleo;
	private int idTipo;
	private int idCarrera;
	private String nombreMateria; 
	private int creditosTotales; 
	private int horasTotales; 
	private int horasTeoricas;
	private int horasPracticas; 
	private String art57;
	private String area;
	private String nucleo;
	private String tipo;

	public int getIdCarrera() {
		return idCarrera;
	}
	public void setIdCarrera(int idCarrera) {
		this.idCarrera = idCarrera;
	}
	public String getNombreMateria() {
		return nombreMateria;
	}
	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}
	public int getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(int idMateria) {
		this.idMateria = idMateria;
	}

	public int getIdArea() {
		return idArea;
	}
	public void setIdArea(int idArea) {
		this.idArea = idArea;
	}
	public int getIdNucleo() {
		return idNucleo;
	}
	public void setIdNucleo(int idNucleo) {
		this.idNucleo = idNucleo;
	}
	public int getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}
	
	public int getCreditosTotales() {
		return creditosTotales;
	}
	public void setCreditosTotales(int creditosTotales) {
		this.creditosTotales = creditosTotales;
	}
	public int getHorasTotales() {
		return horasTotales;
	}
	public void setHorasTotales(int horasTotales) {
		this.horasTotales = horasTotales;
	}
	public int getHorasTeoricas() {
		return horasTeoricas;
	}
	public void setHorasTeoricas(int horasTeoricas) {
		this.horasTeoricas = horasTeoricas;
	}
	public int getHorasPracticas() {
		return horasPracticas;
	}
	public void setHorasPracticas(int horasPracticas) {
		this.horasPracticas = horasPracticas;
	}
	public String getArt57() {
		return art57;
	}
	public void setArt57(String art57) {
		this.art57 = art57;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNucleo() {
		return nucleo;
	}
	public void setNucleo(String nucleo) {
		this.nucleo = nucleo;
	}


}
