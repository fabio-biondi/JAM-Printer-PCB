/**
* Classe GenericAgentID: implementa l'interfaccia AgentID.
* Classe utilizzata per identificare un agente generico
* @author fabio
*/
package JAM;
public class GenericAgentID implements AgentID{

	private String name="";
	private String category="";

	/**
	* Restituisce il nome dell'agente.
	* @return il nome dell'agente.
	*/
	public String getName(){
		return name;
	}

	/**
	* Restituisce la categoria a cui appartiene l'agente.
	* @return la categoria dell'agente.
	*/
	public String getCategory(){
		return category;
	}

	/**
	* Metodo per il confronto di un oggetto della classe che implementa
	* l'interfaccia AgentID con un oggetto di tipo AgentID.
	* @param agentID l'oggetto da confrontare.
	* @return true se il nome e la categoria sono uguali false altrimenti.
	*/
	public boolean equals(Object agentID){
		if(agentID==null) return false;
		GenericAgentID agent;
		try{
			agent = (GenericAgentID) agentID;
			return true;
		}catch(ClassCastException e){
			System.out.println("Errore --> Cast errato");
			return false;
		}
	}

	/**
	* Metodo che restituisce l'oggetto di tipo String che rappresenta l'oggetto stesso.
	* @return la stringa che rappresenta l'oggetto.
	*/
	public String toString(){
		return("(" + getName() + "," + getCategory() + ")");
	}
}

