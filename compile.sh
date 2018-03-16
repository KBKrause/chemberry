ANS="Y"

while [ $ANS != "N" ]; do
    javac -cp ".\rxtx_windows\RXTXcomm.jar;lib\*;" *.java
    echo "Recompile? Y/N"
    read ANS
done