/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package steam.screenshot.downloader.fxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
        
    @FXML private Button btn_start;
    @FXML private Button btn_close;
    
    @FXML private TextField txt_path;
    @FXML private TextField txt_steamid;
    @FXML private BorderPane titleBar;
    
    private Thread parserThread;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        btn_start.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent me)
            {
                Scraper sc = new Scraper(txt_steamid.getText(), txt_path.getText());
                parserThread = new Thread(sc);
                parserThread.start();
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
        
        btn_close.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent me)
            {
                if(parserThread != null) //If the thread has been initialized
                    parserThread.interrupt(); //We want to interrupt it
                
                Platform.exit(); //Exit the application
            }
        });
    }
}
