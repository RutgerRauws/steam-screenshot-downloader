package steam.screenshot.downloader;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Rutger Rauws
 */
public class Scraper
{
    public static void downloadScreenshots(String steamID, String path) throws IOException
    {
        Document document = Jsoup.connect("http://steamcommunity.com/id/" + steamID + "/screenshots/?p=1&sort=newestfirst&browsefilter=myfiles&view=grid&privacy=14#scrollTop=0").get();
        Elements images = document.select(".imgWallItem img");
        
        for(int fileNumber = 0; fileNumber < images.size(); fileNumber++)        
        {
            String URI = images.eq(fileNumber).attr("src").replaceFirst("\\d+x\\d+\\.resizedimage", "");
            Data.writeImageFromUrl(URI, path, fileNumber);
        }
    }
}
