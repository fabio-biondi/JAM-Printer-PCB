/**
* Classe ProvaRemoteMessageBoxUno.
* @author fabio
*/
package JAM;
import java.rmi.*;
import java.util.*;

public class ProvaRemoteMessageBoxUno{
	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args){
		ADSL adsl;
		PersonalAgentID myID;
		MessageBox box1;

		try{
// creo il mio primo agente
			myID = new PersonalAgentID("Paolo","Rossi");
			adsl = (ADSL)Naming.lookup("rmi://127.0.0.1:2000/ADSL");
// creo la prima message box con proprietario myID
			box1 = new MessageBox(myID);
// tramite ADSL inserisco la message box
			adsl.insertRemoteMessageBox(box1);
// creo un secondo agente che sar� il mittente del messaggio inviato a myID
			AgentID agentRemoteID = new PersonalAgentID("Marco", "Verdi");
            Message msg = new Message(agentRemoteID, myID,Performative.UNKNOWN,"prova!",null);

// estraggo la messageBox di Paolo Rossi ovvero l'unica inserita
			List<RemoteMessageBox> boxRequest = adsl.getRemoteMessageBox(myID);
			System.out.println("Scrittura messaggio");
			for(int i = 0; i < boxRequest.size(); i++) {
				boxRequest.get(i).writeMessage(msg);	// scrivo il messaggio nella messageBox
			}
// leggo il messaggio inserito precedentemente
			Message msgRead = box1.readMessage();

			System.out.println("Messaggio Ricevuto "+msgRead);
			System.out.println("Rimozione MessageBox da ADSL");
// rimozione dellla MessageBox di Paolo Rossi
			adsl.removeRemoteMessageBox(myID);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.exit(0);
	}
}
