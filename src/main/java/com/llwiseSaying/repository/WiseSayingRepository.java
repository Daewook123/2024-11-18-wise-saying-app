package com.llwiseSaying.repository;

import com.llwiseSaying.model.WiseSaying;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {

    List<WiseSaying> wiseSayingList = new ArrayList<>();

    String lastIdPath = "src/main/resources/db/wiseSaying/lastId.txt";
    String dataJsonPath = "src/main/resources/db/wiseSaying/data.json";

    public int save(String content, String author) throws IOException {
        int id = lastIdReadAndUpdate(true);
        WiseSaying wiseSaying = new WiseSaying(id, content, author);

        wiseSayingList.add(wiseSaying);
        wiseSayingSave(wiseSaying.getId(), content, author);
        return wiseSaying.getId();
    }

    public boolean delete(int id) {

        String filePath = "src/main/resources/db/wiseSaying/" + id + ".json";
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

        int id = lastIdReadAndUpdate(false);

        if(id < 1){
            return false;
        }else{
            BufferedReader reader = null;

            //data.json 작성
            String buildString = "[";
            for (int i = 1; i <= id; i++) {
                String filePath = "src/main/resources/db/wiseSaying/" + i + ".json";
                File file = new File(filePath);
                if (file.exists()) {
                    reader = new BufferedReader(new FileReader(file));
                    if (i == id) {
                        buildString += reader.readLine();
                    } else {
                        buildString += reader.readLine() + ",";
                    }
                } else {
                    continue;
                }
            }
            buildString += "]";

            try (FileWriter filewriter = new FileWriter(dataJsonPath)) {
                filewriter.write(buildString);
            }
        }
        return true;
    }


public String[] findByWiseSaying(int id) throws IOException {
    String filePath = "src/main/resources/db/wiseSaying/" + id + ".json";

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

public List<String> findByAll() throws IOException {

    int id = lastIdReadAndUpdate(false);
    List<String> list = new ArrayList<>();
    BufferedReader reader = null;

    if (id > 0) {
        for (int i = id; i > 0; i--) {
            String filePath = "src/main/resources/db/wiseSaying/" + i + ".json";
            File file = new File(filePath);
            if (file.exists()) {
                reader = new BufferedReader(new FileReader(file));
                String jsonstring = reader.readLine().replace("{", "").replace("\"", "").replace("}", "");
                String[] arr = jsonstring.split(":|,");
                list.add(arr[1] + " / " + arr[3] + " / " + arr[5]);
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

    String filePath = "src/main/resources/db/wiseSaying/" + id + ".json";

    String jsonString = String.format("{\"id\":\"%d\",\"content\":\"%s\",\"author\":\"%s\"}", id, content, author);

    try (FileWriter filewriter = new FileWriter(filePath)) {
        filewriter.write(jsonString);
    }
}
}






