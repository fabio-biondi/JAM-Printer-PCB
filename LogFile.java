import JAM.JAMIOException;
import java.io.*;

public class LogFile {
    private PrintWriter log;

	/**
	* metodo che crea il file .log nel percorso specificato
	* e ci scrive dentro.
	*/
    public void startLog(String fileName, String messaggio, File path) throws JAMIOException {
        try {
            File file = new File(path, fileName + ".log");
            log = new PrintWriter(file);
            log.println(messaggio);
        	log.flush();
        }
        catch(IOException ioexception)
        {
            throw new JAMIOException(ioexception);
        }
    }

	/**
	* metodo che chiude il file .log specificato
	*/
    public void endLog() {
        log.close();
    }

	/**
	* metodo che scrive nel file .log
	*/
    public void log(String messaggio, Object ExtraObject) throws JAMIOException{
        String temp = "";
        temp = temp + messaggio + "\n\n";
        if(ExtraObject != null) temp += ExtraObject.toString() + "\n\n";
        log.println(temp);
        log.flush();
    }

	/**
	* metodo che scrive nel file .log
	*/
    public void log(String messaggio) throws JAMIOException{
        log(messaggio,null);
    }

}
