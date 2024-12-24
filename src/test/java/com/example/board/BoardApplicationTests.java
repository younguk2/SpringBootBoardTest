package com.example.board;

import com.example.board.domain.Board;
import com.example.board.domain.BoardDto;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoardApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("게시글 엔티티가 생성되는 지 알아보는 테스트")
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

    @Nested
    @ExtendWith(SpringExtension.class)
    class BoardServiceTests {
		BoardService boardService;

		@MockitoBean
		BoardRepository boardRepository;

		@BeforeEach
		void setUp() {
			boardService = new BoardService(boardRepository);
		}

		@Test
		@DisplayName("게시글 생성 성공")
		void createBoard() {
			Board board3 = Board.builder().boardTitle("점심메뉴").boardContent("추천좀").build();
			ReflectionTestUtils.setField(board3, "boardId", 29);

			Mockito.when(boardRepository.save(board3)).thenReturn(board3);

			BoardDto dto = BoardDto.builder().boardId(31).boardTitle("점심메뉴").boardContent("추천좀").build();
			Board launch3 = boardService.upload(dto);

			assertThat(launch3.getBoardTitle()).isEqualTo(board3.getBoardTitle());
		}
	}
}
