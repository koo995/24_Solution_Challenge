import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:front_flutter/view/camera_page.dart';
import 'package:front_flutter/view/challengedetail_page.dart';
import 'package:front_flutter/view/itemdetail_page.dart';
import 'package:http/http.dart' as http;
import 'package:provider/provider.dart';
import '../callapi.dart';



class HomePage extends StatefulWidget {
  HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}
class _HomePageState extends State<HomePage> {
  var tab = 0;
  final PageController _pageController = PageController();
  var data =[];
  var data2={};

  Future<void> _refresh() async {
    await _missionRequest(Provider.of<Store1>(context, listen: false).token);
    await _itemRequest(Provider.of<Store1>(context, listen: false).token);
    setState(() {
      print('Refreshed');
    });
  }
  Future<void> _missionRequest(String token) async {
    try {
      String mytoken = token;
      final response = await http.get(
        Uri.parse('http://34.47.91.250:8080/api/v1/mission'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $mytoken',
        },
      );

      if (response.statusCode == 200) {
        print('mission API Response:${jsonDecode(utf8.decode(response.bodyBytes))}');
        setState(() {
          data = jsonDecode(utf8.decode(response.bodyBytes));
        });
        // 여기서 응답을 처리하거나 상태를 업데이트할 수 있습니다.
      } else {
        print('mission API Request Failed with Status Code: ${response.statusCode}');
        // 실패한 경우에 대한 처리를 추가할 수 있습니다.
      }
    } catch (error) {
      print('Error during API Request: $error');
    }
  }
  Future<void> _itemRequest(String token) async {
    try {
      String mytoken = token;
      final response = await http.get(
        Uri.parse('http://34.47.91.250:8080/api/v1/auth/profile'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $mytoken',
        },
      );

      if (response.statusCode == 200) {
        print('item API Response:${jsonDecode(response.body)}');
        setState(() {
          data2 = jsonDecode(response.body);
        });
        // 여기서 응답을 처리하거나 상태를 업데이트할 수 있습니다.
      } else {
        print('item API Request Failed with Status Code: ${response.statusCode}');
        // 실패한 경우에 대한 처리를 추가할 수 있습니다.
      }
    } catch (error) {
      print('Error during API Request: $error');
    }
  }
@override
  void initState() {
    super.initState();
    _missionRequest(Provider.of<Store1>(context, listen: false).token);
    _itemRequest(Provider.of<Store1>(context, listen: false).token);
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text("Home", style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold),),
          actions: [
            IconButton(
              icon: Icon(Icons.search),
              onPressed: () {
              },
            ),
            IconButton(
              icon: Icon(Icons.more_vert),
              onPressed: () {
              },
            ),
          ],
      ),
      drawer: Drawer(
        child: ListView(
          children: [
            DrawerHeader(
              decoration: BoxDecoration(
                color: Colors.lightGreen,
              ),
              child: Text(
                '',
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 24,
                ),
              ),
            ),

            ListTile(
              leading: Icon(Icons.account_circle),
              title: Text('Profile'),
              onTap: () {
                Navigator.pop(context);
              },
            ),
            ListTile(
              leading: Icon(Icons.settings),
              title: Text('Settings'),
              onTap: () {
                Navigator.pop(context);
              },
            ),
            ListTile(
              leading: Icon(Icons.logout, color: Colors.red),
              title: Text('Logout', style: TextStyle(color: Colors.red, fontWeight: FontWeight.bold)),
              onTap: () async {
                Navigator.pop(context);
              },
            ),
          ],
        ),
      ),
      body:RefreshIndicator(
        onRefresh: _refresh,
        child: PageView(
          controller: _pageController,
          children: [Tab1(data2:data2), Tab2(data:data), Tab3(data2:data2)],
          onPageChanged: (index) {
          setState(() {
          tab = index;});
          },
        ),
      ),
        bottomNavigationBar: BottomNavigationBar(
          selectedIconTheme: IconThemeData(color: Colors.lightGreen),
          selectedFontSize: 15,
          currentIndex: tab,
          onTap: (index) {
            setState(() {
              tab = index;
              _pageController.animateToPage(index,
                  duration: Duration(milliseconds: 500), curve: Curves.ease);
            });
          },
          items: [
            BottomNavigationBarItem(

              backgroundColor: Colors.lightGreen,
              icon: Icon(Icons.home),
              label: 'Home',

            ),
            BottomNavigationBarItem(
              backgroundColor: Colors.lightGreen,
                icon: Icon(Icons.assignment),
                label: 'Challenge',
            ),
            BottomNavigationBarItem(
              backgroundColor: Colors.lightGreen,
                icon: Icon(Icons.star),
                label: 'Rank',
            ),
                        ],
        ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: Colors.lightGreen,
        onPressed: () {
          Navigator.push(context,MaterialPageRoute(builder: (context)=>CameraPage()));
        },
        child: Icon(Icons.camera),
      )
    );
  }
}

class Tab1 extends StatefulWidget {
  Tab1({super.key, this.data2});

  late var data2;

  @override
  State<Tab1> createState() => _Tab1State();
}

class _Tab1State extends State<Tab1> {
  get total => widget.data2['totalImage'];

  @override
  Widget build(BuildContext context) {
    return RefreshIndicator(
      onRefresh: _refresh,
      child: Column(
        children: [
          Container(
            margin: EdgeInsets.all(20),
            child: Column(
              children: [
                Text('My library', style: TextStyle(fontSize: 20)),
                Container(
                  decoration: BoxDecoration(
                    border: Border(bottom:BorderSide(color: Colors.green, width: 3.0)),
                  ),
                  child: Text(
                    '$total',
                    style: TextStyle(
                      fontSize: 40,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
              ],
            ),
          ),
          Expanded(
            child: GridView.builder(
              gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 3,
                crossAxisSpacing: 5,
                mainAxisSpacing: 5,
              ),
              itemCount: total,
              itemBuilder: (BuildContext context, int index) {
                // Check for null values before accessing properties
                if (widget.data2 != null &&
                    widget.data2['image'] != null &&
                    widget.data2['image']['content'] != null &&
                    widget.data2['image']['content'][index] != null) {
                  return InkWell(
                    onTap: () {
                      Provider.of<Store1>(context, listen: false).setId(
                        widget.data2['image']['content'][index]['imageId'].toString(),
                      );
                      Future.delayed(Duration.zero, () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => ItemDetail(index: index),
                          ),
                        );
                      });
                    },
                    child: Container(
                      decoration: BoxDecoration(
                        border: Border.all(color: Colors.grey),
                        color: Colors.grey[200],
                      ),
                      margin: EdgeInsets.all(5),
                      padding: EdgeInsets.all(5),
                      width: 100,
                      height: 100,
                      child: Center(
                        child: Image(
                          image: NetworkImage(
                            '${widget.data2['image']['content'][index]['fullPath']}',
                          ),
                        ),
                      ),
                    ),
                  );
                } else {
                  // Handle the case where data is null or not in the expected format
                  return Container(
                    child: Text('Error: Invalid data format'),
                  );
                }
              },
            ),
          ),
        ],
      ),
    );
  }
  Future<void> _refresh() async {
    try {
      print('Refreshing Tab1');

      await Future.delayed(Duration(seconds: 2));

      _itemRequest(Provider.of<Store1>(context, listen: false).token);

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Tab1 refreshed')),
      );
    } catch (error) {
      print('Error during refresh: $error');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error during refresh')),
      );
    }
  }
  Future<void> _itemRequest(String token) async {
    try {
      String mytoken = token;
      final response = await http.get(
        Uri.parse('http://34.47.91.250:8080/api/v1/auth/profile'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $mytoken',
        },
      );

      if (response.statusCode == 200) {
        print('item API Response:${jsonDecode(response.body)}');
        setState(() {
          widget.data2 = jsonDecode(response.body);
        });
        // 여기서 응답을 처리하거나 상태를 업데이트할 수 있습니다.
      } else {
        print('item API Request Failed with Status Code: ${response.statusCode}');
        // 실패한 경우에 대한 처리를 추가할 수 있습니다.
      }
    } catch (error) {
      print('Error during API Request: $error');
    }
  }
}

class Tab2 extends StatefulWidget {
  Tab2({super.key,this.data});
  late var  data;


  @override
  State<Tab2> createState() => _Tab2State();
}

class _Tab2State extends State<Tab2> {
  get numchallenge => widget.data.length;


  @override
  Widget build(BuildContext context) {
    return RefreshIndicator(
      onRefresh: _refresh,
      child: Column(
        children:[
          Container(
            margin: EdgeInsets.all(20),
            decoration: BoxDecoration(
              border: Border(
                bottom: BorderSide(
                    width: 3.0,
                  color: Colors.green)),
            ),
            child: Text('Challenge',
                style: TextStyle(fontSize: 30, fontWeight: FontWeight.bold, color: Colors.black,
                )),
          ),
            Expanded(
              child: ListView.builder(
                  itemCount: numchallenge,
                  itemBuilder: (context,i){
                    return Card(
                      child: InkWell(
                        child: ListTile(
                          tileColor: Colors.grey[200],
                          leading: Icon(Icons.check_outlined, color: widget.data[i]['missionComplete'] == true ? Colors.green : Colors.red),
                          title: Text('${widget.data[i]['title']}'),
                          subtitle: Text('성공여부 ${widget.data[i]['missionComplete']}'),
                          onTap: () {
                            Navigator.push(context,
                                MaterialPageRoute(
                                    builder:(context)=>ChallengeDetail(i : i)
                                )
                            );
                            },
                        ),
                      ),
                    );
                  }
              ),
            )
        ]
      ),
    );
  }
  Future<void> _refresh() async {
    try {
      print('Refreshing Tab2');

      await Future.delayed(Duration(seconds: 2));

      _missionRequest(Provider.of<Store1>(context, listen: false).token);

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Tab2 refreshed')),
      );
    } catch (error) {
      print('Error during refresh: $error');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error during refresh')),
      );
    }
  }

  Future<void> _missionRequest(String token) async {
    try {
      String mytoken = token;
      final response = await http.get(
        Uri.parse('http://34.47.91.250:8080/api/v1/mission'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $mytoken',
        },
      );

      if (response.statusCode == 200) {
        print('mission API Response:${jsonDecode(utf8.decode(response.bodyBytes))}');
        setState(() {
          widget.data = jsonDecode(utf8.decode(response.bodyBytes));
        });
        // 여기서 응답을 처리하거나 상태를 업데이트할 수 있습니다.
      } else {
        print('mission API Request Failed with Status Code: ${response.statusCode}');
        // 실패한 경우에 대한 처리를 추가할 수 있습니다.
      }
    } catch (error) {
      print('Error during API Request: $error');
    }
  }
}

class Tab3 extends StatefulWidget {
   Tab3({super.key, this.data2});
late final data2;

  @override
  State<Tab3> createState() => _Tab3State();
}

class _Tab3State extends State<Tab3> {
  get Score => widget.data2['currentScore'];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Rank'),
      ),
      body: RefreshIndicator(
        onRefresh: _refresh,
        child: Center(
          child: Text('$Score',style: TextStyle(fontSize: 30, fontWeight: FontWeight.bold),),
        ),
      ),);
  }
  Future<void> _refresh() async {
    try {
      print('Refreshing Tab3');

      await Future.delayed(Duration(seconds: 2));

      _itemRequest(Provider.of<Store1>(context, listen: false).token);

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Tab3 refreshed')),
      );
    } catch (error) {
      print('Error during refresh: $error');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error during refresh')),
      );
    }
  }
  Future<void> _itemRequest(String token) async {
    try {
      String mytoken = token;
      final response = await http.get(
        Uri.parse('http://34.47.91.250:8080/api/v1/auth/profile'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $mytoken',
        },
      );

      if (response.statusCode == 200) {
        print('item API Response:${jsonDecode(response.body)}');
        setState(() {
          widget.data2 = jsonDecode(response.body);
        });
        // 여기서 응답을 처리하거나 상태를 업데이트할 수 있습니다.
      } else {
        print('item API Request Failed with Status Code: ${response.statusCode}');
        // 실패한 경우에 대한 처리를 추가할 수 있습니다.
      }
    } catch (error) {
      print('Error during API Request: $error');
    }
  }
}
