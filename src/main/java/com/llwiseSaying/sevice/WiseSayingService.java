package com.llwiseSaying.sevice;

import com.llwiseSaying.repository.WiseSayingRepository;

import java.io.IOException;
import java.util.List;

public class WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        wiseSayingRepository = new WiseSayingRepository();
    }

    public int registerWiseSaying(String content, String author) throws IOException {
        return wiseSayingRepository.save(content, author);
    }

    public List<String[]> searchAllWiseSaying() throws IOException {
        return wiseSayingRepository.findByAll();
    }

    public String[] searchWiseSaying(int id) throws IOException {
        return wiseSayingRepository.findByWiseSaying(id);
    }

    public boolean deleteWiseSaying(int id) {
        return wiseSayingRepository.delete(id);
    }

    public boolean updateWiseSaying(int id, String content, String author) throws IOException {
        return wiseSayingRepository.update(id, content, author);
    }

    public boolean buildWiseSaying() throws IOException {
        return wiseSayingRepository.build();
    }

    public void initializeWiseSaying() throws IOException {
        wiseSayingRepository.lastIdAndDataFileCheck();
    }
}


