/**
* Classe ProvaRemoteMessageBoxDue.
* @author fabio
*/
package JAM;
import java.rmi.*;
import java.util.*;

public class ProvaRemoteMessageBoxDue {

	public static void main(String[] args){
		ADSL adsl;
		PersonalAgentID myID;
		MessageBox box1;

		try{
// creo un agente di nome Marco Verdi
			myID = new PersonalAgentID("Marco","Verdi");
			adsl = (ADSL)Naming.lookup("rmi://127.0.0.1:2000/ADSL");
// creo una MessageBox a suo nome
			box1 = new MessageBox(myID);
// la inserisco tramite ADSL
			adsl.insertRemoteMessageBox(box1);
// creo un agente che sarà il mittente del messaggio
			AgentID agentRemoteID = new CategoryAgentID("Conti");
            Message msg = new Message(agentRemoteID, myID,Performative.UNKNOWN,"prova2!",null);
// estraggo la messageBox di Conti che non esiste
			List<RemoteMessageBox> boxRequest = adsl.getRemoteMessageBox(agentRemoteID);

// effettuo una richiesta di lettura messaggio a nome Marco Catalano ma vado in wait perchè la messageBox è vuota
			Message msgRead = box1.readMessage();

			System.out.println("Messaggio Ricevuto "+msgRead);
			System.out.println("Rimozione MessageBox da ADSL");
			adsl.removeRemoteMessageBox(myID);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.exit(0);
	}
}
