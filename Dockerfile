# 1단계: 빌드 단계
FROM gradle:7.6-jdk17 AS builder
WORKDIR /home/gradle/project
COPY . .
#RUN gradle clean build --no-daemon
# 테스트용
RUN gradle clean build -x test --no-daemon

# 2단계: 실행 단계
FROM bellsoft/liberica-openjdk-alpine:17
WORKDIR /app
# builder 단계에서 생성된 jar 파일을 복사합니다.
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


