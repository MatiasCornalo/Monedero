package dds.monedero.model;

class Deposito implements tipoTransaccion {
    public double calcularValorDe(Cuenta cuenta,Double monto){
        return cuenta.getSaldo() + monto;
    }
}
