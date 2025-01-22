--1.	B�t�n kullan�c�lar� listeleyin.
SELECT*FROM USERS
--2.	USERID'si 3 olan kullan�c�n�n bilgilerini listeleyin.
SELECT*FROM USERS
WHERE ID=3
--3.	T�m �ehirleri ve hangi �lkede olduklar�n� listeleyin.
SELECT COUNTRIES.COUNTRY ,
CITIES.CITY
FROM CITIES     
INNER JOIN COUNTRIES ON COUNTRIES.ID=CITIES.COUNTRYID
--4.	En pahal� �r�n� listeleyin.
SELECT TOP 1 ITEMS.UNITPRICE,ITEMS.ITEMNAME FROM ITEMS
ORDER BY ITEMS.UNITPRICE DESC
--5.	DISTRICTID'si 5 olan il�eleri listeleyin.  dfv
SELECT*FROM DISTRICTS
WHERE ID=5
--6.	Her bir sipari�in toplam fiyat�n� ve tarihini listeleyin.
SELECT ORDERS.TOTALPRICE AS TOPLAM_F�YAT,ORDERS.DATE_ FROM ORDERS
--7.	CITYID'si 2 olan �ehirleri listeleyin.
SELECT*FROM CITIES
WHERE ID=2
--8.	Her kullan�c�n�n sipari�lerinin toplam fiyat�n� hesaplay�n.
SELECT 
USERS.NAMESURNAME AS KULLANICI_ADI,
ORDERS.TOTALPRICE AS TOPLAM_F�YAT
FROM USERS
INNER JOIN ORDERS ON ORDERS.USERID=USERS.ID
--9.	B�t�n kasabalar� ve ait olduklar� �ehirleri listeleyin.
SELECT TOWNS.TOWN,CITIES.CITY FROM TOWNS
INNER JOIN CITIES ON CITIES.ID=TOWNS.CITYID
--10.Sipari�in ID'si 10 olan �r�n�n t�m detaylar�n� listeleyin.
SELECT*FROM ORDERDETAILS
WHERE ORDERID=10



--------------------------------------------------------------------------------------


--11.	Her �ehrin toplam sat���n� hesaplay�n.
SELECT*FROM INVOICEDETAILS

--12.	Bir sipari�teki t�m �r�nleri ve miktarlar�n� listeleyin.
--13.	Bir kullan�c�ya ait adresleri listeleyin.
--14.	Bir �r�n�n sat�� miktar�n� ve toplam gelirini hesaplay�n.
--15.	Faturas� �denmi� olan sipari�leri listeleyin.
--16.	Her kullan�c�n�n �deme t�r�ne g�re yapt��� �demeleri listeleyin.
--17.	En �ok sipari� veren kullan�c�lar� listeleyin.
--18.	Her �ehirdeki toplam �deme miktar�n� hesaplay�n.
--19.	En fazla �deme yap�lan sipari�i bulun.
--20.	B�t�n kasabalar�n ve ba�l� olduklar� il�elerin isimlerini listeleyin.
