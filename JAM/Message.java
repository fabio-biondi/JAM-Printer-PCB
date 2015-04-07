/**
* Classe Message, definisce caratteristiche e metodi di un messaggio.
* @author fabio
*/
package JAM;
import java.io.Serializable;

public class Message implements Serializable{

	private AgentID sender;
	private AgentID receiver;
	private Performative performative;
	private String content;
	private Object extraArgument;

  	/**
	Costruttore della classe Message con tutti i parametri.
	* @param sender identifica il mittente del messaggio.
	* @param receiver indica il destinatario del messaggio.
	* @param performative specifica la performativa utilizzata.
	* @param content memorizza il contenuto del messaggio.
	* @param extraArgument memorizza altre informazioni in forma di oggetto.
	*/
	public Message(AgentID sender, AgentID receiver, Performative performative, String content, Object extraArgument){
		this.sender = sender;
		this.receiver = receiver;
		this.performative = performative;
		this.content = content;
		this.extraArgument = extraArgument;
	}

	/**
	Costruttore della classe Message con tutti i parametri tranne extraArgument.
	* @param sender identifica il mittente del messaggio.
	* @param receiver indica il destinatario del messaggio.
	* @param performative specifica la performativa utilizzata.
	* @param content memorizza il contenuto del messaggio.
	* @param extraArgument memorizza altre informazioni in forma di oggetto.
	*/
	public Message(AgentID sender, AgentID receiver, Performative performative, String content){
		this.sender = sender;
		this.receiver = receiver;
		this.performative = performative;
		this.content = content;
		this.extraArgument = null;
	}

	/**
	* Costruttore della classe Message senza parametri.
	*/
	public Message(){
		performative=Performative.UNKNOWN;
		content="---";
	}

	/**
	* Metodo che restituisce l'oggetto di tipo AgentID rappresentante il mittente del messaggio.
	* @return oggetto di tipo AgentID.
	*/
	public AgentID getSender(){
		return sender;
	}

	/**
	* Metodo che modifica il campo sender con il valore contenuto nel parametro sender.
	* @param sender indica il mittente del messaggio.
	*/
	public void setSender(AgentID sender){
		this.sender = sender;
	}

	/**
	* Metodo che restituisce l'oggetto di tipo AgentID rappresentante il destinatario del messaggio.
	* @return oggetto di tipo AgentID
	*/
	public AgentID getReceiver(){
		return receiver;
	}

	/**
	* Metodo che modifica il campo receiver con il valore contenuto nel parametro receiver.
	* @param receiver indica il destinatario del messaggio.
	*/
	public void setReceiver(AgentID receiver){
		this.receiver = receiver;
	}

	/**
	* Metodo che restituisce la performativa utilizzata.
	* @return oggetto di tipo Performative.
	*/
	public Performative getPerformative(){
		return performative;
	}

	/**
	* Metodo che modifica il campo performative con il valore nel parametro performative.
	* @param performative performativa utilizzata.
	*/
	public void setPerformative(Performative performative){
		this.performative = performative;
	}

	/**
	* Metodo che restituisce il contenuto del messaggio.
	* @return oggetto di tipo String che descrive il messaggio.
	*/
	public String getContent(){
		return content;
	}

	/**
	* Metodo che modifica il campo content con il valore contenuto nel parametro content.
	* @param content memorizza il contenuto del messaggio.
	*/
	public void setContent(String content){
		this.content = content;
	}

	/**
	* Metodo che restituisce l'oggetto di tipo Object dove sono memorizzate
	* informazioni extra sull'oggetto.
	* @return oggetto di tipo Object.
	*/
	public Object getExtraArgument(){
		return extraArgument;
	}

	/**
	* Metodo che modifica il campo extraArgument con il valore contenuto nel
	* parametro extraContent.
	* @param extraContent memorizza altre informazioni in forma di oggetto.
	*/
	public void setExtraArgument(Object extraArgument){
		this.extraArgument = extraArgument;
	}

	/**
	* Metodo che restituisce una stringa che rappresenta
	* l'oggetto di tipo Message in formato stampabile su console.
	* @return stringa di tipo Message.
	*/
	public String toString(){
		return("\nPerformativa: " + performative + "\n" + "Sender: " + sender + "\n" +"Receiver: " + receiver +
		"\n" +"Content: \n" + content + "\n" +"ExtraArgument:\n" + extraArgument);
	}

	public Message clone(){
	  	return new Message(sender, receiver, performative, content, extraArgument);
  	}
}
