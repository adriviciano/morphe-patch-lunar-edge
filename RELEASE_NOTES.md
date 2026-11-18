**Google Cast · beta 2 completa** para Lunar Edge **4.0.2 (40002)**. La estable **0.1.2** se mantiene sin cambios.

## Corrección de esta beta

El paquete Cast de la beta 1 contenía un solo parche y necesitaba una APK que ya tuviese el motor corregido. La beta 2 completa incorpora **los cuatro parches, activados por defecto**, y Cast depende obligatoriamente de **Fix local video engine**. Evita que aplicar solamente Cast al original vuelva a dejar el motor sin corregir.

La corrección del motor acepta también exactamente las bibliotecas que ya había corregido, comprobando el SHA256 del original reconstruido. Las variantes desconocidas se siguen rechazando.

## Prueba desde el móvil

1. Descarga **lunar-edge-cast-complete-0.2.0-beta.2.mpp** y añádelo como fuente local en Morphe: **Lunar Edge Cast Complete beta 2**.
2. Selecciona el **APK/XAPK original de Lunar Edge 4.0.2** y utiliza solo esa fuente. El resumen debe mostrar **4 parches seleccionados**.
3. Instala y comprueba primero que el vídeo carga en el móvil. Después pulsa emitir: el título debe ser **Google Cast · beta 2**.
4. Si la TV no aparece, prueba **Introducir IP** usando la dirección de sus ajustes de red.

[Instrucciones completas](https://github.com/adriviciano/morphe-patch-lunar-edge/blob/codex/google-cast/CAST_PREVIEW.md).

## Validación y límites

Comprobadas aplicación de los cuatro parches desde el XAPK original ARMv7, aplicación desde una entrada ARM64 ya corregida, instrucciones nativas, dos botones Cast, alternativas DLNA, firma válida y ausencia de diagnóstico temporal. Pruebas del protocolo y de dependencia obligatoria del motor superadas.

Reproducción y pausa de un vídeo local de prueba confirmadas previamente en una Grundig Android TV. **Todavía pendiente:** la integración completa con las fuentes reales de Lunar Edge y la reproducción prolongada con pantalla apagada. No se incluyen APK ni claves de firma.
