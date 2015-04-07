/**
* class PrinterPCBBehaviourServizioClienti, classe che si occupa di 
* definire il comportamento di un agente Stampante
* @author fabio
*/

import JAM.*;
import java.io.*;
import java.util.*;

public class PrinterPCBBehaviourServizioClienti extends JAMWhileBehaviour {
    private LogFile log;
    //int timeOut;
    public PrinterPCBBehaviourServizioClienti(JAMAgent myAgent) {
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
            log.log("Stampante operativa:"+((PrinterPCBAgent)myAgent).getReady());
        } catch(JAMIOException err) {
            System.out.println("Errore: "+ err);
            done();
        }
    }
    /**
    * Si occupa di finalizzare il comportamento
    */
    public void dispose() throws JAMBehaviourInterruptedException {
        try {
            log.log("Totale ordini in carico:"+((PrinterPCBAgent)myAgent).getNumOrdine());
            log.log("END- Eseguita dispose.");
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
        try {
            log.log("1- Start del comportamento, attesa messaggio iniziale.");
            Message message = myAgent.receive();
            log.log("2- Ricevuto messaggio iniziale, inizio sua analisi");
            if(message.getPerformative() != Performative.REQUEST) {
                log.log("3- Performativa diversa da REQUEST, preparo REFUSE.", message);
                Message request = new Message(
                    myAgent.getMyID(), 
                    message.getSender(), 
                    Performative.REFUSE, 
                    "Performativa non accettata", 
                    message
                );
                log.log("4- Invio REFUSE", request);
                myAgent.send(request);
                log.log("5- Inviata REFUSE", request);
            } else if(!message.getContent().equals("Puoi stampare uno schema elettrico su circuito PCB?")) {
                log.log("6- Non capisco il content, preparo NOT_UDERSTOOD.", message);
                Message not_understood = new Message(
                    myAgent.getMyID(), 
                    message.getSender(), 
                    Performative.NOT_UNDERSTOOD, 
                    "REQUEST non compresa", 
                    message
                );
                log.log("7- Invio NOT_UNDERSTOOD.", not_understood);
                myAgent.send(not_understood);
                log.log("8- Invita NOT_UNDERSTOOD.", not_understood);
            } else {
                log.log("9- REQUEST accettabile, preparo AGREE.", message);
                AgentID agentid = message.getSender();
                Message agree = new Message(
                    myAgent.getMyID(), 
                    agentid, 
                    Performative.AGREE, 
                    "REQUEST accettata", 
                    message
                );
                log.log("10- Invio AGREE.", agree);
                myAgent.send(agree);
                log.log("11- Inviata AGREE.", agree);
                log.log("12- Mi metto in attesa conferma da " + agentid + ".");
                sleep(1000);
                log.log("13- Mi risveglio e verifico se arrivata conferfa da " + agentid + ".");
                //Al secondo messaggio ci sono due possibili risposte inform o proposal
                if(!myAgent.isThereMessage(agentid, Performative.INFORM) && !myAgent.isThereMessage(agentid, Performative.PROPOSAL)) {
                    log.log("14- Nessuna conferma da " + agentid + ".");
                } 
                else if(myAgent.isThereMessage(agentid, Performative.INFORM)){
                    Message confirm = myAgent.receive(agentid, Performative.INFORM);
                    log.log("15- Arrivata conferma da " + agentid + ".", confirm);
                    if(confirm.getContent().equals("Stampa il prototipo preesistente XYZ.")) {
                        log.log("16- Preparo messaggio con conferma presa in carico ordine per " + agentid + ".");
                        Message inform = new Message(
                            myAgent.getMyID(), 
                            agentid, 
                            Performative.INFORM, 
                            "INVIO STAMPA. numero ordine: "+ ((PrinterPCBAgent)myAgent).getNumOrdine()
                        );
                        log.log("17- Invio conferma presa in carico ordine a " + agentid + ".", inform);
                        myAgent.send(inform);
                        log.log("19- Inviata conferma presa in carico ordine a " + agentid + ".", inform);
                    }
                }
                else if(myAgent.isThereMessage(agentid, Performative.PROPOSAL)){
                    Message confirm = myAgent.receive(agentid, Performative.PROPOSAL);
                    log.log("15- Arrivata conferma da " + agentid + ".", confirm);
                    if(confirm.getContent().equals("Stampa schema elettrico di un prototipo sviluppato da me.")) {
                        log.log("16- Preparo messaggio con conferma presa in carico ordine per " + agentid + ".");
                        Message inform = new Message(
                            myAgent.getMyID(), 
                            agentid, 
                            Performative.INFORM, 
                            "ATTESA DI SCHEMA DA STAMPARE. numero ordine: "+ ((PrinterPCBAgent)myAgent).getNumOrdine()
                        );
                        log.log("17- Invio conferma presa in carico ordine a " + agentid + ".", inform);
                        myAgent.send(inform);
                        log.log("19- Inviata conferma presa in carico ordine a " + agentid + ".", inform);
                    }
                }//fine if ora chiudo                
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
