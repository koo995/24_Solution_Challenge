import 'dart:convert';
import 'package:front_flutter/callapi.dart';
import 'package:front_flutter/view/missioncamera_page.dart';
import 'package:provider/provider.dart';
import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:http/http.dart' as http;

class ChallengeDetail extends StatefulWidget {
   ChallengeDetail( {super.key, required this.i});

  final int i;

  @override
  State<ChallengeDetail> createState() => _ChallengeDetailState();
}

class _ChallengeDetailState extends State<ChallengeDetail> {
  late GoogleMapController mapController;
  List<Marker> markers = [];
  var data = {};
  var data2 ={};
  Future<void> _apiRequest(String token) async {
    try {
      String mytoken = token;
      final response = await http.get(
        Uri.parse('http://34.47.91.250:8080/api/v1/mission/${widget.i+1}'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $mytoken',
        },
      );
      if (response.statusCode == 200) {
        print('API Response: ${jsonDecode(utf8.decode(response.bodyBytes))}');
        var responseData = jsonDecode(utf8.decode(response.bodyBytes));

        setState(() {
          data = responseData;
        });

        // _apiRequest 호출 후에 data2가 초기화된 후에 locateRequest 호출
        await locateRequest(
          Provider.of<Store1>(context, listen: false).token,
          responseData['speciesId'].toString(),
        );
      } else {
        print('API Request Failed with Status Code: ${response.statusCode}');
        // 실패한 경우에 대한 처리를 추가할 수 있습니다.
      }
    } catch (error) {
      print('Error during API Request: $error');
    }
  }
  Future<void> locateRequest(String token,String index) async {
    try {
      String mytoken = token;
      final response = await http.get(
        Uri.parse('http://34.47.91.250:8080/api/v1/$index/location'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $mytoken',
        },
      );

      if (response.statusCode == 200) {
        print('Locate API Response:${jsonDecode(response.body)}');
        setState(() {
          data2 = jsonDecode(response.body);
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

  Future<void> _initData() async {
    // imageRequest 호출
    await _apiRequest(
      Provider.of<Store1>(context, listen: false).token
    );
  }
  @override
  void initState() {
    super.initState();
    _initData();
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: FloatingActionButton(
        backgroundColor: Colors.lightGreen,
        onPressed: () {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => MCameraPage(index: widget.i+1)),
          );
        },
        elevation: 0.5,
        child: Icon(Icons.camera),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
      appBar: AppBar(
        actions: [],
      ),
      body: ListView(
        children: [
                    Container(
            child: Column(
              children: [
                Text('${data['title']}',
                  style: TextStyle(
                    fontSize: 30,
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                Text('설명 :  ${data['description']}',
                  style: TextStyle(
                    fontSize: 20,
                    color: Colors.black,
                  ),
                ),
              ],
            ),
          ),
          Container(
            margin: EdgeInsets.all(20),
            width: 250 ,
            height: 250,
            decoration: BoxDecoration(boxShadow: [
              BoxShadow(
                color: Colors.grey.withOpacity(0.5),
                spreadRadius: 1,
                blurRadius: 7,
                offset: Offset(0, 5),
              )
            ]),
            child: Image.network(
              '${data['imageUrl']}',
              loadingBuilder: (BuildContext context, Widget child, ImageChunkEvent? loadingProgress) {
                if (loadingProgress == null) {
                  return child;
                } else {
                  return Center(
                    child: CircularProgressIndicator(
                      value: loadingProgress.expectedTotalBytes != null
                          ? loadingProgress.cumulativeBytesLoaded / (loadingProgress.expectedTotalBytes ?? 1)
                          : null,
                    ),
                  );
                }
              },
            ), //여기에 사진 받아야함
          ),
          Center(
            child: Container(
              child:Text('학명 :  ${data['scientificName']}',
                style: TextStyle(
                  fontSize: 20,
                  color: Colors.black,
                ),
              ),
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
                zoom: 13,
              ),
              markers:
              Set.from(markers),
            ),
          ),
        ],
      )
    );
  }

  void _onMapCreated(GoogleMapController controller) async {
    mapController = controller;

    // 데이터를 가져오는 데 딜레이를 주기 위해 Future.delayed 사용
    await Future.delayed(Duration(seconds: 1));
print('로그찍어보기');
    // 나머지 코드 계속 진행
    List<dynamic>? images = data2['images'];
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
