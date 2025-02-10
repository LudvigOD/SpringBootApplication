# Användarmanual

## Installationsbeskrivning
Programmet levereras i en ZIP-fil och innehåller ett admin verktyg, ett registreringsverktyg samt en backend-server.

- Programmet kan installeras på enheten enligt följande: 
    1. Gå till hemsidan https://drive.google.com/drive/folders/16vBeigCoXEIRqUHhCfiq6oR4v9e-aAdi och ladda ned önskad zip-fil. 
        ![](https://imgur.com/yIbu6vz.png)
    2. Flytta zip-filen till önskad mapp. Om den ska öppnas i Ubuntu kan kommandot cp /mnt/c/Users/DittAnvändarnamn/Path/Till/Filen.zip . användas efter att användaren navigerat till den mapp som den ska flyttas till.
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
I registreringsverktyget registrerar varje station när en förare passerar. Registreringen innehåller tre kolumner. Varje rad består av ett startnummer, tidpunkt och en station. Startnummer visar vilket startnummer som har skrivits in vid tryck på "registrera tid"-knappen. Tid visar tidpunkten som knapptrycket registrerades avrundat till en tiondels millisekund. Station visar vilken station som registreringsverktyget representerar vid registrering.
Följande är ett rekommenderat tillvägagångssätt:  
1. Välj aktuell station i drop-down menyn i ruta 1.  
2. Skriv in förarens startnummer i ruta 2.  
3. Tryck därefter på knappen "Registrera tid" i ruta 3. Tiden har nu registrerats till servern.
![](https://i.imgur.com/vO060tt.png)


### Instruktioner för tävlingsledning (adminverktyg)
I adminverktyget så kan tävlingsledningen se inrapporterade tider, där vänstra sidan visar alla utförda registreringar i kronologisk
ordning, och högra sidan visar ihopparade tider, alltså alla förarnummer (i storleksordning) tillsammans med sin start- och måltid, och eventuellt beräknad totaltid. Om någonting behöver korrigeras så kan man klicka på cellen och ändra innehållet. Den första kolumnen visar respektive rads start ID. Den andra kolumnen visar förarens namn om det har registrerats. Den tredje visar förarens starttid om den har registrerats, annars "-". Den fjärde kolumnen visar förarens måltid om den har registrerats, annars "-". Sista kolumnen visar den totala tiden mellan start och mål, om de båda har registrerats.  
![](https://i.imgur.com/at1dIql.png)

### Instruktioner för resultatsvisning
När servern är igång kommer tiderna att registreras på den. Tiderna med all övrig information, som stationsid och startnummer, finns att se på url:en:
    http://localhost:8080/api/races/{raceId}/times  
**Obs!** Istället för {raceId} ska det aktuella raceId:t för racet skrivas in. I nuläget är raceId alltid 1.
