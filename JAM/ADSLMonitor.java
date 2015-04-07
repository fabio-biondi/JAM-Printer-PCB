/**
* Classe ADSLMonitor: classe utilizzata per creare una interfaccia grafica che permetta
* di gestire l'ADSL e viasualizzare i messaggi di log.
* @author fabio
*/
package JAM;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.rmi.*;

public class ADSLMonitor extends JFrame implements Observer{

	private JTextArea display1;
	public static int porta;

	public ADSLMonitor(ADSLImpl adsl){

		CreaInterfaccia interfaccia = new CreaInterfaccia(adsl);
		add(interfaccia);
		Container cp = getContentPane();
		cp.add(interfaccia);
		setTitle("Agent Directory Service Layer Monitor");
    	setVisible(true);
		pack();
	}

	public void update(Observable ob, Object extra_arg){
		display1.append("\n"+extra_arg);
		display1.setCaretPosition(display1.getText().length()); // probabilmente superfluo, da controllare
	}

	public class CreaInterfaccia extends JPanel{

		public CreaInterfaccia(ADSLImpl adsl){

			super(new BorderLayout());

			// creazione NORD
			JPanel pannelloNord = new JPanel();
			JLabel port = new JLabel("Port :        ");
			pannelloNord.add(port);
			JTextField display = new JTextField(30);
			display.setText(Integer.toString(porta));
			pannelloNord.add(display);
			JButton start = new JButton("Start reg");
			pannelloNord.add(start);
			add(pannelloNord, BorderLayout.NORTH);

			// creazione label OVEST
			JLabel connection = new JLabel("<html><b>Connection</b><br>Console :</html>");
			add(connection, BorderLayout.WEST);

			// creazione display CENTRO
			display1 = new JTextArea(30, 40);
			add(display1, BorderLayout.CENTER);

			// creazione EST
			JPanel pannelloEST = new JPanel();
			pannelloEST.setLayout(new BorderLayout());
			JButton startUp = new JButton("Start up");
			pannelloEST.add(startUp, BorderLayout.NORTH);
			JButton stop = new JButton("Shutdown");
			pannelloEST.add(stop, BorderLayout.SOUTH);
			add(pannelloEST, BorderLayout.EAST);

			// creazione SUD
			ExitButton exit = new ExitButton();
			add(exit, BorderLayout.SOUTH);

			ADSLAction startAction = new ADSLAction(adsl);
			ADSLAction startRegAction = new ADSLAction(adsl);
			ADSLAction stopAction = new ADSLAction(adsl);

			start.addActionListener(startAction);
			startUp.addActionListener(startRegAction);
			stop.addActionListener(stopAction);

		}

		private class ADSLAction implements ActionListener{
			ADSLImpl adsl;
			public ADSLAction(ADSLImpl adsl){
				this.adsl = adsl;
			}

			public void actionPerformed(ActionEvent event){
				JButton source = (JButton)event.getSource();
				if(source.getText().equals("Start reg")) {
					adsl.startRMIRegistry();
					display1.append("Start Reg Avviato\n");
				}
				else if(source.getText().equals("Start up")){
					adsl.startADSL();
					display1.append("Start Up Avviato\n");
				}
				else{
					adsl.stopADSL();
					display1.append("Oggetto Rimosso\n");
				}
			}
		}
	}

	public static void main(String[]args){
		try{
			System.out.println("impostare la porta in cui avviare lo rmiregistry: ");
			Scanner tastiera = new Scanner(System.in);
			porta = tastiera.nextInt();
			ADSLImpl adsl = new ADSLImpl("ADSL", porta);
			ADSLMonitor frame = new ADSLMonitor(adsl);
			//aggiungo un osservatore per l'ADSL
			adsl.addObserver(frame);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);

		}catch(RemoteException e){
			System.err.println("Errore: Collegamento ADSL non riuscito!");
			System.exit(1);
		}
	}
}

class ExitButton extends JButton implements ActionListener{
	public ExitButton(){
		super("Exit");
		addActionListener(this);
	}
	public void actionPerformed(ActionEvent e){
		System.exit(0);
	}
}





