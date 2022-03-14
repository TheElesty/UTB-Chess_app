## Scénář
Rozšířit aplikci na hraní šachů o konectivitu k hardwarové šachovnici pomocí bluetooth modulu. Vytvořit databázi a api pro její připojení k této aplkikaci. Do databáze se ukládají data o jednotlivých šachových hrách a deních výzev. Aplikace umožní krokovat v jednotlivých šachových partiích (rozehraných i dokončených) a "simulovat" rozehranou partii s modulem šachů. 

## Požadavky
- Vytoři interface pomocí arduina pro simulování šqchové partie
- Navrhnout databázi a Rest API
- Předelat strukturu současné aplikace do MVVM
- Vytvořit modul simulující výslednou HW šachovnici (komunikace po BT, atd.)

## Technologie
- Kotlin -- Android Studio
- MySQL + PHP pro správu DB
- Arduino NANO + BT HC-05 BLE modul -- ArduinoIDE, Wiring (mutace C pro programování low-end HW)

## Časový plán
-- Přípravné práce -- 8h
- Příprava podkladů pro tvorbu aplikace -- 2h
- Návrh DB a API -- 2h
- Návrh struktury mobilní aplikace (předělání staré struktury) -- 2h
- Zprovoznění DB -- 2h

-- Tvorba aplikace -- 25h
- Předelání kostry programu podle nového návrhu -- 3h
- Krokování šachových her -- 3h
- Dodělání kontroly možných tahů -- 4h
- Napsání PGN, FEN a AGN (long verze) parseru pro komunikaci a zaznamenávání šachových her -- 3h
- Vytvoření DB a požadované API -- 3h
- Vytvoření modulu pro simulaci HW šachovnice -- 3h
- Komunikace aplikace s DB pomocí API -- 3h
- Komunikace aplikace s HW modulem -- 3h

-- Nasazení aplikace -- 7h
- Testování a opravy -- 4h
- Refactoring kódu -- 3h
