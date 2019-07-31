package board.board.service;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.mapper.BoardMapper;
import board.common.FileUtils;

@Service
public class BoardServiceImpl implements BoardService{
	
	/*
	 * 데이터베이스에 접근하는 DAO 빈 선언 
	 * 
	 */
	@Autowired
	private BoardMapper boardMapper;
	
	/*
	 * 파일 정보 저장하기 위해 만든 FileUtils 클래스 사용하기 
	 */
	@Autowired
	private FileUtils fileUtils;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/*
	 * 사용자 요청 처리를 위한 비즈니스 로직 구현 
	 * 
	 */
	@Override
	public List<BoardDto> selectBoardList() throws Exception{
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		// 게시글 등록 -> 게시글 먼저 등록 후 등록된 게시글 번호를 이용해 파일을 저장 
		boardMapper.insertBoard(board);
		
		// FileUtils 클래스를 이용해서 서버에 파일 저장 
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		
		// 파일의 정보를 맵에 저장 
		if(CollectionUtils.isEmpty(list) == false) {
			boardMapper.insertBoardFileList(list);
		}
		
		// 파일관련 로그 남기기 
		if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
			
			// html 에서 사용한 파일 태그를 불러오는 작업 
			// 파일 태그가 여러개 일수도 있기 때문에 iterator 사용함.
			Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
			String name;
			while(iterator.hasNext()) {
				name = iterator.next();
				log.debug("file tag name : " + name);
				List<MultipartFile> lst = multipartHttpServletRequest.getFiles(name);
				for(MultipartFile multipartFile : lst) {
					log.debug("start file information");
					log.debug("file name : " + multipartFile.getOriginalFilename());
					log.debug("file size : " + multipartFile.getSize());
					log.debug("file content type : " + multipartFile.getContentType());
					log.debug("end file information.\n");
				}
			}
		}
		
	}
	
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception{
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		boardMapper.updateHitCount(boardIdx);

		return board;
	}
	
	@Override
	public void updateBoard(BoardDto board) throws Exception{
		boardMapper.updateBoard(board);
	}
	
	public void deleteBoard(int boardIdx) throws Exception{
		boardMapper.deleteBoard(boardIdx);
	}
	
}
