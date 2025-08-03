# Gradle 빌드 스크립트 (JDK 17 사용)
Write-Host "Gradle 빌드를 JDK 17로 실행합니다..." -ForegroundColor Green

# JDK 17 환경변수 설정
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

Write-Host "JAVA_HOME: $env:JAVA_HOME" -ForegroundColor Yellow
Write-Host "Java Version:" -ForegroundColor Yellow
java -version

# Gradle 빌드 실행
Write-Host "Gradle 빌드를 시작합니다..." -ForegroundColor Green
.\gradlew.bat build 