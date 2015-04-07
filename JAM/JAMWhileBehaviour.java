/**
* class JAMWhileBehaviour, ridefinisce il metodo run di thread.
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

public abstract class JAMWhileBehaviour extends JAMBehaviour{

	/**
	* Costruttore della classe JAMWhileBehaviour
	* @param jamAgent.
	*/
	public JAMWhileBehaviour(JAMAgent jamAgent){
		super(jamAgent);
	}

	/**
	* Metodo run che manda in esecuzione il comportamento ciclico dell'agente.
	*/
	public void run(){
		try{
			setup();
			while(!isDone()) action();
		}
		catch(JAMBehaviourInterruptedException err){
			if(isDone()) return;
			System.out.println(err);
		}
		finally{
			try{
				dispose();
			}
			catch(JAMBehaviourInterruptedException err){
				System.out.println(err);
			}
		}
	}

}
