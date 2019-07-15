package board.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import board.board.dto.BoardDto;
import board.board.service.BoardService;


@Controller
public class BoardController {
	
	/*
	 * 비즈니스로직을 처리하는 서비스 빈 연결 
	 */
	@Autowired
	private BoardService boardService;
	
	/*
	 * 주소 지정 
	 * 뷰 지정 
	 * 게시글 조회 
	 * 
	 */
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception{
		ModelAndView mv = new ModelAndView("/board/boardList");
		
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
	}
	
	/*
	 * 게시글 등록 주소 
	 * 
	 */
	@RequestMapping("/board/openBoardWrite.do")
	public String openBoardWrite() throws Exception{
		return "/board/boardWrite";
	}
	/*
	 * 작성 후 리다이렉트
	 */
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board) throws Exception{
		boardService.insertBoard(board);
		return "redirect:/board/openBoardList.do";
	}
}
