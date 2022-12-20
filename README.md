# SipWatch




TRANSMISSIETECHNIEKEN EN MULTIMEDIA

**APP VERSLAG**

**SIPWATCH**

Jarne Van der Plas

*Industriële Wetenschappen*





P a g e | **2**

Inhoudstafel

[Introductie](#br3)[ ](#br3)[...............................................................................................................................................](#br3)[ ](#br3)[3](#br3)

[Fragmenten..............................................................................................................................................](#br4)[ ](#br4)[4](#br4)

[Drank](#br4)[ ](#br4)[fragment](#br4)[ ](#br4)[...................................................................................................................................](#br4)[ ](#br4)[4](#br4)

[Home](#br6)[ ](#br6)[fragment](#br6)[ ](#br6)[...................................................................................................................................](#br6)[ ](#br6)[6](#br6)

[Agenda](#br7)[ ](#br7)[fragment.................................................................................................................................](#br7)[ ](#br7)[7](#br7)

[Opslag......................................................................................................................................................](#br8)[ ](#br8)[8](#br8)

[Check](#br8)[ ](#br8)[bestand](#br8)[ ](#br8)[.....................................................................................................................................](#br8)[ ](#br8)[8](#br8)

[Schrijf](#br8)[ ](#br8)[naar](#br8)[ ](#br8)[bestand.............................................................................................................................](#br8)[ ](#br8)[8](#br8)

[Lees](#br8)[ ](#br8)[uit](#br8)[ ](#br8)[bestand](#br8)[ ](#br8)[..................................................................................................................................](#br8)[ ](#br8)[8](#br8)

[Verwijder](#br8)[ ](#br8)[lijn](#br8)[ ](#br8)[uit](#br8)[ ](#br8)[bestand....................................................................................................................](#br8)[ ](#br8)[8](#br8)

[Naam](#br9)[ ](#br9)[&](#br9)[ ](#br9)[Logo..........................................................................................................................................](#br9)[ ](#br9)[9](#br9)

[Conclusie...............................................................................................................................................](#br10)[ ](#br10)[10](#br10)

Jarne Van der Plas





P a g e | **3**

Introductie

Als onderdeel van de cursus transmissietechnieken en multimedia moeten we een app maken. Ik koos

ervoor om een app te maken waar de gebruiker ingeeft wat hij/zij dronk, de hoeveelheid en welke

soort drank gedronken werd. Er is ook de mogelijkheid om een foto van de drank te nemen. Via

machine learning wordt de tekst op de foto gescand en herkend.

Deze data wordt per dag opgeslagen in een lokaal bestand. Uit deze bestanden wordt de data uit

gelezen wanneer gevraagd. Dit wordt gedaan in twee andere fragmenten: in het home fragment en in

het agenda fragment. Het home fragment toont een overzicht per soort drank voor de huidige dag. In

het agenda fragment kan de gebruiker op de kalender een de dag aanduiden. Vervolgens wordt een

overzicht van de dranken getoond voor de aangeduide dag.

De meest up-to-date code is te vinden op https://github.com/JarneVdP/SipWatch

Jarne Van der Plas





P a g e | **4**

Fragmenten

De app is onderverdeeld in drie fragmenten: Drank, Home en Agenda. Boven op de fragmenten is er

het main fragment. Het main fragment zorgt ervoor dat er onderaan de app een navigatiebalk is. Deze

navigatiebalk zorgt voor het eenvoudig wisselen tussen de verschillende fragmenten. De structuur van

de app is voortgebouwd op het voorbeeld ‘Bottom Navigation Activity’ uit Android Studio.

Drank fragment

In het drank fragment wordt door de gebruiker de naam van de drank, hoeveelheid in centiliter en de

soort drank ingegeven. De naam is vrij in te geven, de hoeveelheid en soort drank wordt uit een

dropdown-menu gekozen. Een aantal dranknamen werden voorgeprogrammeerd. Wanneer de eerste

twee letters van de naam getypt worden, wordt het menu met de drank weergegeven indien de eerste

letters overeenkomen. Ook is er de mogelijkheid om een foto van de drank te nemen. Vervolgens

wordt er tekstherkenning uitgevoerd op de afbeelding. De tekstherkenning wordt uitgevoerd aan de

hand van een machine learning kit verdeeld door google.

*Figuur 1: Drank fragment*

*Figuur 2: Dropdown-menu*

De kit wordt toegevoegd aan het programma door “*implementation 'com.google.android.gms:play-*

*services-mlkit-text-recognition:18.0.2'* ” toe te voegen aan het build.gradle bestand. Google verdeelt

een heel aantal machine learning kits gratis in android studio omdat het de officiële IDE (Integrated

Jarne Van der Plas





P a g e | **5**

Development Environment) is voor android applicaties. Ook ondersteunt Google de ontwikkeling van

Kotlin. Kotlin wordt sinds 2019 aangeraden voor android applicatieontwikkeling. Dat was mijn rede

om Kotlin te gebruiken in plaats van Java als programmeertaal.

Wanneer de tekst herkend is, wordt het dropdown-menu met de verschillende opties weergegeven.

Bijkomend bij de voorgeprogrammeerde namen, worden de herkende tekst toegevoegd. Dit wordt

weergegeven in [*Figuur*](#br4)[* ](#br4)[2:*](#br4)[* ](#br4)[Dropdown-menu*](#br4)[* ](#br4)na het scannen van een fles sinaasappelsap.

*Figuur 1: Herkende tekst*

Het nemen van een foto is meer een feature aangezien dit trager is dan de naam te schrijven. Ook is de

herkenning niet altijd even betrouwbaar. Dan moet de gebruiker meerdere foto’s nemen vooraleer de

correcte naam herkend wordt. Ook zijn merknamen niet altijd duidelijk geschreven. Het machine

learning algoritme is zeer goed met duidelijke tekst, maar logo’s of moeilijkere lettertypes zijn

regelmatig onbetrouwbaar. Ten slotte staat er niet altijd een naam op een consumptie. Wanneer

iemand een herbruikbare fles gebruikt, iets uit een glas of tas drinkt etc. is het niet mogelijk om tekst

te herkennen. Ik koos ervoor om het eenvoudig te maken om manueel de naam van de drank te typen.

Dit zorgt ervoor dat de gebruiker meer geneigd is om zijn drankconsumptie bij te houden.

De naam werd ingegeven en de hoeveelheid- en soort drank werden geselecteerd uit het dropdown-

menu. Wanneer de gebruiker op de “Toevoegen” knop duwt, worden deze elementen gecombineerd

met de huidige dag en het tijdstip. Tussen deze verschillende elementen wordt een puntkomma

toegevoegd. Deze structuur zorgt ervoor dat ik later eenvoudig elementen kan splitsen en filteren.

Vervolgens wordt de tekst naar een bestand geschreven: [*Schrijf*](#br8)[* ](#br8)[naar*](#br8)[* ](#br8)[bestand*](#br8)[.](#br8)

Jarne Van der Plas





P a g e | **6**

Home fragment

In het home fragment wordt de gebruiker welkom geheten. Afhankelijk van de tijd van de dag wordt

er “Goeiemorgen!”, “Middag!”, “Goeienavond!” of “See ya!” weergegeven. Vervolgens is er een

recyclerview geplaatst. Een recyclerview is een element dat een adapter een aantal keer weergeeft. Het

aantal herhalingen wordt bepaald door het aantal verschillende soort dranken dat de gebruiker die dag

al dronk. In figuur 4 en figuur 5 wordt de recyclerview met twee en drie verschillende soorten drank

weergegeven.

Het aantal soorten drank wordt berekend aan de hand van het bestand dat geschreven werd in het

drank fragment. Eerst wordt er nagekeken of het bestand bestaat[:](#br8)[ ](#br8)[*Check*](#br8)[* ](#br8)[bestand*](#br8)[.](#br8)[ ](#br8)Vervolgens wordt

het bestand uitgelezen[:](#br8)[ ](#br8)[*Lees*](#br8)[* ](#br8)[uit*](#br8)[* ](#br8)[bestand*](#br8)[* ](#br8)en wordt er een lijst met het formaat *<String, Int>* opgemaakt.

Als string wordt de soort drank bijgehouden. Als integer wordt de hoeveelheid drank bijgehouden.

Wanneer een soort drank meermaals voorkomt, worden de hoeveelheden bij elkaar opgeteld.

Uiteindelijk is er een lijst voor de huidige dag waar de soorten drank en de totale hoeveelheden in

staan.

Deze lijst wordt weergegeven in een fragment aan de hand van een adapter. Indien er veel

verschillende soorten drank gedronken zijn, wordt de recyclerview scrolbaar. De adapter bevat de

naam van de soort drank, de totale hoeveelheid en een cirkel die opgevuld wordt naarmate de

gebruiker meer drinkt. De hoeveelheid drank vooraleer de cirkel volledig gevuld is, is verschillend per

soort. De algemene waarde is 150 centiliter. Voor wijn en bier is dit 75 cl, voor sterke drank is dit 25

cl. Ook is de kleur van de cirkel een gradiënt. Algemeen gaat het van groen naar oranje en eindigt het

bij rood. Voor water is dit omgekeerd. Het gaat van rood naar oranje en eindigt bij groen. Dit ter

aanmoediging om voldoende water te drinken. Wanneer een element uit de lijst verwijderd wordt,

worden de waarden herberekend.

*Figuur 3: Home fragment*

*Figuur 2: Home fragment*

Jarne Van der Plas





P a g e | **7**

Agenda fragment

Het agenda fragment bestaat uit een textview, een maandkalender en een recyclerview. De gebruiker

wordt gevraagd om de gewenste dag aan te duiden. Vervolgens worden de gegevens van die dag op

gehaald, gesplitst per puntkomma en in een array geplaatst. De aangeduide dag wordt in de textview

weergegeven. Indien er nog geen datum geselecteerd werd, wordt er gevraagd een aan te duiden.

Wanneer het bestand uitgelezen is voor de gekozen dag, wordt de adapter per drank gevuld. De

adapter bestaat uit de naam, hoeveelheid, tijdsstip, soort en datum. Ook is er een knop ‘Delete’. Deze

knop zorgt ervoor dat de gepaste lijn uit het bestand verwijderd wordt. Ook laat het de recyclerview

weten dat het zichzelf moet updaten. Omdat een gsm-scherm niet zeer groot is en het aangeraden is

om genoeg te drinken, moet ook deze recyclerview scrolbaar zijn.

*Figuur 5: Agenda fragment*

*Figuur 4: Agenda fragment*

Jarne Van der Plas





P a g e | **8**

Opslag

Ik koos ervoor om de gegevens in een lokaal bestand, datum + drinkStorage.txt, op te slaan. Zo bleven

de gegevens bewaard onafhankelijk van het sluiten van de app. Ik kon mijn eigen structuur voorzien

waardoor ik eenvoudig kan filteren en de gewenste gegevens weergeven.

Origineel had ik één bestand met alle gegevens. Dit gaf echter problemen bij het verwijderen van

lijnen uit het bestand. De oplossing was om per dag een nieuw bestand aan te maken. Onderstaande

functies maakte ik in een globaal bestand zodat ze in alle fragmenten opgeroepen kunnen worden.

Check bestand

In alle fragmenten wordt er naar een bestand geschreven of uit gelezen. Echter wanneer de app een

bestand probeert te lezen dat niet bestaat, crasht de app. Het is dus belangrijk om na te kijken of het

bestand bestaat. Wanneer de app wordt geopend, wordt er nagekeken of er al een bestand aangemaakt

is voor de huidige dag. Indien er nog geen bestand bestaat, wordt een nieuw, leeg bestand aangemaakt.

Ook bij het kalender fragment wordt er nagekeken of er voor de aangeduide dag een bestand bestaat.

Schrijf naar bestand

De *string* die in het drank fragment gevormd werd, wordt naar het bestand geschreven. Er wordt

automatisch een *new line* toegevoegd. De functie wordt ook gebruikt om een nieuw bestand aan te

maken. Echter wordt er niets naar het bestand geschreven. Het is belangrijk dat er hier geen *new line*

aan het bestand toegevoegd wordt. Indien dit wel gebeurt, zijn er fouten in de positionering bij het

verwijderen van een lijn.

Lees uit bestand

De gewenste bestandsnaam wordt opgegeven in de oproep van de functie. Vervolgens wordt het

bestand uitgelezen en als *stringBuilder* teruggegeven. Het stringBuilder type zorgt voor eenvoudige

concatenatie van *strings.* Door de structuur waarin de gegevens opgeslagen worden, met puntkomma,

is het eenvoudig de data opnieuw te splitsen en de gewenste data op te halen.

Verwijder lijn uit bestand

Indien iets fout werd ingevoerd, is het belangrijk dat deze drank verwijderd kan worden. Dit kan

gedaan worden aan de hand van de “Delete” knop in de recyclerview van het agenda fragment.

Wanneer deze knop wordt ingeduwd, wordt de *string* gemaakt met de gegevens uit de adapter. Deze

string wordt samen met de bestandsnaam opgeroepen in de lijn-te-verwijderen-functie.

De functie maakt een bestand aan met de opgegeven bestandsnaam en achtervoegsel *tmp*. Vervolgens

wordt het bestaande bestand gelezen en wanneer de lijn verschillend is van de te verwijderen lijn,

wordt de lijn naar het tmp-bestand geschreven. Indien de lijn gelijk is, wordt deze lijn overgeslagen.

Zolang er informatie in het bestand staat, wordt dit uitgevoerd. Vervolgens wordt het originele bestand

verwijderd en wordt het tmp-bestand hernoemd met de naam van het originele bestand.

Ten slotte wordt de recyclerview op de hoogte gesteld dat er een verandering is doorgevoerd en wordt

de recyclerview geüpdatet.

Jarne Van der Plas





P a g e | **9**

Naam & Logo

Als naam koos ik SipWatch. Een samenvoegsel van to Sip, drinken/genieten, en to Watch, kijken. Je

kijkt op de kalender naar welke dranken je hebt gedronken, naar van welke dranken je hebt genoten.

Ook klinkt het gelijkaardig aan stopwatch, wat de tijd bij houdt. SipWatch houd je drankcomsumptie

bij.

Het logo van de app is een flesje met een kalender. De app is robuust met een duidelijk doel:

Bijhouden wat en hoeveel de gebruiker dronk en het weergeven in een kalender. Dit wordt in het logo

weergegeven.

*Figuur 6: Logo SipWatch*

Jarne Van der Plas





P a g e | **10**

Conclusie

Het doel was om een app te schrijven. Dit kon in Java of Kotlin.

Ik koos ervoor om aan de hand van Kotlin een app te schrijven waar de gebruiker zijn

drankconsumpties kan ingeven of fotograferen. Deze consumpties worden weergegeven in een

kalender.

Ik ben tevreden van de app. De app is robuust en met een aantal testdagen achter de riem, heb ik nog

geen problemen gevonden. Ik heb heel wat nieuwe elementen geleerd, voornamelijk (de basis van) een

nieuwe taal maar ook de opbouw van een app.

Er zijn zeker nog verbeteringen mogelijk. Ik veronderstel dat een tekstbestand niet het meest

gestructureerd is. Dit kan vervangen worden door iets meer geschikt. Ook denk ik dat een aantal

elementen niet optimaal zijn geschreven voor performantie. Dit kan verbeterd worden. De data is nog

niet heel groot waardoor dit geen problemen geeft. Een aantal keer bereken ik alles opnieuw. Ik

veronderstel dat een aantal elementen enkel opnieuw berekend kunnen worden afhankelijk van het

toevoegen of verwijderen aan het bestand.

Ook hoopte ik meer kwaliteit uit de tekstherkenning te halen. Mogelijks moeten een aantal

beschikbare machine-learning kits samengevoegd worden om het totale plaatje te berekenen zonder

dat de gebruiker zelf input moet geven. Een kop koffie zou nog steeds lijken op een kop thee of kop

chocomelk waardoor dit een moeilijk onderdeel blijft.

Ik testte ook een barcode scanner parallel aan de tekstherkenning. De scanner werkte correct, echter

vond ik online geen lijst waar de codes in gerefereerd worden. Ik scande de barcode, ontving een

nummer maar kon dit spijtig genoeg niet linken aan een drank. Ik veronderstel dat winkels een eigen,

privé database hebben met hun barcodes.

Een uitbreiding zou kunnen zijn om een soort social media platform te maken waar de gebruikers hun

afbeelding weergegeven wordt op een tijdslijn. Alle connecties zien dan wat de gebruiker gedronken

heeft. Dit kan ook negatief gedrag met zich meebrengen. Gebruikers kunnen elkaar beïnvloeden om

meer alcoholische dranken te consumeren. Dit zou niet het gewenste effect hebben. Het gewenste

effect is dat de gebruikers meer consumpties, voornamelijk water, consumeren.

De zorgsector kan een soortgelijke app gebruiken om bij te houden hoeveel een patiënt dronk. De

patiënt is dan verplicht om elke consumptie te scannen.

Jarne Van der Plas

