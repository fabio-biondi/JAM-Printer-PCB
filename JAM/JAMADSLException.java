/**
* class JAMADSLException, gestisce eccezioni di tipo ADSL.
* @author fabio
*/
package JAM;
import java.util.*;
import java.lang.*;

public class JAMADSLException extends JAMException{

	/**
	* Costruttore della classe senza parametri.
	*/
	public JAMADSLException(){
		super();
	}

	/**
	* Costruttore della classe.
	* @param message messaggio di tipo String.
	*/
	public JAMADSLException(String message){
		super(message);
	}

	/**
	* Costruttore della classe.
	* @param message di tipo Throwable.
	*/
	public JAMADSLException(Throwable message){
		super(message.getMessage());
	}
}
