 /**
* class PrinterPCBAgent, classe che si occupa di creare agenti Stampanti
* @author fabio
*/

import JAM.*;

public class PrinterPCBAgent extends JAMAgent {
    private int ordini;
    private int timeOut;
    private boolean ready = false;
    public PrinterPCBAgent(String s) throws JAMADSLException {
        super(new PersonalAgentID(s, "Printer PCB"));
        addBehaviour(new PrinterPCBBehaviourStampa(this));
        addBehaviour(new PrinterPCBBehaviourServizioClienti(this));
    }
    //rendo operativa la Stampante
    public void collegaStampante() {
        //boolean ready = false;
        System.out.println("sto collegando la stampante...");
        
        ready = true;
        if(ready)
            System.out.println(">>> Stampante operativa <<<");
    }
    public boolean getReady() {
        return ready;
    }
    public void resetOrdini() {
        ordini = 0;
    }
    public int getNumOrdine() {
        ordini++;
        return ordini;
    }
    
    public void resetTimeOut() {
        timeOut = 20;
    }
    public void decrementaTimeOut() {
        timeOut--;
    }
    public int getTimeOut() {
        return timeOut;
    }
    
    public static void main(String args[])  throws JAMADSLException  {
        PrinterPCBAgent PrinterPCBAgent = new PrinterPCBAgent("Stampante_Laser");
        PrinterPCBAgent PrinterPCBAgent1 = new PrinterPCBAgent("Stampante_CO2");
    }
}
