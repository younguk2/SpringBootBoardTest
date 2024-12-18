package com.example.board;

import com.example.board.domain.Board;
import com.example.board.repository.BoardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoardApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void createBoard() {
		Board board = Board.builder().boardTitle("테스트 제목").boardContent("테스트 내용").build();

		Assertions.assertNotNull(board);
		Assertions.assertNotNull(board.getBoardTitle());
		Assertions.assertNotNull(board.getBoardContent());

		Assertions.assertEquals("테스트 제목",board.getBoardTitle());
		Assertions.assertEquals("테스트 내용",board.getBoardContent());
	}

	@Nested
	public class BoardRepositoryTests {

		@Autowired
		BoardRepository boardRepository;

		@Test
		@DisplayName("게시글 만들기")
		void testSaveAndRetrieveBoards() {
			// Given
			Board board1 = Board.builder().boardTitle("하하").boardContent("점심시간이다").build();
			Board board2 = Board.builder().boardTitle("흐윽").boardContent("배고프다").build();

			// When
			Board result1 = boardRepository.save(board1);
			Board result2 = boardRepository.save(board2);

			// Then
			assertThat(result1.getBoardTitle()).isEqualTo(board1.getBoardTitle());
			assertThat(result2.getBoardTitle()).isEqualTo(board2.getBoardTitle());
		}
	}
}
