# djge
몰입캠프 Week 1 과제로 'Tab 3개를 수행하는 어플리케이션'을 Android Studio 에서 구현하였다.
Tab 1과 Tab 2는 공통과제이고, Tab3은 자유과제이다.

## Team information
#### Members : 이동재, 임가은

## Description
< TODO : 탭 세 개의 screenshots>
### (Tab 1) "Contacts"
##### 디바이스에 저장된 연락처 목록을 보여주는 탭
디바이스의 로컬 연락처 저장소에 접근하여 연락처 정보를 불러온다. 스크롤하여 Name, PhoneNumber, ThumbNail을 볼 수 있고, 화면 새로 고침이 가능하다.
### (Tab 2) "Gallery"
##### 디바이스에 저장된 이미지들을 보여주는 탭
디바이스의 로컬 갤러리 저장소에 접근하여 이미지 정보를 불러온다. 스크롤하여 사진 PreView를 볼 수 있고, 화면 새로 고침이 가능하다.
### (Tab 3) "Flow"
##### 마음에 드는 이미지에 몰입해 보는 Game
Tab 2에서 구현한 갤러리로부터 마음에 드는 이미지를 불러온다. 휴대폰을 기울이기를 통해 화면의 Ink Cursor를 움직이면서 최대한 비슷하게 이미지를 따라 그리면 성공! < TODO : Tab3 에 대한 이미지 첨부>
###### 게임 방법
  1. 상단의 Gallery 버튼을 눌러서 마음에 드는 이미지를 선택한다
  2. 불러온 사진의 투명도를 조절한다
  3. Start Flow 버튼을 눌러 게임을 시작한다
   ```c 
   + Stop 버튼을 누르면 Ink Cursor가 멈춘다
   + Reset 버튼을 누르면 그렸던 그림이 모두 지워진다
   + Canvas의 임의의 위치를 클릭하여 Ink Cursor의 위치를 재설정 할 수 있다
   ```
  4. Done 버튼을 누르면 나의 Flow Score(몰입 점수)를 알 수 있다
  5. Flow Score가 90점 이상이면 이미지가 갤러리에 저장된다
 
## Environment
#### 개발 환경 : Android Studio ide - Window
#### 디바이스 정보 : Galaxy S10e (Android OS 10)
#### 화면 크기 : 5.8인치 (2280 x 1080) 
#### Appication 용량 : 6.08MB  
  
## Prerequisite
  코드를 실행하기 전에 설치해야 할 pakage나 의존성 문제
  
## Files
  중요한 파일을 대상으로 해당 파일이 어떤 역할을 하는 파일인지 간단히 설명
  
## Usage
  작성한 코드를 어떻게 실행해야 하는지에 대한 가이드 라인
