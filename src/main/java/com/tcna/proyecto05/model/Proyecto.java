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
public class Proyecto {

    private int id;
    private String nombre;
    private List<Empleado> empleadosAsignados;
}
