#define LOADPIN A0

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

}

void loop() {
  // put your main code here, to run repeatedly:
  float reading;
  reading = analogRead(LOADPIN);

  Serial.print("Analog Reading ");
  Serial.println(reading);
  delay(1000);
}
