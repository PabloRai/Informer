import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Document
import static groovyx.gpars.GParsPool.withPool

class Main {
    static void main(String[] args) {
        Set<String> tickers = getTickers("http://www.ravaonline.com/v2/precios/panel.php?m=GEN")
        tickers.addAll(getTickers("http://www.ravaonline.com/v2/precios/panel.php?m=LID"))
        tickers = tickers.sort()
        InputStream input = null
        Set<Reader> readers = new HashSet<>(75)
        Map<String, Reader> mReader = new HashMap<>(75)
        withPool(32) {
            tickers.collectParallel {
                input = new URL("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=${it}&csv=1").openStream()
                mReader.put(it, new InputStreamReader(input, "UTF-8"))
            }
        }
        println(mReader)



    }

    private static HashSet<String> getTickers(String uri) {
        Document document = Jsoup.connect(uri).get()
        Element table = document.select("table").get(4)
        ArrayList nodes = table.select("tr").childNodes
        nodes = nodes.subList(9, nodes.size() - 1)
        return nodes.collect {
            it[1].text()
        }

    }

}

