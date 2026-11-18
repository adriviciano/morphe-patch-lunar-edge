# Prueba Google Cast · beta 1

Esta versión es una prueba separada de la versión estable 0.1.2. No requiere el PC para instalarla mediante Morphe.

## Instalar desde el móvil

1. Descarga [lunar-edge-cast-preview-0.2.0-beta.1.mpp](https://github.com/adriviciano/morphe-patch-lunar-edge/releases/download/v0.2.0-beta.1/lunar-edge-cast-preview-0.2.0-beta.1.mpp).
2. En Morphe, añade ese archivo como **fuente local**. Su nombre es **Lunar Edge Cast Preview beta 1** y contiene un único parche, seleccionado por defecto.
3. Para continuar las pruebas anteriores, selecciona desde almacenamiento **lunar-edge-cast-input.apk**. Esa entrada ya tiene los anuncios desactivados y el motor corregido, pero todavía no tiene Cast.
4. Usa solo la fuente local nueva para esta prueba. El resumen debe indicar **Enable Google Cast (experimental)**, con **1 parche**. No apliques de nuevo las fuentes de diagnóstico ni la corrección del motor a esta entrada.
5. Parchea e instala con Morphe. Abre un vídeo y pulsa el botón de emitir.

El selector nuevo debe mostrar exactamente **Google Cast · beta 1**. Si aparece el selector antiguo, la instalación no está entrando en el código de esta prueba: comprueba la fuente, el resumen de parcheo y que hayas instalado el resultado recién generado.

Si partes del APK/XAPK original, aplica primero los tres parches estables 0.1.2, o utiliza el paquete completo de esta beta con los cuatro parches; no mezcles ambos procedimientos.

## Qué comprobar

- Si aparece el título **Google Cast · beta 1**.
- Si aparece la Android TV al esperar unos 12 segundos.
- Si no aparece, pulsa **Introducir IP**. La dirección actual de la TV se consulta en sus ajustes de red. Esto separa el descubrimiento de la conexión.
- Al elegir la TV, comprueba si abre el reproductor y carga el vídeo de Lunar Edge.
- Después prueba pausa, reproducción y avance. Para volver al selector original está **Otros televisores (DLNA)**.

El móvil y la TV deben estar en la misma red Wi-Fi. Todavía no están verificados los vídeos reales de Lunar Edge en esta beta, la reproducción prolongada con pantalla apagada, los subtítulos ni la continuidad entre episodios.

## Evidencia disponible

El protocolo se ha probado contra un decodificador protobuf independiente. En una Grundig Android TV se han confirmado reproducción y pausa de un MP4 de prueba servido desde el móvil. Se verifica estáticamente que los dos botones del reproductor abren el código nuevo y que el selector DLNA queda conservado como alternativa. La primera instalación de prueba mostró el selector antiguo y aún falta confirmar la integración en la app con esta fuente inequívoca.
