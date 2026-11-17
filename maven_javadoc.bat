
@echo.


@REM JavaDoc erstellen und in target/reports/apidocs/ ablegen
@REM -Dshow=private: Auch private Methoden werden in der Javadoc angezeigt
@REM -Xdoclint:none: Keine Warnung, wenn für eine Methode/Argument/Klasse kein JavaDoc-Kommentar angegeben
@REM "call" wird benötigt, damit die Batch-Datei nach dem Aufruf von "mvnw" weiter ausgeführt wird
call mvnw clean javadoc:javadoc -Dshow=private -DadditionalJOption=-Xdoclint:none
if errorlevel 1 (
    echo FEHLER: Maven JavaDoc-Build fehlgeschlagen!
    pause
    exit /b 1
)

@echo.

@REM JavaDoc-Dateien in docs/ kopieren für GitHub Pages

xcopy target\site\apidocs\ docs\ /E /Y /I

@REM /E: Kopiert auch Unterverzeichnisse, auch wenn diese leer sind (rekursives Kopieren)
@REM /Y: Überschreibt vorhandene Dateien ohne Nachfrage
@REM /I: Wenn Zielverzeichnis nicht existiert, wird es erstellt

@echo.