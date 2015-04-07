/**
* Classe MessageBoxNoSync che rappresenta una lista di messaggi
* con descrizione di mittente e numero massimo di messaggi contenuti.
* @author fabio
*/
package JAM;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;
import javax.naming.*;
public class MessageBoxNoSync extends UnicastRemoteObject{

	private PersonalAgentID owner;	// agente concreto che indica il proprietario di una casella postale
	private List<Message> box;	//coda dei messaggi ricevuti dall'utente proprietario della casella
	private int maxMessage;	// contiene il numero massimo di messaggi inseriti in box
	protected final int DEFAULT_MAX_MESSAGE = 20;	// Numero massimo (di DEFAULT) di messaggi che la lista può memorizzare

	/**
	* Costruttore della classe MessageBoxNoSync, che specifica il proprietario della casella postale
	* e il numero massimo di messaggi che possono essere inseriti nella lista.
	* @param owner proprietario della casella di posta di tipo PersonalAgentID.
	* @param maxMessage indica il numero massimo di messaggi che possono essere inseriti nella lista box.
	*/
	public MessageBoxNoSync(PersonalAgentID owner, int maxMessage) throws RemoteException{
		this.owner = owner;
		this.box = new LinkedList<Message>(); // in alternativa: new Vector<Message>(); new ArrayList<Message>();
		this.maxMessage = maxMessage;
	}

	/**
	* Costruttore della classe MessageBoxNoSync, che specifica il proprietario della casella postale
	* mentre il numero massimo di messaggi che possono essere inseriti nella lista è impostato di default.
	* @param owner proprietario della casella di posta di tipo PersonalAgentID.
	*/
	public MessageBoxNoSync(PersonalAgentID owner) throws RemoteException{
		this.owner = owner;
		this.maxMessage = DEFAULT_MAX_MESSAGE;
		this.box = new LinkedList<Message>();	// in alternativa: new Vector<Message>(); new ArrayList<Message>();
	}

	/**
	* Costruttore della classe MessageBoxNoSync senza parametri
	* il numero massimo di messaggi che possono essere inseriti nella lista è impostato di default.
	*/
	public MessageBoxNoSync() throws RemoteException{
		this.owner = null;
		this.maxMessage = DEFAULT_MAX_MESSAGE;
		this.box = new LinkedList<Message>();	// in alternativa: new Vector<Message>(); new ArrayList<Message>();
	}


	/**
	* Metodo che restituisce il proprietario della casella di posta di tipo PersonalAgentID.
	* @return proprietario casella di posta.
	*/
	public PersonalAgentID getOwner(){
		return owner;
	}

	/**
	* Metodo per modificare il proprietario della casella di posta.
	* @param agente nuovo proprietario di tipo PersonalAgent.
	*/
	public void setOwner(PersonalAgentID agente){
		owner = agente;
	}

	/**
	* Metodo che restituisce true se la casella di posta è vuota.
	* @return boolean true => vuota, false => non vuota.
	*/
	public boolean isBoxEmpty(){
		if(box.size() == 0) return true; // size indica il numero di elementi inseriti in quel momento
		else return false;
	}

	/**
	* Metodo che restituisce true se la casella di posta è piena.
	* @return boolean true => piena, false => non piena.
	*/
	public synchronized boolean isBoxFull(){
		if(box.size() >= maxMessage) return true;
		else return false;
	}


				/*+++++++++++++++++++++++++++++++++++++++
				+										+
				+	inizio dei metodi readMessage		+
				+										+
				+++++++++++++++++++++++++++++++++++++++*/



	/**
	* Metodo che elimina dalla coda il primo messaggio (quello in attesa da più tempo) e lo restituisce
	* @return il più vecchio messaggio in attesa (se presente).
	* @throws JAMMessageBoxException coda di messaggi vuota.
	*/
	public Message readMessage() throws JAMMessageBoxException{
		Message toReturn = readMessage(new GenericAgentID());
		if(toReturn == null) throw new JAMMessageBoxException("Impossibile leggere un messaggio: Coda di messaggi vuota");
		return toReturn;
	}

	/**
	* Metodo che legge il primo messaggio in coda inviato da un certo agente
	* @param sender, mittente del messaggio.
	* @return il primo messaggio inviato da uno specifico mittente (se presente).
	* @throws JAMMessageBoxException non esistono messaggi inviati dall'agente richiesto.
	*/
	public Message readMessage(AgentID sender) throws JAMMessageBoxException{
		Message toReturn = getMsg(sender, null);
		if(toReturn == null) throw new JAMMessageBoxException("Errore: il messaggio inviato da " + sender.getName() + " non è presente!");
		return toReturn;
	}

	/**
	* Metodo che legge il primo messaggio in coda corrispondente ad una certa performativa
	* @param performative, performativa da cercare tra i messaggi
	* @return il primo messaggio in coda con performativa corrispondente al parametro passato.
	* @throws JAMMessageBoxException non esistono messaggi con la performativa richiesta.
	*/
	public Message readMessage(Performative performative) throws JAMMessageBoxException{
		Message toReturn = getMsg(new GenericAgentID(), performative);
		if(toReturn == null) throw new JAMMessageBoxException("Errore: non sono presenti messaggio con performative = "+performative);
		return toReturn;
	}

	/**
	* Metodo che legge il primo messaggio in coda inviato da un certo agente e corrispondente ad una certa performativa
	* @param messageAgent, messaggio inviato da un certo agente.
	* @param performative, performativa da cercare tra i messaggi.
	* @return il primo messaggio in coda con agente e performativa corrispondente ai parametri passati.
	* @throws JAMMessageBoxException non esistono messaggi inviati dall'agente richiesto e con la performativa desiderata.
	* @throws JAMMessageBoxException coda di messaggi vuota.
	*/
	public Message readMessage(AgentID messageAgent, Performative performative) throws JAMMessageBoxException{
		Message toReturn = getMsg(messageAgent, performative);
		if(toReturn == null) throw new JAMMessageBoxException("Errore: non sono presenti messaggi con mittente = " +
			messageAgent.getName() + "e performative = " + performative);
		return toReturn;
	}

				/*+++++++++++++++++++++++++++++++++++++++
				+										+
				+	fine dei metodi readMessage			+
				+   inizio dei metodi isThereMessage	+
				+										+
				+++++++++++++++++++++++++++++++++++++++*/

	/**
	* Metodo che conferma se c'è o meno un elemento da leggere in coda.
	* @return true se l'elemento è presente, false altrimenti.
	*/
	public boolean isThereMessage(){
		if (box.size() != 0) return true;
		else return false;
	}

	/**
	* Metodo che conferma se c'è o meno un elemento da leggere in coda inviato da un agente specifico.
	* @param sender, mittente del messaggio.
	* @return true se l'elemento è presente, false altrimenti.
	*/
	public boolean isThereMessage(AgentID sender){
		if (box.size() != 0){
			for(int i=0; i<box.size(); i++){
				if(((box.get(i)).getSender()).equals(sender)) return true;
			}
		}
		return false;
	}

	/**
	* Metodo che conferma se c'è o meno un elemento da leggere in coda corrispondente ad una certa performativa
	* @param performative, performativa da cercare tra i messaggi
	* @return true se l'elemento è presente, false altrimenti.
	*/
	public boolean isThereMessage(Performative performativa){
		if (box.size() != 0){
			for(int i=0; i<box.size(); i++){
				if((box.get(i).getPerformative()).equals(performativa)) return true;
			}
		}
		return false;
	}

	/**
	* Metodo che conferma se c'è o meno un elemento da leggere in coda inviato da un certo agente corrispondente ad una certa performativa
	* @param messageAgent, messaggio inviato da un certo agente.
	* @param performative, performativa da cercare tra i messaggi.
	* @return true se l'elemento è presente, false altrimenti.
	*/
	public boolean isThereMessage(AgentID sender, Performative performativa){
		if (box.size() != 0){
			for(int i=0; i<box.size(); i++){
				if ((((box.get(i)).getSender()).equals(sender)) && ((box.get(i).getPerformative()).equals(performativa))) return true;
			}
		}
		return false;
	}

				/*
				+++++++++++++++++++++++++++++++++++++++++
				+										+
				+	fine dei metodi isThereMessage		+
				+										+
				+++++++++++++++++++++++++++++++++++++++++
				*/



	/**
	* Metodo di supporto che restituisce il messaggio che soddisfa i requisiti specificati
	* dai suoi parametri se presente in coda, null altrimenti.
	* @param agent, messaggio inviato da un certo agente.
	* @param perf, performativa su cui cercare l'agente.
	* @return il messaggio cercato se contenuto nel box, null altrimenti.
	*/
	protected Message getMsg(AgentID agent, Performative performative){
		Message messaggio = null;
		boolean trovato = false;
		Iterator<Message> lista = box.iterator();

		while((lista.hasNext()) && (trovato == false)){
			messaggio = lista.next();

			if(agent.equals(messaggio.getSender())){
				if(performative == null){
					trovato = true;
				}
				else{
					if(messaggio.getPerformative() == performative){
						trovato = true;
					}
				}
				if(trovato)
					lista.remove();
		 	}
		}
		if(trovato) return messaggio;
		else return null;
	}

	/**
	* Metodo che inserisce in coda alla casella il messaggio passato come parametro
	* @param message, messaggio che si desidera inserire.
	* @throws JAMMessageBoxException se la coda di messaggi è piena non posso inserire nulla di nuovo
	* @throws JAMMessageBoxException se il destinatario del  messaggio non corrisponde
	* con il proprietario della casella di posta
	*/
	public void writeMessage(Message message)throws JAMMessageBoxException{
		AgentID proprietarioMessaggioParametro = message.getReceiver();
		if(isBoxFull()) throw new JAMMessageBoxException("Impossibile eseguire l'operazione: coda messaggi piena");
		else
			if(proprietarioMessaggioParametro.equals(owner))	box.add(message.clone());
			else throw new JAMMessageBoxException("Impossibile eseguire l'operazione: il ricevente non corrisponde con il proprietario della casella di posta");
	}

	/**
	* metodo che stampa la lista contenente i messaggi
	*/
	public void stampaLista(){
		System.out.println("\n ***STAMPA LISTA MESSAGGI*** \n");
		if(isBoxEmpty()) System.out.println("Lista vuota");
		else{
			for(int i = 0; i < box.size(); i++){
				System.out.println("MESSAGGIO N " +i);
				System.out.println(box.get(i));
				//System.out.println("\n");
			}
		}
	}
}
