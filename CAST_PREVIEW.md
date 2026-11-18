# Google Cast · beta 3: diagnóstico de carga

La beta 2 ya encuentra la Android TV del usuario, pero el receptor devuelve `LOAD_FAILED` sin código. Eso confirma que la nueva ruta Cast se ejecuta; todavía no identifica la causa de la carga fallida. Esta beta añade un informe copiable y no afirma corregir la reproducción.

## Prueba desde el móvil

1. Descarga [lunar-edge-cast-complete-0.2.0-beta.3.mpp](https://github.com/adriviciano/morphe-patch-lunar-edge/releases/download/v0.2.0-beta.3/lunar-edge-cast-complete-0.2.0-beta.3.mpp).
2. Añádelo como fuente local **Lunar Edge Cast Complete beta 3**. Para esta prueba utiliza solo esa fuente.
3. Selecciona el APK/XAPK original de Lunar Edge **4.0.2**, con los cuatro parches seleccionados, incluido **Fix local video engine**. No partas de una APK con Cast ya aplicado.
4. Parchea, instala, carga el mismo vídeo y pulsa emitir. Debe mostrar **Google Cast · beta 3**.
5. Selecciona la TV. Cuando aparezca el fallo, sin cerrar los controles pulsa **Diagnóstico de carga**. Espera y pulsa **Copiar informe**; pégalo en el chat.

El informe realiza peticiones HTTP con Range, limita la muestra a 4096 bytes y muestra código HTTP, MIME, clasificación HLS/MP4/HTML/JSON, presencia de CORS, referencias localhost y cifrado declarado en la muestra HLS. Si la fuente es local, compara la dirección original con la dirección LAN enviada a la TV. No vuelca URL, tokens, títulos, contenido, IP ni cabeceras privadas.

Las peticiones de diagnóstico parten del móvil: un resultado HTTP correcto no demuestra que la TV pueda acceder a la fuente ni que acepte el formato. Se conserva el mismo comportamiento Cast de la beta 2 para observar el fallo sin cambiar varias variables a la vez.

## Estado

Se mantienen los cuatro parches predeterminados y la dependencia obligatoria de la reparación del motor. La estable sigue siendo **0.1.2**. En pruebas anteriores, la Grundig reprodujo y pausó un MP4 servido desde el móvil, pero sigue pendiente reproducir las fuentes reales de Lunar Edge. La beta 3 prueba el diagnóstico con respuestas HTTP/HLS/MP4 controladas y verifica que el informe no exponga URL ni tokens.
