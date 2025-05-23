package pua.hibernate;
// Generated 22/04/2019 09:38:09 PM by Hibernate Tools 4.3.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Tema generated by hbm2java
 */
public class Tema implements java.io.Serializable {

	private Integer idTema;
	private Subcompetencia subcompetencia;
	private String tema;
	private Integer numeroTema;
	private Set subtemas = new HashSet(0);

	public Tema() {
	}

	public Tema(Subcompetencia subcompetencia, String tema, Integer numeroTema, Set subtemas) {
		this.subcompetencia = subcompetencia;
		this.tema = tema;
		this.numeroTema = numeroTema;
		this.subtemas = subtemas;
	}

	public Integer getIdTema() {
		return this.idTema;
	}

	public void setIdTema(Integer idTema) {
		this.idTema = idTema;
	}

	public Subcompetencia getSubcompetencia() {
		return this.subcompetencia;
	}

	public void setSubcompetencia(Subcompetencia subcompetencia) {
		this.subcompetencia = subcompetencia;
	}

	public String getTema() {
		return this.tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public Integer getNumeroTema() {
		return this.numeroTema;
	}

	public void setNumeroTema(Integer numeroTema) {
		this.numeroTema = numeroTema;
	}

	public Set getSubtemas() {
		return this.subtemas;
	}

	public void setSubtemas(Set subtemas) {
		this.subtemas = subtemas;
	}

}
