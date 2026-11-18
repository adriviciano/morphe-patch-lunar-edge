# Google Cast · beta 2 completa

La beta 1 solo añadía Cast y dependía de una APK de entrada ya corregida. Si se aplicaba al original, no arreglaba el rechazo de firma del motor local y podía reaparecer el falso error de servidor. La beta 2 completa incluye los cuatro parches y Cast depende obligatoriamente de la corrección del motor.

## Instalar desde el móvil

1. Descarga [lunar-edge-cast-complete-0.2.0-beta.2.mpp](https://github.com/adriviciano/morphe-patch-lunar-edge/releases/download/v0.2.0-beta.2/lunar-edge-cast-complete-0.2.0-beta.2.mpp).
2. En Morphe, añade el archivo como fuente local: **Lunar Edge Cast Complete beta 2**.
3. Selecciona el **APK/XAPK original de Lunar Edge 4.0.2** desde almacenamiento. Utiliza solo la fuente nueva para esta prueba.
4. El resumen debe mostrar **4 parches**, todos seleccionados: **Fix local video engine**, **Remove player video ads**, **Remove player display ads** y **Enable Google Cast (experimental)**.
5. Parchea e instala con Morphe. Primero comprueba que el vídeo carga en el móvil; después pulsa emitir.

El selector debe mostrar **Google Cast · beta 2**. No hacen falta los paquetes de diagnóstico ni el archivo de entrada especial de las pruebas anteriores. Si la APK ya tenía Cast aplicado, vuelve a partir del original: no se aplica Cast dos veces.

## Qué comprobar

- Que el vídeo carga primero en el móvil, sin el falso error de servidor.
- Que al emitir aparece el título **Google Cast · beta 2** y, tras unos 12 segundos, la Android TV.
- Si no aparece, pulsa **Introducir IP**, con la dirección actual de la TV consultada en sus ajustes de red. Esto separa el descubrimiento de la conexión.
- Si la TV abre el reproductor y carga el vídeo, prueba reproducción, pausa y avance.

El móvil y la TV deben estar en la misma red Wi-Fi. Para recuperar el selector antiguo se mantiene **Otros televisores (DLNA)**.

## Validación y límites

El protocolo se ha contrastado con un decodificador protobuf independiente. Una Grundig Android TV confirmó reproducción y pausa de un MP4 de prueba servido desde el móvil. Se verifica que los dos botones de emitir abren el selector nuevo y que el motor está corregido en la APK resultante, tanto desde el XAPK original ARMv7 como desde una entrada ARM64 ya corregida.

Sigue pendiente confirmar manualmente el descubrimiento y las fuentes reales de Lunar Edge dentro de la app, la reproducción prolongada con pantalla apagada, los subtítulos y el cambio de episodio. La estable sigue siendo 0.1.2; esta beta no la sustituye.
