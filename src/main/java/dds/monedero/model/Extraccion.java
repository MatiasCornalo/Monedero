package dds.monedero.model;

class Extraccion implements tipoTransaccion {
    public double calcularValorDe(Cuenta cuenta,Double monto){
        return cuenta.getSaldo() - monto;
    }
}
