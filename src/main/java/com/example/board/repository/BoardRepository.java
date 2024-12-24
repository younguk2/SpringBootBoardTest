package com.example.board.repository;
import com.example.board.domain.Board;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
    Page<Board> findAll(Pageable pageable);

    @Override
    Optional<Board> findById(Long aLong);

    @Modifying
    @Transactional
    @Query("UPDATE Board b SET b.boardTitle = :boardTitle, b.boardContent = :boardContent WHERE b.boardId = :boardId")
    int updateBoardByBoardId(Board board);
}
