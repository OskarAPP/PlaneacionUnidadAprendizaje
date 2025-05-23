package pua.net.dao.mysql;

import java.security.GeneralSecurityException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pua.net.dao.PuaDAO;
import pua.net.dto.*;

public class MySQLPuaDAO extends PuaDAO{
	Connection conn;

	public MySQLPuaDAO(Connection conn) {
		this.conn = conn;
	}

	//create
	private static final String insertarPua =
			"insert into PUA values (null,?,?,?,?,?,?,0)";

	private static final String insertarSubcomp = 
			"insert into SUBCOMPETENCIA values (null,?,?,?,?,?,?)";

	private static final String insertarTema =
			"insert into TEMA values (null,?,?,?)";

	private static final String insertarSubtema =
			"insert into SUBTEMA values (null,?,?,?)";

	private static final String insertarCriterio =
			"insert into CRITERIO values (null,?,?)";

	private static final String insertarEvidencia =
			"insert into EVIDENCIA_SUBCOMPETENCIA values (?,?,?)";

	private static final String insertarDocentePua =
			"insert into PUA_DOCENTE values (?,?)";

	private static final String insertarGenericaPua =
			"insert into PUA_COMPETENCIAGEN values(?,?)";

	private static final String insertarEspecificaPua =
			"insert into PUA_COMPETENCIAESP values(?,?)";

	private static final String insertarActividadDocente =
			"insert into ACTIVIDADDOC values(null,?,?,?)";

	private static final String insertarActividadAlumno =
			"insert into ACTIVIDADAL values(null,?,?,?)";

	private static final String insertarBibliografia =
			"insert into BIBLIOGRAFIA_PUA values(?,?,?)";

	private static final String insertarAmbiente =
			"insert into SUBCOMPETENCIA_AMBIENTE values(?,?)";

	private static final String insertarBibliografiaSub =
			"insert into BIBLIOGRAFIA_SUBCOMPETENCIA values(?,?)";

	private static final String insertarPerfilAcad =
			"insert into PERFILACAD values(null,?,?)";

	private static final String insertarPerfilProf =
			"insert into PERFILPROF values(null,?,?)";

	private static final String insertarPerfilDoc =
			"insert into PERFILDOC values(null,?,?)";
	
	private static final String insertarMaterial = 
			"insert into SUBCOMPETENCIA_MATERIAL values(?,?)";
	
	private static final String insertarEvaluacionFinal =
			"insert into EVALUACION_FINAL values(null,?,?,?)";
	
	private static final String insertarEvaluacionCompetencias =
			"insert into EVALUACION_COMPETENCIAS values(null,?,?,?)";
	
	//read
	private static final String obtenerListaFacultad =
			"select * from FACULTAD order by facultad";

	private static final String obtenerListaCarreras =
			"select * from CARRERA where id_facultad = ? order by nombre_carrera";

	private static final String obtenerListaMateria =
			"select * " +
			"from CARRERA c, MATERIA m, CARRERA_MATERIA cm " +
			"where cm.id_carrera=? " +
			"and cm.id_carrera = c.id_carrera " +
			"and cm.id_materia = m.id_materia order by m.nombre_materia";

	private static final String obtenerInfoMateria =
			"select * " +
			"from MATERIA m, AREAM a, NUCLEOM n, TIPOM t " +
			"where m.id_materia = ? " +
			"and m.id_area = a.id_area " +
			"and m.id_nucleo = n.id_nucleo " +
			"and m.id_tipo = t.id_tipo";

	private static final String obtenerListaLibros =
			"select * from LIBRO l, EDITORIAL e " +
			"where l.id_editorial = e.id_editorial";

	private static final String obtenerListaGenerica =
			"select * from COMPETENCIAGEN";

	private static final String obtenerListaEspecifica =
			"select * from COMPETENCIAESP where id_carrera = ?" ;

	private static final String obtenerPuaPorIdMateria =
			"select * from PUA where id_materia = ? and estado=1";

	private static final String obtenerSubcompPorIdPua =
			"select * from SUBCOMPETENCIA where id_pua = ?";

	private static final String obtenerTemas =
			"select * from TEMA where id_subcompetencia = ?";

	private static final String obtenerCriterios =
			"select * from CRITERIO where id_subcompetencia = ?";

	private static final String obtenerSubtemas =
			"select * from SUBTEMA where id_tema = ?";

	private static final String obtenerEvidencias =
			"select * from EVIDENCIA";

	private static final String obtenerEvidenciasSub =
			"select * " +
			"from EVIDENCIA e, EVIDENCIA_SUBCOMPETENCIA es " +
			"where id_subcompetencia = ? and " +
			"e.id_evidencia = es.id_evidencia";

	private static final String obtenerGenericasPua =
			"select * " +
			"from COMPETENCIAGEN cg, PUA_COMPETENCIAGEN cpg " +
			"where cpg.id_pua = ? " +
			"and cg.id_generica = cpg.id_generica";

	private static final String obtenerEspecificasPua =
			"select * " +
			"from COMPETENCIAESP ce, PUA_COMPETENCIAESP pce " +
			"where pce.id_pua = ? " +
			"and ce.id_especifica = pce.id_especifica";

	private static final String obtenerDirector =
			"select * "+
			"from FACULTAD f, DOCENTE doc "  +
			"where f.id_facultad = ? "+
			"and doc.id_docente = f.id_director";

	private static final String obtenerSecretarioAcademico =
			"select * "+
			"from FACULTAD f, DOCENTE doc "  +
			"where f.id_facultad = ? "+
			"and doc.id_docente = f.id_secretario_academico";

	private static final String obtenerCoordinador =
			"select * "+
			"from CARRERA c, DOCENTE doc "  +
			"where c.id_carrera = ? "+
			"and doc.id_docente = c.id_coordinador";

	private static final String obtenerPresidenteAcademia =
			"select * "+
			"from ACADEMIA ac  , ACADEMIA_MATERIA ma, DOCENTE doc "+
			"where ma.id_materia = ? "+
			"and ma.id_academia = ac.id_academia "+
			"and ac.id_presidente = doc.id_docente";

	private static final String obtenerSecretarioAcademia =
			"select * "+
			"from ACADEMIA ac  , ACADEMIA_MATERIA ma, DOCENTE doc "+
			"where ma.id_materia = ? "+
			"and ma.id_academia = ac.id_academia "+
			"and ac.id_secretario = doc.id_docente";

	private static final String obtenerDocenteFacultad =
			"select * " +
			"from DOCENTE doc, FACULTAD f, DOCENTE_FACULTAD docf "+
			"where f.id_facultad = ? "+
			"and f.id_facultad = docf.id_facultad "+
			"and docf.id_docente = doc.id_docente";

	private static final String obtenerDocentePua =
			"select * "+
			"from DOCENTE doc, PUA pua, PUA_DOCENTE puadoc "+
			"where pua.id_pua = ? "+
			"and pua.id_pua = puadoc.id_pua "+
			"and puadoc.id_docente = doc.id_docente and pua.estado=1";

	private static final String obtenerActividadesAlumno =
			"select * from ACTIVIDADAL where id_subcompetencia = ?";

	private static final String obtenerActividadesDocente =
			"select * from ACTIVIDADDOC where id_subcompetencia = ?";

	private static final String obtenerAmbientes =
			"select * from AMBIENTE";

	private static final String obtenerTiposBibliografia =
			"select * from TIPOBIB";

	private static final String obtenerBibliografiaPua =
			"select * " +
			"from LIBRO l, BIBLIOGRAFIA_PUA bp, TIPOBIB tb, EDITORIAL e " +
			"where id_pua = ? " +
			"and l.id_libro = bp.id_libro " +
			"and e.id_editorial = l.id_editorial " +
			"and tb.id_tipo_bibliografia = bp.id_tipo_bibliografia";

	private static final String obtenerAmbientesSub =
			"select * " +
			"from SUBCOMPETENCIA_AMBIENTE sa, AMBIENTE a " +
			"where sa.id_subcompetencia = ? " +
			"and sa.id_ambiente = a.id_ambiente";

	private static final String obtenerPonderacion =
			"select ponderacion from SUBCOMPETENCIA where id_pua = ? and id_subcompetencia not in (?)";

	private static final String obtenerPonderacionEvidencias =
			"select ponderacion from EVIDENCIA_SUBCOMPETENCIA where id_subcompetencia = ?";

	private static final String obtenerBibliografiaSubcompetencia =
			"select * " +
			"from LIBRO l, BIBLIOGRAFIA_SUBCOMPETENCIA bs " +
			"where id_subcompetencia = ? " +
			"and l.id_libro = bs.id_libro";

	private static final String obtenerPerfilesAcademicos =
			"select * from PERFILACAD where id_pua = ?";

	private static final String obtenerPerfilesProfesionales =
			"select * from PERFILPROF where id_pua = ?";

	private static final String obtenerPerfilesDocente =
			"select * from PERFILDOC where id_pua = ?";
	
	private static final String obtenerListaMateriales = 
			"select * from MATERIAL";
	
	private static final String obtenerListaMaterialesSub = 
			"select * " + 
			"from SUBCOMPETENCIA_MATERIAL sm, MATERIAL m " + 
			"where sm.id_subcompetencia = ? " + 
			"and sm.id_material = m.id_material";
	private static final String obtenerFacultadPorID = "select * from facultad where id_facultad = ?";
	
	private static final String obtenerCarreraPorId = "select * from carrera where id_carrera = ?";
	
	private static final String obtenerEvaluacionCompetencias = "select * from EVALUACION_COMPETENCIAS where id_pua = ?";
	
	private static final String obtenerEvaluacionFinal = "select * from EVALUACION_FINAL where id_pua = ?";
	
	private static final String obtenerAcademia = "select id_materia,nombre_academia,id_presidente, id_secretario from ACADEMIA_MATERIA inner join academia on ACADEMIA_MATERIA.id_academia = ACADEMIA.id_academia where id_materia=?";
	
	private static final String obtenerMateriaPorId="select nombre_materia from materia where id_materia=?";
	
//	private static final String obtenerPuaPorId="select m.id_materia, m.nombre_materia  from Materia m, docente_materia dm, carrera_materia cm " + 
//			"where dm.id_docente=? and cm.id_carrera=? and dm.id_materia=m.id_materia and cm.id_materia=m.id_materia " + 
//			"GROUP BY m.nombre_materia";
	
	private static final String obtenerPuaPorId =
		    "SELECT m.id_materia, m.nombre_materia " +
		    "FROM Materia m " +
		    "JOIN docente_materia dm ON dm.id_materia = m.id_materia " +
		    "JOIN carrera_materia cm ON cm.id_materia = m.id_materia " +
		    "WHERE dm.id_docente = ? " +
		    "  AND cm.id_carrera = ? " +
		    "GROUP BY m.id_materia, m.nombre_materia";

	
	private static final String obtenerDocenteFacultadId=
			"SELECT df.id_facultad, f.facultad FROM docente_facultad df, facultad f " + 
			"where df.id_facultad=f.id_facultad and df.id_docente=?";
	
	private static final String obtenerFacultadDirecctor="SELECT * FROM `facultad` WHERE id_director=? or id_secretario_academico=?";
	
	private static final String obtenerFacultadCoordinador=
			"SELECT f.id_facultad,f.facultad FROM carrera c, facultad f " + 
			"WHERE c.id_facultad=f.id_facultad and c.id_coordinador=?";
			
	
//	private static final String obtenerCarreraAcademia=
//			"select c.id_carrera, c.nombre_carrera, c.plan_estudios from Carrera c, docente_facultad df, carrera_materia cm, "
//			+ "academia_materia am, academia a where c.id_facultad=?  and (a.id_presidente=? or a.id_secretario=?)"+
//			"and (df.id_docente=?) and (a.id_presidente=df.id_docente or a.id_secretario=df.id_docente) "+ 
//			"and df.id_facultad=c.id_facultad and (a.id_academia=am.id_academia) and am.id_materia=cm.id_materia "+
//			"and c.id_carrera=cm.id_carrera GROUP BY c.nombre_carrera";
	
	private static final String obtenerCarreraAcademia =
		    "SELECT c.id_carrera, c.nombre_carrera, c.plan_estudios " +
		    "FROM Carrera c, docente_facultad df, carrera_materia cm, academia_materia am, academia a " +
		    "WHERE c.id_facultad=? " +
		    "AND (a.id_presidente=? OR a.id_secretario=?) " +
		    "AND (df.id_docente=?) " +
		    "AND (a.id_presidente=df.id_docente OR a.id_secretario=df.id_docente) " +
		    "AND df.id_facultad=c.id_facultad " +
		    "AND (a.id_academia=am.id_academia) " +
		    "AND am.id_materia=cm.id_materia " +
		    "AND c.id_carrera=cm.id_carrera " +
		    "GROUP BY c.id_carrera, c.nombre_carrera, c.plan_estudios";


	
	private static final String obtenerTipoDocente="select td.idTipo, t.tipo from tipo_docente td, tipo t"+
	" where td.idTipo=t.idTipo and td.id_docente=?";
	
	private static final String obtenerCarreraCoordinador="select * from carrera where id_coordinador=?";
	
	private static final String obtenerAcademiaMateria =
			"select m.id_materia, m.nombre_materia from MATERIA m inner join CARRERA_MATERIA cm on " +
			"cm.id_materia=m.id_materia inner join academia_materia am " +
			"on am.id_materia=m.id_materia and am.id_materia=cm.id_materia " +
			"inner join academia a on am.id_academia=a.id_academia " +
			"where cm.id_carrera = ? and (a.id_presidente=? or a.id_secretario=?)"; 
	
//	private static final String obtenerCarreraDocente="select c.id_carrera, c.nombre_carrera, c.plan_estudios from Carrera c, docente_facultad df, " 
//			+ "carrera_materia cm, docente_materia dm where c.id_facultad=? and (df.id_docente=?) and df.id_docente=dm.id_docente " 
//			+ "and c.id_carrera=cm.id_carrera and df.id_facultad=c.id_facultad  and cm.id_materia=dm.id_materia GROUP BY c.nombre_carrera";
	
	private static final String obtenerCarreraDocente =
		    "SELECT c.id_carrera, c.nombre_carrera, c.plan_estudios " +
		    "FROM Carrera c, docente_facultad df, carrera_materia cm, docente_materia dm " +
		    "WHERE c.id_facultad=? " +
		    "AND (df.id_docente=?) " +
		    "AND df.id_docente=dm.id_docente " +
		    "AND c.id_carrera=cm.id_carrera " +
		    "AND df.id_facultad=c.id_facultad " +
		    "AND cm.id_materia=dm.id_materia " +
		    "GROUP BY c.id_carrera, c.nombre_carrera, c.plan_estudios";

	
	private static final String obtenerFacultadAcademia=
			"SELECT f.id_facultad, f.facultad FROM facultad f, academia a WHERE " + 
			"a.id_facultad=f.id_facultad and (a.id_presidente=? or a.id_secretario=?)";
	
	//update
	private static final String guardarPua =
			"update PUA set competencia_uda = ?, competencia_formacion = ? where id_pua = ?";

	private static final String guardarSubcompetencia =
			"update SUBCOMPETENCIA set subcompetencia = ?, sesiones = ?, ponderacion = ?, parcial = ? where id_subcompetencia = ?";

	//delete
	private static final String eliminarSubcompetencia =
			"delete from SUBCOMPETENCIA where id_subcompetencia = ?";
	
	private static final String eliminarAllBibliografia=
			"DELETE FROM `bibliografia_pua` WHERE `id_pua`=?";

	private static final String eliminarTema =
			"delete from TEMA where id_tema = ?";

	private static final String eliminarSubtema =
			"delete from SUBTEMA where id_subtema = ?";

	private static final String eliminarDocentePua =
			"delete from PUA_DOCENTE where id_docente = ? and id_pua=?";

	private static final String eliminarCompetenciaGenerica =
			"delete from PUA_COMPETENCIAGEN where id_pua = ? and id_generica = ?";

	private static final String eliminarCompetenciaEspecifica =
			"delete from PUA_COMPETENCIAESP where id_pua = ? and id_especifica = ?";

	private static final String eliminarBibliografia =
			"delete from BIBLIOGRAFIA_PUA where id_pua = ? and id_libro = ?";

	private static final String eliminarActividadAlumno =
			"delete from ACTIVIDADAL where id_actividad_alumno = ?";

	private static final String eliminarActividadDocente =
			"delete from ACTIVIDADDOC where id_actividad_docente = ?";

	private static final String eliminarAmbiente =
			"delete from SUBCOMPETENCIA_AMBIENTE where id_subcompetencia = ? and id_ambiente = ?";

	private static final String eliminarCriterio =
			"delete from CRITERIO where id_criterio = ?";

	private static final String eliminarEvidencia =
			"delete from EVIDENCIA_SUBCOMPETENCIA where id_subcompetencia = ? and id_evidencia = ?";

	private static final String eliminarBibliografiaSub =
			"delete from BIBLIOGRAFIA_SUBCOMPETENCIA where id_subcompetencia = ? and id_libro = ?";

	private static final String eliminarPerfilAcad =
			"delete from PERFILACAD where id_perfil_acad = ?";

	private static final String eliminarPerfilProf =
			"delete from PERFILPROF where id_perfil_prof = ?";

	private static final String eliminarPerfilDoc =
			"delete from PERFILDOC where id_perfil_doc = ?";
	
	private static final String eliminarEvaluacionCompetencias = 
			"delete from EVALUACION_COMPETENCIAS where id_pua = ?";
	
	private static final String eliminarEvaluacionFinal = 
			"delete from EVALUACION_FINAL where id_pua = ?";
	
	private static final String eliminarMaterial =
			"delete from SUBCOMPETENCIA_MATERIAL where id_subcompetencia = ? and id_material = ?";
	
	private static final String obtenerDocenteById = "Select * from docente where id_docente = ?";
		
	private static final String obtenerAcceso="select * from acceso where correo=? and pass=? and estado=1";
	
	@Override
	public CarreraDTO obtenerCarreraPorID(int idCarrera) throws SQLException{
		PreparedStatement sentencia;
		ResultSet rs;
		CarreraDTO carreraDTO = null;
		
		sentencia = conn.prepareStatement(obtenerCarreraPorId);
		sentencia.setInt(1, idCarrera);
		
		rs = sentencia.executeQuery();
		
		if(rs.next()){
			carreraDTO = new CarreraDTO();
			carreraDTO.setIdCarrera(idCarrera);
			carreraDTO.setIdFacultad(rs.getInt("id_facultad"));
			carreraDTO.setNombreCarrera(rs.getString("nombre_carrera"));
			carreraDTO.setPlanEstudios(rs.getInt("plan_estudios"));
			carreraDTO.setIdCoordinador(rs.getInt("id_coordinador"));
		}
		
		return carreraDTO;
		
	}
	
	@Override
	public FacultadDTO obtenerFacultadPorID(int idFacultad) throws SQLException{
		PreparedStatement sentencia;
		ResultSet rs;
		FacultadDTO facultadDTO = null;
		
		sentencia = conn.prepareStatement(obtenerFacultadPorID);
		sentencia.setInt(1, idFacultad);
		
		rs = sentencia.executeQuery();
		
		if(rs.next()){
			facultadDTO = new FacultadDTO();
			facultadDTO.setIdFacultad(idFacultad);
			facultadDTO.setFacultad(rs.getString("facultad"));
			facultadDTO.setIdDirector(rs.getInt("id_director"));
			facultadDTO.setIdSecretarioAcademico(rs.getInt("id_secretario_academico"));
		}
		
		
		return facultadDTO;
	}
	
	@Override
	public DocenteDTO obtenerDocente(String correo, String pass) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		DocenteDTO docenteDTO = null;
		List<AccesoDTO> listaAcceso= new ArrayList<AccesoDTO>();
		sentencia = conn.prepareStatement(obtenerAcceso);
		sentencia.setString(1, correo);
		sentencia.setString(2, pass);
		rs = sentencia.executeQuery();
		
		int idDocente=0;
		while(rs.next()){
			AccesoDTO accesoDTO=new AccesoDTO();
			accesoDTO.setIdAcceso(rs.getInt(1));
			accesoDTO.setIdDocente(rs.getInt(2));
			accesoDTO.setCorreo(correo);
			accesoDTO.setPass(pass);
			accesoDTO.setRol(rs.getString(5));
			accesoDTO.setEstado(1);
			idDocente=accesoDTO.getIdDocente();
			listaAcceso.add(accesoDTO);
		}
		
		if(listaAcceso.size()>0) {
			docenteDTO=obtenerDocenteById(idDocente);
			docenteDTO.setListaAcceso(listaAcceso);
		}
		
		sentencia.close();
		return docenteDTO;
	}
	
	private DocenteDTO obtenerDocenteById(int idDocente) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		DocenteDTO docenteDTO = null;
		sentencia = conn.prepareStatement(obtenerDocenteById);
		sentencia.setInt(1, idDocente);

		rs = sentencia.executeQuery();
		
		if(rs.next()){
			docenteDTO = new DocenteDTO();
			docenteDTO.setIdDocente(rs.getInt("id_docente"));
			docenteDTO.setNombre(rs.getString("nombre"));
			docenteDTO.setApellidoPaterno(rs.getString("apellido_paterno"));
			docenteDTO.setApellidoMaterno(rs.getString("apellido_materno"));
			docenteDTO.setPrefijo(rs.getString("prefijo"));
			docenteDTO.setCorreo(rs.getString("correo"));
			docenteDTO = obtenerTipoDocente(docenteDTO.getIdDocente(), docenteDTO);
		}
		
		sentencia.close();
		return docenteDTO;
	}
	
	@Override
	public DocenteDTO obtenerTipoDocente(int idDocente,DocenteDTO docenteDTO) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		
		sentencia = conn.prepareStatement(obtenerTipoDocente);
		sentencia.setInt(1, docenteDTO.getIdDocente());

		rs = sentencia.executeQuery();
		
		if(rs.next()){
			docenteDTO.setIdTipo(rs.getInt(1));
			docenteDTO.setTipo(rs.getString(2));
		}
		
		sentencia.close();
		return docenteDTO;
	}
	
	
	@Override
	public void guardarPua(PuaDTO puaDTO) throws SQLException{
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(guardarPua);
		sentencia.setString(1, puaDTO.getCompetenciaUda());
		sentencia.setString(2, puaDTO.getCompetenciaFormacion());
		sentencia.setInt(3, puaDTO.getIdPua());

		sentencia.executeUpdate();

		sentencia.close();
	}

	@Override
	public int insertarPua(PuaDTO puaDTO) throws SQLException{
		int idPua = 0;
		PreparedStatement sentencia;
		ResultSet rs;

		sentencia = conn.prepareStatement(insertarPua,PreparedStatement.RETURN_GENERATED_KEYS);
		sentencia.setInt(1,puaDTO.getIdMateria());
		sentencia.setInt(2, puaDTO.getCreditacionCompetencias());
		sentencia.setString(3, puaDTO.getCompetenciaUda());
		sentencia.setString(4, puaDTO.getCompetenciaFormacion());
		sentencia.setInt(5, puaDTO.getPlanEstudio());
		sentencia.setInt(6, 1);
		sentencia.executeUpdate();

		rs = sentencia.getGeneratedKeys();
		if(rs.next()){
			idPua = rs.getInt(1);
		}

		rs.close();
		sentencia.close();

		return idPua;
	}

	@Override
	public PuaDTO obtenerPuaPorIdMateria(int idMateria) throws SQLException{
		PuaDTO puaDTO = new PuaDTO();
		PreparedStatement sentencia;
		ResultSet rs;

		sentencia = conn.prepareStatement(obtenerPuaPorIdMateria);
		sentencia.setInt(1, idMateria);
		rs = sentencia.executeQuery();

		if(rs.next()){
			puaDTO.setIdPua(rs.getInt(1));
			puaDTO.setIdMateria(rs.getInt(2));
			puaDTO.setCreditacionCompetencias(rs.getInt(3));
			puaDTO.setCompetenciaUda(rs.getString(4));
			puaDTO.setCompetenciaFormacion(rs.getString(5));
		}

		rs.close();
		sentencia.close();

		return puaDTO;
	}

	@Override
	public List<FacultadDTO> obtenerListaFacultad() throws SQLException {
		Statement sentencia;
		ResultSet rs;
		List<FacultadDTO> listaFacultad = new ArrayList<FacultadDTO>();
		FacultadDTO facultadDTO;

		sentencia = conn.createStatement();
		rs = sentencia.executeQuery(obtenerListaFacultad);

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
		PreparedStatement sentencia;
		ResultSet rs;
		List<CarreraDTO> listaCarreras = new ArrayList<CarreraDTO>();
		CarreraDTO carreraDTO;

		sentencia = conn.prepareStatement(obtenerListaCarreras);
		sentencia.setInt(1, idFacultad);
		rs = sentencia.executeQuery();

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
	public List<MateriaDTO> obtenerListaMateria(int idCarrera) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<MateriaDTO> listaMateria = new ArrayList<MateriaDTO>();
		MateriaDTO materiaDTO;

		sentencia = conn.prepareStatement(obtenerListaMateria);
		sentencia.setInt(1, idCarrera);
		rs = sentencia.executeQuery();

		while(rs.next()){
			materiaDTO = new MateriaDTO();
			materiaDTO.setIdMateria(rs.getInt(6));
			materiaDTO.setNombreMateria(rs.getString(10));

			listaMateria.add(materiaDTO);
		}
		rs.close();
		sentencia.close();

		return listaMateria;
	}

	public MateriaDTO obtenerInfoMateria(int idMateria) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		MateriaDTO materiaDTO = new MateriaDTO();

		sentencia = conn.prepareStatement(obtenerInfoMateria);
		sentencia.setInt(1, idMateria);
		rs = sentencia.executeQuery();

		if(rs.next()){
			materiaDTO.setIdMateria(rs.getInt(1));
			materiaDTO.setNombreMateria(rs.getString(5));
			materiaDTO.setCreditosTotales(rs.getInt(6));
			materiaDTO.setHorasTotales(rs.getInt(7));
			materiaDTO.setHorasTeoricas(rs.getInt(8));
			materiaDTO.setHorasPracticas(rs.getInt(9));
			materiaDTO.setArt57(rs.getString(10));
			materiaDTO.setArea(rs.getString(12));
			materiaDTO.setNucleo(rs.getString(14));
			materiaDTO.setTipo(rs.getString(16));
		}
		rs.close();
		sentencia.close();

		return materiaDTO;
	}

	public List<GenericaDTO> obtenerListaCompetenciasGenericas() throws SQLException {
		Statement sentencia;
		ResultSet rs;
		List<GenericaDTO> listaGenerica = new ArrayList<GenericaDTO>();
		GenericaDTO genericaDTO;

		sentencia = conn.createStatement();
		rs = sentencia.executeQuery(obtenerListaGenerica);

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

	public List<EspecificaDTO> obtenerListaCompetenciasEspecificas(int idCarrera) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<EspecificaDTO> listaEspecifica = new ArrayList<EspecificaDTO>();
		EspecificaDTO especificaDTO;

		sentencia = conn.prepareStatement(obtenerListaEspecifica);
		sentencia.setInt(1, idCarrera);
		rs = sentencia.executeQuery();

		while (rs.next()) {
			especificaDTO = new EspecificaDTO();
			especificaDTO.setIdEspecifica(rs.getInt(1));
			especificaDTO.setPerfilEspecifica(rs.getString(2));
			listaEspecifica.add(especificaDTO);
		}

		rs.close();
		sentencia.close();

		return listaEspecifica;
	}

	@Override
	public List<LibroDTO> obtenerListaLibros() throws SQLException {
		Statement sentencia;
		ResultSet rs;
		List<LibroDTO> listaLibros = new ArrayList<LibroDTO>();
		LibroDTO librosDTO;

		sentencia = conn.createStatement();
		rs = sentencia.executeQuery(obtenerListaLibros);

		while(rs.next()){
			librosDTO = new LibroDTO();
			librosDTO.setIdLibro(rs.getInt(1));
			librosDTO.setLibro(rs.getString(2));
			librosDTO.setIdEditorial(rs.getInt(3));
			librosDTO.setAutorPrincipal(rs.getString(4));
			librosDTO.setEditorial(rs.getString(6));
			listaLibros.add(librosDTO);

		}

		rs.close();
		sentencia.close();

		return listaLibros;
	}

	public List<SubcompetenciaDTO> obtenerSubcompetencias(int idPua) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		SubcompetenciaDTO subcompDTO;
		List<SubcompetenciaDTO> listaSubcompetencias = new ArrayList<SubcompetenciaDTO>();
		

		sentencia = conn.prepareStatement(obtenerSubcompPorIdPua);
		sentencia.setInt(1, idPua);

		rs = sentencia.executeQuery();

		while (rs.next()) {
			subcompDTO = new SubcompetenciaDTO();
			subcompDTO.setIdSubcompetencia(rs.getInt(1));
			subcompDTO.setIdPua(rs.getInt(2));
			subcompDTO.setNumSubcompetencia(rs.getInt(3));
			subcompDTO.setSubcompetencia(rs.getString(4));
			subcompDTO.setSesiones(rs.getInt(5));
			subcompDTO.setPonderacion(rs.getInt(6));
			subcompDTO.setParcial(rs.getInt(7));
			subcompDTO.setListaTemas( obtenerTemas(subcompDTO.getIdSubcompetencia()) );
			subcompDTO.setListaActividadesAlumno(obtenerActividadesAlumno(subcompDTO.getIdSubcompetencia()));
			subcompDTO.setListaActividadesDocente(obtenerActividadesDocente(subcompDTO.getIdSubcompetencia()));
			subcompDTO.setListaBibliografia(obtenerBibliografiaSubcompetencia(subcompDTO.getIdSubcompetencia()));
			subcompDTO.setListaCriterios(obtenerCriterios(subcompDTO.getIdSubcompetencia()));
			subcompDTO.setListaEvidencia(obtenerEvidenciasSubcompetencia(subcompDTO.getIdSubcompetencia()));
			subcompDTO.setListaAmbiente(obtenerAmbientesSubcompetencia(subcompDTO.getIdSubcompetencia()));
			subcompDTO.setListaMaterial(obtenerListaMaterialesSubcompetencia(subcompDTO.getIdSubcompetencia()));
			listaSubcompetencias.add(subcompDTO);
		}

		rs.close();
		sentencia.close();

		return listaSubcompetencias;
	}
	
	@Override
	public List<TemaDTO> obtenerTemas(int idSubcompetencia) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		TemaDTO temaDTO;
		List<TemaDTO> listaTemas = new ArrayList<TemaDTO>();

		sentencia = conn.prepareStatement(obtenerTemas);
		sentencia.setInt(1, idSubcompetencia);

		rs = sentencia.executeQuery();

		while (rs.next()) {
			temaDTO = new TemaDTO();
			temaDTO.setIdTema(rs.getInt(1));
			temaDTO.setTema(rs.getString(2));
			temaDTO.setNumeroTema(rs.getInt(3));
			temaDTO.setIdSubcompetencia(rs.getInt(4));
			temaDTO.setListaSubtema(obtenerSubtemas(temaDTO.getIdTema()));
			listaTemas.add(temaDTO);
		}

		return listaTemas;
	}

	public int insertarSubcompetencia(SubcompetenciaDTO subcompetenciaDTO) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		int idSubcompetencia = 0;

		sentencia = conn.prepareStatement(insertarSubcomp, PreparedStatement.RETURN_GENERATED_KEYS);

		sentencia.setInt(1, subcompetenciaDTO.getIdPua());
		sentencia.setInt(2, subcompetenciaDTO.getNumSubcompetencia());
		sentencia.setString(3, subcompetenciaDTO.getSubcompetencia());
		sentencia.setInt(4, subcompetenciaDTO.getSesiones());
		sentencia.setInt(5, subcompetenciaDTO.getPonderacion());
		sentencia.setInt(6, subcompetenciaDTO.getParcial());

		sentencia.executeUpdate();

		rs = sentencia.getGeneratedKeys();

		if (rs.next()) {
			idSubcompetencia = rs.getInt(1);
		}

		sentencia.close();

		return idSubcompetencia;


	}

	@Override
	public void guardarSubcompetencia(SubcompetenciaDTO subcompetenciaDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(guardarSubcompetencia);
		sentencia.setString(1, subcompetenciaDTO.getSubcompetencia());
		sentencia.setInt(2, subcompetenciaDTO.getSesiones());
		sentencia.setInt(3, subcompetenciaDTO.getPonderacion());
		sentencia.setInt(4, subcompetenciaDTO.getParcial());
		sentencia.setInt(5, subcompetenciaDTO.getIdSubcompetencia());

		sentencia.executeUpdate();
		sentencia.close();

	}

	@Override
	public void eliminarSubcompetencia(int idSubcompetencia) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarSubcompetencia);
		sentencia.setInt(1, idSubcompetencia);

		sentencia.executeUpdate();
		sentencia.close();

	}

	

	@Override
	public void insertarTema(TemaDTO temaDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(insertarTema, PreparedStatement.RETURN_GENERATED_KEYS);
		sentencia.setString(1, temaDTO.getTema());
		sentencia.setInt(2, temaDTO.getNumeroTema());
		sentencia.setInt(3, temaDTO.getIdSubcompetencia());

		sentencia.executeUpdate();

		sentencia.close();

	}

	@Override
	public void insertarSubtema(SubtemaDTO subtemaDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(insertarSubtema, PreparedStatement.RETURN_GENERATED_KEYS);
		sentencia.setInt(1, subtemaDTO.getIdTema());
		sentencia.setString(2, subtemaDTO.getSubtema());
		sentencia.setInt(3, subtemaDTO.getNumeroSubtema());
		sentencia.executeUpdate();
		
		sentencia.close();

	}

	@Override
	public List<SubtemaDTO> obtenerSubtemas(int idTema) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<SubtemaDTO> listaSubtemas = new ArrayList<SubtemaDTO>();
		SubtemaDTO subtemaDTO;

		sentencia = conn.prepareStatement(obtenerSubtemas);
		sentencia.setInt(1, idTema);

		rs = sentencia.executeQuery();

		while (rs.next()) {
			subtemaDTO = new SubtemaDTO();
			subtemaDTO.setIdSubtema(rs.getInt(1));
			subtemaDTO.setIdTema(rs.getInt(2));
			subtemaDTO.setSubtema(rs.getString(3));
			subtemaDTO.setNumeroSubtema(rs.getInt(4));
			listaSubtemas.add(subtemaDTO);
		}

		return listaSubtemas;
	}

	@Override
	public void eliminarTema(int idTema) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarTema);
		sentencia.setInt(1, idTema);

		sentencia.executeUpdate();
		sentencia.close();

	}

	@Override
	public void eliminarSubtema(int idSubtema) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarSubtema);
		sentencia.setInt(1, idSubtema);

		sentencia.executeUpdate();
		sentencia.close();

	}

	@Override
	public void insertarCriterio(CriterioDTO criterioDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(insertarCriterio, PreparedStatement.RETURN_GENERATED_KEYS);
		sentencia.setString(1, criterioDTO.getCriterio());
		sentencia.setInt(2, criterioDTO.getIdSubcompetencia());

		sentencia.executeUpdate();

		sentencia.close();

	}

	@Override
	public List<CriterioDTO> obtenerCriterios(int idSubcompetencia) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		CriterioDTO criterioDTO;
		List<CriterioDTO> listaCriterios = new ArrayList<CriterioDTO>();

		sentencia = conn.prepareStatement(obtenerCriterios);
		sentencia.setInt(1, idSubcompetencia);

		rs = sentencia.executeQuery();

		while (rs.next()) {
			criterioDTO = new CriterioDTO();
			criterioDTO.setIdCriterio(rs.getInt(1));
			criterioDTO.setCriterio(rs.getString(2));
			criterioDTO.setIdSubcompetencia(rs.getInt(3));
			listaCriterios.add(criterioDTO);
		}

		return listaCriterios;

	}

	@Override
	public List<EvidenciaDTO> obtenerEvidencias() throws SQLException {
		Statement sentencia;
		ResultSet rs;
		List<EvidenciaDTO> listaEvidencias = new ArrayList<EvidenciaDTO>();
		EvidenciaDTO evidenciaDTO;

		sentencia = conn.createStatement();
		rs = sentencia.executeQuery(obtenerEvidencias);

		while (rs.next()) {
			evidenciaDTO = new EvidenciaDTO();
			evidenciaDTO.setIdEvidencia(rs.getInt(1));
			evidenciaDTO.setEvidencia(rs.getString(2));
			listaEvidencias.add(evidenciaDTO);
		}

		return listaEvidencias;
	}

	@Override
	public void insertarEvidencia(EvidenciaDTO evidenciaDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(insertarEvidencia);
		sentencia.setInt(1, evidenciaDTO.getIdSubcompetencia());
		sentencia.setInt(2, evidenciaDTO.getIdEvidencia());
		sentencia.setInt(3, evidenciaDTO.getPonderacion());

		sentencia.executeUpdate();
		sentencia.close();
	}

	@Override
	public List<GenericaDTO> obtenerGenericasPua(int idPua) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		GenericaDTO genericaDTO;
		List<GenericaDTO> listaGenericasPua = new ArrayList<GenericaDTO>();

		sentencia = conn.prepareStatement(obtenerGenericasPua);
		sentencia.setInt(1, idPua);

		rs = sentencia.executeQuery();

		while (rs.next()) {
			genericaDTO = new GenericaDTO();
			genericaDTO.setIdGenerica(rs.getInt(1));
			genericaDTO.setNombreGenerica(rs.getString(2));
			genericaDTO.setIdPua(rs.getInt(3));
			listaGenericasPua.add(genericaDTO);
		}

		return listaGenericasPua;
	}

	@Override
	public List<EspecificaDTO> obtenerEspecificasPua(int idPua) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		EspecificaDTO especificaDTO;
		List<EspecificaDTO> listaEspecificasPua = new ArrayList<EspecificaDTO>();

		sentencia = conn.prepareStatement(obtenerEspecificasPua);
		sentencia.setInt(1, idPua);

		rs = sentencia.executeQuery();

		while (rs.next()) {
			especificaDTO = new EspecificaDTO();
			especificaDTO.setIdEspecifica(rs.getInt(1));
			especificaDTO.setPerfilEspecifica(rs.getString(2));
			especificaDTO.setIdPua(rs.getInt(4));
			listaEspecificasPua.add(especificaDTO);
		}

		return listaEspecificasPua;
	}

	@Override
	public List<DocenteDTO> obtenerDirector(int idFacultad) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<DocenteDTO> listaDirector = new ArrayList<DocenteDTO>();
		DocenteDTO docenteDTO;

		sentencia = conn.prepareStatement(obtenerDirector);
		sentencia.setInt(1, idFacultad);
		rs = sentencia.executeQuery();


		while(rs.next()){
			docenteDTO = new DocenteDTO();
			docenteDTO.setPrefijo(rs.getString(7));
			docenteDTO.setNombre(rs.getString(8));
			docenteDTO.setApellidoPaterno(rs.getString(9));
			docenteDTO.setApellidoMaterno(rs.getString(10));
			docenteDTO.setCorreo(rs.getString(11));

			listaDirector.add(docenteDTO);
		}
		rs.close();
		sentencia.close();

		return listaDirector;
	}

	@Override
	public List<DocenteDTO> obtenerSecretarioAcademico(int idFacultad) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<DocenteDTO> listaSecretarioAcademico = new ArrayList<DocenteDTO>();
		DocenteDTO docenteDTO;

		sentencia = conn.prepareStatement(obtenerSecretarioAcademico);
		sentencia.setInt(1, idFacultad);
		rs = sentencia.executeQuery();


		while(rs.next()){
			docenteDTO = new DocenteDTO();
			docenteDTO.setPrefijo(rs.getString(7));
			docenteDTO.setNombre(rs.getString(8));
			docenteDTO.setApellidoPaterno(rs.getString(9));
			docenteDTO.setApellidoMaterno(rs.getString(10));
			docenteDTO.setCorreo(rs.getString(11));

			listaSecretarioAcademico.add(docenteDTO);
		}
		rs.close();
		sentencia.close();

		return listaSecretarioAcademico;
	}

	@Override
	public List<DocenteDTO> obtenerCoordinador(int idCarrera) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<DocenteDTO> listaCoordinador = new ArrayList<DocenteDTO>();
		DocenteDTO docenteDTO;

		sentencia = conn.prepareStatement(obtenerCoordinador);
		sentencia.setInt(1, idCarrera);
		rs = sentencia.executeQuery();


		while(rs.next()){
			docenteDTO = new DocenteDTO();
			docenteDTO.setPrefijo(rs.getString(7));
			docenteDTO.setNombre(rs.getString(8));
			docenteDTO.setApellidoPaterno(rs.getString(9));
			docenteDTO.setApellidoMaterno(rs.getString(10));
			docenteDTO.setCorreo(rs.getString(11));

			listaCoordinador.add(docenteDTO);
		}
		rs.close();
		sentencia.close();

		return listaCoordinador;
	}
	
	@Override
	public List<DocenteDTO> obtenerPresidenteAcademia(int idMateria) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<DocenteDTO> listaPresidenteAcademia = new ArrayList<DocenteDTO>();
		DocenteDTO docenteDTO;

		sentencia = conn.prepareStatement(obtenerPresidenteAcademia);
		sentencia.setInt(1, idMateria);
		rs = sentencia.executeQuery();


		while(rs.next()){
			docenteDTO = new DocenteDTO();
			docenteDTO.setPrefijo(rs.getString(9));
			docenteDTO.setNombre(rs.getString(10));
			docenteDTO.setApellidoPaterno(rs.getString(11));
			docenteDTO.setApellidoMaterno(rs.getString(12));
			docenteDTO.setCorreo(rs.getString(13));

			listaPresidenteAcademia.add(docenteDTO);
		}
		rs.close();
		sentencia.close();

		return listaPresidenteAcademia;
	}

	@Override
	public List<DocenteDTO> obtenerSecretarioAcademia(int idMateria) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<DocenteDTO> listaSecretarioAcademia = new ArrayList<DocenteDTO>();
		DocenteDTO docenteDTO;

		sentencia = conn.prepareStatement(obtenerSecretarioAcademia);
		sentencia.setInt(1, idMateria);
		rs = sentencia.executeQuery();


		while(rs.next()){
			docenteDTO = new DocenteDTO();
			docenteDTO.setPrefijo(rs.getString(9));
			docenteDTO.setNombre(rs.getString(10));
			docenteDTO.setApellidoPaterno(rs.getString(11));
			docenteDTO.setApellidoMaterno(rs.getString(12));
			docenteDTO.setCorreo(rs.getString(13));

			listaSecretarioAcademia.add(docenteDTO);
		}
		rs.close();
		sentencia.close();

		return listaSecretarioAcademia;
	}

	@Override
	public List<DocenteDTO> obtenerDocenteFacultad(int idFacultad) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<DocenteDTO> listaDocenteFacultad = new ArrayList<DocenteDTO>();
		DocenteDTO docenteDTO;

		sentencia = conn.prepareStatement(obtenerDocenteFacultad);
		sentencia.setInt(1, idFacultad);
		rs = sentencia.executeQuery();


		while(rs.next()){
			docenteDTO = new DocenteDTO();
			docenteDTO.setIdDocente(rs.getInt(1));
			docenteDTO.setPrefijo(rs.getString(2));
			docenteDTO.setNombre(rs.getString(3));
			docenteDTO.setApellidoPaterno(rs.getString(4));
			docenteDTO.setApellidoMaterno(rs.getString(5));
			docenteDTO.setCorreo(rs.getString(6));

			listaDocenteFacultad.add(docenteDTO);
		}
		rs.close();
		sentencia.close();

		return listaDocenteFacultad;
	}

	@Override
	public void insertarDocentePua(DocenteDTO docenteDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(insertarDocentePua);

		sentencia.setInt(1, docenteDTO.getIdDocente());
		sentencia.setInt(2, docenteDTO.getIdPua());
		sentencia.executeUpdate();

		sentencia.close();

	}
	
	@Override
	public void insertarEvaluacionFinal(EvaluacionDTO evaluacionDTO) throws SQLException {
		PreparedStatement sentencia;
		sentencia = conn.prepareStatement(insertarEvaluacionFinal);	
		for (int i = 0; i < 4; i++) {
			if(evaluacionDTO.getPonderacion1()>0 && (i+1)==1) {	
				sentencia.setString(1, evaluacionDTO.getEvaluacion());
				sentencia.setInt(2, evaluacionDTO.getPonderacion1());		
				sentencia.setInt(3, evaluacionDTO.getIdPua());
				sentencia.executeUpdate();
			} else if(evaluacionDTO.getPonderacion2()>0 && (i+1)==2) {
				sentencia.setString(1, evaluacionDTO.getEvaluacion2());
				sentencia.setInt(2, evaluacionDTO.getPonderacion2());
				sentencia.setInt(3, evaluacionDTO.getIdPua());
				sentencia.executeUpdate();
			} else if(evaluacionDTO.getPonderacion3()>0 && (i+1)==3) {
				sentencia.setString(1, evaluacionDTO.getEvaluacion3());
				sentencia.setInt(2, evaluacionDTO.getPonderacion3());
				sentencia.setInt(3, evaluacionDTO.getIdPua());
				sentencia.executeUpdate();
			} else if(evaluacionDTO.getPonderacion4()>0 && (i+1)==4) {
				sentencia.setString(1, evaluacionDTO.getEvaluacion4());
				sentencia.setInt(2, evaluacionDTO.getPonderacion4());
				sentencia.setInt(3, evaluacionDTO.getIdPua());
				sentencia.executeUpdate();
			}
		}
		sentencia.close();
	}

	@Override
	public void insertarEvaluacionCompetencias(EvaluacionDTO evaluacionDTO) throws SQLException {
		PreparedStatement sentencia;
		sentencia = conn.prepareStatement(insertarEvaluacionCompetencias);
		for (int i = 0; i < 4; i++) {
			if(evaluacionDTO.getPonderacion1()>0 && (i+1)==1) {
				sentencia.setString(1, evaluacionDTO.getEvaluacion());
				sentencia.setInt(2, evaluacionDTO.getPonderacion1());
				sentencia.setInt(3, evaluacionDTO.getIdPua());
				sentencia.executeUpdate();
			} else if(evaluacionDTO.getPonderacion2()>0 && (i+1)==2) {
				sentencia.setString(1, evaluacionDTO.getEvaluacion2());
				sentencia.setInt(2, evaluacionDTO.getPonderacion2());
				sentencia.setInt(3, evaluacionDTO.getIdPua());
				sentencia.executeUpdate();
			} else if(evaluacionDTO.getPonderacion3()>0 && (i+1)==3) {
				sentencia.setString(1, evaluacionDTO.getEvaluacion3());
				sentencia.setInt(2, evaluacionDTO.getPonderacion3());
				sentencia.setInt(3, evaluacionDTO.getIdPua());
				sentencia.executeUpdate();
			} else if(evaluacionDTO.getPonderacion4()>0 && (i+1)==4) {
				sentencia.setString(1, evaluacionDTO.getEvaluacion4());
				sentencia.setInt(2, evaluacionDTO.getPonderacion4());
				sentencia.setInt(3, evaluacionDTO.getIdPua());
				sentencia.executeUpdate();
			}
		}
		sentencia.close();
	}
	
	@Override
	public List<DocenteDTO> obtenerDocentePua(int idPua) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<DocenteDTO> listaDocenteFacultad = new ArrayList<DocenteDTO>();
		DocenteDTO docenteDTO;

		sentencia = conn.prepareStatement(obtenerDocentePua);
		sentencia.setInt(1, idPua);
		rs = sentencia.executeQuery();


		while(rs.next()){
			docenteDTO = new DocenteDTO();
			docenteDTO.setIdDocente(rs.getInt(1));
			docenteDTO.setPrefijo(rs.getString(2));
			docenteDTO.setNombre(rs.getString(3));
			docenteDTO.setApellidoPaterno(rs.getString(4));
			docenteDTO.setApellidoMaterno(rs.getString(5));
			docenteDTO.setCorreo(rs.getString(6));

			listaDocenteFacultad.add(docenteDTO);
		}
		rs.close();
		sentencia.close();

		return listaDocenteFacultad;
	}

	@Override
	public void eliminarDocentePua(DocenteDTO docenteDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarDocentePua);
		sentencia.setInt(1, docenteDTO.getIdDocente());
		sentencia.setInt(2, docenteDTO.getIdPua());

		sentencia.executeUpdate();
		sentencia.close();

	}

	@Override
	public List<ActividadDTO> obtenerActividadesAlumno(int idSubcompetencia) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		ActividadDTO actividadDTO;
		List<ActividadDTO> listaActividades = new ArrayList<ActividadDTO>();

		sentencia = conn.prepareStatement(obtenerActividadesAlumno);
		sentencia.setInt(1, idSubcompetencia);

		rs = sentencia.executeQuery();

		while(rs.next()) {
			actividadDTO = new ActividadDTO();
			actividadDTO.setIdActividad(rs.getInt(1));
			actividadDTO.setActividad(rs.getString(2));
			actividadDTO.setRolDeActividad(rs.getString(3));
			actividadDTO.setIdSubcompetencia(rs.getInt(4));
			listaActividades.add(actividadDTO);
		}

		rs.close();
		sentencia.close();

		return listaActividades;
	}

	@Override
	public List<ActividadDTO> obtenerActividadesDocente(int idSubcompetencia) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		ActividadDTO actividadDTO;
		List<ActividadDTO> listaActividades = new ArrayList<ActividadDTO>();

		sentencia = conn.prepareStatement(obtenerActividadesDocente);
		sentencia.setInt(1, idSubcompetencia);

		rs = sentencia.executeQuery();

		while(rs.next()) {
			actividadDTO = new ActividadDTO();
			actividadDTO.setIdActividad(rs.getInt(1));
			actividadDTO.setActividad(rs.getString(2));
			actividadDTO.setRolDeActividad(rs.getString(3));
			actividadDTO.setIdSubcompetencia(rs.getInt(4));
			listaActividades.add(actividadDTO);
		}

		rs.close();
		sentencia.close();

		return listaActividades;
	}

	@Override
	public void insertarActividadDocente(ActividadDTO actividadDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(insertarActividadDocente);
		sentencia.setString(1, actividadDTO.getActividad());
		sentencia.setString(2, actividadDTO.getRolDeActividad());
		sentencia.setInt(3, actividadDTO.getIdSubcompetencia());

		sentencia.executeUpdate();

		sentencia.close();
	}

	@Override
	public void insertarActividadAlumno(ActividadDTO actividadDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(insertarActividadAlumno);
		sentencia.setString(1, actividadDTO.getActividad());
		sentencia.setString(2, actividadDTO.getRolDeActividad());
		sentencia.setInt(3, actividadDTO.getIdSubcompetencia());
		sentencia.executeUpdate();

		sentencia.close();
	}

	@Override
	public void insertarCompetenciaGenericaPua(GenericaDTO genericaDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(insertarGenericaPua);
		sentencia.setInt(1, genericaDTO.getIdPua());
		sentencia.setInt(2, genericaDTO.getIdGenerica());
		sentencia.executeUpdate();

		sentencia.close();
	}

	@Override
	public void insertarCompetenciaEspecificaPua(EspecificaDTO especificaDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(insertarEspecificaPua);
		sentencia.setInt(1, especificaDTO.getIdPua());
		sentencia.setInt(2, especificaDTO.getIdEspecifica());
		sentencia.executeUpdate();

		sentencia.close();
	}

	@Override
	public List<AmbienteDTO> obtenerListaAmbientes() throws SQLException {
		Statement sentencia;
		ResultSet rs;
		AmbienteDTO ambienteDTO;
		List<AmbienteDTO> listaAmbientes = new ArrayList<AmbienteDTO>();

		sentencia = conn.createStatement();
		rs = sentencia.executeQuery(obtenerAmbientes);

		while (rs.next()) {
			ambienteDTO = new AmbienteDTO();
			ambienteDTO.setIdAmbiente(rs.getInt(1));
			ambienteDTO.setAmbiente(rs.getString(2));
			listaAmbientes.add(ambienteDTO);
		}

		rs.close();
		sentencia.close();

		return listaAmbientes;
	}

	@Override
	public List<BibliografiaDTO> obtenerTiposBibliografia() throws SQLException {
		Statement sentencia;
		ResultSet rs;
		List<BibliografiaDTO> listaTiposBibliografia = new ArrayList<BibliografiaDTO>();
		BibliografiaDTO bibliografiaDTO;

		sentencia = conn.createStatement();
		rs = sentencia.executeQuery(obtenerTiposBibliografia);

		while (rs.next()) {
			bibliografiaDTO = new BibliografiaDTO();
			bibliografiaDTO.setIdTipoBibliografia(rs.getInt(1));
			bibliografiaDTO.setTipoBibliografia(rs.getString(2));
			listaTiposBibliografia.add(bibliografiaDTO);
		}

		rs.close();
		sentencia.close();

		return listaTiposBibliografia;
	}

	@Override
	public void insertarBibliografiaPua(BibliografiaDTO bibliografiaDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(insertarBibliografia);
		sentencia.setInt(1, bibliografiaDTO.getIdPua());
		sentencia.setInt(2, bibliografiaDTO.getIdLibro());
		sentencia.setInt(3, bibliografiaDTO.getIdTipoBibliografia());

		sentencia.executeUpdate();
	}

	@Override
	public List<LibroDTO> obtenerBibliografiaPua(int idPua) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<LibroDTO> listaBibliografia = new ArrayList<LibroDTO>();
		LibroDTO libroDTO;

		sentencia = conn.prepareStatement(obtenerBibliografiaPua);
		sentencia.setInt(1, idPua);
		rs = sentencia.executeQuery();

		while (rs.next()) {
			libroDTO = new LibroDTO();
			libroDTO.setIdLibro(rs.getInt(1));
			libroDTO.setLibro(rs.getString(2));
			libroDTO.setAutorPrincipal(rs.getString(4));
			libroDTO.setTipoBibliografia(rs.getString(9));
			libroDTO.setEditorial(rs.getString(11));
			listaBibliografia.add(libroDTO);

		}
		return listaBibliografia;
	}

	@Override
	public void eliminarCompetenciaGenerica(GenericaDTO genericaDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarCompetenciaGenerica);
		sentencia.setInt(1, genericaDTO.getIdPua());
		sentencia.setInt(2, genericaDTO.getIdGenerica());
		sentencia.executeUpdate();

		sentencia.close();
	}

	@Override
	public void eliminarCompetenciaEspecifica(EspecificaDTO especificaDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarCompetenciaEspecifica);
		sentencia.setInt(1, especificaDTO.getIdPua());
		sentencia.setInt(2, especificaDTO.getIdEspecifica());
		sentencia.executeUpdate();

		sentencia.close();
	}

	@Override
	public void eliminarBibliografiaPua(BibliografiaDTO bibliografiaDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarBibliografia);
		sentencia.setInt(1, bibliografiaDTO.getIdPua());
		sentencia.setInt(2, bibliografiaDTO.getIdLibro());
		sentencia.executeUpdate();

		sentencia.close();
	}

	@Override
	public void eliminarActividadAlumno(int idActividad) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarActividadAlumno);
		sentencia.setInt(1, idActividad);
		sentencia.executeUpdate();

		sentencia.close();

	}

	@Override
	public void eliminarActividadDocente(int idActividad) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarActividadDocente);
		sentencia.setInt(1, idActividad);
		sentencia.executeUpdate();

		sentencia.close();

	}

	@Override
	public void insertarAmbienteSubcompetencia(AmbienteDTO ambienteDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(insertarAmbiente);
		sentencia.setInt(1, ambienteDTO.getIdSubcompetencia());
		sentencia.setInt(2, ambienteDTO.getIdAmbiente());

		sentencia.executeUpdate();
		sentencia.close();
	}

	@Override
	public List<AmbienteDTO> obtenerAmbientesSubcompetencia(int idSubcompetencia) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<AmbienteDTO> listaAmbientesSub = new ArrayList<AmbienteDTO>();
		AmbienteDTO ambienteDTO;

		sentencia = conn.prepareStatement(obtenerAmbientesSub);
		sentencia.setInt(1, idSubcompetencia);

		rs = sentencia.executeQuery();

		while (rs.next()) {
			ambienteDTO = new AmbienteDTO();
			ambienteDTO.setIdSubcompetencia(rs.getInt(1));
			ambienteDTO.setIdAmbiente(rs.getInt(2));
			ambienteDTO.setAmbiente(rs.getString(4));
			listaAmbientesSub.add(ambienteDTO);
		}

		rs.close();
		sentencia.close();

		return listaAmbientesSub;
	}

	@Override
	public int obtenerPonderacionExcepto(int idPua, int idSubcompetencia) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		SubcompetenciaDTO subcompetenciaDTO;
		int ponderacionParcial = 0;

		sentencia = conn.prepareStatement(obtenerPonderacion);
		sentencia.setInt(1, idPua);
		sentencia.setInt(2, idSubcompetencia);
		rs = sentencia.executeQuery();

		while (rs.next()) {
			subcompetenciaDTO = new SubcompetenciaDTO();
			subcompetenciaDTO.setPonderacion(rs.getInt(1));
			ponderacionParcial += subcompetenciaDTO.getPonderacion();
			System.out.println("while " + ponderacionParcial);
		}

		return ponderacionParcial;
	}

	@Override
	public void eliminarAmbiente(AmbienteDTO ambienteDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarAmbiente);
		sentencia.setInt(1, ambienteDTO.getIdSubcompetencia());
		sentencia.setInt(2, ambienteDTO.getIdAmbiente());

		sentencia.executeUpdate();

		sentencia.close();
	}

	@Override
	public void eliminarCriterio(int idCriterio) throws SQLException {
		PreparedStatement sentencia;
		sentencia = conn.prepareStatement(eliminarCriterio);
		sentencia.setInt(1, idCriterio);
		sentencia.executeUpdate();
		sentencia.close();
	}

	@Override
	public int obtenerPonderacionEvidencias(int idSubcompetencia) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		EvidenciaDTO evidenciaDTO;
		int pondRestante = 0;

		sentencia = conn.prepareStatement(obtenerPonderacionEvidencias);
		sentencia.setInt(1, idSubcompetencia);

		rs = sentencia.executeQuery();

		while (rs.next()) {
			evidenciaDTO = new EvidenciaDTO();
			evidenciaDTO.setPonderacion(rs.getInt(1));
			pondRestante += evidenciaDTO.getPonderacion();
		}
		return pondRestante;
	}

	@Override
	public void insertarBibliografiaSubcompetencia(BibliografiaDTO bibliografiaDTO) throws SQLException {
		PreparedStatement sentencia;
		sentencia = conn.prepareStatement(insertarBibliografiaSub);

		sentencia.setInt(1, bibliografiaDTO.getIdSubcompetencia());
		sentencia.setInt(2, bibliografiaDTO.getIdLibro());
		sentencia.executeUpdate();

		sentencia.close();

	}

	@Override
	public List<BibliografiaDTO> obtenerBibliografiaSubcompetencia(int idSubcompetencia) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<BibliografiaDTO> listaBibliografiaSubcomp = new ArrayList<BibliografiaDTO>();
		BibliografiaDTO bibliografiaDTO;

		sentencia = conn.prepareStatement(obtenerBibliografiaSubcompetencia);
		sentencia.setInt(1, idSubcompetencia);

		rs = sentencia.executeQuery();

		while(rs.next()) {
			bibliografiaDTO = new BibliografiaDTO();
			bibliografiaDTO.setIdLibro(rs.getInt(1));
			bibliografiaDTO.setLibro(rs.getString(2));
			bibliografiaDTO.setIdSubcompetencia(rs.getInt(5));
			listaBibliografiaSubcomp.add(bibliografiaDTO);
		}

		sentencia.close();
		rs.close();

		return listaBibliografiaSubcomp;
	}

	@Override
	public void eliminarBibliografiaSubcompetencia(BibliografiaDTO bibliografiaDTO) throws SQLException {
		PreparedStatement sentencia;
		sentencia = conn.prepareStatement(eliminarBibliografiaSub);
		sentencia.setInt(1, bibliografiaDTO.getIdSubcompetencia());
		sentencia.setInt(2, bibliografiaDTO.getIdLibro());
		sentencia.executeUpdate();
		sentencia.close();

	}

	@Override
	public List<EvidenciaDTO> obtenerEvidenciasSubcompetencia(int idSubcompetencia) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<EvidenciaDTO> listaEvidenciasSub =
				new ArrayList<EvidenciaDTO>();
		EvidenciaDTO evidenciaDTO;

		sentencia = conn.prepareStatement(obtenerEvidenciasSub);
		sentencia.setInt(1, idSubcompetencia);
		rs = sentencia.executeQuery();

		while (rs.next()) {
			evidenciaDTO = new EvidenciaDTO();
			evidenciaDTO.setIdEvidencia(rs.getInt(1));
			evidenciaDTO.setEvidencia(rs.getString(2));
			evidenciaDTO.setIdSubcompetencia(rs.getInt(3));
			evidenciaDTO.setPonderacion(rs.getInt(5));
			listaEvidenciasSub.add(evidenciaDTO);
		}
		sentencia.close();
		rs.close();

		return listaEvidenciasSub;
	}

	@Override
	public void eliminarEvidencia(EvidenciaDTO evidenciaDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarEvidencia);
		sentencia.setInt(1, evidenciaDTO.getIdSubcompetencia());
		sentencia.setInt(2, evidenciaDTO.getIdEvidencia());

		sentencia.executeUpdate();
		sentencia.close();
	}

	@Override
	public List<PerfilDTO> obtenerPerfilesAcademicos(int idPua) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		PerfilDTO perfilDTO;
		List<PerfilDTO> listaPerfilesAcademicos = new ArrayList<PerfilDTO>();

		sentencia = conn.prepareStatement(obtenerPerfilesAcademicos);
		sentencia.setInt(1, idPua);

		rs = sentencia.executeQuery();

		while(rs.next()) {
			perfilDTO = new PerfilDTO();
			perfilDTO.setIdPerfil(rs.getInt(1));
			perfilDTO.setPerfil(rs.getString(2));
			perfilDTO.setIdPua(rs.getInt(3));
			listaPerfilesAcademicos.add(perfilDTO);
		}

		rs.close();
		sentencia.close();

		return listaPerfilesAcademicos;
	}

	@Override
	public List<PerfilDTO> obtenerPerfilesProfesional(int idPua) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		PerfilDTO perfilDTO;
		List<PerfilDTO> listaPerfilesProfesional = new ArrayList<PerfilDTO>();

		sentencia = conn.prepareStatement(obtenerPerfilesProfesionales);
		sentencia.setInt(1, idPua);

		rs = sentencia.executeQuery();

		while(rs.next()) {
			perfilDTO = new PerfilDTO();
			perfilDTO.setIdPerfil(rs.getInt(1));
			perfilDTO.setPerfil(rs.getString(2));
			perfilDTO.setIdPua(rs.getInt(3));
			listaPerfilesProfesional.add(perfilDTO);
		}

		rs.close();
		sentencia.close();

		return listaPerfilesProfesional;
	}

	@Override
	public List<PerfilDTO> obtenerPerfilesDocente(int idPua) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		PerfilDTO perfilDTO;
		List<PerfilDTO> listaPerfilesDocente = new ArrayList<PerfilDTO>();

		sentencia = conn.prepareStatement(obtenerPerfilesDocente);
		sentencia.setInt(1, idPua);

		rs = sentencia.executeQuery();

		while(rs.next()) {
			perfilDTO = new PerfilDTO();
			perfilDTO.setIdPerfil(rs.getInt(1));
			perfilDTO.setPerfil(rs.getString(2));
			perfilDTO.setIdPua(rs.getInt(3));
			listaPerfilesDocente.add(perfilDTO);

		}

		rs.close();
		sentencia.close();

		return listaPerfilesDocente;
	}

	@Override
	public void insertarPerfilAcademico(PerfilDTO perfilDTO) throws SQLException {
		PreparedStatement sentencia;
		sentencia = conn.prepareStatement(insertarPerfilAcad);
		sentencia.setString(1, perfilDTO.getPerfil());
		sentencia.setInt(2, perfilDTO.getIdPua());
		sentencia.executeUpdate();
		sentencia.close();
	}

	@Override
	public void insertarPerfilProfesional(PerfilDTO perfilDTO) throws SQLException {
		PreparedStatement sentencia;
		sentencia = conn.prepareStatement(insertarPerfilProf);
		sentencia.setString(1, perfilDTO.getPerfil());
		sentencia.setInt(2, perfilDTO.getIdPua());
		sentencia.executeUpdate();
		sentencia.close();

	}

	@Override
	public void insertarPerfilDocente(PerfilDTO perfilDTO) throws SQLException {
		PreparedStatement sentencia;
		sentencia = conn.prepareStatement(insertarPerfilDoc);
		sentencia.setString(1, perfilDTO.getPerfil());
		sentencia.setInt(2, perfilDTO.getIdPua());
		sentencia.executeUpdate();
		sentencia.close();
	}

	@Override
	public void eliminarPerfilAcademico(int idPerfil) throws SQLException {
			PreparedStatement sentencia;
			sentencia = conn.prepareStatement(eliminarPerfilAcad);
			sentencia.setInt(1, idPerfil);
			sentencia.executeUpdate();
			sentencia.close();

	}

	@Override
	public void eliminarPerfilProfesional(int idPerfil) throws SQLException {
			PreparedStatement sentencia;
			sentencia = conn.prepareStatement(eliminarPerfilProf);
			sentencia.setInt(1, idPerfil);
			sentencia.executeUpdate();
			sentencia.close();

	}

	@Override
	public void eliminarPerfilDocente(int idPerfil) throws SQLException {
			PreparedStatement sentencia;
			sentencia = conn.prepareStatement(eliminarPerfilDoc);
			sentencia.setInt(1, idPerfil);
			sentencia.executeUpdate();
			sentencia.close();

	}

	@Override
	public List<MaterialDTO> obtenerListaMateriales() throws SQLException {
		Statement sentencia;
		ResultSet rs;
		List<MaterialDTO> listaMateriales = new ArrayList<MaterialDTO>();
		MaterialDTO materialDTO;
		
		sentencia = conn.createStatement();
		rs = sentencia.executeQuery(obtenerListaMateriales);
		
		while (rs.next()) {
			materialDTO = new MaterialDTO();
			materialDTO.setIdMaterial(rs.getInt(1));
			materialDTO.setMaterial(rs.getString(2));
			listaMateriales.add(materialDTO);
		}
		
		return listaMateriales;
	}

	@Override
	public void insertarMaterial(MaterialDTO materialDTO) throws SQLException {
		PreparedStatement sentencia;
		
		sentencia = conn.prepareStatement(insertarMaterial);
		sentencia.setInt(1, materialDTO.getIdSubcompetencia());
		sentencia.setInt(2, materialDTO.getIdMaterial());
		
		sentencia.executeUpdate();
		
		sentencia.close();
	}

	@Override
	public List<MaterialDTO> obtenerListaMaterialesSubcompetencia(int idSubcompetencia) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<MaterialDTO> listaMaterialesSub = new ArrayList<MaterialDTO>();
		MaterialDTO materialDTO;
		
		sentencia = conn.prepareStatement(obtenerListaMaterialesSub);
		sentencia.setInt(1, idSubcompetencia);
		rs = sentencia.executeQuery();
		
		while (rs.next()) {
			materialDTO = new MaterialDTO();
			materialDTO.setIdSubcompetencia(rs.getInt(1));
			materialDTO.setIdMaterial(rs.getInt(3));
			materialDTO.setMaterial(rs.getString(4));
			listaMaterialesSub.add(materialDTO);
		}
		
		rs.close();
		sentencia.close();
		
		return listaMaterialesSub;
	}

	@Override
	public void eliminarMaterial(MaterialDTO materialDTO) throws SQLException {
		PreparedStatement sentencia;
		sentencia = conn.prepareStatement(eliminarMaterial);
		sentencia.setInt(1, materialDTO.getIdSubcompetencia());
		sentencia.setInt(2, materialDTO.getIdMaterial());
		sentencia.executeUpdate();
		sentencia.close();
		
	}

	@Override
	public List<EvaluacionDTO> obtenerListaDeEvaluacionCompetencias(int idPua) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<EvaluacionDTO> evaluacionCompetencias = new ArrayList<EvaluacionDTO>();
		
		sentencia = conn.prepareStatement(obtenerEvaluacionCompetencias);
		sentencia.setInt(1, idPua);
		rs = sentencia.executeQuery();
		int x=1;
		while(rs.next()){
			EvaluacionDTO evaluacionCompetenciasDTO=new EvaluacionDTO();
			evaluacionCompetenciasDTO.setIdEvaluacion(rs.getInt(1));
			evaluacionCompetenciasDTO.setEvaluacion(rs.getString(2));
			evaluacionCompetenciasDTO.setPonderacion1(rs.getInt(3));
			evaluacionCompetenciasDTO.setIdPua(idPua);
			evaluacionCompetencias.add(evaluacionCompetenciasDTO);
		}
		rs.close();
		sentencia.close();

		return evaluacionCompetencias;
	}

	@Override
	public void eliminarEvaluacionCompetencias(EvaluacionDTO evaluacionDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarEvaluacionCompetencias);
		sentencia.setInt(1,evaluacionDTO.getIdPua());

		sentencia.executeUpdate();
		sentencia.close();
		
	}

	@Override
	public List<EvaluacionFinalDTO> obtenerListaDeEvaluacionFinal(int idPua) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		
		List<EvaluacionFinalDTO> listaEvaluacion=new ArrayList<EvaluacionFinalDTO>();
		sentencia = conn.prepareStatement(obtenerEvaluacionFinal);
		sentencia.setInt(1, idPua);
		rs = sentencia.executeQuery();

		while(rs.next()){
			EvaluacionFinalDTO evaluacionFinalDTO = new EvaluacionFinalDTO();
			evaluacionFinalDTO.setIdEvaluacion(rs.getInt(1));
			evaluacionFinalDTO.setEvaluacion(rs.getString(2));
			evaluacionFinalDTO.setPonderacion1(rs.getInt(3));
			evaluacionFinalDTO.setIdPua(idPua);
			listaEvaluacion.add(evaluacionFinalDTO);
		}
		rs.close();
		sentencia.close();

		return listaEvaluacion;
	}

	@Override
	public void eliminarEvaluacionFinal(EvaluacionFinalDTO evaluacionFinalDTO) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarEvaluacionFinal);
		sentencia.setInt(1,evaluacionFinalDTO.getIdPua());

		sentencia.executeUpdate();
		sentencia.close();
		
	}

	@Override
	public AcademiaDTO obtenerAcademia(int idMateria) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		AcademiaDTO academiaDTO = new AcademiaDTO();

		sentencia = conn.prepareStatement(obtenerAcademia);
		sentencia.setInt(1, idMateria);
		
		rs = sentencia.executeQuery();

		if(rs.next()){
			academiaDTO.setIdMateria(rs.getInt(1));
			academiaDTO.setNombreAcademia(rs.getString(2));
			academiaDTO.setIdPresidente(rs.getInt(3));
			academiaDTO.setIdSecretario(rs.getInt(4));

			
		}

		rs.close();
		sentencia.close();

		return academiaDTO;
	}

	@Override
	public List<MateriaDTO> obtenerListaMateriaDocente(int idDocente, int idCarrera) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<MateriaDTO> listaDocenteMateria = new ArrayList<MateriaDTO>();
		MateriaDTO materiaDTO;

		sentencia = conn.prepareStatement(obtenerPuaPorId);
		sentencia.setInt(1, idDocente);
		sentencia.setInt(2, idCarrera);
		rs = sentencia.executeQuery();

		while(rs.next()){
			materiaDTO = new MateriaDTO();
			materiaDTO.setIdMateria(rs.getInt(1));
			materiaDTO.setNombreMateria(rs.getString(2));
			listaDocenteMateria.add(materiaDTO);
		}
		rs.close();
		sentencia.close();

		return listaDocenteMateria;
	}
	
	@Override
	public MateriaDTO obtenerMateriaPorID(MateriaDTO materiaDTO) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;

		sentencia = conn.prepareStatement(obtenerMateriaPorId);
		sentencia.setInt(1, materiaDTO.getIdMateria());
		rs = sentencia.executeQuery();

		if(rs.next()){
			materiaDTO.setNombreMateria(rs.getString(1));
		}
		rs.close();
		sentencia.close();

		return materiaDTO;
	}
	
	@Override
	public List<FacultadDTO> obtenerDocenteFacultadId(int idDocente, int tipoDocente) throws SQLException {
		PreparedStatement sentencia = null;
		ResultSet rs;
		List<FacultadDTO> listaDocenteFacultad = new ArrayList<FacultadDTO>();
		if(tipoDocente==1 || tipoDocente==2) {
			sentencia = conn.prepareStatement(obtenerFacultadDirecctor);
			sentencia.setInt(1, idDocente);
			sentencia.setInt(2, idDocente);
		} else if(tipoDocente==3) {
			sentencia = conn.prepareStatement(obtenerFacultadCoordinador);
			sentencia.setInt(1, idDocente);
		} else if(tipoDocente==4 || tipoDocente==5) {
			sentencia = conn.prepareStatement(obtenerFacultadAcademia);
			sentencia.setInt(1, idDocente);
			sentencia.setInt(2, idDocente);
		} else {
			sentencia = conn.prepareStatement(obtenerDocenteFacultadId);
			sentencia.setInt(1, idDocente);
		}
		rs = sentencia.executeQuery();

		while(rs.next()){
			FacultadDTO facultadDTO=new FacultadDTO();
			facultadDTO.setIdFacultad(rs.getInt(1));
			facultadDTO.setFacultad(rs.getString(2));
			listaDocenteFacultad.add(facultadDTO);
		}
		
		/*if(tipoDocente==4 || tipoDocente==5) {
			listaDocenteFacultad=facultadDuplicados(listaDocenteFacultad);
		}*/
		rs.close();
		sentencia.close();
		return listaDocenteFacultad;
	}
	
	@Override
	public List<CarreraDTO> obtenerCarreraCoordinador(int idDocente) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<CarreraDTO> listaCoordinadorCarrera = new ArrayList<CarreraDTO>();
		sentencia = conn.prepareStatement(obtenerCarreraCoordinador);
		sentencia.setInt(1, idDocente);
		rs = sentencia.executeQuery();

		while(rs.next()){
			int aux=rs.getInt(2);
			CarreraDTO carreraDTO=new CarreraDTO();
			carreraDTO.setIdCarrera(rs.getInt(1));
			carreraDTO.setIdFacultad(rs.getInt(2));
			carreraDTO.setNombreCarrera(rs.getString(3));
			carreraDTO.setPlanEstudios(rs.getInt(4));
			listaCoordinadorCarrera.add(carreraDTO);
		}
		rs.close();
		sentencia.close();
		return listaCoordinadorCarrera;
	}
		
	@Override
	public List<CarreraDTO> obtenerCarreraMateria(int idFacultad, int idDocente, String accion) throws SQLException {
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		
		if(accion.equals("academia")){
			sentencia = conn.prepareStatement(obtenerCarreraAcademia);
			sentencia.setInt(1, idFacultad);
			sentencia.setInt(2, idDocente);
			sentencia.setInt(3, idDocente);
			sentencia.setInt(4, idDocente);
		} else if(accion.equals("docente")){
			sentencia = conn.prepareStatement(obtenerCarreraDocente);
			sentencia.setInt(1, idFacultad);
			sentencia.setInt(2, idDocente);
		}
		
		rs = sentencia.executeQuery();
		List<CarreraDTO> listaCarrera = new ArrayList<CarreraDTO>();
		while(rs.next()){
			CarreraDTO carreraDTO=new CarreraDTO();
			carreraDTO.setIdCarrera(rs.getInt(1));
			carreraDTO.setNombreCarrera(rs.getString(2));
			carreraDTO.setPlanEstudios(rs.getInt(3));
			listaCarrera.add(carreraDTO);
		}
		rs.close();
		sentencia.close();
		
		return listaCarrera;
	}
	
	@Override
	public List<MateriaDTO> obtenerMateriaAcademia(int idDocente, int idCarrera) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<MateriaDTO> listaMateriaAcademia = new ArrayList<MateriaDTO>();
		sentencia = conn.prepareStatement(obtenerAcademiaMateria);
		sentencia.setInt(1, idCarrera);
		sentencia.setInt(2, idDocente);
		sentencia.setInt(3, idDocente);
		rs = sentencia.executeQuery();

		while(rs.next()){
			MateriaDTO materiaDTO=new MateriaDTO();
			materiaDTO.setIdMateria(rs.getInt(1));
			materiaDTO.setNombreMateria(rs.getString(2));
			listaMateriaAcademia.add(materiaDTO);
		}
		rs.close();
		sentencia.close();
		return listaMateriaAcademia;
	}

	@Override
	public boolean eliminarSubcompetenciaM(int idSubcompetencia) throws SQLException {
		CallableStatement sp = conn.prepareCall("CALL 	eliminarSubcompetencia(?)");
		sp.setInt("subcompetencia", idSubcompetencia);
		return sp.execute();
	}
	
	@Override
	public void eliminarAllBibliografia(int idPua) throws SQLException {
		PreparedStatement sentencia;

		sentencia = conn.prepareStatement(eliminarAllBibliografia);
		sentencia.setInt(1, idPua);
		sentencia.executeUpdate();

		sentencia.close();
	}
	
	@Override
	public void eliminarCompetenciaDocente(int idPua) throws SQLException {
		CallableStatement sp = conn.prepareCall("CALL 	eliminarDocenteCompetencia (?)");
		sp.setInt("pua", idPua);
		sp.execute();
	}

	
	/*private List<CarreraDTO> datosDuplicados(List<CarreraDTO> listaCarrera) {
		List<Integer> ids=new ArrayList<Integer>();
		List<String> nombre=new ArrayList<String>();
		for (int i=0; i<listaCarrera.size(); i++) {
			ids.add(listaCarrera.get(i).getIdCarrera());
			nombre.add(listaCarrera.get(i).getNombreCarrera());
		}
		listaCarrera=new ArrayList<CarreraDTO>();
		
		HashSet<String> hs = new HashSet<>();
		hs.addAll(nombre);
		nombre.clear();
		nombre.addAll(hs);
		
		HashSet<Integer> sh = new HashSet<>();
		sh.addAll(ids);
		ids.clear();
		ids.addAll(sh);
		
		for(int i=0; i<ids.size(); i++){
			CarreraDTO carreraDTO=new CarreraDTO();
			carreraDTO.setIdCarrera(ids.get(i));
			carreraDTO.setNombreCarrera(nombre.get(i));
			listaCarrera.add(carreraDTO);
		}
		return listaCarrera;
	}
	
	private List<FacultadDTO> facultadDuplicados(List<FacultadDTO> listaFacultad) {
		List<Integer> ids=new ArrayList<Integer>();
		List<String> nombre=new ArrayList<String>();
		for (int i=0; i<listaFacultad.size(); i++) {
			ids.add(listaFacultad.get(i).getIdFacultad());
			nombre.add(listaFacultad.get(i).getFacultad());
		}
		listaFacultad=new ArrayList<FacultadDTO>();
		
		HashSet<String> hs = new HashSet<>();
		hs.addAll(nombre);
		nombre.clear();
		nombre.addAll(hs);
		
		HashSet<Integer> sh = new HashSet<>();
		sh.addAll(ids);
		ids.clear();
		ids.addAll(sh);
		
		for(int i=0; i<ids.size(); i++){
			FacultadDTO facultadDTO=new FacultadDTO();
			facultadDTO.setIdFacultad(ids.get(i));
			facultadDTO.setFacultad(nombre.get(i));
			listaFacultad.add(facultadDTO);
		}
		return listaFacultad;
	}*/
}