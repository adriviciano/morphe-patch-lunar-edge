package local.lunaredge.patches

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.mutableClassDefBy
import app.morphe.patcher.patch.patch
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import com.android.tools.smali.dexlib2.immutable.ImmutableMethod

private const val CAST_ROOT = "Lcom/lunaredge/browser/"
private const val CAST_PLAYER = "Lchuangyuan/ycj/videolibrary/video/ExoUserPlayer;"

@Suppress("unused")
val enableGoogleCast = patch(
    name = "Enable Google Cast (experimental)",
    description = "Añade el selector Google Cast beta 2 y controles. Incluye obligatoriamente la corrección del motor local. Mantiene DLNA como alternativa. Pendiente de validar la fuente real de Lunar Edge.",
    default = false
) {
    compatibleWith(Compatibility(packageName = "com.lunaredge.browser", name = "Lunar Edge", targets = listOf(AppTarget("4.0.2", 40002))))
    // Cast nunca debe generar una APK que vuelva a rechazar su nueva firma.
    dependsOn(fixLocalVideoEngine)
    extendWith("extensions/cast.mpe")
    execute {
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
