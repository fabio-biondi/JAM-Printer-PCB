/**
* Classe ProvaADSL, classe usata per testare ADSL e rmiregistry.
* @author fabio
*/
package JAM;
import java.rmi.*;
import java.rmi.server.*;
import javax.naming.*;

public class ProvaADSL{

	public static void main(String[]args) throws RemoteException{

		ADSLImpl adsl = new ADSLImpl();
		try{
			java.rmi.registry.LocateRegistry.createRegistry(2000);
			Naming.rebind("rmi://127.0.0.1:2000/ADSL", adsl);
			System.out.println("In attesa di invocazioni dai client");
		}
		catch(Exception e){
			System.err.println("Failed to bind to RMI Registry " + e);
			System.exit(1);
		}
	}
}
