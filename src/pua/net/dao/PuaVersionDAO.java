package pua.net.dao;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;

import pua.hibernate.*;
public abstract class PuaVersionDAO {
	//save
	public abstract int save(Object objeto);
	
	//update
	public abstract void update(Object objeto) throws HibernateException;
	public abstract void updateSubcompetencia(Pua pua, int idPua) throws HibernateException;
	public abstract void updateEvaluacion(Pua pua, int idPua) throws HibernateException;
	public abstract void updateBibliografiaPua(Pua pua, int idPua) throws HibernateException;
	
	//delete
	public abstract void delete(Object objeto) throws HibernateException;
	public abstract void deleteSubcompetencia(int idPua, PuaDAO puaManyToMany) throws HibernateException, DAOException, SQLException;
	public abstract void deleteEvaluacion(int idPua) throws HibernateException;
	
	//read
	public abstract Pua getPua(int idPua) throws HibernateException;
	public abstract List<PuaVersion> getListaPuaVersion(String busqueda, int orden) throws HibernateException;
	public abstract int getVersion(String nombreMateria) throws HibernateException;
	public abstract int getPuasMax() throws HibernateException;
	public abstract PuaVersion getPuaVersion(int idPua) throws HibernateException;
	public abstract List<Pua> getListaPua(String busqueda, int orden) throws HibernateException;
	public abstract List<PuaVersion> getAllListaPuaVersion() throws HibernateException;
	public abstract List<Pua> getAllListaPua() throws HibernateException;
	public abstract List<Subcompetencia> getAllListaSubcompetencia(int idPua) throws HibernateException;
}