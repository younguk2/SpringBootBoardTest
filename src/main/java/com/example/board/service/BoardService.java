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
import java.util.Optional;

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

    public Board update(BoardDto boardDto) {
        Optional<Board> board = boardRepository.findById(boardDto.getBoardId());
        //Board findBoard = board.orElse(null);
        if(board.isPresent()) {
            boardRepository.updateBoardByBoardId(boardDto.toEntity(boardDto));
            return boardRepository.findById(boardDto.getBoardId()).orElse(null);
        } else {
            throw new IllegalStateException("게시글을 찾을 수 없습니다.");
        }
    }

    public Board delete(Long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        Board deleted = board.orElse(null);
        board.ifPresent(boardRepository::delete);
        return deleted;
    }
}
