package pua.net.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import pua.net.dto.*;
import pua.net.dao.AdministradorDAO;
import pua.net.dao.DAOException;

public class RegistroCSV {
	private String exito;
	private String alerta;
	public boolean registroCSV(File archivo, AdministradorDAO adminDAO, int opc)throws DAOException{
		try {
			FileReader f=new FileReader(archivo);
			BufferedReader br=new BufferedReader(f);
			boolean s=true;
			int count=0;
			while(s) {
				String tx=br.readLine();
				if(tx!=null) {
					if(count>0) {
						String[] valores=tx.split(",");
						switch(opc) {
							case 1:
								registroMaterias(adminDAO,valores);
								break;
							case 2:
								registroDocentes(adminDAO, valores);
								break;
							case 3:
								registroCarreras(adminDAO, valores);
								break;
							case 4:
								registroAcademias(adminDAO, valores);
								break;
							case 5:
								registroGenericas(adminDAO, valores);
								break;
							case 6:
								registroEspecificas(adminDAO,valores);
								break;
						}
						exito="Exito! se ha completado correctamente";
					} else {
						count++;
					}
					
				} else {
					s=false;
				}
			}
			return true;
		} catch (FileNotFoundException e) {
			alerta="Error! Archivo no encontrado";
			e.printStackTrace();
		} catch (IOException e) {
			alerta="Error! No se pudo completar";
			e.printStackTrace();
		} catch(NullPointerException e) {
			alerta="Alerta! Archivo no cargado";
		} catch(NumberFormatException e) {
			alerta="Error! No se pudo completar";
		}
		return false;
	}
	
	private void registroMaterias(AdministradorDAO adminDAO, String[] valores) {
		MateriaDTO materiaDTO=new MateriaDTO();
		materiaDTO.setIdArea(convertToString(valores[1]));
		materiaDTO.setIdNucleo(convertToString(valores[2]));
		materiaDTO.setIdTipo(convertToString(valores[3]));
		materiaDTO.setNombreMateria(valores[4]);
		materiaDTO.setCreditosTotales(convertToString(valores[5]));
		materiaDTO.setHorasTotales(convertToString(valores[6]));
		materiaDTO.setHorasTeoricas(convertToString(valores[7]));
		materiaDTO.setHorasPracticas(convertToString(valores[8]));
		materiaDTO.setArt57(valores[9]);
		try {
			materiaDTO.setIdMateria(adminDAO.insertarMateriaCSV(materiaDTO));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String[] carrera=valores[10].split(" ");
		try {
			for (int i = 0; i < carrera.length; i++) {
				adminDAO.insertarCarreraMateria(convertToString(carrera[i]), materiaDTO.getIdMateria());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String[] academia=valores[11].split(" ");
		try {
			for (int i = 0; i < carrera.length; i++) {
				adminDAO.insertarAcademiaMateria(convertToString(carrera[i]), materiaDTO.getIdMateria());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void registroDocentes(AdministradorDAO adminDAO, String[] valores) {
		DocenteDTO docenteDTO=new DocenteDTO();
		docenteDTO.setPrefijo(valores[1]);
		docenteDTO.setNombre(valores[2]);
		docenteDTO.setApellidoPaterno(valores[3]);
		docenteDTO.setApellidoMaterno(valores[4]);
		docenteDTO.setCorreo(valores[5]);
		docenteDTO.setIdTipo(6);
		
		AccesoDTO accesoDTO=new AccesoDTO();
		accesoDTO.setCorreo(valores[5]);
		accesoDTO.setPass(valores[6]);
		accesoDTO.setEstado(1);
		accesoDTO.setRol("docente");
		
		try {
			adminDAO.insertarDocenteCSV(docenteDTO,accesoDTO);
		} catch (SQLException e) {
			alerta="Error! No se pudo completar";
			e.printStackTrace();
		}
	}
	
	private void registroCarreras(AdministradorDAO adminDAO, String[] valores) {
		CarreraDTO carreraDTO=new CarreraDTO();
		carreraDTO.setIdFacultad(convertToString(valores[0]));
		carreraDTO.setNombreCarrera(valores[1]);
		carreraDTO.setPlanEstudios(convertToString(valores[2]));
		try {
			adminDAO.insertarCarrera(carreraDTO);
		} catch (SQLException e) {
			alerta="Error! No se pudo completar";
			e.printStackTrace();
		}
	}
	
	private void registroAcademias(AdministradorDAO adminDAO, String[] valores) {
		AcademiaDTO academiaDTO=new AcademiaDTO();
		academiaDTO.setIdFacultad(convertToString(valores[0]));
		academiaDTO.setNombreAcademia(valores[1]);
		try {
			adminDAO.insertarAcademia(academiaDTO);
		} catch (SQLException e) {
			alerta="Error! No se pudo completar";
			e.printStackTrace();
		}
	}
	
	private void registroEspecificas(AdministradorDAO adminDAO, String[] valores) {
		EspecificaDTO especificaDTO=new EspecificaDTO();
		especificaDTO.setPerfilEspecifica(valores[0]);
		try {
			adminDAO.insertarEspecifica(especificaDTO, true);
		} catch (SQLException e) {
			alerta="Error! No se pudo completar";
			e.printStackTrace();
		}
	}
	
	private void registroGenericas(AdministradorDAO adminDAO, String[] valores) {
		GenericaDTO genericaDTO=new GenericaDTO();
		genericaDTO.setNombreGenerica(valores[0]);
		try {
			adminDAO.insertarGenerica(genericaDTO);
		} catch (SQLException e) {
			alerta="Error! No se pudo completar";
			e.printStackTrace();
		}
	}
	
	private int convertToString(String txt) {
		return Integer.parseInt(txt);
	}

	public String getExito() {
		return exito;
	}

	public void setExito(String exito) {
		this.exito = exito;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}
	
	
}
