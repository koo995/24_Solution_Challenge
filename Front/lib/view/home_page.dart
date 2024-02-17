import 'dart:convert';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:google_sign_in/google_sign_in.dart';
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
    _missionRequest(Provider.of<Store1>(context, listen: false).token);
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text("Home"),
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
                color: Colors.blue,
              ),
              child: Text(
                'Drawer Header',
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
                await auth.signOut();
              },
            ),
          ],
        ),
      ),
      body:PageView(
        controller: _pageController,
        children: [Tab1(), Tab2(data:data), Tab3()],
        onPageChanged: (index) {
        setState(() {
        tab = index;});
        },
      ),
        bottomNavigationBar: BottomNavigationBar(
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
              backgroundColor: Colors.deepPurpleAccent,
              icon: Icon(Icons.home),
              label: 'Home',
            ),
            BottomNavigationBarItem(
              backgroundColor: Colors.deepPurpleAccent,
                icon: Icon(Icons.search),
                label: 'Challenge',
            ),
            BottomNavigationBarItem(
              backgroundColor: Colors.deepPurpleAccent,
                icon: Icon(Icons.star),
                label: 'Rank',
            ),
                        ],
        ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.push(context,MaterialPageRoute(builder: (context)=>CameraPage()));
        },
        child: Icon(Icons. camera_alt),
      )
    );
  }
}

class Tab1 extends StatefulWidget {
  const Tab1({super.key});

  @override
  State<Tab1> createState() => _Tab1State();
}
var data =[];
class _Tab1State extends State<Tab1> {
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
  _itemRequest(Provider.of<Store1>(context, listen: false).token);
}
var total = 10;
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          margin: EdgeInsets.all(20),
          child: Column(
            children: [
              Text(
                'Total : ${total}',
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                ),
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  Container(
                    margin: EdgeInsets.all(5),
                    child: ElevatedButton(
                      onPressed: () {},
                      child: Text('All'),
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.all(5),
                    child: ElevatedButton(
                      onPressed: () {},
                      child: Text('Animal'),
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.all(5),
                    child: ElevatedButton(
                      onPressed: () {},
                      child: Text('Plant'),
                    ),
                  ),
                ],
              )
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
            itemCount: 20,
            itemBuilder: (BuildContext context, int index) {
                return InkWell(
                  onTap: () {Navigator.push(context,
                      MaterialPageRoute(
                          builder:(context)=>ItemDetail()
                      )
                  );},
                  child: Container(
                    width: 100,
                    height: 100,
                    color: Colors.blue,
                    child: Center(
                      child: Text(
                        '$index',
                        style: TextStyle(
                          fontSize: 20,
                          color: Colors.white,
                        ),
                      ),
                    ),
                  ),
                );
            }
          ),
        )]
    );
  }
}

class Tab2 extends StatelessWidget {
  Tab2({super.key,this.data});
  final  data;
  get numchallenge => data.length;
  @override
  Widget build(BuildContext context) {
    return Column(
      children:[
          Expanded(
            child: ListView.builder(
                itemCount: numchallenge,
                itemBuilder: (context,i){
                  return Card(
                    child: InkWell(
                      child: ListTile(
                        leading: Icon(Icons.star),
                        title: Text('Challenge $i'),
                        subtitle: Text('성공여부 ${data[i]['missionComplete']}'),
                        onTap: () {
                          Navigator.push(context,
                              MaterialPageRoute(
                                  builder:(context)=>ChallengeDetail()
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
    );
  }
}

class Tab3 extends StatelessWidget {
  const Tab3({super.key});

  @override
  Widget build(BuildContext context) {
    return const Placeholder();
  }
}
