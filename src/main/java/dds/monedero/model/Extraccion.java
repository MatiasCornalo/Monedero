package dds.monedero.model;

class Extraccion implements tipoTransaccion {
    public void modificarSaldo(Cuenta cuenta,Double monto){
        cuenta.disminuirDinero(monto);
    }
}
