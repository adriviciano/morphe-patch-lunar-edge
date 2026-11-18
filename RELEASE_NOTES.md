**Google Cast · beta 4 — puente CORS para HLS local** en Lunar Edge **4.0.2**.

El informe de la beta 3 confirmó que el motor entrega HLS sin CORS y que la TV devuelve LOAD_FAILED. Google requiere CORS para streaming adaptativo. Esta beta sirve el HLS local mediante un puente dentro del móvil que añade CORS y reescribe las referencias de listas, segmentos, claves e inicializaciones. Conserva las peticiones de rango y añade contadores de solicitudes de la TV al diagnóstico, sin exponer URL ni tokens.

Descarga **lunar-edge-cast-complete-0.2.0-beta.4.mpp**, añádelo como fuente local y parte del APK/XAPK original con los cuatro parches seleccionados. Mantén la app abierta durante la prueba. Si falla, copia el informe desde **Diagnóstico de carga**.

La reparación obligatoria del motor se conserva. Las pruebas del puente con un origen HLS controlado pasan; sigue pendiente confirmar la fuente real en la TV. La estable sigue siendo **0.1.2**. Instrucciones completas en CAST_PREVIEW.md.
