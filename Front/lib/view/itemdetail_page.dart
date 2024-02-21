import 'dart:async';
import 'package:flutter/material.dart';
import 'package:front_flutter/view/challengemake_page.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:provider/provider.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import '../callapi.dart';

class ItemDetail extends StatefulWidget {
  var index;

  ItemDetail({super.key, required this.index});

  @override
  State<ItemDetail> createState() => _ItemDetailState();
}

class _ItemDetailState extends State<ItemDetail> {
  late GoogleMapController mapController;
  List<Marker> markers = [];
  Map<String, dynamic> data = {};
  Map data2 = {};

  Future<void> locateRequest(String token, String id) async {
    try {
      String mytoken = token;
      final response = await http.get(
        Uri.parse('http://34.47.91.250:8080/api/v1/$id/location'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $mytoken',
        },
      );

      if (response.statusCode == 200) {
        print('Locate API Response:${jsonDecode(response.body)}');
        print('$id');
        setState(() {
          data = jsonDecode(response.body);
        });
        // 여기서 응답을 처리하거나 상태를 업데이트할 수 있습니다.
      } else {
        print('Locate API Request Failed with Status Code: ${response.statusCode}');
        // 실패한 경우에 대한 처리를 추가할 수 있습니다.
      }
    } catch (error) {
      print('Error during API Request: $error');
    }
  }

  Future<void> imageRequest(String token, String id) async {
    try {
      String mytoken = token;
      final response = await http.get(
        Uri.parse('http://34.47.91.250:8080/api/v1/image/$id'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $mytoken',
        },
      );

      if (response.statusCode == 200) {
        print('Img API Response:${jsonDecode(response.body)}');
        setState(() {
          data2 = jsonDecode(response.body);
        });

        // imageRequest에서 데이터를 가져온 후 locateRequest 호출
        await locateRequest(
          Provider.of<Store1>(context, listen: false).token,
          data2['speciesId'].toString(),
        );
      } else {
        print('API Request Failed with Status Code: ${response.statusCode}');
        print('http://34.47.91.250:8080/api/v1/image/$id');
        // 실패한 경우에 대한 처리를 추가할 수 있습니다.
      }
    } catch (error) {
      print('Error during API Request: $error');
    }
  }

  @override
  void initState() {
    super.initState();
    _initData();
  }

  Future<void> _initData() async {
    // imageRequest 호출
    await imageRequest(
      Provider.of<Store1>(context, listen: false).token,
      Provider.of<Store1>(context, listen: false).id.toString(),
    );
  }

  @override
  Widget build(BuildContext context) {
    print('인덱스는 ${widget.index}');
    return Scaffold(
      floatingActionButton: FloatingActionButton(
        backgroundColor: Colors.lightGreen,
        onPressed: () {
          Navigator.of(context).push(
            MaterialPageRoute(builder: (context) => ChallengeMake(speciesId: data2['speciesId'], imageId: data2['imageId'])),
          );
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
              child: Center(child: Text('${data2['scientificName']}', style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold),))
          ),
          Center(

            child: Container(
              child: Text(
                '${data['images']?[0]?['createdAt'] ?? "Unknown date"} 에 발견되었습니다',
                style: TextStyle(
                  fontSize: 15,
                  color: Colors.black,
                ),
              ),
            ),
          ),
          Container(
            decoration: BoxDecoration(
              boxShadow: [
                BoxShadow(
                  color: Colors.grey.withOpacity(0.5),
                  spreadRadius: 1,
                  blurRadius: 7,
                  offset: Offset(0, 5),
                ),
              ],
            ),
            margin: EdgeInsets.all(20),
            child: Image(
              image: NetworkImage('${data2['url']}'),
              width: 100,
              height: 300,
              fit: BoxFit.contain,
            ),
          ),

          Container(
            margin: EdgeInsets.all(20),
            width: 100,
            height: 400,
            child: GoogleMap(
              onMapCreated: _onMapCreated,
              initialCameraPosition: CameraPosition(
                target: LatLng(
                  37.624405555555555,
                  127.07709166666666,
                ),
                zoom: 14,
              ),
              markers: Set.from(markers),
            ),
          ),
        ],
      ),
    );
  }

  void _onMapCreated(GoogleMapController controller) async {
    mapController = controller;

    // 데이터를 가져오는 데 딜레이를 주기 위해 Future.delayed 사용
    await Future.delayed(Duration(seconds: 3));
    print('로그찍어보기');
    // 나머지 코드 계속 진행
    List<dynamic>? images = data['images'];
    markers.clear();

    if (images != null) {
      for (int i = 0; i < images.length; i++) {
        if (images[i]['location'] != null &&
            images[i]['location']['latitude'] != null &&
            images[i]['location']['longitude'] != null) {
          addMarker(
            LatLng(
              images[i]['location']['latitude'],
              images[i]['location']['longitude'],
            ),
            'Marker ${images[i]['imageId']}',
          );
        }
      }
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