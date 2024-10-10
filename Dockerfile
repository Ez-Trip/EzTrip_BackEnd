# OpenJDK 17을 기반 이미지로 사용
FROM openjdk:22-jdk-slim

# 컨테이너 내부의 작업 디렉토리 설정
WORKDIR /app

# 로컬에서 생성한 JAR 파일을 컨테이너로 복사
COPY build/libs/*.jar app.jar

ENV JASYPT_ENCRYPTOR_PASSWORD="mySuperSecretPassword123!"

# Spring Boot가 사용하는 포트 열기
EXPOSE 8080

# Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

