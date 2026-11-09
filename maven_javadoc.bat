
@echo.

mvn clean javadoc:javadoc -Dshow=private -DadditionalJOption=-Xdoclint:none
@REM -Xdoclint:none: Keine Warnung, wenn für eine Methode/Argument/Klasse kein JavaDoc-Kommentar angegeben

@REM Einstiegsdokument für die erzeuge Javadoc-Dateien: target/reports/apidocs/index.html

@echo.

