package dds.monedero.model;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
  // Nota: En ningún lenguaje de programación usen jamás doubles (es decir, números con punto flotante) para modelar dinero en el mundo real.
  // En su lugar siempre usen numeros de precision arbitraria o punto fijo, como BigDecimal en Java y similares
  // De todas formas, NO es necesario modificar ésto como parte de este ejercicio. 
  private double monto;
  private tipoTransaccion tipo;

  public Movimiento(LocalDate fecha, double monto, tipoTransaccion tipo) {
    this.fecha = fecha;
    this.monto = monto;
    this.tipo  = tipo;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public boolean fueExtraidoEn(LocalDate fecha) {
    return tipo.isExtraccion() && esDeLaFecha(fecha);
  }

  public boolean isDeposito(){
    return !tipo.isExtraccion();
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public void modificarSaldo(Cuenta cuenta) {
    tipo.modificarSaldo(cuenta,monto);
  }

}
