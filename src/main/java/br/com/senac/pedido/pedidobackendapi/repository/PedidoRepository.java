package br.com.senac.pedido.pedidobackendapi.repository;

import br.com.senac.pedido.pedidobackendapi.modelo.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
