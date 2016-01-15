/*
  Blink
  Turns on an LED on for one second, then off for one second, repeatedly.
 
  This example code is in the public domain.
 */
 
// Pin 13 has an LED connected on most Arduino boards.
// give it a name:
int couleur = -1;
int feu = -1;
int debounce = 500 ;

int capteur1 = 2 ; 
int capteur2 = 3 ;
int capteur3 = 4 ;
int capteur4 = 5 ;
int capteur5 = 6 ;
int capteur6 = 7 ;

int f1R = A0 ;
int f1V = A1 ;

int f2R = A2 ;
int f2V = A3 ;

int f3R = A4 ;
int f3V = A5 ;

int var1 = -1; 
int var2 = -1 ;

unsigned long delC1 = 0 ; 
unsigned long delC2 = 0 ;
unsigned long delC3 = 0 ;
unsigned long delC4 = 0 ;
unsigned long delC5 = 0 ;
unsigned long delC6 = 0 ;

// the setup routine runs once when you press reset:
void setup() {                
 //met le port serie en route
  Serial.begin(9600) ;
//declartion des variables de gare
  pinMode(capteur4,INPUT) ;
  pinMode(capteur5,INPUT) ;
  pinMode(capteur6,INPUT) ;

//activation des resistance de pullup pour limiter la consomation de courant
  digitalWrite(capteur4, HIGH);
  digitalWrite(capteur5, HIGH);
  digitalWrite(capteur6, HIGH);
 
 //declaration des variables de debut de canton
  pinMode(capteur3,INPUT) ;
  pinMode(capteur1,INPUT); 
  pinMode(capteur2,INPUT) ;
  
  
  //ici opn active les resistance de pullup
  digitalWrite(capteur1, HIGH);
  digitalWrite(capteur2, HIGH);
  digitalWrite(capteur3, HIGH);
 //declaration des variables pour les feux sur les prises analogiques
  pinMode(13,OUTPUT) ;
  digitalWrite(13,LOW) ;
  
  pinMode(f1R,OUTPUT);
  digitalWrite(f1R,HIGH) ;
  pinMode(f1V,OUTPUT);
  digitalWrite(f1V,LOW) ;
  
  pinMode(f2R,OUTPUT);
  digitalWrite(f2R,HIGH) ;
  pinMode(f2V,OUTPUT);
   digitalWrite(f2V,LOW) ;

  pinMode(f3R,OUTPUT);
  digitalWrite(f3R,HIGH) ;
  pinMode(f3V,OUTPUT);
  digitalWrite(f3V,LOW) ;
  
  
}

// the loop routine runs over and over again forever:
void loop() {
 if (Serial.available() > 0) {
    if (feu == -1)
    {
      feu = Serial.read();
    }
    else if (couleur == -1)
      couleur = Serial.read() ;
  }
  if (feu >=0 && couleur >= 0)
  {
    if (feu == 1)
    {
      var1 = f1R ;
      var2 = f1V ;
    }
    if (feu == 2)
    {
      var1 = f2R ;
      var2 = f2V ;
    }
    if (feu == 3)
    {
      var1 = f3R ;
      var2 = f3V ;
    }      
    if (couleur == 0)
    {
      digitalWrite(var1 ,HIGH) ;
      digitalWrite(var2,LOW) ;
    }
    else if (couleur == 1)
    {
      digitalWrite(var1 ,LOW) ;
      digitalWrite(var2 ,HIGH) ;      
    }
    feu = -1 ;
    couleur = -1 ;
    var1 = -1 ;
    var2 = -1 ;
    delay(200) ;
  }
  if ((delC1 > 0) && ((millis() - delC1) > debounce))
  {
      delC1 = 0 ;
  }

  if ((delC2 > 0) && ((millis() - delC2) > debounce))
  {
      delC2 = 0 ;
  }

  if ((delC3 > 0) && ((millis() - delC3) > debounce))
  {
      delC3 = 0 ;
  }
  if ((delC4 > 0) && ((millis() - delC4) > debounce))
  {
      delC4 = 0 ;
  }
  if ((delC5 > 0) && ((millis() - delC5) > debounce))
  {
      delC5 = 0 ;
  }
  if ((delC6 > 0) && ((millis() - delC6) > debounce))
  {
      delC6 = 0 ;
  }
  
  if ((digitalRead(capteur1) == LOW) && delC1 == 0)
  {
    delC1=millis();
    Serial.println("1"); 
   
  }  // wait for a second
  if ((digitalRead(capteur2) == LOW) && delC2 == 0)
  {
    delC2=millis();
    Serial.println("2");    
  
      
  } 
  if ((digitalRead(capteur3) == LOW) && delC3 == 0)
  {
    delC3=millis();
    Serial.println("3");   
   
    
  } 
  if ((digitalRead(capteur4) == LOW) && delC4 == 0)
  {
    delC4=millis();
    Serial.println("4");      
  } 
  if ((digitalRead(capteur5) == LOW) && delC5 == 0)
  {
    delC5=millis();
    Serial.println("5");   
  } 
  if ((digitalRead(capteur6) == LOW) && delC6 == 0)
  {
    delC6=millis();
    Serial.println("6");      
   
   
  }   
}
