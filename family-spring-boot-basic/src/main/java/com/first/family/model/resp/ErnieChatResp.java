package com.first.family.model.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/11/8 15:06
 */
@Data
public class ErnieChatResp {
    private String id;
    private String object;
    private Long created;
    private String result;
    @JsonProperty("is_truncated")
    private Boolean isTruncated;
    @JsonProperty("need_clear_history")
    private Boolean needClearHistory;
    private Map<String, Object> usage;
}
