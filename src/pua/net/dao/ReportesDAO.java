package pua.net.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pua.net.dto.EspecificaDTO;
import pua.net.dto.GenericaDTO;

public abstract class ReportesDAO {
	
	public abstract List<Integer> getNumMaterias(int id_facultad, List<GenericaDTO> listaG) throws SQLException;
	public abstract List<GenericaDTO> getGenericas() throws SQLException;
	public abstract List<Integer> getNumGenericas(List<GenericaDTO> listg);
	public abstract List<Integer> getNumMateriasCarrera(int id_facultad, int id_carrera, List<GenericaDTO> listaG) throws SQLException;
	public abstract List<Integer> getNumMateriasE(int id_carrera, List<EspecificaDTO> liste) throws SQLException;
	public abstract List<Integer> getNumEspecificas(List<EspecificaDTO> listaE) throws SQLException;
}
