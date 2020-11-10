# How to contribute for this repository

## Process
- Step 1 : Fork this repository 
- Step 2 : Clone the repository to your local machine
- Step 3 : Figure out the bugs or the improvement, and mentions provided in the issue section of the repository
- Step 4 : Add the changes to your repository with your comment(author, description, date) in the code 
- Step 5 : Run formatRun.bat file to make the code format organize
- Step 6 : Run SpotBugs.bat file to check and fix the bug
- Step 7 : Create a Pull Request, And that's all


## Set-up the tools for PR 

### Google-Java-Format 
- Plug-In : Help > Eclipse Marketplace > Search > Find Google-Java-Format
- StandAlone :  java -jar lib/google-java-format-1.9-all-deps.jar --replace src/*.java
				Run formatRun.bat


### SpotBugs Plug-In 
- Plug-In : Help > Eclipse Marketplace > Search > Find SpotBugs
- StandAlone :  java -jar lib/spotbugs/spotbugs.jar -project SpotBugs.fbp
				Run SpotBugs.bat