int analogPin = 0;     // potentiometer wiper (middle terminal) connected to analog pin 3
                      // outside leads to ground and +5V
int raw = 0;           // variable to store the raw input value
int Vin = 5;           // variable to store the input voltage
float Vout = 0;        // variable to store the output voltage
float R1 = 100000;         // variable to store the R1 value
float R2 = 0;          // variable to store the R2 value
float buffer = 0;      // buffer variable for calculation
float conductance = 0; // variable to hold conductance (1/R)

void setup()
{
 Serial.begin(9600);             // Setup serial
 digitalWrite(13, HIGH);         // Indicates that the program has intialized
}

void loop()
{
 raw = analogRead(analogPin);    // Reads the Input PIN
 Vout = (5.0 / 1023.0) * raw;    // Calculates the Voltage on th Input PIN
 buffer = (Vin / Vout) - 1;
 R2 = R1 / buffer;
 conductance = (1 / (R2));
 Serial.print("Voltage: ");      //
 Serial.println(Vout);           // Outputs the information
 Serial.print("R2: ");           //
 Serial.println(R2);             //
 Serial.print("R2 Conductance: ");
 Serial.println(conductance);
 delay(1000);
}
