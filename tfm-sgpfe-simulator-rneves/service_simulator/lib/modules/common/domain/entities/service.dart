import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

class Service extends Equatable {
  final int id;
  final String name;
  final String description;

  Service(
      {@required this.id,
      @required this.name,
      @required this.description});

  @override
  List<Object> get props => [id, name, description];
}
