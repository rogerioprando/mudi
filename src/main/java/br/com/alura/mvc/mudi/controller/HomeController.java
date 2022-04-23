package br.com.alura.mvc.mudi.controller;

import br.com.alura.mvc.mudi.model.Pedido;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model){
        Pedido pedido = new Pedido();
        pedido.setNomeProduto("REDMI NOTE 11");
        pedido.setUrlImagem("https://m.media-amazon.com/images/I/71bru4lFzzL._AC_SL1500_.jpg");
        pedido.setUrlProduto("https://www.amazon.com.br/REDMI-NOTE-11-TWILIGHT-128GB/dp/B09QSC4HN9/?_encoding=UTF8&pd_rd_w=9sLlm&pf_rd_p=7721c677-7472-42db-8338-bc52d43d62e7&pf_rd_r=96QXFKN9FMQWA38TBYW6&pd_rd_r=821bab64-f18c-4d6c-9d7a-567a0193d6df&pd_rd_wg=gRS26&ref_=pd_gw_ci_mcx_mr_hp_d");
        pedido.setDescricao("Descrição qualquer para esse pedido");

        List<Pedido> pedidos = Arrays.asList(pedido);
        model.addAttribute("pedidos", pedidos);

        return "home";
    }

}
