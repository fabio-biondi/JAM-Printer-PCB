/**
* Classe MessageBox, estende MessageBoxNoSync e implementa l'interfaccia RemoteMessageBox.
* Fornisce metodi sincronizzati per le gestione delle MessageBox
* @author fabio
*/
package JAM;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.lang.*;
import java.rmi.RemoteException;

public class MessageBox extends MessageBoxNoSync implements RemoteMessageBox{

	/**
	* Costruttore della classe MessageBoxNoSync, che specifica il proprietario della casella postale
	* e il numero massimo di messaggi che possono essere inseriti nella lista.
	* @param owner proprietario della casella di posta di tipo PersonalAgentID.
	* @param maxMessage indica il numero massimo di messaggi che possono essere inseriti nella lista box.
	*/
	public MessageBox(PersonalAgentID owner, int maxMessage) throws RemoteException{
		super(owner, maxMessage);
	}

	/**
	* Costruttore della classe MessageBoxNoSync, che specifica il proprietario della casella postale
	* mentre il numero massimo di messaggi che possono essere inseriti nella lista � impostato di default.
	* @param owner proprietario della casella di posta di tipo PersonalAgentID.
	*/
	public MessageBox(PersonalAgentID owner) throws RemoteException{
		super(owner);
	}

	/**
	* Costruttore della classe MessageBoxNoSync senza parametri
	* il numero massimo di messaggi che possono essere inseriti nella lista � impostato di default.
	*/
	public MessageBox() throws RemoteException{
		super();
	}

	/**
	* Metodo che elimina dalla coda il primo messaggio (quello in attesa da pi� tempo) e lo restituisce
	* @return il pi� vecchio messaggio in attesa (se presente).
	*/
	public synchronized Message readMessage() throws JAMMessageBoxException{
		return readMsg(new GenericAgentID(),null);
	}

	/**
	* Metodo che legge il primo messaggio in coda inviato da un certo agente
	* @param sender, mittente del messaggio.
	* @return il primo messaggio inviato da uno specifico mittente (se presente).
	*/
	public synchronized Message readMessage(AgentID sender) throws JAMMessageBoxException{
		return readMsg(sender,null);
	}

	/**
	* Metodo che legge il primo messaggio in coda corrispondente ad una certa performativa
	* @param performative, performativa da cercare tra i messaggi
	* @return il primo messaggio in coda con performativa corrispondente al parametro passato.
	*/
	public synchronized Message readMessage(Performative performative) throws JAMMessageBoxException{
		return readMsg(new GenericAgentID(),performative);
	}

	/**
	* Metodo che legge il primo messaggio in coda inviato da un certo agente e corrispondente ad una certa performativa
	* @param messageAgent, messaggio inviato da un certo agente.
	* @param performative, performativa da cercare tra i messaggi.
	* @return il primo messaggio in coda con agente e performativa corrispondente ai parametri passati.
	*/
	public synchronized Message readMessage(AgentID messageAgent, Performative performative) throws JAMMessageBoxException{
		return readMsg(messageAgent,performative);
	}


	/**
	* Metodo che conferma se c'� o meno un elemento da leggere in coda.
	* @return true se l'elemento � presente, false altrimenti.
	*/
	public synchronized boolean isThereMessage(){
		return super.isThereMessage();
	}

	/**
	* Metodo che conferma se c'� o meno un elemento da leggere in coda inviato da un agente specifico.
	* @param sender, mittente del messaggio.
	* @return true se l'elemento � presente, false altrimenti.
	*/
	public synchronized boolean isThereMessage(AgentID sender){
		return super.isThereMessage(sender);
	}

	/**
	* Metodo che conferma se c'� o meno un elemento da leggere in coda corrispondente ad una certa performativa
	* @param performative, performativa da cercare tra i messaggi
	* @return true se l'elemento � presente, false altrimenti.
	*/
	public synchronized boolean isThereMessage(Performative performative){
		return super.isThereMessage(performative);
	}

	/**
	* Metodo che conferma se c'� o meno un elemento da leggere in coda inviato da un certo agente corrispondente ad una certa performativa
	* @param messageAgent, messaggio inviato da un certo agente.
	* @param performative, performativa da cercare tra i messaggi.
	* @return true se l'elemento � presente, false altrimenti.
	*/
	public synchronized boolean isThereMessage(AgentID messageAgent, Performative performative){
		return super.isThereMessage(messageAgent, performative);
	}

	/**
	* Metodo che inserisce in coda alla casella il messaggio passato come parametro
	* @param message, messaggio che si desidera inserire.
	* @throws InterruptedException eccezione sollevata dal wait.
	* @throws JAMMessageBoxException se la coda di messaggi � piena non posso inserire nulla di nuovo
	* con il proprietario della casella di posta
	*/
	public synchronized void writeMessage(Message message) {
		//System.out.println("scrivo nuovo messaggio");
		while(isBoxFull())
			try{
				System.out.println("lista piena attendo");
				wait();
			}
			catch(InterruptedException e){e.printStackTrace();}

			try{
				super.writeMessage(message);
				notifyAll();
			}
			catch(JAMMessageBoxException e){e.printStackTrace();}
	}


	/**
	* Metodo di supporto
	* @param agent agente
	* @param perf performativa di un messaggio
	* @throws InterruptedException eccezione sollevata dal wait.
	*/
	private synchronized Message readMsg(AgentID agent, Performative perf){
		Message mess = null;
		while((mess=getMsg(agent, perf))==null){ // utilizzo il metodo di supporto di MessageBoxNoSync
			try{
				System.out.println("Box di " + super.getOwner() + "non contiene messaggi");
				System.out.println("WAIT...	");
				wait();
			}
			catch(InterruptedException e){e.printStackTrace();}
		}
		notifyAll();
		return mess;
	}
}
