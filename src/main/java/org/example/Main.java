package org.example;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}

class App {

    Scanner sc = new Scanner(System.in);
    String path = "src/main/java/org/example/db/wiseSaying";

    public void run() {

        System.out.println("==명언 앱==");

        while (true) {
            System.out.print("명령)");
            String command = sc.nextLine();

            switch (command){
                case "등록":
                    create();
                    break;
                case "목록":
                    read();
                    break;
                case "삭제":
                    System.out.print("(첫 인덱스는 1 입니다)?id= ");
                    delete();
                    break;
                case "수정":
                    System.out.print("(첫 인덱스는 1 입니다)?id= ");
                    update();
                    break;
                case "빌드":
                    build();
                    break;
                case "종료":
                    sc.close();
                    return;
                default:
                    continue;
            }
        }
    }
    public void create() {

        System.out.print("명언 : ");
        String content = sc.nextLine();

        System.out.print("작가 : ");
        String author = sc.nextLine();


        int count = lastIdFileRead(true);
        writerFileCreate(count, content, author);

        System.out.println(count + "번 명언이 등록되었습니다.");

    }

    public int lastIdFileRead(Boolean createBool) {
        try {

            String lastIdPath = path + "/lastId.txt";

            File file = new File(lastIdPath);
            BufferedReader reader = null;

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();

                try (FileWriter filewriter = new FileWriter(file)) {
                    filewriter.write("0");
                }
                file = new File(lastIdPath);
            }

            reader = new BufferedReader(new FileReader(file));
            int count = Integer.parseInt(reader.readLine());

            if(createBool){
                try (FileWriter filewriter = new FileWriter(file)) {
                    count += 1;
                    filewriter.write(String.valueOf(count));
                    return count;
                }
            }else{
                return count;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public void writerFileCreate(int count, String content, String author) {
        try {

            String writerFilePath = path + "/" + count + ".json";
            File file = new File(writerFilePath);

            file.getParentFile().mkdirs();
            file.createNewFile();

            try (FileWriter filewriter = new FileWriter(file)) {
                String jsonString = String.format("{\"id\":\"%d\",\"content\":\"%s\",\"author\":\"%s\"}", count, content, author);
                filewriter.write(jsonString);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void read(){

        try {

            int count = lastIdFileRead(false);
            BufferedReader reader = null;

            if(count > 0){
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");
            }else{
                System.out.println("명언이 존재하지 않습니다.");
                return;
            }

            for(int i = 1; i<=count; i++){
                String writerFilePath = path + "/" + i + ".json";
                File writerFile = new File(writerFilePath);
                if(!writerFile.exists()){
                    continue;
                }else {
                    reader = new BufferedReader(new FileReader(writerFile));
                    String list = reader.readLine().replace("{", "").replace("\"", "").replace("}", "");
                    String[] arr = list.split(":|,");
                    System.out.println(arr[1] + " / " + arr[3] + " / " + arr[5]);
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void delete(){

        int index = sc.nextInt(); // Integer.ParseInt(sc.nextLine()); <- 사용시 다음 행 X
        sc.nextLine(); // enter 삭제

        String writerFilePath = path + "/" + index + ".json";

        File file = new File(writerFilePath);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println(index+"번 명언이 삭제되었습니다.");
            } else {
                System.out.println(index+"번 명언 삭제를 실패하였습니다:");
            }
        } else {
            System.out.println(index + "번 명언은 존재하지 않습니다.");
        }
    }


    public void update(){

        try {
            int index = sc.nextInt(); // Integer.ParseInt(sc.nextLine()); <- 사용시 다음 행 X
            sc.nextLine(); // enter 삭제

            String writerFilePath = path + "/" + index + ".json";

            File writerFile = new File(writerFilePath);
            BufferedReader reader = null;

            if (!writerFile.exists()) {
                System.out.println(index + "번 명언은 존재하지 않습니다.");
            }else{
                reader = new BufferedReader(new FileReader(writerFile));
                String list = reader.readLine().replace("{", "").replace("\"", "").replace("}", "");
                String[] arr = list.split(":|,");

                System.out.println("명언(기존) : "+ arr[3]);
                System.out.print("명언 : ");
                String content = sc.nextLine();

                System.out.println("작가(기존) : "+ arr[5]);
                System.out.print("작가 : ");
                String author = sc.nextLine();

                writerFileCreate(index, content, author);
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void build() {
        try {

            int count = lastIdFileRead(false);
            if (count<1){
                System.out.println("명언이 존재하지 않습니다.");
                return;
            }

            String dataJsonPath= path+"/"+"data.json";

            File dataJsonFile = new File(dataJsonPath);
            BufferedReader reader = null;

            if (!dataJsonFile.exists()) {
                dataJsonFile.getParentFile().mkdirs();
                dataJsonFile.createNewFile();

                try (FileWriter filewriter = new FileWriter(dataJsonFile)) {
                    filewriter.write("");
                }
                dataJsonFile = new File(dataJsonPath);
            }

            //data.json 작성
            String buildString = "[";
            for(int i = 1; i<=count; i++){
                String writerFilePath = path + "/" + i + ".json";
                File writerFile = new File(writerFilePath);
                if(!writerFile.exists()){
                    continue;
                }else {
                    reader = new BufferedReader(new FileReader(writerFile));
                    if(i == count){
                        buildString += reader.readLine();
                    }else{
                        buildString += reader.readLine()+ ",";
                    }
                }
            }
            buildString += "]";

            reader = new BufferedReader(new FileReader(dataJsonFile));
            try (FileWriter filewriter = new FileWriter(dataJsonFile)) {
                filewriter.write(buildString);
                System.out.println("data.json 파일의 내용이 갱신되었습니다.");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}


    

