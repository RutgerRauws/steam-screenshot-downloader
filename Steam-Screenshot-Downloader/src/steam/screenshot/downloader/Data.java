package steam.screenshot.downloader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Rutger Rauws
 */
class DataRunnable implements Runnable
{    
    private final String URI;
    private final String path;
    private final int fileNumber;
    
    @Override
    public void run()
    {
        try // I wish I could just throw the exception back to Scraper.java
        {
            URL url = new URL(URI);
            BufferedImage image = ImageIO.read(url);
            File outputFile = new File(path + "\\" +fileNumber + ".jpg");
            
            ImageIO.write(image, "jpg", outputFile);
        }
        catch (IOException e)
        {
            // What to do..?
        }
    }
    
    public DataRunnable(String URI, String path, int fileNumber)
    {
        this.URI = URI;
        this.path = path;
        this.fileNumber = fileNumber;
    }
}

public class Data
{
    public static void writeImageFromUrl(String URI, String path, int fileNumber)
    {
        DataRunnable runnable = new DataRunnable(URI, path, fileNumber);
        new Thread(runnable).start();
    }
}
