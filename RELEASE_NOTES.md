Parches experimentales para **Lunar Edge 4.0.2 (40002)**, paquete `com.lunaredge.browser`.

Versión 0.1.2: corrige el falso error de «red o servidor» después de firmar la APK con Morphe.

- **Fix local video engine:** permite arrancar `libpp_hls.so` con la firma nueva. Solo admite las bibliotecas exactas de 4.0.2, comprobadas con SHA256, para ARM64 y ARMv7.

- **Remove player video ads:** omite los anuncios de vídeo del reproductor principal y local conservando la ruta de continuación sin anuncio.
- **Remove player display ads:** evita cargar los intersticiales y anuncios nativos superpuestos del reproductor principal.

Importar en Morphe: https://morphe.software/add-source?github=adriviciano/morphe-patch-lunar-edge

Fuente manual: https://github.com/adriviciano/morphe-patch-lunar-edge

Confirmado en un móvil ARM64: el motor pasa de devolver `-1` a escuchar en el puerto 7000, la API devuelve código 10000 con datos y el vídeo reproduce sin mostrar el anuncio. ARMv7 verificado por aplicación del parche y comparación binaria; pendiente de prueba real. El paquete de producción no incluye el registro temporal usado para diagnosticarlo. Se conserva el DLNA original; no se ha añadido Google Cast.

Esta publicación contiene únicamente el paquete de parches y sus metadatos. Utiliza tu propia APK/XAPK original.
