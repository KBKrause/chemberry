ANS="127.0.0.1"

echo "Enter server IP: "
read ANS

java -Djava.library.path=/usr/lib/jni -cp /usr/share/java/RXTXcomm.jar:lib/*:. StudentGUIDriver -s $ANS
