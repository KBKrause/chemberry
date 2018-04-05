//#include "OneWire.h"

#define pHPin    A0          // pH meter connected to input 0/A0
#define pHOffset 0.00

#define condPin  A1
unsigned long int avgValue;  //Store the average value of the sensor feedback
float b;
int buf[10],temp;
int condVal;

#define tempPin A2

//OneWire ds(tempPin);

void setup() 
{
  Serial.begin(9600);
}

void loop() 
{  
  readCond();
  readPH();
  //readTemp(1);
  //readTemp(0);
}

void serialEvent()
{
  if (Serial.available())
  {
    Serial.print(Serial.read());
    Serial.println();
  }
}
/*
void readTemp(bool ch)
{
  static byte data[12];
  static byte addr[8];
  static float TemperatureSum;
  if(!ch)
  {
          if ( !ds.search(addr)) {
              Serial.println("no more sensors on chain, reset search!");
              ds.reset_search();
              //return 0;
          }      
          if ( OneWire::crc8( addr, 7) != addr[7]) {
              Serial.println("CRC is not valid!");
              //return 0;
          }        
          if ( addr[0] != 0x10 && addr[0] != 0x28) {
              Serial.print("Device is not recognized!");
              //return 0;
          }      
          ds.reset();
          ds.select(addr);
          ds.write(0x44,1); // start conversion, with parasite power on at the end
  }
  else
  {
      byte present = ds.reset();
      ds.select(addr);    
      ds.write(0xBE); // Read Scratchpad            
      for (int i = 0; i < 9; i++) 
      { // we need 9 bytes
        data[i] = ds.read();
      }         
      ds.reset_search();           
      byte MSB = data[1];
      byte LSB = data[0];        
      float tempRead = ((MSB << 8) | LSB); //using two's compliment
      TemperatureSum = tempRead / 16;
    
      Serial.print("Temperature: ");
      Serial.print(TemperatureSum,3);
      Serial.println(" ");
      delay(1000);
  }
}
*/

void readCond()
{
  condVal = analogRead(condPin);
  float voltage = condVal * (5.0/1023.0);
  Serial.print("Voltage: ");
  Serial.print(voltage,3);
  Serial.println(" ");
  delay(1000);
}

void readPH()
{
  for(int i=0;i<10;i++)       //Get 10 sample value from the sensor for smooth the value
  { 
    buf[i]=analogRead(pHPin);
    delay(10);
  }
  for(int i=0;i<9;i++)        //sort the analog from small to large
  {
    for(int j=i+1;j<10;j++)
    {
      if(buf[i]>buf[j])
      {
        temp=buf[i];
        buf[i]=buf[j];
        buf[j]=temp;
      }
    }
  }
  avgValue=0;
  for(int i=2;i<8;i++)                      //take the average value of 6 center sample
  avgValue+=buf[i];
  float phValue=(float)avgValue*5.0/1024/6; //convert the analog into millivolt
  phValue=(3.5*phValue) + pHOffset;                      //convert the millivolt into pH value
  Serial.print("pH: ");  
  Serial.print(phValue,3);
  Serial.println(" ");
  digitalWrite(13, HIGH);       
  delay(800);
  digitalWrite(13, LOW); 
}

