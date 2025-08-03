# Spring Boot 애플리케이션 시작 스크립트 (JDK 24 사용)
Write-Host "Spring Boot 애플리케이션을 JDK 24로 시작합니다..." -ForegroundColor Green

# JDK 24 환경변수 설정
$env:JAVA_HOME = "C:\Program Files\Java\jdk-24"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

Write-Host "JAVA_HOME: $env:JAVA_HOME" -ForegroundColor Yellow
Write-Host "Java Version:" -ForegroundColor Yellow
java -version

Write-Host "NAVER_CLIENT_ID: $env:NAVER_CLIENT_ID" -ForegroundColor Yellow
Write-Host "NAVER_CLIENT_SECRET: $env:NAVER_CLIENT_SECRET" -ForegroundColor Yellow

# Spring Boot 애플리케이션 시작
Write-Host "Spring Boot 애플리케이션을 시작합니다..." -ForegroundColor Green
.\gradlew.bat bootRun 