/**
* class JAMBehaviourException, gestisce le eccezioni riguardanti i comportamenti.
* @author fabio
*/
package JAM;
import java.util.*;
import java.lang.*;

public class JAMBehaviourException extends JAMException{

	/**
	* Costruttore della classe senza parametri.
	*/
	public JAMBehaviourException(){
		super();
	}

	/**
	* Costruttore della classe.
	* @parm message di tipo string.
	*/
	public JAMBehaviourException(String message){
		super(message);
	}
}
