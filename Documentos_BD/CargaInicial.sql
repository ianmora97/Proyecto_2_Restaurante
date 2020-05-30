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
-- ADICIONAL 
-- ============================================
insert into adicional (nombre_platillo,nombre,tipo) values ('Alfajor','Extras',1);
select * from adicional;


-- ============================================ 
-- OPCIONES 
-- ============================================
insert into opcion  (id_adicional,nombre,precio)  values (1,'Más Azucar',100);
insert into opcion  (id_adicional,nombre,precio)  values (1,'Extra Dulce de leche',500);
select * from opcion;

-- ============================================ 
-- SELECCIONADA 
-- ============================================
insert into seleccionada values (1,1);
insert into seleccionada values (1,2);
select * from seleccionada;

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

