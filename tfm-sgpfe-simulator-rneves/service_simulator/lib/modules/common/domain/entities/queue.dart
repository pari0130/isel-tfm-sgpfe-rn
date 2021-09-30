import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

class Queue extends Equatable {
  final int id;
  final String letter;
  final String name;
  final String description;
  final int servicePostOfficeId;
  final int activeServers;
  final int type;
  final int maxAvailable;
  final bool tolerance;

  Queue(
      {@required this.id,
      @required this.description,
      @required this.letter,
      @required this.name,
      @required this.activeServers,
      @required this.type,
      @required this.maxAvailable,
      @required this.tolerance,
      @required this.servicePostOfficeId});

  @override
  List<Object> get props => [
        id,
        letter,
        name,
        description,
        servicePostOfficeId,
        activeServers,
        type,
        maxAvailable,
        tolerance
      ];
}
