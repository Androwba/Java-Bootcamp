## Project Structure

## ImagesToChar
#  ├── src
#  │   ├── java
#  │   │   └── edu
#  │   │       └── school21
#  │   │           └── printer
#  │   │               ├── app
#  │   │               │   └── Program.java
#  │   │               └── logic
#  │   │                   └── ImageReader.java
#  │   ├── resources
#  │   │   └── image.bmp
#  │   └── manifest.txt
#  ├── target
#  └── README.txt

## Instructions for compiling and running the ImagesToChar application

rm -rf target

# 1.Compile the Java Source Files:
javac -d target src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/ImageReader.java

# 2.Copy Resources to the Target Directory:
cp -r src/resources/* target/

# 3.Create the JAR File:
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

# 4.Run the Application:
java -jar target/images-to-chars-printer.jar
