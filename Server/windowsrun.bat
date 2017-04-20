if not exist ./paperspigot-1.8.8.jar (
    powershell -Command "Invoke-WebRequest https://ci.destroystokyo.com/job/PaperSpigot/443/artifact/Paperclip.jar -OutFile paperspigot-1.8.8.jar"
)
java -Xmx1024M -jar paperspigot-1.8.8.jar -o true -nojline
PAUSE