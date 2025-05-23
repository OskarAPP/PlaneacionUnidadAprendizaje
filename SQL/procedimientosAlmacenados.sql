use pua;
delimiter $$
CREATE PROCEDURE eliminarDocente (idDocente int, idCargo int)
BEGIN
	DELETE FROM docente_facultad WHERE id_docente=idDocente;
    DELETE FROM docente_materia WHERE id_docente=idDocente;
    DELETE FROM acceso WHERE id_docente=idDocente;
	DELETE FROM tipo_docente WHERE id_docente=idDocente;
	DELETE FROM docente WHERE id_docente=idDocente;
    DELETE FROM pua_docente where id_docente=idDocente;
	if idCargo=1 then
		UPDATE FACULTAD set id_director=null  where id_director=idDocente;
	elseif idCargo=2 then
		UPDATE FACULTAD set id_secretario_academico=null where id_secretario_academico=idDocente;
	elseif idCargo=3 then
		update CARRERA set id_coordinador=null where id_coordinador=idDocente;
	elseif idCargo=4 then
		update ACADEMIA set id_presidente=null where id_presidente=idDocente;
	elseif idCargo=5 then
		update ACADEMIA set id_secretario=null where id_secretario=idDocente;
	end if;
END $$

delimiter $$
CREATE PROCEDURE eliminarDocenteFacultad (idDocente int, nombreFacultad varchar(100), idCargo int)
BEGIN
	declare idFa int; 
    set idFa=(select id_facultad from facultad where facultad=nombreFacultad);
	DELETE FROM docente_facultad WHERE id_docente=idDocente and id_facultad=idFa;
	set idCargo=(select idTipo from tipo_docente where id_docente=idDocente and idFacultad=idFa);
    DELETE FROM tipo_docente where id_docente=idDocente and idFacultad=idFa;
    if idCargo=1 then
		UPDATE FACULTAD set id_director=null  where id_director=idDocente and id_facultad=idFa;
	elseif idCargo=2 then
		UPDATE FACULTAD set id_secretario_academico=null where id_secretario_academico=idDocente and id_facultad=idFa;
	elseif idCargo=3 then
		update CARRERA set id_coordinador=null where id_coordinador=idDocente and id_facultad=idFa;
	elseif idCargo=4 then
		update ACADEMIA set id_presidente=null where id_presidente=idDocente and id_facultad=idFa;
	elseif idCargo=5 then
		update ACADEMIA set id_secretario=null where id_secretario=idDocente and id_facultad=idFa;	
	end if;
END $$

delimiter $$
CREATE PROCEDURE updateDocenteFacultad (idDocente int, idFacultadN int, idFacultadA int)
BEGIN
	declare idAcademia int;
    declare idCarrera int;
    declare idCargo int;
    update TIPO_DOCENTE set idFacultad=idFacultadN where id_docente=idDocente and idFacultad=idFacultadA;
    update docente_facultad set id_facultad=idFacultadN where id_docente=idDocente and id_facultad=idFacultadA;
    
    set idAcademia=(select t.idAcademia from tipo_docente t where t.id_docente=idDocente and t.idFacultad=idFacultadN);
    set idCargo=(select t.idTipo from tipo_docente t where t.id_docente=idDocente and t.idFacultad=idFacultadN);
    set idCarrera=(select t.idCarrera from tipo_docente t where t.id_docente=idDocente and t.idFacultad=idFacultadN);
    
    update TIPO_DOCENTE set idTipo=6 where id_docente=idDocente and idFacultad=idFacultadN;
    
    if idCargo=1 then
		UPDATE FACULTAD set id_director=null  where id_director=idDocente and id_facultad=idFacultadA;
	elseif idCargo=2 then
		UPDATE FACULTAD set id_secretario_academico=null where id_secretario_academico=idDocente and id_facultad=idFacultadA;
	elseif idCargo=3 then
		update CARRERA set id_coordinador=null where id_coordinador=idDocente and id_facultad=idFacultadA;
	elseif idCargo=4 then
		update ACADEMIA set id_presidente=null where id_presidente=idDocente and id_facultad=idFacultadA;
	elseif idCargo=5 then
		update ACADEMIA set id_secretario=null where id_secretario=idDocente and id_facultad=idFacultadA;	
	end if;
END $$

delimiter $$
CREATE PROCEDURE updatePuesto (idDocente int, idFac int, idCar int, idAcad int, idCargo int, idCargoA int)
BEGIN
	update TIPO_DOCENTE set idCarrera=null, idAcademia=null where id_docente=idDocente and idFacultad=idFacultad;
    if idCargo=1 then
		update TIPO_DOCENTE set idTipo=idCargo where id_docente=idDocente and idFacultad=idFac;
		UPDATE FACULTAD set id_director=idDocente, id_secretario_academico=null  where id_facultad=idFac;
	elseif idCargo=2 then
		update TIPO_DOCENTE set idTipo=idCargo where id_docente=idDocente and idFacultad=idFac;
		UPDATE FACULTAD set id_director=null, id_secretario_academico=idDocente where id_facultad=idFac;
	elseif idCargo=3 then
		update TIPO_DOCENTE set idTipo=idCargo, idCarrera=idCar where id_docente=idDocente and idFacultad=idFac;
		update CARRERA set id_coordinador=idDocente where id_carrera=idCar and id_facultad=idFac;
	elseif idCargo=4 then
		update TIPO_DOCENTE set idTipo=idCargo, idAcademia=idAcad where id_docente=idDocente and idFacultad=idFac;
		update ACADEMIA set id_presidente=idDocente,id_secretario=null where id_academia=idAcad and id_facultad=idFac;
	elseif idCargo=5 then
		update TIPO_DOCENTE set idTipo=idCargo, idAcademia=idAcad where id_docente=idDocente and idFacultad=idFac;
		update ACADEMIA set id_presidente=null, id_secretario=idDocente where id_academia=idAcad and id_facultad=idFac;	
	end if;
    
    if idCargoA=1 then
		UPDATE FACULTAD set id_director=null  where id_director=idDocente and id_facultad=idFac;
	elseif idCargoA=2 then
		UPDATE FACULTAD set id_secretario_academico=null where id_secretario_academico=idDocente and id_facultad=idFac;
	elseif idCargoA=3 then
		update CARRERA set id_coordinador=null where id_coordinador=idDocente and id_facultad=idFac;
	elseif idCargoA=4 then
		update ACADEMIA set id_presidente=null where id_presidente=idDocente and id_facultad=idFac;
	elseif idCargoA=5 then
		update ACADEMIA set id_secretario=null where id_secretario=idDocente and id_facultad=idFac;	
	end if;
END $$

delimiter $$
CREATE PROCEDURE eliminarMateria (idMateria int)
BEGIN
	DELETE from academia_materia where id_materia=idMateria;
	DELETE FROM carrera_materia WHERE id_materia=idMateria;
	DELETE FROM materia WHERE id_materia=idMateria;
END

delimiter $$
CREATE PROCEDURE eliminarCarrera (carreraID int)
BEGIN
    declare idFacultadA int;
    declare idCoordinadorF int;
    
    set idCoordinadorF=(select c.id_coordinador from carrera c where c.id_carrera=carreraID);
    set idFacultadA=(select c.id_facultad from carrera c where c.id_carrera=carreraID);
    
    update TIPO_DOCENTE set idCarrera=NULL, idTipo=6 where id_docente=idCoordinadorF and idFacultad=idFacultadA;
    
    update competenciaesp set id_carrera=NULL where id_carrera=carreraID;
	
	DELETE FROM carrera_materia WHERE id_carrera=carreraID;
	DELETE FROM carrera WHERE id_carrera=carreraID;
END $$

delimiter $$
CREATE PROCEDURE asignarMateria (idCarrera int, idMateria int, opc int)
BEGIN
	if opc=1 then
		INSERT INTO carrera_materia VALUES (idCarrera, idMateria);
	elseif opc=2 then
		DELETE FROM carrera_materia WHERE id_materia=idMateria AND id_carrera=idCarrera;
	end if;
END $$

delimiter $$
CREATE PROCEDURE eliminarFacultad (facultadID int)
BEGIN
    DELETE from tipo_docente where idFacultad=facultad;
	UPDATE academia SET id_facultad=NULL WHERE id_facultad=facultadID;
	UPDATE carrera SET id_facultad=NULL WHERE id_facultad=facultadID;
    DELETE FROM docente_facultad WHERE id_facultad=facultadID;
	DELETE FROM facultad WHERE id_facultad=facultadID;
END $$

DELIMITER $$
CREATE PROCEDURE eliminarAcademia(academiaID int)
BEGIN
	declare idPresidente int;
    declare idSecretario int;
    declare idFacultadA int;
    
    set idPresidente=(select a.id_presidente from academia a where a.id_academia=academiaID);
    set idSecretario=(select a.id_secretario from academia a where a.id_academia=academiaID);
    set idFacultadA=(select a.id_facultad from academia a where a.id_academia=academiaID);
    
    update TIPO_DOCENTE set idAcademia=NULL, idTipo=6 where id_docente=idPresidente and idFacultad=idFacultadA;
    update TIPO_DOCENTE set idAcademia=NULL, idTipo=6 where id_docente=idSecretario and idFacultad=idFacultadA;
    
	DELETE FROM academia_materia WHERE id_academia=academiaID;
	DELETE FROM academia WHERE id_academia=academiaID;
END $$

delimiter $$
CREATE PROCEDURE eliminarGenerica (genericaID int)
BEGIN
	DELETE FROM pua_competenciagen WHERE id_generica=genericaID;
	DELETE FROM competenciagen WHERE id_generica=genericaID;
END $$ 

delimiter $$
CREATE PROCEDURE eliminarSubcompetencia (subcompetencia int)
BEGIN
	DELETE FROM `bibliografia_subcompetencia` WHERE `id_subcompetencia`=subcompetencia;
	DELETE FROM `evidencia_subcompetencia` WHERE `id_subcompetencia`=subcompetencia;
	DELETE FROM `subcompetencia_material` WHERE `id_subcompetencia`=subcompetencia;
	DELETE FROM `subcompetencia_ambiente` WHERE `id_subcompetencia`=subcompetencia;
	DELETE FROM `actividadal` WHERE `id_subcompetencia`=subcompetencia;
	DELETE FROM `actividaddoc` WHERE `id_subcompetencia`=subcompetencia;
	DELETE FROM `criterio` WHERE `id_subcompetencia`=subcompetencia;
END $$

delimiter $$
CREATE PROCEDURE eliminarDocenteCompetencia (pua int)
BEGIN
	DELETE FROM `pua_docente` WHERE `id_pua`=pua;
DELETE FROM `pua_competenciaesp` WHERE `id_pua`=pua;
DELETE  FROM `pua_competenciagen` WHERE `id_pua`=pua;
END

delimiter $$
CREATE PROCEDURE eliminarEspecifica (especificaID int)
BEGIN
	DELETE FROM pua_competenciaesp WHERE id_especifica=especificaID;
	DELETE FROM competenciaesp WHERE id_especifica=especificaID;
END $$

delimiter $$
CREATE PROCEDURE asignarMateriaDocente (idDocente int, idMateria int, opc int)
BEGIN
	if opc=1 then
		INSERT INTO docente_materia VALUES (idDocente, idMateria);
	elseif opc=2 then
		DELETE FROM docente_materia WHERE id_materia=idMateria AND id_docente=idDocente;
	end if;
END $$