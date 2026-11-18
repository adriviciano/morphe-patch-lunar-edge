# Google Cast · beta 4: puente CORS para HLS local

La beta 3 confirmó `LOAD_FAILED` y HLS servido por el motor local sin cabeceras CORS, tanto en loopback como en la dirección LAN. [Google exige CORS para streaming adaptativo en Cast](https://developers.google.com/cast/docs/media). Es una incompatibilidad concreta que esta beta corrige; todavía falta confirmar si era la única causa del rechazo en la TV del usuario.

## Prueba desde el móvil

1. Descarga [lunar-edge-cast-complete-0.2.0-beta.4.mpp](https://github.com/adriviciano/morphe-patch-lunar-edge/releases/download/v0.2.0-beta.4/lunar-edge-cast-complete-0.2.0-beta.4.mpp).
2. Añádelo como fuente local **Lunar Edge Cast Complete beta 4** y utiliza solo esa fuente para esta prueba.
3. Parte del APK/XAPK original de Lunar Edge **4.0.2**, con los cuatro parches seleccionados. No uses una APK con Cast ya aplicado.
4. Instala, carga el mismo vídeo y pulsa emitir. Debe mostrar **Google Cast · beta 4**.
5. Selecciona la TV y mantén el móvil con Lunar Edge abierta y conectado al mismo Wi-Fi durante esta prueba.
6. Si falla, sin cerrar los controles pulsa **Diagnóstico de carga → Copiar informe** y pégalo en el chat.

## Qué cambia

Para HLS local se inicia un pequeño servidor HTTP dentro de la app, enlazado a la dirección Wi-Fi del móvil y restringido a la IP del receptor elegido. La TV recibe una ruta aleatoria de sesión. El puente lee el motor por loopback, añade CORS, responde a OPTIONS y HEAD y conserva Range/Content-Range en los recursos binarios. Reescribe las referencias relativas, absolutas y los atributos URI de las listas HLS para que listas secundarias, claves, inicializaciones y segmentos también pasen por el puente. Las URL y consultas del origen se conservan internamente y no se vuelcan al informe.

El puente se cierra con la sesión. No cambia la reparación del motor, los anuncios ni el selector DLNA. La fuente remota y los vídeos locales que no se identifican como HLS mantienen el comportamiento anterior.

## Informe y validación

El informe conserva las comprobaciones original/LAN desde el móvil y añade **Puente CORS: solicitudes TV; listas; recursos; bytes; fallos; último HTTP**. Las solicitudes se cuentan únicamente para rutas válidas del receptor seleccionado. Esto permite distinguir si la TV llega al puente y si pide datos, sin incluir IP, URL, contenido o tokens. Es normal que la comprobación directa del motor siga diciendo CORS ausente: las cabeceras se añaden en el puente.

Pruebas automáticas con un origen HLS sin CORS: listas principal/secundaria, URI absoluta de clave, URI relativa de inicialización, consulta de segmento, Range/206, OPTIONS, HEAD, error 403, rutas desconocidas y rechazo de otro cliente. Se comprueba también el paquete de cuatro parches y la reparación obligatoria del motor. Falta la reproducción de la fuente real en la TV; no se afirma que ya esté resuelta. La estable sigue siendo **0.1.2**.
