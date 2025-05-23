package pua.net.dao.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import pua.net.dao.AdministradorDAO;
import pua.net.dao.DAOException;
import pua.net.dao.PuaVersionDAO;
import pua.net.dto.AcademiaDTO;
import pua.net.dto.CarreraDTO;
import pua.net.dto.DocenteDTO;
import pua.net.dto.FacultadDTO;
import pua.net.dto.MateriaDTO;

import pua.hibernate.*;

public class MySQL_Listas {
	private static List<FacultadDTO> listaFacultad;
	private static List<MateriaDTO> listaNucleo;
	private static List<MateriaDTO> listaArea;
	private static List<MateriaDTO> listaTipo;
	private static List<CarreraDTO> listaCarrera;
	private static List<DocenteDTO> listaTipoDocente;
	private static List<MateriaDTO> listaMateria;
	private static List<AcademiaDTO> listaAcademia;
	private static List<PuaVersion> listaPuaVersion;
	private static List<Pua> listaPua;
	private static MySQL_Listas singleton;
	private static MySQL_Listas hibernate;
	
	//lists with connection
	private MySQL_Listas(AdministradorDAO adminDAO) {
		try {
			listaFacultad=adminDAO.obtenerListaFacultad();
			listaArea=adminDAO.obtenerListaArea();
			listaNucleo=adminDAO.obtenerListaNucleo();
			listaTipo=adminDAO.obtenerListaTipo();
			listaTipoDocente=adminDAO.obtenerListaTipoDocente();
			listaCarrera=adminDAO.obtenerListaCarreras(-1);
			listaMateria=adminDAO.obtenerListaMateria();
			listaAcademia=adminDAO.obtenerListaAcademia(-1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static MySQL_Listas getInstance(AdministradorDAO adminDAO) throws DAOException {
		try{
	        if (singleton == null){
	            singleton = new MySQL_Listas(adminDAO);
	        }
		}catch(Exception e){
			throw new DAOException(e);
		}
		
        return singleton;
    }
	
	public static void reset() {
		singleton=null;
	}
	
	//list with hibernate
	private MySQL_Listas(PuaVersionDAO puaDAO) {
		listaPuaVersion=puaDAO.getAllListaPuaVersion();
		listaPua=puaDAO.getAllListaPua();
	}
	public static MySQL_Listas getInstance(PuaVersionDAO puaDAO) throws HibernateException {
		try{
	        if (hibernate == null){
	        	hibernate = new MySQL_Listas(puaDAO);
	        }
		}catch(Exception e){
			throw new HibernateException(e);
		}
		
        return hibernate;
    }
	
	public static void resetHibernate() {
		hibernate=null;
	}
	
	//setters and getters
	public static List<AcademiaDTO> getListaAcademia() {
		return listaAcademia;
	}
	public static void setListaAcademia(List<AcademiaDTO> listaAcademia) {
		MySQL_Listas.listaAcademia = listaAcademia;
	}
	public static List<MateriaDTO> getListaMateria() {
		return listaMateria;
	}
	public static void setListaMateria(List<MateriaDTO> listaMateria) {
		MySQL_Listas.listaMateria = listaMateria;
	}
	public static List<FacultadDTO> getListaFacultad() {
		return listaFacultad;
	}
	public static void setListaFacultad(List<FacultadDTO> listaFacultad) {
		MySQL_Listas.listaFacultad = listaFacultad;
	}
	public static List<MateriaDTO> getListaNucleo() {
		return listaNucleo;
	}
	public static void setListaNucleo(List<MateriaDTO> listaNucleo) {
		MySQL_Listas.listaNucleo = listaNucleo;
	}
	public static List<MateriaDTO> getListaArea() {
		return listaArea;
	}
	public static void setListaArea(List<MateriaDTO> listaArea) {
		MySQL_Listas.listaArea = listaArea;
	}
	public static List<MateriaDTO> getListaTipo() {
		return listaTipo;
	}
	public static void setListaTipo(List<MateriaDTO> listaTipo) {
		MySQL_Listas.listaTipo = listaTipo;
	}
	public static List<CarreraDTO> getListaCarrera() {
		return listaCarrera;
	}
	public static void setListaCarrera(List<CarreraDTO> listaCarrera) {
		MySQL_Listas.listaCarrera = listaCarrera;
	}
	public static List<DocenteDTO> getListaTipoDocente() {
		return listaTipoDocente;
	}
	public static void setListaTipoDocente(List<DocenteDTO> listaTipoDocente) {
		MySQL_Listas.listaTipoDocente = listaTipoDocente;
	}
	public static MySQL_Listas getSingleton() {
		return singleton;
	}
	public static void setSingleton(MySQL_Listas singleton) {
		MySQL_Listas.singleton = singleton;
	}
	public static List<PuaVersion> getListaPuaVersion() {
		return listaPuaVersion;
	}
	public static List<Pua> getListaPua() {
		return listaPua;
	}
	public static void setListaPuaVersion(List<PuaVersion> listaPuaVersion) {
		MySQL_Listas.listaPuaVersion = listaPuaVersion;
	}
	public static void setListaPua(List<Pua> listaPua) {
		MySQL_Listas.listaPua = listaPua;
	}	
}