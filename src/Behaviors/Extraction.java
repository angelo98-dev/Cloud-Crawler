package Behaviors;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import jade.util.leap.Set;
import org.apache.http.Header;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class Extraction extends WebCrawler {

    //On a pas besoin de crawler les fichiers, css,js,jpg,mp3 etc.
    //On defini un patter pour exclure ces fichiers

    private final static Pattern EXCLUSION  =
            Pattern.compile(".*(\\\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {

        String urlString = url.getURL().toLowerCase();
        // Ignore the url if it has an extension that matches our defined set - EXCLUSION.
        if(EXCLUSION.matcher(urlString).matches()){
            return true;
        }
        // Only accept the url if it is in the "www........." domain and protocol is "http".
        //return urlString.startsWith("https://www.googleapis.com/discovery/v1/apis");
        return  true;

    }

    @Override
    public void visit(Page page) {

        int docid = page.getWebURL().getDocid();
        String url = page.getWebURL().getURL();
        String domain = page.getWebURL().getDomain();
        String path = page.getWebURL().getPath();
        String subDomain = page.getWebURL().getSubDomain();
        String parentUrl = page.getWebURL().getParentUrl();

        logger.debug("Docid: {}", docid);
        logger.info("URL: {}", url);
        logger.debug("Domain: '{}'", domain);
        logger.debug("Sub-domain: '{}'", subDomain);
        logger.debug("Path: '{}'", path);
        logger.debug("Parent page: {}", parentUrl);

        String name[] = url.split(":");
        String nameFile_1 = name[1];

        String name_1=nameFile_1.replace('/','_');
        String nameFile=name_1.replace('.','_');
        writeFile(page,nameFile);

        Header[] responseHeaders = page.getFetchResponseHeaders();
        if (responseHeaders != null) {
            logger.debug("Response headers:");
            for (Header header : responseHeaders) {
                logger.debug("\t{}: {}", header.getName(), header.getValue());
            }
        }
        logger.debug("=============");
    }


    public void writeFile(Page page,String name){

        try {

            Files.write(Paths.get(new File("src/serviceDepot/"+name+".json").getAbsolutePath()),page.getContentData());
            WebCrawler.logger.info("Stored:{} ",page.getWebURL().getURL());

            read();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void read(){

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src/serviceDepot/__www_googleapis_com_discovery_v1_apis.json")) {

            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject)obj;
            JSONArray serviceList=  (JSONArray) jsonObject.get("items"); //Gives Main JSoN
            System.out.println(serviceList);

            //Iterate over service array
            serviceList.forEach(service -> parseServiceFile((JSONObject) service));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private static void parseServiceFile(JSONObject service)
    {
        //Get service discoveryRestUrl List
        String discoveryRestUrl = (String) service.get("discoveryRestUrl");

        int numberOfCrawlers = 1;
        CrawlerControler cc = new CrawlerControler(discoveryRestUrl);
        CrawlController controller = cc.getControler();

        CrawlController.WebCrawlerFactory<Extraction> factory = () -> new Extraction();
        controller.start(factory, numberOfCrawlers);

    }


}