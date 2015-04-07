/**
* class PrinterPCBBehaviourServizioClienti, classe che si occupa di 
* definire il comportamento di un agente Stampante
* @author fabio
*/

import JAM.*;

public class PrinterPCBBehaviourStampa extends JAMSimpleBehaviour {
    public PrinterPCBBehaviourStampa(JAMAgent myAgent) {
        super(myAgent);
    }
    /**
    * Si occupa di inizilizzare il comportamento
    */
    public void setup() throws JAMBehaviourInterruptedException {
        ((PrinterPCBAgent)myAgent).resetTimeOut();
        ((PrinterPCBAgent)myAgent).collegaStampante();
        sleep(1000);
        System.out.println("Stampante operativa:"+((PrinterPCBAgent)myAgent).getReady());
        //((PrinterPCBAgent)myAgent).resetOrdini();
        System.out.println("Inizio orario lavoro");
    }
    /**
    * Si occupa di finalizzare il comportamento
    */
    public void dispose() throws JAMBehaviourInterruptedException { 

    }
    /**
    * Si occupa di eseguire il comportamento
    */
    public void action() throws JAMBehaviourInterruptedException {
        ((PrinterPCBAgent)myAgent).resetOrdini();
        /*((PrinterPCBAgent)myAgent).decrementaTimeOut();
        System.out.println("timeout"+((PrinterPCBAgent)myAgent).getTimeOut());
        sleep(1000);
        if(((PrinterPCBAgent)myAgent).getTimeOut() <= 0)
            done();
        //((PrinterPCBAgent)myAgent).resetOrdini();
        */
    }
}
