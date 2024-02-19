import 'dart:async';
import 'package:flutter/material.dart';
import 'package:front_flutter/view/challengemake_page.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:provider/provider.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import '../callapi.dart';


class ItemDetail extends StatefulWidget {
   ItemDetail({super.key});

  @override
  State<ItemDetail> createState() => _ItemDetailState();
}

class _ItemDetailState extends State<ItemDetail> {

  late GoogleMapController mapController;
  List<Marker> markers = [];
  Map<String, dynamic> data ={};

  Future<void> locateRequest(String token) async {
    try {
      String mytoken = token;
      final response = await http.get(
        Uri.parse('http://34.47.91.250:8080/api/v1/2/location'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $mytoken',
        },
      );

      if (response.statusCode == 200) {
        print('API Response:${jsonDecode(response.body)}');
        setState(() {
          data = jsonDecode(response.body);
        });
        // 여기서 응답을 처리하거나 상태를 업데이트할 수 있습니다.
      } else {
        print('API Request Failed with Status Code: ${response.statusCode}');
        // 실패한 경우에 대한 처리를 추가할 수 있습니다.
      }
    } catch (error) {
      print('Error during API Request: $error');
    }
  }

  @override
  void initState() {
    super.initState();
    locateRequest(Provider
        .of<Store1>(context, listen: false)
        .token);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.of(context).push(
              MaterialPageRoute(builder: (context) => ChallengeMake()));
        },
        child: const Icon(Icons.add),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
      appBar: AppBar(
        actions: [],
      ),
      body: ListView(
          children: [
            Container(
                child: Image.asset('assets/images/sample.jpg') //여기에 사진 받아야함
            ),
            Container(
              width: 100,
              height: 100,
              decoration: BoxDecoration(
                color: Colors.blue,
              ),
              child: Text('$data',
                style: TextStyle(
                  fontSize: 20,
                  color: Colors.black,
                ),
              ),
            ),
            Container(
              width: 100,
              height: 300,
              child: GoogleMap(
                onMapCreated: _onMapCreated,
                initialCameraPosition: CameraPosition(
                  target: LatLng(37, 127),
                  zoom: 12,
                ),
                markers:
                Set.from(markers),
              ),
            ),
          ]
      ),
    );
  }

  void _onMapCreated(GoogleMapController controller) {
    mapController = controller;
    // 이미지 데이터로 마커 생성
    List<dynamic> images = data['images'];

    // 기존 마커들 유지
    markers.clear();

    for (int i = 0; i < images.length; i++) {
      addMarker(
        LatLng(images[i]['location']['latitude'],
            images[i]['location']['longitude']),
        'Marker ${images[i]['imageId']}',
      );
    }
  }

  void addMarker(LatLng position, String markerId) {
    final MarkerId id = MarkerId(markerId);
    final Marker marker = Marker(
      markerId: id,
      position: position,
      infoWindow: InfoWindow(title: markerId),
    );

    setState(() {
      markers.add(marker);
    });
  }
}