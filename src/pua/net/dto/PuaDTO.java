package pua.net.dto;

public class PuaDTO {
	private int idPua;
	private int idMateria;
	private int creditacionCompetencias;
	private String competenciaUda;
	private String competenciaFormacion;
	private String perfilAcademico;
	private String perfilProfesional;
	private String perfilDocente;
	private CarreraDTO carreraDTO;
	private MateriaDTO materiaDTO;
	private FacultadDTO facultadDTO;
	private AcademiaDTO academiaDTO;
	private int planEstudio;
	public int getPlanEstudio() {
		return planEstudio;
	}
	public void setPlanEstudio(int planEstudio) {
		this.planEstudio = planEstudio;
	}
	public AcademiaDTO getAcademiaDTO() {
		return academiaDTO;
	}
	public void setAcademiaDTO(AcademiaDTO academiaDTO) {
		this.academiaDTO = academiaDTO;
	}
	public CarreraDTO getCarreraDTO() {
		return carreraDTO;
	}
	public MateriaDTO getMateriaDTO() {
		return materiaDTO;
	}
	public FacultadDTO getFacultadDTO() {
		return facultadDTO;
	}
	public void setCarreraDTO(CarreraDTO carreraDTO) {
		this.carreraDTO = carreraDTO;
	}
	public void setMateriaDTO(MateriaDTO materiaDTO) {
		this.materiaDTO = materiaDTO;
	}
	public void setFacultadDTO(FacultadDTO facultadDTO) {
		this.facultadDTO = facultadDTO;
	}
	public int getIdPua() {
		return idPua;
	}
	public void setIdPua(int idPua) {
		this.idPua = idPua;
	}
	public int getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(int idMateria) {
		this.idMateria = idMateria;
	}
	public int getCreditacionCompetencias() {
		return creditacionCompetencias;
	}
	public void setCreditacionCompetencias(int creditacionCompetencias) {
		this.creditacionCompetencias = creditacionCompetencias;
	}
	public String getCompetenciaUda() {
		return competenciaUda;
	}
	public void setCompetenciaUda(String competenciaUda) {
		this.competenciaUda = competenciaUda;
	}
	public String getCompetenciaFormacion() {
		return competenciaFormacion;
	}
	public void setCompetenciaFormacion(String competencia_formacion) {
		this.competenciaFormacion = competencia_formacion;
	}
	public String getPerfilAcademico() {
		return perfilAcademico;
	}
	public void setPerfilAcademico(String perfilAcademico) {
		this.perfilAcademico = perfilAcademico;
	}
	public String getPerfilProfesional() {
		return perfilProfesional;
	}
	public void setPerfilProfesional(String perfilProfesional) {
		this.perfilProfesional = perfilProfesional;
	}
	public String getPerfilDocente() {
		return perfilDocente;
	}
	public void setPerfilDocente(String perfilDocente) {
		this.perfilDocente = perfilDocente;
	}
	
	
}
