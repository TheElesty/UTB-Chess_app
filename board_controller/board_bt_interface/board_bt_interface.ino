#include <SoftwareSerial.h>

#define RX 8
#define TX 9

SoftwareSerial bt_serial(TX, RX);
bool to_board, to_mobile;
char board_msg[16];
char mobile_msg[16];

char move_queue[32][4];
char init_fen[100];
int i;

void setup() {
  Serial.begin(38400);  
  bt_serial.begin(9600);
}

void loop() {  
  
  //BT Module receive data from mobile
  i=0;
  while(bt_serial.available()) {
    mobile_msg[i] = bt_serial.read();
    i++;
  }

  Serial.println(mobile_msg);

  if() {
    switch(to_board){
      case: 
      break;
      
      case:
      break;
      
      case:
      break;
      
      case:
      break;
      
      case:
      break;
      
      default:
      break;
    }
  }
  
  //BT Module receive data from PC
  i=0;
  while(Serial.available()) {
    board_msg[i] = Serial.read();
    i++;
  }
  
  bt_serial.println(board_msg);

  if() {
    switch(to_mobile){
      case: 
      break;
      
      case:
      break;
      
      case:
      break;
      
      case:
      break;
      
      case:
      break;
      
      default:
      break;
    }
  }
}
