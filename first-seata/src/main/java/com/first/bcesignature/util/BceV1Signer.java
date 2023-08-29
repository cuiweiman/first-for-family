package com.first.bcesignature.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.codec.binary.Hex;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.http.HttpMethod;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * @description: iam token
 * @author: cuiweiman
 * @date: 2023/8/25 11:41
 */
public class BceV1Signer {

    private String endpoint;
    private String ak;
    private String sk;

    public BceV1Signer(String endpoint, String ak, String sk) {
        this.endpoint = endpoint;
        this.ak = ak;
        this.sk = sk;
    }

    private static final Set<String> DEFAULT_HEADERS_TO_SIGN = Sets.newHashSet();
    private static final Integer EXPIRATION_PERIOD_IN_SECONDS = 1800;

    private static final Joiner SIGNED_HEADER_STRING_JOINER = Joiner.on(';');
    private static final Joiner HEADER_JOINER = Joiner.on('\n');

    private static final DateTimeFormatter ALTERNATE_ISO8601_DATE_FORMAT =
            ISODateTimeFormat.dateTimeNoMillis().withZone(DateTimeZone.UTC);

    static {
        DEFAULT_HEADERS_TO_SIGN.add("Host".toLowerCase());
        DEFAULT_HEADERS_TO_SIGN.add("Content-Length".toLowerCase());
        DEFAULT_HEADERS_TO_SIGN.add("Content-Type".toLowerCase());
        DEFAULT_HEADERS_TO_SIGN.add("Content-MD5".toLowerCase());
    }


    public void sign(String path, HttpMethod method,
                     Map<String, String> parameters,
                     Map<String, String> headers) {
        headers.put("Host", endpoint);
        String token = token(path, method, parameters, headers);
        headers.put("Authorization", token);
    }

    public String token(String path, HttpMethod method,
                        Map<String, String> parameters,
                        Map<String, String> headers) {
        HashSet<String> headersOption = Sets.newHashSet("Host");
        String accessKeyId = this.ak;
        String secretAccessKey = this.sk;
        Date timestamp = new Date();
        String authString = "bce-auth-v1/" + accessKeyId + "/"
                + this.formatAlternateIso8601Date(timestamp) + "/"
                + EXPIRATION_PERIOD_IN_SECONDS;
        String signingKey = this.sha256Hex(secretAccessKey, authString);
        String canonicalUri = this.getCanonicalUriPath(path);
        String canonicalQueryString = HttpUtils.getCanonicalQueryString(parameters, true);
        SortedMap<String, String> headersToSign = this.getHeadersToSign(headers, headersOption);
        String canonicalHeader = this.getCanonicalHeaders(headersToSign);
        String signedHeaders = SIGNED_HEADER_STRING_JOINER.join(headersToSign.keySet());
        signedHeaders = signedHeaders.trim().toLowerCase();

        String canonicalRequest = method.name() + "\n" + canonicalUri + "\n"
                + canonicalQueryString + "\n" + canonicalHeader;
        String signature = this.sha256Hex(signingKey, canonicalRequest);
        String authorizationHeader = authString + "/" + signedHeaders + "/" + signature;
        System.out.printf("CanonicalRequest:%s%nAuthorization:%s%n",
                canonicalRequest, authorizationHeader);
        return authorizationHeader;
    }

    private String sha256Hex(String signingKey, String stringToSign) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(signingKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return new String(Hex.encodeHex(mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8))));
        } catch (Exception var4) {
            throw new RuntimeException("Fail to generate the signature", var4);
        }
    }

    private String getCanonicalUriPath(String path) {
        if (path == null) {
            return "/";
        } else {
            return path.startsWith("/") ? HttpUtils.normalizePath(path) : "/" + HttpUtils.normalizePath(path);
        }
    }

    private boolean isDefaultHeaderToSign(String header) {
        header = header.trim().toLowerCase();
        return header.startsWith("x-bce-") || DEFAULT_HEADERS_TO_SIGN.contains(header);
    }

    private SortedMap<String, String> getHeadersToSign(Map<String, String> headers, Set<String> headersToSign) {
        SortedMap<String, String> ret = Maps.newTreeMap();
        String key;
        if (headersToSign != null) {
            Set<String> tempSet = Sets.newHashSet();
            for (String o : headersToSign) {
                key = o;
                tempSet.add(key.trim().toLowerCase());
            }
            headersToSign = tempSet;
        }

        Set<Map.Entry<String, String>> entries = headers.entrySet();
        Iterator<Map.Entry<String, String>> var7 = entries.iterator();

        while (true) {
            Map.Entry entry;
            do {
                do {
                    do {
                        if (!var7.hasNext()) {
                            return ret;
                        }
                        entry = var7.next();
                        key = (String) entry.getKey();
                    } while (entry.getValue() == null);
                } while (((String) entry.getValue()).isEmpty());
            } while ((headersToSign != null
                    || !this.isDefaultHeaderToSign(key)) && (headersToSign == null
                    || !headersToSign.contains(key.toLowerCase())
                    || "Authorization".equalsIgnoreCase(key)));

            ret.put(key, String.valueOf(entry.getValue()));
        }
    }

    private String getCanonicalHeaders(SortedMap<String, String> headers) {
        if (headers.isEmpty()) {
            return "";
        } else {
            List<String> headerStrings = Lists.newArrayList();

            for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
                Map.Entry entry = stringStringEntry;
                String key = (String) entry.getKey();
                if (key != null) {
                    String value = (String) entry.getValue();
                    if (value == null) {
                        value = "";
                    }
                    headerStrings.add(HttpUtils.normalize(key.trim().toLowerCase())
                            + ':' + HttpUtils.normalize(value.trim()));
                }
            }
            Collections.sort(headerStrings);
            return HEADER_JOINER.join(headerStrings);
        }
    }

    private String formatAlternateIso8601Date(Date date) {
        return ALTERNATE_ISO8601_DATE_FORMAT.print(new DateTime(date));
    }

    public Integer getExpiration() {
        return EXPIRATION_PERIOD_IN_SECONDS;
    }
}
