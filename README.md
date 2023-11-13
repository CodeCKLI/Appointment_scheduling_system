
Purpose of the application: 
	The purpose of this application is to provide a GUI-based appointment scheduling application.  

Development Environment:
	IntelliJ IDEA 2021.1.3 (Community Edition)
	Build #IC-211.7628.21, built on June 30, 2021
	Runtime version: 11.0.11+9-b1341.60 amd64
	VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
	JDK version: JDK-17.0.1
	JavaFX version compatible with JDK version: javafx-sdk-17.0.1
	mysql connector version: mysql-connector-java-8.0.25

How to run the program:
	1. Download the source file
	2. Unzip the C195.java zip file
	3. Open the folder C195 with intellij IDE
	4. Select File -> Project Structure tab
	5. Make sure you are using the correct JDK(JDK-17.0.1)
	6. Select File -> Project Structure -> Libraries page, 
	and use the + button to add the following libraries that can be located at Desktop folder:
	javafx-sdk-17.0.1,
	mysql-connector-java-8.0.25
	7. Select Edit Configurations -> Build and run section
	8. add the follow path to Environment variables:
	--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics
	9. Check and apply all the settings
	10. Open helper folder and check JDBCHelp class, see if the parameters for mysql connections are correct.
	11. Run the program with the play button in Intellij IDE. 
		
MySQL Connector driver version number: mysql connector version: mysql-connector-java-8.0.25
