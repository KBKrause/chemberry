# Chemberry

<hr>

<h3>Abstract</h3>
Research and work in the chemical field relies heavily on computational devices. Without the aid of elaborate computers such as mass spectrometers, FTIR spectrometers, NMR instruments and various other apparatuses, chemical breakthroughs would be nowhere near where they are today. Breakthroughs in drug design and pharmacology are often assisted by computer simulations. Therefore, in an academic setting, it is important that chemistry students master the theory while also mastering usage of these complex computers. However, the interfaces to these computers and software can inhibit students’ growth and confidence. Chemberry can address this issue by making it easier and more convenient for chemistry students to participate in microscale laboratory experiments.

Chemberry is a Raspberry Pi 3 Model B interfaced with several lab instruments. They currently include but will not be limited to:

 * analog pH sensor
 * electrical conductivity probe
 * temperature and humidity sensor

Chemistry students can interact and obtain measurements from these instruments using a simple, informational GUI. As students continue to take measurements with their Chemberry, their instructor will automatically be notified over a TCP connection. These are just a few of its many useful features.

Overall, this device is a more cost-effective and less complex alternative to comparable lab microcontrollers.

This project is aimed to be complete by May 2018. Hardware has not yet been acquired, so measurements are being taken using random numbers as a prototype.
<hr>

<h3>Usage</h3>
Chemberry is written in Java and requires the JRE; compiling it additionally requires the JDK, both of which can be found <a href = "http://www.oracle.com/technetwork/java/javase/downloads/index.html">on oracle's website.</a>

To install on your OS, open a terminal and find the folder with the source code (.java) files.
```
javac *.java
```

This will produce the .class files needed by the JVM. The instructor program must be ran first before the student side can be ran. By 5/2018, I will have enabled the student / client side code to run without a necessary connection to the instructor.

```
java InstructorMain
```

This will open the GUI to the instructor side.

```
java Main
```

Finally, this will connect the Raspberry Pi to the instructor. Measurements made by the students will be sent to the instructor as long as a connection remains.
Later on, I will include detailed instructions for building your own Chemberry!

<hr>

<h3>About</h3>

This program was written using Visual Studio Code and Red Hat's Java language extension.<br> It was authored by Kevin Krause. <a href = "https://www.linkedin.com/in/kevin-krause-131664105/"> Find him on LinkedIn.</a>

<h3>Acknowledgements</h3>

Thanks is given to

 * Carthage College for inspiring my love for chemistry
 * Perry Kivolowitz and Mark Mahoney for being awesome CS professors
 * My senior cohort for being supportive of me