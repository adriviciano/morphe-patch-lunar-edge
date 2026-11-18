# Lunar Edge: parches locales de Morphe

Paquete experimental para **Lunar Edge 4.0.2 (40002)**, paquete `com.lunaredge.browser`.

## Google Cast en pruebas

La versión estable sigue siendo **0.1.2**. La [beta 0.2.0-beta.1](https://github.com/adriviciano/morphe-patch-lunar-edge/releases/tag/v0.2.0-beta.1) añade una prueba separada de Google Cast. [Instrucciones desde el móvil](CAST_PREVIEW.md). La fuente local **Lunar Edge Cast Preview beta 1** contiene únicamente el parche Cast seleccionado por defecto y muestra el título **Google Cast · beta 1**.

## Archivos preparados

- `dist/lunar-edge-patches-0.1.2.mpp`: paquete para Morphe, con clases JVM y `classes.dex` para Android.
- `dist/lunar-edge-4.0.2-patched.apk`: APK fusionada y firmada con una clave local de pruebas.
- `dist/patch-result.json`: resultado de la aplicación de los parches.

La APK proporcionada contiene librerías **ARMv7 (armeabi-v7a)**. No incluye ARM64; los teléfonos que solo admiten aplicaciones de 64 bits necesitan otro XAPK de origen.

## Qué modifica

| Parche | Cambio |
| --- | --- |
| Fix local video engine | Corrige el rechazo local de la nueva firma en el motor nativo, que provocaba un falso error de red o servidor. ARM64 y ARMv7 con SHA256 exacto. |
| Remove player video ads | Omite las rutas de anuncios de los reproductores principal y local usando la continuación que ya usa la app cuando no hay anuncio. |
| Remove player display ads | Impide cargar intersticiales de la posición 16 y anuncios nativos superpuestos del reproductor principal. |

Las modificaciones DEX verifican clase, nombre, parámetros y una llamada del cuerpo original. La corrección nativa verifica el SHA256 completo y la instrucción original antes de cambiar la rama de rechazo por la firma. Si la versión cambia, hay que analizarla de nuevo. No se modifica la suscripción ni se simulan recompensas o respuestas de servidores. Los anuncios de otras pantallas quedan fuera del alcance.

## Uso

[**Añadir esta fuente a Morphe**](https://morphe.software/add-source?github=adriviciano/morphe-patch-lunar-edge)

También puedes añadir manualmente la fuente de GitHub `adriviciano/morphe-patch-lunar-edge` desde Morphe. Selecciona tu APK/XAPK original de Lunar Edge 4.0.2 y activa los tres parches. El paquete `.mpp` está en [Releases](https://github.com/adriviciano/morphe-patch-lunar-edge/releases/latest).

Para fuentes que soliciten directamente la URL de metadatos:

```text
https://raw.githubusercontent.com/adriviciano/morphe-patch-lunar-edge/main/patches-bundle.json
```

También puedes aplicar el paquete con Morphe Desktop:

```powershell
java -jar tools\morphe-desktop.jar patch -p dist\lunar-edge-patches-0.1.2.mpp -o dist\lunar-edge-4.0.2-patched.apk 'Lunar+Edge_4.0.2_APKPure.xapk'
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

La verificación inspecciona las seis modificaciones DEX, sus registros de llamada, la firma de la APK, la corrección nativa, la ausencia de diagnóstico temporal y las rutas DLNA. Se ha confirmado reproducción real en un móvil ARM64 después de corregir el motor; la prueba original mostraba `enginePort=-1` y la corregida `enginePort=7000`, con respuesta de vídeo válida. ARMv7 se ha verificado estáticamente. Aún quedan por probar las distintas fuentes de vídeo, pausa/reanudación, cambio de episodio y envío a TV.

Si usaste los paquetes locales de diagnóstico durante las pruebas, desactiva esas fuentes y vuelve a partir del APK/XAPK original para obtener una instalación sin instrumentación temporal.

## Envío a TV

La APK original ya incluye DLNA/UPnP, búsqueda de receptores y botones de envío en ambas orientaciones del reproductor principal. `transformRules()` y `willWindow()` abren los selectores y envían la URL del vídeo mediante `DeviceControl`. Los parches conservan estas rutas.

La estable 0.1.2 no añade Google Cast. La beta incorpora un emisor Cast V2 con descubrimiento NSD, selector de TV y controles de reproducción, pausa, posición y volumen. Permite introducir una IP local cuando falla el descubrimiento y prioriza IPv4 cuando Android ofrece varias direcciones. Conserva el selector DLNA como alternativa. La reproducción y pausa de un vídeo de prueba se han confirmado en una Android TV; sigue pendiente validar las fuentes reales de Lunar Edge dentro de la app y el funcionamiento con pantalla apagada. No utiliza código ni bibliotecas de PyChromecast dentro de la APK.

## Referencias

- [Morphe Patcher](https://github.com/MorpheApp/morphe-patcher)
- [Plantilla oficial](https://github.com/MorpheApp/morphe-patches-template)
- [Morphe Desktop](https://github.com/MorpheApp/morphe-desktop)

No se redistribuyen las herramientas ni el XAPK en el repositorio. Este proyecto no está afiliado a Morphe ni a Lunar Edge.

## Publicación

La compilación de la beta requiere además Android SDK Platform 35 (`ANDROID_HOME`). `build.ps1 -CastPreview` genera el paquete de prueba de un único parche; `scripts/test-cast.ps1` verifica el protocolo y que el parche esté seleccionado por defecto. Los directorios de compilación están separados por versión y modalidad.

Después de compilar, `scripts/generate-metadata.ps1` genera `patches-list.json` y `patches-bundle.json` desde el paquete completo. Las etiquetas ejecutan GitHub Actions, que publica los paquetes, metadatos y SHA256 en Releases. Las versiones con sufijo se publican como prerelease y no sustituyen la estable. No se suben APK, XAPK ni claves locales de firma.
