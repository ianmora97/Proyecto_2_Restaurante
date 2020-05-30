use restaurante;

-- ============================================ 
-- USUARIO 
-- ============================================ 
insert into usuario values ('david@gmail.com','123','David','Aguilar Rojas', 22556688);
insert into usuario values ('ian@gmail.com','123','Ian','Mora Rodrigez', 22556688);
select *from usuario;

-- ============================================ 
-- CATEGORIA 
-- ============================================ 

insert into categoria  (nombre) values  ('Postres');
insert into categoria  (nombre) values  ('Plato Fuerte');
insert into categoria  (nombre) values  ('Entrada');
insert into categoria  (nombre) values  ('Salida');
select * from categoria;

-- ============================================ 
-- Platillo 
-- ============================================ 
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Tres Leches',1,'Un delicioso postre con las leches mas deliciosas',1000);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Fancito de coco',1,'Flan sabemas',600);
insert into platillo (nombre_platillo,id_categoria,descripcion,precio) values ('Alfajor',1,'Galleta con dulce de leche y glaseado de azucar glass',1100);
insert into platillo(nombre_platillo,id_categoria,descripcion,precio)  values ('Queue de chocolate',1,'Nada que decir, un simple queue pero de chocolate',800);
select * from platillo order by 1;

-- ============================================ 
-- OPCIONES 
-- ============================================
select * from opcion;
insert into opcion (nombre_platillo,nombre,tipo,precio) values ('Alfajor','cantidad',1,1);
insert into opcion (nombre_platillo,nombre,tipo,precio) values ('Alfajor','Más Azucar',2,100);
insert into opcion (nombre_platillo,nombre,tipo,precio) values ('Alfajor','Extra Dulce de leche',2,500);


-- ============================================ 
-- UBICACION 
-- ============================================
insert into ubicacion values('Hatillo 5 detras de los multifamiliares casa #14','San Jose','San Jose',10110);
select * from ubicacion;

-- ============================================ 
-- ORDEN 
-- ============================================
insert into orden (usuario_correo,direccion,fecha_entrega,estatus,tipo_entrega,asap) values ('david@gmail.com','Hatillo 5 detras de los multifamiliares casa #14',CURDATE(),1,1,1);
select * from orden;

-- ============================================ 
-- DETALLE 
-- ============================================

-- LA CANTIDAD NO DEBERIA DE ESTAR AQUI COMO UN CAMPO EN LA TABLA SINO COMO UNA OPCION
insert into detalle  (id_orden,nombre_platillo,cantidad,total) values (1,'Alfajor',1,1100);
select * from detalle;



-- ============================================ 
-- SELECCIONADA 
-- ============================================
insert into seleccionada values (1,1);
insert into seleccionada values (1,2);
insert into seleccionada values (1,3);

select us.nombre Usuario, u.provincia Provincia, od.id_orden Num_Orden, c.nombre Categoria, p.nombre_platillo Platillo, o.nombre Opcion from 
seleccionada s, opcion o, detalle d, orden od, ubicacion u, platillo p, categoria c, usuario us
where s.id_opcion = o.id_opcion and
s.id_detalle = d.id_detalle and
d.nombre_platillo = p.nombre_platillo and
p.id_categoria = c.id_categoria and
d.id_orden = od.id_orden and
od.direccion = u.direccion and
od.usuario_correo = us.usuario_correo;

