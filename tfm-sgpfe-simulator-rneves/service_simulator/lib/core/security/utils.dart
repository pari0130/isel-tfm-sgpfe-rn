import 'dart:convert';

import 'package:crypto/crypto.dart';

class SecurityUtils {
  static String computePassword(String password) {
    return computeSHA256Hash(password);
  }

  static String computeSHA256Hash(String data) {
    final bytes = utf8.encode(data);
    final digest = sha256.convert(bytes);

    return digest.toString().toUpperCase();
  }
}
