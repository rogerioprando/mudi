function onLoad() {
    var app = new Vue(
        {
            el: '#ofertas',
            data: {
                pedidos: []
            },
            mounted() {
                axios
                    .get('http://localhost:8080/api/pedidos/aguardando')
                    .then(response => {
                        response.data.forEach(pedido => {
                            pedido.ofertaEnviada = false;
                            pedido.erros = {
                                valor: '',
                                dataDaEntrega: ''
                            }
                        })
                        this.pedidos = response.data
                    })
            },
            methods: {
                enviarOferta: function (pedido) {
                    pedido.erros = {
                        valor: '',
                        dataDaEntrega: ''
                    };

                    axios
                        .post('http://localhost:8080/api/ofertas', {
                            pedidoId: pedido.id,
                            valor: pedido.valorNegociado,
                            dataDaEntrega: pedido.dataDaEntrega,
                            comentario: pedido.comentario
                        })
                        .then(response => pedido.ofertaEnviada = true)
                        .catch(error => {
                            error.response.data.errors.forEach(error => {
                                pedido.erros[error.field] = error.defaultMessage;
                            })
                        })
                }
            }
        });
}