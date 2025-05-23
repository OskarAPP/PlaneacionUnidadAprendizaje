package pua.net.controlador;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class ReportesAction extends ActionSupport implements ServletContextAware, SessionAware{
	private Logger log;
	private ServletContext servletContext;
	
	public ReportesAction() {
		log = LoggerFactory.getLogger(ReportesAction.class);
	}
	
	public void setSession(Map<String, Object> arg0) {}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public String execute() {
		return "";
	}

}
