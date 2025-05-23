package pua.net.controlador;

import java.util.Map;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import  pua.net.dto.UsuarioHabilitado;
import pua.net.dto.DocenteDTO;

public class LoginInterceptor implements Interceptor{
	 public void destroy() { }
	 public void init() { }
	  
	  public String intercept(ActionInvocation actionInvocation) throws Exception {
	      
		  Map<String,Object> session = actionInvocation.getInvocationContext().getSession();
		  DocenteDTO docenteDTO = (DocenteDTO) session.get("docenteDTO");
	      
	      if(docenteDTO == null){
	          return "login";
	      }else{
	          Action action = (Action) actionInvocation.getAction();
	          if (action instanceof UsuarioHabilitado) {
	              ((UsuarioHabilitado) action).setUsuario(docenteDTO);
	          }
	          return actionInvocation.invoke();
	      }
	  }
}
