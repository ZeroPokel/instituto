package com.springpojects.miguelangelfemeniavazquez.instituto.dao.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.springpojects.miguelangelfemeniavazquez.instituto.dao.AlumnosDAO;
import com.springpojects.miguelangelfemeniavazquez.instituto.model.Alumno;

import jakarta.annotation.PostConstruct;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;

@Repository
public class AlumnosDAOImpl extends JdbcDaoSupport implements AlumnosDAO{
    
    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public List<Alumno> findAll(){
        String query = "select * from Alumnos";

        final List<Alumno> alumnos = getJdbcTemplate().query(query, new BeanPropertyRowMapper(Alumno.class));

        return alumnos;
    }

    @Override
    public Alumno findByID(int codigo){
        String query = "select * from Alumnos where codigo = ?";

        Object params [] = {codigo};
        int types [] = {Types.INTEGER};

        Alumno alumno = (Alumno) getJdbcTemplate().queryForObject(query, params, types, new BeanPropertyRowMapper(Alumno.class));

        return alumno;
    }

    @Override
    public void insert(Alumno alumno) {

        String query = "insert into Alumnos (nombre," +
                                            " apellidos," +
                                            " dni," +
                                            " fechaNacimiento," +
                                            " email," +
                                            " nuevo," +
                                            " foto)" +
                                            " values (?, ?, ?, ?, ?, ?, ?)";
        
        
        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(new PreparedStatementCreator() {
                                    
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                                        
                ps.setString(1, alumno.getNombre());
                ps.setString(2, alumno.getApellidos());
                ps.setString(3, alumno.getDni());
                ps.setString(4, alumno.getFechaNacimiento());
                ps.setString(5, alumno.getEmail());
                ps.setBoolean(6, alumno.isNuevo());
                InputStream is = new ByteArrayInputStream(alumno.getFoto());
                    ps.setBlob(7, is);
                    return ps;
            }
        }, keyHolder);
                                    
        alumno.setCodigo(keyHolder.getKey().intValue());
        
    }

    @Override
    public void update(Alumno alumno) {

        String query = "update Alumnos set nombre = ?," +
                                            " apellidos = ?," +
                                            " dni = ?," +
                                            " fechaNacimiento = ?," +
                                            " email = ?," +
                                            " nuevo = ?," +
                                            " foto = ?" +
                                            " where codigo = ?";
        
        Object[] params = {
            alumno.getNombre(),
            alumno.getApellidos(),
            alumno.getDni(),
            alumno.getFechaNacimiento(),
            alumno.getEmail(),
            alumno.isNuevo(),
            alumno.getFoto(),
            alumno.getCodigo()
        };
                                    
        final int[] types = {
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.BOOLEAN,
            Types.BLOB,
            Types.VARCHAR
        };

        int update = getJdbcTemplate().update(query, params, types);
    }

    @Override
    public void delete(int codigo){
        
        String query = "delete from Alumnos where codigo = ?";

        Object params [] = {codigo};
        int types [] = {Types.INTEGER};

        int update = getJdbcTemplate().update(query, params, types);

    }

    @Override
    public void updateImg(Alumno alumno){

        String query = "update Alumnos set foto = ? where codigo = ?";

        Object params [] = {
            alumno.getFoto(),
            alumno.getCodigo()
        };

        final int[] types = {
            Types.BLOB,
            Types.VARCHAR
        };

        int update = getJdbcTemplate().update(query, params, types);
    }

}
