package pua.net.dao.mysql;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import pua.hibernate.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pua.net.dao.*;
import pua.net.dto.DocenteDTO;
import pua.net.dto.EspecificaDTO;
import pua.net.dto.GenericaDTO;
public class MySQLPuaVersion extends PuaVersionDAO {
	private Session session;
    private Transaction transaction;
    
    //read
    private static final String obtenerListaPua=
    		"from Pua p where p.materia.nombreMateria like ? or p.materia.nombreMateria like ? or p.materia.nombreMateria like ? "
    		+ "order by p.materia.nombreMateria asc";
    
    private static final String obtenerListaPuaVersion=
    		"from PuaVersion p where p.materia like ? or p.materia like ? or p.materia like ?";
    
    private static final String obtenerVersion=
    		"from PuaVersion p where p.materia=?";
    
    private static final String obtenerAllPuaVersion=
    		"from PuaVersion p";
    
    private static final String obtenerAllPua=
    		"from Pua p";
    
    private static final String puaMax="from Pua p";
    
    private static final String obtenerSubcompetenciaById=
    		"from Subcompetencia s where s.pua.idPua=?";
    
    private static final String bibliografiaSubcompetencias =
    		"from BibliografiaSubcompetencia b where b.subcompetencia.idSubcompetencia=?";
    
    private static final String actividaddocs =
    		"from Actividaddoc b where b.subcompetencia.idSubcompetencia=?";
    
    private static final String actividadals =
    		"from Actividadal b where b.subcompetencia.idSubcompetencia=?";
    
    private static final String subcompetenciaAmbientes =
    		"from SubcompetenciaAmbiente b where b.subcompetencia.idSubcompetencia=?";
    
    private static final String temas =
    		"from Tema b where b.subcompetencia.idSubcompetencia=?";
    
    private static final String subtemas=
    		"from Subtema t where t.tema.idTema=?";
    
    private static final String criterios=
    		"from Criterio c where c.subcompetencia.idSubcompetencia=?";
    
    private static final String evidenciaSubcompetencias =
    		"from EvidenciaSubcompetencia a where a.subcompetencia.idSubcompetencia=?";
    
    private static final String subcompetenciaMaterials =
    		"from SubcompetenciaMaterial a where a.subcompetencia.idSubcompetencia=?";
    
    private static final String listaEvaFinal=
    		"from EvaluacionFinal e where e.pua.idPua=?";
    
    private static final String listaEvaCompetencia=
    		"from EvaluacionCompetencias e where e.pua.idPua=?";
    
    private static final String bibliografiaPua=
    		"from BibliografiaPua b where b.pua.idPua=?";
    
//    private void StartOperation() throws HibernateException {
//		session=HibernateUtil.getSessionFactory().openSession();
//        session.clear();
//        transaction = session.beginTransaction();
//    }
    
    private void StartOperation() throws HibernateException {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.clear();
            transaction = session.beginTransaction();
        } catch (HibernateException e) {
            throw new HibernateException("Error al iniciar la sesión de Hibernate: " + e.getMessage(), e);
        }
    }  //añadido
    
    private void MasterOfEX(HibernateException HE) throws HibernateException {
        transaction.rollback();
        throw new HibernateException("Error en al acceder a datos en " + HE);
    }
    
    @Override
    public int save(Object objeto) {
        int id = 0;
        try {
            StartOperation();
            id = (int) session.save(objeto);
            transaction.commit();
        } catch (HibernateException HE) {
            MasterOfEX(HE);
            throw HE;
        }  catch(ClassCastException e) {
        	session.close();
        	StartOperation();
            session.save(objeto);
            transaction.commit();
        }  finally {
        	session.flush();
            session.close();
        }
        return id;
    }
    
    @Override
    public void update(Object objeto) throws HibernateException {
        try {
            StartOperation();
            session.update(objeto);
            transaction.commit();
        } catch (HibernateException HE) {
            MasterOfEX(HE);
            throw HE;
        } finally {
        	session.flush();
            session.close();
        }
    }

    @Override
    public void delete(Object objeto) throws HibernateException {
        try {
            StartOperation();
            session.delete(objeto);
            transaction.commit();
        } catch (HibernateException HE) {
            MasterOfEX(HE);
            throw HE;
        } finally {
        	session.flush();
            session.close();
        }
    }
    
    @Override
    public Pua getPua(int idPua) throws HibernateException {
    	Pua pua = null;
        try {
            StartOperation();
            pua = (Pua) session.get(Pua.class, idPua);
        } finally {
            session.close();
        }
        return pua;
    }
    
    @Override
    public PuaVersion getPuaVersion(int idPua) throws HibernateException {
    	PuaVersion pua = null;
        try {
            StartOperation();
            pua = (PuaVersion) session.get(PuaVersion.class, idPua);
        } finally {
            session.close();
        }
        return pua;
    }
    
    @Override
    public int getVersion(String nombreMateria) throws HibernateException {
    	List<PuaVersion> listaPuaVersion = null;
        try {
            StartOperation();
            listaPuaVersion = session.createQuery(obtenerVersion).setParameter(0, nombreMateria).list();
        } finally {
           session.close();
        }
        return listaPuaVersion.size()+1;
    }
    
    @Override
    public List<Pua> getListaPua(String busqueda, int orden) throws HibernateException {
        List<Pua> listaPua = null;
        try {
            StartOperation();
            listaPua = session.createQuery(obtenerListaPua).setParameter(0, busqueda+"%")
            		.setParameter(1, "%"+busqueda+"%").setParameter(2, "%"+busqueda).list();
        } finally {
           session.close();
        }
        return listaPua;
    }

    @Override
    public List<PuaVersion> getListaPuaVersion(String busqueda, int orden) throws HibernateException {
        List<PuaVersion> listaPuaVersion = null;
        try {
            StartOperation();
            listaPuaVersion = session.createQuery(obtenerListaPuaVersion).setParameter(0, busqueda+"%")
            		.setParameter(1, "%"+busqueda+"%").setParameter(2, "%"+busqueda).list();
        } finally {
           session.close();
        }
        return listaPuaVersion;
    }
    
    @Override
    public List<PuaVersion> getAllListaPuaVersion() throws HibernateException {
        List<PuaVersion> listaPuaVersion = null;
        try {
            StartOperation();
            listaPuaVersion = session.createQuery(obtenerAllPuaVersion).list();
        } finally {
        if(session != null )
           session.close();
        }
        return listaPuaVersion;
    }
    
    @Override
    public List<Pua> getAllListaPua() throws HibernateException{
    	List<Pua> listaPua = null;
        try {
            StartOperation();
            listaPua = session.createQuery(obtenerAllPua).list();
        } finally {
           session.close();
        }
        return listaPua;
    }

	@Override
	public int getPuasMax() throws HibernateException {
		List<Pua> listaPua = null;
        try {
            StartOperation();
            listaPua = session.createQuery(puaMax).list();
        } finally {
          session.close();
        }
        return listaPua.size()+1;
	}
	
	@Override
	public List<Subcompetencia> getAllListaSubcompetencia(int idPua) throws HibernateException {
		List<Subcompetencia> listaSubcompetencia = null;
        try {
        	StartOperation();
            listaSubcompetencia = session.createQuery(obtenerSubcompetenciaById).setParameter(0, idPua).list();
        } finally {
        	session.close();
        }
        return listaSubcompetencia;
	}

	@Override
	public void updateSubcompetencia(Pua pua, int idPua) throws HibernateException {
		List<Subcompetencia> listaSubcompetencia = null;
        try {
        	StartOperation();
            listaSubcompetencia = session.createQuery(obtenerSubcompetenciaById).setParameter(0, idPua).list();
        } finally {
        	session.close();
        }
        for (Subcompetencia subcompetencia : listaSubcompetencia) {
        	int idSubcompetencia=subcompetencia.getIdSubcompetencia();
        	subcompetencia.setPua(pua);
        	subcompetencia.setIdSubcompetencia(save(subcompetencia));
        	updateBibliografiaSubcompetencias(idSubcompetencia, subcompetencia);
        	updateActividaddoc(idSubcompetencia, subcompetencia); 
        	updateActividadal(idSubcompetencia, subcompetencia);
        	updateSubcompetenciaMaterial(idSubcompetencia, subcompetencia);
        	updateTemas(idSubcompetencia, subcompetencia);
        	updateEvidenciaSubcompetencia(idSubcompetencia, subcompetencia);
        	updateCriterio(idSubcompetencia, subcompetencia);
        	updateSubcompetenciaAmbiente(idSubcompetencia, subcompetencia);
        	updateBibliografiaPua(pua, idPua);
		}
	}
	
	@Override
	public void updateEvaluacion(Pua pua, int idPua) throws HibernateException {
		List<EvaluacionFinal> listaFinal = null;
		List<EvaluacionCompetencias> listaComptencia = null;
        try {
        	StartOperation();
        	listaFinal = session.createQuery(listaEvaFinal).setParameter(0, idPua).list();
        	listaComptencia = session.createQuery(listaEvaCompetencia).setParameter(0, idPua).list();
        } finally {
        	session.close();
        }
        for (EvaluacionCompetencias evaluacionCompetencias : listaComptencia) {
        	evaluacionCompetencias.setPua(pua);
        	save(evaluacionCompetencias);
		}
        
        for (EvaluacionFinal evaluacionFinal : listaFinal) {
        	evaluacionFinal.setPua(pua);
        	save(evaluacionFinal);
		}
	}
	
	@Override
	public void updateBibliografiaPua(Pua pua, int idPua) throws HibernateException {
		List<BibliografiaPua> bibliografiaPua = null;
        try {
        	StartOperation();
        	bibliografiaPua = session.createQuery(this.bibliografiaPua).setParameter(0, idPua).list();
        } finally {
        	session.close();
        }
        for (BibliografiaPua bibliografia : bibliografiaPua) {
			BibliografiaPuaId aux=bibliografia.getId();
			aux.setIdPua(pua.getIdPua());
			bibliografia.setPua(pua);
			bibliografia.setId(aux);
			save(bibliografia);
		}
	}
	
	private void updateBibliografiaSubcompetencias(int idSubcompetencia, Subcompetencia subcompetencia) {
		List<BibliografiaSubcompetencia> bibliografiaSubcompetencias = null;
        try {
        	StartOperation();
        	bibliografiaSubcompetencias = session.createQuery(this.bibliografiaSubcompetencias).setParameter(0, idSubcompetencia).list();
        } finally {
        	session.close();
        }
        
        for (BibliografiaSubcompetencia bibliografiaSubcompetencia : bibliografiaSubcompetencias) {
        	bibliografiaSubcompetencia.setSubcompetencia(subcompetencia);
        	BibliografiaSubcompetenciaId aux=bibliografiaSubcompetencia.getId();
        	aux.setIdSubcompetencia(subcompetencia.getIdSubcompetencia());
        	bibliografiaSubcompetencia.setId(aux);
        	save(bibliografiaSubcompetencia);
		}
	}
	
	private void updateActividaddoc (int idSubcompetencia, Subcompetencia subcompetencia) {
		List<Actividaddoc> Actividaddocs = null;
        try {
        	StartOperation();
        	Actividaddocs = session.createQuery(this.actividaddocs).setParameter(0, idSubcompetencia).list();
        } finally {
        	session.close();
        }
        
        for (Actividaddoc actividaddoc : Actividaddocs) {
        	actividaddoc.setSubcompetencia(subcompetencia);
        	save(actividaddoc);
		}
	}
	
	private void updateActividadal(int idSubcompetencia, Subcompetencia subcompetencia) {
		List<Actividadal> actividadals = null;
        try {
        	StartOperation();
        	actividadals = session.createQuery(this.actividadals).setParameter(0, idSubcompetencia).list();
        } finally {
        	session.close();
        }
        for (Actividadal actividadal : actividadals) {
        	actividadal.setSubcompetencia(subcompetencia);
			save(actividadal);
		}
        
	}
	
	private void updateSubcompetenciaAmbiente(int idSubcompetencia, Subcompetencia subcompetencia) {
		List<SubcompetenciaAmbiente> subcompetenciaAmbientes  = null;
        try {
        	StartOperation();
        	subcompetenciaAmbientes  = session.createQuery(this.subcompetenciaAmbientes ).setParameter(0, idSubcompetencia).list();
        } finally {
        	session.close();
        }
        for (SubcompetenciaAmbiente subcompetenciaAmbiente : subcompetenciaAmbientes) {
        	subcompetenciaAmbiente.setSubcompetencia(subcompetencia);
        	SubcompetenciaAmbienteId aux =subcompetenciaAmbiente.getId();
        	aux.setIdSubcompetencia(subcompetencia.getIdSubcompetencia());
        	subcompetenciaAmbiente.setId(aux);
        	save(subcompetenciaAmbiente);
		}
	}
	
	private void updateCriterio(int idSubcompetencia, Subcompetencia subcompetencia) {
		List<Criterio> criterios = null;
        try {
        	StartOperation();
        	criterios = session.createQuery(this.criterios).setParameter(0, idSubcompetencia).list();
        } finally {
        	session.close();
        }
        
        for (Criterio criterio : criterios) {
        	criterio.setSubcompetencia(subcompetencia);
        	save(criterio);
		}
	}
	
	private void updateEvidenciaSubcompetencia(int idSubcompetencia, Subcompetencia subcompetencia) {
		List<EvidenciaSubcompetencia> evidenciaSubcompetencias = null;
        try {
        	StartOperation();
        	evidenciaSubcompetencias = session.createQuery(this.evidenciaSubcompetencias).setParameter(0, idSubcompetencia).list();
        } finally {
        	session.close();
        }
        
        for (EvidenciaSubcompetencia evidenciaSubcompetencia : evidenciaSubcompetencias) {
        	evidenciaSubcompetencia.setSubcompetencia(subcompetencia);
        	EvidenciaSubcompetenciaId aux=evidenciaSubcompetencia.getId();
        	aux.setIdSubcompetencia(subcompetencia.getIdSubcompetencia());
        	evidenciaSubcompetencia.setId(aux);
        	save(evidenciaSubcompetencia);
		}
        
	}
	
	private void updateSubcompetenciaMaterial(int idSubcompetencia, Subcompetencia subcompetencia) {
		List<SubcompetenciaMaterial> subcompetenciaMaterials = null;
        try {
        	StartOperation();
        	subcompetenciaMaterials = session.createQuery(this.subcompetenciaMaterials).setParameter(0, idSubcompetencia).list();
        } finally {
        	session.close();
        }
        
        for (SubcompetenciaMaterial subcompetenciaMaterial : subcompetenciaMaterials) {
        	subcompetenciaMaterial.setSubcompetencia(subcompetencia);
        	SubcompetenciaMaterialId aux=subcompetenciaMaterial.getId();
        	aux.setIdSubcompetencia(subcompetencia.getIdSubcompetencia());
        	subcompetenciaMaterial.setId(aux);
        	save(subcompetenciaMaterial);
		}
	}
	
	private void updateTemas(int idSubcompetencia, Subcompetencia subcompetencia) {
		List<Tema> temas = null;
        try {
        	StartOperation();
        	temas = session.createQuery(this.temas).setParameter(0, idSubcompetencia).list();
        } finally {
        	session.close();
        }
        
        for (Tema tema : temas) {
        	tema.setSubcompetencia(subcompetencia);
        	int idTema=tema.getIdTema();
        	tema.setIdTema(save(tema));
        	updateSubtemas(idTema, tema);
		}
	}
	
	private void updateSubtemas(int idTema, Tema tema) {
		List<Subtema> subtemas = null;
        try {
        	StartOperation();
        	subtemas = session.createQuery(this.subtemas).setParameter(0, idTema).list();
        } finally {
        	session.close();
        }
        
        for (Subtema subtema : subtemas) {
        	subtema.setTema(tema);
        	save(subtema);
		}
	}
	
	
	//delete
	@Override
	public void deleteSubcompetencia(int idPua, PuaDAO puaManyToMany) throws HibernateException, DAOException, SQLException {
		List<Subcompetencia> listaSubcompetencia = null;
        try {
        	StartOperation();
            listaSubcompetencia = session.createQuery(obtenerSubcompetenciaById).setParameter(0, idPua).list();
        } finally {
        	session.close();
        }
        
        
		puaManyToMany.eliminarAllBibliografia(idPua);
		puaManyToMany.eliminarCompetenciaDocente(idPua);
        
        for (Subcompetencia subcompetencia : listaSubcompetencia) {
        	deleteTemas(subcompetencia.getIdSubcompetencia());
        	puaManyToMany.eliminarSubcompetenciaM(subcompetencia.getIdSubcompetencia());
        	delete(subcompetencia);
		}
	}
	
	@Override
	public void deleteEvaluacion(int idPua) throws HibernateException {
		List<EvaluacionFinal> listaFinal = null;
		List<EvaluacionCompetencias> listaComptencia = null;
        try {
        	StartOperation();
        	listaFinal = session.createQuery(listaEvaFinal).setParameter(0, idPua).list();
        	listaComptencia = session.createQuery(listaEvaCompetencia).setParameter(0, idPua).list();
        } finally {
        	session.close();
        }
        for (EvaluacionCompetencias evaluacionCompetencias : listaComptencia) {
        	delete(evaluacionCompetencias);
		}
        
        for (EvaluacionFinal evaluacionFinal : listaFinal) {
        	delete(evaluacionFinal);
		}
	}
	
	private void deleteTemas(int idSubcompetencia) {
		List<Tema> temas = null;
        try {
        	StartOperation();
        	temas = session.createQuery(this.temas).setParameter(0, idSubcompetencia).list();
        } finally {
        	session.close();
        }
        
        for (Tema tema : temas) {
        	delete(tema);
		}
	}
}