import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../../../../common/domain/entities/ticket.dart';
import '../../../../../common/presentation/widgets/common/loading_indicator.dart';
import '../../../bloc/queue/queue_bloc.dart';
import '../../../bloc/queue/queue_event.dart';
import '../../../bloc/queue/queue_state.dart';

class QueueWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _QueueStateWidget();
}

class _QueueStateWidget extends State<QueueWidget> {
  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final Map arguments = ModalRoute.of(context).settings.arguments as Map;
    return BlocProvider(
      create: (context) {
        return BlocProvider.of<QueueBloc>(context)
          ..add(QueueFetch(postOfficeId: 0));
      },
      //TODO ADD LISTENER
      child: BlocBuilder<QueueBloc, QueueState>(
        buildWhen: (previous, current) {
          return current is! QueueAttended && current is! QueueServed;
        },
        builder: (context, state) {
          if (state is QueueFetched) {
            return _buildQueueView(state.tickets);
          }
          return Container(
            child: LoadingIndicator(color: Color(0xFF1766A6)),
          );
        },
      ),
    );
  }

  Widget _buildQueueView(List<Ticket> tickets) {
    return Container(
      child: GridView.builder(
        itemCount: tickets.length,
        itemBuilder: (context, index) {
          return _buildQueueCard(tickets[index]);
        },
        gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
          maxCrossAxisExtent: 350.0,
          crossAxisSpacing: 10.0,
          mainAxisSpacing: 10.0,
        ),
      ),
    );
  }

  Map<int, int> _nextTicketMap = Map<int, int>();
  Map<int, bool> _servingTicketMap = Map<int, bool>();
  Map<int, String> _servingTicketTextMap = Map<int, String>();

  //TEMPORARY
  Map<int, DateTime> _nextTicketDurationMap = Map<int, DateTime>();

  static const int _TOLERANCE = 30;

  void _addToNextTicket(id, number) {
    if (!_nextTicketMap.containsKey(id)) {
      _nextTicketMap[id] = number;
      _nextTicketDurationMap[id] = null;
      _servingTicketMap[id] = false;
      _servingTicketTextMap[id] = "Serve Queue";
    }
  }

  Widget _buildQueueCard(Ticket ticket) {
    _addToNextTicket(ticket.id, ticket.number);
    _callNext() {
      _nextTicketMap[ticket.id] = _nextTicketMap[ticket.id] + 1;
      BlocProvider.of<QueueBloc>(context).add(QueueAttend(queueId: ticket.id));
      _nextTicketDurationMap[ticket.id] = DateTime.now();
    }

    _nextTicket() {
      if (_nextTicketDurationMap[ticket.id] != null) {
        DateTime dateNow = DateTime.now();
        int difference =
            dateNow.difference(_nextTicketDurationMap[ticket.id]).inSeconds;
        if (difference > _TOLERANCE)
          _callNext();
        else
          Scaffold.of(context).showSnackBar(
            SnackBar(
                content: Text(
                  'You must wait ${30 - difference} seconds before calling the next client!',
                  style: TextStyle(color: Colors.black),
                ),
                backgroundColor: Colors.yellow[300]),
          );
      } else if (_servingTicketMap[ticket.id])
        _callNext();
      else
        Scaffold.of(context).showSnackBar(
          SnackBar(
              content: Text(
                'Queue not being served',
                style: TextStyle(color: Colors.black),
              ),
              backgroundColor: Colors.yellow[300]),
        );
    }

    _serving() {
      _servingTicketMap[ticket.id] = !_servingTicketMap[ticket.id];
      _servingTicketTextMap[ticket.id] =
          _servingTicketMap[ticket.id] ? "Leave Queue" : "Serve Queue";
      BlocProvider.of<QueueBloc>(context).add(QueueServing(
          queueId: ticket.id, serving: _servingTicketMap[ticket.id]));
    }

    return Padding(
      padding: EdgeInsets.symmetric(horizontal: 4.0),
      child: Card(
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(3.0),
        ),
        child: Container(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Expanded(
                flex: 0,
                child: Container(
                  child: Image.asset(
                    'assets/background2.jpg',
                    fit: BoxFit.fill,
                  ),
                ),
              ),
              Expanded(
                flex: 1,
                child: Container(
                  alignment: Alignment.centerLeft,
                  margin: EdgeInsets.only(left: 7.0),
                  child: Text(
                    ticket.name,
                    textAlign: TextAlign.left,
                    style: TextStyle(
                      fontFamily: "Oswald Medium",
                      fontWeight: FontWeight.normal,
                      color: Color(0xFF1766A6),
                      fontSize: 20.0,
                    ),
                  ),
                ),
              ),
              Expanded(
                flex: 0,
                child: Container(
                  margin: EdgeInsets.only(left: 7.0),
                  child: Text(
                    "Number: ${_nextTicketMap[ticket.id]}",
                    textAlign: TextAlign.left,
                    style: TextStyle(
                      fontFamily: "Oswald Medium",
                      fontWeight: FontWeight.normal,
                      color: Colors.black,
                      fontSize: 16.0,
                    ),
                  ),
                ),
              ),
              Expanded(
                flex: 1,
                child: Row(
                  children: [
                    Container(
                      padding: EdgeInsets.only(right: 20.0),
                      alignment: Alignment.centerRight,
                      child: RaisedButton(
                        textColor: Colors.white,
                        padding: const EdgeInsets.all(0.0),
                        onPressed: () {
                          setState(() {
                            _serving();
                          });
                        },
                        child: Container(
                          padding: const EdgeInsets.all(10.0),
                          decoration: const BoxDecoration(
                            gradient: LinearGradient(
                              colors: <Color>[
                                Color(0xFF1766A6),
                                Color(0xFF0D47A1),
                                Color(0xFF1976D2),
                              ],
                            ),
                          ),
                          child: Text(
                            _servingTicketTextMap[ticket.id],
                            style: TextStyle(
                              fontSize: 16.0,
                            ),
                          ),
                        ),
                      ),
                    ),
                    Container(
                      padding: EdgeInsets.only(right: 20.0),
                      alignment: Alignment.centerRight,
                      child: RaisedButton(
                        textColor: Colors.white,
                        padding: const EdgeInsets.all(0.0),
                        onPressed: () {
                          setState(() {
                            _nextTicket();
                          });
                        },
                        child: Container(
                          padding: const EdgeInsets.all(10.0),
                          decoration: const BoxDecoration(
                            gradient: LinearGradient(
                              colors: <Color>[
                                Color(0xFF1766A6),
                                Color(0xFF0D47A1),
                                Color(0xFF1976D2),
                              ],
                            ),
                          ),
                          child: Text(
                            "Call Next",
                            style: TextStyle(
                              fontSize: 16.0,
                            ),
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
