# sp3

Vi har ændret hvordan vi læser data ind i vores program.
Vi har 3 readUserDaterFromDB metoder der læser: userdata, filmdata og seriedata
Derefter kalder vi metoderne i service med sql query's som parametre (udover i userdata hvor de er en del af metoden).
retur kommer der lister af strings som vi splitter om ligesom da vi læste fra en csv.
til sidst bliver alt dataen gemt ned i databasen med saveToDB() :)
