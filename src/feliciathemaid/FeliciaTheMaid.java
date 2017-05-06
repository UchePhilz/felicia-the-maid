/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feliciathemaid;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Uche
 */
public class FeliciaTheMaid extends Application {

    Thread th = new Thread();
    final Map<String, Integer> map = new HashMap();
    String images = ".jpg,.png,.jpeg,.gif,.bmp,webp,exif,.rif,.tiff".toLowerCase();
    String videos = ".3g2,.3gp,.avi,.flv,.m4v,.mov,.mp4,.mpg,.rm,.srt,swf,vob,.wmv,.divx,.mkv,mpeg,.ogv,.3gp2,"
            + ".amv,.camrec".toLowerCase();
    String audio = ".mp3,.wav,.aiff,.arf,.cda,.zab3,.wpl,.avr,.midi,.amr,.wv,.aip,.ocdf,.alb,.cdfs,.sib,.gp3,"
            + ".zvr,.m4a,audionote,.sng,.ytif,.wma".toLowerCase();
    File rootFile = new File("");
    List<File> fileArray = new ArrayList();
    final TextArea ta = new TextArea("Felicia The Maid - (Mount Olives)");
    final Button btn = new Button();

    public FeliciaTheMaid() throws URISyntaxException {
        //.substring(0, f.getParent().lastIndexOf("\\"))
        URI uri = new URI(FeliciaTheMaid.class.getResource("").getFile());
        File f = new File(uri);
        this.rootFile = new File(f.getParent().substring(0, f.getParent().lastIndexOf("\\")));
        //this.rootFile = new File("C:\\Users\\Uche\\Music");
        if (rootFile.listFiles() != null) {
            this.fileArray = Arrays.asList(rootFile.listFiles());
        } else {
            this.fileArray = new ArrayList();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        btn.setText("Begin");
        btn.setDisable(true);
        ta.setEditable(false);
        ta.setWrapText(true);
        initFelicia();

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loop();
            }
        });

        VBox root = new VBox();
        root.getChildren().addAll(btn, ta);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Felicia The Maid " + rootFile.getName() + " (Mount Olives)");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void loop() {
        Runnable r = new Runnable() {

            @Override
            public void run() {

                for (String key : map.keySet()) {
                    if (map.get(key) > 1) {
                        File extDir = new File(rootFile.getPath() + "\\" + key.replace(".", "").toUpperCase() + " - Felicia");
                        extDir.mkdirs();

                        ta.appendText("\nFelicia>> I am going to create a folder for " + key + " files");
                        ta.appendText("\nFelicia>> I am not transfering " + key + " files");
                        if (extDir.isDirectory()) {
                            if (extDir.exists()) {
                                arrangeFiles(key, extDir);
                            } else {
                                arrangeFiles(key, extDir);
                            }
                        } else {
                            p("messed up!");
                        }

                    }

                }
                ta.appendText("\nFelicia>> I done with this folder");

                btn.setDisable(true);
            }
        };

        th = new Thread(r);
        th.setDaemon(true);
        th.start();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void initFelicia() {

        Runnable r = new Runnable() {

            @Override
            public void run() {
                ta.appendText("\nFelicia>> I am in " + rootFile.getName());
                if (!fileArray.isEmpty()) {
                    for (File file : fileArray) {
                        if (file.isFile()) {
                            if (file.getName().contains(".") && !file.getName().equals("FeliciaTheMaid")) {
                                String ext = file.getName().substring(file.getName().lastIndexOf("."));
                                if (!map.containsKey(ext)) {

                                    map.put(ext, 1);
                                } else {

                                    map.put(ext, map.get(ext) + 1);

                                }
                            } else {
                                p("no Idea");
                            }

                        }
                    }
                    boolean disableBegin = true;
                    for (String key : map.keySet()) {
                        if (map.get(key) == 1) {
                            ta.appendText("\nFelicia >> There is " + map.get(key) + " " + key.toUpperCase() + " file");
                        } else {
                            disableBegin = false;
                            ta.appendText("\nFelicia >> There are " + map.get(key) + " " + key.toUpperCase() + " files");
                        }

                    }

                    ta.appendText("\nFelicia>> Only files that are more that 2 will be moved.");
                    btn.setDisable(disableBegin);
                } else {
                    ta.appendText("\nFelicia>> This folder is empty");
                    ta.appendText("\nFelicia>> There is nothing to do.");
                    btn.setDisable(true);
                }
            }
        };

        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    private void arrangeFiles(String ext, File extDir) {
        for (File file : fileArray) {
            if (!file.isDirectory()) {
                if (file.getName().contains(".")) {
                    if (file.getName().substring(file.getName().toLowerCase().lastIndexOf(".")).equals(ext)) {
                        pack(file, extDir);
                    }
                } else {
                    p("what is this? " + file.getName());
                }

            } else {
                // p("Is not a dir");
            }
        }
    }

    private void pack(File file, File extDir) {
        try {
            // ta.appendText("Felicia>> I am moving " + file.getName() + " to " + extDir.getName());
        } catch (NullPointerException nullBomb) {
            nullBomb.printStackTrace();
        }
        try {
            Files.move(file.toPath(), extDir.toPath().resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {

            try {
                Files.move(file.toPath(), extDir.toPath().resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex1) {
                ta.appendText("\n" + ex1);
            }
        }

    }

    private void p(String s) {
        System.out.println(s);
    }
}
