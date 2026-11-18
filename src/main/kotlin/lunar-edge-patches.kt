package local.lunaredge.patches

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.mutableClassDefBy
import app.morphe.patcher.patch.patch
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

private const val ROOT = "Lcom/lunaredge/browser/"
private const val PLAYER = "Lchuangyuan/ycj/videolibrary/video/ExoUserPlayer;"
private const val VIDEO_VIEW = "Lchuangyuan/ycj/videolibrary/widget/VideoPlayerView;"
private const val AD_INFO = "${ROOT}netbean/JYChildCaptureTask;"
private val playbackParameters = listOf(
    "Z", "Z", "Landroid/app/Activity;", VIDEO_VIEW, "I", "I",
    "Landroid/view/View;", "Landroid/os/Handler;", PLAYER
)

private fun requireMethod(
    owner: String,
    name: String,
    parameters: List<String>,
    expectedCall: String
): MutableMethod {
    val method = mutableClassDefBy(owner).methods.singleOrNull {
        it.name == name && it.returnType == "V" &&
            it.parameterTypes.map(CharSequence::toString) == parameters
    } ?: error("No se encuentra la firma esperada: $owner->$name. Solo se admite Lunar Edge 4.0.2.")

    // Verificamos el cuerpo original para rechazar variantes incompatibles.
    check(method.implementation?.instructions?.any { instruction ->
        val reference = (instruction as? ReferenceInstruction)?.reference as? MethodReference
        reference != null && "${reference.definingClass}->${reference.name}" == expectedCall
    } == true) { "El flujo de $owner->$name ha cambiado; no se aplicará el parche." }
    return method
}

@Suppress("unused")
val removePlayerVideoAds = patch(
    name = "Remove player video ads",
    description = "Omite los anuncios de vídeo del reproductor y continúa por su ruta local de reproducción. Compatible con Lunar Edge 4.0.2.",
    default = true
) {
    compatibleWith(Compatibility(packageName = "com.lunaredge.browser", name = "Lunar Edge", targets = listOf(AppTarget("4.0.2", 40002))))
    execute {
        val mainPlayer = "${ROOT}ui/JYAlternativeZero;"
        val localPlayer = "${ROOT}ui/JYNameTask;"
        val startPlayback = requireMethod(mainPlayer, "loadAdPlay", playbackParameters, "$mainPlayer->v2")
        val resumePlayback = requireMethod(mainPlayer, "raiseLoop", listOf("Z") + playbackParameters, "$PLAYER->M")
        val localPlayback = requireMethod(localPlayer, "matchFocusObject", playbackParameters, "$PLAYER->h0")

        // Conservamos las mismas rutas que usa la aplicación cuando no hay anuncios.
        startPlayback.addInstructions(0, "invoke-virtual/range {p0 .. p0}, $mainPlayer->v2()V\nreturn-void")
        resumePlayback.addInstructions(0, "invoke-virtual/range {p10 .. p10}, $PLAYER->M()V\nreturn-void")
        localPlayback.addInstructions(0, "invoke-virtual/range {p9 .. p9}, $PLAYER->h0()V\nreturn-void")
    }
}

@Suppress("unused")
val removePlayerDisplayAds = patch(
    name = "Remove player display ads",
    description = "Evita cargar los intersticiales y anuncios nativos superpuestos del reproductor de Lunar Edge 4.0.2.",
    default = true
) {
    compatibleWith(Compatibility(packageName = "com.lunaredge.browser", name = "Lunar Edge", targets = listOf(AppTarget("4.0.2", 40002))))
    execute {
        val mainPlayer = "${ROOT}ui/JYAlternativeZero;"
        val interstitial = requireMethod(
            "${ROOT}util/JYTailSession;", "c", listOf(AD_INFO, "I"),
            "Lcom/wangxiong/sdk/view/InterstitialAdLoader;->loadAd"
        )
        val nativeBanner = requireMethod(
            mainPlayer, "disableMirror", listOf(AD_INFO),
            "${ROOT}util/JybPreviousSpace;->b"
        )
        val nativeOverlay = requireMethod(
            mainPlayer, "s2", listOf(AD_INFO, "I", "I", "I"),
            "Lcom/wangxiong/sdk/view/CustomNativeLoader;-><init>"
        )
        // No se publican eventos de anuncio mostrado: el reproductor sigue en su estado normal.
        listOf(interstitial, nativeBanner, nativeOverlay).forEach {
            it.addInstructions(0, "return-void")
        }
    }
}
