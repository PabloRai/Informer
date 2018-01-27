

class Main {
    static void main(String[] args) {
        Set<String> tickers = TickerUtils.getTickers("http://www.ravaonline.com/v2/precios/panel.php?m=GEN")
        tickers.addAll(TickerUtils.getTickers("http://www.ravaonline.com/v2/precios/panel.php?m=LID"))
        tickers = tickers.sort()
        Map<String, Double> mathematicalHope = new HashMap<>()
        Map<String, List<String>> readers = TickerUtils.getHistoricalData(tickers)


        List<String> lines

        while (readers.size() != tickers.size()) {
            println("Readers size: ${readers.size()}")
            println("Tickers size: ${tickers.size()}")
            println("Some tickers where not fetched, retrying..")
            Set<String> fallbackTickers = tickers.clone() as Set<String>
            fallbackTickers.removeAll(readers.keySet())
            println("The tickers are: $fallbackTickers")
            readers.putAll(TickerUtils.getHistoricalData(fallbackTickers))
        }



        readers.each { ticker, reader ->
            lines = reader
            lines.removeAll(TickerUtils.nonData)
            mathematicalHope.put(ticker, TickerUtils.getPercentage(lines))
        }
        mathematicalHope = mathematicalHope.sort {a,b ->
            a.value < b.value ? 1 : -1
        }
        mathematicalHope.each {
            println(it)
        }







    }



}

