package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.BoardDto;
import com.example.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAllBoard(Pageable pageable) {
        List<Board> boards = new ArrayList<>();
        Page<Board> Boardpage = boardRepository.findAll(pageable);

        if (Boardpage.hasContent()) {
            boards = Boardpage.getContent();
        }
        return boards;
    }

    public Board upload(BoardDto boardDto) {
        Board board = boardDto.toEntity(boardDto);
        boardRepository.save(board);
        return board;
    }
}
