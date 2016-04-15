/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package steam.screenshot.downloader.fxml;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import steam.screenshot.downloader.Scraper;

/**
 *
 * @author Rutger Rauws
 */
public class FXMLDocumentController implements Initializable
{
    private double xWindowOffset;
    private double yWindowOffset;
        
    @FXML private TextField txt_path;
    @FXML private TextField txt_steamid;
    @FXML private Button btn_start;
    @FXML private Button btn_browse;
    
    @FXML private BorderPane titleBar;
    @FXML private Button btn_close;
    
    private Thread parserThread;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        btn_start.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent ae)
            {
                btn_start.setDisable(true);
                
                Scraper sc = new Scraper(txt_steamid.getText(), txt_path.getText());
                parserThread = new Thread(sc);
                parserThread.start();
            }
        });
        
        btn_browse.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t)
            {
                DirectoryChooser dc = new DirectoryChooser();
                File path = dc.showDialog(btn_browse.getScene().getWindow());
                
                if(path != null) //A selection has been made by the user
                {
                    txt_path.setText(path.getPath());
                }
            }
        });
        
        
        /*
         * 
         * Window manager
         * 
         */
        titleBar.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent me)
            {
                Stage stage = (Stage)titleBar.getScene().getWindow();
                
                xWindowOffset = stage.getX() - me.getScreenX();
                yWindowOffset = stage.getY() - me.getScreenY();
            }
        });
        
        titleBar.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent me)
            {
                Stage stage = (Stage)titleBar.getScene().getWindow();
                
                stage.setX(me.getScreenX() + xWindowOffset);
                stage.setY(me.getScreenY() + yWindowOffset);
            }
        });
        
        btn_close.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent ae)
            {
                if(parserThread != null) //If the thread has been initialized
                    parserThread.interrupt(); //We want to interrupt it
                
                Platform.exit(); //Exit the application
            }
        });
    }
}
