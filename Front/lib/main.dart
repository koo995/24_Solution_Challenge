import 'package:flutter/material.dart';
import 'package:front_flutter/view/home_page.dart';
import 'package:front_flutter/view/itemdetail_page.dart';
import 'package:front_flutter/view/login_page.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:front_flutter/view/map_page.dart';
import 'package:front_flutter/view/signinex.dart';
import 'firebase_options.dart';
import 'package:provider/provider.dart';


void main() async{
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  runApp(
      ChangeNotifierProvider(
        create: (context) => Store1(),
        child: MaterialApp(
            debugShowCheckedModeBanner: false,
            title: 'My App',
            home: MyApp(),
          ),
      ));
}
class Store1 extends ChangeNotifier {
  var apiUrl = 'https://34.47.91.250:8080/api/v1/';
}


class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return SignInEx();
  }
}