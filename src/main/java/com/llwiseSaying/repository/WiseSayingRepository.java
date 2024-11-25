package com.llwiseSaying.repository;


import com.llwiseSaying.AppConfig;
import com.llwiseSaying.model.WiseSaying;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {

    List<WiseSaying> wiseSayingList = new ArrayList<>();
    AppConfig appConfig = AppConfig.getInstance("main");

    String lastIdPath;
    String dataJsonPath;
    String wiseSayingPath;

    public WiseSayingRepository(){
        this.lastIdPath = appConfig.getLastIdFilePath();
        this.dataJsonPath = appConfig.getDataJsonFilePath();
        this.wiseSayingPath = appConfig.getWiseSayingPath();
    }

    public int save(String content, String author) throws IOException {
        int id = lastIdReadAndUpdate(true);
        WiseSaying wiseSaying = new WiseSaying(id, content, author);

        wiseSayingList.add(wiseSaying);
        wiseSayingSave(wiseSaying.getId(), content, author);
        return wiseSaying.getId();
    }

    public boolean delete(int id) {

        String filePath = wiseSayingPath + id + ".json";
        boolean deleted = false;
        File file = new File(filePath);

        if (file.exists()) {
            deleted = file.delete();
        }

        return deleted;
    }

    public boolean update(int id, String content, String author) throws IOException {
        wiseSayingSave(id, content, author);
        return true;
    }

    public boolean build() throws IOException {

        List<String[]> list = findByAll();

        if(list.isEmpty()){
            return false;
        }else{
            //data.json 작성
            String jsonString = "[";
            for (int i= 0; i<list.size(); i++) {
                jsonString += "{\"id\": \""+ list.get(i)[0]+ "\", \"content\": \"" + list.get(i)[1] + "\",\"author\": \"" + list.get(i)[2] + "\"}, ";
                if(i == list.size()-1){
                    jsonString += "{\"id\": \""+ list.get(i)[0]+ "\", \"content\": \"" + list.get(i)[1] + "\",\"author\": \"" + list.get(i)[2] + "\"}";
                }
            }
            jsonString += "]";

            try (FileWriter filewriter = new FileWriter(dataJsonPath)) {
                filewriter.write(jsonString);
            }

            return true;
        }
    }


public String[] findByWiseSaying(int id) throws IOException {
    String filePath = wiseSayingPath + id + ".json";

    File file = new File(filePath);
    BufferedReader reader = null;

    if (!file.exists()) {
        return null;
    } else {
        reader = new BufferedReader(new FileReader(file));
        String list = reader.readLine().replace("{", "").replace("\"", "").replace("}", "");
        String[] arr = list.split(":|,");
        return new String[]{arr[3], arr[5]};
    }
}

public List<String[]> findByAll() throws IOException {

    int id = lastIdReadAndUpdate(false);
    List<String[]> list = new ArrayList<>();
    BufferedReader reader = null;

    if (id > 0) {
        for (int i = id; i > 0; i--) {
            String filePath = wiseSayingPath + i + ".json";
            File file = new File(filePath);
            if (file.exists()) {
                reader = new BufferedReader(new FileReader(file));
                String jsonstring = reader.readLine().replace("{", "").replace("\"", "").replace("}", "");
                String[] arr = jsonstring.split(":|,");
                list.add(new String[]{arr[1], arr[3], arr[5]});

            } else {
                continue;
            }
        }
    }
    return list;
}

public void lastIdAndDataFileCheck() throws IOException {

    File lastIdfile = new File(lastIdPath);
    File dataJsonFile = new File(dataJsonPath);

    if (!lastIdfile.exists()) {
        lastIdfile.getParentFile().mkdirs();
        lastIdfile.createNewFile();

        try (FileWriter filewriter = new FileWriter(lastIdfile)) {
            filewriter.write("0");
        }
    }

    if (!dataJsonFile.exists()) {
        dataJsonFile.getParentFile().mkdirs();
        dataJsonFile.createNewFile();

        try (FileWriter filewriter = new FileWriter(dataJsonFile)) {
            filewriter.write("");
        }
    }

}

public int lastIdReadAndUpdate(boolean Y_N) throws IOException {

    BufferedReader reader = null;
    reader = new BufferedReader(new FileReader(lastIdPath));
    int id = Integer.parseInt(reader.readLine());

    if (Y_N) {
        try (FileWriter filewriter = new FileWriter(lastIdPath)) {
            id++;
            filewriter.write(String.valueOf(id));
        }
    }

    return id;
}

public void wiseSayingSave(int id, String content, String author) throws IOException {

    String filePath = wiseSayingPath + id + ".json";

    String jsonString = String.format("{\"id\":\"%d\",\"content\":\"%s\",\"author\":\"%s\"}", id, content, author);

    try (FileWriter filewriter = new FileWriter(filePath)) {
        filewriter.write(jsonString);
    }
}
}







