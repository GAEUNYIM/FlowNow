# djge
몰입캠프 Week 1 과제로 'Tab 3개를 수행하는 어플리케이션'을 Android Studio 에서 구현하였다.  

## Team information
#### Members : 이동재, 임가은

## Description
< TODO : 탭 세 개의 screenshots>
### (Tab 1) "Contacts"
##### 디바이스에 저장된 연락처 목록을 보여주는 탭
디바이스의 로컬 연락처 저장소에 접근하여 연락처 정보를 불러온다. 스크롤하여 Name, PhoneNumber, ThumbNail을 볼 수 있고, 화면 새로 고침이 가능하다.



### (Tab 2) "Gallery"
##### 디바이스에 저장된 이미지들을 보여주는 탭
디바이스의 로컬 갤러리 저장소에 접근하여 이미지 정보를 불러온다. 스크롤하여 사진 PreView를 볼 수 있고, 화면 새로 고침이 가능하다. 사진을 꾹 누를 경우 팝업창이 뜨면서 사진을 삭제할 수 있다.


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
  4. DONE 버튼을 누르면 나의 Flow Score(몰입 점수)를 알 수 있다
  5. SHOW SCORELIST 버튼을 누르면 Database에 저장된 점수 목록을 팝업창을 통해서 확인할 수 있다.
 
##### 구현 방법
  1. Canvas를 사용해 그림을 그리고 저장할 수 있도록 했다. 
  2. 디바이스의 가속도 센서에서 값을 받아와 이를 Ink Cursor의 속도벡터값과 위치벡터값을 업데이트하는데 사용한다. 
  3. 사진 선택은 Recyclerview의 Adapter 내부에서 OnClickListener를 사용하여 구현했다.  
  4. SQLite를 사용해 Database를 디바이스에 구축하여 Score를 저장했다.


## Implementation
### Tap1
  1. SQLite를 통해 불러온 연락처 정보를 Recyclerview로 화면에 보여준다.  
  2. 새로고침은 MainActivity에서 Fragment를 재시작하는 것으로 구현했다.  
### Tap2
  1. SQLite를 통해 불러온 이미지를 Recyclerview로 화면에 보여준다.  
  2. 사진의 처리는 glide를 사용하여 구현하였다.  
  3. 사진을 꾹 누르는 터치는 사진을 띄우는 Recyclerview의 Adapter 내부에서 ContextMenuListener를 사용하여 구현했다.  
  4. 새로고침은 MainActivity에서 Fragment를 재시작하는 것으로 구현했다.  
  
### Tap3  
  1. Canvas를 사용해 그림을 그리고 저장할 수 있도록 했다. 
  2. 디바이스의 가속도 센서에서 값을 받아와 이를 Ink Cursor의 속도벡터값과 위치벡터값을 업데이트하는데 사용한다. 
  3. 사진 선택은 Recyclerview의 Adapter 내부에서 OnClickListener를 사용하여 구현했다.  
  4. SQLite를 사용해 Database를 디바이스에 구축하여 Score를 저장했다.  
  5. 점수 계산은 Bitmap을 Array로 변환한 후 cosine similarity를 계산해 점수로 변환한다.  
  ```c
  private class accListener implements SensorEventListener{
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (isGmode) {
                //Log.i("SENSOR", "Acceleration changed.");
                //Log.i("SENSOR", "  X: " + paintvector[0]
                //        + ", Y: " + paintvector[1]);
                float accx = -event.values[0];
                float accy = event.values[1];
                paintvector[0] += accx*0.18*acc;
                paintvector[1] += accy*0.18*acc;
                if(paintvector[0]<0) {
                    paintvector[0] = 0;
                }
                else if(paintvector[0]>1000){
                    paintvector[0]=1000;
                }
                if(paintvector[1]<0){
                    paintvector[1] = 0;
                }
                else if(paintvector[1]>800){
                    paintvector[1] = 800;
                }
                Paint p = new Paint();
                p.setColor(color);
                p.setStrokeWidth(lineWith);
                points.add(new PaintPoint(paintvector[0], paintvector[1], true, p));
                cursor.clear();
                Paint cursorp = new Paint();
                cursorp.setColor(color);
                cursorp.setStrokeWidth(cursorwith);
                cursor.add(new PaintPoint(paintvector[0]-10, paintvector[1]-10, true, cursorp));
                cursor.add(new PaintPoint(paintvector[0]+10, paintvector[1]+10, true, cursorp));
                invalidate();
            }
        }
  ```  
  ```c
  public double GetCosineSimilarity(Integer[] array1, Integer[] array2){
        if(array1.length!= array2.length){
            Log.e("Diff Lengtherror", "error occured");
            return (float)0.0;
        }
        double dotproduct = 0;
        double array1rs = 0;
        double array2rs = 0;
        double cos = 0;
        double argb1 = 0;
        double argb2 = 0;
        for(int i=0; i<array1.length; i++){
            for(int j=0;j<3;j++){
                argb1 = (double) ((array1[i]&(0x000000ff<<(j*2*8)))>>(j*2*8));
                argb2 = (double) ((array2[i]&(0x000000ff<<(j*2*8)))>>(j*2*8));
                if(i%10000==0) Log.e("IMAGE VALUE", Double.toString(argb1)+" "+Double.toString(argb2));
                argb1 /= (double) 256;
                argb2 /= (double) 256;
                argb1 = 1-argb1;
                argb2 = 1-argb2;
                array1rs += argb1;
                array2rs += argb2;
                dotproduct += argb1*argb2;
            }

        }
        array1rs = Math.sqrt(array1rs);
        array2rs = Math.sqrt(array2rs);
        cos = (double)dotproduct / (array1rs * array2rs);
        return cos;
    }
  ```    
## 개선할 점
#### 1. Score 계산 문제 해결
* cosine similarity 대신 phase correlation 혹은 opencv를 활용하여 이미지의 유사도 측정  
* 측정한 유사도를 Score로 변환하는 식 제작하기  
#### 2. 서버를 활용한 데이터베이스로 변경
* 2주차에서 배운 내용을 바탕으로 개선할 예정


## Environment
#### 개발 환경 : Android Studio ide - Window
#### 디바이스 정보 : Galaxy S10e (Android OS 10)
#### 화면 크기 : 5.8인치 (2280 x 1080) 
#### Appication 용량 : 6.08MB  
  
## Prerequisite
  코드를 실행하기 전에 설치해야 할 package나 의존성 문제
  
## Files
  중요한 파일을 대상으로 해당 파일이 어떤 역할을 하는 파일인지 간단히 설명
  
## Usage
  작성한 코드를 어떻게 실행해야 하는지에 대한 가이드 라인
