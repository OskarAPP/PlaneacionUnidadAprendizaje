package pua.net.dto;

import java.util.List;

public class SubcompetenciaDTO {
	private int idSubcompetencia;
	private int numSubcompetencia;
	private int idPua;
	private String subcompetencia;
	private int sesiones;
	private int ponderacion;
	private int parcial;
	private List<TemaDTO> listaTemas;
	private List<ActividadDTO> listaActividadesAlumno;
	private List<ActividadDTO> listaActividadesDocente;
	private List<BibliografiaDTO> listaBibliografia;
	private List<CriterioDTO> listaCriterios;
	private List<EvidenciaDTO> listaEvidencia;
	private List<AmbienteDTO> listaAmbiente;
	private List<MaterialDTO> listaMaterial;
	private String listaSesiones;
	private String temas, subtemas, alumno, docente, bibliografia ;
	private String criterios, evidencia, ambiente, material, ponderadoE, ponderado;
	public String getPonderado() {
		return ponderado;
	}
	public void setPonderado(String ponderado) {
		this.ponderado = ponderado;
	}
	public String getCriterios() {
		return criterios;
	}
	public String getEvidencia() {
		return evidencia;
	}
	public String getAmbiente() {
		return ambiente;
	}
	public String getMaterial() {
		return material;
	}
	public String getPonderadoE() {
		return ponderadoE;
	}
	public void setCriterios(String criterios) {
		this.criterios = criterios;
	}
	public void setEvidencia(String evidencia) {
		this.evidencia = evidencia;
	}
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public void setPonderadoE(String ponderadoE) {
		this.ponderadoE = ponderadoE;
	}
	public String getListaSesiones() {
		return listaSesiones;
	}
	public String getTemas() {
		return temas;
	}
	public String getSubtemas() {
		return subtemas;
	}
	public String getAlumno() {
		return alumno;
	}
	public String getDocente() {
		return docente;
	}
	public String getBibliografia() {
		return bibliografia;
	}
	public void setListaSesiones(String listaSesiones) {
		this.listaSesiones = listaSesiones;
	}
	public void setTemas(String temas) {
		this.temas = temas;
	}
	public void setSubtemas(String subtemas) {
		this.subtemas = subtemas;
	}
	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}
	public void setDocente(String docente) {
		this.docente = docente;
	}
	public void setBibliografia(String bibliografia) {
		this.bibliografia = bibliografia;
	}
	public List<MaterialDTO> getListaMaterial() {
		return listaMaterial;
	}
	public void setListaMaterial(List<MaterialDTO> listaMaterial) {
		this.listaMaterial = listaMaterial;
	}
	public List<AmbienteDTO> getListaAmbiente() {
		return listaAmbiente;
	}
	public void setListaAmbiente(List<AmbienteDTO> listaAmbiente) {
		this.listaAmbiente = listaAmbiente;
	}
	public List<CriterioDTO> getListaCriterios() {
		return listaCriterios;
	}
	public void setListaCriterios(List<CriterioDTO> listaCriterios) {
		this.listaCriterios = listaCriterios;
	}
	public List<EvidenciaDTO> getListaEvidencia() {
		return listaEvidencia;
	}
	public void setListaEvidencia(List<EvidenciaDTO> listaEvidencia) {
		this.listaEvidencia = listaEvidencia;
	}
	public List<BibliografiaDTO> getListaBibliografia() {
		return listaBibliografia;
	}
	public void setListaBibliografia(List<BibliografiaDTO> listaBibliografia) {
		this.listaBibliografia = listaBibliografia;
	}
	public List<ActividadDTO> getListaActividadesDocente() {
		return listaActividadesDocente;
	}
	public void setListaActividadesDocente(List<ActividadDTO> listaActividadesDocente) {
		this.listaActividadesDocente = listaActividadesDocente;
	}
	public List<ActividadDTO> getListaActividadesAlumno() {
		return listaActividadesAlumno;
	}
	public void setListaActividadesAlumno(List<ActividadDTO> listaActividadesAlumno) {
		this.listaActividadesAlumno = listaActividadesAlumno;
	}
	public List<TemaDTO> getListaTemas() {
		return listaTemas;
	}
	public void setListaTemas(List<TemaDTO> listaTemas) {
		this.listaTemas = listaTemas;
	}
	public int getIdSubcompetencia() {
		return idSubcompetencia;
	}
	public void setIdSubcompetencia(int idSubcompetencia) {
		this.idSubcompetencia = idSubcompetencia;
	}
	
	public int getNumSubcompetencia() {
		return numSubcompetencia;
	}
	public void setNumSubcompetencia(int numSubcompetencia) {
		this.numSubcompetencia = numSubcompetencia;
	}
	public int getIdPua() {
		return idPua;
	}
	public void setIdPua(int idPua) {
		this.idPua = idPua;
	}
	public String getSubcompetencia() {
		return subcompetencia;
	}
	public void setSubcompetencia(String subcompetencia) {
		this.subcompetencia = subcompetencia;
	}
	public int getSesiones() {
		return sesiones;
	}
	public void setSesiones(int sesiones) {
		this.sesiones = sesiones;
	}
	public int getPonderacion() {
		return ponderacion;
	}
	public void setPonderacion(int ponderacion) {
		this.ponderacion = ponderacion;
	}
	public int getParcial() {
		return parcial;
	}
	public void setParcial(int parcial) {
		this.parcial = parcial;
	}
	

}
