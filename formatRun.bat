@echo Formatter Script start....
@java -jar lib/google-java-format-1.9-all-deps.jar --replace src/main/*.java
@java -jar lib/google-java-format-1.9-all-deps.jar --replace src/test/*.java
@echo Done
@pause