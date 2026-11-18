param([string]$Version = '0.2.0-beta.2')
$ErrorActionPreference = 'Stop'
Set-Location (Split-Path $PSScriptRoot -Parent)
$morphe = Join-Path $PWD 'tools\morphe-desktop.jar'
$classes = Join-Path $PWD "build\$Version-cast-complete\cast-classes"
New-Item -ItemType Directory -Force build\cast-tests | Out-Null
& javac --release 11 -cp $classes -d build\cast-tests scripts\CastWireTest.java
if ($LASTEXITCODE -ne 0) { throw 'No compila el test de Cast.' }
& java -cp "build\cast-tests;$classes" CastWireTest
if ($LASTEXITCODE -ne 0) { throw 'Fallan las pruebas del protocolo Cast.' }
& tools\kotlin-current\kotlinc\bin\kotlinc.bat scripts\verify-cast-bundle.kt -classpath $morphe -jvm-target 11 -d build\cast-tests\bundle-tests.jar
if ($LASTEXITCODE -ne 0) { throw 'No compila el test del paquete.' }
& java -cp "build\cast-tests\bundle-tests.jar;$morphe" Verify_cast_bundleKt "dist\lunar-edge-cast-complete-$Version.mpp"
if ($LASTEXITCODE -ne 0) { throw 'El paquete de prueba no cumple sus requisitos.' }
