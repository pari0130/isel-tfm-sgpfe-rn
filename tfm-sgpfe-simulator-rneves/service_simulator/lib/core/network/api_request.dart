import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

import '../../config/env_config.dart';

class ApiRequest {
  static Future<dynamic> doPost(Map params, String path, String token) async {
    String host = EnvConfig.getInstance().values.serverUrl;
    Map<String, String> headers = {};
    if (token != null)
      headers[HttpHeaders.authorizationHeader] = "Bearer $token";
    var client = http.Client();
    try {
      var response = await client.post(host + path,
          body: json.encode(params), headers: headers);
      if (response.statusCode != 200) return null;
      return jsonDecode(response.body);
    } finally {
      client.close();
    }
  }

  static Future<dynamic> doGet(String path, String token) async {
    String host = EnvConfig.getInstance().values.serverUrl;
    var client = http.Client();
    try {
      final response = await client.get(Uri.parse(host + path),
          headers: {HttpHeaders.authorizationHeader: "Bearer $token"});
      if (response.statusCode != 200) return null;
      return jsonDecode(response.body);
    } catch (error) {
      print(error);
    } finally {
      client.close();
    }
  }

  static Future<dynamic> doPut(Map params, String path, String token) async {
    String host = EnvConfig.getInstance().values.serverUrl;
    Map<String, String> headers = {};
    if (token != null)
      headers[HttpHeaders.authorizationHeader] = "Bearer $token";

    var client = http.Client();
    try {
      //THIS HAS NO BODY FOR NOW TODO FIX IT
      var response = await client.put(host + path,
          headers: headers, body: params != {} ? json.encode(params) : "");
      if (response.statusCode != 200) return null;
      return jsonDecode(response.body);
    } catch (error) {
      print(error);
    } finally {
      client.close();
    }
  }
}
