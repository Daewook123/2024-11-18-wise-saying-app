package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String path = "src/main/java/org/example/db/wiseSaying";

        System.out.println("==명언 앱==");

        while (true) {
            System.out.print("명령)");
            String command = sc.nextLine();

            switch (command){
                case "등록":
                    create(sc, path);
                    break;
                case "목록":
                    read(path);
                    break;
                case "삭제":
                    System.out.print("(첫 인덱스는 1 입니다)?id= ");
                    delete(sc, path);
                    break;
                case "수정":
                    System.out.print("(첫 인덱스는 1 입니다)?id= ");
                    update(sc, path);
                    break;
                case "종료":
                    sc.close();
                    return;
                default:
                    continue;
            }

        }
    }

    public static void create(Scanner sc, String path) {

        System.out.print("명언 : ");
        String text = sc.nextLine();

        System.out.print("작가 : ");
        String writer = sc.nextLine();

        String lastIdPath = path + "/lastId.txt";
        int count = lastIdFileRead(lastIdPath);

        String writerFilePath = path + "/" + count + ".json";
        writerFileCreate(writerFilePath, count, text, writer);

        System.out.println(count + "번 명언이 등록되었습니다.");

    }

    public static int lastIdFileRead(String lastIdPath) {
        try {

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
            int count = Integer.parseInt(reader.readLine()) + 1;

            try (FileWriter filewriter = new FileWriter(file)) {
                filewriter.write(String.valueOf(count));
            }

            return count;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public static void writerFileCreate(String writerFilePath, int count, String text, String writer) {
        try {

            File file = new File(writerFilePath);

            file.getParentFile().mkdirs();
            file.createNewFile();

            try (FileWriter filewriter = new FileWriter(file)) {
                String jsonString = String.format("{\"id\":\"%d\",\"content\":\"%s\",\"author\":\"%s\"}", count, text, writer);
                filewriter.write(jsonString);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void read(String path){

        try {
            String lastIdPath = path + "/lastId.txt";

            File lastIdfile = new File(lastIdPath);
            BufferedReader reader = null;

            if(!lastIdfile.exists()) {
                System.out.println("명언이 존재하지 않습니다.");
                return;
            }

            reader = new BufferedReader(new FileReader(lastIdfile));
            int count = Integer.parseInt(reader.readLine());

            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------------");

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

    public static void delete(Scanner sc, String path){

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


    public static void update(Scanner sc, String path){

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
                String text = sc.nextLine();

                System.out.println("작가(기존) : "+ arr[5]);
                System.out.print("작가 : ");
                String writer = sc.nextLine();

                writerFileCreate(writerFilePath, index, text, writer);
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}


    

