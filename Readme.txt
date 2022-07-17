Precondition:

Install Java JDK latest version
Install Intellij Idea community verion for editing Java code
Download chromedriver place in the  workspace https://chromedriver.chromium.org/downloads.

Test data:

Specify the url, username, password in the src/main/resources/testdata.properties
If there is a constraint to give the details in the properties file then make delete the value from .properties files and 
run from maven command in the terminal

mvn test "-Durl=https://sandbox2.talygen.com" "-Dusername=safiya@yopmail.com" "-Dpassword=talygen@123"


To Run:

1. Specify the functionality in the testing.xml           
2. To run , navigate to the workspace and issue the command mvn test.
3. Once the execution is completed, navigate to the reports folder and open the AutomationReport.html file to see the result.


