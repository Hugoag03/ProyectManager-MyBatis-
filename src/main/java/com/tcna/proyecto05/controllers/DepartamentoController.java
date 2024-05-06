package com.tcna.proyecto05.controllers;

import com.tcna.proyecto05.mappers.DepartamentoMapper;
import com.tcna.proyecto05.mappers.EmpleadoMapper;
import com.tcna.proyecto05.model.Departamento;
import com.tcna.proyecto05.model.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoMapper departamentoMapper;

    @Autowired
    private EmpleadoMapper empleadoMapper;

    @GetMapping()
    public String listaDepartamentos(Model model) {
        List<Departamento> departamentos = departamentoMapper.getAllDepartamentos();
        model.addAttribute("departamentos", departamentos);

        return "departamentos/listaDepartamentos";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCreacionDepartamento(Model model) {
        model.addAttribute("departamento", new Departamento());
        return "departamentos/crearDepartamento";
    }

    @GetMapping("/{id}")
    public String mostrarDetallesDepartamento(@PathVariable int id, Model model) {
        Departamento departamento = departamentoMapper.getDepartamentoById(id);
        model.addAttribute("departamento", departamento);
        return "departamentos/detallesDepartamento";
    }

    @PostMapping("/crear")
    public String crearDepartamento(@ModelAttribute Departamento departamento) {
        departamentoMapper.insertDepartamento(departamento);
        System.out.println(departamento.toString());
        return "redirect:/departamentos";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicionDepartamento(@PathVariable int id, Model model) {
        Departamento departamento = departamentoMapper.getDepartamentoById(id);
        model.addAttribute("departamento", departamento);

        return "departamentos/editarDepartamento";
    }

    @PostMapping("/{id}/editar")
    public String editarDepartamento(@PathVariable int id, @ModelAttribute Departamento departamento) {
        departamento.setId(id);
        departamentoMapper.updateDepartamento(departamento);

        return "redirect:/departamentos";
    }


    //EL PARAMETRO NOMBRE ES NULO ¿?
    @GetMapping("/{id}/eliminar")
    public String eliminarDepartamento(@PathVariable int id) {
        List<Empleado> empleados = empleadoMapper.getEmpleadosDeDepartamentoById(id);

        for (Empleado empleado : empleados) {
            empleado.setDepartamento(null);
            empleadoMapper.updateEmpleado(empleado);
        }

        departamentoMapper.deleteDepartamentoById(id);

        return "redirect:/departamentos";
    }


}
