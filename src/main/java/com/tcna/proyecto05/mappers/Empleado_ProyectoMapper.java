package com.tcna.proyecto05.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Empleado_ProyectoMapper {

    @Insert("INSERT INTO proyecto_empleado(ID_PROYECTO, ID_EMPLEADO) VALUES(#{id_proyecto}, #{id_empleado})")
    void insertarTablaIntermediaProyecto(int id_proyecto, int id_empleado);

    @Delete("DELETE FROM proyecto_empleado WHERE ID_EMPLEADO = #{id}")
    void eliminarRelacionesPorEmpleado(int id);

    @Delete("DELETE FROM proyecto_empleado WHERE ID_PROYECTO = #{id}")
    void eliminarRelacionesPorProyecto(int id);
}
