import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        GameStatsLinkFinder finder = new GameStatsLinkFinder();
        List<String> links = finder.getAllLinksFromTable("28/03/2023");


    }
}
