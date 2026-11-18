# Lunar Edge: parches locales de Morphe

Paquete experimental para **Lunar Edge 4.0.2 (40002)**, paquete `com.lunaredge.browser`.

## Archivos preparados

- `dist/lunar-edge-patches-0.1.1.mpp`: paquete para Morphe, con clases JVM y `classes.dex` para Android.
- `dist/lunar-edge-4.0.2-patched.apk`: APK fusionada y firmada con una clave local de pruebas.
- `dist/patch-result.json`: resultado de la aplicación de los parches.

La APK proporcionada contiene librerías **ARMv7 (armeabi-v7a)**. No incluye ARM64; los teléfonos que solo admiten aplicaciones de 64 bits necesitan otro XAPK de origen.

## Qué modifica

| Parche | Cambio |
| --- | --- |
| Remove player video ads | Omite las rutas de anuncios de los reproductores principal y local usando la continuación que ya usa la app cuando no hay anuncio. |
| Remove player display ads | Impide cargar intersticiales de la posición 16 y anuncios nativos superpuestos del reproductor principal. |

Cada modificación verifica clase, nombre, parámetros y una llamada del cuerpo original antes de aplicarse. Si la versión cambia, hay que analizarla de nuevo. No se modifica la suscripción ni se simulan recompensas o respuestas de servidores. Los anuncios de otras pantallas quedan fuera del alcance.

## Uso

[**Añadir esta fuente a Morphe**](https://morphe.software/add-source?github=adriviciano/morphe-patch-lunar-edge)

También puedes añadir manualmente la fuente de GitHub `adriviciano/morphe-patch-lunar-edge` desde Morphe. Selecciona tu APK/XAPK original de Lunar Edge 4.0.2 y activa los dos parches. El paquete `.mpp` está en [Releases](https://github.com/adriviciano/morphe-patch-lunar-edge/releases/latest).

Para fuentes que soliciten directamente la URL de metadatos:

```text
https://raw.githubusercontent.com/adriviciano/morphe-patch-lunar-edge/main/patches-bundle.json
```

También puedes aplicar el paquete con Morphe Desktop:

```powershell
java -jar tools\morphe-desktop.jar patch -p dist\lunar-edge-patches-0.1.1.mpp -o dist\lunar-edge-4.0.2-patched.apk 'Lunar+Edge_4.0.2_APKPure.xapk'
```

También puedes probar la APK ya generada. Está firmada con una clave diferente a la original: Android no permitirá instalarla encima de la versión de APKPure. Conserva los datos que necesites antes de desinstalar la original. Para futuras actualizaciones locales conserva la misma clave de firma. La clave incluida en `dist` usa la contraseña de pruebas `android`; no se debe publicar.

## Compilar y verificar

Requiere Java/JDK 21 y PowerShell. Desde la raíz del proyecto:

```powershell
.\scripts\setup-tools.ps1
.\build.ps1
```

Las herramientas se descargan de sus repositorios oficiales con versiones y SHA256 fijados; no hace falta un token de GitHub. La compilación usa la API incluida en Morphe Desktop 2.2.0 y D8 incluido en JADX 1.5.6.

Después de generar una APK firmada:

```powershell
.\scripts\verify.ps1
```

La verificación inspecciona las seis modificaciones DEX, sus registros de llamada, la firma de la APK, las librerías nativas y la presencia de las rutas DLNA. **No sustituye una prueba en Android.** Aún hay que comprobar abrir un vídeo, pausa/reanudación, cambio de episodio y anuncios durante reproducción en un teléfono.

## Envío a TV

La APK original ya incluye DLNA/UPnP, búsqueda de receptores y botones de envío en ambas orientaciones del reproductor principal. `transformRules()` y `willWindow()` abren los selectores y envían la URL del vídeo mediante `DeviceControl`. Los parches conservan estas rutas.

No se ha añadido Google Cast. Para dispositivos Chromecast/Google TV hace falta integrar un emisor Google Cast y sus dependencias, o delegar en una aplicación de envío instalada en Android. DLNA por sí solo no ofrece compatibilidad con cualquier Chromecast. La TV debe poder acceder a la URL y admitir el formato del vídeo; compartir red no garantiza compatibilidad con todas las fuentes.

## Referencias

- [Morphe Patcher](https://github.com/MorpheApp/morphe-patcher)
- [Plantilla oficial](https://github.com/MorpheApp/morphe-patches-template)
- [Morphe Desktop](https://github.com/MorpheApp/morphe-desktop)

No se redistribuyen las herramientas ni el XAPK en el repositorio. Este proyecto no está afiliado a Morphe ni a Lunar Edge.

## Publicación

Después de compilar, `scripts/generate-metadata.ps1` genera `patches-list.json` y `patches-bundle.json` desde el paquete. La etiqueta `v0.1.1` ejecuta GitHub Actions, vuelve a compilar y publica el `.mpp`, los metadatos y su SHA256 en Releases. No se suben APK, XAPK ni claves locales de firma.
