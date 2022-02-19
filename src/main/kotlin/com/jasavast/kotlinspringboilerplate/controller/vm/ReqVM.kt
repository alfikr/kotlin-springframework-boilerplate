package com.jasavast.kotlinspringboilerplate.controller.vm

import lombok.Data
import org.json.JSONObject
import javax.annotation.Nullable
import javax.validation.constraints.NotEmpty

@Data
class ReqVM(@NotEmpty final val method: String, @Nullable final val data: Object) {
    fun getData():JSONObject{
        return JSONObject().put("method",method).put("data",data)
    }
}