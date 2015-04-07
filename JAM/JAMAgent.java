/**
* class JAMAgent, utilizzata nell'esempio dell'asta per definire la classe banditore e clientAgent.
* @author fabio
*/
package JAM;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.lang.*;
import java.rmi.RemoteException;
import javax.naming.*;

public abstract class JAMAgent extends Observable{

	private MessageBox myMessageBox;// MessageBox di questo JAMAgent
    private PersonalAgentID myID;// Agente proprietario di questo JAMAgent
    private ADSL adsl;// ADSL a cui si collega questo JAMAgent

    // Dati dell'ADSL
    private String name;
    private String ip;
    private int port;

	protected List<JAMBehaviour> myBehaviours;

	protected boolean check = true;
	JAMAgentMonitor frame;

	/**
	* Costruttore JAMAgent, dove agentID è il nome dell'agente e il suo ruolo,
	* e nome, ip e porta si riferiscono all'rmiregistry.
	* @param agentID AgentID proprietario della JAMAgent
	* @param ip indirizzo ip della ADSL a cui il JAMAgent si collega
	* @param name nome dell'ADSL a cui il JAMAgent si collega
	* @param port porta dell'ADSL a cui il JAMAgent si collega
	*/
	public JAMAgent(AgentID agentID, String ip, String name, int port) throws RemoteException, JAMADSLException{
		this.myID = (PersonalAgentID)agentID;
		myMessageBox = new MessageBox(myID, 25);
		myBehaviours = new LinkedList<JAMBehaviour>();
		this.ip = ip;
		this.name = name;
		this.port = port;
		frame  = new JAMAgentMonitor(this);
	}

	/**
	* Costruttore JAMAgent, dove agentID è il nome dell'agente e il suo ruolo,
	* tutto il resto è impostato di default.
	* @param agentID, di tipo AgentID è il nome dell'agente e il suo ruolo
	* @throws JAMADSLException, sollevo una eccezione se non va a buon fine
	* la creazione della messageBox.
	*/
	public JAMAgent(AgentID agentID) throws JAMADSLException{
		myBehaviours = new LinkedList<JAMBehaviour>();
		this.myID=(PersonalAgentID)agentID;
		try{
			myMessageBox = new MessageBox(myID, 25);
		}catch(RemoteException ecc) {throw new JAMADSLException();}
		this.ip = "127.0.0.1";
		this.name = "ADSL";
		this.port = 1099;
		frame = new JAMAgentMonitor(this);
	}

	/**
	* Metodo che aggiunge un comportamento behaviour alla lista myBehaviours.
	* @param behaviour comportamento dell'agente.
	*/
	public void addBehaviour(JAMBehaviour behaviour){
		myBehaviours.add(behaviour);
	}

	/**
	* Restituisce il proprietario di questa JAMAgent
	* @return PersonalAgentID proprietario di questa JAMAgent
	*/
	public PersonalAgentID getMyID(){
		return myID;
	}

	/**
	* modifica il proprietario di questa JAMAgent
	* @param myID, nuovo proprietario
	*/
	public void setMyID(PersonalAgentID myID){
		this.myID = myID;
	}

	/**
	* Metodo che restituisce il nome dell'ADSL inserito nello rmiregistry
	* @return name, nome dell'ADSL.
	*/
	public String getName(){
		return name;
	}

	/**
	* Metodo che modifica il nome dell'ADSL nel rmiregistry
	* @param name, nome nuovo dell'ADSL
	*/
	public void setName(String name){
		this.name = name;
	}

	/**
	* restituisce il valore dell'IP dell'ADSL
	* @return, il Valore dell'indirizzo ip della ADSL
	*/
	public String getIP(){
		return this.ip;
    }

	/**
	* Modifica il valore IP dell'ADSL con quello passato come parametro
	* @param ip Stringa che contiene il nuovo ip della'ADSL
	*/
    public void setIP(String ip){
        this.ip = ip;
    }

	/**
	* restituisce il valore della porta della ADSL su cui è collegato
	* questo JAMAgent
	* @return, valore della porta dell'ADSL
	*/
    public int getPort(){
        return this.port;
    }

	/**
	* modifica il valore porta dell'ADSL con il valore specificato
	* @param port Valore della porta dell'ADSL
	*/
    public void setPort(int porta){
        this.port = porta;
    }

	/**
	* Metodo che crea un oggetto di tipo thread per ogni comportamento di tipo JAMBehaviour
	* non già in esecuzione presente in myBehaviours e invoca su di esso il metodo start.
	*/
	public void start(){
		for(JAMBehaviour tmp: myBehaviours){
			if(!(tmp.isRunning())){
				// creazione del thread Comportamento
				Thread myThread = new Thread(tmp);
				tmp.setMyThread(myThread);
				myThread.start();
			}
		}
		if(check){
			setChanged();
			notifyObservers("Agente" + myID + " start OK.");
			check=false;
		}
	}

	/**
	* effettua la lookup presso lo rmiregistry all'indirizzo ip/port dell'oggetto ADSL
	* di nome ADSL e memorizza tale riferimento remoto in adsl, quindi, se tutto è andato bene,
	* iscrive presso l'ADSL l'oggetto di tipo MessageBox myMessageBox.
	* @throws JAMADSLException se viene lanciata una eccezione dall'oggetto ADSL
	*/
	public void init(){
		try{
			adsl = (ADSL)Naming.lookup("rmi://" + ip +":"+port + "/"+ name);
			adsl.insertRemoteMessageBox(myMessageBox);
			setChanged();
			notifyObservers("Agente" + myID + " init OK.\n");
		}catch(Exception e){
			setChanged();
			notifyObservers("Agente" + myID + " init fallita.");
			System.err.println("Eccezione init su jameAgent " + e);
		}
	}

	/**
	* effettua la rimozione della casella myMessageBox dall'ADSL;
	* @throws JAMADSLException se la messageBox non e' presente nel
	* registro della ADSL
	*/
	public void destroy(){
		try{
			adsl.removeRemoteMessageBox(myID);
			setChanged();
			notifyObservers("Agente " + myID + " destroy OK");
		}catch(Exception e){
			setChanged();
			notifyObservers("Agente " + myID + " destroy KO");
			System.err.println("Eccezione init su jameAgent " + e);
		}
	}

	/**
	* Restituisce se sono presenti messaggi in questa messageBox
	* @return true se esistono messaggi con queste caratteristiche false altrimenti
	*/
	public boolean isThereMessage(){
		return myMessageBox.isThereMessage();
	}

	/**
	* Restituisce se sono presenti messaggi in questa messageBox inviati da un agente specifico
	* @param sender -> AgentID che ha inviato il messaggio
	* @return true se esistono messaggi con queste caratteristiche false altrimenti
	*/
	public boolean isThereMessage(AgentID sender){
		return myMessageBox.isThereMessage(sender);
	}

    /**
	* Restituisce se sono presenti messaggi in questa messageBox con una certa performativa
	* @param performative, tipo del messaggio cercato
	* @return true se esistono messaggi con queste caratteristiche false altrimenti
	*/
	public boolean isThereMessage(Performative performative){
		return myMessageBox.isThereMessage(performative);
	}

	/**
	* Restituisce se sono presenti messaggi in questa messageBox inviati da un agente specfico
	* e con una performativa specifica
	* @param sender, AgentID che ha inviato il messaggio
	* @param performative, tipo di performativa del messaggio
	* @return true se esistono messaggi con queste caratteristiche false altrimenti
	*/
	public boolean isThereMessage(AgentID messageAgent, Performative performative){
		return myMessageBox.isThereMessage(messageAgent, performative);
	}

	/**
	* richiede all'ADSL mediante il metodo getRemoteMessageBoxes gli oggetti
	* di tipo RemoteMessageBox il cui proprietario è specificato dal receiver del messaggio
	* message, su tali oggetti invoca la writeMessage di message;
	* @param msg Messaggio da inviare agli agenti
	* @throws JAMADSLException In caso di mal funzionamento della ADSL
	*/
	public void send(Message msg) throws JAMADSLException{
		try{
			List<RemoteMessageBox> temp = adsl.getRemoteMessageBox(msg.getReceiver());
			for(RemoteMessageBox rmb : temp) rmb.writeMessage(msg);
			String log = "\n Agent " + myID + ": \nPerform " + msg.getPerformative() + " \n to " + msg.getReceiver() + "msg ->" +msg.toString();
			setChanged();
			notifyObservers(log);
		}
		catch(RemoteException e){throw new JAMADSLException();}
		catch(Exception e){e.printStackTrace();}
	}

	/**
	* legge della propria casella myMessageBox un messaggio mediante
	* il metodo readMessage() con le caratteristiche specificate dai parametri
	* @throws JAMBoxException se non sono presenti messaggi
	* @return Il primo messaggio presente nel box
	*/
	public Message receive() throws JAMBehaviourInterruptedException{
		Message msg = null;
		try{
			msg = myMessageBox.readMessage();
			String log = "Agent " + myID + " RECEIVE";
			setChanged();
			notifyObservers(log);
		}catch(JAMMessageBoxException err){ throw new JAMBehaviourInterruptedException();}
		return msg;
	}

	/**
	* legge della propria casella myMessageBox un messaggio mediante
	* il metodo readMessage() con le caratteristiche specificate dai parametri
	* @param sender Agente che ha inviato il messaggio
	* @return Il primo messaggio inviato dall'Agente specificato.
	*/
	public Message receive(AgentID sender) throws JAMBehaviourInterruptedException{
		Message msg = null;
		try{
			msg = myMessageBox.readMessage(sender);
			String log = "Agent " + myID + " RECEIVE";
			setChanged();
			notifyObservers(log);
		}catch(JAMMessageBoxException err){ throw new JAMBehaviourInterruptedException();}
		return msg;
	}

	/**
	* legge della propria casella myMessageBox un messaggio mediante
	* il metodo readMessage() con le caratteristiche specificate dai parametri
	* @param performative tipo del messaggio cercato.
	* @return Il primo messaggio presente nel box con performativa specificata
	*/
	public Message receive(Performative performative) throws JAMBehaviourInterruptedException{
		Message msg = null;
		try{
			msg = myMessageBox.readMessage(performative);
			String log = "Agent " + myID + " RECEIVE";
			setChanged();
			notifyObservers(log);
		}catch(JAMMessageBoxException err){throw new JAMBehaviourInterruptedException();}

		return msg;
	}

	/**
	* legge della propria casella myMessageBox un messaggio mediante
	* il metodo readMessage() con le caratteristiche specificate dai parametri
	* @param sender Agente che ha inviato il messaggio
	* @param performative tipo del messaggio cercato
	* @throws JAMBoxException se non sono presenti messaggi del tipo rich.
	* @return Il primo messaggio di una certa performativa presente nel box
	* inviato dall'Agente specificato
	*/
	public Message receive(AgentID agent, Performative performative) throws JAMBehaviourInterruptedException{
		Message msg = null;
		try{
			msg = myMessageBox.readMessage(agent, performative);
			String log = "Agent " + myID + " RECEIVE";
			setChanged();
			notifyObservers(log);
		}catch(JAMMessageBoxException err){ throw new JAMBehaviourInterruptedException();}
		return msg;
	}

}

