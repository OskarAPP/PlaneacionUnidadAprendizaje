package pua.net.dao;

public class DAOException extends Exception {

	public DAOException() {
    }

    public DAOException( Throwable t ) {
        super(t);
    }
    
    public DAOException(String s) {
    	super(s);
    }
}
