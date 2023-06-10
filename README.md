# TinyML-GoodHelp
This repository contains the source code for our TinyML Project.

## What is the Project:
This project is an enhanced version of the present Nurse-Call System. It has a feature of Voice Call, which increases the accessibilty for the person in need. The project also incorporates an App which is made for the care provider to monitor the real-time status of the patients and to respond to their help requests promplty over a wifi.

## Hardware Used:
1. Arduino Nano 33 BLE Sense
2. Arduino Nano 33 IoT
3. Android Phone

## Steps to Run the Project:

1. Deploy the firmware for Iot board on the Arduino Nano 33 Iot board. Don't forget to include Button and Firebase libraries before uploading the code. Similary deploy the TinyML code on the Arduino Nano 33 BLE Sense board, after including the help_inferencing library.
2. Open the App folder using Android Studio, connect your android phone to the computer, once you see the phone connected on the application screen, then run the app, which will install the app on your connected android phone.
3. One more thing to care about is the Wifi, since the iot board connects to a Wi-Fi network, you need to change the wifi configurations in the code for IoT board according to your network.

After completing all the steps you can commnuicate between the App and the board and can see the project work.
