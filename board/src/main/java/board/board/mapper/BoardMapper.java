package board.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;

@Mapper
public interface BoardMapper {
	/*
	 * 인터페이스이므로 메서드의 이름과 반환형식만 지정 
	 * "여기서 지정한 이름은 SQL의 이름과 동일해야함" 
	 */
	List<BoardDto> selectBoardList() throws Exception;
	void insertBoard(BoardDto board) throws Exception;
	void updateHitCount(int boardIdx) throws Exception;
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	void updateBoard(BoardDto board) throws Exception;
	void deleteBoard(int boardIdx) throws Exception;
	void insertBoardFileList(List<BoardFileDto> list) throws Exception;
	List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception;
}
