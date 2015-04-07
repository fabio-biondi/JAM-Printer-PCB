/**
* class JAMIOException, gestisce le eccezioni riguardanti I/O.
* @author fabio
*/
package JAM;
import java.util.*;
import java.lang.*;


public class JAMIOException extends JAMException{

	/**
	* Costruttore della classe senza parametri.
	*/
	public JAMIOException(){
		super();
	}

	/**
	* Costruttore della classe.
	* @param message di tipo string.
	*/
	public JAMIOException(String message){
		super(message);
	}

	/**
	* Costruttore della classe.
	* @param message di tipo Throwable.
	*/
	public JAMIOException(Throwable message){
		super(message.getMessage());
	}
}
