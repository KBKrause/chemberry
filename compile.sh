# This is the script for compiling source code. To run:
# ./compile.sh
# It only works in Unix environments. On Windows, I use cygwin and git bash.

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