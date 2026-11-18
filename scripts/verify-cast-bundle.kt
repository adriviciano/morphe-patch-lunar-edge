import app.morphe.patcher.patch.loadPatchesFromJar
import java.io.File
import java.util.jar.JarFile

fun main(args: Array<String>) {
    val file = File(args.single())
    val patches = loadPatchesFromJar(setOf(file))
    check(patches.size == 1) { "La prueba debe contener un único parche, contiene ${patches.size}." }
    val cast = patches.single()
    check(cast.name == "Enable Google Cast (experimental)")
    check(cast.default) { "El parche de prueba debe estar seleccionado por defecto." }
    check(cast.compatibility!!.single().packageName == "com.lunaredge.browser")
    JarFile(file).use { jar ->
        check(jar.manifest.mainAttributes.getValue("Name") == "Lunar Edge Cast Preview beta 1")
        check(jar.getEntry("classes.dex") != null)
        val extension = jar.getInputStream(jar.getEntry("extensions/cast.mpe")).use { it.readBytes() }
        check(extension.copyOfRange(0, 3).toString(Charsets.US_ASCII) == "dex")
        check(extension.toString(Charsets.ISO_8859_1).contains("Google Cast"))
        check(!extension.toString(Charsets.ISO_8859_1).contains("LunarDiag"))
    }
    println("OK: fuente Cast única, predeterminada, identificable y con extensión DEX.")
}
