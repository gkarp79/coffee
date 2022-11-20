# beanshop

1. Gradle 프로젝트 업데이트

2.더블클릭   
![image](https://user-images.githubusercontent.com/69612644/201860536-526d56b1-8a71-4087-8d78-9ea46bf855fe.png)


3. 파일 추가 확인  
![image](https://user-images.githubusercontent.com/69612644/201860790-3476ad26-aaeb-4e79-8bfc-79a346b55287.png)


4. application.yml 에서 Create 및 데이터베이스 연결 개인 아이디 비밀 번호로 변경  

5. 해당 경로에 맞게 폴더 생성  
![image](https://user-images.githubusercontent.com/69612644/201861262-dd2b86fe-c1df-4856-aa6f-c6aee633b94e.png)

6. 데이터베이스 MYSQL에 스키마 bean 생성  
 
7. 실행  

======================================test 설정 =========================================  
![image](https://user-images.githubusercontent.com/69612644/202057828-6850f1df-54e2-4c2a-a1f3-22ad84998887.png)  
Run 'Tests in 'coffee.test"를 실행하면 우측 상단에 Configuration이 생깁니다.   
![image](https://user-images.githubusercontent.com/69612644/202058001-a2b93935-3c34-4fae-919d-ad84cdd4bcaf.png)  
Edit Configurations에 들어가면 Gradle에 'Tests in 'coffee.test"가 있습니다.  
![image](https://user-images.githubusercontent.com/69612644/202058123-5603f527-717b-4fb1-b59c-76d69179a694.png)  
Modify options에 Add VM options 하면 새로 input이 생기는데  
![image](https://user-images.githubusercontent.com/69612644/202058226-85baf328-7d96-4454-9d60-e11740eb32bb.png)  
그곳에 -Dfile.encoding="UTF-8" 추가 해주시면 Test도 정상 동작합니다.  
