package br.com.alura.mvc.mudi.controller;

import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.StatusPedido;
import br.com.alura.mvc.mudi.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    PedidoRepository pedidoRepository;

    // /home
    @GetMapping
    public String home(Model model, Principal principal){

        // Principal vai retornar

        //List<Pedido> pedidos = pedidoRepository.findAll();
        List<Pedido> pedidos = pedidoRepository.findAllByUsuario(principal.getName());

        model.addAttribute("pedidos", pedidos);

        return "home";
    }

    // /home/aguardando
    @GetMapping("/{status}")
    public String porStatus(@PathVariable("status") String status, Model model){

        List<Pedido> pedidos = pedidoRepository.findByStatus(StatusPedido.valueOf(status.toUpperCase()));
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("status", status);

        return "home";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String onError() {
        return "redirect:/home";
    }
    /*
    //ModelAndView do Spring MVC

    @GetMapping("/home2")
    public ModelAndView home2(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("pedidos", pedidos);
        return mv;
    }
    */
}
