package com.tcna.proyecto05.controllers;

import com.tcna.proyecto05.mappers.EmpleadoMapper;
import com.tcna.proyecto05.mappers.Empleado_ProyectoMapper;
import com.tcna.proyecto05.mappers.ProyectoMapper;
import com.tcna.proyecto05.model.Empleado;
import com.tcna.proyecto05.model.Proyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/proyectos")
public class ProyectoController {

    @Autowired
    private EmpleadoMapper empleadoMapper;

    @Autowired
    private ProyectoMapper proyectoMapper;

    @Autowired
    private Empleado_ProyectoMapper empleadoProyectoMapper;

    @GetMapping()
    public String listaProyectos(Model model) {
        List<Proyecto> proyectos = proyectoMapper.getAllProyectos();
        model.addAttribute("proyectos", proyectos);

        return "proyectos/listaProyectos";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrearProyecto(Model model) {
        List<Empleado> empleados = empleadoMapper.getAllEmpleados();
        model.addAttribute("proyecto", new Proyecto());
        model.addAttribute("empleados", empleados);
        return "proyectos/formularioProyecto";
    }

    @PostMapping("/crear")
    public String crearProyecto(@ModelAttribute Proyecto proyecto,
                                @RequestParam("empleado_id") Optional<List<Integer>> empleadosIds) {

        proyectoMapper.insertarProyecto(proyecto);

        if (empleadosIds.isPresent() && empleadosIds.get() != null) {
            List<Integer> empleadoList = empleadosIds.get();
            List<Empleado> idEmpleados = empleadoMapper.getEmpleadosByIds(empleadoList);
            proyecto.setEmpleadosAsignados(idEmpleados);

            for (Integer idEmpleado : empleadoList) {
                empleadoProyectoMapper.insertarTablaIntermediaProyecto(proyecto.getId(), idEmpleado);
            }
        }
        return "redirect:/proyectos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProyecto(@PathVariable int id, Model model) {
        Proyecto proyecto = proyectoMapper.getProyectoById(id);
        List<Empleado> empleados = empleadoMapper.getAllEmpleados();
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("empleados", empleados);
        return "proyectos/formularioProyecto";
    }

    @PostMapping("/editar/{id}")
    public String editarProyecto(@PathVariable int id, @ModelAttribute Proyecto proyecto,
                                 @RequestParam("empleado_id") Optional<List<Integer>> idEmpleados) {

        empleadoProyectoMapper.eliminarRelacionesPorProyecto(id);

        proyecto.setId(id);
        proyectoMapper.updateProyecto(proyecto);

        if (idEmpleados.isPresent() && idEmpleados.get() != null) {
            List<Integer> empleadoList = idEmpleados.get();
            List<Empleado> empleados = empleadoMapper.getEmpleadosByIds(empleadoList);
            proyecto.setEmpleadosAsignados(empleados);

            for (Integer idEmpleado : empleadoList) {
                empleadoProyectoMapper.insertarTablaIntermediaProyecto(proyecto.getId(), idEmpleado);
            }
        }

        return "redirect:/proyectos";
    }


    @GetMapping("/mostrar_empleados_proyectos/{id}")
    public String mostrarEmpleadosDelProyecto(@PathVariable int id, Model model) {
        Proyecto proyecto = proyectoMapper.getProyectoById(id);
        List<Empleado> empleados = proyectoMapper.getEmpleadosAsignadosAProyecto(id);


        model.addAttribute("proyecto", proyecto);
        model.addAttribute("empleados", empleados);

        return "proyectos/mostrar_empleados_proyectos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProyecto(@PathVariable int id) {
        empleadoProyectoMapper.eliminarRelacionesPorProyecto(id);
        proyectoMapper.eliminarProyecto(id);

        return "redirect:/proyectos";
    }

}
