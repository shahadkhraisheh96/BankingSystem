# PowerShell script to start the BankingSystem Spring Boot backend
$javaBin = "C:\Users\shaha\.jdks\openjdk-25.0.2\bin\java.exe"

if (-not (Test-Path $javaBin)) {
    Write-Error "JDK 25 not found at $javaBin"
    exit 1
}

Write-Host "Assembling classpath dependencies from .m2 repository..." -ForegroundColor Cyan
$jars = (Get-ChildItem -Path "$HOME\.m2\repository" -Filter "*.jar" -Recurse | Where-Object { $_.FullName -notmatch "-sources|-javadoc|1\.7\.36" } | ForEach-Object { $_.FullName }) -join ";"
$classpath = "target\classes;$jars"

Write-Host "Launching BankingSystem Spring Boot backend on http://localhost:8080 ..." -ForegroundColor Green
& $javaBin -cp $classpath com.prograssoft.shahad.Main
