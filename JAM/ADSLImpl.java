/**
* Classe ADSLImpl, classe che implementa l'interfaccia remota ADSL e contiene il campo messageBoxes
* che contiene tutti gli oggetti remoti di tipo MessageBoxes.
* @author fabio
*/
package JAM;
import java.util.*;
import java.lang.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Remote;
import javax.naming.*;
import java.rmi.server.*;
import java.rmi.*;
//import java.io.Serializable;

public class ADSLImpl extends UnicastRemoteObject implements ADSL{

	private List<RemoteMessageBox> messageBoxes;

	private String ip;
	private int port;
	private String name;

	public static int PORT = 2000;
	public static String NAME ="ADSL";
	public static String IP = "127.0.0.1";

	private List<Observer> osservatori;
	//private Observable ADSL;
	private String log;

	/**
	* Costruttore della classe ADSLImpl.
    * @param ip, contiene l'indirizzo IP su cui e' disponibile rmiregistry
    * @param name, contiene il nome dell'ADSL su cui e' disponibile rmiregistry
    * @param port, contiene il numero della porta su cui e' disponibile rmiregistry
	*/
	public ADSLImpl(String ip, int port, String name) throws RemoteException{
		this.ip = ip;
		this.port = port;
		this.name = name;
	  	messageBoxes = new LinkedList<RemoteMessageBox>();
	  	osservatori = new LinkedList<Observer>();
	}

	/**
	* Costruttore della classe ADSLImpl senza parametri, valori impostati tutti di default.
	*/
	public ADSLImpl() throws RemoteException{
		this.name = NAME;
		this.port = PORT;
		this.ip = IP;
		this.messageBoxes = new LinkedList<RemoteMessageBox>();
		osservatori = new LinkedList<Observer>();
	}

	/**
	* Costruttore della classe ADSLImpl con ip impostato di default.
	* @param name, contiene il nome dell'ADSL su cui e' disponibile rmiregistry
    * @param port, contiene il numero della porta su cui e' disponibile rmiregistry
	*/
	public ADSLImpl(String name, int port) throws RemoteException{
		this.name = name;
		this.port = port;
		this.ip = IP;
		this.messageBoxes = new LinkedList<RemoteMessageBox>();
		osservatori = new LinkedList<Observer>();
	}

	/**
	* Metodo che restituisce una lista di riferimenti ad oggetti (remoti) di tipo RemoteMessageBox i cui proprietari sono uguali a agentID.
	* @param agentID, per il confronto con i proprietari della lista di RemoteMessageBox.
	* @return una lista di riferimenti ad oggetti di tipo RemoteMessageBox.
	*/
	public synchronized List<RemoteMessageBox> getRemoteMessageBox(AgentID agentID) throws RemoteException{
		stampaNotifica("ricerca MessageBox con proprietario: " + agentID.toString());

		List<RemoteMessageBox> lista = new LinkedList<RemoteMessageBox>();
		for(RemoteMessageBox temp: messageBoxes){
			if(agentID.equals(temp.getOwner())) lista.add(temp);
		}
		if(lista.isEmpty()){
			stampaNotifica(agentID.toString() + " non ha MessageBox registrate");
			stampaNotifica("------------------------------\n");
		}
		return lista;
	}

	/**
	* Metodo che richiede l’inserimento di messageBox presso l’ADSL, se l’elemento è gia presente
	* non viene effettuata alcuna operazione e viene lanciata un’opportuna eccezione.
	* @param messageBox, elemento da inserire nell'ADSL.
	*/
	public synchronized void insertRemoteMessageBox(RemoteMessageBox messageBox) throws RemoteException{
		stampaNotifica("Richiesto inserimento di una nuova MessageBox di : " + messageBox.getOwner());
		for(RemoteMessageBox temp: messageBoxes){
			if(messageBox.getOwner().equals(temp.getOwner())){
				stampaNotifica("Impossibile inserire: Message box a nome di: "+messageBox.getOwner()+" già presente.");
				stampaNotifica("----------------------------\n");
			 	throw new RemoteException("Impossibile effettuare l'inserimento");
			}
		}
		messageBoxes.add(messageBox);
	}

	/**
	* Metodo che richiede la cancellazione dell’oggetto RemoteMessageBox presente presso l’ADSL di proprietà dell’agente agentID.
	* Se ’elemento non è presente non viene effettuata alcuna operazione e viene lanciata un’opportuna eccezione.
	* @param agentID, per il confronto dell'oggetto RemoteMessageBox.
	*/
	public synchronized void removeRemoteMessageBox(AgentID agentID) throws RemoteException{
		RemoteMessageBox box;
		stampaNotifica("Rimozione MessageBox di: "+agentID.toString());
		Iterator<RemoteMessageBox> temp = messageBoxes.iterator();
		boolean remove = false;
		while(temp.hasNext()){
			box = temp.next();
			if(agentID.equals(box.getOwner())){
				temp.remove();
				remove = true;
				stampaNotifica("Cancellata box appartenente all'agente : " + agentID);
				stampaNotifica("----------------------------\n");
			}
		}
		if(!remove){
			stampaNotifica("Nessuna box presente dell'agente : " + agentID);
			//throw new RemoteException("Nessun messaggio appartenente all'agente " + agentID + " è presente nella lista");
		}
	}

	/**
	* Metodo che stampa un avviso della notifica e avvisa tutti gli observer dell'oggetto.
	* @param informazioni, Stringa da stampare.
	*/
	private void stampaNotifica(String info){
		System.out.println(info);
		notifyObservers(info);
	}

	/**
	* Metodo che avvisa tutti gli observer dell'oggetto.
	* @param informazioni, Stringa da comunicare.
	*/
	public synchronized void notifyObservers(String info){
		for(Observer temp : osservatori) temp.update(null, info);
	}

	/**
	* Metodo che aggiunge l'oggetto ali osservatori.
	* @param informazioni, oggetto osservatore.
	*/
	public synchronized void addObserver(Observer ob){
		osservatori.add(ob);
	}

	/**
	* Metodo che avvia un processo rmiregistry sulla porta port della macchina su cui l’applicativo è eseguito;
	*/
	public void startRMIRegistry(){
		try{
			java.rmi.registry.LocateRegistry.createRegistry(port);
			System.out.println("RMIRegistry avviato");
		}
		catch(Exception e){
			System.err.println("Errore startRMIRegistry" + e);
			System.exit(1);
		}
	}

	/**
	* Metodo che iscrive (rebind) l’oggetto ADSLImpl corrente sullo rmiregistry con nome ADSL;
	*/
	public void startADSL(){
		try{
			Naming.rebind("rmi://"+ip+":"+port+"/"+name,this);
			System.out.println("ADSL attivato");
		}
		catch(Exception e){
			System.err.println("errore startADSL " + e);
			System.exit(1);
		}
	}

	/**
	* Metodo che cancella (unbind) l’oggetto ADSLImpl corrente dallo rmiregistry.
	*/
	public void stopADSL(){
		try{
			Naming.unbind("rmi://"+ip+":"+port+"/"+name);
			System.out.println("Oggetto rimosso");
		}
		catch(Exception e){
			System.err.println("errore stopADSL " + e);
			System.exit(1);
		}
	}
}

