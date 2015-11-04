package steam.screenshot.downloader;

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
class DataRunnable implements Runnable
{    
    private final URL url;
    private final String path;
    private final int fileNumber;
    
    @Override
    public void run()
    {
        try // I wish I could just throw the exception back to Scraper.java
        {
            BufferedImage image = ImageIO.read(url);
            File outputFile = new File(path + "\\" + fileNumber + ".jpg");
            
            ImageIO.write(image, "jpg", outputFile);
        }
        catch (IOException e)
        {
            // What to do..?
        }
    }
    
    public DataRunnable(URL url, String path, int fileNumber)
    {
        this.url = url;
        this.path = path;
        this.fileNumber = fileNumber;
    }
}

public class Data
{
    public static void writeImageFromURL(URL url, String path, int fileNumber)
    {
        DataRunnable runnable = new DataRunnable(url, path, fileNumber);
        new Thread(runnable).start();
    }
    
    public static void writeImagesFromURL(ArrayList<URL> URLs, String path)
    {
        for(int i = 0; i < URLs.size(); i++)
            writeImageFromURL(URLs.get(i), path, i);
    }
}
