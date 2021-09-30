import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:service_simulator/core/utils/constants.dart';
import '../../../../../common/presentation/widgets/common/loading_indicator.dart';
import '../../../../../common/domain/entities/service.dart';

import '../../../../../../config/routes.dart';
import '../../../bloc/service/service_bloc.dart';
import '../../../bloc/service/service_event.dart';
import '../../../bloc/service/service_state.dart';

class AdminServiceWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        return BlocProvider.of<AdminServiceBloc>(context)..add(ServiceFetch());
      },
      child: BlocConsumer<AdminServiceBloc, AdminServiceState>(
        listener: (context, state) {
          if (state is ServiceCreated) {
            var text = "Service Not Created";
            var color = Theme.of(context).errorColor;
            if (state.success) {
              text = "Service Created";
              color = Colors.green;
              BlocProvider.of<AdminServiceBloc>(context).add(ServiceFetch());
              _cleanUpControllers();
            }

            Scaffold.of(context).showSnackBar(
              SnackBar(
                content: Text(text),
                backgroundColor: color,
              ),
            );
          }
        },
        buildWhen: (previous, current) => current is! ServiceCreated,
        builder: (context, state) {
          if (state is ServiceFetched) {
            return _buildServicesView(state.services, context);
          }
          return Container(
            child: LoadingIndicator(color: Color(0xFF1766A6)),
          );
        },
      ),
    );
  }

  Widget _buildServicesView(List<Service> services, context) {
    return Container(
      child: GridView.builder(
        itemCount: services.length + 1,
        itemBuilder: (context, index) {
          if (index == services.length) return _buildServiceCardCreate(context);
          return _buildServiceCard(services[index], context);
        },
        gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
          maxCrossAxisExtent: 300.0,
          crossAxisSpacing: 10.0,
          mainAxisSpacing: 10.0,
        ),
      ),
    );
  }

  Widget _buildServiceCardCreate(context) {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: 4.0),
      child: InkWell(
        onTap: () async => {
          showDialog(
            context: context,
            builder: (BuildContext bcontext) {
              return _popupCreate(context);
            },
          ),
        },
        child: Card(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(3.0),
          ),
          child: Container(
              alignment: Alignment.center,
              child: Icon(Icons.add, color: Colors.grey, size: 30)),
        ),
      ),
    );
  }

  final _serviceNameController = TextEditingController();
  final _serviceDescriptionController = TextEditingController();

  void _cleanUpControllers() {
    _serviceNameController.clear();
    _serviceDescriptionController.clear();
  }

  Widget _popupCreate(context) {
    Widget cancelButton = FlatButton(
      child: Text(
        "Cancel",
        style: TextStyle(
            fontFamily: 'Oswald Bold',
            fontSize: 15,
            letterSpacing: 1.2,
            fontStyle: FontStyle.normal,
            color: Colors.redAccent),
      ),
      onPressed: () {
        Navigator.of(context).pop();
      },
    );

    Widget createButton = FlatButton(
      child: Text(
        "Create",
        style: TextStyle(
            fontFamily: 'Oswald Bold',
            fontSize: 15,
            letterSpacing: 1.2,
            fontStyle: FontStyle.normal,
            color: Theme.of(context).primaryColor),
      ),
      onPressed: () {
        BlocProvider.of<AdminServiceBloc>(context).add(ServiceCreate(
            name: _serviceNameController.text,
            description: _serviceDescriptionController.text));
        Navigator.of(context).pop();
      },
    );

    AlertDialog alert = AlertDialog(
      title: Text("New Service",
          style: TextStyle(
              fontFamily: "Lato Regular",
              fontWeight: FontWeight.bold,
              letterSpacing: 0.8,
              color: Theme.of(context).primaryColor,
              fontSize: 18.0)),
      content: Container(
        width: 500, //TODO use mediaquery
        child: Column(
          mainAxisSize: MainAxisSize.min,
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            TextFormField(
              enabled: true,
              cursorColor: Theme.of(context).primaryColor,
              style: TextStyle(
                color: Theme.of(context).primaryColor,
              ),
              decoration: InputDecoration(
                hintText: 'Name',
                enabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                disabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                focusColor: Theme.of(context).primaryColor,
                hintStyle: TextStyle(
                    fontSize: 15,
                    fontWeight: FontWeight.normal,
                    color: Theme.of(context).primaryColor),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                focusedBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
              ),
              controller: _serviceNameController,
            ),
            SizedBox(height: 10),
            TextFormField(
              enabled: true,
              cursorColor: Theme.of(context).primaryColor,
              style: TextStyle(
                color: Theme.of(context).primaryColor,
              ),
              minLines: 4,
              maxLines: 6,
              decoration: InputDecoration(
                hintText: 'Description',
                enabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                disabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                focusColor: Theme.of(context).primaryColor,
                hintStyle: TextStyle(
                    fontSize: 15,
                    fontWeight: FontWeight.normal,
                    color: Theme.of(context).primaryColor),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                focusedBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
              ),
              controller: _serviceDescriptionController,
            ),
          ],
        ),
      ),
      actions: [cancelButton, createButton],
    );
    return alert;
  }

  Widget _buildServiceCard(Service service, context) {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: 4.0),
      child: InkWell(
        onTap: () => {
          Navigator.of(context).pushNamed(
            FEIRoutes.SERVICE_DETAILS_ADMIN,
            arguments: <String, dynamic>{
              "service_id": service.id,
              "service_name": service.name,
              "service_description": service.description
            },
          ).then(
            (value) {
              if (value != null && value == SERVICE_CHANGED)
                BlocProvider.of<AdminServiceBloc>(context).add(ServiceFetch());
            },
          ),
        },
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
                      'assets/background.png',
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
                      service.name,
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
                  flex: 1,
                  child: Container(
                    alignment: Alignment.centerLeft,
                    margin: EdgeInsets.only(left: 7.0),
                    child: Text(
                      service.description,
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
              ],
            ),
          ),
        ),
      ),
    );
  }
}
