/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package steam.screenshot.downloader.fxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author Rutger Rauws
 */
public class FXMLDocumentController implements Initializable
{
    @FXML
    private Button btn_start;
    
    @FXML
    private void handleStartAction(ActionEvent event)
    {
        System.out.println("You clicked me!");
        btn_start.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }
}
