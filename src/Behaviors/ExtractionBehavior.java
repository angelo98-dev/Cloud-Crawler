package Behaviors;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import org.slf4j.ILoggerFactory;

public class ExtractionBehavior extends TickerBehaviour {


    public ExtractionBehavior(Agent a, long period) {
        super(a, period);
    }

    @Override
    protected void onTick() {

        System.out.println("Start Extraction ... ");
        // Number of threads to use during crawling. Increasing this typically makes crawling faster. But crawling
        int numberOfCrawlers = 1;
        //CrawlerControler cc = new CrawlerControler("https://www.googleapis.com/discovery/v1/apis");
        CrawlerControler cc = new CrawlerControler("https://www.googleapis.com/discovery/v1/apis");

        CrawlController controller = cc.getControler();

        CrawlController.WebCrawlerFactory<Extraction> factory = () -> new Extraction();
        controller.start(factory, numberOfCrawlers);

    }

}
