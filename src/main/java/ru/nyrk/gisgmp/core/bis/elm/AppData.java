package ru.nyrk.gisgmp.core.bis.elm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("AppData")
public class AppData {
    @XStreamAlias("ImportData")
    private ImportData importData;

//    @XStreamAlias("ImportDataResponse")
//    private ImportDataResponse importDataResponse;
//
//    public AppData() {
//        importDataResponse = new ImportDataResponse();
//    }

    public ImportData getImportData() {
        return importData;
    }

//    public ImportDataResponse getImportDataResponse() {
//        return importDataResponse;
//    }
}
