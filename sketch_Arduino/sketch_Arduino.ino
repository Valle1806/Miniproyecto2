  #include "GFButton.h"
  #include <SoftwareSerial.h>   
  
  GFButton button1(2);
  GFButton button2(3);
  GFButton button3(4);
  GFButton button4(5);
  
  SoftwareSerial BT(10,11);    
   
  void setup() {
    BT.begin(9600);
  }
  void loop(){
      if(button1.wasPressed())BT.print(1);
      if(button2.wasPressed())BT.print(2);
      if(button3.wasPressed())BT.print(3);
      if(button4.wasPressed())BT.print(4);
  }
