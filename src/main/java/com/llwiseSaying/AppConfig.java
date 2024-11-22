package com.llwiseSaying;

public class AppConfig {

    private static AppConfig instance;
    private String lastIdFilePath;
    private String dataJsonFilePath;
    private String wiseSayingPath;

    public static AppConfig getInstance(String status) {
        if (instance == null) {
            instance = new AppConfig(status);
        }
        return instance;
    }

    public AppConfig(String status) {
        this.lastIdFilePath = "src/"+status+"/resources/db/wiseSaying/lastId.txt";
        this.dataJsonFilePath = "src/"+status+"/resources/db/wiseSaying/data.json";
        this.wiseSayingPath = "src/"+status+"/resources/db/wiseSaying/";
    }

//    public AppConfig(String test) {
//        this.lastIdFilePath = "src/test/resources/db.wiseSaying/lastId.txt";
//        this.dataJsonFilePath = "src/test/resources/db.wiseSaying/data.json";
//        this.wiseSayingPath = "src/test/resources/db/wiseSaying/";
//    }

    public String getLastIdFilePath() {
        return lastIdFilePath;
    }

    public String getDataJsonFilePath() {
        return dataJsonFilePath;
    }

    public String getWiseSayingPath() {
        return wiseSayingPath;
    }

}
