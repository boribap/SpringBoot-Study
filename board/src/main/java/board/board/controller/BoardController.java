package board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
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
	@RequestMapping(value="/board", method=RequestMethod.GET)
	public ModelAndView openBoardList() throws Exception{
		ModelAndView mv = new ModelAndView("/board/restBoardList");
		
		log.debug("restBoardList");
		
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
	}
	
	/*
	 * 게시글 등록 주소 
	 * 
	 */
	@RequestMapping(value="/board/write", method=RequestMethod.GET)
	public String openBoardWrite() throws Exception{
		return "/board/restBoardWrite";
	}
	/*
	 * 작성 후 리다이렉트
	 * 
	 * MultipartHttpServletRequest : 업로드된 파일을 처리하기 위한 다양한 메서드 제공하는 인터페이스
	 */
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		boardService.insertBoard(board, multipartHttpServletRequest);
		return "redirect:/board";
	}
	
	/*
	 * 상세화면의 호출 주소 추가
	 * 
	 * 글 상세 내용 조회하는 로직 호출 
	 * 
	 */
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
		ModelAndView mv = new ModelAndView("/board/restBoardDetail");
		
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		// 게시글 상세화면을 호출하면 게시글의 상세 내용을 조회하고 그 결과를 board 라는 키로 뷰로 넘겨줌
		mv.addObject("board",board);
		
		return mv;
	}
	
	/*
	 * 게시글을 수정하는 주소 
	 * 
	 */
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.PUT)
	public String updateBoard(BoardDto board) throws Exception{
		boardService.updateBoard(board);
		
		return "redirect:/board";
	}
	
	/*
	 * 게시글을 삭제하는 주소 
	 * 
	 */
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		
		return "redirect:/board";
	}
	
	/*
	 * 파일 다운로드 하는 주소 
	 * 
	 */
	@RequestMapping(value="/board/file", method=RequestMethod.GET)
	public void downloadBoradFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
		
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}
