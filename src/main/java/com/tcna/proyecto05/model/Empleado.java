package com.tcna.proyecto05.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Empleado {

    private int id;
    private String nombre;
    private String apellido;
    private double salario;
    private Departamento departamento;
    private List<Proyecto> proyectosAsignados;
}
