package com.llwiseSaying;

import com.llwiseSaying.conrtoller.WiseSayingController;
import com.llwiseSaying.sevice.WiseSayingService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

class WiseSayingControllerTest {


    @BeforeEach
    public void setUp() {
        AppConfig appConfig = AppConfig.getInstance("test");
    }

    @DisplayName("등록 테스트")
    @Test
    void registerTest() throws IOException {

        // given

        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        WiseSayingController wiseSayingController = new WiseSayingController(TestUtil.genScanner("""
                등록
                오늘 점심 뭐 먹지.
                대욱
                종료
                """));

        // when
        wiseSayingController.run();



        // then
        String msg = out.toString().trim();
        assertTrue(msg.contains("명언이 등록되었습니다.")); // 성공 메세지가 포함되어 있는지 확인
        TestUtil.clearSetOutToByteArray(out);
        System.out.println(msg);
    }

    @DisplayName("목록(All) 테스트")
    @Test
    void searchAllTest() throws IOException {
        // given
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        WiseSayingController wiseSayingController = new WiseSayingController(TestUtil.genScanner("""
                목록
                All
                종료
                """));

        // when
        wiseSayingController.run();

        // then
        String msg = out.toString().trim();
        assertTrue(msg.contains("""
                번호 / 작가 / 명언
                ----------------------
                5 / 오늘 점심 뭐 먹지. / 대욱
                4 / 미래를 생각하라. / 대욱
                3 / 과거를 사랑하라. / 작자미상2
                2 / 현재를 사랑하라. / 작자미상
                1 / 현재를 사랑하라. / 작자미상
                """));
        TestUtil.clearSetOutToByteArray(out);
        System.out.println(msg);
    }

    @DisplayName("목록(keyword : content) 테스트")
    @Test
    void searchContentTest() throws IOException {
        // given
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        WiseSayingController wiseSayingController = new WiseSayingController(TestUtil.genScanner("""
                목록
                Keyword
                content
                사랑
                종료
                """));

        // when
        wiseSayingController.run();

        // then
        String msg = out.toString().trim();
        assertTrue(msg.contains("""
                번호 / 작가 / 명언
                ----------------------
                3 / 과거를 사랑하라. / 작자미상2
                2 / 현재를 사랑하라. / 작자미상
                1 / 현재를 사랑하라. / 작자미상
                """));
        TestUtil.clearSetOutToByteArray(out);
        System.out.println(msg);
    }

    @DisplayName("목록(keyword : author) 테스트")
    @Test
    void searchAuthorTest() throws IOException {
        // given
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        WiseSayingController wiseSayingController = new WiseSayingController(TestUtil.genScanner("""
                목록
                Keyword
                author
                대욱
                종료
                """));

        // when
        wiseSayingController.run();

        // then
        String msg = out.toString().trim();
        assertTrue(msg.contains("""
                번호 / 작가 / 명언
                ----------------------
                5 / 오늘 점심 뭐 먹지. / 대욱
                4 / 미래를 생각하라. / 대욱
                """));
        TestUtil.clearSetOutToByteArray(out);
        System.out.println(msg);
    }

    @DisplayName("목록 실패 테스트")
    @Test
    void searchFailTest() throws IOException {
        // given

        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        WiseSayingController wiseSayingController = new WiseSayingController(TestUtil.genScanner("""
                목록
                종료
                """));

        // when
        wiseSayingController.run();

        // then
        String msg = out.toString().trim();
        assertTrue(msg.contains("명언이 존재하지 않습니다."));
        TestUtil.clearSetOutToByteArray(out);
        System.out.println(msg);
    }

    @DisplayName("삭제 테스트")
    @Test
    void deleteTest() throws IOException {
        // given
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        WiseSayingController wiseSayingController = new WiseSayingController(TestUtil.genScanner("""
                삭제
                3
                종료
                """));

        // when
        wiseSayingController.run();

        // then
        String msg = out.toString().trim();
        assertTrue(msg.contains("명언이 삭제되었습니다."));
        TestUtil.clearSetOutToByteArray(out);
        System.out.println(msg);
    }

    @DisplayName("삭제 실패 테스트")
    @Test
    void deleteFailTest() throws IOException {
        // given
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        WiseSayingController wiseSayingController = new WiseSayingController(TestUtil.genScanner("""
                삭제
                15
                종료
                """));

        // when
        wiseSayingController.run();

        // then
        String msg = out.toString().trim();
        assertTrue(msg.contains("명언 삭제를 실패하였습니다."));
        TestUtil.clearSetOutToByteArray(out);
        System.out.println(msg);
    }

    @DisplayName("수정 테스트")
    @Test
    void updateTest() throws IOException {
        // given
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        WiseSayingController wiseSayingController = new WiseSayingController(TestUtil.genScanner("""
                수정
                1
                수정 명언 입니다.
                수정 작가
                종료
                """));

        // when
        wiseSayingController.run();

        // then
        String msg = out.toString().trim();
        assertTrue(msg.contains("명언이 수정되었습니다."));
        TestUtil.clearSetOutToByteArray(out);
        System.out.println(msg);
    }

    @DisplayName("수정 실패 테스트")
    @Test
    void updateFailTest() throws IOException {
        // given
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        WiseSayingController wiseSayingController = new WiseSayingController(TestUtil.genScanner("""
                수정
                5
                종료
                """));

        // when
        wiseSayingController.run();

        // then
        String msg = out.toString().trim();
        assertTrue(msg.contains("해당 명언이 존재하지 않습니다.") || msg.contains("명언 수정을 실패하였습니다:"));
        TestUtil.clearSetOutToByteArray(out);
        System.out.println(msg);
    }

    @DisplayName("빌드 테스트")
    @Test
    void buildTest() throws IOException {
        // given
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        WiseSayingController wiseSayingController = new WiseSayingController(TestUtil.genScanner("""
                빌드
                종료
                """));

        // when
        wiseSayingController.run();

        // then
        String msg = out.toString().trim();
        assertTrue(msg.contains("data.json 파일이 갱신되었습니다."));
        TestUtil.clearSetOutToByteArray(out);
        System.out.println(msg);
    }

    @DisplayName("빌드 실패 테스트")
    @Test
    void buildFailTest() throws IOException {
        // given
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        WiseSayingController wiseSayingController = new WiseSayingController(TestUtil.genScanner("""
                빌드
                종료
                """));

        // when
        wiseSayingController.run();

        // then
        String msg = out.toString().trim();
        assertTrue(msg.contains("data.json 파일의 갱신을 실패하였습니다."));
        TestUtil.clearSetOutToByteArray(out);
        System.out.println(msg);
    }
}
