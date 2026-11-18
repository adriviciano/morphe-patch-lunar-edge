import com.android.apksig.ApkVerifier
import com.android.tools.smali.dexlib2.DexFileFactory
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.Opcodes
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.instruction.RegisterRangeInstruction
import java.io.File
import java.util.zip.ZipFile

fun main(args: Array<String>) {
    val apk = File(args.single())
    val container = DexFileFactory.loadDexContainer(apk, Opcodes.getDefault())
    val classes = container.dexEntryNames.flatMap { container.getEntry(it)!!.dexFile.classes }.associateBy { it.type }
    val root = "Lcom/lunaredge/browser/"
    fun method(owner: String, name: String): Method = classes.getValue(owner).methods.single { it.name == name }
    fun checkPrefix(owner: String, name: String, target: String?) {
        val implementation = method(owner, name).implementation!!
        val instructions = implementation.instructions.toList()
        if (target == null) {
            check(instructions.first().opcode == Opcode.RETURN_VOID)
        } else {
            check(instructions[0].opcode == Opcode.INVOKE_VIRTUAL_RANGE)
            check((instructions[0] as ReferenceInstruction).reference.toString() == target)
            check(instructions[1].opcode == Opcode.RETURN_VOID)
            val expectedRegister = if (name == "loadAdPlay") implementation.registerCount - 10 else implementation.registerCount - 1
            check((instructions[0] as RegisterRangeInstruction).startRegister == expectedRegister)
        }
        println("OK: $owner->$name")
    }
    checkPrefix("${root}ui/JYAlternativeZero;", "loadAdPlay", "${root}ui/JYAlternativeZero;->v2()V")
    checkPrefix("${root}ui/JYAlternativeZero;", "raiseLoop", "Lchuangyuan/ycj/videolibrary/video/ExoUserPlayer;->M()V")
    checkPrefix("${root}ui/JYNameTask;", "matchFocusObject", "Lchuangyuan/ycj/videolibrary/video/ExoUserPlayer;->h0()V")
    checkPrefix("${root}util/JYTailSession;", "c", null)
    checkPrefix("${root}ui/JYAlternativeZero;", "disableMirror", null)
    checkPrefix("${root}ui/JYAlternativeZero;", "s2", null)
    // Comprobamos también que sigue presente la ruta DLNA y sus botones originales.
    check(classes.containsKey("Lcom/android/cast/dlna/dmc/DLNACastManager;"))
    check(method("${root}ui/JYAlternativeZero;", "transformRules").implementation != null)
    check(method("${root}ui/JYAlternativeZero;", "willWindow").implementation != null)
    ZipFile(apk).use { zip ->
        check(zip.entries().asSequence().any { it.name.startsWith("lib/armeabi-v7a/") })
    }
    val signature = ApkVerifier.Builder(apk).build().verify()
    check(signature.isVerified) { "Firma inválida: ${signature.errors}" }
    println("OK: firma APK, librerías ARMv7 y rutas DLNA conservadas.")
    println("Pendiente: reproducción y descubrimiento de TV en dispositivos reales.")
}
