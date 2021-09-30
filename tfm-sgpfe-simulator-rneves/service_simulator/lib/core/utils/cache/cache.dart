//TODO delete
class Cache {
  Map<String, dynamic> _cache;

  Cache() {
    _cache = Map<String, dynamic>();
  }

  dynamic read(String key) {
    print("Cache read $_cache");
    return _cache[key];
  }

  void write(
    String key,
    dynamic value,
  ) {
    _cache[key] = value;
    print("Cache write $key $value");
  }

  bool contains(String key) {
    return _cache.containsKey(key);
  }

  void reset() {
    _cache.clear();
  }
}
