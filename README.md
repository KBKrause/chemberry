<h1>Chemberry</h1>
<li><i>a Java program that facilitates microscale chemistry experiments in undergraduate chemistry labs
<li><i>powered by Raspberry Pi and Arduino Uno
<li><i>API build and documentation provided for open source use
<h2>Contents</h2>
<a href="#abstract"> 1. Abstract</a><br>
<a href="#usage">2. Usage / Downloads</a><br>
<a href="#compilation">3. Compilation</a><br>
<a href="#execution">4. Execution</a><br>
<a href="#about">5. About</a><br>
<a href="#acknowledgements">6. Acknowledgements</a><br>
<hr>
<h3 id="abstract">Abstract</h3>

Research and work in the chemical field relies heavily on computational devices. Without the aid of elaborate computers such as mass spectrometers, FTIR spectrometers, NMR instruments and various other apparatuses, chemical breakthroughs would be nowhere near where they are today. Breakthroughs in drug design and pharmacology are often assisted by computer simulations. Therefore, in an academic setting, it is important that chemistry students master the theory while also mastering usage of these complex computers. However, the interfaces to these computers and software can inhibit students’ growth and confidence. Chemberry can address this issue by making it easier and more convenient for chemistry students to participate in microscale laboratory experiments.

  

Chemberry is a Raspberry Pi 3 Model B interfaced with an Arduino Uno, connected to several lab instruments. They currently include but will not be limited to:

  

* analog pH sensor

* electrical conductivity probe

* temperature and humidity sensor

  

Chemistry students can interact and obtain measurements from these instruments using a simple, informational GUI. As students continue to take measurements with their Chemberry, their instructor will automatically be notified over a TCP connection. These are just a few of its many useful features.

  

Overall, this device is a more cost-effective and less complex alternative to comparable lab microcontrollers.

  

This open source project is minimally complete. I invite anybody interested to contact me if they have an interest in making any contributions.

  

To test this project for yourself, see below under "Usage."

<hr>

  

<h3 id="usage">Usage</h3>

Mac documentation will be provided later, since it is inferior. Windows and Linux users, please download the following software. You will need to be comfortable using the terminal and command line if you want to follow these instructions.<br>

<h4>Downloads</h4>

<b>Java 8 and Java 8 Developer Kit</b><br>

Chemberry is written in Java and requires the JRE; compiling it additionally requires the JDK, both of which can be found <a  href = "http://www.oracle.com/technetwork/java/javase/downloads/index.html">on oracle's website.</a> Please download these and remember where they were installed. Linux users can also download it with

  

```

sudo apt-get install oracle-java8-jdk

```

<b>RXTX Serial Connection</b><br>

Download this library to communicate with the voltage readings produced by the Arduino. <br>

<i>Windows</i> users: download the appropriate package for your OS and follow the instructions <a  href = "http://rxtx.qbang.org/wiki/index.php/Download">on the wiki.</a>  <br>

<i>Linux</i> users: open a terminal and download the RXTX package

  

```

sudo apt-get install librxtx-java

```

<b>Arduino IDE</b>

This is the easiest (and perhaps the only) way to interface with the Arduino. Download it from <a  href = "http://rxtx.qbang.org/wiki/index.php/Download">the Arduino website.</a>

<b>JFreeChart / JCommon</b>

This library is necessary to draw graphs, and in particular, to use the "Visualize" button on the client GUI. Find it <a href = "http://www.jfree.org/index.html"> here. </a>

<h4 id="compilation">Compilation</h4>

To compile on your OS, open a terminal and find the folder with the source code (.java) files from Github. First, you will need to compile the Java code into bytecode while linking to the .jar and .dll or .so files from RXTX and JFreeChart. All .java files should be in a single "root" directory while the libraries can be placed in something like root\lib\. RXTX is dynamically linked, so we don't have to explicitly specify it in the javac command below.<br>
<i>Windows</i> users should place the RXTX .dll in the root directory, then

  

```

javac -cp "lib\*;" *.java

```

<i>Linux</i> users can point the CLASSPATH to the jar downloaded from apt-get

```

javac -cp /usr/share/java/RXTXcomm.jar:. *.java

```

  

This will produce the .class files needed by the JVM. The instructor program (ProfessorMain) must be ran first before the student side (StudentGUIDriver) can be ran, or alternatively, the student side can run "offline" using command line arguments. 

  

Lastly, upload the Arduino code to your board using the Arduino IDE.

<ul>

<li>Launch arduino.exe</li>

<li>Open chemberry.ino

<li>Click "Verify" to ensure there are no syntax errors

<li>Plug in your arduino to any USB port. The green light will flash if it is connected</li>

<li>Click "Upload" and watch the green bar in the bottom right fill to completion. If it succeeds, it will display some information about memory usage

</ul>

<h4 id="execution">Execution</h4>

The command to launch the JVM only slightly varies depending on your OS.<br>

<i>Windows</i> users can run the program from the root directory with

  

```

java -cp "RXTXcomm.jar;lib\*;" Main -of

```

  

<i>Linux</i> users need to add the Java Native Interface (JNI) to their command

```

java -Djava.library.path=/usr/lib/jni -cp /usr/share/java/RXTXcomm.jar:. Main -of

```

  

<hr>

  

<h3 id="about">About</h3>

  

This program was written using Visual Studio Code, Red Hat's Java language extension, the Arduino IDE, OneWire.h and OneWire.cpp for the Arduino, the RXTXcomm library, JFreeChart, and the NetBeans IDE 8.2 for UX/UI. RXTX and JFreeChart are both licensed under the GNU LGPL.<br> 

If you use or modify this code, please annotate that it originated here.

It was authored by Kevin Krause. <a  href = "https://www.linkedin.com/in/kevin-krause-131664105/"> Find him on LinkedIn.</a>

  

<h3 id="acknowledgements">Acknowledgements</h3>

  

Thanks is given to

  

* Carthage College for inspiring my love for chemistry

* Perry Kivolowitz and Mark Mahoney for being awesome CS professors

* My senior cohort for being supportive of me