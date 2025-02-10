# Användarmanual

## Installationsbeskrivning
Programmet levereras i en ZIP-fil och innehåller ett admin verktyg, ett registreringsverktyg samt en backend-server.

- Programmet kan installeras på enheten enligt följande: 
    1. Gå till hemsidan https://drive.google.com/drive/folders/16vBeigCoXEIRqUHhCfiq6oR4v9e-aAdi och ladda ned önskad zip-fil. 
        ![](https://imgur.com/yIbu6vz.png)
    2. Flytta zip-filen till önskad mapp. Om den ska öppnas i Ubuntu kan kommandot ```cp /mnt/c/Users/DittAnvändarnamn/Path/Till/Filen.zip``` . användas efter att användaren navigerat till den mapp som den ska flyttas till.
    3. Unzipa filen. Detta kan göras med kommandot ```unzip din_fil.zip -d /sökväg/till/mapp```. Därefter finns samtliga filer som behövs för att köra programmet i den valda mappen.
- Nedan finns ett antal kommandon för att köra de olika delarna av programmet. Dessa kommandon förutsätter att användaren är inuti den mapp som den zippade-filen packades upp till.  
- Innan programmen körs måste servern startas. Detta görs med hjälp av följande kommandon:  
    - chmod +x runner 
    - ./runner start
- För att stänga av servern används kommandot:
    - ./runner stop
- Registreringsverktyget startas därefter, i en ny terminal, med kommandot:  
    - java -jar register.jar 
- Adminverktyget startas också i ytterligare en ny terminal med kommandot:  
    - java -jar admin.jar

## Förberedelser
Programmet förutsätter att enheten har Java 21. 

## Konfiguration
Konfigurationen kommer att ske i adminverktyget. Där ska ledningen kunna lägga in samtliga förare med respektives startnummer, samt vilka stationer som finns i tävlingen. I den nuvarande versionen finns däremot inte stöd för konfigurering än.

## Användarbeskrivning
Nedan följer instruktioner för hur de olika applikationerna används.

### Instruktioner för tidtagare (registrering)
I registreringsverktyget registrerar varje station när en förare passerar. Registreringen innehåller tre kolumner. Varje rad består av ett startnummer, tidpunkt och en station. Startnumret i **ruta 1** visar vilket startnummer som har skrivits in vid tryck på "registrera tid"-knappen. Tiden i **ruta 2** visar tidpunkten som knapptrycket registrerades avrundat till en tiondels millisekund. Stationen i **ruta 3** visar vilken station som registrerade tidpunkten.
Följande är ett rekommenderat tillvägagångssätt:  
1. Välj aktuell station i drop-down menyn i **ruta 4**.  
2. Skriv in förarens startnummer i **ruta 5**.  
3. Tryck därefter på knappen "Registrera tid" i **ruta 6**. Tiden har nu registrerats till servern.
![](https://i.imgur.com/aKLu89j.png)
![](https://i.imgur.com/HZKPLwT.png)


### Instruktioner för tävlingsledning (adminverktyg)
I adminverktyget kan tävlingsledningen se inrapporterade tider. Den vänstra tabellen visar alla utförda registreringar i kronologisk
ordning och den högra tabellen har två olika vyer.
Den första vyn, som visas som default, kallas för "Deltagar"-vyn **(1)**. I denna vy visas startnummer, namn, starttid, måltid och totaltid. I denna vy skrivs felmeddelanden ut vid duplicerade tider för stationerna. Den andra vyn är stället en resultatvisare och kallas för "Resultat"-vyn **(2)**. I resultatvyn visas inga felmeddelanden eller specifika stationstider, utan endast förarens placering, startnummer, namn och totaltid.
![](https://i.imgur.com/V48TQcV.png)

I den vänstra tabellen visas startnumret i rött om det finns mer än en registrerad tid för föraren vid samma station. I den högra tabellen står det om en tid saknas för föraren eller om mer än en tid är registrerad för mål/start-stationen i "Deltagar"-vyn. Ledningen kan i efterhand ändra vilken förare en tid tillhör. Detta görs genom att i den vänstra tabellen dubbelklicka på förarnumret, skriva in det nya förarnumret och trycka på enter. 

Ledningen kan också, vid flera tider för en station, välja att trycka på felmeddelandet i den högra tabellen. Då visas en ny tabell **(3)** med alla registrerade tider för föraren vid stationen. Denna tabell kan också visas utan att det står ett felmedellande genom att trycka på tiden i den högra tabellen. I nuläget kan inte tider raderas. För att istället få bort den registrerade tiden från föraren behöver ett annat nummer skrivas in. Detta görs genom att i den nya tabellen dubbelklicka på förarnumret **(4)**, skriva in ett annat nummer **(5)** och därefter trycka på enter. När det endast finns en tid kvar på stationen kommer denna automatiskt att räknas som tidpunkten för föraren på stationen **(6)**. 
![](https://i.imgur.com/CNHX9wZ.png)

### Instruktioner för resultatsvisning
När servern är igång kommer tiderna att registreras på den. Tiderna med all övrig information, som stationsid och startnummer, finns att se på url:en:
    http://localhost:8080/api/races/{raceId}/times  
**Obs!** Istället för {raceId} ska det aktuella raceId:t för racet skrivas in. I nuläget är raceId alltid 1.
