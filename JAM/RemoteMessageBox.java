/**
* interfaccia RemoteMessageBox, metodi messi a disposizione in maniera remota: scrittura messaggi e recupero
* informazioni sul proprietario della message box.
* @author  fabio
*/
package JAM;
import java.rmi.*;

public interface RemoteMessageBox extends Remote{

	/**
	* Inserisce nella coda di messaggi, il messaggio passato come parametro.
	* @param message, � il messaggio che si desidera inserire nella coda.
	*/
	public void writeMessage(Message message) throws RemoteException, InterruptedException;

	/**
	* Metodo che restituisce l'agente proprietario della coda.
	* @return proprietario della coda.
	*/
	public PersonalAgentID getOwner() throws RemoteException;

}
