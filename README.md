# CRUD 게시판 API

이 프로젝트는 **Spring Boot**를 이용하여 구현한 **게시판 API**입니다. 이 프로젝트는 **게시글 CRUD** (Create, Read, Update, Delete) 기능의 학습을 위해 제작되었습니다. API 요청은 **RESTful** 형식으로 이루어지며, 요청에 대한 응답은 JSON 형식으로 반환됩니다.

<br/>
   
## 프로젝트 구조

─ controller : API 요청을 처리하는 컨트롤러  
─ dto        : 요청/응답에 사용할 DTO  
─ entity     : 데이터베이스와 매핑되는 엔티티  
─ repository : JPA를 사용한 데이터 접근 계층 (CRUD 처리)  
─ service    : 비즈니스 로직을 담당하는 서비스 계층  
─ exception  : 예외 처리 관련 클래스  


    
## 기능 구현


### 1. 게시글 작성 (POST `/boards`)
게시글을 작성하는 API입니다. 클라이언트로부터 제목과 내용을 받아 게시글을 생성합니다.
- **Request Body**: 제목(title), 내용(content)
- **Response**: 생성된 게시글의 ID, 제목(title), 내용(content)

### 2. 모든 게시글 조회 (GET `/boards`)
모든 게시글을 조회하는 API입니다. 데이터베이스에 저장된 게시글을 리스트 형식으로 반환합니다.
- **Response**: 게시글 목록 (ID, 제목(title), 내용(content))

### 3. 특정 게시글 조회 (GET `/boards/{id}`)
게시글 ID로 특정 게시글을 조회하는 API입니다. ID가 존재하지 않을 경우우 예외가 발생합니다.
- **Response**: 게시글의 ID, 제목(title), 내용(content)

### 4. 게시글 수정 (PUT `/boards/{id}`)
게시글을 수정하는 API입니다. 기존 게시글을 업데이트하며, 제목과 내용을 수정합니다.
- **Request Body**: 수정된 제목(title), 내용(content)
- **Response**: 수정된 게시글의 ID, 제목(title), 내용(content)

### 5. 게시글 삭제 (DELETE `/boards/{id}`)
게시글을 삭제하는 API입니다. 요청 ID로 게시글을 삭제하며, 게시글이 존재하지 않을 경우 예외가 발생합니다.
- **Response**: 204 No Content (삭제 완료 시)


## 사용된 기술 스택

- **Spring Boot**: Java 기반의 프레임워크로 RESTful API를 구축
- **Spring Data JPA**: MySQL과의 연동 및 데이터베이스 접근
- **MySQL**: 게시글 데이터를 저장하는 데이터베이스
- **Hibernate**: JPA 구현체로 데이터베이스 연동
- **Lombok**: Getter, Setter 등의 코드를 자동으로 생성
- **Validation API (Jakarta Validation)**: 입력 데이터에 대한 유효성 검사



## 예외 처리

애플리케이션에서 발생할 수 있는 예외를 **GlobalExceptionHandler** 클래스를 통해 처리합니다. 주요 예외는 다음과 같습니다.

- **IllegalArgumentException**: 존재하지 않는 게시글 조회 시 발생하며, 상태 코드 404를 반환합니다.
- **DataIntegrityViolationException**: 데이터베이스 무결성 제약 위반 시 발생하며, 상태 코드 400을 반환합니다.
- **Exception(기타 예외)**: 예기치 못한 서버 오류가 발생하면 상태 코드 500을 반환합니다.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("조회 실패: " + ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("데이터 무결성 오류: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + ex.getMessage());
    }
}
```



## 학습한 기술

1. **RESTful API 설계**: REST 아키텍처 스타일의 API 설계
2. **Spring Boot**: Spring Boot로 애플리케이션을 설정하고 REST API를 작성하는 방법
3. **JPA (Java Persistence API)**: 데이터베이스와의 연동을 위한 객체 관계 매핑(ORM) 기술
4. **Spring Data JPA**: 데이터베이스 접근을 위한 Spring Data JPA 사용법
5. **Validation**: 클라이언트 입력 데이터에 대한 유효성 검사 구현 (Dto)
6. **예외 처리**: 애플리케이션 전역 예외 처리 방법
