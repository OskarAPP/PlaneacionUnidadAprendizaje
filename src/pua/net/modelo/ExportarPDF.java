package pua.net.modelo;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import pua.net.dto.*;
public class ExportarPDF {
	public void exportToPDF(List<GenericaDTO> listaGenericasPua, List<EspecificaDTO> listaEspecificasPua, PuaDTO puaDTO,List<SubcompetenciaDTO>listaSubcompetencias, List<LibroDTO> listaBibliografia
			,List<EvaluacionFinalDTO> listaEvaluacionFinal, List<EvaluacionDTO> listaEvaluacionCompetencias, List<PerfilDTO> listaPerfiles, List<DocenteDTO> listaDocenteFacultad,
			List<DocenteDTO> listaPresidenteAcademia, List<DocenteDTO> listaSecretarioAcademia, List<DocenteDTO> listaCoordinador, List<DocenteDTO> listaSecretarioAcademico,
			List<DocenteDTO> listaDirector, String nombreArchivo) {
		try {
			 String ruta=ServletActionContext.getServletContext().getRealPath("/");
			 javax.naming.InitialContext ctx = new javax.naming.InitialContext();
			 HttpServletResponse response = ServletActionContext.getResponse();
			 response.setContentType("application/pdf");
	         response.setHeader("Cache-control", "no-cache");
	         response.setHeader("Pragma", "no-cache");
	         response.setDateHeader("Expires", 0);
	         Map parametros = new HashMap();
	         listaSubcompetencias=expanderLista(listaSubcompetencias);
	         parametros.put("RUTA_IMG",ServletActionContext.getServletContext().getRealPath("/WEB-INF/report/"));
	         parametros.put("competenciaUda", puaDTO.getCompetenciaUda());
	         parametros.put("competenciaFormacion", puaDTO.getCompetenciaFormacion());
	         parametros.put("FACULTAD_NOMBRE", puaDTO.getFacultadDTO().getFacultad());
	         parametros.put("PROGRAMA_EDUCATIVO", puaDTO.getCarreraDTO().getNombreCarrera());
	         parametros.put("planEstudio", puaDTO.getCarreraDTO().getPlanEstudios());
	         parametros.put("Unidad_Aprendizaje", puaDTO.getMateriaDTO().getNombreMateria());
	         parametros.put("htotal", puaDTO.getMateriaDTO().getHorasTotales());
	         parametros.put("hpractica", puaDTO.getMateriaDTO().getHorasPracticas());
	         parametros.put("hteorica", puaDTO.getMateriaDTO().getHorasTeoricas());
	         parametros.put("creditos", puaDTO.getMateriaDTO().getCreditosTotales());
	         parametros.put("area", puaDTO.getMateriaDTO().getArea());
	         parametros.put("nucleo", puaDTO.getMateriaDTO().getNucleo());
	         parametros.put("tipo", puaDTO.getMateriaDTO().getTipo());
	         parametros.put("art57", puaDTO.getMateriaDTO().getArt57().toUpperCase());
	         parametros.put("Nombre_academia", puaDTO.getAcademiaDTO().getNombreAcademia());
	         parametros.put("listaReporte",listaReporte(listaSubcompetencias));
	         parametros.put("creditacionCompetencias",puaDTO.getMateriaDTO().getArt57().toUpperCase().equals("NO")?1:0);
	         parametros.put("listaEvaluacionFinal", listaEvaluacionFinal(listaEvaluacionFinal));
	         parametros.put("listaEvaluacion", listaEvaluacionCompetencias(listaEvaluacionCompetencias));
	         
	         JasperReport subReport = (JasperReport) JRLoader.loadObjectFromFile(ruta+"WEB-INF/report/puaReport_subreport1.jasper");
	         JRBeanCollectionDataSource data=new JRBeanCollectionDataSource(listaSubcompetencias);
	         parametros.put("datasource", data);
	         parametros.put("subReport", subReport);
	         
	         JasperReport subReport2 = (JasperReport) JRLoader.loadObjectFromFile(ruta+"WEB-INF/report/puaReport_subreport2.jasper");
	         JRBeanCollectionDataSource data2=new JRBeanCollectionDataSource(listaBibiliografia(listaBibliografia));
	         parametros.put("datasource_1", data2);
	         parametros.put("subReport_1", subReport2);
	         
	         JasperReport subReport3 = (JasperReport) JRLoader.loadObjectFromFile(ruta+"WEB-INF/report/puaReport_subreport3.jasper");
	         JRBeanCollectionDataSource data3=new JRBeanCollectionDataSource(listaPerfiles);
	         parametros.put("datasource_2", data3);
	         parametros.put("subReport_2", subReport3);
	         
	         JasperReport comite = (JasperReport) JRLoader.loadObjectFromFile(ruta+"WEB-INF/report/comite.jasper");
	         JRBeanCollectionDataSource comiteDb=new JRBeanCollectionDataSource(listaComite(listaDocenteFacultad, listaPresidenteAcademia, listaSecretarioAcademia, 
	        		 listaCoordinador, listaSecretarioAcademico, listaDirector));
	         parametros.put("comiteDb", comiteDb);
	         parametros.put("comite", comite);
	         
	         Reporte re=new Reporte();
	         re.setListaEspecifica(listaEspecificasPua);
	         re.setListaGenerica(listaGenericasPua);
	         List<Reporte> auxReport=new ArrayList<Reporte>();
	         auxReport.add(re);
	         JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(ruta+"WEB-INF/report/puaReport.jasper");	 
	         JRBeanCollectionDataSource data4=new JRBeanCollectionDataSource(auxReport);
	         JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, data4);
	         
	         OutputStream op=response.getOutputStream();
	         byte[] bites = JasperExportManager.exportReportToPdf(jasperPrint);
	         response.setHeader("Content-disposition", "attachment; filename=PUA "+puaDTO.getMateriaDTO().getNombreMateria()+" "+puaDTO.getCarreraDTO().getPlanEstudios()+".pdf");
	         response.setContentLength(bites.length);
	         op.write(bites);
	         op.close();
		} catch (IOException | JRException e) {
	        e.printStackTrace();
	    } catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private List<Reporte> listaComite(List<DocenteDTO> listaDocenteFacultad, List<DocenteDTO> listaPresidenteAcademia, List<DocenteDTO> listaSecretarioAcademia, 
			List<DocenteDTO> listaCoordinador, List<DocenteDTO> listaSecretarioAcademico, List<DocenteDTO> listaDirector){
		List<Reporte> listaComite = new ArrayList<Reporte>();
		Reporte reporte= new Reporte();
		String docente="", coordinador="", academia="",secretario="", fecha="", director="";
		for (int i = 0; i < listaDocenteFacultad.size(); i++) {
			try {
				docente+="\n"+"     "+listaDocenteFacultad.get(i).getPrefijo()+" "+listaDocenteFacultad.get(i).getNombre()+" "+listaDocenteFacultad.get(i).getApellidoMaterno()+" "
						+listaDocenteFacultad.get(i).getApellidoMaterno()+"\n";
			} catch(Exception e) {}
		}
		
		for (int i = 0; i < listaCoordinador.size(); i++) {
			try {
				coordinador+="\n"+"     "+listaCoordinador.get(i).getPrefijo()+" "+listaCoordinador.get(i).getNombre()+" "+listaCoordinador.get(i).getApellidoMaterno()+" "
						+listaCoordinador.get(i).getApellidoMaterno()+"\n";
			} catch(Exception e) {}
		}
		
		for (int i = 0; i < listaSecretarioAcademico.size(); i++) {
			try {
				secretario+="\n"+"     "+listaSecretarioAcademico.get(i).getPrefijo()+" "+listaSecretarioAcademico.get(i).getNombre()+" "+listaSecretarioAcademico.get(i).getApellidoMaterno()+" "
						+listaSecretarioAcademico.get(i).getApellidoMaterno()+"\n";
			} catch(Exception e) {}
		}
		
		for (int i = 0; i < listaDirector.size(); i++) {
			try {
				director+="\n"+"     "+listaDirector.get(i).getPrefijo()+" "+listaDirector.get(i).getNombre()+" "+listaDirector.get(i).getApellidoMaterno()+" "
						+listaDirector.get(i).getApellidoMaterno()+"\n";
			} catch(Exception e) {}
		}
		
		for (int i = 0; i < listaPresidenteAcademia.size(); i++) {
			try {
				academia+="\n"+"     "+listaPresidenteAcademia.get(i).getPrefijo()+" "+listaPresidenteAcademia.get(i).getNombre()+" "+listaPresidenteAcademia.get(i).getApellidoMaterno()+" "
						+listaPresidenteAcademia.get(i).getApellidoMaterno()+" (Presidente)"+"\n";
			} catch(Exception e) {}
			
			try {
				academia+="\n"+"     "+listaSecretarioAcademia.get(i).getPrefijo()+" "+listaSecretarioAcademia.get(i).getNombre()+" "+listaSecretarioAcademia.get(i).getApellidoMaterno()+" "
						+listaSecretarioAcademia.get(i).getApellidoMaterno()+" (Secretario)";
			} catch(Exception e) {}
		}
		reporte.setDocente(docente);
		reporte.setDirector(director);
		reporte.setAcademia(academia);
		reporte.setCoordinador(coordinador);
		reporte.setFecha(fecha);
		reporte.setSecretario(secretario);
		reporte.setFecha(fecha());
		listaComite.add(reporte);
		return listaComite;
	}
	
	private String fecha() {
		Calendar c2 = new GregorianCalendar();
		String fecha="";
		switch(c2.get(Calendar.MONTH)) {
		case 0:
			fecha="     Enero";
			break;
		case 1:
			fecha="     Febrero";
			break;
		case 2:
			fecha="     Marzo";
			break;
		case 3:
			fecha="     Abril";
			break;
		case 4:
			fecha="     Mayo";
			break;
		case 5:
			fecha="     Junio";
			break;
		case 6:
			fecha="     Julio";
			break;
		case 7:
			fecha="     Agosto";
			break;
		case 8:
			fecha="     Septiembre";
			break;
		case 9:
			fecha="     Octubre";
			break;
		case 10:
			fecha="     Noviembre";
			break;
		case 11:
			fecha="     Diciembre";
			break;
		}
		fecha+=" "+c2.get(Calendar.DAY_OF_WEEK)+", "+c2.get(Calendar.YEAR);
		return fecha;
	}
	
	private List<EvaluacionFinalDTO> listaEvaluacionFinal(List<EvaluacionFinalDTO> listaEvaluacionFinal){
		switch(listaEvaluacionFinal.size()) {
			case 1:
				listaEvaluacionFinal.add(null);
				listaEvaluacionFinal.add(null);
				listaEvaluacionFinal.add(null);
				break;
			case 2:
				listaEvaluacionFinal.add(null);
				listaEvaluacionFinal.add(null);
				break;
			case 3: 
				listaEvaluacionFinal.add(null);
				break;
		}
		return listaEvaluacionFinal;
	}
	
	private List<EvaluacionDTO> listaEvaluacionCompetencias(List<EvaluacionDTO> listaEvaluacionCompetencias){
		switch(listaEvaluacionCompetencias.size()) {
			case 1:
				listaEvaluacionCompetencias.add(null);
				listaEvaluacionCompetencias.add(null);
				listaEvaluacionCompetencias.add(null);
				break;
			case 2:
				listaEvaluacionCompetencias.add(null);
				listaEvaluacionCompetencias.add(null);
				break;
			case 3: 
				listaEvaluacionCompetencias.add(null);
				break;
		}
		return listaEvaluacionCompetencias;
	}
	
	private List<BibliografiaDTO> listaBibiliografia(List<LibroDTO>listaLibro){
		List<BibliografiaDTO> listaBibliografia=new ArrayList<BibliografiaDTO>();
		BibliografiaDTO bibliografia=new BibliografiaDTO();
		List<LibroDTO> listaBasica=new ArrayList<LibroDTO>();
		List<LibroDTO> listaComplementaria=new ArrayList<LibroDTO>();
		List<LibroDTO> listaSitios=new ArrayList<LibroDTO>();
		for (int i = 0; i < listaLibro.size(); i++) {
			switch(listaLibro.get(i).getTipoBibliografia().toUpperCase()) {
				case "BÁSICA":
					listaBasica.add(listaLibro.get(i));
				break;
				case "COMPLEMENTARIA":
					listaComplementaria.add(listaLibro.get(i));
				break;
				case "SITIO WEB":
					listaSitios.add(listaLibro.get(i));
				break;
			}
		}
		bibliografia.setListaBasica(listaBasica);
		bibliografia.setListaComplementaria(listaComplementaria);
		bibliografia.setListaSitios(listaSitios);
		listaBibliografia.add(bibliografia);
		return listaBibliografia;
	}
	
	private List<SubcompetenciaDTO> expanderLista(List<SubcompetenciaDTO>listaSubcompetencias){
		List<SubcompetenciaDTO>aux= new ArrayList<SubcompetenciaDTO>();
		for (int i = 0; i < listaSubcompetencias.size(); i++) {
			String alumno="", docente="", bibliografia="", temas="", subtemas="", ponderado;
			String ponderacionE="", material="", ambiente="", evidencia="", criterio="";
			SubcompetenciaDTO subcompetencia=new SubcompetenciaDTO();
			ponderado=listaSubcompetencias.get(i).getPonderacion()+"";
			for (int j = 0; j <listaSubcompetencias.get(i).getListaTemas().size() ; j++) {
				temas+=listaSubcompetencias.get(i).getListaTemas().get(j).getNumeroTema()+".- "+listaSubcompetencias.get(i).getListaTemas().get(j).getTema()+"\n";
				for (int j2 = 0; j2 < listaSubcompetencias.get(i).getListaTemas().get(j).getListaSubtema().size(); j2++) {
					subtemas+=(j+1)+"."+(j2+1)+".- "+listaSubcompetencias.get(i).getListaTemas().get(j).getListaSubtema().get(j2).getSubtema()+"\n";
				}
			}
			
			for (int j = 0; j < listaSubcompetencias.get(i).getListaActividadesAlumno().size(); j++) {
				alumno+=listaSubcompetencias.get(i).getListaActividadesAlumno().get(j).getActividad()+"\n"+"\n";
			}
			
			for (int j = 0; j < listaSubcompetencias.get(i).getListaActividadesDocente().size(); j++) {
				docente+=listaSubcompetencias.get(i).getListaActividadesDocente().get(j).getActividad()+"\n"+"\n";
			}
			
			for (int j = 0; j < listaSubcompetencias.get(i).getListaBibliografia().size(); j++) {
				bibliografia+=listaSubcompetencias.get(i).getListaBibliografia().get(j).getIdLibro()+"\n"+"\n";
			}
			
			for (int j = 0; j < listaSubcompetencias.get(i).getListaMaterial().size(); j++) {
				material+=listaSubcompetencias.get(i).getListaMaterial().get(j).getMaterial()+"\n"+"\n";
			}	
			
			for (int j = 0; j < listaSubcompetencias.get(i).getListaAmbiente().size(); j++) {
				ambiente+=listaSubcompetencias.get(i).getListaAmbiente().get(j).getAmbiente()+"\n"+"\n";
			}	
			
			for (int j = 0; j < listaSubcompetencias.get(i).getListaEvidencia().size(); j++) {
				evidencia+=listaSubcompetencias.get(i).getListaEvidencia().get(j).getEvidencia()+"\n"+"\n";
				ponderacionE+=listaSubcompetencias.get(i).getListaEvidencia().get(j).getPonderacion()+"\n"+"\n";
			}
			
			for (int j = 0; j < listaSubcompetencias.get(i).getListaCriterios().size(); j++) {
				criterio+=listaSubcompetencias.get(i).getListaCriterios().get(j).getCriterio()+"\n"+"\n";
			}
			
			subcompetencia.setSubcompetencia(listaSubcompetencias.get(i).getSubcompetencia());
			subcompetencia.setEvidencia(evidencia);
			subcompetencia.setPonderadoE(ponderacionE);
			subcompetencia.setCriterios(criterio);
			subcompetencia.setPonderacion(listaSubcompetencias.get(i).getPonderacion());
			subcompetencia.setAlumno(alumno);
			subcompetencia.setDocente(docente);
			subcompetencia.setTemas(temas);
			subcompetencia.setSubtemas(subtemas);
			subcompetencia.setBibliografia(bibliografia);
			subcompetencia.setPonderado(ponderado);
			subcompetencia.setSesiones(listaSubcompetencias.get(i).getSesiones());
			subcompetencia.setMaterial(material);
			subcompetencia.setAmbiente(ambiente);
			subcompetencia.setNumSubcompetencia(listaSubcompetencias.get(i).getNumSubcompetencia());
			subcompetencia.setParcial(listaSubcompetencias.get(i).getParcial());
			aux.add(subcompetencia);
		}
		return aux;
	}
	
	private List<Reporte> listaReporte(List<SubcompetenciaDTO>listaSubcompetencias){
		List<Reporte> listaReporte=new ArrayList<Reporte>();
		Reporte reporte;
		
		for (int i = 0; i < listaSubcompetencias.size(); i++) {
			reporte=new Reporte();
			reporte.setSubcompetencia(cardinal(listaSubcompetencias.get(i).getNumSubcompetencia()));
			reporte.setParcial(parcial(listaSubcompetencias.get(i).getParcial()));
			reporte.setPonderado(listaSubcompetencias.get(i).getPonderacion());
			listaReporte.add(reporte);
		}
		
		switch(listaReporte.size()) {
			case 2:
				listaReporte.add(null);
				listaReporte.add(null);
				listaReporte.add(null);
				break;
			case 3:
				listaReporte.add(null);
				listaReporte.add(null);
				break;
			case 4:
				listaReporte.add(null);
				break;
		}
		return listaReporte;
	}
	
	private String parcial(int num) {
		switch(num) {
			case 1:
				return "Primer Parcial";
			case 2:
				return "Segunda Parcial";
			case 3:
				return "Tercer Parcial";
			case 4:
				return "Cuarta}o Parcial";
		}
		return null;
	}
	
	private String cardinal(int num) {
		switch(num) {
			case 1:
				return "Primera";
			case 2:
				return "Segunda";
			case 3:
				return "Tercera";
			case 4:
				return "Cuarta";
		}
		return null;
	}
}