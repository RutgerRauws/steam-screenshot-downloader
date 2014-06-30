/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package steam.screenshot.downloader.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import steam.screenshot.downloader.Scraper;

/**
 *
 * @author Rutger Rauws
 */
public class FXMLDocumentController implements Initializable
{
    @FXML private Button btn_start;
    @FXML private TextField txtfield_path;
    @FXML private TextField txtfield_steamid;
       
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        btn_start.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent me)
            {
                try
                {
                    Scraper.downloadScreenshots(txtfield_steamid.getText(), txtfield_path.getText());
                }
                catch (IOException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        });
    }
}
