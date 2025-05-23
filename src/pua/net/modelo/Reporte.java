package pua.net.modelo;

import java.util.List;

import pua.net.dto.*;

public class Reporte {
	private String subcompetencia;
	private String parcial;
	private int ponderado;
	private List<EvaluacionFinalDTO> listaEvaluacionFinal;
	private List<EvaluacionDTO> listaEvaluacionCompetencias;
	private List<GenericaDTO> listaGenerica;
	private List<EspecificaDTO> listaEspecifica;
	private String docente, coordinador, secretario, director, academia, fecha;
	public String getDocente() {
		return docente;
	}
	public String getCoordinador() {
		return coordinador;
	}
	public String getSecretario() {
		return secretario;
	}
	public String getDirector() {
		return director;
	}
	public String getAcademia() {
		return academia;
	}
	public String getFecha() {
		return fecha;
	}
	public void setDocente(String docente) {
		this.docente = docente;
	}
	public void setCoordinador(String coordinador) {
		this.coordinador = coordinador;
	}
	public void setSecretario(String secretario) {
		this.secretario = secretario;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public void setAcademia(String academia) {
		this.academia = academia;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public List<EvaluacionFinalDTO> getListaEvaluacionFinal() {
		return listaEvaluacionFinal;
	}
	public List<EvaluacionDTO> getListaEvaluacionCompetencias() {
		return listaEvaluacionCompetencias;
	}
	public List<GenericaDTO> getListaGenerica() {
		return listaGenerica;
	}
	public List<EspecificaDTO> getListaEspecifica() {
		return listaEspecifica;
	}
	public void setListaEvaluacionFinal(List<EvaluacionFinalDTO> listaEvaluacionFinal) {
		this.listaEvaluacionFinal = listaEvaluacionFinal;
	}
	public void setListaEvaluacionCompetencias(List<EvaluacionDTO> listaEvaluacionCompetencias) {
		this.listaEvaluacionCompetencias = listaEvaluacionCompetencias;
	}
	public void setListaGenerica(List<GenericaDTO> listaGenerica) {
		this.listaGenerica = listaGenerica;
	}
	public void setListaEspecifica(List<EspecificaDTO> listaEspecifica) {
		this.listaEspecifica = listaEspecifica;
	}
	public String getSubcompetencia() {
		return subcompetencia;
	}
	public String getParcial() {
		return parcial;
	}
	public int getPonderado() {
		return ponderado;
	}
	public void setSubcompetencia(String subcompetencia) {
		this.subcompetencia = subcompetencia;
	}
	public void setParcial(String parcial) {
		this.parcial = parcial;
	}
	public void setPonderado(int ponderado) {
		this.ponderado = ponderado;
	}
}
