use pua;

select * from materia;
select * from carrera_materia;

insert into carrera_materia values(1,1); #logica
insert into carrera_materia values(4,2); #topo
insert into carrera_materia values(3,3); #electr
insert into carrera_materia values(2,4); #est
insert into carrera_materia values(1, 5); 
insert into carrera_materia values(2, 5);
insert into carrera_materia values(3, 5); #calculo

#query infoMateria
select * 
from materia m
join area_materia a on m.id_area = a.id_area
join nucleo_materia n on m.id_nucleo = n.id_nucleo
join tipo_materia t on m.id_tipo = t.id_tipo
where m.id_materia = 5;

#query materias
select * 
from carrera c
join carrera_materia cm on cm.id_carrera = c.id_carrera
join materia m on cm.id_materia = m.id_materia
where cm.id_carrera = 2;

select * from SUBCOMPETENCIA;
select * from PUA;

insert into SUBCOMPETENCIA values (null,3,1,'edfgdfvdfv',4,20);
