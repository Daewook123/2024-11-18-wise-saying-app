package com.llwiseSaying.conrtoller;

import com.llwiseSaying.sevice.WiseSayingService;

import java.io.IOException;
import java.util.*;

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
        switch (command.substring(0,2)){
            case "등록": registerCommand();       break;
            case "목록": searchCommand(command);  break;
            case "삭제": deleteCommand();         break;
            case "수정": updateCommand();         break;
            case "빌드": buildCommand();          break;
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

    public void searchCommand(String command) throws IOException {
        List<String[]> list = wiseSayingService.searchAllWiseSaying();


        if(command.equals("목록")) {
            if (list.isEmpty()) {
                System.out.println("명언이 존재하지 않습니다.");
                return;
            } else {
                pagingPrintWiseSaying(1, list);
            }
        }else {
            Map<String, String> params = new HashMap<>();
            String query = command.split("\\?")[1];

            for (String param : query.split("&")) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
            if(params.size()==1){
                pagingPrintWiseSaying(Integer.parseInt(params.get("page")),list);
            }else if(params.size()==2){
                pagingPrintWiseSaying(1,
                        filterWiseSayings(list, params.get("keywordType"), params.get("keyword")));
            }
            else if(params.size()==3){
                pagingPrintWiseSaying(Integer.parseInt(params.get("page")),
                        filterWiseSayings(list, params.get("keywordType"), params.get("keyword")));
            }
        }

    }

    private void pagingPrintWiseSaying(int page, List<String[]> list){
        int totalPages = (list.size()%5==0) ? list.size()/5 : list.size()/5+1;

        if(totalPages<page){
            System.out.println("해당 페이지는 존재하지 않습니다.");
        }else{
            int index = page*5;
            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------------");

            for(int i =index-5; i<index; i++){
                System.out.println(list.get(i)[0] + " / " + list.get(i)[1] + " / " + list.get(i)[2]);
                if(list.size()==i+1) break;
            }
        }

        if(page < totalPages){
            System.out.printf("페이지 : [%d] / %d\n", page, page+1);
        }else{
            System.out.printf("페이지 : %d / [%d]\n", page-1, totalPages);
        }
    }

    private List<String[]> filterWiseSayings(List<String[]> list, String keywordType, String keyword) {
        List<String[]> resultlist = new ArrayList<>();

        for (String[] text : list) {
            if ((keywordType.equals("author") && text[1].contains(keyword))||
                    (keywordType.equals("content") && text[2].contains(keyword))) {
                resultlist.add(new String[]{text[0], text[1], text[2]});
            }
        }
        return resultlist;
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
