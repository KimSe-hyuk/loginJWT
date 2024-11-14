package com.example.spring.apringbootsecuritykimseheak.mapper;

import com.example.spring.apringbootsecuritykimseheak.model.Board;
import com.example.spring.apringbootsecuritykimseheak.model.Paging;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> selectBoardList(Paging page);
    int countBoards();
}
