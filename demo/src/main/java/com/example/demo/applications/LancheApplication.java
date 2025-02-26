package com.example.demo.applications;

import com.example.demo.entities.Lanche;
import com.example.demo.repositories.LancheRepository;
import com.example.demo.services.LancheService;

import java.util.List;

public class LancheApplication {
    private LancheService lancheService;
    private LancheRepository lancheRepository;

    public LancheApplication(
            LancheService lancheService,
            LancheRepository lancheRepository) {
        this.lancheService = lancheService;
        this.lancheRepository = lancheRepository;
    }

    public void cadastrar(Lanche lanche) {
        this.lancheRepository.adicionar(lanche);
        this.lancheService.salvar(lanche);
    }

    public boolean excluir(int codigo) {
        Lanche lanche = this.lancheRepository.buscarPorCodigo(codigo);
        if (lanche != null) {
            this.lancheService.excluir(codigo, lanche);
            this.lancheRepository.remover(codigo);
            return true;
        }
        return false;
    }

    public boolean atualizar(int codigo, Lanche lanche) {
        Lanche lancheExistente = this.lancheRepository.buscarPorCodigo(codigo);
        if (lancheExistente != null) {
            this.lancheService.atualizar(codigo, lanche);
            this.lancheRepository.atualizar(codigo, lanche);
            return true;
        }
        return false;
    }

    public List<Lanche> buscar() {
        return this.lancheRepository.buscar();
    }

    public Lanche buscarPorCodigo(int codigo) {
        return this.lancheRepository.buscarPorCodigo(codigo);
    }

    public double calcularLanche(Lanche lanche, int quantidade) {
        return lanche.getPreco() * quantidade;
    }
}
