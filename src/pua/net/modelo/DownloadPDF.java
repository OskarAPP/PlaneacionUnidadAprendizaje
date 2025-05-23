package pua.net.modelo;

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

import pua.hibernate.*;

public class DownloadPDF {
	public void exportToPDF(PuaVersion pua) {
		try {
			 String ruta=ServletActionContext.getServletContext().getRealPath("/");
			 javax.naming.InitialContext ctx = new javax.naming.InitialContext();
			 HttpServletResponse response = ServletActionContext.getResponse();
			 response.setContentType("application/pdf");
	         response.setHeader("Cache-control", "no-cache");
	         response.setHeader("Pragma", "no-cache");
	         response.setDateHeader("Expires", 0);
	         
	         OutputStream op=response.getOutputStream();
	         byte[] bites=pua.getPua_1();
	         response.setHeader("Content-disposition", "attachment; filename=PUA "+pua.getMateria()+"("+pua.getPua().getPlanEstudio()+") v"+pua.getVersion()+".pdf");
	         response.setContentLength(bites.length);
	         op.write(bites);
	         op.close();
		} catch (IOException e) {
	        e.printStackTrace();
	    } catch (NamingException e) {
			e.printStackTrace();
		}
	}
}