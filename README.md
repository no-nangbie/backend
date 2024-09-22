
<a href="https://club-project-one.vercel.app/" target="_blank">
<img src="https://github.com/user-attachments/assets/6cc8dc09-b574-4d68-8185-e409722090a7" alt="배너" width="100%"/>
</a>

<br/>
<br/>

# 0. Getting Started (시작하기)
[서비스 링크](http://guild-master.s3-website.ap-northeast-2.amazonaws.com)


<br/>
<br/>

# 1. Project Overview (프로젝트 개요)
- 프로젝트 이름: 낭비없냉
- 프로젝트 설명: 식료품을 관리하여 불필요한 지출 및 음식물 쓰레기 발생 최소화

<br/>
<br/>

# 2. Key Features (주요 기능)

- **냉장고 관련 기능**:
  - 재료 
  - 

- **레시피 관련 기능**:
  - 이벤트 생성

- **회원 관련 기능**:
  - 회원 가입
  - 로그인
  - 로그 아웃
  - 회원 탈퇴


<br/>
<br/>

# 3.📝 관련 문서

#### [📌 요구사항 정의서](https://docs.google.com/spreadsheets/d/1gfuB42EHzzEN3mD6FSUGyY0NquEhttps://docs.google.com/spreadsheets/d/1fiRrCwtlphy_jmjqqs45Bgn-7dJVolpgS5HwgvlJ5A0/edit)

#### [📌 API 명세서](https://docs.google.com/spreadsheets/d/1gfuB42EHzzEN3mD6FSUGyY0NquEv6dH_eMiSqoHsKfw/edit?gid=626989714#gid=626989714)

#### [📌 목업](https://www.figma.com/design/eldq0trjBohvEKYUz5DaGJ/%EB%83%89%EC%9E%A5%EA%B3%A0?node-id=0-1&node-type=canvas&t=vywo7RPcHMtObSiv-0)

#### [📌 ERD](https://www.erdcloud.com/d/JHnyMK853wxpLo62d)

<br>
<br/>
<br/>

# 4. Tasks & Responsibilities (작업 및 역할 분담)
|  |  |  |
|-----------------|-----------------|-----------------|
| 👑<br>   신민준   |  <img src="https://github.com/user-attachments/assets/7cd2442d-bad2-4499-b3a6-8f4563c0502d" alt="신민준" width="100"> | <ul><li>프로젝트 계획 및 관리</li><li>팀 리딩 및 커뮤니케이션</li><li>JWT Security 구현</li></li><li>UI/UX 구현</li></ul>     |
|   윤영아   |  <img src="https://github.com/user-attachments/assets/65376949-699b-4b50-ab2a-d8821ba900fa" alt="윤영아" width="150" height="70">| <ul><li>길드 검색, 길드 보드, 길드원 관리 페이지 게시판 제작</li><li>길드, 이벤트 조회 및 상세 확인, 길드원 가입 신청 및 수락 기능</li><li>Axios 를 활용한 백엔드와의 비동기 통신 구현</li><li>페이지별 컴포넌트 세분화 및 코드 베이스 일관화</li></ul> |
|   양수명   |  <img src="https://github.com/user-attachments/assets/c20ac766-1351-41fe-98e3-924b6aa854f0" alt="양수명" width="100" height="80" height="80">    |<ul><li>테이블 설계 및 객체 연관 관계 매핑</li><li>인증, 인가를 위한 시큐리티 적용 및 JWT 구현 </li><li>레디스를 활용한 로그아웃 구현</li><li>계층 구조에 따른 회원가입, 길드 가입요청, 가입요청수락 등 전반적인 핵심 서비스 로직 구현</li><li>전역 예외 처리 구현</li></ul>  |
|   김준하    |  <img src="https://github.com/user-attachments/assets/080bf5a1-bc7d-4279-944f-9a2ca49f562c" alt="김준하" width="100" height="80">    | <ul><li>길드 이벤트 CRUD 기능 설계 및 구현</li><li>길드원 이벤트 참가 기능 개발</li><li>이벤트 참가자 정보 관리 및 통계 구현</li><li>요구사항 분석을 통한 테이블 관계 정의 및 ERD 정규화</li><li>AWS 클라우드 인프라 배포</li></ul>    |

<br/>
<br/>

# 5. Technology Stack (기술 스택)
### 🔨 Front-end
<img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white">
<img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white">
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white">
<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white">


### ⛏ Back-end
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white">
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
<br/>

### ⛏ Database & Caching
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">


<br/>

### ⛏ Etc
<img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"> 
<img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white">
<img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white">
<img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=Amazon%20S3&logoColor=white">

<br/>




# 6. Project Structure (프로젝트 구조)
<br>
<details>

<summary> 📂 프로젝트 폴더 구조</summary>

```
🏠 최애의 i
├─ .github
│  └─ ISSUE_TEMPLATE  ─────────────── 📝 이슈 템플릿
│
├─ 📂 client
│  │─ .env.sample
│  │─ .eslintrc.json  ──────────────── ⚙️ eslint 설정 파일
│  │─ .gitignore
│  │─ .prettierrc.json ─────────────── ⚙️ prettier 설정 파일
│  │─ package-lock.json
│  │─ package.json
│  │
│  ├─ ├─ public
├─  src
│  ├─ App.css
│  ├─ App.js
│  ├─ Global.css
│  ├─ auth ─────────────────────────────── 🙋‍♂️ 로그인 전역 관리 파일
│  │  ├─ UsePersistedState.jsx
│  │  └─ index.jsx
│  ├─ component ────────────────────────── 🗂️ 각 페이지에서 사용되는 컴포넌트
│  │  ├─ GuildBoardPage
│  │  ├─ GuildListPage
│  │  ├─ HomePage
│  │  ├─ SignUpPage
│  │  ├─ ManagePage
│  │  │  ├─ ManagePlayerTab.js
│  │  │  ├─ PlayerItem.js
│  │  │  ├─ PlayerList.js
│  │  │  ├─ PlayersItem.js
│  │  │  ├─ Tab.js
│  │  │  ├─ WaitList.js
│  │  │  ├─ WaitPlayersItem.js
│  │  │  └─ memberGuildData.js
│  │  ├─ LargeModal.js ──────────────────── 🗂️ 모든 페이지에서 공통으로 사용되는 컴포넌트
│  │  ├─ Modal.js
│  │  ├─ OutPut.js
│  │  └─ RegistInput.js
│  ├─ image 
│  │  ├─ loastark.png
│  │  ├─ lol.png
│  │  ├─ overwatch.png
│  │  └─ valorant.png
│  ├─ logo
│  │  ├─ fulllogo_white.png
│  │  ├─ fulllogo_white_big.png
│  │  └─ logo_white.png
│  ├─ pages ─────────────────────────────── 🗂️ 라우팅이 적용된 API를 요청하는 페이지 컴포넌트
│  │  ├─ GlobalHeader.js
│  │  ├─ GuildBoardPage.js
│  │  ├─ GuildListPage.js
│  │  ├─ HomePage.js
│  │  ├─ LandingPage.js
│  │  ├─ LoginPage.js
│  │  ├─ ManagePage.js
│  │  ├─ MyPage.js
│  │  └─ SignUpPage.js
│  │
│  ├─setupTests.js
│  ├─ index.css
│  ├─ index.js
│  └─ logo.svg
│
└─ 📂 server
   │─ .gitignore
   │─ build.gradle
   │─ gradlew
   │─ gradlew.bat
   │─ settings.gradle
   │
   ├─ 📂 gradle-wrapper
   │  ├─ gradle-wrapper.jar
   │  └─ gradle-wrapper.properties
   │
   └─ └─ src
   ├─ main
   │  └─ java
   │     └─ com
   │        └─ continewbie
   │           └─ guild_master
   │              ├─ GuildMasterApplication.java
   │              ├─ advice
   │              │  └─ GlobalExceptionAdvice.java
   │              ├─ auditable
   │              │  └─ Auditable.java
   │              ├─ auth
   │              │  ├─ controller
   │              │  │  └─ AuthController.java
   │              │  ├─ dto
   │              │  │  └─ LoginDto.java
   │              │  ├─ filter
   │              │  │  ├─ JwtAuthenticationFilter.java
   │              │  │  └─ JwtVerificationFilter.java
   │              │  ├─ handler
   │              │  │  ├─ MemberAccessDeniedHandler.java
   │              │  │  ├─ MemberAuthenticationEntryPoint.java
   │              │  │  ├─ MemberAuthenticationFailureHandler.java
   │              │  │  └─ MemberAuthenticationSuccessHandler.java
   │              │  ├─ jwt
   │              │  │  └─ JwtTokenizer.java
   │              │  ├─ service
   │              │  │  └─ AuthService.java
   │              │  ├─ userDetails
   │              │  │  └─ MemberDetailsService.java
   │              │  └─ utils
   │              │     ├─ ErrorResponse.java
   │              │     └─ JwtAuthorityUtils.java
   │              ├─ config
   │              │  └─ SecurityConfiguration.java
   │              ├─ dto
   │              │  ├─ MultiResponseDto.java
   │              │  ├─ PageInfo.java
   │              │  └─ SingleResponseDto.java
   │              ├─ errorresponse
   │              │  └─ ErrorResponse.java
   │              ├─ event
   │              │  ├─ controller
   │              │  │  └─ EventController.java
   │              │  ├─ dto
   │              │  │  └─ EventDto.java
   │              │  ├─ entity
   │              │  │  └─ Event.java
   │              │  ├─ mapper
   │              │  │  └─ EventMapper.java
   │              │  ├─ repository
   │              │  │  └─ EventRepository.java
   │              │  └─ service
   │              │     └─ EventService.java
   │              ├─ exception
   │              │  ├─ BusinessLogicException.java
   │              │  └─ ExceptionCode.java
   │              ├─ game
   │              │  ├─ controller
   │              │  │  └─ GameController.java
   │              │  ├─ dto
   │              │  │  └─ GameDto.java
   │              │  ├─ entity
   │              │  │  └─ Game.java
   │              │  ├─ mapper
   │              │  │  └─ GameMapper.java
   │              │  ├─ repository
   │              │  │  └─ GameRepository.java
   │              │  └─ service
   │              │     └─ GameService.java
   │              ├─ guild
   │              │  ├─ controller
   │              │  │  └─ GuildController.java
   │              │  ├─ dto
   │              │  │  └─ GuildDto.java
   │              │  ├─ entity
   │              │  │  └─ Guild.java
   │              │  ├─ mapper
   │              │  │  └─ GuildMapper.java
   │              │  ├─ repository
   │              │  │  └─ GuildRepository.java
   │              │  └─ service
   │              │     └─ GuildService.java
   │              ├─ helper
   │              │  └─ event
   │              │     └─ MemberRegistrationApplicationEvent.java
   │              ├─ member
   │              │  ├─ controller
   │              │  │  └─ MemberController.java
   │              │  ├─ dto
   │              │  │  └─ MemberDto.java
   │              │  ├─ entity
   │              │  │  └─ Member.java
   │              │  ├─ mapper
   │              │  │  └─ MemberMapper.java
   │              │  ├─ repository
   │              │  │  └─ MemberRepository.java
   │              │  └─ service
   │              │     └─ MemberService.java
   │              ├─ memberguild
   │              │  ├─ dto
   │              │  │  └─ MemberGuildDto.java
   │              │  ├─ entity
   │              │  │  └─ MemberGuild.java
   │              │  └─ mapper
   │              │     └─ MemberGuildMapper.java
   │              ├─ memeberevent
   │              │  ├─ dto
   │              │  │  ├─ MemberEventDto.java
   │              │  │  └─ MemberEventResponseDto.java
   │              │  ├─ entity
   │              │  │  └─ MemberEvent.java
   │              │  ├─ mapper
   │              │  │  └─ MemberEventMapper.java
   │              │  └─ repository
   │              │     └─ MemberEventRepository.java
   │              ├─ position
   │              │  ├─ dto
   │              │  │  └─ PositionDto.java
   │              │  ├─ entity
   │              │  │  └─ Position.java
   │              │  └─ repository
   │              │     └─ PositionRepository.java
   │              ├─ redis
   │              │  └─ RedisRepositoryConfig.java
   │              └─ utils
   │                 ├─ CustomBeanUtils.java
   │                 ├─ DataInitializer.java
   │                 ├─ UriCreator.java
   │                 └─ validator
   │                    ├─ InvalidEventDateException.java
   │                    ├─ NotSpace.java
   │                    └─ NotSpaceValidator.java
   └─ test
      └─ java
         └─ com
            └─ continewbie
               └─ guild_master
                  └─ GuildMasterApplicationTests.java
```

<br>
<br/>
</details>
<br>
<br/>

## 7. 구현 이미지

| 페이지 (기능)         | 이미지                                                                                                                          |
| --------------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| 메인                  | ![메인 페이지](https://github.com/nalsae/seb45_main_011/assets/101828759/7e293d8a-9934-4aa8-b3af-e6ccd7aa112b)                  |
| 로그인                | ![로그인 페이지](https://github.com/nalsae/seb45_main_011/assets/101828759/7b393893-9c3b-45a3-aa21-748f46b8b522)                |
| 회원 가입             | ![회원 가입 페이지](https://github.com/nalsae/seb45_main_011/assets/101828759/9b6db6ec-0e98-4267-a172-c2adf29a9fb9)             |
| 정원 (꾸미기)         | ![정원 페이지 - 꾸미기](https://github.com/nalsae/seb45_main_011/assets/101828759/de3caba6-42f5-4708-ad9c-8bd2e50a4231)         |
| 정원 (장식품 구매)    | ![정원 페이지 - 구매](https://github.com/nalsae/seb45_main_011/assets/101828759/49c9acf3-b06d-44e9-9a16-208f8a022b63)           |
| 정원 (식물 카드 연동) | ![정원 페이지 - 식물 카드 연동](https://github.com/nalsae/seb45_main_011/assets/101828759/ef6da970-f632-45b8-b0f9-eb737d5b9903) |
| 식물 카드 목록, 등록  | ![식물카드 목록](https://github.com/nalsae/seb45_main_011/assets/101828759/a7f05c66-9a9b-4ff0-a32d-08599fb4dd9a)                |
| 식물 카드 상세        | ![식물 카드 상세](https://github.com/nalsae/seb45_main_011/assets/101828759/38ab89f9-ec9c-4410-aeb9-83e9cd09ca7e)               |
| 식물 카드 수정, 삭제  | ![식물 카드 수정, 삭제](https://github.com/nalsae/seb45_main_011/assets/101828759/6fe42722-8676-4eee-bd00-27bc16c9c9f0)         |
| 커뮤니티              | ![커뮤니티](https://github.com/nalsae/seb45_main_011/assets/101828759/a27f3bfd-3346-4070-a6b8-082b5708f73a)                     |
| 게시글 상세           | ![게시글 상세](https://github.com/nalsae/seb45_main_011/assets/101828759/128584db-95cd-4edf-9161-74962f67d645)                  |
| 게시글 등록           | ![게시글 등록](https://github.com/nalsae/seb45_main_011/assets/101828759/211bd724-984e-45b8-989a-4c59246dbec2)                  |
| 게시글 수정, 삭제     | ![게시글 수정, 삭제](https://github.com/nalsae/seb45_main_011/assets/101828759/1ea79b6f-2717-406b-89f4-adbdfcaa785e)            |
| 댓글 등록, 수정, 삭제 | ![댓글 등록, 수정, 삭제](https://github.com/nalsae/seb45_main_011/assets/101828759/52ccd8f0-5e30-4198-bfc9-2dca9b28b870)        |
| 정보 수정             | ![정보 수정](https://github.com/nalsae/seb45_main_011/assets/101828759/a74749c1-d239-48cc-ac55-f6eda46ff228)                    |
| 내 게시글             | ![내 게시글](https://github.com/nalsae/seb45_main_011/assets/101828759/9d05dd7d-0cd3-4f86-b0b8-43b5438ba724)                    |
| 회원 탈퇴             | ![회원 탈퇴](https://github.com/nalsae/seb45_main_011/assets/101828759/120777f3-9c77-4681-bd3c-78cd5204c90e)                    |

<br>
