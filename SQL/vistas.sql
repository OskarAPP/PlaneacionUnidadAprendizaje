use pua;


CREATE VIEW vista_genericas AS
select m.nombre_materia, m.id_materia, cg.competencia_generica,cg.id_generica, c.nombre_carrera, c.id_carrera, f.facultad, f.id_facultad
from pua p, materia m, PUA_COMPETENCIAGEN pg, competenciagen cg, CARRERA_MATERIA cm, CARRERA c, facultad f
where p.id_materia = m.id_materia
	  and p.id_pua = pg.id_pua
	  and pg.id_generica = cg.id_generica
-- 	  and f.facultad = 'Ingeniería' 
-- 	  and c.id_carrera = '2'
-- 	  and m.id_materia='5'		
	  and m.id_materia = cm.id_materia
	  and cm.id_carrera = c.id_carrera
	  and c.id_facultad = f.id_facultad;

CREATE VIEW vista_especificas AS
select m.nombre_materia, m.id_materia, cg.competencia_especifica, cg.id_especifica, c.nombre_carrera, c.id_carrera, f.facultad, f.id_facultad
from pua p, materia m, PUA_COMPETENCIAESP pg, competenciaesp cg, CARRERA_MATERIA cm, CARRERA c, facultad f
where p.id_materia = m.id_materia
	  and p.id_pua = pg.id_pua
	  and  pg.id_especifica = cg.id_especifica
	  and m.id_materia = cm.id_materia
      and cm.id_carrera = c.id_carrera
	  and c.id_facultad = f.id_facultad;

select count(*) from vista_especificas where id_carrera=1 and id_especifica =2;

INSERT INTO `pua`.`COMPETENCIAESP` (`competencia_especifica`,`id_carrera`) VALUES ('Esto es solo una prueba','1');
INSERT INTO `pua`.`COMPETENCIAESP` (`competencia_especifica`,`id_carrera`) VALUES ('Esto es solo otra prueba','2');

CREATE VIEW cargoDocente AS
    SELECT f.id_facultad, f.facultad, t.idTipo, t.tipo, td.id_docente
	FROM facultad f, tipo_docente td, tipo t WHERE
	f.id_facultad=td.idFacultad and t.idTipo=td.idTipo 