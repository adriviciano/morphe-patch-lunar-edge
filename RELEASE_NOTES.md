# Prueba de corte Cast: 0.2.1-beta.1

Corrige el traspaso al receptor: pausa el reproductor local en lugar de liberarlo. En la APK 4.0.2, O llama a R y destruye el reproductor; L pausa sin destruirlo. El parche verifica la función original antes de aplicarse.

Añade diagnóstico de estado de sesión y motivo de cierre, y distingue errores al leer del motor, escribir a la TV y conexiones sin petición completa. No modifica timeouts, heartbeat ni el comportamiento del puente HLS. El contador antiguo de fallos no bastaba para atribuir las interrupciones a la red.

Una prueba de 42 segundos verifica que el puente obtiene listas actualizadas y fragmentos nuevos, sin limitarse al contenido cargado inicialmente. La prueba del traspaso detecta la liberación anterior y pasa con la pausa.

Descarga el MPP, añádelo como fuente local y parte del APK/XAPK original completo 4.0.2 con los cuatro parches seleccionados. Emite el mismo vídeo al menos dos minutos con pantalla encendida y app abierta. Si falla, copia el informe desde Diagnóstico de carga. Instrucciones en CAST_PREVIEW.md.

La estable permanece en 0.2.0. Falta validar reproducción prolongada en la TV real; este cambio no se presenta como solución ya confirmada del corte.
