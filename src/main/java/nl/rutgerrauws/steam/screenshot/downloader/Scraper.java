package nl.rutgerrauws.steam.screenshot.downloader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Rutger Rauws
 */

public class Scraper extends Task<Void>
{
    private String steamID, path;
    
    public Scraper(String steamID, String path)
    {
        this.steamID = steamID;
        this.path = path;
    }
    
    @Override
    protected Void call() throws Exception
    {
        //TODO: Use an Exception Listener for this
        try
        {
            ArrayList<Integer> fileIDs = getFileIDs();
            ArrayList<URL> URLs = getImages(fileIDs);
            
            updateMessage("Downloading images.");
            Data.writeImagesFromURL(URLs, path);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private ArrayList<Integer> getFileIDs() throws IOException
    {
        ArrayList<Integer> fileIDs = new ArrayList<Integer>();
        
        Document document = Jsoup.connect("http://steamcommunity.com/id/" + steamID + "/screenshots/?p=1&sort=newestfirst&browsefilter=myfiles&view=grid&privacy=14#scrollTop=0").get();
        Elements pages = document.select("div.pagingPageLinks > a.pagingPageLink");
        
        if(pages.size() == 0) //The profile in question is either set to private, has no pictures or only one page of pictures
            return getFileIDs(1);
        
        int lastPageNumber = Integer.parseInt(pages.last().text());
        
        for(int pageNumber = 1; pageNumber <= lastPageNumber; pageNumber++)
        {
            updateMessage("Discovered page " + pageNumber + ".");
            fileIDs.addAll(getFileIDs(pageNumber));
        }
        
        return fileIDs;
    }
    
    private ArrayList<Integer> getFileIDs(int pageNumber) throws IOException
    {
        ArrayList<Integer> fileIDs = new ArrayList<Integer>();
        
        Document document = Jsoup.connect("http://steamcommunity.com/id/" + steamID +
                                          "/screenshots/?p=" + pageNumber +
                                          "&sort=newestfirst&browsefilter=myfiles&view=grid&privacy=14#scrollTop=0"
                                         ).get();
        
        /*
         * Steam has taken action to download images easily from their servers,
         * so we need to obtain the file ID first and do a seperate request to download the images.
        */
        Elements images = document.select("a.profile_media_item.modalContentLink");
        
        for(int fileNumber = 0; fileNumber < images.size(); fileNumber++)        
        {
            updateMessage("Getting file number " + (fileNumber + 1) + " of " + fileIDs.size() + ".");
            String URL = images.eq(fileNumber).attr("href");
            String ID = URL.replaceFirst("http://steamcommunity.com/sharedfiles/filedetails/\\?id=", "");
            
            fileIDs.add(Integer.parseInt(ID));
        }
        
        return fileIDs;
    }
    
    public ArrayList<URL> getImages(ArrayList<Integer> fileIDs) throws IOException
    {
        ArrayList<URL> URLs = new ArrayList<URL>();
        
        for(int fileNumber = 0; fileNumber < fileIDs.size(); fileNumber++)
        {
            updateMessage("Getting image URL " + (fileNumber + 1) + " of " + fileIDs.size() + ".");
            
            Document document = Jsoup.connect(
                                                "http://steamcommunity.com/sharedfiles/filedetails/?id=" +
                                                fileIDs.get(fileNumber)
                                             ).get();
            
            Element anchor = document.select("div.actualmediactn > a").get(0);

            URLs.add(new URL(anchor.attr("href")));
        }
        
        return URLs;
    }
}
