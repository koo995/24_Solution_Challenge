import 'package:flutter/material.dart';
import 'package:front_flutter/view/home_page.dart';
import 'package:front_flutter/view/itemdetail_page.dart';
import 'package:front_flutter/view/login_page.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:front_flutter/view/map_page.dart';
import 'package:front_flutter/view/signinex.dart';
import 'firebase_options.dart';
import 'package:provider/provider.dart';
import 'callapi.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';


void main() async{
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  runApp(
       MyApp());
}


class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => Store1(),
      child: MaterialApp(
        home: SignInEx(),
      ),
    );
  }
}
