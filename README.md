# android_project

## 다이어트 앱 개발


### 구현 방식

#### input interface
- 밑에 +버튼: floating으로 만들어서 식단 입력할 수 있게 만들기
- 식단: 사진, 식사명, 음식수량, 칼로리, 평, 날짜, 시간(아침, 점심, 저녁, 간식인지 구분)
- 사진은 갤러리에서 가져오도록 구현
- 식사 장소는 구글 맵 api사용 
- DB에 정보를 저장하고 가져올 수 있게 만들기
- 날짜를 선택 -> 그 날짜에 맞는 정보 불러오기


#### main interface
- 총 칼로리, 입력한 식사정보 창 화면에 띄우기  
- 식사를 입력하면 추가한 순서대로 이미지,식사시간, 칼로리 보여주기 
- 이미지 클릭했을 때 상세정보 보여주기
- 상세내용은 DB에서 정보를 가져와서 RecyclerView 이용


## Todo App
#### 1. App 디자인
![image](https://user-images.githubusercontent.com/86701879/208557556-81551309-3daf-4d22-b5c6-8370d0c9a4a3.png)

#### 2. 메인화면
![image](https://user-images.githubusercontent.com/86701879/208558223-4592b001-fcf4-4c5a-ab37-166f30a5dcfa.png)

#### 3. input 화면(이미지 선택)
![image](https://user-images.githubusercontent.com/86701879/208558244-e7c855fe-5941-4d95-b2fa-7db9ace98aa2.png)

![image](https://user-images.githubusercontent.com/86701879/208557617-ed5a17a0-06d9-4864-8066-66aef7fe58a7.png)

#### 4. 지도 사용
![image](https://user-images.githubusercontent.com/86701879/208557631-9301ab70-78df-4589-832d-a108c521daf4.png)

#### 5. 상세내용
![image](https://user-images.githubusercontent.com/86701879/208558052-cab2bb69-aedc-45a9-a003-8451733fc7ab.png)

![image](https://user-images.githubusercontent.com/86701879/208557659-1fb88908-edc7-486a-92af-5fadf8c99db7.png)

#### 6. 캘린더
![image](https://user-images.githubusercontent.com/86701879/208557697-63f4d005-a5ee-4f0c-9eff-aa22d7475686.png)


### 개발툴
- Android Studio: java
- DB: Room
