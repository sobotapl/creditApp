package pl.ps.creditapp.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.core.model.ProcessedCreditApplication;

import java.io.*;

public class FileManager {
    private static final Logger log = LoggerFactory.getLogger(FileManager.class);

    public void write(ProcessedCreditApplication creditApplication){
       String path = Constants.OUTPUT_PATH+creditApplication.getApplication().getId()+".dat";
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))){
            out.writeObject(creditApplication);
            log.info("Application with id {} successfully saved.", creditApplication.getApplication().getId());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ProcessedCreditApplication read(String id){
        String path = Constants.OUTPUT_PATH+ id + "dat";
        File file = new File(path);
        if(file.exists()){
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
                return (ProcessedCreditApplication)in.readObject();

            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalStateException(e.getMessage());
            }
        }else{
            log.error("Application with id {} not found.", id);
            throw new IllegalStateException(String.format("Application with id %s not found.", id));
        }

    }
}
