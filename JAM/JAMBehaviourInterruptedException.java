/**
* class JAMBehaviourInterruptedException, gestisce le eccezioni riguardanti i comportamenti, estende JAMBehaviourException.
* @author fabio
*/
package JAM;
import java.util.*;
import java.lang.*;

public class JAMBehaviourInterruptedException extends JAMBehaviourException{

	/**
	* Costruttore della classe senza parametri.
	*/
	public JAMBehaviourInterruptedException(){
		super();
	}

	/**
	* Costruttore della classe.
	* @param message di tipo string.
	*/
	public JAMBehaviourInterruptedException(String message){
		super(message);
	}
}
