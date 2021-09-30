import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

abstract class AdminQueueDetailsEvent extends Equatable {
  const AdminQueueDetailsEvent();

  @override
  List<Object> get props => [];
}

class QueueDetailsUpdate extends AdminQueueDetailsEvent {
  final int id;
  final String name;
  final String description;
  final String letter;
  final int type;
  final int activeServers;
  final int maxAvailable;
  final bool tolerance;

  QueueDetailsUpdate({
    @required this.id,
    @required this.name,
    @required this.description,
    @required this.letter,
    @required this.type,
    @required this.activeServers,
    @required this.maxAvailable,
    @required this.tolerance,
  });

  @override
  List<Object> get props => [
        id,
        name,
        description,
        letter,
        type,
        activeServers,
        maxAvailable,
        tolerance
      ];
}

class QueueDetailsDelete extends AdminQueueDetailsEvent {
  final int id;

  QueueDetailsDelete({@required this.id});

  @override
  List<Object> get props => [id];
}
