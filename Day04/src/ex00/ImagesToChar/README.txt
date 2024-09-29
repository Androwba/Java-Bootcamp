# Instructions for compiling and running the ImagesToChar application

## Project Structure

# ImagesToChar
# ├── src
# │   └── main
# │       └── java
# │           └── edu
# │               └── school21
# │                   └── printer
# │                       ├── app
# │                       │   └── Main.java
# │                       └── logic
# │                           └── ImagePrinter.java
# ├── target
# └── it.bmp

## Compile the application

# 1. Open the terminal and navigate to the project root directory (ImagesToChar).
# 2. Run the following command to compile the source files:
javac -d target src/java/edu/school21/printer/app/Main.java src/java/edu/school21/printer/logic/ImagePrinter.java

## Run the application

# 1. Run the following command to execute the application:
java -cp target edu.school21.printer.app.Main . 0 it.bmp

# or just execute ./README.txt