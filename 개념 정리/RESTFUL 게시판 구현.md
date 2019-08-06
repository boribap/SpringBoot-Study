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

- 지금까지는 Thymeleaf, jQuery를 사용해 화면을 개발 --> 한 주소에서 비지니스 로직도 처리하고 화면도 호출함 
- 이것을 구분해 줘야 REST API를 만든것이라고 할 수 있음 
- `@RestController` 어노테이션을 사용 --> 해당 API의 응답 결과를 웹 response body를 이용해서 보내준다.  결과값을 JSON형식으로 만들어 준다. 
- post 메서드는 `@RequestBody` 를 사용하고 get은 `@RequesParam` 어노테이션을 사용한다.

