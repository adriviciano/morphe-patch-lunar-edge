$ErrorActionPreference = 'Stop'
Set-Location (Split-Path $PSScriptRoot -Parent)
$morphe = Join-Path $PWD 'tools\morphe-desktop.jar'
& 'tools\kotlin-current\kotlinc\bin\kotlinc.bat' scripts\verify-patched-apk.kt -classpath $morphe -jvm-target 11 -d build\verification.jar
if ($LASTEXITCODE -ne 0) { throw 'No se pudo compilar el verificador.' }
& java -cp "build\verification.jar;$morphe" Verify_patched_apkKt dist\lunar-edge-4.0.2-patched.apk
if ($LASTEXITCODE -ne 0) { throw 'La APK no supera la verificación.' }
