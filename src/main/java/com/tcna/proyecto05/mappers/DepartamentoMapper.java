package com.tcna.proyecto05.mappers;

import com.tcna.proyecto05.model.Departamento;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartamentoMapper {

    @Insert("INSERT INTO departamento (Nombre, Ubicacion) VALUES (#{nombre}, #{ubicacion})")
    void insertDepartamento(Departamento departamento);

    @Select("SELECT * FROM departamento WHERE ID = #{id}")
    Departamento getDepartamentoById(int id);

    @Select("SELECT * FROM departamento")
    List<Departamento> getAllDepartamentos();

    @Update("UPDATE departamento SET Nombre = #{nombre}, Ubicacion = #{ubicacion} WHERE ID = #{id}")
    void updateDepartamento(Departamento departamento);

    @Delete("DELETE FROM departamento WHERE ID = #{id}")
    void deleteDepartamentoById(int id);


}
