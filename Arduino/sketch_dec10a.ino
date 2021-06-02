#include <Wire.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include "MAX30100_PulseOximeter.h"
const char *ssid =  "Galaxy A51693";     
const char *pass =  "tmqb7021";

#define REPORT_TIME 5000

PulseOximeter oximeter;
uint32_t LastReportedReading = 0;
void onBeatDetected()
{
    Serial.println("Beat!");
}
float BPM;
int SpO2;
void setup() 
{
       Serial.begin(115200);
       
              
       Serial.println("Connecting to ");
       
       Serial.println(ssid); 
 
       WiFi.begin(ssid, pass);  // To connect to WiF
       
       while (WiFi.status() != WL_CONNECTED) 
          {
            delay(500);
            Serial.print(".");
          }
      Serial.println("");
      Serial.println("WiFi connected"); 
      pinMode(2, OUTPUT); // Implying that pin 2 of pulse oximter will act as output pin
      Serial.print("Initializing Pulse Oximeter..");
      pinMode(16, OUTPUT); // Implying that pin 16 will act as output
      if (!oximeter.begin())
      {
         Serial.println("FAILED");
         for(;;);
      }
       else
      {
         Serial.println("SUCCESS");
         digitalWrite(2, HIGH);   
      }
      oximeter.setOnBeatDetectedCallback(onBeatDetected);     
      oximeter.setIRLedCurrent(MAX30100_LED_CURR_24MA);
}
 
void loop() 
{     
 
  oximeter.update();
  BPM = oximeter.getHeartRate();
  SpO2 = oximeter.getSpO2();

  
  
  if (millis() - LastReportedReading > REPORT_TIME)
  {
  digitalWrite(2, LOW);                                              
  Serial.print("Heart rate: ");  Serial.print(BPM);
  Serial.print("\n");
  Serial.print("SpO2: "); Serial.print(SpO2);
  Serial.print("\n");
  digitalWrite(2, HIGH);                                           
  
  LastReportedReading = millis();
   HTTPClient http;
   String data = String(SpO2);
  String b = String(BPM);
  String reqBody = "?Spo2=" + data + "&Bpm=" + b;
  String link = "http://192.168.121.56/conn.php" + reqBody;
  http.begin(link);
  int httpCode = http.GET();
  String payload = http.getString();
  Serial.println(httpCode);
  Serial.println(payload);
   Serial.println("Data Entered Successfully");
   http.end();
 
  
 
 
  }
 

 
   
  
 
}
