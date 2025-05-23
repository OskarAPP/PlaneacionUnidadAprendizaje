package pua.net.pruebas;

import java.util.List;

import pua.net.dao.DAOFactory;
import pua.net.dao.PuaDAO;
import pua.net.dao.mysql.MySQLPuaDAO;
import pua.net.dto.LibroDTO;
import pua.net.dto.CarreraDTO;
import pua.net.dto.EspecificaDTO;

public class PruebaConexion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DAOFactory daoFactory = null;
		PuaDAO puaDAO;
		
		try{
			daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL, 
					"com.mysql.jdbc.Driver", "127.0.0.1", "pua", "root", "L1mbert#");
			puaDAO = daoFactory.getPuaDAO();
			
			int ponderacionSubcompetencia = 30;
			
			System.out.println(puaDAO.obtenerPonderacionEvidencias(1) * .30);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	

	}
	
	

}
