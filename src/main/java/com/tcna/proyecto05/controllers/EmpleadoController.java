package com.tcna.proyecto05.controllers;

import com.tcna.proyecto05.mappers.DepartamentoMapper;
import com.tcna.proyecto05.mappers.EmpleadoMapper;
import com.tcna.proyecto05.mappers.Empleado_ProyectoMapper;
import com.tcna.proyecto05.mappers.ProyectoMapper;
import com.tcna.proyecto05.model.Departamento;
import com.tcna.proyecto05.model.Empleado;
import com.tcna.proyecto05.model.Proyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoMapper empleadoMapper;

    @Autowired
    private DepartamentoMapper departamentoMapper;

    @Autowired
    private ProyectoMapper proyectoMapper;

    @Autowired
    private Empleado_ProyectoMapper empleadoProyectoMapper;


    @GetMapping()
    public String listaEmpleados(Model model) {
        List<Empleado> empleados = empleadoMapper.getAllEmpleados();
        System.out.println(empleados.toString());
        model.addAttribute("empleados", empleados);
        return "empleados/listaEmpleados";
    }


    @GetMapping("/crear")
    public String mostrarFormularioCrearEmpleado(Model model) {
        List<Departamento> departamentos = departamentoMapper.getAllDepartamentos();
        List<Proyecto> proyectos = proyectoMapper.getAllProyectos();
        Empleado empleado = new Empleado();
        model.addAttribute("empleado", empleado);
        model.addAttribute("departamentos", departamentos);
        model.addAttribute("proyectos", proyectos);

        return "empleados/crearEmpleado";
    }

    @PostMapping("/crear")
    public String crearEmpleado(@ModelAttribute Empleado empleado,
                                @RequestParam("departamento_id") String idDepartamento,
                                @RequestParam(name = "proyecto_id", required = false) Optional<List<Integer>> idProyectos) {

        int departamentoId;
        try {
            departamentoId = Integer.parseInt(idDepartamento);
        } catch (NumberFormatException e) {
            // Manejar el caso en el que el valor de departamento_id no es un número válido
            // Por ejemplo, puedes establecer un valor predeterminado o mostrar un mensaje de error
            departamentoId = 0; // O cualquier otro valor predeterminado
        }

        if (departamentoId != 0) {
            Departamento departamento = departamentoMapper.getDepartamentoById(departamentoId);
            empleado.setDepartamento(departamento);
        }
        // Insertar el empleado en la base de datos
        empleadoMapper.insertarEmpleado(empleado);

        if (idProyectos.isPresent() && idProyectos.get() != null) {
            List<Integer> proyectosIds = idProyectos.get();

            // Obtener los proyectos por sus IDs y asignarlos al empleado
            List<Proyecto> proyectos = proyectoMapper.getProyectosByIds(proyectosIds);
            empleado.setProyectosAsignados(proyectos);

            // Insertar las relaciones empleado-proyecto en la tabla intermedia
            for (Integer idProyecto : proyectosIds) {
                empleadoProyectoMapper.insertarTablaIntermediaProyecto(idProyecto, empleado.getId());
            }
        }

        return "redirect:/empleados";
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarEmpleado(@PathVariable int id, Model model) {
        Empleado empleado = empleadoMapper.getEmpleadoById(id);
        List<Departamento> departamentos = departamentoMapper.getAllDepartamentos();
        List<Proyecto> proyectos = proyectoMapper.getAllProyectos();

        List<Proyecto> proyectosAsignados = empleadoMapper.getProyectosAsignadosAEmpleados(id);

        empleado.setProyectosAsignados(proyectosAsignados);

        model.addAttribute("empleado", empleado);
        model.addAttribute("departamentos", departamentos);
        model.addAttribute("proyectos", proyectos);

        return "empleados/editarEmpleado";
    }

    @PostMapping("/editar/{id}")
    public String editarEmpleado(@PathVariable int id, @ModelAttribute Empleado empleado,
                                 @RequestParam("departamento_id") String idDepartamento,
                                 @RequestParam(name = "proyecto_id", required = false) Optional<List<Integer>> idProyectos) {
        int departamentoId;
        try {
            departamentoId = Integer.parseInt(idDepartamento);
        } catch (NumberFormatException e) {
            // Manejar el caso en el que el valor de departamento_id no es un número válido
            // Por ejemplo, puedes establecer un valor predeterminado o mostrar un mensaje de error
            departamentoId = 0; // O cualquier otro valor predeterminado
        }

        if (departamentoId != 0) {
            Departamento departamento = departamentoMapper.getDepartamentoById(departamentoId);
            empleado.setDepartamento(departamento);
        }

        empleadoProyectoMapper.eliminarRelacionesPorEmpleado(id);

        empleado.setId(id);
        empleadoMapper.updateEmpleado(empleado);
        if (idProyectos.isPresent() && idProyectos.get() != null) {
            List<Integer> proyectosIds = idProyectos.get();
            List<Proyecto> proyectos = proyectoMapper.getProyectosByIds(proyectosIds);
            empleado.setProyectosAsignados(proyectos);

            for (Integer idProyecto : proyectosIds) {
                empleadoProyectoMapper.insertarTablaIntermediaProyecto(idProyecto, empleado.getId());
            }
        }

        return "redirect:/empleados";
    }


    @GetMapping("/mostrar_proyectos_empleados/{id}")
    public String mostrarProyectosDelEmpleado(@PathVariable int id, Model model) {
        Empleado empleado = empleadoMapper.getEmpleadoById(id);
        List<Proyecto> proyectos = empleadoMapper.getProyectosAsignadosAEmpleados(id);
        model.addAttribute("empleado", empleado);
        model.addAttribute("proyectos", proyectos);

        return "empleados/mostrar_proyectos_empleados";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable int id) {
        empleadoProyectoMapper.eliminarRelacionesPorEmpleado(id);
        empleadoMapper.eliminarEmpleado(id);

        return "redirect:/empleados";
    }


}
