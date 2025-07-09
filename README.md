# public-holiday
전 세계 공휴일 데이터를 저장,조회,관리하는 서비스

## 빌드 & 실행 방법
### 빌드
./gradlew build

### 실행
./gradlew bootRun

포트 : 8080

## API 외의 기능
1) 최초 실행 시 최근 5 년의 공휴일을 일괄 적재
2) 매년 1 월 2 일 01:00 에 전년도·금년도 데이터를 자동 업데이트

## REST API 명세

### 1) GET /search/publicHolidays
- 설명 : 연도, 국가, 기간(form-to) 필터 기반 공휴일 조회
- Request
  - Query Parameters: ?year=2025&countryCode=US&currentPage=1&limit=3
- Response 예시
    ```
    {
    "pagination": {
        "totalItems": 14,
        "offset": 0,
        "limit": 3,
        "totalPages": 5,
        "currentPage": 1
    },
    "data": [
        {
            "date": "2025-01-01",
            "localName": "New Year's Day",
            "types": [
                "Public"
            ],
            "countryCode": "US",
            "name": "New Year's Day",
            "global": "true",
            "fixed": "false",
            "countryName": "United States"
        },
        {
            "date": "2025-01-20",
            "localName": "Martin Luther King, Jr. Day",
            "types": [
                "Public"
            ],
            "countryCode": "US",
            "name": "Martin Luther King, Jr. Day",
            "global": "true",
            "fixed": "false",
            "countryName": "United States"
        },
        {
            "date": "2025-02-12",
            "localName": "Lincoln's Birthday",
            "types": [
                "Observance"
            ],
            "countryCode": "US",
            "name": "Lincoln's Birthday",
            "global": "false",
            "fixed": "false",
            "counties": [
                "US-CA",
                "US-CT",
                "US-IL",
                "US-IN",
                "US-KY",
                "US-MI",
                "US-NY",
                "US-MO",
                "US-OH"
            ],
            "countryName": "United States"
        }
    ]
  }
    ```

### 2) PUT /api/update/{year}/{countryCode}
- 설명 : 특정 연도, 국가 데이터를 재호출하여 업데이트
- Request
  - Path Variable : 2025, KR
- Response 예시
  ```
  {
    "status": 200,
    "message": "Success"
  }
  ```

### 3) DELETE /api/delete/{year}/{countryCode}
- 설명 : 특정 연도, 국가의 공휴일 데이터 삭제
- Request
  - Path Variable : 2025, KR
- Response 예시
  ```
  {
    "status": 200,
    "count": 14
  }
  ```

## ./gradlew clean test 성공 스크린샷

![테스트 성공 스크린샷](https://github.com/yelin1106/public-holiday/blob/main/gradlew-clean-test-success.png?raw=true)

## Swagger UI 확인방법
- Swagger UI : http://localhost:8080/swagger-ui/index.html

