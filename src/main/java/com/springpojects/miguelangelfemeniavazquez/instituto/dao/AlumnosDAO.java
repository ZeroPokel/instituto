package com.springpojects.miguelangelfemeniavazquez.instituto.dao;

import java.util.List;

import com.springpojects.miguelangelfemeniavazquez.instituto.model.Alumno;

public interface AlumnosDAO {
    
    public List<Alumno> findAll();
    public Alumno findByID(int codigo);
    public void insert(Alumno alumno);
    public void update(Alumno alumno);
    public void delete(int codigo);
    public void updateImg(Alumno alumno);

}
