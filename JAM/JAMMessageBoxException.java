/**
* Classe JAMMessageBoxException, gestisce le eccezioni derivanti dai messaggi.
* @author fabio
*/
package JAM;
public class JAMMessageBoxException extends JAMException{
	/**
	* Costruttore per una nuova eccezione
	*/
	public JAMMessageBoxException(){
		super();
	}

	/**
	* Costruttore per una nuova eccezione con messaggio di errore
	* @param messaggio stringa descrittiva dell'errore
	*/
	public JAMMessageBoxException(String messaggio){
		super(messaggio);
	}
}
