package pua.net.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pua.net.dao.DAOFactory;
import pua.net.dao.PuaDAO;
import pua.net.dao.ReportesDAO;
import pua.net.dto.EspecificaDTO;
import pua.net.dto.GenericaDTO;


public class MySQLReporteDAO extends ReportesDAO{
	Connection conn;
	DAOFactory daoFactory = null;
	PuaDAO puaDAO;
	Iterator<GenericaDTO> it;
	Iterator<Integer> it2;
	//private static final String obtenerGenericaFacultad ="select * from vista_genericas where id_generica= ? and id_facultad=?";
	private static final String obtenerNumeroMaterias ="select count(*) from vista_genericas where id_facultad= ? and id_generica= ?";
	private static final String obtenerGenericaCarrera ="select count(*) from vista_genericas where id_facultad=? and id_carrera=? and id_generica= ?";
	private static final String obtenerNumMateriasEsp ="select count(*) from vista_especificas where id_carrera=? and id_especifica=?";
	

	
	public MySQLReporteDAO(Connection conn) {
		this.conn = conn;
		try{
			daoFactory = DAOFactory.getDAOFactory(1, conn);
			puaDAO = daoFactory.getPuaDAO();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	@Override
	public List<Integer> getNumMaterias(int id_facultad, List<GenericaDTO> listaG) throws SQLException {
		// obtener numero de materias por carrera
		PreparedStatement sentencia;
		ResultSet rs;
		List<Integer> numMaterias = new ArrayList<Integer>();
				
			for(int i=1; i<=listaG.size(); i++){
				sentencia = conn.prepareStatement(obtenerNumeroMaterias);
				sentencia.setInt(1, id_facultad);
				
			sentencia.setInt(2, i);
			rs = sentencia.executeQuery();
			
			while(rs.next()){
			int numero = rs.getInt(1);
			numMaterias.add(numero);
			}
			
			
		}
		return numMaterias;
	}
	
	@Override
	public List<GenericaDTO> getGenericas() throws SQLException{ 
		GenericaDTO gdto = new GenericaDTO();
		List<GenericaDTO> listaG = puaDAO.obtenerListaCompetenciasGenericas();
				
		return listaG;
	}
	
	@Override
	public List<Integer> getNumGenericas(List<GenericaDTO> listg){
		Iterator<GenericaDTO> it;
		 GenericaDTO g = new GenericaDTO();
		 List<Integer> numGenericas = new ArrayList<Integer>();
		 it = listg.iterator();

		 while(it.hasNext()){
			 g = it.next();
			 numGenericas.add(g.getIdGenerica());
		 }
		 
		 return numGenericas;
	}
	
	@Override
	public List<Integer> getNumMateriasCarrera(int id_facultad, int id_carrera, List<GenericaDTO> listaG) throws SQLException {
		// obtener numero de materias por carrera
		PreparedStatement sentencia;
		ResultSet rs;
		List<Integer> numMaterias = new ArrayList<Integer>();
				
			for(int i=1; i<=listaG.size(); i++){
				sentencia = conn.prepareStatement(obtenerGenericaCarrera);
				sentencia.setInt(1, id_facultad);
				sentencia.setInt(2, id_carrera);
				
			sentencia.setInt(3, i);
			rs = sentencia.executeQuery();
			
			while(rs.next()){
			int numero = rs.getInt(1);
			numMaterias.add(numero);
			}	
		}
		return numMaterias;
	}


	@Override
	public List<Integer> getNumMateriasE(int id_carrera, List<EspecificaDTO> liste) throws SQLException {
		PreparedStatement sentencia;
		ResultSet rs;
		List<Integer> numMaterias = new ArrayList<Integer>();
		EspecificaDTO edto;
				
			for(int i=0; i<liste.size(); i++){
				edto = new EspecificaDTO();
				edto = liste.get(i);
				sentencia = conn.prepareStatement(obtenerNumMateriasEsp);
				sentencia.setInt(1, id_carrera);
				sentencia.setInt(2, edto.getIdEspecifica());
			rs = sentencia.executeQuery();
			
			while(rs.next()){
			int numero = rs.getInt(1);
			numMaterias.add(numero);
			}	
		}
		return numMaterias;
	}


	@Override
	public List<Integer> getNumEspecificas(List<EspecificaDTO> listaE) throws SQLException {
		EspecificaDTO edto = new EspecificaDTO();
		Iterator<EspecificaDTO> it;
		 List<Integer> numEspecificas = new ArrayList<Integer>();
		 it = listaE.iterator();

		 while(it.hasNext()){
			 edto = it.next();
			 numEspecificas.add(edto.getIdEspecifica());
		 }
		 return numEspecificas;
	}

	
}
 