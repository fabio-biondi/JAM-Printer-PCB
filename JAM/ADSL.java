/**
* interface ADSL, interfaccia remota che permette di modificare le MessageBox
* collaborando con RemoteMessageBox che offre le funzionalità di modifica.
* @author fabio
*/
package JAM;
import java.util.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;

public interface ADSL extends Remote{

	/**
	* Metodo che restituisce una lista di riferimenti ad oggetti (remoti) di tipo RemoteMessageBox i cui proprietari sono uguali a agentID.
	* @param agentID, parametro di confronto per trovare il proprietario della lista, può essere generic, category o personal.
	* @return  lista di riferimenti ad oggetti di tipo RemoteMessageBox.
	*/
	public List<RemoteMessageBox> getRemoteMessageBox(AgentID agentID) throws RemoteException;

	/**
	* Metodo che richiede l’inserimento di messageBox presso l’ADSL, se l’elemento è gia presente
	* non viene effettuata alcuna operazione e viene lanciata un’opportuna eccezione.
	* @param messageBox, elemento da inserire nell'ADSL.
	*/
	public void insertRemoteMessageBox(RemoteMessageBox messageBox) throws RemoteException;

	/**
	* Metodo che richiede la cancellazione dell’oggetto RemoteMessageBox presente presso l’ADSL di proprietà dell’agente agentID.
	* Se ’elemento non è presente non viene effettuata alcuna operazione e viene lanciata un’opportuna eccezione.
	* @param agentID, per il confronto dell'oggetto RemoteMessageBox.
	*/
	public void removeRemoteMessageBox(AgentID agentID) throws RemoteException;

}


