package pua.hibernate;
// Generated 22/04/2019 09:38:09 PM by Hibernate Tools 4.3.1.Final

/**
 * BibliografiaSubcompetenciaId generated by hbm2java
 */
public class BibliografiaSubcompetenciaId implements java.io.Serializable {

	private Integer idSubcompetencia;
	private Integer idLibro;

	public BibliografiaSubcompetenciaId() {
	}

	public BibliografiaSubcompetenciaId(Integer idSubcompetencia, Integer idLibro) {
		this.idSubcompetencia = idSubcompetencia;
		this.idLibro = idLibro;
	}

	public Integer getIdSubcompetencia() {
		return this.idSubcompetencia;
	}

	public void setIdSubcompetencia(Integer idSubcompetencia) {
		this.idSubcompetencia = idSubcompetencia;
	}

	public Integer getIdLibro() {
		return this.idLibro;
	}

	public void setIdLibro(Integer idLibro) {
		this.idLibro = idLibro;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BibliografiaSubcompetenciaId))
			return false;
		BibliografiaSubcompetenciaId castOther = (BibliografiaSubcompetenciaId) other;

		return ((this.getIdSubcompetencia() == castOther.getIdSubcompetencia())
				|| (this.getIdSubcompetencia() != null && castOther.getIdSubcompetencia() != null
						&& this.getIdSubcompetencia().equals(castOther.getIdSubcompetencia())))
				&& ((this.getIdLibro() == castOther.getIdLibro()) || (this.getIdLibro() != null
						&& castOther.getIdLibro() != null && this.getIdLibro().equals(castOther.getIdLibro())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIdSubcompetencia() == null ? 0 : this.getIdSubcompetencia().hashCode());
		result = 37 * result + (getIdLibro() == null ? 0 : this.getIdLibro().hashCode());
		return result;
	}

}
