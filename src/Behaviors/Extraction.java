package Behaviors;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.http.Header;

import java.io.File;
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
        return urlString.startsWith("https://www.googleapis.com/discovery/v1/apis");

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



        writeFile(page);


        Header[] responseHeaders = page.getFetchResponseHeaders();
        if (responseHeaders != null) {
            logger.debug("Response headers:");
            for (Header header : responseHeaders) {
                logger.debug("\t{}: {}", header.getName(), header.getValue());
            }
        }

        logger.debug("=============");
    }


    public void writeFile(Page page){

        try {

            Files.write(Paths.get(new File("ServicesCollected").getAbsolutePath()),page.getContentData());
            WebCrawler.logger.info("Stored:{} ",page.getWebURL().getURL());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
