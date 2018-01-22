class Ticker {
    String tickerId
    double lastValue
    double dayPerent
    double previousValue
    double openValue
    double minimumValue
    double maxValue
    double nominalVolume
    double effectiveVolume

    static  String[] props = Ticker.declaredFields.findAll {
        (!it.toString().contains("static") && !it.toString().contains("transient") ) ? it.toString() : null
    }.collect {it.toString().substring(it.toString().lastIndexOf('.')+1)}
    Ticker( data) {
        ArrayList<String> cleanData = data.collect {
           if(it.text().trim() != "" && !it.text().trim().contains(":")) {
               return it.text()
           }
        }
        cleanData.removeAll([null])
        for (int i = 0; i < cleanData.size() ; i++) {
            cleanData[i] = cleanData[i].replace(".", "")
            cleanData[i] = cleanData[i].replace(",", ".")
        }
        for (int i = 0; i < cleanData.size(); i++) {
            if(!cleanData[i].isNumber()) {
                this."${props[i]}" = cleanData[i]
            } else {
                this."${props[i]}" = cleanData[i] as double
            }
        }

    }
}



