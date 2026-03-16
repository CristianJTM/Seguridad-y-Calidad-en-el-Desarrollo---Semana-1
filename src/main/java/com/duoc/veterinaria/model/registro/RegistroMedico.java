package com.duoc.veterinaria.model.registro;

import java.util.ArrayList;
import java.util.List;

import com.duoc.veterinaria.model.paciente.Paciente;

public class RegistroMedico {

    private Paciente paciente;

    private List<Diagnostico> diagnosticos = new ArrayList<>();

    private List<Tratamiento> tratamientos = new ArrayList<>();

    private List<Medicamento> medicamentos = new ArrayList<>();

    private List<NotaMedica> notas = new ArrayList<>();

}