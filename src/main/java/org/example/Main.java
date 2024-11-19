package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        List<Writer> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("==명언 앱==");

        while (true) {
            System.out.print("명령)");
            String command = sc.nextLine();

            switch (command){
                case "등록":
                    list.add(create(sc));
                    System.out.println(list.size()+"번 명언이 등록되었습니다.");
                    break;
                case "목록":
                    read(list);
                    break;
                case "삭제":
                    System.out.print("(첫 인덱스는 1 입니다)?id= ");
                    delete(list, sc);
                    break;
                case "수정":
                    System.out.print("(첫 인덱스는 1 입니다)?id= ");
                    update(list, sc);
                    break;
                case "종료":
                    return;
                default:
                    continue;
            }

        }
    }

    public static Writer create(Scanner sc) {

        Writer wt = new Writer();

        System.out.print("명언 : ");
        String text = sc.nextLine();
        wt.setSaying(text);

        System.out.print("작가 : ");
        String writer = sc.nextLine();
        wt.setWriter(writer);

        return wt;
    }

    public static void read(List<Writer> list){
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for(int i=list.size()-1; i>=0; i--){
            System.out.println(i+1+" / "+list.get(i).getWriter()+" / "+list.get(i).getSaying());
        }
    }

    public static void delete(List<Writer> list, Scanner sc){

        int index = sc.nextInt(); // Integer.ParseInt(sc.nextLine()); <- 사용시 다음 행 X
        sc.nextLine(); // enter 삭제

        try {
            list.remove(index-1);
            System.out.println(index+"번 명언이 삭제되었습니다.");
        }catch (Exception e) {
            System.out.println(index+"번 명언은 존재하지 않습니다.");
        }
    }

    public static void update(List<Writer> list, Scanner sc){

        int index = sc.nextInt(); // Integer.ParseInt(sc.nextLine()); <- 사용시 다음 행 X
        sc.nextLine(); // enter 삭제

        System.out.println("명언(기존) : "+list.get(index-1).getSaying());
        System.out.print("명언 : ");
        String text = sc.nextLine();
        list.get(index-1).setSaying(text);

        System.out.println("작가(기존) : "+list.get(index-1).getWriter());
        System.out.print("작가 : ");
        String writer = sc.nextLine();
        list.get(index-1).setWriter(writer);

    }
    
}
