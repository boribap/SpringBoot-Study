package board.board.dto;
import lombok.Data;

@Data
public class BoardDto {
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String createdId;
	private String createdDatetime;
	private String updaterId;
	private String updatedDatetime;
}
