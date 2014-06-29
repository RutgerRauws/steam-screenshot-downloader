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
public class Data
{
    public static void writeImageFromUrl(String URI, String path, int fileNumber) throws IOException
    {
        URL url = new URL(URI);
        BufferedImage image = ImageIO.read(url);
        File outputFile = new File(path + "/" +fileNumber + ".jpg");
        
        ImageIO.write(image, "jpg", outputFile);
    }
}
