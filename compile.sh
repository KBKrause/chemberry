ANS="Y"

while [ $ANS != "N" ]; do
    javac -cp ".\rxtx_windows\RXTXcomm.jar;lib\*;" *.java
    echo "Recompile? Y/N"
    read ANS
    if [[ $ANS != "N" && $ANS != "Y" ]]; then
        echo "Invalid response; recompiling"
        ANS="Y"
    fi
done