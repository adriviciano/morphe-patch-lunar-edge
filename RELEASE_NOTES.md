# Versión estable 0.2.0

Google Cast se incorpora al paquete principal de Morphe y queda activado por defecto junto a la reparación del motor y los dos parches de anuncios.

La beta 4 resolvió el fallo LOAD_FAILED del HLS local mediante un puente CORS. El usuario confirmó reproducción real en su Grundig Android TV. Esta versión promueve ese mismo comportamiento a estable: descubrimiento, conexión, puente HLS/CORS, controles y alternativa DLNA. Se conserva el diagnóstico copiable sin URL ni tokens.

## Obtenerla

Actualiza la fuente `adriviciano/morphe-patch-lunar-edge` en Morphe, o añádela desde https://morphe.software/add-source?github=adriviciano/morphe-patch-lunar-edge. Selecciona el APK/XAPK original completo de Lunar Edge 4.0.2, con los cuatro parches seleccionados. Si usaste fuentes beta locales, desactívalas para esta instalación. El único paquete principal es `lunar-edge-patches-0.2.0.mpp`.

El móvil y la TV deben compartir Wi-Fi. Mantén Lunar Edge abierta durante la emisión. Otros modelos, fuentes, subtítulos, cambios de episodio y reproducción prolongada con pantalla apagada no se han validado exhaustivamente.

Las pruebas automáticas verifican protocolo Cast, diagnóstico, reescritura HLS, CORS, recursos dependientes y Range. También se verifica el paquete principal con cuatro parches predeterminados y la dependencia obligatoria del motor.
