package com.springpojects.miguelangelfemeniavazquez.instituto.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springpojects.miguelangelfemeniavazquez.instituto.dao.AlumnosDAO;
import com.springpojects.miguelangelfemeniavazquez.instituto.model.Alumno;
import com.springpojects.miguelangelfemeniavazquez.instituto.services.AlumnosService;

@Service
public class AlumnosServiceImpl implements AlumnosService{
    
    @Autowired
    AlumnosDAO alumnosDAO;

    @Override
    public List<Alumno> findAll() {
        return alumnosDAO.findAll();
    }

    @Override
    public Alumno findByID(int codigo){
        return alumnosDAO.findByID(codigo);
    }

    @Override
    public void insert(Alumno alumno){
        alumnosDAO.insert(alumno);

    }

    @Override
    public void delete(int codigo){
        alumnosDAO.delete(codigo);
    }

    @Override
    public void update(Alumno alumno){
        alumnosDAO.update(alumno);

        if(alumno.getFoto() != null && alumno.getFoto().length > 0){
            alumnosDAO.updateImg(alumno);
        }
    }
}
