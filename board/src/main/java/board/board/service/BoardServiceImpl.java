package board.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.board.dto.BoardDto;
import board.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	
	/*
	 * 데이터베이스에 접근하는 DAO 빈 선언 
	 * 
	 */
	@Autowired
	private BoardMapper boardMapper;
	
	/*
	 * 사용자 요청 처리를 위한 비즈니스 로직 구현 
	 * 
	 */
	@Override
	public List<BoardDto> selectBoardList() throws Exception{
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDto board) throws Exception{
		boardMapper.insertBoard(board);
	}
	
}
