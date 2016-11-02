// the value of the 'other' resistor
#define SERIESRESISTOR 10000    
 
// What pin to connect the sensor to
#define THERMISTORPIN A0 

//thermistor nomial
#define THERMISTORNOMIAL 10000

//temp for nominal resistance
#define TEMPERATURENOMIAL 25

//the beta coefficient of the thermistor
#define BCOEFFICIENT 3950

void setup(void) {
  Serial.begin(9600);
}
 
void loop(void) {
  float reading;
 
  reading = analogRead(THERMISTORPIN);
 
  Serial.print("Analog reading "); 
  Serial.println(reading);
 
  // convert the value to resistance
  reading = (1023 / reading)  - 1;
  reading = SERIESRESISTOR / reading;
  Serial.print("Thermistor resistance "); 
  Serial.println(reading);

  float steinhart;
  steinhart = reading / THERMISTORNOMIAL;
  steinhart = log(steinhart);
  steinhart /= BCOEFFICIENT;
  steinhart += 1.0 / (TEMPERATURENOMIAL + 273.15);
  steinhart = 1/steinhart;
  steinhart -= 273.15;

  Serial.print("Temperature ");
  Serial.print(steinhart);
  Serial.println(" *C");

  delay(1000);
 
 
  delay(1000);
}
