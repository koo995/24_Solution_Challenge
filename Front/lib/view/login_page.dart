import 'package:flutter/material.dart';
import 'home_page.dart';

class LoginPage extends StatefulWidget {
   LoginPage({Key? key}) : super(key: key);

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return  MaterialApp(
        debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      home: Scaffold(
        body:
        Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Container(
                margin: EdgeInsets.all(20),
                child: TextField(
                  decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Username',
                  ),
                ),
              ),
              Container(
                margin: EdgeInsets.all(20),
                child: TextField(
                  decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText:'Password',
                  ),
                ),
              ),
              Container(
                margin: EdgeInsets.all(5),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Checkbox(
                      value: false,
                      onChanged: (bool? value) {},
                    ),
                    Text("Remember me"),
                  ],
                )
              ),
              Container(
                margin: EdgeInsets.all(20),
                child: ElevatedButton(
                  onPressed: () {
                    Navigator.of(context).pushAndRemoveUntil(
                      MaterialPageRoute(builder: (context) => HomePage()),(route)=>false
                    );
                  },
                  child: Text('Login'),
                ),
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text("Sign up", style: TextStyle(fontSize: 15),),
                  Text(" | ", style: TextStyle(fontSize: 15),),
                  Text("Forgot ID", style: TextStyle(fontSize: 15),),
                  Text(" | ", style: TextStyle(fontSize: 15),),
                  Text("Forgot PW", style: TextStyle(fontSize: 15),),
                ],
                  ),
            ],
          ),
        ),
      ),
    );
  }
}


