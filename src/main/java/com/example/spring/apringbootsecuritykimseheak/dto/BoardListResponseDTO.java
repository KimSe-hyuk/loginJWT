package com.example.spring.apringbootsecuritykimseheak.dto;

import com.example.spring.apringbootsecuritykimseheak.model.Board;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BoardListResponseDTO {
    List<Board> boards;
    boolean last;
}
