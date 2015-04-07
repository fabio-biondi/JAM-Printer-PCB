/**
* Classe PersonalAgentID: implementa l'interfaccia AgentID per ereditariet�
* dalla classe GenericAgentID che estende.
* Classe utilizzata per identificare un agente appartenente a una determinata categoria.
* @author fabio
*/
package JAM;
public class PersonalAgentID extends CategoryAgentID{

	private String name="";
	/**
	* Costruttore della classe PersonalAgentID che identifica nome e categoria dell'agente.
	* @param nome nome dell'agente.
	* @param category category dell'agente.
	*/
	public PersonalAgentID(String nome, String categoria){
		super(categoria); // cos� evito di dover dichiarare private category ="";
		this.name = nome;
	}

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
	* Confronta un oggetto della classe che implementa l'interfaccia AgentID con un oggetto di tipo AgentID.
	* @param agentID parametro da confrontare
	* @return true se il parametro ha lo stesso nome e appartiene alla stessa categoria dell'oggetto su cui e invocato il metodo, false altrimenti
	*/
	public boolean equals(Object agentID)
	{
		if (agentID == null) return false;
		PersonalAgentID agent;
		try
		{
			agent = (PersonalAgentID)agentID;
			if(((agent.name).equals(this.name)) && ((agent.getCategory()).equals(this.getCategory())))  return true;
			// uso getCategory() perch� category in CategoryAgentID � privato eaccedo tramite un metodo al suo valore
			else return false;
		}
		catch(ClassCastException e)
		{
			return super.equals(agentID);
		}

	}
}
