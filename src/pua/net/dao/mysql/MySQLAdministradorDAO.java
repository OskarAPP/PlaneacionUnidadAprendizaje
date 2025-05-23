package pua.net.dao.mysql;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import pua.net.dto.*;
import pua.net.dao.AdministradorDAO;

public class MySQLAdministradorDAO extends AdministradorDAO{
	Connection conn;
	
	public MySQLAdministradorDAO(Connection conn){
		this.conn = conn;
	}
	//create
	private static final String insertarFacultad =
			"insert into FACULTAD values (null,?,null,null)";
	
	private static final String insertarAcademia=
			"INSERT INTO academia VALUES (null,?,null,null,?)";
	
	private static final String insertarCarrera=
			"INSERT INTO carrera VALUES (null,?,?,?,null)";
	
	private static final String insertarCarreaMateria=
			"insert into CARRERA_MATERIA VALUES(?,?)";

	private static final String insertarMateria =
			"insert into MATERIA values (null,?,?,?,?,?,?,?,?,?)";
	
	private static final String insertarDocente =
			"insert into DOCENTE values (null,?,?,?,?,?)";
	
	private static final String insertarAcceso =
			"insert into ACCESO values (null,?,?,?,?,1)";
	
	private static final String insertarDocenteFacultad=
			"insert into docente_facultad values(?,?)";
	
	private static final String insertarTipoDocente=
			"insert into tipo_docente values(null,?,?,?,?,?)";
	
	private static final String insertarAcademiaMateria=
			"INSERT INTO `academia_materia`(`id_materia`, `id_academia`) VALUES (?,?)";
	
	private static final String insertarGenerica =
			"insert into competenciagen values (null,?)";
	
	private static final String insertarEspecifica =
			"insert into competenciaesp values (null,?,?)";
	
	private static final String insertarEspecificaCSV =
			"insert into competenciaesp values (null,?,null)";
	
	//read
	private static final String obtenerDocenteById=
			"SELECT * FROM docente where id_docente=?";
	
	private static final String obtenerListaFacultad =
			"select * from FACULTAD order by facultad asc";

	private static final String obtenerListaCarreras =
			"select * from CARRERA where id_facultad = ?";
	
	private static final String obtenerListaMaterias =
			"select * from MATERIA";
	
	private static final String obtenerListaAccesos =
			"select * from ACCESO where id_docente=?";
	
	private static final String ListaCarreras =
			"select * from CARRERA";
	
	private static final String ListaCarrerasMaterias =
			"select * from CARRERA_MATERIA where id_carrera=?";
	
	private static final String obtenerListaNucleo =
			"select * from NUCLEOM";
	
	private static final String obtenerListaTipo =
			"select * from TIPOM";
	
	private static final String obtenerListaArea =
			"select * from AREAM";
	
	private static final String obtenerListaDocenteFacultad=
			"SELECT df.id_docente, f.facultad FROM docente_facultad as df, facultad as f "+
			"where df.id_docente=? and df.id_facultad=f.id_facultad ORDER BY f.facultad ASC";
	
	private static final String obtenerListaTipoDocente=
			"select * from TIPO";
	
	private static final String obtenerListaAcademia=
			"SELECT a.id_academia, a.nombre_academia, a.id_presidente, a.id_secretario, " + 
			"a.id_facultad, f.facultad FROM academia a, facultad f where f.id_facultad=a.id_facultad"
			+ " and a.id_facultad=?";
	
	private static final String obtenerTipoDocente="select td.idTipo, t.tipo from tipo_docente td, tipo t"+
			" where td.idTipo=t.idTipo and td.id_docente=? order by td.idTipo asc";
	
	private static final String obtenerListaAcademia2=
			"SELECT * FROM academia";
	
	private static final String obtenerAcademiaByMateria=
			"SELECT id_academia FROM academia_materia WHERE id_materia=?";
	
	private static final String validarAcademia=
			"select * from ACADEMIA where nombre_academia=? and id_facultad=?";
	
	private static final String validarDocente=
			"select * from DOCENTE where nombre=? and apellido_paterno=? and apellido_materno=? and correo=?";
	
	private static final String validarMateria=
			"select * from MATERIA where nombre_materia=? and id_nucleo=? and id_tipo=? and id_area=?";
	
	private static final String validarCarrera=
			"select * from CARRERA where nombre_carrera=? and plan_estudios=? and id_facultad=?";
	
	private static final String buscarDocente=
			"SELECT * FROM docente WHERE (nombre LIKE ? or nombre like ? or nombre like ? " + 
			"or apellido_paterno LIKE ? or apellido_paterno like ? or apellido_paterno like ? "+ 
			"or apellido_materno LIKE ? or apellido_materno like ? or apellido_materno like ?) "+
			"ORDER by apellido_paterno ";

	private static final String buscarMateria=
			"SELECT * FROM materia WHERE (nombre_materia LIKE ? or nombre_materia like ? or nombre_materia like ?)" +
			"ORDER by nombre_materia ";
	
	private static final String buscarAcademia=
			"SELECT * FROM academia WHERE (nombre_academia LIKE ? or nombre_academia like ? or nombre_academia like ?)" +
			"ORDER by nombre_academia ";
	
	private static final String buscarFacultad=
			"SELECT * FROM facultad WHERE (facultad LIKE ? or facultad like ? or facultad like ?)" +
			"ORDER by facultad ";
	
	private static final String buscarCarrera=
			"SELECT * FROM carrera  WHERE (nombre_carrera like ? or nombre_carrera like ? or nombre_carrera like ?) ORDER BY id_carrera ";
	
	private static final String listaTipoDocente=
			"SELECT * from cargoDocente where id_docente=? order by idTipo asc";
	
	private static final String buscarGenerica=
			"SELECT * FROM competenciagen WHERE competencia_generica like ? or competencia_generica like ? "
			+ "or competencia_generica like ? order by competencia_generica ";
	
	private static final String buscarEspecifica=
			"SELECT * FROM competenciaesp WHERE competencia_especifica like ? or competencia_especifica like ? "
			+ "or competencia_especifica like ? order by competencia_especifica ";
	
	//update
	private static final String actualizarDirector=
			"update FACULTAD set id_director=? where id_facultad=?";
	
	private static final String actualizarSecretario=
			"update FACULTAD set id_secretario_academico=? where id_facultad=?";
	
	private static final String actualizarFacultad=
			"UPDATE facultad SET facultad=? WHERE id_facultad=?";
	
	private static final String actualizarAcademia=
			"UPDATE academia SET id_facultad=?, nombre_academia=? WHERE id_academia=?";
	
	private static final String actualizarCoordinador=
			"update CARRERA set id_coordinador=? where id_carrera=?";
	
	private static final String actualizarPresidenteAcademia=
			"update ACADEMIA set id_presidente=? where id_academia=?";
	
	private static final String actualizarSecretarioAcademia=
			"update ACADEMIA set id_secretario=? where id_academia=?";
	
	private static final String cambiarEstadoDocente=
			"update ACCESO set estado=? where id_docente=? and id_acceso=?";
	
	private static final String actualizarMateria=""
			+ "UPDATE materia SET id_area=?, id_nucleo=?, id_tipo=?, nombre_materia=?, creditos_totales=?, "
			+ "horas_totales=?, horas_teoricas=?, horas_practicas=?, art57=? WHERE id_materia=?";
	
	private static final String actualizarCarrera=
			"UPDATE carrera SET id_facultad=?,nombre_carrera=?,plan_estudios=? WHERE id_carrera=?";
	
	private static final String actualizarDocente=
			"UPDATE docente SET prefijo=?,nombre=?,apellido_paterno=?,apellido_materno=?,correo=? WHERE id_docente=?";
	
	private static final String actualizarAcceso=
			"UPDATE acceso SET pass=?, rol=?, WHERE id_acceso=?";
	
	private static final String cambiarCorreo=
			"UPDATE acceso SET correo=? WHERE id_docente=?";
	
	private static final String cambiarAcademia=
			"UPDATE academia_materia SET id_academia=? WHERE id_materia=?";
	
	private static final String actualizarEspecifica=
			"UPDATE competenciaesp SET competencia_especifica = ?, id_carrera = ? WHERE id_especifica = ?";
	
	private static final String actualizarGenerica=
			"UPDATE competenciagen SET competencia_generica = ? WHERE id_generica = ?";
	
	private static final String actualizarRol=
			"UPDATE acceso SET rol=? WHERE id_acceso=?";
	
	//delete
	private static final String deleteAcceso=
			"delete from acceso where id_acceso=?";

	
	//listas y consultas
	@Override
	public DocenteDTO obtenerDocenteById(int idDocente) throws SQLException {
		DocenteDTO docenteDTO=new DocenteDTO();
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		sentencia=conn.prepareStatement(obtenerDocenteById);
		sentencia.setInt(1, idDocente);
		rs=sentencia.executeQuery();
		
		if(rs.next()) {
			docenteDTO.setIdDocente(rs.getInt("id_docente"));
			docenteDTO.setNombre(rs.getString("nombre"));
			docenteDTO.setApellidoPaterno(rs.getString("apellido_paterno"));
			docenteDTO.setApellidoMaterno(rs.getString("apellido_materno"));
			docenteDTO.setCorreo(rs.getString("correo"));
			docenteDTO.setPrefijo(rs.getString("prefijo"));
			docenteDTO.setListaFacultad(obtenerListaDocenteFacultad(docenteDTO.getIdDocente()));
			docenteDTO.setListaTipo(obtenerListaTipDocente(docenteDTO.getIdDocente()));
			docenteDTO.setListaAcceso(obtenerListaAccesos(docenteDTO.getIdDocente()));
		}
		
		sentencia = conn.prepareStatement(obtenerTipoDocente);
		sentencia.setInt(1, docenteDTO.getIdDocente());

		rs = sentencia.executeQuery();
		
		if(rs.next()){
			docenteDTO.setIdTipo(rs.getInt(1));
			docenteDTO.setTipo(rs.getString(2));
		}
		
		sentencia.close();
		rs.close();
		return docenteDTO;
	}
	@Override
	public List<FacultadDTO> obtenerListaFacultad() throws SQLException {
		List<FacultadDTO> listaFacultad = new ArrayList<FacultadDTO>();
		FacultadDTO facultadDTO;
		Statement sentencia = conn.createStatement();
		ResultSet rs = sentencia.executeQuery(obtenerListaFacultad);

		while(rs.next()){
			facultadDTO = new FacultadDTO();
			facultadDTO.setIdFacultad(rs.getInt(1));
			facultadDTO.setFacultad(rs.getString(2));
			facultadDTO.setIdDirector(rs.getInt(3));
			facultadDTO.setIdSecretarioAcademico(rs.getInt(4));
			listaFacultad.add(facultadDTO);
		}
		rs.close();
		sentencia.close();
		return listaFacultad;
	}

	@Override
	public List<CarreraDTO> obtenerListaCarreras(int idFacultad) throws SQLException {
		List<CarreraDTO> listaCarreras = new ArrayList<CarreraDTO>();
		CarreraDTO carreraDTO;
		PreparedStatement sentencia = null;
		
		if(idFacultad==-1) 
			sentencia = conn.prepareStatement(ListaCarreras);
		else {
			sentencia = conn.prepareStatement(obtenerListaCarreras);
			sentencia.setInt(1, idFacultad);
		}
		ResultSet rs = sentencia.executeQuery();

		while (rs.next()) {
			carreraDTO = new CarreraDTO();
			carreraDTO.setIdCarrera(rs.getInt(1));
			carreraDTO.setIdFacultad(rs.getInt(2));
			carreraDTO.setNombreCarrera(rs.getString(3));
			carreraDTO.setPlanEstudios(rs.getInt(4));
			carreraDTO.setIdCoordinador(rs.getInt(5));
			listaCarreras.add(carreraDTO);
		}

		rs.close();
		sentencia.close();
		return listaCarreras;
	}
	
	@Override
	public List<CarreraDTO> obtenerListaCarrerasMaterias(int idCarrera) throws SQLException {
		List<CarreraDTO> listaCarreras = new ArrayList<CarreraDTO>();
		CarreraDTO carreraDTO;
		PreparedStatement sentencia = null;
		
		sentencia = conn.prepareStatement(ListaCarrerasMaterias);
		sentencia.setInt(1, idCarrera);
		ResultSet rs = sentencia.executeQuery();

		while (rs.next()) {
			carreraDTO = new CarreraDTO();
			carreraDTO.setIdCarrera(rs.getInt(1));
			carreraDTO.setIdFacultad(rs.getInt(2));
			listaCarreras.add(carreraDTO);
		}

		rs.close();
		sentencia.close();
		return listaCarreras;
	}
	
	@Override
	public List<MateriaDTO> obtenerListaMateria() throws SQLException {
		List<MateriaDTO> listaMateria = new ArrayList<MateriaDTO>();
		MateriaDTO materiaDTO;
		Statement sentencia = conn.createStatement();
		ResultSet rs = sentencia.executeQuery(obtenerListaMaterias);

		while(rs.next()){
			materiaDTO = new MateriaDTO();
			materiaDTO.setIdMateria(rs.getInt(1));
			materiaDTO.setIdArea(rs.getInt(2));
			materiaDTO.setIdNucleo(rs.getInt(3));
			materiaDTO.setIdTipo(rs.getInt(4));
			materiaDTO.setNombreMateria(rs.getString(5));
			materiaDTO.setCreditosTotales(rs.getInt(6));
			materiaDTO.setHorasTotales(rs.getInt(7));
			materiaDTO.setHorasTeoricas(rs.getInt(8));
			materiaDTO.setHorasPracticas(rs.getInt(9));
			materiaDTO.setArt57(rs.getString(10));
			listaMateria.add(materiaDTO);
		}
		rs.close();
		sentencia.close();
		return listaMateria;
	}
	
	private int obtenerAcademiaByMateria(int idMateria)throws SQLException {
		PreparedStatement sentencia = conn.prepareStatement(obtenerAcademiaByMateria);
		sentencia.setInt(1, idMateria);
		ResultSet rs = sentencia.executeQuery();
		int idAcademia=0;

		while(rs.next()){
			idAcademia=rs.getInt(1);
		}
		sentencia.close();
		rs.close();
		return idAcademia;
	}
	
	@Override
	public List<MateriaDTO> buscarMateria(String nombre, int orden) throws SQLException {
		List<MateriaDTO> listaMateria = new ArrayList<MateriaDTO>();
		MateriaDTO materiaDTO;
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		sentencia=conn.prepareStatement(orden==1?buscarMateria+"ASC"
				:buscarMateria+"DESC");
		sentencia.setString(1, "%"+nombre);
		sentencia.setString(2, "%"+nombre+"%");
		sentencia.setString(3, nombre+"%");
		rs=sentencia.executeQuery();
		
		while(rs.next()){
			materiaDTO = new MateriaDTO();
			materiaDTO.setIdMateria(rs.getInt(1));
			materiaDTO.setIdArea(rs.getInt(2));
			materiaDTO.setIdNucleo(rs.getInt(3));
			materiaDTO.setIdTipo(rs.getInt(4));
			materiaDTO.setNombreMateria(rs.getString(5));
			materiaDTO.setCreditosTotales(rs.getInt(6));
			materiaDTO.setHorasTotales(rs.getInt(7));
			materiaDTO.setHorasTeoricas(rs.getInt(8));
			materiaDTO.setHorasPracticas(rs.getInt(9));
			materiaDTO.setArt57(rs.getString(10).toLowerCase());
			materiaDTO.setIdCarrera(obtenerAcademiaByMateria(materiaDTO.getIdMateria()));
			listaMateria.add(materiaDTO);
		}
		rs.close();
		sentencia.close();
		return listaMateria;
	}
	
	@Override
	public List<AcademiaDTO> buscarAcademia(String nombre, int orden) throws SQLException {
		List<AcademiaDTO> listaAcademia = new ArrayList<AcademiaDTO>();
		AcademiaDTO academiaDTO;
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		sentencia=conn.prepareStatement(orden==1?buscarAcademia+"ASC"
				:buscarAcademia+"DESC");
		sentencia.setString(1, "%"+nombre);
		sentencia.setString(2, "%"+nombre+"%");
		sentencia.setString(3, nombre+"%");
		rs=sentencia.executeQuery();
		
		while(rs.next()){
			academiaDTO = new AcademiaDTO();
			academiaDTO.setIdAcademia(rs.getInt(1));
			academiaDTO.setNombreAcademia(rs.getString(2));
			academiaDTO.setIdFacultad(rs.getInt(5));
			listaAcademia.add(academiaDTO);
		}
		rs.close();
		sentencia.close();
		return listaAcademia;
	}
	
	@Override
	public List<FacultadDTO> buscarFacultad(String nombre, int orden) throws SQLException {
		List<FacultadDTO> listaFacultad = new ArrayList<FacultadDTO>();
		FacultadDTO facultadDTO;
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		sentencia=conn.prepareStatement(orden==1?buscarFacultad+"ASC"
				:buscarFacultad+"DESC");
		sentencia.setString(1, "%"+nombre);
		sentencia.setString(2, "%"+nombre+"%");
		sentencia.setString(3, nombre+"%");
		rs=sentencia.executeQuery();
		
		while(rs.next()){
			facultadDTO = new FacultadDTO();
			facultadDTO.setIdFacultad(rs.getInt(1));
			facultadDTO.setFacultad(rs.getString(2));
			listaFacultad.add(facultadDTO);
		}
		rs.close();
		sentencia.close();
		return listaFacultad;
	}
	
	@Override
	public List<CarreraDTO> buscarCarrera(String nombre, int orden) throws SQLException {
		List<CarreraDTO> listaCarrera = new ArrayList<CarreraDTO>();
		CarreraDTO carreraDTO;
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		sentencia=conn.prepareStatement(orden==1?buscarCarrera+"ASC"
				:buscarCarrera+"DESC");
		sentencia.setString(1, "%"+nombre);
		sentencia.setString(2, "%"+nombre+"%");
		sentencia.setString(3, nombre+"%");
		rs=sentencia.executeQuery();
		while(rs.next()){
			carreraDTO = new CarreraDTO();
			carreraDTO.setIdCarrera(rs.getInt(1));
			carreraDTO.setIdFacultad(rs.getInt(2));
			carreraDTO.setNombreCarrera(rs.getString(3));
			carreraDTO.setPlanEstudios(rs.getInt(4));
			listaCarrera.add(carreraDTO);
		}
		rs.close();
		sentencia.close();
		return listaCarrera;
	}

	@Override
	public List<MateriaDTO> obtenerListaArea() throws SQLException {
		List<MateriaDTO> listaMateria = new ArrayList<MateriaDTO>();
		PreparedStatement sentencia=conn.prepareStatement(obtenerListaArea);
		ResultSet rs=sentencia.executeQuery();
		while(rs.next()){
			MateriaDTO materiaDTO=new MateriaDTO();
			materiaDTO.setIdArea(rs.getInt("id_area"));
			materiaDTO.setArea(rs.getString("area"));
			listaMateria.add(materiaDTO);
		}
		sentencia.close();
		rs.close();
		return listaMateria;
	}

	@Override
	public List<MateriaDTO> obtenerListaNucleo() throws SQLException {
		List<MateriaDTO> listaMateria = new ArrayList<MateriaDTO>();
		PreparedStatement sentencia=conn.prepareStatement(obtenerListaNucleo);
		ResultSet rs=sentencia.executeQuery();
		while(rs.next()){
			MateriaDTO materiaDTO=new MateriaDTO();
			materiaDTO.setIdNucleo(rs.getInt("id_nucleo"));
			materiaDTO.setNucleo(rs.getString("nucleo"));
			listaMateria.add(materiaDTO);
		}
		sentencia.close();
		rs.close();
		return listaMateria;
	}

	@Override
	public List<MateriaDTO> obtenerListaTipo() throws SQLException {
		List<MateriaDTO> listaMateria = new ArrayList<MateriaDTO>();
		PreparedStatement sentencia=conn.prepareStatement(obtenerListaTipo);
		ResultSet rs=sentencia.executeQuery();
		while(rs.next()){
			MateriaDTO materiaDTO=new MateriaDTO();
			materiaDTO.setIdTipo(rs.getInt("id_tipo"));
			materiaDTO.setTipo(rs.getString("tipo"));
			listaMateria.add(materiaDTO);
		}
		sentencia.close();
		rs.close();
		return listaMateria;
	}
	
	@Override
	public List<DocenteDTO> obtenerListaTipoDocente() throws SQLException {
		List<DocenteDTO> listaDocente = new ArrayList<DocenteDTO>();
		PreparedStatement sentencia=conn.prepareStatement(obtenerListaTipoDocente);
		ResultSet rs=sentencia.executeQuery();
		while(rs.next()){
			DocenteDTO docenteDTO=new DocenteDTO();
			docenteDTO.setIdTipo(rs.getInt("idTipo"));
			docenteDTO.setTipo(rs.getString("tipo"));
			listaDocente.add(docenteDTO);
		}
		sentencia.close();
		rs.close();
		return listaDocente;
	}
	
	@Override
	public List<AcademiaDTO> obtenerListaAcademia(int idFacultad) throws SQLException {
		List<AcademiaDTO> listaAcademia = new ArrayList<AcademiaDTO>();
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		if(idFacultad>0) {
			sentencia=conn.prepareStatement(obtenerListaAcademia);
			sentencia.setInt(1, idFacultad);
		} else {
			sentencia=conn.prepareStatement(obtenerListaAcademia2);
		}
		
		rs=sentencia.executeQuery();
		while(rs.next()){
			AcademiaDTO academiaDTO=new AcademiaDTO();
			academiaDTO.setIdAcademia(rs.getInt("id_academia"));
			academiaDTO.setNombreAcademia(rs.getString("nombre_academia"));
			academiaDTO.setIdPresidente(rs.getInt("id_presidente"));
			academiaDTO.setIdSecretario(rs.getInt("id_secretario"));
			if(idFacultad>0) {
				academiaDTO.setIdFacultad(rs.getInt("id_facultad"));
				academiaDTO.setFacultad(rs.getString("facultad"));
			}
			listaAcademia.add(academiaDTO);
		}
		sentencia.close();
		rs.close();
		return listaAcademia;
	}
	
	@Override
	public List<DocenteDTO> buscarDocente(String nombre, int orden) throws SQLException {
		List<DocenteDTO> listaDocente=new ArrayList<DocenteDTO>();
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		sentencia=conn.prepareStatement(orden==1?buscarDocente+"ASC"
				:buscarDocente+"DESC");
		sentencia.setString(1, "%"+nombre);
		sentencia.setString(2, "%"+nombre+"%");
		sentencia.setString(3, nombre+"%");
		sentencia.setString(4, "%"+nombre);
		sentencia.setString(5, "%"+nombre+"%");
		sentencia.setString(6, nombre+"%");
		sentencia.setString(7, "%"+nombre);
		sentencia.setString(8, "%"+nombre+"%");
		sentencia.setString(9, nombre+"%");
		rs=sentencia.executeQuery();
		
		while(rs.next()) {
			DocenteDTO docenteDTO=new DocenteDTO();
			docenteDTO.setIdDocente(rs.getInt("id_docente"));
			docenteDTO.setNombre(rs.getString("nombre"));
			docenteDTO.setApellidoPaterno(rs.getString("apellido_paterno"));
			docenteDTO.setApellidoMaterno(rs.getString("apellido_materno"));
			docenteDTO.setCorreo(rs.getString("correo"));
			docenteDTO.setPrefijo(rs.getString("prefijo"));
			docenteDTO.setListaFacultad(obtenerListaDocenteFacultad(docenteDTO.getIdDocente()));
			docenteDTO.setListaTipo(obtenerListaTipDocente(docenteDTO.getIdDocente()));
			docenteDTO.setListaAcceso(obtenerListaAccesos(docenteDTO.getIdDocente()));
			listaDocente.add(docenteDTO);
		}
		sentencia.close();
		rs.close();
		return listaDocente;
	}
	
	@Override
	public List<AccesoDTO> obtenerListaAccesos(int idDocente) throws SQLException {
		List<AccesoDTO> listAcceso=new ArrayList<AccesoDTO>();
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		sentencia=conn.prepareStatement(obtenerListaAccesos);
		sentencia.setInt(1, idDocente);

		rs=sentencia.executeQuery();
		
		while(rs.next()) {
			AccesoDTO accesoDTO=new AccesoDTO();
			accesoDTO.setIdAcceso(rs.getInt(1));
			accesoDTO.setIdDocente(idDocente);
			accesoDTO.setCorreo(rs.getString(3));
			accesoDTO.setPass(rs.getString(4));
			accesoDTO.setRol(rs.getString(5));
			accesoDTO.setEstado(rs.getInt(6));
			listAcceso.add(accesoDTO);
		}
		sentencia.close();
		rs.close();
		return listAcceso;
	}

	@Override
	public List<FacultadDTO> obtenerListaDocenteFacultad(int idDocente) throws SQLException {
		List<FacultadDTO> listaFacultad = new ArrayList<FacultadDTO>();
		PreparedStatement sentencia = null;
		sentencia=conn.prepareStatement(obtenerListaDocenteFacultad);
		sentencia.setInt(1, idDocente);
		ResultSet rs = sentencia.executeQuery();
		while(rs.next()){
			FacultadDTO facultadDTO = new FacultadDTO();
			facultadDTO.setIdFacultad(rs.getInt(1));
			facultadDTO.setFacultad(rs.getString(2));

			listaFacultad.add(facultadDTO);
		}
		rs.close();
		sentencia.close();
		return listaFacultad;
	}
	
	@Override
	public List<TipoDTO> obtenerListaTipDocente(int idDocente) throws SQLException {
		List<TipoDTO> listaTipo = new ArrayList<TipoDTO>();
		PreparedStatement sentencia=conn.prepareStatement(listaTipoDocente);
		sentencia.setInt(1, idDocente);
		ResultSet rs = sentencia.executeQuery();
		while(rs.next()){
			TipoDTO tipoDTO = new TipoDTO();
			tipoDTO.setFacultad(rs.getString("facultad"));
			tipoDTO.setIdFacultad(rs.getInt("id_facultad"));
			tipoDTO.setIdTipo(rs.getInt("idTipo"));
			tipoDTO.setTipo(rs.getString("tipo"));
			listaTipo.add(tipoDTO);
		}
		
		rs.close();
		sentencia.close();
		return listaTipo;
	}
	
	@Override
	public List<GenericaDTO> buscarGenerica(String nombre, int orden) throws SQLException {
		List<GenericaDTO> listaGenerica = new ArrayList<GenericaDTO>();
		GenericaDTO genericaDTO;
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		sentencia=conn.prepareStatement(orden==1?buscarGenerica+"ASC"
				:buscarGenerica+"DESC");
		sentencia.setString(1, "%"+nombre);
		sentencia.setString(2, "%"+nombre+"%");
		sentencia.setString(3, nombre+"%");
		rs=sentencia.executeQuery();
		while(rs.next()){
			genericaDTO = new GenericaDTO();
			genericaDTO.setIdGenerica(rs.getInt(1));
			genericaDTO.setNombreGenerica(rs.getString(2));
			listaGenerica.add(genericaDTO);
		}
		rs.close();
		sentencia.close();
		return listaGenerica;
	}
	
	@Override
	public List<EspecificaDTO> buscarEspecifica(String nombre, int orden) throws SQLException {
		List<EspecificaDTO> listaEspecifica = new ArrayList<EspecificaDTO>();
		EspecificaDTO especificaDTO;
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		sentencia=conn.prepareStatement(orden==1?buscarEspecifica+"ASC"
				:buscarEspecifica+"DESC");
		sentencia.setString(1, "%"+nombre);
		sentencia.setString(2, "%"+nombre+"%");
		sentencia.setString(3, nombre+"%");
		rs=sentencia.executeQuery();
		while(rs.next()){
			especificaDTO = new EspecificaDTO();
			especificaDTO.setIdEspecifica(rs.getInt(1));
			especificaDTO.setPerfilEspecifica(rs.getString(2));
			especificaDTO.setIdCarrera(rs.getInt(3));
			listaEspecifica.add(especificaDTO);
		}
		rs.close();
		sentencia.close();
		return listaEspecifica;
	}
	
	//Registro
	@Override
	public boolean insertarMateria(MateriaDTO materiaDTO, int idAcademia) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		int idMateria = 0;
		if(validarDatosRepetidos(null,materiaDTO)==false){
			sentencia = conn.prepareStatement(insertarMateria, PreparedStatement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, materiaDTO.getIdArea());
			sentencia.setInt(2, materiaDTO.getIdNucleo());
			sentencia.setInt(3, materiaDTO.getIdTipo());
			sentencia.setString(4, materiaDTO.getNombreMateria());
			sentencia.setInt(5, materiaDTO.getCreditosTotales());
			sentencia.setInt(6, materiaDTO.getHorasTotales());
			sentencia.setInt(7, materiaDTO.getHorasTeoricas());
			sentencia.setInt(8, materiaDTO.getHorasPracticas());
			sentencia.setString(9, materiaDTO.getArt57());
			sentencia.executeUpdate();

			rs = sentencia.getGeneratedKeys();

			if (rs.next()) {
				idMateria = rs.getInt(1);
			}

			sentencia=conn.prepareStatement(insertarCarreaMateria);
			sentencia.setInt(1, materiaDTO.getIdCarrera());
			sentencia.setInt(2, idMateria);
			sentencia.executeUpdate();
			sentencia.close();
			
			insertarAcademiaMateria(idAcademia, idMateria);
			sentencia.close();
			rs.close();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean insertarGenerica(GenericaDTO genericaDTO) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		int idMateria = 0;
		sentencia = conn.prepareStatement(insertarGenerica, PreparedStatement.RETURN_GENERATED_KEYS);
		sentencia.setString(1, genericaDTO.getNombreGenerica());
		int i=sentencia.executeUpdate();

		rs = sentencia.getGeneratedKeys();

		if (rs.next()) {
			idMateria = rs.getInt(1);
		}
		sentencia.close();
		return i==0?true:false;
	}
	
	@Override
	public boolean insertarEspecifica(EspecificaDTO especificaDTO, boolean csv) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		int idMateria = 0;
		if(csv==true) {
			sentencia = conn.prepareStatement(insertarEspecificaCSV, PreparedStatement.RETURN_GENERATED_KEYS);
			sentencia.setString(1, especificaDTO.getPerfilEspecifica());
		} else {
			sentencia = conn.prepareStatement(insertarEspecifica, PreparedStatement.RETURN_GENERATED_KEYS);
			sentencia.setString(1, especificaDTO.getPerfilEspecifica());
			sentencia.setInt(2, especificaDTO.getIdCarrera());
		}
		
		int i=sentencia.executeUpdate();

		rs = sentencia.getGeneratedKeys();

		if (rs.next()) {
			idMateria = rs.getInt(1);
		}
		sentencia.close();
		return i==0?true:false;
	}
	
	@Override
	public boolean insertarAcademia(AcademiaDTO academiaDTO) throws SQLException {
		PreparedStatement sentencia;
		if(validarAcademiaFacultad(academiaDTO,null,null)==false){
			sentencia = conn.prepareStatement(insertarAcademia, PreparedStatement.RETURN_GENERATED_KEYS);
			sentencia.setString(1, academiaDTO.getNombreAcademia());
			sentencia.setInt(2, academiaDTO.getIdFacultad());
			sentencia.executeUpdate();
			sentencia.close();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean insertarFacultad(FacultadDTO facultadDTO) throws SQLException {
		PreparedStatement sentencia;
		if(validarAcademiaFacultad(null,facultadDTO,null)==false){
			sentencia = conn.prepareStatement(insertarFacultad, PreparedStatement.RETURN_GENERATED_KEYS);
			sentencia.setString(1, facultadDTO.getFacultad());
			sentencia.executeUpdate();
			sentencia.close();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean insertarCarrera(CarreraDTO carreraDTO) throws SQLException {
		PreparedStatement sentencia;
		if(validarAcademiaFacultad(null,null,carreraDTO)==false){
			sentencia = conn.prepareStatement(insertarCarrera, PreparedStatement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, carreraDTO.getIdFacultad());
			sentencia.setString(2, carreraDTO.getNombreCarrera());
			sentencia.setInt(3, carreraDTO.getPlanEstudios());
			sentencia.executeUpdate();
			sentencia.close();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int insertarMateriaCSV(MateriaDTO materiaDTO) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		int idMateria = 0;
		if(validarDatosRepetidos(null,materiaDTO)==false){
			sentencia = conn.prepareStatement(insertarMateria, PreparedStatement.RETURN_GENERATED_KEYS);

			sentencia.setInt(1, materiaDTO.getIdArea());
			sentencia.setInt(2, materiaDTO.getIdNucleo());
			sentencia.setInt(3, materiaDTO.getIdTipo());
			sentencia.setString(4, materiaDTO.getNombreMateria());
			sentencia.setInt(5, materiaDTO.getCreditosTotales());
			sentencia.setInt(6, materiaDTO.getHorasTotales());
			sentencia.setInt(7, materiaDTO.getHorasTeoricas());
			sentencia.setInt(8, materiaDTO.getHorasPracticas());
			sentencia.setString(9, materiaDTO.getArt57());

			sentencia.executeUpdate();

			rs = sentencia.getGeneratedKeys();

			if (rs.next()) {
				idMateria = rs.getInt(1);
			}
			
			sentencia.close();
			rs.close();
			return idMateria;
		} else {
			return 0;
		}
		
	}
	
	@Override
	public void insertarCarreraMateria(int idCarrera, int idMateria) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
			sentencia=conn.prepareStatement(insertarCarreaMateria);
			sentencia.setInt(1, idCarrera);
			sentencia.setInt(2, idMateria);
			sentencia.executeUpdate();
			sentencia.close();
	}
	
	@Override
	public void insertarAcademiaMateria(int idAcademia, int idMateria) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
			sentencia=conn.prepareStatement(insertarAcademiaMateria);
			sentencia.setInt(1, idMateria);
			sentencia.setInt(2, idAcademia);
			sentencia.executeUpdate();
			sentencia.close();
	}
	
	@Override
	public boolean insertarDocente(DocenteDTO docenteDTO,int idAcademia, int idCarrera, AccesoDTO accesoDTO) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		int idDocente = 0;
		if(validarDatosRepetidos(docenteDTO,null)==false){
			sentencia = conn.prepareStatement(insertarDocente, PreparedStatement.RETURN_GENERATED_KEYS);

			sentencia.setString(1, docenteDTO.getPrefijo());
			sentencia.setString(2, docenteDTO.getNombre());
			sentencia.setString(3, docenteDTO.getApellidoPaterno());
			sentencia.setString(4, docenteDTO.getApellidoMaterno());
			sentencia.setString(5, docenteDTO.getCorreo());
			sentencia.executeUpdate();

			rs = sentencia.getGeneratedKeys();

			if (rs.next()) {
				idDocente = rs.getInt(1);
			}
			
			accesoDTO.setIdDocente(idDocente);
			accesoDTO.setCorreo(docenteDTO.getCorreo());
			insertarAcceso(accesoDTO);
			if(accesoDTO.getRol().equals("administrador")) {
				accesoDTO.setRol("docente");
				insertarAcceso(accesoDTO);
			}
			
			docenteDTO.setIdDocente(idDocente);
			sentencia=conn.prepareStatement(insertarDocenteFacultad);
			sentencia.setInt(1, idDocente);
			sentencia.setInt(2, docenteDTO.getIdFacultad());
			sentencia.executeUpdate();
			
			if(docenteDTO.getIdTipo()!=6){
				updateCargo(docenteDTO, idAcademia,idCarrera);
			}
			
			sentencia=conn.prepareStatement(insertarTipoDocente);
			sentencia.setInt(1, docenteDTO.getIdTipo());
			sentencia.setInt(2, idDocente);
			sentencia.setInt(3, docenteDTO.getIdFacultad());
			if(docenteDTO.getIdTipo()==3){
				sentencia.setInt(4, idCarrera);
				sentencia.setInt(5, 0);
			} else if(docenteDTO.getIdTipo()==4 || docenteDTO.getIdTipo()==5) {
				sentencia.setInt(4, 0);
				sentencia.setInt(5, idAcademia);
			} else {
				sentencia.setInt(4, 0);
				sentencia.setInt(5, 0);
			}
			sentencia.executeUpdate();
			sentencia.close();
			rs.close();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean insertarDocenteCSV(DocenteDTO docenteDTO,AccesoDTO accesoDTO) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		int idDocente = 0;
		if(validarDatosRepetidos(docenteDTO,null)==false){
			sentencia = conn.prepareStatement(insertarDocente, PreparedStatement.RETURN_GENERATED_KEYS);

			sentencia.setString(1, docenteDTO.getPrefijo());
			sentencia.setString(2, docenteDTO.getNombre());
			sentencia.setString(3, docenteDTO.getApellidoPaterno());
			sentencia.setString(4, docenteDTO.getApellidoMaterno());
			sentencia.setString(5, docenteDTO.getCorreo());
			sentencia.executeUpdate();

			rs = sentencia.getGeneratedKeys();

			if (rs.next()) {
				idDocente = rs.getInt(1);
			}
			accesoDTO.setIdDocente(idDocente);
			insertarAcceso(accesoDTO);
			sentencia.close();
			rs.close();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void insertarAcceso(AccesoDTO accesoDTO) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		sentencia = conn.prepareStatement(insertarAcceso, PreparedStatement.RETURN_GENERATED_KEYS);

		sentencia.setInt(1, accesoDTO.getIdDocente());
		sentencia.setString(2, accesoDTO.getCorreo());
		sentencia.setString(3, accesoDTO.getPass());
		sentencia.setString(4, accesoDTO.getRol());
		sentencia.executeUpdate();

		rs = sentencia.getGeneratedKeys();
		sentencia.close();
	}
	
	@Override
	public void insertarDocenteFacultad(int idDocente, int idFacultad) throws SQLException {
	    PreparedStatement checkStmt = null;
	    PreparedStatement insertDocenteFacultadStmt = null;
	    PreparedStatement insertTipoDocenteStmt = null;
	    ResultSet rs = null;

	    try {
	        conn.setAutoCommit(false); 
	        String checkSql = "SELECT 1 FROM docente_facultad WHERE docente_id = ? AND facultad_id = ?";
	        checkStmt = conn.prepareStatement(checkSql);
	        checkStmt.setInt(1, idDocente);
	        checkStmt.setInt(2, idFacultad);
	        rs = checkStmt.executeQuery();

	        if (!rs.next()) { 
	            insertDocenteFacultadStmt = conn.prepareStatement(insertarDocenteFacultad);
	            insertDocenteFacultadStmt.setInt(1, idDocente);
	            insertDocenteFacultadStmt.setInt(2, idFacultad);
	            insertDocenteFacultadStmt.executeUpdate();

	            
	            insertTipoDocenteStmt = conn.prepareStatement(insertarTipoDocente);
	            insertTipoDocenteStmt.setInt(1, 6); 
	            insertTipoDocenteStmt.setInt(2, idDocente);
	            insertTipoDocenteStmt.setInt(3, idFacultad);
	            insertTipoDocenteStmt.setInt(4, 0);
	            insertTipoDocenteStmt.setInt(5, 0);
	            insertTipoDocenteStmt.executeUpdate();

	            conn.commit(); 
	        } else {
	            throw new SQLIntegrityConstraintViolationException(
	                "El docente ya está asignado a esta facultad (Docente ID: " + idDocente + ", Facultad ID: " + idFacultad + ")"
	            );
	        }
	    } catch (SQLException e) {
	        conn.rollback(); 
	        throw e;
	    } finally {
	        
	        if (rs != null) rs.close();
	        if (checkStmt != null) checkStmt.close();
	        if (insertDocenteFacultadStmt != null) insertDocenteFacultadStmt.close();
	        if (insertTipoDocenteStmt != null) insertTipoDocenteStmt.close();
	        conn.setAutoCommit(true); 
	    }
	}	
	
//@Override	
//	public void insertarDocenteFacultad(int idDocente, int idFacultad) throws SQLException {
//		PreparedStatement sentencia;
//		sentencia=conn.prepareStatement(insertarDocenteFacultad);
//		sentencia.setInt(1, idDocente);
//		sentencia.setInt(2, idFacultad);
//		sentencia.executeUpdate();
//		
//		sentencia=conn.prepareStatement(insertarTipoDocente);
//		sentencia.setInt(1, 6);
//		sentencia.setInt(2, idDocente);
//		sentencia.setInt(3, idFacultad);
//		sentencia.setInt(4, 0);
//		sentencia.setInt(5, 0);
//		sentencia.executeUpdate();
//		sentencia.close();
//	}
	
	//Actualización
	@Override
	public int updateCargo(DocenteDTO docenteDTO , int idAcademia, int idCarrera)throws SQLException{
		PreparedStatement sentencia = null;
		switch(docenteDTO.getIdTipo()){
			case 1:
				sentencia=conn.prepareStatement(actualizarDirector);
				sentencia.setInt(1, docenteDTO.getIdDocente());
				sentencia.setInt(2, docenteDTO.getIdFacultad());
				break;
			case 2:
				sentencia=conn.prepareStatement(actualizarSecretario);
				sentencia.setInt(1, docenteDTO.getIdDocente());
				sentencia.setInt(2, docenteDTO.getIdFacultad());
				break;
			case 3:
				sentencia=conn.prepareStatement(actualizarCoordinador);
				sentencia.setInt(1, docenteDTO.getIdDocente());
				sentencia.setInt(2, idCarrera);
				break;
			case 4:
				sentencia=conn.prepareStatement(actualizarPresidenteAcademia);
				sentencia.setInt(1, docenteDTO.getIdDocente());
				sentencia.setInt(2, idAcademia);
				break;
			case 5:
				sentencia=conn.prepareStatement(actualizarSecretarioAcademia);
				sentencia.setInt(1, docenteDTO.getIdDocente());
				sentencia.setInt(2, idAcademia);
				break;
			default:
				break;
		}
		int accion=sentencia.executeUpdate();
		sentencia.close();
		return accion;
	}
	
	@Override
	public int updateMateria(MateriaDTO materiaDTO)throws SQLException{
		PreparedStatement sentencia = null;
		sentencia=conn.prepareStatement(actualizarMateria);
		sentencia.setInt(1, materiaDTO.getIdArea());
		sentencia.setInt(2, materiaDTO.getIdNucleo());
		sentencia.setInt(3, materiaDTO.getIdTipo());
		sentencia.setString(4, materiaDTO.getNombreMateria());
		sentencia.setInt(5, materiaDTO.getCreditosTotales());
		sentencia.setInt(6, materiaDTO.getHorasTotales());
		sentencia.setInt(7, materiaDTO.getHorasTeoricas());
		sentencia.setInt(8, materiaDTO.getHorasPracticas());
		sentencia.setString(9, materiaDTO.getArt57());
		sentencia.setInt(10, materiaDTO.getIdMateria());
		int accion=sentencia.executeUpdate();
		sentencia.close();
		return accion;
	}
	
	
	@Override
	public int updateCarrera(CarreraDTO carreraDTO)throws SQLException{
		PreparedStatement sentencia = null;
		sentencia=conn.prepareStatement(actualizarCarrera);
		sentencia.setInt(1, carreraDTO.getIdFacultad());
		sentencia.setString(2, carreraDTO.getNombreCarrera());
		sentencia.setInt(3, carreraDTO.getPlanEstudios());
		sentencia.setInt(4, carreraDTO.getIdCarrera());
		int accion=sentencia.executeUpdate();
		sentencia.close();
		return accion;
	}
	
	@Override
	public int updateGenerica(GenericaDTO genericaDTO)throws SQLException{
		PreparedStatement sentencia = null;
		sentencia=conn.prepareStatement(actualizarGenerica);
		sentencia.setString(1, genericaDTO.getNombreGenerica());
		sentencia.setInt(2, genericaDTO.getIdGenerica());
		int accion=sentencia.executeUpdate();
		sentencia.close();
		return accion;
	}
	
	@Override
	public int updateEspecifica(EspecificaDTO especificaDTO)throws SQLException{
		PreparedStatement sentencia = null;
		sentencia=conn.prepareStatement(actualizarEspecifica);
		sentencia.setString(1, especificaDTO.getPerfilEspecifica());
		sentencia.setInt(2, especificaDTO.getIdCarrera());
		sentencia.setInt(3, especificaDTO.getIdEspecifica());
		int accion=sentencia.executeUpdate();
		sentencia.close();
		return accion;
	}
	
	@Override
	public int updateAcademia(AcademiaDTO academiaDTO)throws SQLException{
		PreparedStatement sentencia = null;
		sentencia=conn.prepareStatement(actualizarAcademia);
		sentencia.setInt(1, academiaDTO.getIdFacultad());
		sentencia.setString(2, academiaDTO.getNombreAcademia());
		sentencia.setInt(3, academiaDTO.getIdAcademia());
		int accion=sentencia.executeUpdate();
		sentencia.close();
		return accion;
	}
	
	@Override
	public int updateFacultad(FacultadDTO facultadDTO)throws SQLException{
		PreparedStatement sentencia = null;
		sentencia=conn.prepareStatement(actualizarFacultad);
		sentencia.setString(1, facultadDTO.getFacultad());
		sentencia.setInt(2, facultadDTO.getIdFacultad());
		int accion=sentencia.executeUpdate();
		sentencia.close();
		return accion;
	}
	
	@Override
	public int updateDocente(DocenteDTO docenteDTO)throws SQLException{
		PreparedStatement sentencia = null;
		sentencia=conn.prepareStatement(actualizarDocente);
		sentencia.setString(1, docenteDTO.getPrefijo());
		sentencia.setString(2, docenteDTO.getNombre());
		sentencia.setString(3, docenteDTO.getApellidoPaterno());
		sentencia.setString(4, docenteDTO.getApellidoMaterno());
		sentencia.setString(5, docenteDTO.getCorreo());
		sentencia.setInt(6, docenteDTO.getIdDocente());
		int accion=sentencia.executeUpdate();
		
		if(accion==1) {
			sentencia=conn.prepareStatement(cambiarCorreo);
			sentencia.setString(1, docenteDTO.getCorreo());
			sentencia.setInt(2, docenteDTO.getIdDocente());
			accion=sentencia.executeUpdate();
		} else {
			accion=0;
		}
		sentencia.close();
		return accion;
	}
	
	@Override
	public int cambiarAcademia(int idMateria, int idAcademia)throws SQLException{
		PreparedStatement sentencia = null;
		sentencia=conn.prepareStatement(cambiarAcademia);
		sentencia.setInt(1, idAcademia);
		sentencia.setInt(2, idMateria);
		int accion=sentencia.executeUpdate();
		sentencia.close();
		return accion;
	}
	
	@Override
	public boolean asignarMateria(int idCarrera, int idMateria, int opc)throws SQLException{
		CallableStatement sp = conn.prepareCall("CALL asignarMateria(?,?,?)");
		sp.setInt("idCarrera", idCarrera);
		sp.setInt("idMateria", idMateria);
		sp.setInt("opc", opc);
		return sp.execute();
	}
	
	private boolean validarDatosRepetidos(DocenteDTO docenteDTO, MateriaDTO materiaDTO)throws SQLException{
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		boolean validar=false;
		if(docenteDTO!=null){
			sentencia=conn.prepareStatement(validarDocente);
			sentencia.setString(1, docenteDTO.getNombre());
			sentencia.setString(2, docenteDTO.getApellidoPaterno());
			sentencia.setString(3, docenteDTO.getApellidoMaterno());
			sentencia.setString(4, docenteDTO.getCorreo());
			rs = sentencia.executeQuery();
			
			validar=rs.next()?true:false;
		} else if(materiaDTO!=null){
			sentencia=conn.prepareStatement(validarMateria);
			sentencia.setString(1, materiaDTO.getNombreMateria());
			sentencia.setInt(2, materiaDTO.getIdNucleo());
			sentencia.setInt(3, materiaDTO.getIdTipo());
			sentencia.setInt(4, materiaDTO.getIdArea());
			rs = sentencia.executeQuery();
			validar=rs.next()?true:false;
		}
		rs.close();
		sentencia.close();
		return validar;
	}
	
	private boolean validarAcademiaFacultad(AcademiaDTO academiaDTO, FacultadDTO facultadDTO, CarreraDTO carreraDTO)throws SQLException{
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		boolean validar=false;
		if(academiaDTO!=null){
			sentencia=conn.prepareStatement(validarAcademia);
			sentencia.setString(1, academiaDTO.getNombreAcademia());
			sentencia.setInt(2, academiaDTO.getIdFacultad());
			rs = sentencia.executeQuery();
			
			validar=rs.next()?true:false;
		} else if(facultadDTO!=null){
			sentencia=conn.prepareStatement(validarMateria);
			sentencia.setString(1, facultadDTO.getFacultad());
			rs = sentencia.executeQuery();
			validar=rs.next()?true:false;
		} else if(carreraDTO!=null){
			sentencia=conn.prepareStatement(validarCarrera);
			sentencia.setString(1, carreraDTO.getNombreCarrera());
			sentencia.setInt(2, carreraDTO.getPlanEstudios());
			sentencia.setInt(3, carreraDTO.getIdFacultad());
			rs = sentencia.executeQuery();
			validar=rs.next()?true:false;
		}
		rs.close();
		sentencia.close();
		return validar;
	}
		
	@Override
	public int cambiarEstadoDocente(AccesoDTO accesoDTO) throws SQLException {
		PreparedStatement sentencia = null;
		sentencia=conn.prepareStatement(cambiarEstadoDocente);
		sentencia.setInt(2, accesoDTO.getIdDocente());
		sentencia.setInt(1, accesoDTO.getEstado());
		sentencia.setInt(3, accesoDTO.getIdAcceso());
		int accion=sentencia.executeUpdate();
		sentencia.close();
		return accion;
	}
	
	@Override
	public int updateAcceso(AccesoDTO accesoDTO) throws SQLException {
		PreparedStatement sentencia = null;
		sentencia=conn.prepareStatement(actualizarAcceso);
		sentencia.setString(1, accesoDTO.getCorreo());
		sentencia.setString(2, accesoDTO.getPass());
		sentencia.setString(3, accesoDTO.getRol());
		int accion=sentencia.executeUpdate();
		sentencia.close();
		return accion;
	}
	
	@Override
	public boolean cambiarDocenteFacultad(int idDocente, int idFacultad, int idFacultadAntigua) throws SQLException {
		CallableStatement sp = conn.prepareCall("CALL updateDocenteFacultad(?,?,?)");
		sp.setInt("idDocente", idDocente);
		sp.setInt("idFacultadN", idFacultad);
		sp.setInt("idFacultadA", idFacultadAntigua);
		return sp.execute();
	}
	
	@Override
	public boolean asignarMateriaDocente(int idDocente, int idMateria, int opc)throws SQLException{
		CallableStatement sp = conn.prepareCall("CALL asignarMateriaDocente(?,?,?)");
		sp.setInt("idDocente", idDocente);
		sp.setInt("idMateria", idMateria);
		sp.setInt("opc", opc);
		return sp.execute();
	}

	@Override
	public boolean updatePuesto(int idDocente, int idFacultad, int idCarrera, int idAcademia, int idCargo, int idCargoA) throws SQLException {
		CallableStatement sp = conn.prepareCall("CALL updatePuesto(?,?,?,?,?,?)");
		sp.setInt("idDocente", idDocente);
		sp.setInt("idFac", idFacultad);
		sp.setInt("idCar", idCarrera);
		sp.setInt("idAcad", idAcademia);
		sp.setInt("idCargo", idCargo);
		sp.setInt("idCargoA", idCargoA);
		return sp.execute();
	}
	
	//Eliminar
	@Override
	public boolean eliminarDocente(int idDocente, int idCargo) throws SQLException {
		CallableStatement sp = conn.prepareCall("CALL eliminarDocente(?,?)");
		sp.setInt("idDocente", idDocente);
		sp.setInt("idCargo", idCargo);
		return sp.execute();
	}
	
	@Override
	public boolean eliminarMateria(int idMateria) throws SQLException {
		CallableStatement sp = conn.prepareCall("CALL eliminarMateria(?)");
		sp.setInt("idMateria", idMateria);
		return sp.execute();
	}
	
	@Override
	public boolean eliminarCarrera(int idCarrera) throws SQLException {
		CallableStatement sp = conn.prepareCall("CALL eliminarCarrera(?)");
		sp.setInt("carreraID", idCarrera);
		return sp.execute();
	}
	
	@Override
	public boolean eliminarAcademia(int idAcademia) throws SQLException {
		CallableStatement sp = conn.prepareCall("CALL eliminarAcademia(?)");
		sp.setInt("academiaID", idAcademia);
		return sp.execute();
	}
	
	@Override
	public boolean eliminarFacultad(int idFacultad) throws SQLException {
		CallableStatement sp = conn.prepareCall("CALL eliminarFacultad(?)");
		sp.setInt("facultadID", idFacultad);
		return sp.execute();
	}
	
	@Override
	public boolean eliminarGenerica(int idGenerica) throws SQLException {
		CallableStatement sp = conn.prepareCall("CALL eliminarGenerica(?)");
		sp.setInt("genericaID", idGenerica);
		return sp.execute();
	}
	
	@Override
	public boolean eliminarEspecifica(int idEspecifica) throws SQLException {
		CallableStatement sp = conn.prepareCall("CALL eliminarEspecifica(?)");
		sp.setInt("especificaID", idEspecifica);
		return sp.execute();
	}

	@Override
	public boolean eliminarDocenteFacultad(int idDocente, String idFacultad, int idCargo) throws SQLException {
		CallableStatement sp = conn.prepareCall("CALL eliminarDocenteFacultad(?,?,?)");
		sp.setInt("idDocente", idDocente);
		sp.setString("nombreFacultad", idFacultad);
		sp.setInt("idCargo", idCargo);
		return sp.execute();
	}
	
	@Override
	public int eliminarAcceso(int idAcceso) throws SQLException {
		PreparedStatement sentencia = null;
		sentencia=conn.prepareStatement(deleteAcceso);
		sentencia.setInt(1, idAcceso);
		int accion=sentencia.executeUpdate();
		sentencia.close();
		return accion;
	}
}