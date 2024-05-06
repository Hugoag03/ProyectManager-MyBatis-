package com.tcna.proyecto05.mappers;

import com.tcna.proyecto05.model.Empleado;
import com.tcna.proyecto05.model.Proyecto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmpleadoMapper {

    @Insert("INSERT INTO empleado (Nombre, Apellido, Salario, ID_DEPARTAMENTO) VALUES (#{nombre}, #{apellido}, #{salario}, #{departamento.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID")
    void insertarEmpleado(Empleado empleado);

    @Select("SELECT * FROM empleado WHERE ID = #{id}")
    Empleado getEmpleadoById(int id);


    @Select("SELECT * FROM empleado WHERE ID IN (#{ids})")
    List<Empleado> getEmpleadosByIds(@Param("ids") List<Integer> ids);

    @Results(id = "empleadoResultMap", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "nombre", column = "Nombre"),
            @Result(property = "apellido", column = "Apellido"),
            @Result(property = "salario", column = "Salario"),
            @Result(property = "departamento", column = "ID_DEPARTAMENTO",
                    one = @One(select = "com.tcna.proyecto05.mappers.DepartamentoMapper.getDepartamentoById"))
    })
    @Select("SELECT * FROM empleado")
    List<Empleado> getAllEmpleados();


    @Update("UPDATE empleado SET Nombre = #{nombre}, Apellido = #{apellido}, Salario = #{salario}, ID_DEPARTAMENTO = #{departamento.id} WHERE ID = #{id}")
    void updateEmpleado(Empleado empleado);

    @Delete("DELETE FROM empleado WHERE ID = #{id}")
    void eliminarEmpleado(int id);

    @Select("SELECT * FROM proyecto as p INNER JOIN proyecto_empleado as pe ON p.ID = pe.ID_PROYECTO INNER JOIN empleado as e ON pe.ID_EMPLEADO = e.ID WHERE e.ID = #{idEmpleado}")
    List<Proyecto> getProyectosAsignadosAEmpleados(int idEmpleado);

    @Select("SELECT e.* FROM empleado as e INNER JOIN departamento as d ON e.ID_DEPARTAMENTO = d.ID WHERE d.ID = #{id}")
    List<Empleado> getEmpleadosDeDepartamentoById(int id);

}
