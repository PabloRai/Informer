import groovy.transform.Synchronized
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

import groovyx.gpars.GParsExecutorsPoolEnhancer

class TickerUtils {
    static String[] nonData = ["", "fecha,apertura,maximo,minimo,cierre,volumen,openint"]

    @Synchronized
    static HashMap<String, List<String>> getHistoricalData(Set<String> tickers) {
        Map<String, List<String>> result = new HashMap<>(79)
        InputStream input = null
        GParsExecutorsPoolEnhancer.enhanceInstance(tickers)
        tickers.eachParallel {
            input = new URL("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=${it}&csv=1").openStream()
            try {
                result.put(it, input.readLines())

            } catch (Exception ignored) {
                println("Failed to fetch $it")
            }

        }
        return result

    }

    static HashSet<String> getTickers(String uri) {
        Document document = Jsoup.connect(uri).get()
        Element table = document.select("table").get(4)
        ArrayList nodes = table.select("tr").childNodes
        nodes = nodes.subList(9, nodes.size() - 1)
        return nodes.collect {
            it[1].text()
        }
    }

    static double getPercentage( List<String> lines, int times = 100, int holdingTime = 60) {
        try {
            if(lines. size() > 2000) {
                lines = lines.subList(1000, lines.size() - 1)
            }
            double result = 0
            String[] line
            double initialValue = 0
            double finalValue
            double percent
            Random random = new Random()
            int myRandomNumber = 0
            for (int i = 0; i < times; i++) {
                while (initialValue == 0) {
                    myRandomNumber = random.nextInt(Math.abs(lines.size() - 1 - holdingTime))
                    line = lines[myRandomNumber].replace("\"", "")?.split(",")
                    initialValue = line[1] as double
                }

                line = lines[myRandomNumber + holdingTime].replace("\"", "")?.split(",")
                finalValue = line[1] as double
                percent = ((finalValue - initialValue) / initialValue) * 100
                result += percent

                initialValue = 0
            }
            result /= times
            return result
        } catch (Exception ignored) {
            return 0
        }
    }
}
