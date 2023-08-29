package com.first.bcesignature;

import com.first.bcesignature.util.BceV1Signer;
import com.first.bcesignature.util.HttpUtils;
import com.google.common.collect.Maps;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/8/25 11:41
 */
public class BceSignerDemo {

    public static void main(String[] args) throws URISyntaxException {
        URI uri = new URI("http://www.test.com:8371/v1/products/f2kxbuyt7herxi6s");
        String ak = "ac**";
        String sk = "sk**";
        BceV1Signer bceV1Signer = new BceV1Signer(uri.getAuthority(), ak, sk);

        // signature

        HashMap<String, String> param = Maps.newHashMap();
        param.put("pageNo", "1");
        param.put("pageSize", "10");
        HashMap<String, String> header = Maps.newHashMap();
        // create token & header
        bceV1Signer.sign(uri.getPath(), HttpMethod.GET, param, header);
        // request
        String url = uri.toString().concat("?").concat(HttpUtils.getCanonicalQueryString(param, false));
        Request request = new Request.Builder().url(url)
                .headers(Headers.of(header))
                .get().build();

        Call call = HttpUtils.okHttpClient().newCall(request);
        // execute
        try (Response response = call.execute()) {
            System.out.println("\tresponse.code:\t" + response.code());
            if (HttpStatus.OK.value() == response.code()) {
                assert response.body() != null;
                String body = response.body().string();
                System.out.printf("\tresponse.body: %s %n", body);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
