/**
* Classe JAMException.
* @author fabio
*/
package JAM;

public class JAMException extends Exception{

	/**
	* Costruttore per una nuova eccezione
	*/
	public JAMException(){
		super();
	}

	/**
	* Costruttore per una nuova eccezione con messaggio di errore
	* @param messaggio stringa che descrive l'errore
	*/
	public JAMException(String messaggio){
		super(messaggio);
	}
}
