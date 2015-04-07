/**
* Interfaccia AgentID: definisce diversi metodi per ricavare informazioni su Agenti.
* @author fabio
*/
package JAM;
import java.io.Serializable;

public interface AgentID extends Serializable{

	/**
	* Confronta un oggetto della classe che implementa l�interfaccia AgentID con un oggetto di tipo AgentID.
	* @param agentID oggetto da confrontare
	* @return true se il parametro � uguale all'oggetto su cui � invocato il metodo, false altrimenti
	*/
	public boolean equals(Object agentID);

	/**
	* Restituisce il valore del campo name di tipo String che rappresenta il nome dell�agente.
	* @return una stringa con il valore del campo name che rappresenta il nome dell'agente
	*/
	public String getName();

	/**
	* Restituisce il valore del campo category di tipo String che rappresenta la categoria dell�agente.
	* @return una stringa con il valore del campo category che rappresenta la categoria dell'agente
	*/
	public String getCategory();

	/**
	* Restituisce un oggetto di tipo String che rappresenta l'oggetto stesso.
	* @return una stringa che rappresenta l'oggetto
	*/
	public String toString();

}
