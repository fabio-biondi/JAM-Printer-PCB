/**
* class JAMBehaviour, classe astratta che implementa l'interfaccia Runnable.
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

public abstract class JAMBehaviour implements Runnable{

	private boolean done; // indica se il comportamento è stato eseguito completamente
	private Thread myThread; // riferimwnto al thread che esegue il comportamento
	protected JAMAgent myAgent; // indica l'agente possessore di tale comportamento

	private boolean running;
	private int sleep;
	protected static int DEFAULT_SLEEP;

	/**
	* Costruttore della classe JAMBehaviour
	* @param agent indica l'agente possessore di tale comportamento.
	*/
	public JAMBehaviour(JAMAgent agent){
		myAgent = agent;
		done = false;
		//running = false;
	}

	/**
	* Metodo che setta done a true.
	*/
	public void done(){
		this.done = true;
		this.setRunning(false);
		myThread.interrupt(); //senza questa non si accorge di una eventuale richiesta di terminazione
	}

	/**
	* Metodo che restituisce il valore di done.
	* @return done, valore attuale della variabile.
	*/
	public boolean isDone(){
		return done;
	}

	/**
	* Metodo che restituisce il valore corrente della variabile booleana running per capire
	* se un comportamento è in esecuzione o meno.
	* @return running, valore della variabile.
	*/
	public boolean isRunning(){
		return running;
	}

	/**
	* Metodo che imposta il valore della variabile booleana running.
	* @param running, valore della modifica.
	*/
	public void setRunning(boolean running){
		this.running = running;
	}

	/**
	* Metodo che imposta il valore della variabile myThread.
	* @param myThread, valore che si desidera assegnare
	*/
	public void setMyThread(Thread myThread){
		this.myThread = myThread;
	}

	/**
	* Metodo che invoca la Thread.sleep sul thread corrente per ms millisecondi.
	* @param ms, ms di interruzione.
	*/
	public void sleep(int ms){
		try{
			myThread.sleep(ms);
		}catch(InterruptedException e){}
	}

	/**
	* Metodo astratto che definisce il codice da eseguire una o più volte.
	*/
	public abstract void action() throws JAMBehaviourInterruptedException;

	/**
	* Metodo astratto che definisce il codice da eseguire prima di lanciare il metodo action.
	*/

	public abstract void setup() throws JAMBehaviourInterruptedException;

	/**
	* Metodo astratto che definisce il codice da eseguire prima di terminare l'esecuzione del comportamento.
	*/
	public abstract void dispose() throws JAMBehaviourInterruptedException;

}
