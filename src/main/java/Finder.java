import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.List;
import java.util.stream.Collectors;

public class Finder {

    public static final String url = "https://www.academiadasapostasbrasil.com/";
    private WebClient client = new WebClient();

    Finder(){
        this.client.getOptions().setCssEnabled(false);
        this.client.getOptions().setJavaScriptEnabled(false);
    }

    public List<String> getAllLinksFromTable(){
        List<String> linksToGameStats = null;
        try {
            //send the request
            HtmlPage page = client.getPage(url);
            // Retrieve all <tr> elements
            List<?> items = page.getByXPath("//td[@class='stats']")
                    .stream()
                    .map(o -> ((HtmlTableCell) o))
                    .collect(Collectors.toList());

            linksToGameStats = this.extractAllLinks(items);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return linksToGameStats;
    }

    private List<String> extractAllLinks(List<?> linksOfAllGames) {

        List<String> strings = linksOfAllGames.stream().map(e -> {
            HtmlTableCell cell = (HtmlTableCell) e;
            HtmlAnchor ancora = (HtmlAnchor) cell.getByXPath("./a").get(0);
            return ancora.getHrefAttribute();
        }).collect(Collectors.toList());
        return strings;
    }
}


