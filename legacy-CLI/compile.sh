echo "Compiling Class Files..."
javac -d ./build/classes ./src/*.java

echo "Executing program..."
java -cp ./build/classes: Main
