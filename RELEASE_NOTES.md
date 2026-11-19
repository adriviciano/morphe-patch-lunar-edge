# Versión estable 0.2.1

- Repara la detección DLNA: declara el servicio `DLNACastService` que utiliza la lista de televisores y faltaba en el manifiesto original. La LG 43UK6300PLB con webOS responde y aparece tras la corrección.
- Pausa el reproductor local durante el traspaso a Google Cast, en lugar de liberarlo. La semántica del método se verifica al parchear.
- Amplía el diagnóstico con estado y cierre de la sesión, errores de lectura/escritura y conexiones que no completan una petición. El contador anterior no permitía atribuir los fallos a microcortes.

Validación: actualización instalada con la firma de Morphe, usuario confirma vídeo en la LG y consultas AVTransport muestran reproducción y avance continuo durante más de dos minutos. Prueba de streaming del puente: 42 segundos, 15 listas y 15 fragmentos nuevos. Verificación de APK ARM64 y servicio DLNA interno registrado. El test del manifiesto falla con la 0.2.0 y pasa con la corrección.

La Grundig Android TV no estaba disponible: sigue pendiente comprobar el corte intermitente de Google Cast en ese dispositivo. Esta versión no afirma que ese corte esté ya resuelto.

Actualiza la fuente `adriviciano/morphe-patch-lunar-edge` en Morphe y parte del APK/XAPK original completo de Lunar Edge 4.0.2 con los cuatro parches seleccionados. Desactiva las fuentes beta locales para usar el paquete principal. Se conservan los datos al actualizar con la misma firma de Morphe.
