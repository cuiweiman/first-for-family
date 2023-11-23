package com.first.family.model.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/11/8 14:39
 */
public class ErnieErrResp {

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_msg")
    private String errorMsg;

}
