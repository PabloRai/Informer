

class Main {
    static void main(String[] args) {
        Set<String> tickers = TickerUtils.getTickers("http://www.ravaonline.com/v2/precios/panel.php?m=GEN")
        tickers.addAll(TickerUtils.getTickers("http://www.ravaonline.com/v2/precios/panel.php?m=LID"))
        tickers = tickers.sort()
        Map<String, Double> mathematicalHope = new HashMap<>()
        Map<String, List<String>> readers = TickerUtils.getHistoricalData(tickers)


        List<String> lines

        if(readers.size() == tickers.size()) {
            readers.each { ticker, reader ->
                lines = reader
                lines.removeAll(TickerUtils.nonData)
                mathematicalHope.put(ticker, TickerUtils.getPorcentage(lines))
            }
        } else {
            println("Algo salio mal")
        }
        println(mathematicalHope)
        println("Done")







    }



}

