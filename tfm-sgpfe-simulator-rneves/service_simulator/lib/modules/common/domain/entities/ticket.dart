import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

class Ticket extends Equatable {
  final int id;
  final int number;
  final String letter;
  final String name;
  final int queueId;

  Ticket(
      {@required this.id,
      @required this.number,
      @required this.letter,
      @required this.name,
      @required this.queueId});

  @override
  List<Object> get props => [id, number, letter, name, queueId];
}
