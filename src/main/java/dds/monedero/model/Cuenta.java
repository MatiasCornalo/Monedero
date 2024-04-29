package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();
  private double limite = 1000;

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void poner(double cuanto) {
    this.validarMonto(cuanto);
    this.validarDepositos();

    Movimiento movimientoNuevo = new Movimiento(LocalDate.now(),cuanto,new Deposito());
    this.agregarMovimiento(movimientoNuevo);
  }

  // Delegue la responsabilidad de conocer la cantidad de depositos que tiene una cuenta
  public long cantidadDeDepositos(){
    return movimientos.stream().filter(movimiento -> movimiento.isDeposito()).count();
  }

  public void sacar(double cuanto) {
    this.validarMonto(cuanto);
    this.validarSaldo(cuanto);

    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    this.disminuirLimite(montoExtraidoHoy);

    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + this.getLimite());
    }
    
    Movimiento movimientoNuevo = new Movimiento(LocalDate.now(),cuanto,new Extraccion());
    this.agregarMovimiento(movimientoNuevo);
  }

  // La cuenta conocia demasiado la composicion de un movimiento, se rompia el encapsulamiento
  public void agregarMovimiento(Movimiento movimiento) {
    movimiento.modificarSaldo(this);
    movimientos.add(movimiento);
  }

  public void aumentarDinero(Double monto){
    saldo += monto;
  }

  public void disminuirDinero(Double monto){
    saldo -= monto;
  }

  public double getLimite(){
    return limite;
  }

  public void disminuirLimite(double monto){
    limite -= monto;
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> movimiento.fueExtraidoEn(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public void validarMonto(double monto){
    if (monto <= 0) throw new MontoNegativoException(monto + ": el monto a ingresar debe ser un valor positivo");
  }

  public void validarSaldo(double cuanto){
    if (getSaldo() - cuanto < 0) throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
  }

  public void validarDepositos(){
    if (this.cantidadDeDepositos() >= 3) throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
  }
    
  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}
