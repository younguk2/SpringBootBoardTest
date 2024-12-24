package com.example.board.controller;

import com.example.board.domain.Board;
import com.example.board.domain.BoardDto;
import com.example.board.service.BoardService;
import com.example.board.util.BasicResponse;
import com.example.board.util.DefaultResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board")
    public ResponseEntity<? extends BasicResponse> board(
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo
    ) {
        PageRequest pageRequest = PageRequest.of(pageNo, 10);
        List<Board> result =  boardService.getAllBoard(pageRequest);

        DefaultResponse<List<Board>> defaultResponse = DefaultResponse.<List<Board>>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("출력한 게시글")
                .result(result)
                .build();

        return ResponseEntity.ok().body(defaultResponse);

    }

    @PostMapping("/board/write")
    public ResponseEntity<? extends BasicResponse> writeBoard(
            @RequestBody(required = false) BoardDto boardDto
    ){
        Board board = boardService.upload(boardDto);
        DefaultResponse<Board> defaultResponse = DefaultResponse.<Board>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("게시글 작성 완료")
                .result(board)
                .build();
        return ResponseEntity.ok().body(defaultResponse);
    }

    @PutMapping("/board/update")
    public ResponseEntity<? extends BasicResponse> updateBoard(
            @RequestBody(required = false) BoardDto boardDto
    ){
        Board updateBoard = boardService.update(boardDto);

        DefaultResponse<Board> defaultResponse = DefaultResponse.<Board>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("게시글 수정 완료")
                .result(updateBoard)
                .build();
        return ResponseEntity.ok().body(defaultResponse);
    }

    @DeleteMapping("/board/delete/")
    public ResponseEntity<? extends BasicResponse> deleteBoard(
            @RequestBody(required = false) Long boardId
    ){
        Board deletedBoard = boardService.delete(boardId);
        DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(boardId+" 번 게시글이 삭제되었습니다.")
                .build();
        return ResponseEntity.ok().body(defaultResponse);
    }
}
