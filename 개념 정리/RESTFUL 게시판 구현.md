## RESTFUL 게시판 구현

**REST란? 잘 표현된 HTTP URI로 리소스를 정의하고 HTTP 메서드로 리소스에 대한 행위를 정의하는 것. 리소스는 json, xml과 같은 여러언어로 표현 가능**

* 용어 정리 
  * 리소스 : 서비스를 제공하는 시스템의 자원 --> URI 로 정의된다. 
  * URI 설계 규칙 
    1. 명사를 사용 
    2. 슬래시로 계층관계를 나타냄 
    3. 소문자로만 작성
    4. 하이픈(-)은 사용할 수 있지만 언더바(_)는 사용하지 않음 
  * HTTP 메서드 : CRUD 4가지 메서드 사용 



#### RESTful 게시판으로 변경 

- /board/openBoardList.do --> GET /board
- /board/openBoardWrite.do --> GET /board/write
- /board/insertBoard.do --> POST /board/write

```java
@RequestMapping("/board/openBoardList.do")

@RequestMapping(value="/board", method=RequestMethod.GET)
```



- HTML은 put과 delete 메서드 지원안함 

  - 스프링은 HiddenHttpMethodFilter 를 통해 해당 기능 제공
  - 이것은 _method 라는 이름의 파라미터가 존재할 경우 그 값을 요청방식으로 사용한다. ( _method 값을 PUT으로 보내면 컨트롤러에서 RequestMethod.PUT의 값을 가진 URI를 호출)
  - 그래서 보이지 않는 _method 라는 이름의 입력창 추가 

  

#### REST API로 변경 





