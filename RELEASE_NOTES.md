# Emisión en segundo plano · 0.3.0-beta.1

Añade un servicio visible de conexión a TV para mantener el motor, el puente y la sesión al bloquear la pantalla o cambiar de app. Se activa únicamente al elegir receptor y muestra una notificación con **Dejar de emitir**. Incluye Google Cast y DLNA/LG.

Protege onPause/onResume únicamente mientras esa Activity emite, sin omitir las llamadas a super. Mantiene CPU y red durante la sesión y libera los recursos al detener, terminar o desconectar; un cierre antiguo no puede detener una emisión nueva. Conserva los cuatro parches y la reparación del servicio DLNA.

Instala el MPP como fuente local y parte del APK/XAPK original completo 4.0.2 con los cuatro parches. Si Android lo solicita, permite notificaciones. Prueba cambiando de app y después bloqueando la pantalla, al menos dos minutos en cada situación; comprueba también que al detener desaparece la notificación.

**Pendiente de prueba física**: no hay móvil conectado y el usuario probará después. Se verifica compilación, manifiesto, DEX y propiedad/cierre de sesión; no se afirma que la pantalla apagada esté ya validada. La estable principal permanece en **0.2.1**.

Instrucciones y límites completos en CAST_PREVIEW.md.
