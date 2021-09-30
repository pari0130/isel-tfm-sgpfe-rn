import 'dart:async';

import 'package:shared_preferences/shared_preferences.dart';

import '../core/utils/cache/cache.dart';
import '../modules/common/data/repositories/queue_repository.dart';
import '../modules/common/data/repositories/service_repository.dart';
import '../modules/common/data/repositories/user_repository.dart';
import 'types.dart';

class FeiApplication {
  static Map<String, dynamic> _repositories;
  static Map<String, FutureOr<dynamic>> _components;

  static FeiApplication _instance;

  factory FeiApplication() {
    _instance ??= FeiApplication._init();

    return _instance;
  }

  FeiApplication._init() {
    _initComponents();
    _initRepositories();
  }

  static FeiApplication getInstance() {
    return _instance;
  }

  void _initComponents() {
    _components = <String, FutureOr<dynamic>>{
      ComponentType.SHARED_PREFERENCE_COMPONENT:
          SharedPreferences.getInstance(),
      ComponentType.CACHE_COMPONENT: Cache()
    };
  }

  void _initRepositories() {
    _repositories = <String, dynamic>{
      RepositoryType.USER_REPOSITORY:
          UserRepository(cache: getComponent(ComponentType.CACHE_COMPONENT)),
      RepositoryType.SERVICE_REPOSITORY: ServiceRepository(),
      RepositoryType.QUEUE_REPOSITORY: QueueRepository()
    };
  }

  dynamic getRepository(String repository) {
    return _repositories[repository];
  }

  dynamic getComponent(String component) {
    return _components[component];
  }
}
