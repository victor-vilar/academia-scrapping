import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GameStatsLinkFinder {

    public static final String url = "https://www.academiadasapostasbrasil.com/";
    private WebClient client = new WebClient();

    GameStatsLinkFinder(){
        this.client.getOptions().setCssEnabled(false);
        this.client.getOptions().setJavaScriptEnabled(false);
    }

    //função utilizar para buscar a pagina com a data de hoje
    private HtmlPage getDefaultPage(){
        try{
            HtmlPage page = client.getPage(url);
            return page;
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public List<String> getAllLinksFromTable(String data){
        List<String> linksToGameStats = null;

        HtmlPage page = this.findPage(data);

        // Retrieve all <tr> elements
        List<?> items = page.getByXPath("//td[@class='stats']")
                .stream()
                .map(o -> ((HtmlTableCell) o))
                .collect(Collectors.toList());

        linksToGameStats = this.extractAllLinks(items);
        return linksToGameStats;
    }

    //pega todos os links das td's encontradas
    private List<String> extractAllLinks(List<?> linksOfAllGames) {

        List<String> strings = linksOfAllGames.stream().map(e -> {
            HtmlTableCell cell = (HtmlTableCell) e;
            HtmlAnchor ancora = (HtmlAnchor) cell.getByXPath("./a").get(0);
            return ancora.getHrefAttribute();
        }).collect(Collectors.toList());
        return strings;
    }

    // a tabela possui um campo em que posso escolher a data das partidas, dessa forma posso fazer uma pesquisa pela data dos eventos
    // essa função encontra a tabela com a data que foi passada
    public HtmlPage findPage(String data){

        //variavel para armazenar a data que esta na pagina atual
        String dataNaPagina = "";

        //se a data for igual a nulo, significa que os jogos que procuro são da data de hoje,
        //então ira retornar a pagina que aparece quando o site é visitado, ou seja, a data do dia em que foi acessado.
        if(data.equalsIgnoreCase("")){
           return this.getDefaultPage();
        }

        //se a data tiver sido preenchida então vou utilizar o metodo para buscar a pagina e então continuar nesse codigo
        HtmlPage page = this.getDefaultPage();

        //equanto a data na pagina for diferente da data que estou buscando ira repetir
        while(!data.equalsIgnoreCase(dataNaPagina)){

            //buscando botao para caso seja necessário clicalo
            HtmlSpan button = (HtmlSpan) page.getByXPath("//span[@class='aa-icon-next date-increment']").get(0);

            //buscando os labels para saber se a data da pagina é igual a data buscada
            List<HtmlLabel> labels = page.getByXPath("//label[@class='nativedatepickerContainer']").stream().map(e -> (HtmlLabel) e).collect(Collectors.toList());

            dataNaPagina = labels.get(0).getAttribute("data-displaydate");

            if(!labels.get(0).getAttribute("data-displaydate").equalsIgnoreCase(data)){
               try{
                   page = null;
                   page = (HtmlPage) button.click();
               }catch(IOException e){
                    System.out.println("Deu erro");
               }
            }
        }

        return page;
    }


}


