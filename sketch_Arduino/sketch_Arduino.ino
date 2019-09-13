#include "GFButton.h"
#include <SoftwareSerial.h>   

//crea las instancias de botones
GFButton button1(2);
GFButton button2(3);
GFButton button3(4);
GFButton button4(5);

// Definimos los pines RX y TX del Arduino conectados al Bluetooth
SoftwareSerial BT(10,11);    
 
void setup() {
  //Serial.begin(9600);
  BT.begin(9600);
  //pinMode(inputPin, INPUT);
}
 
void loop(){

    if(button1.wasPressed()){
         BT.print(1);
      }
    
    if(button2.wasPressed()){
         BT.print(2);
      }
    
    if(button3.wasPressed()){
         BT.print(3);
      }
    
    if(button4.wasPressed()){
         BT.print(4);
      }
}
