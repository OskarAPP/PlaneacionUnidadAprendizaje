package pua.net.dao;

import java.sql.SQLException;
import java.util.List;

import pua.net.dto.*;

public abstract class PuaDAO {
	//read
	public abstract DocenteDTO obtenerDocente(String correo,String pass ) throws SQLException;
	public abstract DocenteDTO obtenerTipoDocente(int idDocente,DocenteDTO docenteDTO) throws SQLException;
	public abstract MateriaDTO obtenerInfoMateria(int idMateria) throws SQLException;
	public abstract PuaDTO obtenerPuaPorIdMateria(int idMateria) throws SQLException;
	public abstract List<FacultadDTO> obtenerListaFacultad() throws SQLException;
	public abstract MateriaDTO obtenerMateriaPorID(MateriaDTO materiaDTO) throws SQLException;
	public abstract List<FacultadDTO> obtenerDocenteFacultadId(int idDocente, int tipoDocente) throws SQLException;
	public abstract List<CarreraDTO> obtenerCarreraMateria(int idFacultad, int idDocente, String accion) throws SQLException;
	public abstract List<CarreraDTO> obtenerCarreraCoordinador(int idDocente) throws SQLException;
	public abstract List<MateriaDTO> obtenerMateriaAcademia(int idDocente, int idCarrera) throws SQLException;
	public abstract List<MateriaDTO> obtenerListaMateriaDocente(int idDocente, int idCarrera) throws SQLException;
	public abstract List<CarreraDTO> obtenerListaCarreras(int idFacultad) throws SQLException;
	public abstract List<MateriaDTO> obtenerListaMateria(int idCarrera) throws SQLException;
	public abstract List<GenericaDTO> obtenerListaCompetenciasGenericas() throws SQLException;
	public abstract List<EspecificaDTO> obtenerListaCompetenciasEspecificas(int idCarrera) throws SQLException;
	public abstract List<LibroDTO> obtenerListaLibros() throws SQLException;
	public abstract List<SubcompetenciaDTO> obtenerSubcompetencias(int idPua) throws SQLException;
	public abstract List<TemaDTO> obtenerTemas(int idSubcompetencia) throws SQLException;
	public abstract List<SubtemaDTO> obtenerSubtemas(int idTema) throws SQLException;
	public abstract List<CriterioDTO> obtenerCriterios(int idSubcompetencia) throws SQLException;
	public abstract List<ActividadDTO> obtenerActividadesAlumno(int idSubcompetencia) throws SQLException;
	public abstract List<ActividadDTO> obtenerActividadesDocente(int idSubcompetencia) throws SQLException;
	public abstract List<EvidenciaDTO> obtenerEvidencias() throws SQLException;
	public abstract List<EvidenciaDTO> obtenerEvidenciasSubcompetencia(int idSubcompetencia) throws SQLException;
	public abstract List<GenericaDTO> obtenerGenericasPua(int idPua) throws SQLException;
	public abstract List<EspecificaDTO> obtenerEspecificasPua(int idPua) throws SQLException;
	public abstract List<DocenteDTO> obtenerDirector(int idFacultad) throws SQLException;
	public abstract List<DocenteDTO> obtenerSecretarioAcademico(int idFacultad) throws SQLException;
	public abstract List<DocenteDTO> obtenerCoordinador(int idCarrera) throws SQLException;
	public abstract List<DocenteDTO> obtenerPresidenteAcademia(int idMateria) throws SQLException;
	public abstract List<DocenteDTO> obtenerSecretarioAcademia(int idMateria) throws SQLException;
	public abstract List<DocenteDTO> obtenerDocenteFacultad(int idFacultad) throws SQLException;
	public abstract List<DocenteDTO> obtenerDocentePua(int idPua) throws SQLException;
	public abstract List<AmbienteDTO> obtenerListaAmbientes() throws SQLException;
	public abstract List<BibliografiaDTO> obtenerTiposBibliografia() throws SQLException;
	public abstract List<LibroDTO> obtenerBibliografiaPua(int idPua) throws SQLException;
	public abstract List<AmbienteDTO> obtenerAmbientesSubcompetencia(int idSubcompetencia) throws SQLException;
	public abstract List<BibliografiaDTO> obtenerBibliografiaSubcompetencia(int idSubcompetencia) throws SQLException;
	public abstract List<PerfilDTO> obtenerPerfilesAcademicos(int idPua) throws SQLException;
	public abstract List<PerfilDTO> obtenerPerfilesProfesional(int idPua) throws SQLException;
	public abstract List<PerfilDTO> obtenerPerfilesDocente(int idPua) throws SQLException;
	public abstract List<MaterialDTO> obtenerListaMateriales() throws SQLException;
	public abstract List<MaterialDTO> obtenerListaMaterialesSubcompetencia(int idSubcompetencia) throws SQLException;
	public abstract int obtenerPonderacionExcepto(int idPua, int idSubcompetencia) throws SQLException;
	public abstract int obtenerPonderacionEvidencias(int idSubcompetencia) throws SQLException;
	public abstract CarreraDTO obtenerCarreraPorID(int idCarrera) throws SQLException;
	public abstract FacultadDTO obtenerFacultadPorID(int idFacultad) throws SQLException;
	public abstract List<EvaluacionDTO> obtenerListaDeEvaluacionCompetencias(int idPua) throws SQLException;
	public abstract List<EvaluacionFinalDTO> obtenerListaDeEvaluacionFinal(int idPua) throws SQLException;
	public abstract AcademiaDTO obtenerAcademia(int idMateria) throws SQLException;
	
	//create
	public abstract int insertarPua(PuaDTO puaDTO) throws SQLException;
	public abstract int insertarSubcompetencia(SubcompetenciaDTO subcompetenciaDTO) throws SQLException;
	public abstract void insertarSubtema(SubtemaDTO subtemaDTO) throws SQLException;
	public abstract void insertarTema(TemaDTO temaDTO) throws SQLException;
	public abstract void insertarActividadDocente(ActividadDTO actividadDTO) throws SQLException;
	public abstract void insertarActividadAlumno(ActividadDTO actividadDTO) throws SQLException;
	public abstract void insertarCriterio(CriterioDTO criterioDTO) throws SQLException;
	public abstract void insertarEvidencia(EvidenciaDTO evidenciaDTO) throws SQLException;
	public abstract void insertarDocentePua(DocenteDTO docenteDTO) throws SQLException;
	public abstract void insertarCompetenciaGenericaPua(GenericaDTO genericaDTO) throws SQLException;
	public abstract void insertarCompetenciaEspecificaPua(EspecificaDTO especificaDTO) throws SQLException;
	public abstract void insertarBibliografiaPua(BibliografiaDTO bibliografiaDTO) throws SQLException;
	public abstract void insertarAmbienteSubcompetencia(AmbienteDTO ambienteDTO) throws SQLException;
	public abstract void insertarBibliografiaSubcompetencia(BibliografiaDTO bibliografiaDTO) throws SQLException;
	public abstract void insertarPerfilAcademico(PerfilDTO perfilDTO) throws SQLException;
	public abstract void insertarPerfilProfesional(PerfilDTO perfilDTO) throws SQLException;
	public abstract void insertarPerfilDocente(PerfilDTO perfilDTO) throws SQLException;
	public abstract void insertarMaterial(MaterialDTO materialDTO) throws SQLException;
	public abstract void insertarEvaluacionFinal(EvaluacionDTO evaluacionDTO) throws SQLException;
	public abstract void insertarEvaluacionCompetencias(EvaluacionDTO evaluacionDTO) throws SQLException;
	

	//update
	public abstract void guardarPua(PuaDTO puaDTO) throws SQLException;
	public abstract void guardarSubcompetencia(SubcompetenciaDTO subcompetenciaDTO) throws SQLException;
	
	//delete
	public abstract void eliminarSubcompetencia(int idSubcompetencia) throws SQLException;
	public abstract void eliminarTema(int idTema) throws SQLException;
	public abstract void eliminarSubtema(int idSubtema) throws SQLException;
	public abstract void eliminarDocentePua(DocenteDTO docenteDTO) throws SQLException;
	public abstract void eliminarCompetenciaGenerica(GenericaDTO genericaDTO) throws SQLException;
	public abstract void eliminarCompetenciaEspecifica(EspecificaDTO especificaDTO) throws SQLException;
	public abstract void eliminarBibliografiaPua(BibliografiaDTO bibliografiaDTO) throws SQLException;
	public abstract void eliminarActividadAlumno(int idActividad) throws SQLException;
	public abstract void eliminarActividadDocente(int idActividad) throws SQLException;
	public abstract void eliminarAmbiente(AmbienteDTO ambienteDTO) throws SQLException;
	public abstract void eliminarCriterio(int idCriterio) throws SQLException;
	public abstract void eliminarEvidencia(EvidenciaDTO evidenciaDTO) throws SQLException;
	public abstract void eliminarBibliografiaSubcompetencia(BibliografiaDTO bibliografiaDTO) throws SQLException;
	public abstract void eliminarPerfilAcademico(int idPerfil) throws SQLException;
	public abstract void eliminarPerfilProfesional(int idPerfil) throws SQLException;
	public abstract void eliminarPerfilDocente(int idPerfil) throws SQLException;
	public abstract void eliminarMaterial(MaterialDTO materialDTO) throws SQLException;
	public abstract void eliminarEvaluacionCompetencias(EvaluacionDTO evaluacionDTO) throws SQLException;
	public abstract void eliminarEvaluacionFinal(EvaluacionFinalDTO evaluacionFinalDTO) throws SQLException;
	public abstract boolean eliminarSubcompetenciaM(int idSubcompetencia) throws SQLException;
	public abstract void eliminarAllBibliografia(int idPua) throws SQLException;
	public abstract void eliminarCompetenciaDocente(int idPua) throws SQLException;
}
