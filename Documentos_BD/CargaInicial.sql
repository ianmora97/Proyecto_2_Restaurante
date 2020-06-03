use restaurante;

-- ============================================ 
-- USUARIO 
-- ============================================ 
insert into usuario values ('david@gmail.com','pulplix','123',1);
insert into usuario values ('ian@gmail.com','ianMora666','123',1);
select *from usuario;

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
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Tres Leches',8,'Un delicioso postre con las leches mas deliciosas',1000);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Fancito de coco',8,'Flan sabemas',600);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Alfajor',8,'Galleta con dulce de leche y glaseado de azucar glass',1100);
insert into platillo(nombre_platillo,id_categoria,descripcion,precio)  values ('Queue de chocolate',8,'Nada que decir, un simple queue pero de chocolate',800);
select * from platillo order by 1;
select * from platillo where id_platillo=1;
-- ============================================ 
-- UBICACION 
-- ============================================
insert into ubicacion (direccion,provincia,canton,codigo_postal) values('Hatillo 5 detras de los multifamiliares casa #14','San Jose','San Jose',10110);
select * from ubicacion;

-- ============================================ 
-- ORDEN 
-- ============================================
insert into orden (usuario_correo,id_ubicacion,fecha_entrega,estatus,tipo_entrega,asap) values ('david@gmail.com',1,CURDATE(),1,1,1);
select * from orden;

-- ============================================ 
-- DETALLE 
-- ============================================

-- LA CANTIDAD NO DEBERIA DE ESTAR AQUI COMO UN CAMPO EN LA TABLA SINO COMO UNA OPCION
insert into detalle  (id_orden,id_platillo,cantidad,total) values (1,3,1,1100);
select * from detalle;
-- ============================================ 
-- ADICIONAL 
-- ============================================
insert into adicional (id_platillo,nombre,tipo) values (3,'Extras',1);
select * from adicional;
-- ============================================ 
-- OPCIONES 
-- ============================================
insert into opcion  (id_adicional,nombre,precio)  values (1,'Más Azucar',100);
insert into opcion  (id_adicional,nombre,precio)  values (1,'Extra Dulce de leche',500);
insert into opcion  (id_adicional,nombre,precio)  values (1,'Coma Coma pastar Roma',500);

select * from opcion;
-- ============================================ 
-- SELECCIONADA 
-- ============================================
insert into seleccionada values (1,1);
insert into seleccionada values (1,2);
select * from seleccionada;


-- ============================================ 
-- Select casi completo de una Orden 
-- ============================================
-- SE DEBDE DE ARREGLAR DEBIDO AL CAMBIO REALIZADADO EL 02/06/2020
select us.username Usuario, u.provincia Provincia, od.id_orden Num_Orden, c.nombre Categoria, p.nombre_platillo Platillo, ad.nombre Adicional, o.nombre Opcion from 
seleccionada s, opcion o, detalle d, orden od, ubicacion u, platillo p, categoria c, usuario us, adicional ad
where  s.id_opcion = o.id_opcion and
ad.id_adicional = o.id_adicional and
s.id_detalle = d.id_detalle and
d.nombre_platillo = p.nombre_platillo and
p.id_categoria = c.id_categoria and
d.id_orden = od.id_orden and
od.direccion = u.direccion and
od.usuario_correo = us.usuario_correo;

