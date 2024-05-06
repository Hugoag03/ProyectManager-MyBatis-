package com.tcna.proyecto05.mappers;

import com.tcna.proyecto05.model.Empleado;
import com.tcna.proyecto05.model.Proyecto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProyectoMapper {

    @Insert("INSERT INTO proyecto(Nombre) VALUES (#{nombre})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID")
    void insertarProyecto(Proyecto proyecto);

    @Select("SELECT * FROM proyecto WHERE ID = #{id}")
    Proyecto getProyectoById(int id);

    @Select("SELECT * FROM proyecto WHERE ID IN (#{ids})")
    List<Proyecto> getProyectosByIds(@Param("ids") List<Integer> ids);

    @Select("SELECT * FROM proyecto")
    List<Proyecto> getAllProyectos();

    @Update("UPDATE proyecto SET Nombre = #{nombre} WHERE ID = #{id}")
    void updateProyecto(Proyecto proyecto);

    @Delete("DELETE FROM proyecto WHERE ID = #{id}")
    void eliminarProyecto(int id);

    @Results(id = "empleadoResultMap", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "nombre", column = "Nombre"),
            @Result(property = "apellido", column = "Apellido"),
            @Result(property = "salario", column = "Salario"),
            @Result(property = "departamento", column = "ID_DEPARTAMENTO",
                    one = @One(select = "com.tcna.proyecto05.mappers.DepartamentoMapper.getDepartamentoById"))
    })
    @Select("SELECT * FROM empleado as e INNER JOIN proyecto_empleado as pe ON e.ID = pe.ID_EMPLEADO INNER JOIN proyecto as p ON pe.ID_PROYECTO = p.ID WHERE p.ID = #{idProyecto}")
    List<Empleado> getEmpleadosAsignadosAProyecto(int idProyecto);


}
