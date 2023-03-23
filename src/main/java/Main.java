import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

    Finder finder = new Finder();
    List<String> links = finder.getAllLinksFromTable();
    links.stream().forEach(e -> System.out.println(e));

    }
}
