use restaurante;

-- ============================================ 
-- USUARIO 
-- ============================================ 
insert into usuario values ('david@gmail.com','pulplix','123',1);
insert into usuario values ('ian@gmail.com','ianMora666','123',1);

select *from usuario;
delete from usuario where usuario_correo = 'asda@gmail.com';
-- ============================================ 
-- Cliente 
-- ============================================ 
insert into cliente values ('david@gmail.com','David','Aguilar','22546789');
select *from cliente;

-- ============================================ 
-- UBICACION 
-- ============================================
insert into ubicacion (direccion,provincia,canton,codigo_postal) values('Hatillo 5 detras de los multifamiliares casa #14','San Jose','San Jose',500);
insert into ubicacion (direccion,provincia,canton,codigo_postal) values('Escazu','San Jose','San Jose',300);
insert into ubicacion (direccion,provincia,canton,codigo_postal) values('Santa Ana','San Jose','San Jose',200);
insert into ubicacion (direccion,provincia,canton,codigo_postal) values('San Pedro','San Jose','San Jose',100);
insert into ubicacion (direccion,provincia,canton,codigo_postal) values('Desamparados','San Jose','San Jose',400);
insert into ubicacion (direccion,provincia,canton,codigo_postal) values('La Carpio','Barrios Bajos','San Jose',1);
insert into ubicacion (direccion,provincia,canton,codigo_postal) values('Sagrada Familia','Barrios Bajos','San Jose',2);
select * from ubicacion;
-- ============================================ 
-- Metodos de Pago 
-- ============================================
insert into metodos_pago (nombre) values('Cash');
insert into metodos_pago (nombre) values('Credit Card');
insert into metodos_pago (nombre) values('Paypal');
delete from metodos_pago where nombre = 'Credit Cart';
select * from metodos_pago;

-- ============================================ 
-- CATEGORIA 
-- ============================================ 

insert into categoria  (nombre) values  ('Appetizer');
insert into categoria  (nombre) values  ('Main Course');
insert into categoria  (nombre) values  ('Salads');
insert into categoria  (nombre) values  ('Seafoods');
insert into categoria  (nombre) values  ('Traditional');
insert into categoria  (nombre) values  ('Vegetarian');
insert into categoria  (nombre) values  ('Soups');
insert into categoria  (nombre) values  ('Desserts');
insert into categoria  (nombre) values  ('Drinks');
insert into categoria  (nombre) values  ('Specials');
insert into categoria  (nombre) values  ('Rice Dishes');
select * from categoria;

-- ============================================ 
-- Platillo 
-- ============================================ 

insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Tostadas con Queso',1,'Deliciosas tostadas de pan casero con mantequila de ajo y queso parmesano derretido',1800);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Casado de Chuleta',2,'Arroz, frijoles, ensalada,un huevo frito, chuleta con cebolla, chiledulce y un fresco natural ',4000);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Casado de pollo',2,'Arroz, frijoles, ensalada,un huevo frito, Filet de pollo a la Ranch',3800);

insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('La tres quesos',3,'Una ensalada de lechuga, tomate, chile dulce, fajitas de pollo, y queso parmezano,Roquefort y Chedar ',6500);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Arroz con camarones',4,'Arroz conndimentado con cubito maggie de un color amarillo McDonnalds y muchos camarones carnosos y jugosos ',8000);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Gallo pinto',5,'Arroz y frijoles revueltos con mucha salsa Lizano, maduro, salchichón, huevo (revuelto o frito) y queso frito',3000);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Las dos Z',6,'El punto debil de todo vegetariano, un delicioso plato con Zacate y Zanahoria rallada con una salsita Ranch de acompañamiento',3000);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Sopa Maggie',7,'Sopa de paquete para quitar cualquier gripe. Hasta el COVID-19',1000);

insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Tres Leches',8,'Un delicioso postre con las leches mas deliciosas',1000);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Alfajor',8,'Galleta con dulce de leche y glaseado de azucar glass',1100);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Natural',9,' Piña, Guanabana, arroz con Piña, Sandia, Melon, Fresa, Maracuya',1000);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('La gordita',10,'Dos tortas de carne de res o pollo, tomate, lechuga, salsa barbacoa, queso amarillo y queso frito. ',6000);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Arroz Chino',11,'Arroz directamente traido de China, con su pegocidad característica, acompañado de salsa Soya.',6000);

select * from platillo;

-- ============================================ 
-- ADICIONAL 
-- ============================================
insert into adicional (id_platillo,nombre,tipo,requerida) values (1,'Extras de Tostadas',1,0);
insert into adicional (id_platillo,nombre,tipo,requerida) values (2,'Extra Ingredientes',1,0);
insert into adicional (id_platillo,nombre,tipo,requerida) values (2,'Extra Componentes',1,0);

insert into adicional (id_platillo,nombre,tipo,requerida) values (6,'Extras de Ensalada',1,0);
insert into adicional (id_platillo,nombre,tipo,requerida) values (3,'Salsas',1,0);

insert into adicional (id_platillo,nombre,tipo,requerida) values (3,'Qtd',0,1);
insert into adicional (id_platillo,nombre,tipo,requerida) values (10,'Extras de Dulces',1,0);

select * from adicional where id_platillo = 10;

-- ============================================ 
-- OPCIONES 
-- ============================================
insert into opcion  (id_adicional,nombre,precio)  values (7,'Más Azucar',100);
insert into opcion  (id_adicional,nombre,precio)  values (7,'Extra Dulce de leche',500);
insert into opcion  (id_adicional,nombre,precio)  values (3,'Queso',500);
insert into opcion  (id_adicional,nombre,precio)  values (3,'Dos Bistec',1500);

insert into opcion  (id_adicional,nombre,precio)  values (2,'Tomate',500);
insert into opcion  (id_adicional,nombre,precio)  values (2,'Cebolla',500);
insert into opcion  (id_adicional,nombre,precio)  values (2,'Ajo',500);
insert into opcion  (id_adicional,nombre,precio)  values (4,'1',4100);

insert into opcion  (id_adicional,nombre,precio)  values (6,'1',4100);
insert into opcion  (id_adicional,nombre,precio)  values (6,'2',8200);
insert into opcion  (id_adicional,nombre,precio)  values (5,'Salsa Picante',300);
insert into opcion  (id_adicional,nombre,precio)  values (5,'Salsa Ranch',400);

insert into opcion  (id_adicional,nombre,precio)  values (5,'Salsa Tomate',300);
insert into opcion  (id_adicional,nombre,precio)  values (5,'Salsa Mayonesa',300);


select * from opcion where nombre = 'Salsa Ranch';

-- ============================================ 
-- ORDEN 
-- ============================================
insert into orden (usuario_correo,id_metodo_pago,id_ubicacion,fecha_entrega,estatus,tipo_entrega,asap,total) values ();
delete from orden where id_orden= 6; 
select max(id_orden) id_orden from orden;
select * from platilloSeleccionado;
select id_platilloSeleccionado, a.nombre from adicional_seleccionada adS, adicional a
where adS.id_adicional = a.id_adicional;

select * from opcionesSeleccionadas;

-- ============================================ 
-- DETALLE 
-- ============================================ 
