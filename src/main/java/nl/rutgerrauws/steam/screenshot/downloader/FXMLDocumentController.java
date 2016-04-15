package nl.rutgerrauws.steam.screenshot.downloader;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

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
    @FXML private Button startButton;
    @FXML private Button browseButton;
    
    @FXML private BorderPane titleBar;
    @FXML private Button closeButton;
    
    @FXML private Label statusLabel;
    
    private Thread parserThread;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        startButton.setOnAction(startHandler);
        browseButton.setOnMousePressed(browseHandler);
        
        /*
         * Window manager
         */
        titleBar.setOnMousePressed(titleBarClickedHandler);
        titleBar.setOnMouseDragged(titleBarDraggedHandler);
        closeButton.setOnAction(closeButtonHandler);
    }
    
    
    EventHandler<ActionEvent> startHandler = new EventHandler<ActionEvent>()
    {
        @Override
        public void handle(ActionEvent ae)
        {
            startButton.setDisable(true);

            Scraper sc = new Scraper(txt_steamid.getText(), txt_path.getText());
            parserThread = new Thread(sc);
            parserThread.start();
        }
    };
    
    EventHandler<MouseEvent> browseHandler = new EventHandler<MouseEvent>()
    {
        @Override
        public void handle(MouseEvent t)
        {
            DirectoryChooser dc = new DirectoryChooser();
            File path = dc.showDialog(browseButton.getScene().getWindow());

            if(path != null) //A selection has been made by the user
            {
                txt_path.setText(path.getPath());
            }
        }
    };
    
    
    /*
     * 
     * Window manager
     * 
     */
    EventHandler<MouseEvent> titleBarClickedHandler = new EventHandler<MouseEvent>()
    {
        @Override
        public void handle(MouseEvent me)
        {
            Stage stage = (Stage)titleBar.getScene().getWindow();

            xWindowOffset = stage.getX() - me.getScreenX();
            yWindowOffset = stage.getY() - me.getScreenY();
        }
    };
    
    EventHandler<MouseEvent> titleBarDraggedHandler = new EventHandler<MouseEvent>()
    {
        @Override
        public void handle(MouseEvent me)
        {
            Stage stage = (Stage)titleBar.getScene().getWindow();

            stage.setX(me.getScreenX() + xWindowOffset);
            stage.setY(me.getScreenY() + yWindowOffset);
        }
    };
    
    EventHandler<ActionEvent> closeButtonHandler = new EventHandler<ActionEvent>()
    {
        @Override
        public void handle(ActionEvent ae)
        {
            if(parserThread != null) //If the thread has been initialized
                parserThread.interrupt(); //We want to interrupt it

            Platform.exit(); //Exit the application
        }
    };
}
