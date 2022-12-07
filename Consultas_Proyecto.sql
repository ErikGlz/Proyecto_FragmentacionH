/*
--PROYECTO: FRAGMENTACION HORIZONTAL
--EQUIPO 1
--	Naranjo Ferrara Pamela
--	González González Erik Ulises
*/

------------------------
--SERVIDORES VINCULADOS 
------------------------
--SERVER1.SalesAW
--[LOCALHOST\SERVER2].ProductionAW
--[LOCALHOST\SERVER3].OtrosAW

---------------------------
--CONSULTA A DEL PROYECTO
---------------------------
create procedure consulta_a
@cat int --Parametro: Categoria de un producto
as
begin
set nocount on;
select soh.TerritoryID as Territorio, sum(t.LineTotal) as Total_Venta
			from SERVER1.SalesAW.Sales.SalesOrderHeader soh
			inner join
			(
				select salesorderid, productid, orderqty, linetotal
				from SERVER1.SalesAW.Sales.SalesOrderDetail sod
				where ProductID in 
				(
					select productid
					from [LOCALHOST\SERVER2].ProductionAW.Production.Product
					where ProductSubcategoryID in
					(
					select ProductSubcategoryID
					from [LOCALHOST\SERVER2].ProductionAW.Production.ProductSubcategory
					where ProductCategoryID in 
						(
						select ProductCategoryID
						from [LOCALHOST\SERVER2].ProductionAW.Production.ProductCategory
						where ProductCategoryID = @cat 
						) 
					)
				)
			) as t
			on soh.SalesOrderID = t.SalesOrderID
			group by soh.TerritoryID
			order by soh.TerritoryID
end
go
exec consulta_a 4;
go

---------------------------
--CONSULTA B DEL PROYECTO
---------------------------
create procedure consulta_b
@territorio int --Parametro: ID  de un territorio
as
begin
set nocount on;
		
select top (1) Q.ProductID as ID, Q.Name as Nombre, Q.cantidad as CantidadTotal, 
W.TerritoryID as IDTerritorio, W.NombreTerritorio as NTerritorio, W.cantidadpt as CantidadTerritorio
from (select a.ProductID, b.Name, c.TerritoryID, d.Name as NombreTerritorio, count(*) cantidadpt
	  from SERVER1.SalesAW.Sales.SalesOrderDetail a
	  inner join SERVER1.SalesAW.Sales.SalesOrderHeader c
	  on a.SalesOrderID = c.SalesOrderID
	  inner join [LOCALHOST\SERVER2].ProductionAW.Production.Product b 
	  on a.ProductID = b.ProductID
	  inner join Sales.SalesTerritory d 
	  on c.TerritoryID = d.TerritoryID
	  where d.TerritoryID = @territorio
	  group by a.ProductID, c.TerritoryID, b.Name, d.Name
	  ) as W
	 inner join (
				 select T.ProductID, T.Name, count(*) cantidad
				 from (select a.ProductID, b.Name, c.TerritoryID
					   from SERVER1.SalesAW.Sales.SalesOrderDetail a
				       inner join SERVER1.SalesAW.Sales.SalesOrderHeader c
					   on a.SalesOrderID = c.SalesOrderID
					   inner join [LOCALHOST\SERVER2].ProductionAW.Production.Product b 
					   on a.ProductID = b.ProductID
					   where c.TerritoryID = @territorio ) as T
					   group by ProductID, Name
					  ) as Q
	on W.Name = Q.Name and W.ProductID = Q.ProductID
	order by CantidadTotal desc, CantidadTerritorio desc
end
go
exec consulta_b 5
go

---------------------------
--CONSULTA C DEL PROYECTO
---------------------------
create procedure consulta_c
@cat int, --Parametro: categoria
@loc int  --Parametro: localidad
as
begin
set nocount on;
declare @registrosActualizados int
update d
set d.Quantity = (d.Quantity*1.05)
from
[LOCALHOST\SERVER2].ProductionAW.Production.ProductInventory d
inner join [LOCALHOST\SERVER2].ProductionAW.Production.Product a
on a.ProductID = d.ProductID
inner join [LOCALHOST\SERVER2].ProductionAW.Production.ProductSubcategory b
on a.ProductSubcategoryID = b.ProductSubcategoryID
inner join [LOCALHOST\SERVER2].ProductionAW.Production.ProductCategory c
on c.ProductCategoryID = b.ProductCategoryID
inner join [LOCALHOST\SERVER2].ProductionAW.Production.Location e
on e.LocationID = d.LocationID
where c.ProductCategoryID=@cat and d.LocationID=@loc
			
select @registrosActualizados = @@ROWCOUNT  
select @registrosActualizados as ProductosAumentados			
end
go
exec consulta_c
select * from [LOCALHOST\SERVER2].ProductionAW.Production.ProductCategory --Categorias
select * from [LOCALHOST\SERVER2].ProductionAW.Production.ProductInventory --Localidades
select * from [LOCALHOST\SERVER2].ProductionAW.Production.Location
go

--Vista:	  
create view vista_c as 
select a.ProductID as IDProd, a.Name as NProd, b.ProductCategoryID as IDCat,
c.Name as Categoria, floor(d.Quantity) as Cantidad, d.LocationID as IDLoc, e.Name as NLoc
from [LOCALHOST\SERVER2].ProductionAW.Production.Product a
inner join [LOCALHOST\SERVER2].ProductionAW.Production.ProductSubcategory b
on a.ProductSubcategoryID = b.ProductSubcategoryID
inner join [LOCALHOST\SERVER2].ProductionAW.Production.ProductCategory c
on c.ProductCategoryID = b.ProductCategoryID
inner join [LOCALHOST\SERVER2].ProductionAW.Production.ProductInventory d
on a.ProductID = d.ProductID
inner join [LOCALHOST\SERVER2].ProductionAW.Production.Location e
on e.LocationID = d.LocationID
go

---------------------------
--CONSULTA D DEL PROYECTO
---------------------------
create procedure consulta_d
@territorio int --Parametro: ID de territorio
as
begin
set nocount on;
declare @registrosActualizados int

select (c.CustomerID) CIDc, (c.TerritoryID) TIDc, (soh.CustomerID) CIDs,  (soh.TerritoryID) TIDs
from SERVER1.SalesAW.Sales.SalesOrderHeader soh
inner join SERVER1.SalesAW.Sales.Customer c
on c.CustomerID = soh.CustomerID and c.TerritoryID != soh.TerritoryID
where c.TerritoryID = @territorio
			
select @registrosActualizados = @@ROWCOUNT  
select @registrosActualizados as ClientesTD
end
go
exec consulta_d 1;
go
---------------------------
--CONSULTA E DEL PROYECTO
---------------------------
create procedure consulta_e
@cantidad int, @producto int, @orden int --Parametros: ID de orden, ID de producto y Cantidad de productos
as
begin
set nocount on;
declare @registrosActualizados int

update SERVER1.SalesAW.Sales.SalesOrderDetail 
set OrderQty = @cantidad
from SERVER1.SalesAW.Sales.SalesOrderDetail 
where SalesOrderID = @orden and ProductID = @producto
			
--execute consulta_e @cantidad, @producto, @orden;
SELECT @registrosActualizados = @@ROWCOUNT  
SELECT @registrosActualizados as ProductosAumentados
end
go
SELECT * FROM SERVER1.SalesAW.Sales.SalesOrderDetail --Para verificar ID's de SalesOrderID válidos
go

--Vista:
create view vista_e as
select s.SalesOrderID as Orden, s.ProductID as IDProd, s.OrderQty as Cantidad, p.Name as Nombre
from SERVER1.SalesAW.Sales.SalesOrderDetail s
inner join [LOCALHOST\SERVER2].ProductionAW.Production.Product p
on p.ProductID = s.ProductID
go

---------------------------
--CONSULTA F DEL PROYECTO
---------------------------
create procedure consulta_f
@metodo int, @orden int --Parametros: ID de orden y numero de metodo de envío
as
begin
set nocount on;
declare @registrosActualizados int

update SERVER1.SalesAW.Sales.SalesOrderHeader
set	ShipMethodID = @metodo
from SERVER1.SalesAW.Sales.SalesOrderHeader 
where SalesOrderID = @orden
			
--execute consulta_f @metodo, @orden;
SELECT @registrosActualizados = @@ROWCOUNT  
SELECT @registrosActualizados as MetodosCambiados
end
go
select SalesOrderID from SERVER1.SalesAW.Sales.SalesOrderHeader--Para verificar ID's de SalesOrderID válidos
go

--Vista:
create view vista_f as
select s.SalesOrderID as IDOrden, s.ShipMethodID as IDMetodo, m.Name as Metodo
from SERVER1.SalesAW.Sales.SalesOrderHeader s
inner join [LOCALHOST\SERVER3].OtrosAW.Purchasing.ShipMethod m
on s.ShipMethodID=m.ShipMethodID
go

---------------------------
--CONSULTA G DEL PROYECTO
---------------------------
create procedure consulta_g 
@customerID int, @newEmail nvarchar(50) --Parametros: ID de cliente y nombre del nuevo email
as
begin
if exists(select * from SERVER1.SalesAW.Sales.Customer
	      where CustomerID = @customerID and PersonID is not null)
begin
	update [LOCALHOST\SERVER3].OtrosAW.Person.EmailAddress	
	set EmailAddress = @newEmail
	where BusinessEntityID = (select PersonID from SERVER1.SalesAW.Sales.Customer
						      where CustomerID = @customerID)
end
end
go	
exec consulta_g 11000,'nuevocorreo@gmail.com'
select * from SERVER1.SalesAW.Sales.Customer where PersonID is not null--Para checar valores validos de customerID
go

--Consulta para verificar el cambio de email del cliente
select * from [LOCALHOST\SERVER3].OtrosAW.Person.EmailAddress
where BusinessEntityID = (
	select PersonID from SERVER1.SalesAW.Sales.Customer
	where CustomerID = 11014)
go

---------------------------
--CONSULTA H DEL PROYECTO
---------------------------
create procedure consulta_h
@territorio int --Parametro: ID de territorio
as
begin
set nocount on;

select top 1 SalesPersonID Empleado, count(SalesPersonID) Ordenes, TerritoryID
				from SERVER1.SalesAW.Sales.SalesOrderHeader
				where TerritoryID = @territorio
				group by SalesPersonID,TerritoryID
				order by Ordenes desc
end
go
exec consulta_h 5;
go

---------------------------
--CONSULTA I DEL PROYECTO
---------------------------
create procedure consulta_i
@fecha1 date,@fecha2 date
as
begin
set nocount on;
select st."Group", sum(sod.LineTotal) as Ventas
		from SERVER1.SalesAW.Sales.SalesOrderHeader soh
		inner join SERVER1.SalesAW.Sales.SalesOrderDetail sod
		on soh.SalesOrderID = sod.SalesOrderID
		inner join SERVER1.SalesAW.Sales.SalesTerritory st
		on soh.TerritoryID = st .TerritoryID
		where OrderDate between @fecha1 and @fecha2
		group by st."Group"
end
go
exec consulta_i '2011-05-01','2011-05-31'
go

---------------------------
--CONSULTA J DEL PROYECTO
---------------------------
create procedure consulta_j
@fecha1 date,@fecha2 date
as
begin
set nocount on;
select top 5 sod.ProductID, sum(sod.LineTotal) Ventas
from SERVER1.SalesAW.Sales.SalesOrderHeader soh
inner join SERVER1.SalesAW.Sales.SalesOrderDetail sod
on soh.SalesOrderID = sod.SalesOrderID
where OrderDate BETWEEN @fecha1 AND @fecha2
group by sod.ProductID
order by ventas desc
end
go
exec consulta_j '2011-05-01','2011-05-31';
go
