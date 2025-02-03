# Användarmanual

## Installationsbeskrivning
Programmet levereras i en ZIP-fil och innehåller ett admin verktyg, ett registreringsverktyg samt en backend-server.

- Programmet kan installeras på enheten med hjälp av ```wget``` enligt följande:  
    - wget --no-check-certificate 'https://drive.google.com/uc?export=download&id=FILID' -O din_fil.zip
    - unzip din_fil.zip -d /sökväg/till/mapp     
- Därefter finns samtliga filer som behövs för att köra programmet i den valda mappen.  
- Innan programmen körs måste servern startas. Detta görs med hjälp av:  
    - chmod +x runner 
    - ./runner start
- Registreringsverktyget startas därefter med kommandot:  
    - java -jar register.jar 
- Adminverktyget startas med kommandot:  
  - java -jar admin.jar

## Förberedelser
Programmet förutsätter att enheten har Java 21. I nuläget behövs inga ytterliggare filer än de som medföljer när releasen laddas ned med ```wget```.

## Konfiguration
Konfigurationen kommer att ske i adminverktyget. Där ska ledningen kunna lägga in samtliga förare med respektives startnummer, samt vilka stationer som finns i tävlingen. I den nuvarande versionen finns däremot inte stöd för konfigurering än.

## Användarbeskrivning
Nedan följer instruktioner för hur de olika applikationerna används.

### Instruktioner för tidtagare (registrering)
I registreringsverktyget registrerar varje station när en förare passerar. Detta görs enligt följande:  
1. Välj aktuell station i drop-down menyn i ruta 1.  
2. Skriv in förarens startnummer i ruta 2.  
3. Tryck därefter på knappen "Registrera tid" i ruta 3. Tiden har nu registrerats till servern.
![](https://i.imgur.com/vO060tt.png)

### Instruktioner för tävlingsledning (adminverktyg)
I adminverktyget så kan tävlingsledningen se inrapporterade tider, där vänstra sidan visar alla utförda registreringar i kronologisk
ordning, och högra sidan visar ihopparade tider, alltså alla förarnummer (i storleksordning) tillsammans med sin start- och måltid, och eventuellt beräknad totaltid. Om någonting behöver korrigeras så kan man klicka på cellen och ändra innehållet.  
![](https://i.imgur.com/at1dIql.png)

### Instruktioner för resultatsvisning
När servern är igång kommer tiderna att registreras på den. Tiderna med all övrig information, som stationsid och startnummer, finns att se på url:en:
    http://localhost:8080/api/races/{raceId}/times  
**Obs!** Istället för {raceId} ska det aktuella raceId:t för racet skrivas in. I nuläget är raceId alltid 1.
