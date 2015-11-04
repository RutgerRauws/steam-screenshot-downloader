package steam.screenshot.downloader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Rutger Rauws
 */
public class Scraper
{
    public static ArrayList<Integer> getFileIDs(String steamID) throws IOException
    {
        ArrayList<Integer> fileIDs = new ArrayList<Integer>();
        
        Document document = Jsoup.connect("http://steamcommunity.com/id/" + steamID + "/screenshots/?p=1&sort=newestfirst&browsefilter=myfiles&view=grid&privacy=14#scrollTop=0").get();
        
        /*
         * Steam has taken action to download images easily from their servers,
         * so we need to obtain the file ID first and do a seperate request to download the images.
        */
        Elements images = document.select("a.profile_media_item.modalContentLink  ");
        
        for(int fileNumber = 0; fileNumber < images.size(); fileNumber++)        
        {
            String URL = images.eq(fileNumber).attr("href");
            String ID = URL.replaceFirst("http://steamcommunity.com/sharedfiles/filedetails/\\?id=", "");
            
            fileIDs.add(Integer.parseInt(ID));
        }
        
        return fileIDs;
    }
    
    public static ArrayList<URL> getImages(ArrayList<Integer> fileIDs) throws IOException
    {
        ArrayList<URL> URLs = new ArrayList<URL>();
        
        for(Integer fileID : fileIDs)
        {
            Document document = Jsoup.connect("http://steamcommunity.com/sharedfiles/filedetails/?id=" + fileID).get();
            Element anchor = document.select("div.actualmediactn > a").get(0);

            URLs.add(new URL(anchor.attr("href")));
        }
        
        return URLs;
    }
}
