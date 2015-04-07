/**
* class PrinterPCBBehaviourServizioClienti, classe che si occupa di 
* definire il comportamento di un agente Sviluppatore
* @author fabio
*/

import JAM.*;
import java.io.*;
import java.util.*;

public class PrototypeDeveloperBehaviourNotUnderstood extends JAMWhileBehaviour {
    private LogFile log;
    public PrototypeDeveloperBehaviourNotUnderstood(JAMAgent myAgent) {
        super(myAgent);
        log = new LogFile();
    }
    /**
    * Si occupa di inizilizzare il comportamento
    */
    public void setup() throws JAMBehaviourInterruptedException {
        try {
            File pathLog = new File("./Log");
            log.startLog(myAgent.getMyID().toString().trim(), "Log file per " + myAgent.getMyID(), pathLog);
            log.log("0- Inizializzazione comportamento.");
        } catch(JAMIOException err) {
            System.out.println("Errore: " + err);
            done();
        }
    }
    /**
    * Si occupa di finalizzare il comportamento
    */
    public void dispose() throws JAMBehaviourInterruptedException {
        try {
            log.log("END- Esecuguita dispose.");
            log.endLog();
        } catch(JAMIOException err) {
            System.out.println("Errore: " + err);
            done();
        }
    }
    /**
    * Si occupa di eseguire il comportamento
    */
    public void action() throws JAMBehaviourInterruptedException {
        try  {
            log.log("1- Start del comportamento, attesa messaggio iniziale.");
            CategoryAgentID timeProviderCategoryID = new CategoryAgentID("Printer PCB");
            log.log("2- Preparo REQUEST del prototipo.");
            Message request = new Message(
                myAgent.getMyID(), 
                timeProviderCategoryID, 
                Performative.REQUEST, 
                "Vendete piastre PCB vergini per stampa casalinga?" //content errato
            );
            log.log("3- Invio REQUEST.", request);
            myAgent.send(request);
            log.log("4- Invita REQUEST.", request);
            log.log("5- Attendo risposta.");
            Message answer = myAgent.receive(timeProviderCategoryID);
            log.log("6- Trovata risposta.", answer);
            if(answer.getPerformative() != Performative.AGREE) {
                log.log("7- La risposta non e' una agree eseguo una done.", answer);
                done();
            } else {
                log.log("8- La risposta e' una agree, prepara la conferma.", answer);
                AgentID selectedProvider = answer.getSender();
                Message confirm = new Message(
                    myAgent.getMyID(),
                    selectedProvider, 
                    Performative.INFORM, 
                    "Stampa il prototipo preesistente XYZ."
                );
                log.log("9- Invio la conferma.", confirm);
                myAgent.send(confirm);
                log.log("10- Inviata la conferma.", confirm);
                log.log("11- Attendo il messaggio con conferma presa in carico ordine da " + selectedProvider);
                sleep(2000);
                log.log("12- Controllo se arrivato il messaggio con conferma presa in carico ordine da " + selectedProvider);
                if(myAgent.isThereMessage(selectedProvider, Performative.INFORM)) {
                    log.log("13- Leggo il messaggio con conferma presa in carico ordine da " + selectedProvider);
                    Message inform = myAgent.receive(selectedProvider, Performative.INFORM);
                    log.log("14- Letto il messaggio con conferma presa in carico ordine da " + selectedProvider, inform);
                    System.out.println(inform);
                } else {
                    log.log("15- Nessun messaggio da " + selectedProvider);
                    System.out.println("Nessuna risposta, rinuncio.");
                }
                done();
            }
        } catch(JAMADSLException err1) {
            System.out.println("Errore: " + err1);
            done();
        } catch(JAMIOException err2) {
            System.out.println("Errore: " + err2);
            done();
        }
    }
}