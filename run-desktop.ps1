# PowerShell script to start the Angular Desktop Application
Set-Location "$PSScriptRoot\frontend"

Write-Host "Starting Angular Frontend on http://localhost:4200 ..." -ForegroundColor Cyan
npm start
