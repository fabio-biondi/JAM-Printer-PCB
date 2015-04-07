/**
* Classe ProvaRemoteMessageBoxTre.
* @author fabio
*/
package JAM;
import java.rmi.*;
import java.util.*;

public class ProvaRemoteMessageBoxTre{
	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args){
		ADSL adsl;
		PersonalAgentID mittente, destinatario;
		MessageBox box1;

		try{

			mittente = new PersonalAgentID("Luca","Bianchi");
			destinatario = new PersonalAgentID("Marco","Verdi");
			adsl = (ADSL)Naming.lookup("rmi://127.0.0.1:2000/ADSL");
            Message msg = new Message(mittente, destinatario ,Performative.UNKNOWN,"prova!",null);
// prelevo la messageBox di Marco Verdi e scrivo un messaggio dentro in modo da sbloccare ProvaRemoteMessageBoxDue
			List<RemoteMessageBox> boxRequest = adsl.getRemoteMessageBox(destinatario);
			System.out.println("Scrittura messaggio");
			for(int i = 0; i < boxRequest.size(); i++) {
				boxRequest.get(i).writeMessage(msg);	// scrivo il messaggio nella messageBox
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.exit(0);
	}
}
