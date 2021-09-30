import '../../domain/entities/queue.dart';
import '../../domain/entities/service.dart';
import '../../domain/entities/service_post_office.dart';
import '../datasource/api/service_api.dart';

class ServiceRepository {
  Future<List<Service>> getServices(token) async {
    try {
      return ServiceApi.getServices(token);
    } catch (error) {
      return [];
    }
  }

  Future<Service> getService(token) async {
    try {
      return ServiceApi.getService(token);
    } catch (error) {
      return null;
    }
  }

  Future<List<ServicePostOffice>> getServicePostOffices(
      int serviceId, token) async {
    try {
      return ServiceApi.getServicePostOffices(serviceId, token);
    } catch (error) {
      return [];
    }
  }

  Future<List<Queue>> getServicePostOfficeQueues(
      int postOfficeId, token) async {
    try {
      return ServiceApi.getServicePostOfficeQueues(postOfficeId, token);
    } catch (error) {
      return [];
    }
  }

  Future<bool> createService(name, description, token) async {
    try {
      return ServiceApi.createService(name, description, token);
    } catch (error) {
      return false;
    }
  }

  Future<bool> createServicePostOffice(
      description, latitude, longitude, address, serviceId, token) async {
    try {
      return ServiceApi.createServicePostOffice(
          description, latitude, longitude, address, serviceId, token);
    } catch (error) {
      return false;
    }
  }

  Future<bool> createServicePostOfficeQueue(
      name,
      description,
      letter,
      type,
      activeServers,
      maxAvailable,
      servicePostOfficeId,
      tolerance,
      token) async {
    try {
      return ServiceApi.createServicePostOfficeQueue(
          name,
          description,
          letter,
          type,
          activeServers,
          maxAvailable,
          servicePostOfficeId,
          tolerance,
          token);
    } catch (error) {
      return false;
    }
  }

  Future<bool> updateService(id, name, description, token) async {
    try {
      return ServiceApi.updateService(id, name, description, token);
    } catch (error) {
      return false;
    }
  }

  Future<bool> updateServicePostOffice(
      id, description, latitude, longitude, address, token) async {
    try {
      return ServiceApi.updateServicePostOffice(
          id, description, latitude, longitude, address, token);
    } catch (error) {
      return false;
    }
  }

  Future<bool> updateServicePostOfficeQueue(id, name, description, letter, type,
      activeServers, maxAvailable, tolerance, token) async {
    try {
      return ServiceApi.updateServicePostOfficeQueue(id, name, description,
          letter, type, activeServers, maxAvailable, tolerance, token);
    } catch (error) {
      return false;
    }
  }

  Future<bool> deleteService(id, token) async {
    try {
      return ServiceApi.deleteService(id, token);
    } catch (error) {
      return false;
    }
  }

  Future<bool> deleteServicePostOffice(id, token) async {
    try {
      return ServiceApi.deleteServicePostOffice(id, token);
    } catch (error) {
      return false;
    }
  }

  Future<bool> deleteServicePostOfficeQueue(id, token) async {
    try {
      return ServiceApi.deleteServicePostOfficeQueue(id, token);
    } catch (error) {
      return false;
    }
  }
}
