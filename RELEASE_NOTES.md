**Google Cast · beta 3 — diagnóstico de LOAD_FAILED** para Lunar Edge **4.0.2**.

La beta 2 encuentra la Android TV, pero la carga real devuelve LOAD_FAILED sin código. Esta versión añade **Diagnóstico de carga** y **Copiar informe** a los controles. El informe comprueba la respuesta HTTP y el formato de la fuente desde el móvil sin incluir URL ni tokens. No se presenta como una corrección del fallo de reproducción.

Descarga **lunar-edge-cast-complete-0.2.0-beta.3.mpp**, añádelo como fuente local y parte del APK/XAPK original con los cuatro parches seleccionados. Reproduce el fallo y, sin cerrar los controles, pulsa **Diagnóstico de carga → Copiar informe**. Consulta CAST_PREVIEW.md para los pasos completos.

Se conserva la reparación obligatoria del motor local. La estable sigue siendo **0.1.2**. Pendiente validar la fuente real con el informe del móvil.
