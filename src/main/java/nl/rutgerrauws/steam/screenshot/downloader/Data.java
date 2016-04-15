package nl.rutgerrauws.steam.screenshot.downloader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Rutger Rauws
 */

public class Data
{
    public static void writeImageFromURL(URL url, String path, int fileNumber) throws IOException
    {
        BufferedImage image = ImageIO.read(url);
        File outputFile = new File(path + "/" + fileNumber + ".jpg");
            
        ImageIO.write(image, "jpg", outputFile);
    }
    
    public static void writeImagesFromURL(ArrayList<URL> URLs, String path) throws IOException
    {
        for(int i = 0; i < URLs.size(); i++)
        {
            writeImageFromURL(URLs.get(i), path, i);
            
            if(Thread.interrupted())
                return;
        }
    }
}
