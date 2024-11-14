package com.example.spring.apringbootsecuritykimseheak.service;

import com.example.spring.apringbootsecuritykimseheak.mapper.BoardMapper;
import com.example.spring.apringbootsecuritykimseheak.model.Board;
import com.example.spring.apringbootsecuritykimseheak.model.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;
    public List<Board> getBoardList(int page, int size) {
        int offset = (page - 1) * size; // 페이지는 1부터 시작, offset 계산
        return boardMapper.selectBoardList(
                Paging.builder()
                        .offset(offset)
                        .size(size)
                        .build()
        );
    }
    public int getTotalBoards() {
        return boardMapper.countBoards(); // 총 게시글 수 반환
    }
}
