package com.springpojects.miguelangelfemeniavazquez.instituto.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springpojects.miguelangelfemeniavazquez.instituto.model.Alumno;
import com.springpojects.miguelangelfemeniavazquez.instituto.services.AlumnosService;

@Controller
@RequestMapping("alumnos")
public class AlumnoController {

    @Autowired
    AlumnosService alumnosService;
    
    @RequestMapping(value = "/list")
    public ModelAndView list() {

        List<Alumno> alumnos = alumnosService.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("alumnos", alumnos);
        modelAndView.setViewName("alumnos/list");

        return modelAndView;
    }

    @GetMapping(value = "/create")
    public ModelAndView create(Alumno alumno) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("alumno", new Alumno());
        modelAndView.setViewName("alumnos/create");

        return modelAndView;
    }

    @PostMapping(path = "/save")
    public ModelAndView save(Alumno alumno, 
        @RequestParam("imageForm") MultipartFile multipartFile) throws IOException{
        
        byte[] imagen = multipartFile.getBytes();

        alumno.setFoto(imagen);

        alumnosService.insert(alumno);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:edit/" + alumno.getCodigo());

        return modelAndView;
    }

    @GetMapping(path = { "/edit/{codigo}" })
    public ModelAndView edit(
            @PathVariable(name = "codigo", required = true) int codigo) {

        Alumno alumno = alumnosService.findByID(codigo);
                
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("alumno", alumno);
        modelAndView.setViewName("alumnos/edit");

        return modelAndView;
    }

    @PostMapping(path = { "/update" })
    public ModelAndView update(Alumno alumno, 
        @RequestParam("imageForm") MultipartFile multipartFile) throws IOException {

        byte[] imagen = multipartFile.getBytes();

        alumno.setFoto(imagen);

        alumnosService.update(alumno);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:edit/" + alumno.getCodigo());
        
        return modelAndView;
    }

    @GetMapping(path = { "/delete/{codigo}" })
    public ModelAndView delete(
            @PathVariable(name = "codigo", required = true) int codigo) {

        alumnosService.delete(codigo);
        List<Alumno> alumnos = alumnosService.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("alumnos", alumnos);
        modelAndView.setViewName("alumnos/list");

        return modelAndView;
    }
}
