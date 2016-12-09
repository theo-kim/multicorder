//Included classes and initializations for Thermistor
#define SERIESRESISTOR 10000    
#define THERMISTORPIN A0
#define THERMISTORNOMIAL 10000
#define TEMPERATURENOMIAL 25
#define BCOEFFICIENT 3950

//Included classes and initializations for Conductivity
int analogPin = 22;     // potentiometer wiper (middle terminal) connected to analog pin 3
                      // outside leads to ground and +5V
float raw = 0;           // variable to store the raw input value
float Vin = 3.3;           // variable to store the input voltage
float Vout = 0;        // variable to store the output voltage
float R1 = 100000;         // variable to store the R1 value
float R2 = 0;          // variable to store the R2 value
float buffer = 0;      // buffer variable for calculation
float conductance = 0.0; // variable to hold conductance (1/R)

//Included classes and initializations for Battery Manager
//#include <SparkFunBQ27441.h>
//const unsigned int BATTERY_CAPACITY = 1000; // e.g. 850mAh battery

//Included classes and initializations for BMP sensor
#include <Wire.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BMP085_U.h>
Adafruit_BMP085_Unified bmp = Adafruit_BMP085_Unified(10085);

//Included classes and initializations for DHT sensor
#include "DHT.h"
#define DHTPIN 17     // what digital pin we're connected to
#define DHTTYPE DHT22   // DHT 22  (AM2302), AM2321
DHT dht(DHTPIN, DHTTYPE);

//Included classes and initializations for UV sensor
#include <Wire.h>
#include "Adafruit_SI1145.h"
Adafruit_SI1145 uv = Adafruit_SI1145();

//Included classes and initializations for Bluetooth Module
#include <SPI.h>
#include "Adafruit_BLE_UART.h"

// Connect CLK/MISO/MOSI to hardware SPI
#define ADAFRUITBLE_REQ 10
#define ADAFRUITBLE_RDY 2     // This should be an interrupt pin, on Uno thats #2 or #3
#define ADAFRUITBLE_RST 9

Adafruit_BLE_UART bluetooth = Adafruit_BLE_UART(ADAFRUITBLE_REQ,ADAFRUITBLE_RDY,ADAFRUITBLE_RST);
aci_evt_opcode_t laststatus = ACI_EVT_DISCONNECTED;

//Device ID
int deviceID = 123456;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  bluetooth.begin();
}

void loop() {

  bluetooth.pollACI();

  bluetooth.write(bluetoothStatus()); //status of bluetooth comm

  bluetooth.write(12); //number of sensor values

  //sensor values
  bluetooth.write(001);
  bluetooth.write(thermistor(0));
  
  bluetooth.write(002);
  bluetooth.write(dht22(0));
  bluetooth.write(003);
  bluetooth.write(dht22(1));
  
  bluetooth.write(004);
  bluetooth.write(bmp180(0));
  bluetooth.write(005);
  bluetooth.write(bmp180(1));
  bluetooth.write(006);
  bluetooth.write(bmp180(2));
  
  bluetooth.write(007);
  bluetooth.write(uvSensor(0));
  bluetooth.write(010);
  bluetooth.write(uvSensor(1));
  bluetooth.write(011);
  bluetooth.write(uvSensor(2));

  bluetooth.write(012);
  bluetooth.write(conductivity(0));
  bluetooth.write(013);
  bluetooth.write(conductivity(1));
  bluetooth.write(014);
  bluetooth.write(conductivity(2));

  //footer
  bluetooth.write(deviceID);
}

int bluetoothStatus(){
  // Ask what is our current status
  aci_evt_opcode_t status = bluetooth.getState();
  // If the status changed....
  if (status != laststatus) {
    if (status == ACI_EVT_DEVICE_STARTED) {
        return 1;
    }
    if (status == ACI_EVT_CONNECTED) {
        return 2;
    }
    if (status == ACI_EVT_DISCONNECTED) {
        return 3;
    }
    else {
      return 0;
    }
    // OK set the last status change to this one
    laststatus = status;
  }
}
float thermistor(int x){
  float reading;
  reading = analogRead(THERMISTORPIN);
  //reading = (1023 / reading) - 1;
  //reading = SERIESRESISTOR / reading;
  return reading;
}

float dht22(int x){
  float reading;
  switch(x){
    case 0:
      reading = dht.readHumidity();
      break;
    case 1:
      reading = dht.readTemperature();
      break;
    default:
      reading = 0.0;
  }
  return reading;
}

float bmp180(int x){
  float reading;
  float temperature;
  float seaLevelPressure = SENSORS_PRESSURE_SEALEVELHPA;
  
  sensors_event_t event;
  bmp.getEvent(&event);
  if(event.pressure){
    reading = event.pressure;
  } else {
    reading = 0.0;
  }
  switch(x){
    case 0: 
      reading = event.pressure;
      break;
    case 1:
      bmp.getTemperature(&temperature);
      reading = temperature;
      break;
    case 2:
      reading = bmp.pressureToAltitude(seaLevelPressure,event.pressure);
      break;
    default:
      reading = 0.0;
      break;
  }
  return reading;
}

float uvSensor(int x){
  float reading;
  switch(x){
    case 0:
      reading = uv.readVisible();
      break;
    case 1:
      reading = uv.readIR();
      break;
    case 2:
      reading = uv.readUV();
      break;
    default:
      reading = 0.0;
      break;
  }
  return reading;
}

float conductivity(int x){
  float reading;
 
  raw = analogRead(analogPin);    // Reads the Input PIN
  Vout = (5.0 / 1023.0) * raw;    // Calculates the Voltage on th Input PIN
  buffer = (Vin / Vout) - 1;
  R2 = R1 / buffer;
  conductance = (1 / (R2));

  switch(x){
    case 0:
      reading = Vout;
      break;
    case 1:
      reading = R2;
      break;
    case 2:
      reading = conductance;
      break;
    default:
      reading = 0.0;
      break;
  }
  return reading;
}

