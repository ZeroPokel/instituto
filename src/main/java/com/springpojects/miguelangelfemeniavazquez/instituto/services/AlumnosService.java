package com.springpojects.miguelangelfemeniavazquez.instituto.services;

import java.util.List;

import com.springpojects.miguelangelfemeniavazquez.instituto.model.Alumno;

public interface AlumnosService {

    public List<Alumno> findAll();
    public Alumno findByID(int codigo);
    public void insert(Alumno alumno);
    public void update(Alumno alumno);
    public void delete(int codigo);
    
}
