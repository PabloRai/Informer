import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Document

class Main {
    static void main(String[] args) {
        Document document = Jsoup.connect("http://www.ravaonline.com/v2/precios/panel.php?m=GEN").get()
        Element table = document.select("table").get(4)
        ArrayList nodes = table.select("tr").childNodes
        nodes = nodes.subList(9, nodes.size()-1)
        ArrayList<Ticker> tickers = nodes.collect {
                new Ticker (it)
            }
        
    }

}

