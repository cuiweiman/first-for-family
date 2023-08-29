package com.first.bcesignature.util;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/8/25 11:42
 */
public class HttpUtils {
    private static final Joiner QUERY_STRING_JOINER = Joiner.on('&');
    private static BitSet URI_UNRESERVED_CHARACTERS = new BitSet();
    private static String[] PERCENT_ENCODED_STRINGS = new String[256];

    private HttpUtils() {
    }

    static {
        int i;
        for (i = 97; i <= 122; ++i) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }
        for (i = 65; i <= 90; ++i) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }
        for (i = 48; i <= 57; ++i) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }

        URI_UNRESERVED_CHARACTERS.set(45);
        URI_UNRESERVED_CHARACTERS.set(46);
        URI_UNRESERVED_CHARACTERS.set(95);
        URI_UNRESERVED_CHARACTERS.set(126);
        for (i = 0; i < PERCENT_ENCODED_STRINGS.length; ++i) {
            PERCENT_ENCODED_STRINGS[i] = String.format("%%%02X", i);
        }
    }

    public static OkHttpClient okHttpClient() {
        return (new OkHttpClient.Builder())
                .retryOnConnectionFailure(false)
                .connectionPool(new ConnectionPool(200, 300L, TimeUnit.SECONDS))
                .connectTimeout(90L, TimeUnit.SECONDS)
                .readTimeout(240L, TimeUnit.SECONDS)
                .writeTimeout(120L, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .build();
    }

    public static String normalizePath(String path) {
        return normalize(path).replace("%2F", "/");
    }

    public static String normalize(String value) {
        StringBuilder builder = new StringBuilder();
        byte[] var2 = value.getBytes(StandardCharsets.UTF_8);
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            if (URI_UNRESERVED_CHARACTERS.get(b & 255)) {
                builder.append((char) b);
            } else {
                builder.append(PERCENT_ENCODED_STRINGS[b & 255]);
            }
        }

        return builder.toString();
    }


    public static String getCanonicalQueryString(Map<String, String> parameters, boolean forSignature) {
        if (parameters.isEmpty()) {
            return "";
        } else {
            List<String> parameterStrings = Lists.newArrayList();
            Iterator var3 = parameters.entrySet().iterator();

            while (true) {
                Map.Entry entry;
                do {
                    if (!var3.hasNext()) {
                        Collections.sort(parameterStrings);
                        return QUERY_STRING_JOINER.join(parameterStrings);
                    }

                    entry = (Map.Entry) var3.next();
                } while (forSignature && "Authorization".equalsIgnoreCase((String) entry.getKey()));

                String key = (String) entry.getKey();
                Preconditions.checkNotNull(key, "parameter key should not be null");
                String value = (String) entry.getValue();
                if (value == null) {
                    if (forSignature) {
                        parameterStrings.add(normalize(key) + '=');
                    } else {
                        parameterStrings.add(normalize(key));
                    }
                } else {
                    parameterStrings.add(normalize(key) + '=' + normalize(value));
                }
            }
        }
    }

}
