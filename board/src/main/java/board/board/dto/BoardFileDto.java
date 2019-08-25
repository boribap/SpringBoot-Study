package board.board.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="BoardFileDto : 게시글 첨부파일", description="게시글 첨부파일")
@Data
public class BoardFileDto {
	@ApiModelProperty(value="첨부파일 번호")
	private int idx;
	@ApiModelProperty(value="게시글 번호")
	private int boardIdx;
	@ApiModelProperty(value="원본 파일 이름")
	private String originalFileName;
	@ApiModelProperty(value="파일 저장 경로")
	private String storedFilePath;
	@ApiModelProperty(value="첨부파일 크기")
	private long fileSize;
}
