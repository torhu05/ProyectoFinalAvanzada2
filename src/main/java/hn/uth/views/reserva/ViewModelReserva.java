package hn.uth.views.reserva;

import java.util.List;

import hn.uth.data.Cliente;
import hn.uth.data.Habitacion;
import hn.uth.data.Reserva;

public interface ViewModelReserva {

	void mostrarReservaEnGrid(List<Reserva> items);
	void mostrarMensajeError(String mensaje);
	void mostrarMensajeExito(String mensaje);
	List<Reserva> RElementos();
	void mostrarClienteEnComboBox(List<Cliente> items);
	void mostrarHabitacionEnComboBox(List<Habitacion> items);
}
