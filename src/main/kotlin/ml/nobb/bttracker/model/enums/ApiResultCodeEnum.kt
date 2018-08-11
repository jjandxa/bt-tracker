package ml.nobb.bttracker.model.enums

enum class ApiResultCodeEnum(val code: Int,val  message: String) {
    SUCCESS(0, "success"),
    ERROR(-1, "error")
}