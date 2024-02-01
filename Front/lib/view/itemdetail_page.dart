import 'package:flutter/material.dart';

class ItemDetail extends StatelessWidget {
  const ItemDetail({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        actions: [],
      ),
      body: ListView(
        children:[
          Container(
            width: 100,
            height: 100,
            child:Text('이미지 들어갈 영역',
            style: TextStyle(
              fontSize: 20,
              color: Colors.black,
            ),
          ),
          ),
          Container(
            width: 100,
            height: 100,
            decoration:BoxDecoration(
                color: Colors.blue,
              ),
            child:Text('내용 들어갈 영역',
            style: TextStyle(
              fontSize: 20,
              color: Colors.black,
            ),
          ),
          ),
          Container(
            width: 100,
            height: 100,
            decoration:BoxDecoration(
              color: Colors.red,
            ),
            child: Text('지도 들어갈 영역',
            style: TextStyle(
              fontSize: 20,
              color: Colors.black,
            ),
          ),
          )
        ]
      ),
    );
  }
}
