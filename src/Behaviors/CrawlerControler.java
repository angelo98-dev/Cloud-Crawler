package Behaviors;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.io.File;

public class CrawlerControler {

    CrawlController controller = null;

    public CrawlerControler(String seed) {

        CrawlConfig configuration = new CrawlConfig();
        File crawlStorage = new File("src/resources/frontiers");

        configuration.setCrawlStorageFolder(crawlStorage.getAbsolutePath());
        configuration.setPolitenessDelay(2000);
        configuration.setMaxDepthOfCrawling(2);
        configuration.setMaxPagesToFetch(1);
        configuration.setResumableCrawling(false);

        // Instant.
        PageFetcher pageFetcher = new PageFetcher(configuration);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        try {
            controller = new CrawlController(configuration, pageFetcher, robotstxtServer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        controller.addSeed(seed);
        configuration.setIncludeBinaryContentInCrawling(true);

    }


    public CrawlController getControler(){

        return controller;

    }


}

