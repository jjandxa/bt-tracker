package ml.nobb.bttracker.model.vo

import ml.nobb.bttracker.model.enums.ApiResultCodeEnum

data class ApiResult(val code: Int? = null, val message: String? = null) {

    companion object {

        fun success(): ApiResult {
            return ApiResult(ApiResultCodeEnum.SUCCESS.code, ApiResultCodeEnum.SUCCESS.message)
        }

        fun fail(message: String): ApiResult {
            return ApiResult(ApiResultCodeEnum.ERROR.code, message)
        }
    }
}