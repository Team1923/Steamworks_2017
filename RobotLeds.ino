#include <Adafruit_NeoPixel.h>
#include <Wire.h>

#ifdef __AVR__
#include <avr/power.h>
#endif

#define PIN 6
#define PINT 7

// Parameter 1 = number of pixels in strip
// Parameter 2 = Arduino pin number (most are valid)
// Parameter 3 = pixel type flags, add together as needed:
//   NEO_KHZ800  800 KHz bitstream (most NeoPixel products w/WS2812 LEDs)
//   NEO_KHZ400  400 KHz (classic 'v1' (not v2) FLORA pixels, WS2811 drivers)
//   NEO_GRB     Pixels are wired for GRB bitstream (most NeoPixel products)
//   NEO_RGB     Pixels are wired for RGB bitstream (v1 FLORA pixels, not v2)
//   NEO_RGBW    Pixels are wired for RGBW bitstream (NeoPixel RGBW products)
Adafruit_NeoPixel strip = Adafruit_NeoPixel(41, PIN, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel strip2 = Adafruit_NeoPixel(41, PINT, NEO_GRB + NEO_KHZ800);
// IMPORTANT: To reduce NeoPixel burnout risk, add 1000 uF capacitor across
// pixel power leads, add 300 - 500 Ohm resistor on first pixel's data input
// and minimize distance between Arduino and first pixel.  Avoid connecting
// on a live circuit...if you must, connect GND first.

int rdrivei=0;
int rdriveo=0;
int ldrivei=0;
int ldriveo=0;
int runderi=0;
int rundero=0;
int lunderi=0;
int lundero=0;


void setup() {
  strip.begin();
  strip.show(); // Initialize all pixels to 'off'
  strip2.begin();
  strip2.show();

  pinMode(13, OUTPUT);
  Wire.begin(4);
  Wire.onReceive(receiveEvent);
}

void loop() {
  // Some example procedures showing how to display to the pixels:
  //colorWipe(strip.Color(255, 0, 0), 50); // Red

     //colorWipe(strip.Color(255, 0, 0), 0); // Red
     //colorWipe2(strip2.Color(255, 0,  0), 0); //Red

     // colorWipe(strip.Color(0, 0, 255), 0); // Blue
     // colorWipe2(strip2.Color(0, 0,  255), 0); //Blue

     //colorWipe(strip.Color(0, 255, 0), 0); // Green
    // colorWipe2(strip2.Color(0, 255,  0), 0); //Green
    
    //    colorWipe(strip.Color(255, 0, 30), 0); // Pink
    //    colorWipe2(strip2.Color(255, 0, 30), 0); //Pink
    
//    delay(2000);

//    colorWipe(strip.Color(255,100,50),0); //White
//    colorWipe2(strip.Color(255,100,50),0); //White

//    delay(2000);

  //  colorWipe(strip.Color(0, 0, 255), 50); // Blue
  ////colorWipe(strip.Color(0, 0, 0, 255), 50); // White RGBW
  //  // Send a theater pixel chase in...
  //  theaterChase(strip.Color(127, 127, 127), 50); // White
  //  theaterChase(strip.Color(127, 0, 0), 50); // Red
  //  theaterChase(strip.Color(0, 0, 127), 50); // Blue
  //
   //rainbow(10);
   //rainbow2(10);
  
  
  rainbowCycle(20);
  
  
    //theaterChaseRainbow(50);

  //colorWipe(strip.Color(0,255,000), 0);

//  rDrive(strip.Color(255,51,255));
//  lDrive(strip.Color(255,51,255));
  
}

void rDrive(uint32_t c){
    for (uint16_t i = rdrivei; i < rdriveo; i++) {
    strip.setPixelColor(i, c);
  }  
  strip.show();
}

void lDrive(uint32_t c){
    for (uint16_t i = ldrivei; i < ldriveo; i++) {
    strip.setPixelColor(i, c);
  }  
  strip.show();
}

// Fill the dots one after the other with a color
void colorWipe(uint32_t c, uint8_t wait) {
  for (uint16_t i = 0; i < strip.numPixels(); i++) {
    strip.setPixelColor(i, c);
    strip.show();
    delay(wait);
  }
}

void colorWipe2(uint32_t c, uint8_t wait) {
  for (uint16_t i = 0; i < strip2.numPixels(); i++) {
    strip2.setPixelColor(i, c);
    strip2.show();
    delay(wait);
  }
}

void right(uint32_t c) {
  for (uint16_t i = 0; i < (strip.numPixels() / 2); i++) {
    strip.setPixelColor(i, c);
    strip.show();
  }
  for (uint16_t i = strip.numPixels() / 2; i < strip.numPixels(); i++) {
    strip.setPixelColor(strip.Color(0, 0, 0), c);
    strip.show();
  }
}

void left(uint32_t c) {
  for (uint16_t i = 0; i < (strip.numPixels() / 2); i++) {
    strip.setPixelColor(strip.Color(0, 0, 0), c);
    strip.show();
  }
  for (uint16_t i = strip.numPixels() / 2; i < strip.numPixels(); i++) {
    strip.setPixelColor(i, c);
    strip.show();
  }
}

void rightFlash(uint32_t wait) {
  right(strip.Color(0, 255, 0));
  delay(wait);
  right(strip.Color(0, 0, 0));
  delay(wait);
}


void leftFlash(uint32_t wait) {
  right(strip.Color(0, 255, 0));
  delay(wait);
  right(strip.Color(0, 0, 0));
  delay(wait);
}

void rainbow(uint8_t wait) {
  uint16_t i, j;

  for (j = 0; j < 256; j++) {
    for (i = 0; i < strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel((i + j) & 255));
    }
    strip.show();
    delay(wait);
  }
}

void rainbow2(uint8_t wait) {
  uint16_t i, j;

  for (j = 0; j < 256; j++) {
    for (i = 0; i < strip2.numPixels(); i++) {
      strip2.setPixelColor(i, Wheel((i + j) & 255));
    }
    strip2.show();
    delay(wait);
  }
}

// Slightly different, this makes the rainbow equally distributed throughout
void rainbowCycle(uint8_t wait) {
  uint16_t i, j;

  for (j = 0; j < 256 * 5; j++) { // 5 cycles of all colors on wheel
    for (i = 0; i < strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel(((i * 256 / strip.numPixels()) + j) & 255));
      strip2.setPixelColor(i, Wheel(((i * 256 / strip2.numPixels()) + j) & 255));
    }
    strip.show();
    strip2.show();
    delay(wait);
  }
}

//Theatre-style crawling lights.
void theaterChase(uint32_t c, uint8_t wait) {
  for (int j = 0; j < 10; j++) { //do 10 cycles of chasing
    for (int q = 0; q < 3; q++) {
      for (uint16_t i = 0; i < strip.numPixels(); i = i + 3) {
        strip.setPixelColor(i + q, c);  //turn every third pixel on
      }
      strip.show();

      delay(wait);

      for (uint16_t i = 0; i < strip.numPixels(); i = i + 3) {
        strip.setPixelColor(i + q, 0);      //turn every third pixel off
      }
    }
  }
}

//Theatre-style crawling lights with rainbow effect
void theaterChaseRainbow(uint8_t wait) {
  for (int j = 0; j < 256; j++) {   // cycle all 256 colors in the wheel
    for (int q = 0; q < 3; q++) {
      for (uint16_t i = 0; i < strip.numPixels(); i = i + 3) {
        strip.setPixelColor(i + q, Wheel( (i + j) % 255)); //turn every third pixel on
      }
      strip.show();

      delay(wait);

      for (uint16_t i = 0; i < strip.numPixels(); i = i + 3) {
        strip.setPixelColor(i + q, 0);      //turn every third pixel off
      }
    }
  }
}


// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
uint32_t Wheel(byte WheelPos) {
  WheelPos = 255 - WheelPos;
  if (WheelPos < 85) {
    return strip.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if (WheelPos < 170) {
    WheelPos -= 85;
    return strip.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return strip.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}

void receiveEvent(int howMany) {
  byte bits[1];
  int c = 0;
  while (Wire.available() > 0) {
    bits[c] = Wire.read();
    c++;
  }

  int byte = bits[1];

  if (byte == 0) {
    colorWipe(strip.Color(0, 0, 0), 0);
  }
  else if (byte == 1) {
    colorWipe(strip.Color(0, 255, 0), 0);
  }
  else if (byte == 2) {
    right(strip.Color(0,255,0));
  }
  else if (byte == 3) {
    left(strip.Color(0,255,0));
  }
  else if (byte == 4) {
    rightFlash(200);
  }
  else if (byte == 5) {
    leftFlash(200);
  }
  else {

  }
}

