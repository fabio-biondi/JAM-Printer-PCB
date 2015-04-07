/**
* Classe CategoryAgentID: implementa l'interfaccia AgentID per ereditariet�
* dalla classe GenericAgentID che estende.
* Classe utilizzata per identificare un agente appartenente a una determinata categoria.
* @author fabio
*/
package JAM;
public class CategoryAgentID extends GenericAgentID{

	protected String category="";

	/**
	* Costruttore della classe CategoryAgentID che identifica tutti gli agenti per categoria.
	* @param categoria categoria dell'agente.
	*/
	public CategoryAgentID(String categoria){
		this.category = categoria;
	}

	/**
	* Restituisce la categoria a cui appartiene l'agente.
	* @return la categoria dell'agente.
	*/
	public String getCategory(){
		return category;
	}

	/**
	* Confronta un oggetto della classe che implementa l'interfaccia AgentID con un oggetto di tipo AgentID.
	* @param agentID parametro da confrontare
	* @return true se il parametro ha lo stesso nome e appartiene alla stessa categoria dell'oggetto su cui � invocato il metodo, false altrimenti
	*/
	public boolean equals(Object agentID)
	{
		if (agentID == null) return false;
		CategoryAgentID agent;
		try
		{
			agent = (CategoryAgentID)agentID;
			if(this.category.equals(agent.category))
				return true;
			else return false;
		}
		catch(ClassCastException e)
		{
			return super.equals(agentID);
		}

	}
}
