--1.	Bütün kullanýcýlarý listeleyin.
SELECT*FROM USERS
--2.	USERID'si 3 olan kullanýcýnýn bilgilerini listeleyin.
SELECT*FROM USERS
WHERE ID=3
--3.	Tüm þehirleri ve hangi ülkede olduklarýný listeleyin.
SELECT COUNTRIES.COUNTRY ,
CITIES.CITY
FROM CITIES     
INNER JOIN COUNTRIES ON COUNTRIES.ID=CITIES.COUNTRYID
--4.	En pahalý ürünü listeleyin.
SELECT TOP 1 ITEMS.UNITPRICE,ITEMS.ITEMNAME FROM ITEMS
ORDER BY ITEMS.UNITPRICE DESC
--5.	DISTRICTID'si 5 olan ilçeleri listeleyin.
SELECT*FROM DISTRICTS
WHERE ID=5
--6.	Her bir sipariþin toplam fiyatýný ve tarihini listeleyin.
SELECT ORDERS.TOTALPRICE AS TOPLAM_FÝYAT,ORDERS.DATE_ FROM ORDERS
--7.	CITYID'si 2 olan þehirleri listeleyin.
SELECT*FROM CITIES
WHERE ID=2
--8.	Her kullanýcýnýn sipariþlerinin toplam fiyatýný hesaplayýn.
SELECT 
USERS.NAMESURNAME AS KULLANICI_ADI,
ORDERS.TOTALPRICE AS TOPLAM_FÝYAT
FROM USERS
INNER JOIN ORDERS ON ORDERS.USERID=USERS.ID
--9.	Bütün kasabalarý ve ait olduklarý þehirleri listeleyin.
SELECT TOWNS.TOWN,CITIES.CITY FROM TOWNS
INNER JOIN CITIES ON CITIES.ID=TOWNS.CITYID
--10.Sipariþin ID'si 10 olan ürünün tüm detaylarýný listeleyin.
SELECT*FROM ORDERDETAILS
WHERE ORDERID=10



--------------------------------------------------------------------------------------


--11.	Her þehrin toplam satýþýný hesaplayýn.
SELECT*FROM INVOICEDETAILS

--12.	Bir sipariþteki tüm ürünleri ve miktarlarýný listeleyin.
--13.	Bir kullanýcýya ait adresleri listeleyin.
--14.	Bir ürünün satýþ miktarýný ve toplam gelirini hesaplayýn.
--15.	Faturasý ödenmiþ olan sipariþleri listeleyin.
--16.	Her kullanýcýnýn ödeme türüne göre yaptýðý ödemeleri listeleyin.
--17.	En çok sipariþ veren kullanýcýlarý listeleyin.
--18.	Her þehirdeki toplam ödeme miktarýný hesaplayýn.
--19.	En fazla ödeme yapýlan sipariþi bulun.
--20.	Bütün kasabalarýn ve baðlý olduklarý ilçelerin isimlerini listeleyin.
