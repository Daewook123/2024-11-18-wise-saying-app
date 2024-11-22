package com.llwiseSaying.conrtoller;

import com.llwiseSaying.sevice.WiseSayingService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class WiseSayingController {

    private final WiseSayingService wiseSayingService;
    private Scanner sc;

    public WiseSayingController() {
        wiseSayingService = new WiseSayingService();
        this.sc = new Scanner(System.in);
    }

    public WiseSayingController(Scanner sc) {
        wiseSayingService = new WiseSayingService();
        this.sc = sc;
    }

    public void run() throws IOException {

        wiseSayingService.initializeWiseSaying();
        System.out.println("==명언 앱==");

        while (true) {
            System.out.print("명령)");
            String command = sc.nextLine();

            if(command.equals("종료")){
                sc.close();
                return;
            }
            handleCommand(command);
        }
    }
    public void handleCommand(String command) throws IOException {
        switch (command){
            case "등록": registerCommand();  break;
            case "목록": searchCommand();    break;
            case "삭제": deleteCommand();    break;
            case "수정": updateCommand();    break;
            case "빌드": buildCommand();     break;
        }
    }

    public void registerCommand() throws IOException {

        System.out.print("명언 : ");
        String content = sc.nextLine();

        System.out.print("작가 : ");
        String author = sc.nextLine();

        int id = wiseSayingService.registerWiseSaying(content,author);
        System.out.println(id + "번 명언이 등록되었습니다.");

    }
    public void searchCommand() throws IOException {

        List<String[]> list = wiseSayingService.searchAllWiseSaying();

        if (!list.isEmpty()) {
            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------------");

            for(String[] text : list){
                System.out.println(text[0] + " / " + text[1] + " / " + text[2]);
            }
        } else {
            System.out.println("명언이 존재하지 않습니다.");
        }

    }

    public void deleteCommand(){
        System.out.print("몇 번 명언을 삭제하시겠습니까? : ");
        int id = Integer.parseInt(sc.nextLine());

        boolean status= wiseSayingService.deleteWiseSaying(id);

        if(status) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        }else{
            System.out.println(id+"번 명언 삭제를 실패하였습니다.");
        }

    }
    public void updateCommand() throws IOException {
        System.out.print("몇 번 명언을 수정하시겠습니까? : ");
        int id = Integer.parseInt(sc.nextLine());

        String[] arr = wiseSayingService.searchWiseSaying(id);
        if(arr == null){
            System.out.println("해당 명언이 존재하지 않습니다.");
        }else {
            System.out.println("명언(기존) : " + arr[0]);
            System.out.print("명언 : ");
            String content = sc.nextLine();

            System.out.println("작가(기존) : " + arr[1]);
            System.out.print("작가 : ");
            String author = sc.nextLine();

            boolean status = wiseSayingService.updateWiseSaying(id, content, author);

            if (status) {
                System.out.println(id + "번 명언이 수정되었습니다.");
            } else {
                System.out.println(id + "번 명언 수정을 실패하였습니다:");
            }
        }
    }

    public void buildCommand() throws IOException {
        boolean status = wiseSayingService.buildWiseSaying();

        if (status) {
            System.out.println("data.json 파일이 갱신되었습니다.");
        } else {
            System.out.println("data.json 파일의 갱신을 실패하였습니다.");
        }
    }
}
