/**
* class PrinterPCBBehaviourServizioClienti, classe che si occupa di 
* creare agenti di tipo Sviluppatore
* @author fabio
*/

import JAM.*;

public class PrototypeDeveloperAgent extends JAMAgent {
    public PrototypeDeveloperAgent(String category, String name) throws JAMADSLException {
        super(new PersonalAgentID(category, name));
    }
    public static void main(String[] args) throws JAMADSLException {
        PrototypeDeveloperAgent PrototypeDeveloperAgent = new PrototypeDeveloperAgent("Matteo", "Baldoni");
        PrototypeDeveloperAgent.addBehaviour(new PrototypeDeveloperBehaviour(PrototypeDeveloperAgent));
        PrototypeDeveloperAgent PrototypeDeveloperAgent1 = new PrototypeDeveloperAgent("Mario", "Rossi");
        PrototypeDeveloperAgent1.addBehaviour(new PrototypeDeveloperBehaviourRefuse(PrototypeDeveloperAgent1));
        PrototypeDeveloperAgent PrototypeDeveloperAgent2 = new PrototypeDeveloperAgent("Mercoledi", "Adams");
        PrototypeDeveloperAgent2.addBehaviour(new PrototypeDeveloperBehaviourNotUnderstood(PrototypeDeveloperAgent2));
        PrototypeDeveloperAgent PrototypeDeveloperAgent3 = new PrototypeDeveloperAgent("Fabio", "Biondi");
        PrototypeDeveloperAgent3.addBehaviour(new PrototypeDeveloperBehaviourProposal(PrototypeDeveloperAgent3));
    }
}
