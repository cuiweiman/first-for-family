package com.first.family.model.request;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/11/7 09:30
 */
@Data
public class ChatReq {
    private String clientId;
    private List<ErnieBotReq> messages;

}
