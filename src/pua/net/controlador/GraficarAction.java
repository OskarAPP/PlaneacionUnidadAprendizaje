package pua.net.controlador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opensymphony.xwork2.ActionSupport;
import pua.net.dao.DAOFactory;
import pua.net.dao.PuaDAO;
import pua.net.dao.ReportesDAO;
import pua.net.dto.FacultadDTO;
import pua.net.dto.GenericaDTO;
import pua.net.modelo.Grafica;

public class GraficarAction extends ActionSupport implements ServletContextAware {
	private ServletContext servletContext;
	private String filename;
	private String resultado;
	private Grafica graficas;
	private byte[] imgBytes;
	private InputStream inputStream;
	private String tipoAccion;
	private Logger log;
	private PuaDAO puaDAO;
	private ReportesDAO reportes;
	private List<Integer> numMaterias;
	private List<Integer> numGenericas;
	private List<Integer> numMateriasCarrera;
	//MySQLReporteDAO reportes; //<---AQUI
	private FacultadDTO facultadDTO;
	private List<FacultadDTO> listaFacultad;
	private int idFacultad;
	private int idCarrera;
	private List<GenericaDTO> listagen;
	private List<String> lista;
	private int fin;
	private DAOFactory daoFactory;

	public GraficarAction() {
		log = LoggerFactory.getLogger(GraficarAction.class);
	}

	public void setSession(Map<String, Object> arg0) {
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}

	public String execute() throws IOException {

		if (log.isDebugEnabled()) {
			log.debug("Accion: " + tipoAccion);
		}

		if(tipoAccion == null ){
			tipoAccion = "nada";
		}
		filename = "Y.jpg";

		numMaterias = new ArrayList<Integer>();
		numGenericas = new ArrayList<Integer>();
		
		//DAOFactory daoFactory = null;
		
		
	
		try {
			daoFactory = DAOFactory.getDAOFactory(Integer.parseInt(servletContext.getInitParameter("TIPO")),
					(String) servletContext.getInitParameter("DRIVER"), (String) servletContext.getInitParameter("IP"),
					(String) servletContext.getInitParameter("BD"), (String) servletContext.getInitParameter("USUARIO"),
					(String) servletContext.getInitParameter("PASS"));
			
			puaDAO = daoFactory.getPuaDAO();
			reportes = daoFactory.getReportesDAO();
			 //reportes = new MySQLReporteDAO(daoFactory.getConexion());
			 
			 
			 
			if (tipoAccion.equals("nada")) {

				resultado = "reportesPrincipal";
				listaFacultad = puaDAO.obtenerListaFacultad();
				

			}
			//idFacultad = 1;
			graficas = new Grafica();
			listagen = puaDAO.obtenerListaCompetenciasGenericas();
			numGenericas = reportes.getNumGenericas(listagen);
			numMaterias = reportes.getNumMaterias(idFacultad, reportes.getGenericas());
			numMateriasCarrera = reportes.getNumMateriasCarrera(idFacultad, idCarrera, reportes.getGenericas());
			lista = new ArrayList<String>();
			fin = numGenericas.size()-1;
			
			for (int i=0; i<listagen.size();i++){
				if (listagen.get(i).getIdGenerica()==numGenericas.get(i)){
					lista.add(listagen.get(i).getNombreGenerica());
				}
			}
			
			if(log.isDebugEnabled()){
				log.debug("idFacultad: " + idFacultad);
			}
			
			if (tipoAccion.equals("graficaFacultad")){
				resultado = "graficaFacultad";
				imgBytes = graficas.getGrafica("Competencias Genericas",numGenericas, numMaterias);
				inputStream = new ByteArrayInputStream(imgBytes);
			}
			
			if (tipoAccion.equals("tablaFacultad")) {
				
				resultado = "tablaFacultadDatos";
				//sacar datos de la bd para la tabla
			}
			
			if (tipoAccion.equals("graficaCarrera")){
				resultado = "graficaCarrera";
				imgBytes = graficas.getGrafica("Competencias Genericas",numGenericas, numMateriasCarrera);
				inputStream = new ByteArrayInputStream(imgBytes);
			}
			
			if (tipoAccion.equals("tablaCarrera")) {
				
				resultado = "tablaCarreraDatos";
				//sacar datos de la bd para la tabla
			}
			

		} catch (Exception e) {
			e.printStackTrace();

		}
	

		return resultado;
	}

	
	
	public List<Integer> getNumMateriasCarrera() {
		return numMateriasCarrera;
	}

	public void setNumMateriasCarrera(List<Integer> numMateriasCarrera) {
		this.numMateriasCarrera = numMateriasCarrera;
	}

	public List<Integer> getNumMaterias() {
		return numMaterias;
	}

	public void setNumMaterias(List<Integer> numMaterias) {
		this.numMaterias = numMaterias;
	}

	public List<Integer> getNumGenericas() {
		return numGenericas;
	}

	public void setNumGenericas(List<Integer> numGenericas) {
		this.numGenericas = numGenericas;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Grafica getGraficas() {
		return graficas;
	}

	public void setGraficas(Grafica graficas) {
		this.graficas = graficas;
	}

	public byte[] getImgBytes() {
		return imgBytes;
	}

	public void setImgBytes(byte[] imgBytes) {
		this.imgBytes = imgBytes;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public List<FacultadDTO> getListaFacultad() {
		return listaFacultad;
	}
	
	public void setListaFacultad(List<FacultadDTO> listaFacultad) {
		this.listaFacultad = listaFacultad;
	}
	public int getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(int idFacultad) {
		this.idFacultad = idFacultad;
	}

	public int getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(int idCarrera) {
		this.idCarrera = idCarrera;
	}

	public PuaDAO getPuaDAO() {
		return puaDAO;
	}

	public void setPuaDAO(PuaDAO puaDAO) {
		this.puaDAO = puaDAO;
	}

	public ReportesDAO getReportes() {
		return reportes;
	}

	public void setReportes(ReportesDAO reportes) {
		this.reportes = reportes;
	}

	public FacultadDTO getFacultadDTO() {
		return facultadDTO;
	}

	public void setFacultadDTO(FacultadDTO facultadDTO) {
		this.facultadDTO = facultadDTO;
	}

	public DAOFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public List<String> getLista() {
		return lista;
	}

	public void setLista(List<String> lista) {
		this.lista = lista;
	}

	public int getFin() {
		return fin;
	}

	public void setFin(int fin) {
		this.fin = fin;
	}

	public List<GenericaDTO> getListagen() {
		return listagen;
	}

	public void setListagen(List<GenericaDTO> listagen) {
		this.listagen = listagen;
	}
	
	
}