package local.lunaredge.patches

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.mutableClassDefBy
import app.morphe.patcher.patch.patch
import app.morphe.patcher.patch.document
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import com.android.tools.smali.dexlib2.immutable.ImmutableMethod
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction

private const val CAST_ROOT = "Lcom/lunaredge/browser/"
private const val CAST_PLAYER = "Lchuangyuan/ycj/videolibrary/video/ExoUserPlayer;"

@Suppress("unused")
val enableGoogleCast = patch(
    name = "Enable Google Cast",
    description = "Añade Google Cast con puente CORS para HLS local y controles. Exige la corrección del motor y conserva DLNA. Reproducción confirmada en Android TV.",
    default = true
) {
    compatibleWith(Compatibility(packageName = "com.lunaredge.browser", name = "Lunar Edge", targets = listOf(AppTarget("4.0.2", 40002))))
    // Cast nunca debe generar una APK que vuelva a rechazar su nueva firma.
    dependsOn(fixLocalVideoEngine)
    extendWith("extensions/cast.mpe")
    execute {
        // La lista DLNA existente enlaza este servicio, ausente del manifiesto original.
        document("AndroidManifest.xml").use { manifest ->
            val namespace = "http://schemas.android.com/apk/res/android"
            val application = manifest.getElementsByTagName("application").item(0)
            val services = manifest.getElementsByTagName("service")
            val name = "com.android.cast.dlna.dmc.DLNACastService"
            if ((0 until services.length).none { (services.item(it) as org.w3c.dom.Element).getAttributeNS(namespace, "name") == name }) {
                val service = manifest.createElement("service")
                service.setAttributeNS(namespace, "android:name", name)
                service.setAttributeNS(namespace, "android:exported", "false")
                application.appendChild(service)
            }
        }
        // El traspaso debe pausar, nunca liberar, el reproductor original.
        val pause = mutableClassDefBy(CAST_PLAYER).methods.single {
            it.name == "L" && it.parameterTypes.isEmpty() && it.returnType == "V"
        }
        val calls = pause.implementation!!.instructions.filterIsInstance<ReferenceInstruction>().map { it.reference.toString() }
        check(calls.any { it.endsWith("->setPlayWhenReady(Z)V") } && calls.none { it == "${CAST_PLAYER}->R()V" }) {
            "La función de pausa local ha cambiado: no se añade Cast a esta variante."
        }
        val playerClass = mutableClassDefBy("${CAST_ROOT}ui/JYAlternativeZero;")
        val fields = playerClass.fields.associate { it.name to it.type }
        check(fields["D"] == "I" && fields["l"] == "Ljava/util/List;" && fields["j"] == CAST_PLAYER && fields["k"] == "${CAST_ROOT}netbean/JybThrowTask;") {
            "El reproductor ha cambiado: no se añade Cast a esta variante."
        }
        val methods = listOf("transformRules" to "lunarDlnaPortrait", "willWindow" to "lunarDlnaLandscape").map { (name, fallback) ->
            val method = playerClass.methods.single { it.name == name && it.parameterTypes.isEmpty() && it.returnType == "V" }
            check(playerClass.methods.none { it.name == fallback }) { "Cast ya está aplicado. Usa la entrada sin el parche Cast." }
            val original = MutableMethod(ImmutableMethod.of(method)).apply { this.name = fallback }
            method to original
        }
        methods.forEach { (method, original) ->
            playerClass.methods.add(original)
            method.addInstructions(0, "invoke-static/range {p0 .. p0}, Llocal/lunaredge/cast/CastBridge;->show(Landroid/app/Activity;)V\nreturn-void")
        }
    }
}
