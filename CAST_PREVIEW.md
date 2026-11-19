# Prueba 0.2.1-beta.1: cortes de emisión

La estable continúa en **0.2.0**. Esta versión se publica por separado para comprobar una corrección del traspaso a la TV.

## Hallazgos

- El parche 0.2.0 llamaba a `ExoUserPlayer.O()` al recibir PLAYING de la TV. En la APK original, O llama a R y libera el reproductor local. La intención era pausarlo. Esta prueba usa L, que conserva el reproductor y pone `playWhenReady=false`. El parche verifica esa función antes de aplicarse.
- Una prueba independiente de 42 segundos con el puente 0.2.0 obtuvo 15 listas HLS actualizadas y 15 fragmentos nuevos: no envía una copia fija del búfer inicial.
- El usuario observa el fallo con la app abierta y pantalla encendida. Su informe muestra ~12MB enviados y 11 fallos, pero el contador antiguo incluye conexiones que no llegan a completar una petición. No demuestra por sí solo microcortes de red ni identifica la causa del corte de reproducción.

## Probar desde el móvil

1. Descarga [lunar-edge-patches-0.2.1-beta.1.mpp](https://github.com/adriviciano/morphe-patch-lunar-edge/releases/download/v0.2.1-beta.1/lunar-edge-patches-0.2.1-beta.1.mpp).
2. Añádelo como fuente local en Morphe y para esta prueba utiliza solo esa fuente, versión **0.2.1-beta.1**.
3. Selecciona el APK/XAPK original completo de Lunar Edge **4.0.2**, con los cuatro parches seleccionados. No partas de una APK con Cast aplicado.
4. Instala y emite el mismo vídeo manteniendo la app abierta y pantalla encendida. Prueba al menos **dos minutos**, con el selector **Google Cast · 0.2.1 beta 1**.
5. Si se interrumpe o se corta, sin cerrar los controles pulsa **Diagnóstico de carga → Copiar informe** y pégalo en el chat.

El nuevo informe incluye estado de sesión, reproducción confirmada y motivo de cierre, además de lecturas del motor, escrituras a TV y conexiones que no completan petición. Nunca muestra URL, IP, títulos ni tokens. Los timeouts y el protocolo no se cambian en esta prueba.

## Validación pendiente

La prueba del traspaso falló al usar O y pasa con L. Se verifican también las semánticas de L en la APK real, protocolo Cast, CORS/HLS y entrega de fragmentos después de 30 segundos. Falta confirmar si este cambio resuelve el caso real en la TV. No se actualiza la fuente estable principal hasta esa comprobación.
