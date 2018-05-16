// This code has been modified from several tutorials across Adafruit and DFrobot.

#include "OneWire.h"

#define ReadTemperature 1
// Analog pins
#define pHPin           A0       
#define ECsensorPin     A1   
// Digital pins
#define temperaturePin  2
#define pHOffset        0.00

// Number of samples to take
const byte numReadings = 20;     

unsigned int AnalogSampleInterval=25,printInterval=700,tempSampleInterval=850;  //analog sample interval;serial print interval;temperature sample interval
unsigned int readings[numReadings];      // the readings from the analog input
byte index = 0;                  // the index of the current reading
unsigned long AnalogValueTotal = 0;                  // the running total
unsigned int AnalogAverage = 0,averageVoltage=0;                // the average
unsigned long AnalogSampleTime,printTime,tempSampleTime;
float temperature,ECcurrent; 

unsigned long int avgValue;
float b;
int buf[10],temp;
int condVal;

// OneWire library used for temperature sensor
OneWire ds(temperaturePin);

void setup() {
  Serial.begin(9600);
  
  // Initialize all the readings to 0:
  for (byte thisReading = 0; thisReading < numReadings; thisReading++)
  {
    readings[thisReading] = 0;
  }
  TempProcess(false);
  AnalogSampleTime=millis();
  printTime=millis();
  tempSampleTime=millis();
}

void loop() {
  //float currentTemp = alternateTempProcess();
  //float currentTemp = TempProcess(1);
  //Serial.print("Temp: ");
  //Serial.print(currentTemp,3);
  //Serial.println(" ");
  //delay(1000);
  
   // Every once in a while, sample the analog value and calculate the average.
  if(millis()-AnalogSampleTime>=AnalogSampleInterval)  
  {
    AnalogSampleTime=millis();
     // subtract the last reading:
    AnalogValueTotal = AnalogValueTotal - readings[index];
    // read from the sensor:
    readings[index] = analogRead(ECsensorPin);
    // add the reading to the total:
    AnalogValueTotal = AnalogValueTotal + readings[index];
    // advance to the next position in the array:
    index = index + 1;
    // if we're at the end of the array...
    if (index >= numReadings)
    // ...wrap around to the beginning:
    index = 0;
    // calculate the average:
    AnalogAverage = AnalogValueTotal / numReadings;
    
  }
  /*
   Every once in a while,MCU read the temperature from the temperaturePin and then let the temperaturePin start the convert.
   Attention:The interval between start the convert and read the temperature should be greater than 750 millisecond,or the temperature is not accurate!
  */
   if(millis()-tempSampleTime>=tempSampleInterval) 
  {
    tempSampleTime=millis();
    temperature = TempProcess(ReadTemperature);  // read the current temperature from the  temperaturePin
    TempProcess(false);                   //after the reading,start the convert for next reading
  }
   /*
   Every once in a while,print the information on the serial monitor.
  */
  if(millis()-printTime>=printInterval)
  {
    printTime=millis();
    averageVoltage=AnalogAverage*(float)5000/1024;
    //Serial.print("Analog value:");
    //Serial.print(AnalogAverage);   //analog average,from 0 to 1023
    //Serial.print("    Voltage:");
    Serial.print("mv: ");  //millivolt average,from 0mv to 4995mV
    Serial.println(averageVoltage);
    delay(1000);
    Serial.print("temp: ");
    Serial.println(temperature);    //current temperature
    delay(1000);
    //Serial.print("^C     EC:");
    
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
  delay(1000);
  digitalWrite(13, HIGH);       
  digitalWrite(13, LOW); 
    
    float TempCoefficient=1.0+0.0185*(temperature-25.0);    //temperature compensation formula: fFinalResult(25^C) = fFinalResult(current)/(1.0+0.0185*(fTP-25.0));
    float CoefficientVolatge=(float)averageVoltage/TempCoefficient;   
    //if(CoefficientVolatge<150)Serial.println("No solution!");   //25^C 1413us/cm<-->about 216mv  if the voltage(compensate)<150,that is <1ms/cm,out of the range
    //else if(CoefficientVolatge>3300)Serial.println("Out of the range!");  //>20ms/cm,out of the range
    //else
    //{ 
      if(CoefficientVolatge<=448)ECcurrent=6.84*CoefficientVolatge-64.32;   //1ms/cm<EC<=3ms/cm
      else if(CoefficientVolatge<=1457)ECcurrent=6.98*CoefficientVolatge-127;  //3ms/cm<EC<=10ms/cm
      else ECcurrent=5.3*CoefficientVolatge+2278;                           //10ms/cm<EC<20ms/cm
      ECcurrent/=1000;    //convert us/cm to ms/cm
      //Serial.print(ECcurrent,2);  //two decimal
      //Serial.println("ms/cm");
    //}
  }
}

// Interpret and recalculate values for the temperature in celsius.
// The parameter to this function indicates if a "calibration" is not needed (false or true).
float TempProcess(bool ch)
{
  static byte data[12];
  static byte addr[8];
  static float TemperatureSum;
  if(!ch){
          if ( !ds.search(addr)) 
          {
              //Serial.println("No sensors detected!");
              ds.reset_search();
              return 0;
          }      
          if ( OneWire::crc8( addr, 7) != addr[7]) 
          {
              //Serial.println("CRC is not valid!");
              return 0;
          }        
          if ( addr[0] != 0x10 && addr[0] != 0x28) 
          {
              //Serial.print("Device is not recognized!");
              return 0;
          }      
          ds.reset();
          ds.select(addr);

          // This TX begins the conversion
          ds.write(0x44,1);
  }
  else
  {  
          byte present = ds.reset();
          ds.select(addr);    
          
          ds.write(0xBE);        
          for (int i = 0; i < 9; i++) 
          {
            data[i] = ds.read();
          }         
          ds.reset_search();    
                 
          byte MSB = data[1];
          byte LSB = data[0];        
          float tempRead = ((MSB << 8) | LSB); //using two's compliment
          TemperatureSum = tempRead / 16;
  }
          return TemperatureSum;  
}
