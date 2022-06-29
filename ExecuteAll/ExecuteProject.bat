echo "Running Audit-Management-System\n"
cd Exec
start /min RunAuthMS.bat
start /min RunBenchMS.bat
start /min RunCheckMS.bat
start /min RunSeverityMS.bat
start RunWebApp.bat
