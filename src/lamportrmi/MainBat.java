package lamportrmi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Cette classe sert à créer des fichiers .bat pour pouvoir lancer automatiquements
 * des serveurs et avoir des clients qui peuvent se connecter aux serveurs
 * 
 * @author Nathan & Jimmy
 */
public class MainBat {
    public static void main (String[] args) {
        final String DELIMITER = " ";
        final String FILE_NAME = "./structure.txt";
        final String ECHO = "@ECHO OFF";
        final String BAT = ".bat";
        final String UTF = "UTF-8";
        
        List<ServerDAO> serverList = new ArrayList<>();

        /**
         * Lecture du fichier structure pour la création des serveurs et clients
         */
        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
            stream.forEach((line) -> {
                String[] temp = line.split(DELIMITER);
                String ip_adress = temp[0];
                int port = Integer.parseInt(temp[1]);
                int id = Integer.parseInt(temp[2]);
                serverList.add(new ServerDAO(ip_adress, port, id));
            });
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        /**
         * Boucle pour la création des fichier .bat pour lancer les serveurs et les
         * clients dans une ligne de commande
         */
        for(int i = 0; i < serverList.size(); ++i) {
            // Création des .bat pour les serveurs
            try (PrintWriter writer = new PrintWriter("./launch_server" +
                                                      i + BAT, UTF)) {
                writer.println(ECHO);
                writer.println("java -jar \"./dist/LamportRMI.jar\" " +
                                serverList.get(i).getIpAdress() + " " +
                                Integer.toString(serverList.get(i).getPort()));
            }catch(FileNotFoundException f) {
                System.out.println(f.toString());
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(MainBat.class.getName())
                      .log(Level.SEVERE, null, ex);
            }
            
            // Création des .bat pour les clients
            try (PrintWriter writer2 = new PrintWriter("./src/lamportrmi/client"
                                                        + i + BAT, UTF)) {
                writer2.println(ECHO);
                writer2.println("javac -cp ../ ClientOfGlobalRMIServer.java");
                writer2.println("javac -cp ../ DistributedServer.java");
                writer2.println("java -cp ../ lamportrmi/ClientOfGlobalRMIServer "
                        + serverList.get(i).getIpAdress() + " "
                        + Integer.toString((serverList.get(i).getPort()
                                            + serverList.size())) + " " + Integer.toString(i));
            }catch(FileNotFoundException f) {
                System.out.println(f.toString());
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(MainBat.class.getName())
                      .log(Level.SEVERE, null, ex);
            }
        }
        
        /**
         * Boucle pour lancer les serveurs automatiquement
         */
        for(int i = 0; i < serverList.size(); ++i) {
            String cmd = "cmd /c start \"\" launch_server" + i + ".bat";
            try {
                Runtime.getRuntime().exec(cmd);
            } catch (IOException ex) {
                Logger.getLogger(MainBat.class.getName())
                      .log(Level.SEVERE, null, ex);
            } 
        }
    }
}
