/**
* class JAMAgentMonitor, classe che realizza un interfaccia grafica di un agente
* che permetta di monitorare l'attività di un agente visualizzando i messaggi inseriti
* nella MessageBox locale e quelli successivamente letti.
* @author fabio
*/
package JAM;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.rmi.RemoteException;

public class JAMAgentMonitor extends JFrame implements Observer{

	private JAMAgent agent; // riferimento all'oggetto JAMAgent di cui questa istanza è monitor
	private AgentID agentID;

	/**
	* Costruttore di JAMAgentMonitor.
	* @param agent serve per avere un riferimento
	*/
	public JAMAgentMonitor(JAMAgent ag){
		agent = ag;
		agent.addObserver(this); // JamAgent crea un monitor che lo osserva
		CreaInterfaccia interfaccia = new CreaInterfaccia();
		Container cp = getContentPane();
		cp.add(interfaccia);
		setTitle("JAM Agent Monitor --> "+ag.getMyID().getName());
		setVisible(true);
		pack();
	}

	/**
	* Metodo che inizializza l'agente.
	*/
	public void initJAMAgent() throws RemoteException{
		agent.init();
	}

	/**
	* Metodo che fa partire il comportamento dell'agente.
	*/
	public void startJAMAgent(){
		agent.start();
	}

	/**
	* Metodo che termina il comportamento dell'agente.
	*/
	public void destroyJAMAgent() throws RemoteException{
		agent.destroy();
	}

	/**
	* Metodo che aggiorna il display ogni volta che l'oggetto osservato viene modificato.
	* @param ob e l'oggetto Observable che notifica il cambiamento
	* @param extra_arg puo essere usato per passare informazioni addizionali.
	*/
	public void update(Observable ob, Object extra_arg){
		display1.append("\n " + extra_arg);
		display1.setCaretPosition(display1.getText().length());
	}

	private class CreaInterfaccia extends JPanel{

		public CreaInterfaccia(){

			super(new BorderLayout());

			// creazione label OVEST
			JLabel connection = new JLabel("<html><b>Connection</b><br>Console :</html>");
			add(connection, BorderLayout.WEST);

			// creazione display CENTRO
			display1 = new JTextArea(10, 20);
			JScrollPane scroll = new JScrollPane(display1);
			add(scroll, BorderLayout.CENTER);

			// creazione EST
			JPanel pannelloEST = new JPanel();
			pannelloEST.setLayout(new BorderLayout());

			JButton init = new JButton("Init");
			pannelloEST.add(init, BorderLayout.NORTH);
			JButton start = new JButton("Start");
			pannelloEST.add(start, BorderLayout.CENTER);
			JButton destroy = new JButton("Destroy");
			pannelloEST.add(destroy, BorderLayout.SOUTH);

			add(pannelloEST, BorderLayout.EAST);

			// creazione SUD
			ExitButton1 exit = new ExitButton1();
			add(exit, BorderLayout.SOUTH);

			JAMAction initAction = new JAMAction();
			JAMAction startAction = new JAMAction();
			JAMAction destroyAction = new JAMAction();

			init.addActionListener(initAction);
			start.addActionListener(startAction);
			destroy.addActionListener(destroyAction);
		}


	}

	private JTextArea display1;
	private JScrollPane scroll;

	private class JAMAction implements ActionListener{

		public JAMAction(){
		}

		public void actionPerformed(ActionEvent e){

			try{
				JButton source = (JButton)e.getSource();
				if(source.getText().equals("Init")) {initJAMAgent(); source.setEnabled(false);}
				else if(source.getText().equals("Start")) {startJAMAgent(); source.setEnabled(false);}//disattiva il bottone una volta premuto
				else destroyJAMAgent();
			}catch(RemoteException err){System.out.println(err);}
		}
	}
}

class ExitButton1 extends JButton implements ActionListener{
	public ExitButton1(){
		super("Exit");
		addActionListener(this);
	}
	public void actionPerformed(ActionEvent e){
		System.exit(0);
	}
}
