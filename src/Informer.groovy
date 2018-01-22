import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Document

class Main {
    static void main(String[] args) {
        Set<Ticker> tickers = getTickers("http://www.ravaonline.com/v2/precios/panel.php?m=GEN")
        tickers.addAll(getTickers("http://www.ravaonline.com/v2/precios/panel.php?m=LID"))
        println(tickers)
    }

    private static List<Ticker> getTickers(String uri) {
        Document document = Jsoup.connect(uri).get()
        Element table = document.select("table").get(4)
        ArrayList nodes = table.select("tr").childNodes
        nodes = nodes.subList(9, nodes.size() - 1)
        return nodes.collect {
            new Ticker(it)
        }

    }

}

