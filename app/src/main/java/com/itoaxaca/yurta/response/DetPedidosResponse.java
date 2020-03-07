package com.itoaxaca.yurta.response;

import java.util.ArrayList;

public class DetPedidosResponse {
    private ArrayList<DetPedidoResponse> det_pedidos;
    public ArrayList<DetPedidoResponse> getDet_pedidos() {
        return det_pedidos;
    }

    public DetPedidosResponse(ArrayList<DetPedidoResponse> det_pedidos) {
        this.det_pedidos = det_pedidos;
    }

    public void setDet_pedidos(ArrayList<DetPedidoResponse> det_pedidos) {
        this.det_pedidos = det_pedidos;
    }
}
