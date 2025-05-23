package pua.net.dto;
import java.util.List;
public class PerfilDTO {
	private int idPerfil;
	private String perfil;
	private int idPua;
	private List<PerfilDTO> listaAcademicos;
	private List<PerfilDTO> listaDocentes;
	private List<PerfilDTO> listaProfesionales;
	
	public List<PerfilDTO> getListaAcademicos() {
		return listaAcademicos;
	}
	public List<PerfilDTO> getListaDocentes() {
		return listaDocentes;
	}
	public List<PerfilDTO> getListaProfesionales() {
		return listaProfesionales;
	}
	public void setListaAcademicos(List<PerfilDTO> listaAcademicos) {
		this.listaAcademicos = listaAcademicos;
	}
	public void setListaDocentes(List<PerfilDTO> listaDocentes) {
		this.listaDocentes = listaDocentes;
	}
	public void setListaProfesionales(List<PerfilDTO> listaProfesionales) {
		this.listaProfesionales = listaProfesionales;
	}
	public int getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public int getIdPua() {
		return idPua;
	}
	public void setIdPua(int idPua) {
		this.idPua = idPua;
	}
}