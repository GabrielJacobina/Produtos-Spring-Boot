package com.example.Spring.Boot.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.Spring.Boot.models.ProdutoModel;
import com.example.Spring.Boot.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoModel>> getAllProdutos() {
        List<ProdutoModel> produtosList = produtoRepository.findAll();
        if (produtosList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<ProdutoModel>>(produtosList, HttpStatus.OK);
        }
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<ProdutoModel> getOneProduto(@PathVariable(value = "id") long id){
        Optional<ProdutoModel> produtoO = produtoRepository.findById(id);
        if (!produtoO.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<ProdutoModel>(produtoO.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/produtos")
    public ResponseEntity<ProdutoModel> saveProduto(@RequestBody @Validated ProdutoModel produto){
        return new ResponseEntity<ProdutoModel>(produtoRepository.save(produto), HttpStatus.CREATED);
    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable(value = "id") long id) {
        Optional<ProdutoModel> produtoO = produtoRepository.findById(id);
        if (!produtoO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            produtoRepository.delete(produtoO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<ProdutoModel> updateProduto(@PathVariable(value = "id") long id, @RequestBody @Validated ProdutoModel produto){
        Optional<ProdutoModel> produtoO = produtoRepository.findById(id);
        if (!produtoO.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            produto.setIdProduto(produtoO.get().getIdProduto());
            return new ResponseEntity<ProdutoModel>(produtoRepository.save(produto), HttpStatus.OK);
        }
    }
}
