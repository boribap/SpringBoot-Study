package board.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
	 * 로거 적용
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/*
	 * 주소 지정 
	 * 뷰 지정 
	 * 게시글 조회 
	 * 
	 */
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception{
		ModelAndView mv = new ModelAndView("/board/boardList");
		
		log.debug("openBoardList");
		
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
	 * 
	 * MultipartHttpServletRequest : 업로드된 파일을 처리하기 위한 다양한 메서드 제공하는 인터페이스
	 */
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		boardService.insertBoard(board, multipartHttpServletRequest);
		return "redirect:/board/openBoardList.do";
	}
	
	/*
	 * 상세화면의 호출 주소 추가
	 * 
	 * 글 상세 내용 조회하는 로직 호출 
	 * 
	 */
	@RequestMapping("/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		// 게시글 상세화면을 호출하면 게시글의 상세 내용을 조회하고 그 결과를 board 라는 키로 뷰로 넘겨줌
		mv.addObject("board",board);
		
		return mv;
	}
	
	/*
	 * 게시글을 수정하는 주소 
	 * 
	 */
	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(BoardDto board) throws Exception{
		boardService.updateBoard(board);
		
		return "redirect:/board/openBoardList.do";
	}
	
	/*
	 * 게시글을 삭제하는 주소 
	 * 
	 */
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		
		return "redirect:/board/openBoardList.do";
	}
}
