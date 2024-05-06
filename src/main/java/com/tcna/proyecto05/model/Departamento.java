package com.tcna.proyecto05.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Departamento {

    private int id;
    private String nombre;
    private String ubicacion;
}
