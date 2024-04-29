package dds.monedero.model;

class Deposito implements tipoTransaccion {
    public void modificarSaldo(Cuenta cuenta,Double monto){
        cuenta.aumentarDinero(monto);
    }
    
    public boolean isExtraccion() {
        return false;
    }
}
