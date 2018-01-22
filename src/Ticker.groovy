class Ticker {
    String tickerId
    String lastValue
    String dayPerent
    String previousValue
    String openValue
    String minimumValue
    String maxValue
    String nominalVolume
    String effectiveVolume

    static  String[] props = Ticker.declaredFields.findAll {
        (!it.toString().contains("static") && !it.toString().contains("transient") ) ? it.toString() : null
    }.collect {it.toString().substring(32)}
    Ticker( data) {
        ArrayList<String> cleanData = data.collect {
           if(it.text().trim() != "" && !it.text().trim().contains(":")) {
               return it.text().replaceAll(",", ".")
           }
        }
        cleanData.removeAll([null])
        for (int i = 0; i < cleanData.size(); i++) {
            this."${props[i]}" = cleanData[i]
        }

    }
}



