package br.com.senac.pedido.pedidobackendapi.controller;

import br.com.senac.pedido.pedidobackendapi.dto.PedidoRequest;
import br.com.senac.pedido.pedidobackendapi.dto.PedidoResponse;
import br.com.senac.pedido.pedidobackendapi.modelo.Pedido;
import br.com.senac.pedido.pedidobackendapi.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;


    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<List<PedidoResponse>> retornarPedido() {
        List<Pedido> pedidoList = new ArrayList<>();

        pedidoList = pedidoRepository.findAll();

        List<PedidoResponse> pedidoResponseList = new ArrayList<>(); //

        for (Pedido dadoPedido : pedidoList) {
            pedidoResponseList.add(new PedidoResponse(dadoPedido.getId(), dadoPedido.getNome(), dadoPedido.getRua(), dadoPedido.getBairro(),dadoPedido.getCidade(), dadoPedido.getEstado(), dadoPedido.getValor()));
        }

        return ResponseEntity.ok().body(pedidoResponseList);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Void> criarPedido(@RequestBody PedidoRequest pedido) {
        Pedido pedidoModel = new Pedido();

        pedidoModel.setNome(pedido.getNome());
        pedidoModel.setRua(pedido.getRua());
        pedidoModel.setBairro(pedido.getBairro());
        pedidoModel.setEstado(pedido.getEstado());
        pedidoModel.setCidade(pedido.getCidade());
        pedidoModel.setValor(pedido.getValor());


        try {
            pedidoRepository.save(pedidoModel);

            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<Void> atualizarPedido(@RequestBody PedidoRequest pedidoRequest, @PathVariable Long id) {
        Optional<Pedido> pedido;

        pedido = pedidoRepository.findById(id)
                .map(record -> {
                    record.setRua(pedidoRequest.getRua());
                    record.setCidade(pedidoRequest.getCidade());
                    record.setNome(pedidoRequest.getNome());
                    record.setBairro(pedidoRequest.getBairro());
                    record.setValor(pedidoRequest.getValor());
                    record.setEstado(pedidoRequest.getEstado());
                    return pedidoRepository.save(record);
                });

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping
    public ResponseEntity<Void> apagarPedido (){
        pedidoRepository.deleteAll();
        return ResponseEntity.ok().body(null);
    }

}