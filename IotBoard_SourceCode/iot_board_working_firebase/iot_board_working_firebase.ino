/*
 * Created by K. Suwatchai (Mobizt)
 * 
 * Email: k_suwatchai@hotmail.com
 * 
 * Github: https://github.com/mobizt
 * 
 * Copyright (c) 2022 mobizt
 *
*/

//Example shows how to connect to Firebase RTDB and perform basic operation for set, get, push and update data to database

//Required WiFiNINA Library for Arduino from https://github.com/arduino-libraries/WiFiNINA

#include "Firebase_Arduino_WiFiNINA.h"
#include <string>
#include "Button.h" 

#define DATABASE_URL "patient-s-database-cd92d-default-rtdb.firebaseio.com"
#define DATABASE_SECRET "CyEkoUmUGPEI2720GAFX7eTvACYDbJefTgZSkhap"
#define WIFI_SSID "SanskarHotspot"
#define WIFI_PASSWORD "yfqb6524"

//Define Firebase data object
FirebaseData fbdo;
static int global_count = 0;

// My Variables:
Button buttonR(2);
Button buttonG(3);
int GREEN_LED = 7;
int YELLOW_LED = 6;
int RED_LED = 5;
int HELP_LED = 4;
bool green_led = false;
bool yellow_led = false;
bool red_led = false;
unsigned long previousMillis = 0;
bool onceHere = true;

void setup()
{

  Serial.begin(115200);
  Serial1.begin(115200);  // Serial1 port for reading data

  delay(100);
  Serial.println();

  Serial.print("Connecting to Wi-Fi");
  int status = WL_IDLE_STATUS;
  while (status != WL_CONNECTED)
  {
    status = WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
    Serial.print(".");
    delay(100);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  //Provide the autntication data
  Firebase.begin(DATABASE_URL, DATABASE_SECRET, WIFI_SSID, WIFI_PASSWORD);
  Firebase.reconnectWiFi(true);

  // clear internal memory used
  fbdo.clear();

  // Button Setup:
  buttonR.on_press(on_press);
  buttonR.on_long_press(on_long_press);
  buttonR.set_maximum_gap(5000);
  buttonG.on_press(on_press_reset);

  // Led Setup:
  pinMode(GREEN_LED, OUTPUT);
  pinMode(YELLOW_LED, OUTPUT);
  pinMode(RED_LED, OUTPUT);
  pinMode(HELP_LED, OUTPUT);
}

void loop() 
{

  //Button Init:
  buttonR.init();
  buttonG.init();
  waitTimeRead(1000);

  if (Serial1.available()) {
    char data = Serial1.read();
    Serial.println(data);

    if (data == 'H') {
      Serial.println("\nRx: HELP");

      Firebase.setString(fbdo, "BedNo/01/Message", String(global_count++) + "> help");
      // Firebase.setString(fbdo, "BedNo/01/Message", "Help!!");
    }
  }
}

void on_press() {
    Serial.println("Need Help");
    Firebase.setString(fbdo, "BedNo/01/Message", "Help!!");
    // handle error
    // if (Firebase.failed()) {
    //     Serial.print("Message push failed!!");
    //     Serial.println(Firebase.error());  
    //     return;
    // }
}
void on_long_press() {
  Serial.println("Emergency");
  Firebase.setString(fbdo, "BedNo/01/Message", "Emergency");
  digitalWrite(HELP_LED, HIGH);
  // handle error
  // if (Firebase.failed()) {
  //     Serial.print("Message push failed!!");
  //     Serial.println(Firebase.error());  
  //     return;
  // }
}
void on_press_reset() {
  Serial.println("Patient is Good");
  Firebase.setString(fbdo, "BedNo/01/Message", "Good");
  digitalWrite(HELP_LED, LOW);
  // handle error
  // if (Firebase.failed()) {
  //     Serial.print("Message push failed!!");
  //     Serial.println(Firebase.error());  
  //     return;
  // }

  Firebase.setString(fbdo, "BedNo/01/Wait Time", "Null");
  // handle error
  // if (Firebase.failed()) {
  //     Serial.print("Message push failed!!");
  //     Serial.println(Firebase.error());  
  //     return;
  // }
}
void waitTimeRead(const long interval) {
  unsigned long currentMillis = millis();
  if (currentMillis - previousMillis >= interval) {
    // save the last time you blinked the LED
    previousMillis = currentMillis;

    // String waitTime = Firebase.getString(fbdo, "BedNo/01/Wait Time");
    Firebase.getString(fbdo, "BedNo/01/Wait Time");
    String waitTime = fbdo.stringData();
    if(waitTime == "10min") {
      digitalWrite(GREEN_LED, HIGH);
      digitalWrite(RED_LED, LOW);
      digitalWrite(YELLOW_LED, LOW);
      Serial.println("10min");
    } else if(waitTime == "20min") {
      digitalWrite(GREEN_LED, LOW);
      digitalWrite(RED_LED, LOW);
      digitalWrite(YELLOW_LED, HIGH);
      Serial.println("20min");
    } else if(waitTime == "Later" || waitTime == "Nurse") {
      digitalWrite(GREEN_LED, LOW);
      digitalWrite(RED_LED, HIGH);
      digitalWrite(YELLOW_LED, LOW);
      Serial.println("Later");
    } else {
      digitalWrite(GREEN_LED, LOW);
      digitalWrite(RED_LED, LOW);
      digitalWrite(YELLOW_LED, LOW);
      // Serial.println("All Led OFF");
    }
    
  }
}

