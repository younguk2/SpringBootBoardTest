package com.example.board.domain;

import jakarta.xml.ws.BindingType;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
    private long boardId;
    private String boardTitle;
    private String boardContent;

    @Builder
    public BoardDto(long boardId, String boardTitle, String boardContent) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }

    public Board toEntity(BoardDto boardDto) {
        return Board.builder()
            .boardTitle(boardDto.getBoardTitle())
            .boardContent(boardDto.getBoardContent()).build();
    }
}

