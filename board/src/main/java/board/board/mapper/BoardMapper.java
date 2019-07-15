package board.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.board.dto.BoardDto;

@Mapper
public interface BoardMapper {
	/*
	 * 인터페이스이므로 메서드의 이름과 반환형식만 지정 
	 * "여기서 지정한 이름은 SQL의 이름과 동일해야함" 
	 */
	List<BoardDto> selectBoardList() throws Exception;
	void insertBoard(BoardDto board) throws Exception;
}
