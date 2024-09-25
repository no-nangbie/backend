<img src="https://github.com/user-attachments/assets/f241754a-1612-4428-82e6-3983153e9c9a" alt="배너" width="100%"/>
</a>

<br/>
<br/>

# 낭비없냉

### 프로젝트 소개
- 나의 냉장고 속 식료품을 관리하는 서비스입니다.
- 냉장고 속 식료품을 가지고 만들 수 있는 요리를 검색 할 수 있습니다.
- 사용자의 취향 및 알레르기 정보, 식료품의 상태에 따라 최적의 요리 레시피를 추천해 줍니다.
- 개발 기간 : 2024.08.30 ~ 2024.09.23
- URL : https://nonangbie.site
<br/>

### 기대효과
식료품 소비기한을 관리하고 불필요한 지출 및 음식물 쓰레기 발생을 최소화에 도움을 줄 수 있습니다.

<br/>
<hr/>

# Key Features (주요 기능)

- **냉장고 관련 기능**:
  - 냉장고 재료 등록 
  - 냉장고 재료 전체 조회
  - 냉장고 재료 상세 조회
  - 냉장고 재료 수정
  - 냉장고 모든 식재료 삭제
  - 냉장고 재료 삭제
  - 식재료 검색

- **메뉴 관련 기능**:
  - 메뉴 등록
  - 전체 메뉴 조회
  - 단일 메뉴 조회
  - 메뉴 수정
  - 메뉴 삭제
  - 메뉴 좋아요
  - 메뉴 추천
  - 음성 인식 API

  **게시물 관련 기능**:
  - 게시글 등록
  - 게시글 수정
  - 전체 게시글 조회
  - 단일 게시글 조회
  - 게시글 삭제
  - 게시글 좋아요

- **회원 관련 기능**:
  - 이메일 인증 코드 전송
  - 이메일 인증 코드 확인
  - 회원 가입
  - 닉네임 중복 확인
  - 로그인
  - 로그 아웃
  - 회원 탈퇴


<br/>
<br/>

# 📝 관련 문서

#### [📌 요구사항 정의서](https://docs.google.com/spreadsheets/d/1gfuB42EHzzEN3mD6FSUGyY0NquEhttps://docs.google.com/spreadsheets/d/1fiRrCwtlphy_jmjqqs45Bgn-7dJVolpgS5HwgvlJ5A0/edit)

#### [📌 API 명세서](https://www.notion.so/API-5738db9cfa4948248fb665f5362b46bb)

#### [📌 목업](https://www.figma.com/design/eldq0trjBohvEKYUz5DaGJ/%EB%83%89%EC%9E%A5%EA%B3%A0?node-id=0-1&node-type=canvas&t=vywo7RPcHMtObSiv-0)

#### [📌 ERD](https://www.erdcloud.com/d/JHnyMK853wxpLo62d)

<br>
<br/>
<br/>

# Tasks & Responsibilities (작업 및 역할 분담)
|  |  |  |
|-----------------|-----------------|-----------------|
| 👑<br>   신민준   |  <img src="https://github.com/user-attachments/assets/7cd2442d-bad2-4499-b3a6-8f4563c0502d" alt="신민준" width="100"> | <ul><li>프로젝트 계획 및 관리</li><li>팀 리딩 및 커뮤니케이션</li><li>JWT Security 구현</li></li><li>UI/UX 구현</li></ul>     |
|   윤영아   |  <img src="https://github.com/user-attachments/assets/2d362c3d-0ad7-47b1-8238-3d76412a4756" alt="윤영아" width="100" height="70">| <ul><li>길드 검색, 길드 보드, 길드원 관리 페이지 게시판 제작</li><li>길드, 이벤트 조회 및 상세 확인, 길드원 가입 신청 및 수락 기능</li><li>Axios 를 활용한 백엔드와의 비동기 통신 구현</li><li>페이지별 컴포넌트 세분화 및 코드 베이스 일관화</li></ul> |
|   양수명   |  <img src="https://github.com/user-attachments/assets/c20ac766-1351-41fe-98e3-924b6aa854f0" alt="양수명" width="100" height="80" height="80">    |<ul><li>테이블 설계 및 객체 연관 관계 매핑</li><li>인증, 인가를 위한 시큐리티 적용 및 JWT 구현 </li><li>레디스를 활용한 로그아웃 구현</li><li>계층 구조에 따른 회원가입, 길드 가입요청, 가입요청수락 등 전반적인 핵심 서비스 로직 구현</li><li>전역 예외 처리 구현</li></ul>  |
|   김준하    |  <img src="https://github.com/user-attachments/assets/080bf5a1-bc7d-4279-944f-9a2ca49f562c" alt="김준하" width="100" height="80">    | <ul><li>메뉴 CRUD</li><li>Web Speech API 연결</li><li>EC2 및 S3 배포</li><li>요구사항 분석을 통한 테이블 관계 정의 및 ERD 정규화</li><li>HTTPS 도메인 적용</li></ul>    |

<br/>
<br/>

# Technology Stack (기술 스택)
### 🔨 Front-end
<img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white"><img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white"><img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white"><img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white">


### ⛏ Back-end
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white"><img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
<br/>

### ⛏ Database & Caching
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"><img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">


<br/>

### ⛏ Etc
<img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white"><img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white"><img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=Amazon%20S3&logoColor=white">

<br/>




# Project Structure (프로젝트 구조)
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

## 구현 이미지

| 랜딩 페이지 | 회원가입 |
| :---: | :---: |
|  |  |

| 로그인 실패 및 성공 | 마이페이지 |
| :---: | :---: |
|  |  |

|  게시글 작성  | 댓글 작성 |
| :---: | :---: |
|  |  |

| 자랑 게시판 | 마커 그리기 |
| :---: | :---: |
|  |  |

| 매칭 요청하기 | 매칭 요청 수락하기 |
| :---: | :---: |
|  |  |

| 친구 요청하기 | 친구 요청 수락하기  |
| :---: | :---: |
|  |  |

<br>
