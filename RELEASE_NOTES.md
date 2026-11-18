Prueba **Google Cast · beta 1** para Lunar Edge **4.0.2 (40002)**. La estable **0.1.2** se mantiene sin cambios.

## Prueba desde el móvil

Descarga **lunar-edge-cast-preview-0.2.0-beta.1.mpp** y añádelo como fuente local en Morphe. Su nombre es **Lunar Edge Cast Preview beta 1** y contiene **un único parche seleccionado por defecto**.

Para continuar la prueba anterior, usa `lunar-edge-cast-input.apk` de Descargas y únicamente esta fuente local. El resumen debe indicar **Enable Google Cast (experimental)** con **1 parche**. Instala el resultado y abre el botón de emitir dentro de un vídeo.

El selector debe mostrar exactamente **Google Cast · beta 1**. Si aparece el antiguo, la instalación no está entrando en el código de esta prueba. Si muestra el nuevo pero no encuentra la TV, prueba **Introducir IP**, usando la dirección de los ajustes de red de la TV.

[Instrucciones completas](https://github.com/adriviciano/morphe-patch-lunar-edge/blob/codex/google-cast/CAST_PREVIEW.md).

## Alcance y validación

- Descubrimiento Cast de Android TV/Chromecast, conexión al Default Media Receiver y controles de reproducción, pausa, posición y volumen.
- Selector DLNA original conservado como alternativa.
- Reproducción y pausa de un MP4 generado localmente confirmadas en una Grundig Android TV desde el móvil.
- Sobre protobuf contrastado con un decodificador independiente; paquete de un único parche y cambio de ambos botones verificados.
- **Pendiente:** integración de la búsqueda en la app, fuentes reales de Lunar Edge, reproducción prolongada con pantalla apagada, subtítulos y cambio de episodio.

El paquete completo contiene también los tres parches estables, para partir del APK/XAPK original. El paquete `cast-preview` contiene solo Cast, para partir de la entrada de prueba que ya tiene el motor y los anuncios corregidos. No se incluyen APK ni claves.
