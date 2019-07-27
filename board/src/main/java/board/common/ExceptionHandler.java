package board.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandler {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	// 모든 에러 처리 
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {
		
		// 에러시 보여줄 화면 지정 
		ModelAndView mv = new ModelAndView("/error/error_default");
		
		mv.addObject("exception", exception);
		log.error("exception", exception);
		
		return mv;
	}
}
