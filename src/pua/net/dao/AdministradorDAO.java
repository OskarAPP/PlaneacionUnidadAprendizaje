package pua.net.dao;
import java.sql.SQLException;
import java.util.List;

import pua.net.dto.*;
public abstract class AdministradorDAO {
	//read
	public abstract List<FacultadDTO> obtenerListaFacultad() throws SQLException;
	public abstract List<CarreraDTO> obtenerListaCarreras(int idFacultad) throws SQLException;
	public abstract List<CarreraDTO> obtenerListaCarrerasMaterias(int idCarrera) throws SQLException;
	public abstract List<MateriaDTO> obtenerListaArea() throws SQLException;
	public abstract List<MateriaDTO> obtenerListaNucleo() throws SQLException;
	public abstract List<MateriaDTO> obtenerListaTipo() throws SQLException;
	public abstract List<DocenteDTO> obtenerListaTipoDocente() throws SQLException;
	public abstract List<AcademiaDTO> obtenerListaAcademia(int idFacultad) throws SQLException; 
	public abstract List<DocenteDTO> buscarDocente(String nombre, int orden) throws SQLException;
	public abstract List<MateriaDTO> buscarMateria(String nombre, int orden) throws SQLException;
	public abstract List<CarreraDTO> buscarCarrera(String nombre, int orden) throws SQLException;
	public abstract List<AcademiaDTO> buscarAcademia(String nombre, int orden) throws SQLException;
	public abstract List<FacultadDTO> buscarFacultad(String nombre, int orden) throws SQLException;
	public abstract List<FacultadDTO> obtenerListaDocenteFacultad(int idDocente) throws SQLException;
	public abstract List<TipoDTO> obtenerListaTipDocente(int idDocente) throws SQLException;
	public abstract List<MateriaDTO> obtenerListaMateria() throws SQLException;
	public abstract List<GenericaDTO> buscarGenerica(String nombre, int orden) throws SQLException;
	public abstract List<EspecificaDTO> buscarEspecifica(String nombre, int orden) throws SQLException;
	public abstract List<AccesoDTO> obtenerListaAccesos(int idDocente) throws SQLException;
	public abstract DocenteDTO obtenerDocenteById(int idDocente) throws SQLException;
	
	//create
	public abstract boolean insertarMateria(MateriaDTO materiaDTO,int idAcademia) throws SQLException;
	public abstract boolean insertarAcademia(AcademiaDTO academiaDTO) throws SQLException;
	public abstract boolean insertarFacultad(FacultadDTO facultadDTO) throws SQLException;
	public abstract boolean insertarCarrera(CarreraDTO carreraDTO) throws SQLException;
	public abstract boolean insertarGenerica(GenericaDTO genericaDTO) throws SQLException;
	public abstract boolean insertarEspecifica(EspecificaDTO especificaDTO, boolean csv) throws SQLException;
	public abstract boolean insertarDocente(DocenteDTO docenteDTO, int idAcademia, int idCarrera, AccesoDTO accesoDTO) throws SQLException;
	public abstract boolean insertarDocenteCSV(DocenteDTO docenteDTO, AccesoDTO accesoDTO ) throws SQLException;
	public abstract void insertarAcceso(AccesoDTO accesoDTO) throws SQLException;
	public abstract void insertarDocenteFacultad(int idDocente, int idFacultad) throws SQLException;
	public abstract int insertarMateriaCSV(MateriaDTO materiaDTO) throws SQLException;
	public abstract void insertarCarreraMateria(int idCarrera, int idMateria) throws SQLException;
	public abstract void insertarAcademiaMateria(int idAcademia, int idMateria) throws SQLException;
	
	//update
	public abstract int updateCargo(DocenteDTO docenteDTO , int idAcademia, int idCarrera)throws SQLException;
	public abstract int updateDocente(DocenteDTO docenteDTO)throws SQLException;
	public abstract int updateMateria(MateriaDTO materiaDTO)throws SQLException;
	public abstract int updateCarrera(CarreraDTO carreraDTO)throws SQLException;
	public abstract int updateAcademia(AcademiaDTO academiaDTO)throws SQLException;
	public abstract int updateFacultad(FacultadDTO facultadDTO)throws SQLException;
	public abstract int updateGenerica(GenericaDTO genericaDTO)throws SQLException;
	public abstract int updateEspecifica(EspecificaDTO especificaDTO)throws SQLException;
	public abstract int updateAcceso(AccesoDTO accesoDTO)throws SQLException;
	public abstract int cambiarEstadoDocente(AccesoDTO accesoDTO)throws SQLException;
	public abstract int cambiarAcademia(int idMateria, int idAcademia)throws SQLException;
	public abstract boolean cambiarDocenteFacultad(int idDocente, int idFacultad, int idFacultadAntigua) throws SQLException;
	public abstract boolean updatePuesto(int idDocente, int idFacultad, int idCarrera, int idAcademia, int idCargo, int idCargoA) throws SQLException;
	public abstract boolean asignarMateria(int carreraDTO, int idMateriaDTO, int opc)throws SQLException;
	public abstract boolean asignarMateriaDocente(int idDocente, int idMateria, int opc)throws SQLException;
	
	//delete
	public abstract boolean eliminarDocente(int idDocente, int idCargo)throws SQLException;
	public abstract boolean eliminarDocenteFacultad(int idDocente, String idFacultad, int idCargo)throws SQLException;
	public abstract boolean eliminarMateria(int idMateria) throws SQLException;
	public abstract boolean eliminarCarrera(int idCarrera) throws SQLException;
	public abstract boolean eliminarAcademia(int idAcademia) throws SQLException;
	public abstract boolean eliminarFacultad(int idFacultad) throws SQLException;
	public abstract boolean eliminarGenerica(int idGenerica) throws SQLException;
	public abstract boolean eliminarEspecifica(int idEspecifica) throws SQLException;
	public abstract int eliminarAcceso(int idAcceso) throws SQLException;
}