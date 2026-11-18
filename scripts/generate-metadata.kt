import app.morphe.patcher.patch.loadPatchesFromJar
import com.reandroid.json.JSONObject
import java.io.File
import java.util.jar.JarFile

fun main(args: Array<String>) {
    val bundle = File(args.single())
    val version = JarFile(bundle).use { it.manifest.mainAttributes.getValue("Version") }
    val patches = loadPatchesFromJar(setOf(bundle)).sortedBy { it.name }.map { patch ->
        mapOf(
            "name" to patch.name,
            "description" to patch.description,
            "default" to patch.default,
            "category" to patch.category,
            "compatiblePackages" to patch.compatibility?.map { compatibility ->
                mapOf(
                    "packageName" to compatibility.packageName,
                    "name" to compatibility.name,
                    "description" to compatibility.description,
                    "apkFileType" to compatibility.apkFileType?.name,
                    "appIconColor" to compatibility.appIconColor,
                    "signatures" to compatibility.signatures,
                    "formFactor" to compatibility.formFactor?.name,
                    "targets" to compatibility.targets.map { target ->
                        mapOf(
                            "version" to target.version,
                            "versionCodes" to target.versionCodes?.mapKeys { it.key.name },
                            "isExperimental" to target.isExperimental,
                            "minSdk" to target.minSdk,
                            "description" to target.description
                        )
                    }
                )
            },
            "options" to emptyList<String>()
        )
    }
    File("patches-list.json").writeText(JSONObject(mapOf("version" to version, "patches" to patches)).toString(2) + "\n")
    val repository = "https://github.com/adriviciano/morphe-patch-lunar-edge"
    val metadata = mapOf(
        "created_at" to "2026-11-18T13:35:18",
        "description" to "Parches para Lunar Edge 4.0.2: reparación del motor local, eliminación de anuncios del reproductor y Google Cast con puente HLS/CORS. Cuatro parches activos por defecto. Reproducción confirmada en Android TV.",
        "download_url" to "$repository/releases/download/v$version/lunar-edge-patches-$version.mpp",
        "signature_download_url" to "",
        "version" to version
    )
    File("patches-bundle.json").writeText(JSONObject(metadata).toString(2) + "\n")
    println("Metadatos de Morphe generados desde ${patches.size} parches, versión $version.")
}
