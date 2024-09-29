## Project Structure

#  ImagesToChar
#  ├── lib
#  │   ├── jcommander-1.82.jar
#  │   └── JColor-5.5.1.jar
#  ├── src
#  │   ├── java
#  │   │   └── edu
#  │   │       └── school21
#  │   │           └── printer
#  │   │               ├── app
#  │   │               │   └── Program.java
#  │   │               └── logic
#  │   │                   └── ImagePrinter.java
#  │   ├── resources
#  │   │   └── it.bmp
#  │   └── manifest.txt
#  ├── target
#  ├── images-to-chars-printer.jar
#  └── README.txt

## Steps to Compile and Run

rm -rf target

# 1.Compile the Java Source Files:
javac -cp "lib/*:." -d target src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/ImagePrinter.java

# 2.Copy Resources to the Target Directory:
cp -r src/resources/* target/

# 3.Create the JAR File:
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

# 4.Run the Application:
java -cp "target:lib/*" edu.school21.printer.app.Program --white=RED --black=GREEN
