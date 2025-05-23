package pua.net.controlador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import pua.net.dto.EspecificaDTO;
import pua.net.dto.FacultadDTO;
import pua.net.modelo.Grafica;

public class GraficaEspecificaAction extends ActionSupport implements ServletContextAware {
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
	private List<Integer> numMateriasE;
	private List<Integer> numEspecificas;
	private FacultadDTO facultadDTO;
	private List<FacultadDTO> listaFacultad;
	private List<EspecificaDTO> listaEspecifica;
	private List<String> lista;
	private int idFacultad;
	private int idCarrera;
	private DAOFactory daoFactory;
	private int fin;
	private String mensajeR ="";
	private String mensaje="";
	
	public GraficaEspecificaAction() {
		log = LoggerFactory.getLogger(GraficaEspecificaAction.class);
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
		filename = "X.jpg";

		numMateriasE = new ArrayList<Integer>();
		numEspecificas = new ArrayList<Integer>();
		
		//DAOFactory daoFactory = null;
		
		
	
		try {
			daoFactory = DAOFactory.getDAOFactory(Integer.parseInt(servletContext.getInitParameter("TIPO")),
					(String) servletContext.getInitParameter("DRIVER"), (String) servletContext.getInitParameter("IP"),
					(String) servletContext.getInitParameter("BD"), (String) servletContext.getInitParameter("USUARIO"),
					(String) servletContext.getInitParameter("PASS"));
			
			puaDAO = daoFactory.getPuaDAO();
			reportes = daoFactory.getReportesDAO();
			
			if (tipoAccion.equals("nada")) {

				resultado = "reportesEspecifica";
				listaFacultad = puaDAO.obtenerListaFacultad();
			}
			
			
			if(log.isDebugEnabled()){
				log.debug("idFacultad: " + idFacultad);
				log.debug("idCarrera: " + idCarrera);
			}
			
			graficas = new Grafica();
			listaEspecifica = puaDAO.obtenerListaCompetenciasEspecificas(idCarrera);
			if (listaEspecifica.size()>0){
				numMateriasE = reportes.getNumMateriasE(idCarrera, listaEspecifica);
				numEspecificas = reportes.getNumEspecificas(listaEspecifica);
				lista = new ArrayList<String>();
				fin = numEspecificas.size()-1;
				
				
				for (int i=0; i<listaEspecifica.size();i++){
					if (listaEspecifica.get(i).getIdEspecifica()==numEspecificas.get(i)){
						lista.add(listaEspecifica.get(i).getPerfilEspecifica());
					}
				}
			}
			if (tipoAccion.equals("graficaEspecificaImagen")){

				resultado = "graficaEspecificaImagen";	
				imgBytes = graficas.getGrafica("Competencias Específicas",numEspecificas, numMateriasE);
				inputStream = new ByteArrayInputStream(imgBytes);
			}
			
			if (tipoAccion.equals("tablaEspecifica")) {
				resultado="vacio";
				mensajeR="fallo";
				mensaje="No hay datos registrados";
				
				
				if (listaEspecifica.size()>0){
					resultado = "tablaEspecificaDatos";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
	
		}


		return resultado;
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
	public String getTipoAccion() {
		return tipoAccion;
	}
	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}
	public List<Integer> getNumMateriasE() {
		return numMateriasE;
	}
	public void setNumMateriasE(List<Integer> numMateriasE) {
		this.numMateriasE = numMateriasE;
	}
	public List<Integer> getNumEspecificas() {
		return numEspecificas;
	}
	public void setNumEspecificas(List<Integer> numEspecificas) {
		this.numEspecificas = numEspecificas;
	}
	public FacultadDTO getFacultadDTO() {
		return facultadDTO;
	}
	public void setFacultadDTO(FacultadDTO facultadDTO) {
		this.facultadDTO = facultadDTO;
	}
	public List<FacultadDTO> getListaFacultad() {
		return listaFacultad;
	}
	public void setListaFacultad(List<FacultadDTO> listaFacultad) {
		this.listaFacultad = listaFacultad;
	}
	public List<EspecificaDTO> getListaEspecifica() {
		return listaEspecifica;
	}
	public void setListaEspecifica(List<EspecificaDTO> listaEspecifica) {
		this.listaEspecifica = listaEspecifica;
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
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
	public String getMensajeR() {
		return mensajeR;
	}
	public void setMensajeR(String mensajeR) {
		this.mensajeR = mensajeR;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
