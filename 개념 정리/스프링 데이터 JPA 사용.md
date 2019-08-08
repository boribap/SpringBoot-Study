## 스프링 데이터 JPA 사용

#### JPA 란? (Java Persistence API)

> 자바객체와 데이터베이스 테이블 간의 매핑을 처리하는 **ORM 기술의 표준** 

> 각 기능의 동작이 어떻게 되어야 한다는 것을 기술한 기술 명세 

ORM : 객체와 관계를 설정하는 것, 객체와 RDBMS를 매핑시키는 개념

JPA는 기술 명세이기때문에 이 기술명세에 따라 실제로 기능을 구현한 구현체가 필요함. --> Hibernate, EclipseLink 등등 --> JPA 프로바이더 

- 장점
  - 개발이 편리
    - 기본적인 CRUD용 SQL 직접 작성할 필요없음 
  - 데이터베이스에 독립적인 개발이 가능
    - 데이터베이스에 종속적이지 않음. 
    - 데이터베이스가 변경되더라도 JPA가 해당 데이터베이스에 맞는 쿼리를 생성해줌 
  - 유지보수가 쉽다
    - 테이블이 변경되어도 JPA의 앤티티(객체)만 수정하면 됨.
- 단점
  - learning curve가 크다
    - SQL을 직접적으로 작성하지 않으므로 튜님할때도 어려움
  - 특정 데이터베이스의 기능을 사용할 수 없다. 
  - 객체지향 설계가 필요 



#### 스프링 데이터 JPA 

- JPA를 스프링에서 쉽게 사용할 수 있도록 도와주는 프레임워크
- 내부적으로 Hibernate를 사용하여 구현하였다. 
- repository 라는 인터페이스 제공 --> 해당 인터페이스만 상속받아 정해진 규칙에 맞게 메서드를 작성하면 개발자가 작성해야 할 코드가 완성됨. 



#### 기본 설정 

1. JPA 설정 추가 

   application.properties 설정 추가 

   Database 설정 클래스에 Bean 등록

2. 자바8의 날짜 API설정

   자바7이하에서 문제되었던 시간관련 클래스가 추가됨 --> JSR-310

   설정없이 사용하면 mysql 버전에 따라 문제 발생 가능 --> Jsr310JpaConverters 적용하여 해결 

   

#### 엔티티 생성 

- 기존에는 BoardDto, BoardFileDto를 사용함 

- `@Entity` 어노테이션 사용 --> 해당 클래스가 JPA의 엔티티임을 나타냄 
- 엔티티 클래스는 테이블과 매핑됨 
- `@id` --> 엔티티의 PK 나타냄 



#### Jpa 컨트롤러 생성 

- `/jpa` 를 붙여 URI 작성 
- repository 사용하여 작성



#### 뷰 생성

- jpa를 위한 뷰 생성 
- `th:text="|${list.originalFileName}(${#numbers.formatInteger(list.fileSize/1000,1,'DEFAULT')} kb)|"` : 마이바티스는 쿼리에서 첨부파일의 크기를 계산했지만 JPA는 쿼리를 작성하지 않기 때문에 화면에서 첨부파일의 크기를 변경해서 보여줘야한다.



#### Repository 생성

- **Repository란?** 스프링데이터 JPA가 제공하는 인터페이스

- 여러종류의 인터페이스 중에서 CrudRepository 인터페이스를 사용할 것

  - 각 화살표는 extends 를 나타냄 

  - ![여러 종류의 인터페이스](C:\Users\bsww201\AppData\Roaming\Typora\typora-user-images\1565282431917.png)

  - CrudRepository 인터페이스는 레포지토리에서 사용할 도메인클래스와 도메인의 id 타입을 파라미터로 받는다. 

    ```java
    public interface JpaBoardRepository extends CrudRepository<BoardEntity, Integer>{
    	// 내용
    }
    ```

  - CrudRepository 인터페이스가 제공하는 메서드 중 해당 프로젝트에 사용한 메서드 

    | 메서드                                                     | 설명                                              |
    | ---------------------------------------------------------- | ------------------------------------------------- |
    | < S extends T> S save(S entity)                            | 주어진 엔티티를 저장                              |
    | < S extends T> Iterable< S> saveAll(Iterable< S> entities) | 주어진 엔티티 목록을 저장                         |
    | Option< T> findById(Id id)                                 | 주어진 Id로 식별된 엔티티를 반환                  |
    | boolean existsById(Id id)                                  | 주어진 Id로 식별된 엔티티가 존재하는지를 반환     |
    | Iterable< T> findById(Iterable< ID> ids)                   | 주어진 아이디 목록에 맞는 모든 엔티티 목록을 반환 |
    | Iterable< T> findAll()                                     | 모든 엔티티를 반환                                |
    | long count()                                               | 사용 가능한 엔티티의 개수를 반환                  |
    | void deleteById(ID id)                                     | 주어진 ID로 식별된 엔티티를 삭제                  |
    | void delete(T entity)                                      | 주어진 엔티티를 삭제                              |
    | void deleteAll(Iterable<? extends T> entities)             | 주어진 엔티티목록으로 식별된 엔티티를 모두 삭제   |
    | void deleteAll()                                           | 모든 엔티티를 삭제                                |

    

- @Query` 어노테이션을 사용하면 실행하고 싶은 쿼리를 직접 정의할 수 있음. 

  - JPQL이나 SQL을 이용할 수 있음 

  - JPQL를 사용해보면, 아래와 같다. 

  - `:[변수이름] ` 으로 파라미터를 지정한다. 변수이름은 `@Param` 어노테이션에 대응된다. 

  - **주의** ) FORM 절에 디비의 테이블이름이 아닌 **검색하려는 엔티티의 이름** 을 사용한다. 

    ```java
    @Query("SELECT file FROM BoardFileEntity file WHERE board_idx = :boardIdx AND idx = :idx")
    	BoardFileEntity findBoardFile(@Param("boardIdx") int boardIdx, @Param("idx") int idx);
    ```

  - 이렇게 쿼리 어노테이션을 사용하는데 쿼리문이 길어지거나 ㅁ낳아지게 되면 해당 쿼리문을 **XML 파일**에 작성한다. 

    - `<named-query>` 태그  : JPQL 사용
    - `<named-native-query>` 태그  : SQL 사용 
    - 그러나 너무 쿼리가 복잡하다면 아예 JPA 를 사용하는게 아닌 기존에 사용하던 마이바티스와 같은 매퍼 프레이워크 사용을 권장한다. 



- 쿼리 메서드 : 스프링 데이터 JPA는 규칙에 맞게 메서드를 추가하면 그 메서드의 이름으로 쿼리를 생성하는 기능을 제공

  - 쿼리메서드는 **find...By**,  **read...By**,  **query...By**, **count...By**, **get...By** 로 시작해야한다. 

  - 첫번째 By 뒤쪽은 Column이름으로 구성됨 (쿼리의 검색조건이 되는것)

    `findAllByOrderByBoardIdxDesc()` 와 같이 구성할 수 있음 

  - 두개 이상의 속성 조합하려면 And 키워드 사용하면 된다. --> 이외에도 여러 비교 연산자 존재 

    `findByTitleAndContents(String title, String contents); ` : 제목과 내용으로 검색 



