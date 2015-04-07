/**
* class JAMSimpleBehaviour.
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

public abstract class JAMSimpleBehaviour extends JAMBehaviour{

	/**
	* Costruttore della classe JAMSimpleBehaviour
	* @param jamAgent.
	*/
	public JAMSimpleBehaviour(JAMAgent jamAgent){
		super(jamAgent);
	}

	/**
	* Metodo che esegue una volta sola il comportamento di un'agente.
	*/
	public void run(){
		try{
			setup();
			//if(!isDone())
				action();
			//else return;
		}catch(JAMBehaviourInterruptedException err){
			if(isDone()) return;
			System.out.println(err);
		}finally{
			try{
				dispose();
			}catch(JAMBehaviourInterruptedException err){
				System.out.println(err);
			}
		}
	}

}
