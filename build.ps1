param()
$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot

# Las herramientas se guardan en el proyecto; no se necesitan credenciales de GitHub.
$compiler = Join-Path $PSScriptRoot 'tools\kotlin-current\kotlinc\bin\kotlinc.bat'
$morphe = Join-Path $PSScriptRoot 'tools\morphe-desktop.jar'
$jadx = Join-Path $PSScriptRoot 'tools\jadx\lib\jadx-1.5.6-all.jar'
foreach ($toolPath in @($compiler, $morphe, $jadx)) {
    if (-not (Test-Path -LiteralPath $toolPath)) { throw "Falta la herramienta: $toolPath. Consulta README.md." }
}
New-Item -ItemType Directory -Force build\classes, build\dex, dist | Out-Null
& $compiler 'src\main\kotlin\lunar-edge-patches.kt' -classpath $morphe -jvm-target 11 '-Xlambdas=class' -d 'build\classes'
if ($LASTEXITCODE -ne 0) { throw 'Falló la compilación Kotlin.' }

$manifest = @"
Manifest-Version: 1.0
Name: Lunar Edge local patches
Description: Parches locales para los anuncios del reproductor
Version: 0.1.0
Author: Lunar Edge local patches
Source: https://github.com/adriviciano/morphe-patch-lunar-edge
Website: https://github.com/adriviciano/morphe-patch-lunar-edge
License: GPL-3.0-only

"@
[IO.File]::WriteAllText((Join-Path $PSScriptRoot 'build\MANIFEST.MF'), $manifest, [Text.UTF8Encoding]::new($false))
& jar --create --file 'dist\lunar-edge-patches-0.1.0.mpp' --manifest 'build\MANIFEST.MF' -C 'build\classes' .
if ($LASTEXITCODE -ne 0) { throw 'Falló la creación del paquete.' }

# Se incluye DEX para Morphe Manager y clases JVM para Morphe Desktop.
Copy-Item -LiteralPath 'dist\lunar-edge-patches-0.1.0.mpp' -Destination 'build\patches.jar' -Force
& java -cp $jadx com.android.tools.r8.D8 --release --min-api 26 --classpath $morphe --output 'build\dex' 'build\patches.jar'
if ($LASTEXITCODE -ne 0) { throw 'Falló la conversión DEX.' }
& jar --update --file 'dist\lunar-edge-patches-0.1.0.mpp' -C 'build\dex' classes.dex
if ($LASTEXITCODE -ne 0) { throw 'Falló la inclusión de classes.dex.' }
Write-Host 'Creado dist\lunar-edge-patches-0.1.0.mpp'
