import 'package:flutter/material.dart';
import 'package:front_flutter/view/camera_page.dart';
import 'package:front_flutter/view/challengedetail_page.dart';
import 'package:front_flutter/view/itemdetail_page.dart';

class HomePage extends StatefulWidget {
  HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}
class _HomePageState extends State<HomePage> {
  var tab = 0;
  final PageController _pageController = PageController();

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
              leading: Icon(Icons.message),
              title: Text('Messages'),
              onTap: () {
                Navigator.pop(context);
              },
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
          ],
        ),
      ),
      body:PageView(
        controller: _pageController,
        children: [Tab1(), Tab2(), Tab3(), Tab4()],
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
            BottomNavigationBarItem(
              backgroundColor: Colors.deepPurpleAccent,
                icon: Icon(Icons.map),
                label: 'Map',
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

class _Tab1State extends State<Tab1> {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          margin: EdgeInsets.all(20),
          child: Column(
            children: [
              Text(
                'Total : 20',
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
  const Tab2({super.key});

  @override
  Widget build(BuildContext context) {
    return Column(
      children:[
          Expanded(
            child: ListView.builder(
                itemCount: 30,
                itemBuilder: (context,i){
                  return Card(
                    child: InkWell(
                      child: ListTile(
                        leading: Icon(Icons.star),
                        title: Text('Challenge $i'),
                        subtitle: Text('Challenge $i'),
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
class Tab4 extends StatelessWidget {
  const Tab4({super.key});

  @override
  Widget build(BuildContext context) {
    return const Placeholder();
  }
}