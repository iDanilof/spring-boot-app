package com.example.springbootapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/*
 *
 *
 * Copyright (c) 2022. Inditex
 */
public class IconicJavaClientgetOrdersPRE {
  private static final String ScApiHost = "https://sellercenter-api-preprod.theiconic.com.au/";

  private static final String HASH_ALGORITHM = "HmacSHA256";

  private static final String CHAR_UTF_8 = "UTF-8";

  private static final String CHAR_ASCII = "ASCII";

  public static void main(final String[] args) {
    final Map<String, String> params = new HashMap<String, String>();
    params.put("UserID", "manueldog@ext.inditex.com");
    params.put("Timestamp", getCurrentTimestamp());
    params.put("Version", "1.0");
    params.put("Action", "GetOrders");
    params.put("Format", "JSON");
    params.put("CreatedAfter", OffsetDateTime.parse("2022-01-01T00:00:00+02").toString());
    params.put("CreatedBefore", OffsetDateTime.parse("2022-09-08T10:59:59+02").toString());
    params.put("Offset", "0");
    params.put("Limit", "100");
    params.put("Status", "pending");
    final String apiKey = "c11a227389de829366a094913eb6e4938712352a";
    // final String XML =
    // "<?xml version=\"1.0\"
    // encoding=\"UTF-8\"?><Request><Product><SellerSku>4105382173aaee4</SellerSku><Price>12</Price></Product></Request>";
    final var XML = "";
    final String out = getSellercenterApiResponse(params, apiKey, XML); // provide XML as an empty string when not needed
    System.out.println(out); // print out the XML response
  }

  /**
   * calculates the signature and sends the request
   *
   * @param params Map - request parameters
   * @param apiKey String - user's API Key
   * @param XML String - Request Body
   */
  public static String getSellercenterApiResponse(final Map<String, String> params, final String apiKey, final String XML) {
    String queryString = "";
    String Output = "";
    HttpURLConnection connection = null;
    URL url = null;
    final Map<String, String> sortedParams = new TreeMap<String, String>(params);
    System.out.println(sortedParams);
    queryString = toQueryString(sortedParams);
    final String signature = hmacDigest(queryString, apiKey, HASH_ALGORITHM);
    queryString = queryString.concat("&Signature=".concat(signature));
    final String request = ScApiHost.concat("?".concat(queryString));
    try {
      url = new URL(request);
      connection = (HttpURLConnection) url.openConnection();
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setInstanceFollowRedirects(false);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      connection.setRequestProperty("charset", CHAR_UTF_8);
      connection.setUseCaches(false);
      if (!XML.equals("")) {
        connection.setRequestProperty("Content-Length", "" + XML.getBytes().length);
        final DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(XML);
        wr.flush();
        wr.close();
      }
      String line;
      final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      while ((line = reader.readLine()) != null) {
        Output += line + "\n";
      }
    } catch (final Exception e) {
      e.printStackTrace();
    }
    System.out.println(Output);
    System.out.println(queryString);
    System.out.println(request);
    return Output;
  }

  /**
   * generates hash key
   *
   * @param msg
   * @param keyString
   * @param algo
   * @return string
   */
  private static String hmacDigest(final String msg, final String keyString, final String algo) {
    String digest = null;
    try {
      final SecretKeySpec key = new SecretKeySpec((keyString).getBytes(StandardCharsets.UTF_8), algo);
      final Mac mac = Mac.getInstance(algo);
      mac.init(key);
      final byte[] bytes = mac.doFinal(msg.getBytes(StandardCharsets.US_ASCII));
      final StringBuffer hash = new StringBuffer();
      for (int i = 0; i < bytes.length; i++) {
        final String hex = Integer.toHexString(0xFF & bytes[i]);
        if (hex.length() == 1) {
          hash.append('0');
        }
        hash.append(hex);
      }
      digest = hash.toString();
    } catch (final InvalidKeyException e) {
      e.printStackTrace();
    } catch (final NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return digest;
  }

  /**
   * build querystring out of params map
   *
   * @param data map of params
   * @return string
   * @throws UnsupportedEncodingException
   */
  private static String toQueryString(final Map<String, String> data) {
    String queryString = "";
    final StringBuffer params = new StringBuffer();
    for (final Map.Entry<String, String> pair : data.entrySet()) {
      params.append(URLEncoder.encode(pair.getKey(), StandardCharsets.UTF_8) + "=");
      params.append(URLEncoder.encode(pair.getValue(), StandardCharsets.UTF_8) + "&");
    }
    if (params.length() > 0) {
      params.deleteCharAt(params.length() - 1);
    }
    queryString = params.toString();
    return queryString;
  }

  /**
   * returns the current timestamp
   *
   * @return current timestamp in ISO 8601 format
   */
  private static String getCurrentTimestamp() {
    final TimeZone tz = TimeZone.getTimeZone("UTC");
    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
    df.setTimeZone(tz);
    final String nowAsISO = df.format(new Date());
    return nowAsISO;
  }
}

