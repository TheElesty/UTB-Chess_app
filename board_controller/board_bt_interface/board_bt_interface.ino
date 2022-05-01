#include <SoftwareSerial.h>

#define RX 8
#define TX 9

SoftwareSerial bt_serial(TX, RX);

void setup() {
  Serial.begin(38400);  
  bt_serial.begin(9600);
}

void loop() {
  char w;
  
  //BT Module receive data from mobile
  while(bt_serial.available()) {
    w = bt_serial.read();
    Serial.print(w);
  }


  
  //BT Module receive data from PC
  while(Serial.available()) {
    w = Serial.read();
    bt_serial.print(w);
  }
}
