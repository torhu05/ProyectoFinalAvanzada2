package hn.uth.controller;

import hn.uth.data.ClienteResponse;
import hn.uth.data.Habitacion;
import hn.uth.data.HabitacionResponse;
import hn.uth.data.Reserva;
import hn.uth.data.ReservaResponse;
import hn.uth.model.DatabaseRepositoryImpl;
import hn.uth.views.creserva.CReservaView;
import hn.uth.views.reserva.ViewModelReserva;
public class InteractorImplReserva implements InteractorReserva{

	private DatabaseRepositoryImpl modelo;
	private ViewModelReserva vista;
	
	public InteractorImplReserva(ViewModelReserva view) {
		super();
		this.vista = view;
		this.modelo = DatabaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
	}
	public InteractorImplReserva() {
		super();
		this.modelo = DatabaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
	}
	

	@Override
	public void consultarReserva() {
		// TODO Auto-generated method stub
		try {
			ReservaResponse respuesta = this.modelo.consultarReserva();
			if(respuesta == null ||respuesta.getCount() == 0 || respuesta.getItems() == null) {
				this.vista.mostrarMensajeError("No hay Reserva que mostrar");
				
			}else {
				this.vista.mostrarReservaEnGrid(respuesta.getItems());
			}
			
		}catch (Exception error) {
			error.printStackTrace();
		}
		
	}
	@Override
	public void CrearReserva(Reserva nuevo) {
		try {
			boolean creado = this.modelo.CrearReserva(nuevo);
			if(creado == true) {
				this.vista.mostrarMensajeExito("Reserva creada exitosamente");
				
			}else {
				this.vista.mostrarMensajeError("Error al crear la habitacion");
			}
			
		}catch (Exception error) {
			error.printStackTrace();
		}
	}
	
	@Override
	public void ActualizarReserva(Reserva cambiar) {
		try {
			boolean creado = this.modelo.ActualizarReserva(cambiar);
			if(creado == true) {
				this.vista.mostrarMensajeExito("Reserva modificada exitosamente");
				
			}else {
				this.vista.mostrarMensajeError("Error al modificar reserva");
			}
			
		}catch (Exception error) {
			error.printStackTrace();
		}
	}
	
	@Override
	public void EliminarReserva(String id) {
		try {
			boolean Eliminado = this.modelo.EliminarReserva(id);
			if(Eliminado == true) {
				this.vista.mostrarMensajeExito("Reserva Borrada exitosamente");
				
			}else {
				this.vista.mostrarMensajeError("Error al Borrar la Reserva");
			}
			
		}catch (Exception error) {
			error.printStackTrace();
		}
	}
	@Override
	public void ConsultarCliente() {
		// TODO Auto-generated method stub
		try {
			ClienteResponse respuesta = this.modelo.consultarCliente();
			if(respuesta == null ||respuesta.getCount() == 0 || respuesta.getItems() == null) {
				this.vista.mostrarMensajeError("No hay Cliente que mostrar");
				
			}else {
				this.vista.mostrarClienteEnComboBox(respuesta.getItems());
			}
			
		}catch (Exception error) {
			error.printStackTrace();
		}
		
	}
	@Override
	public void ConsultarHabitacion() {
		// TODO Auto-generated method stub
		try {
			HabitacionResponse respuesta = this.modelo.consultarHabitacion();
			if(respuesta == null ||respuesta.getCount() == 0 || respuesta.getItems() == null) {
				this.vista.mostrarMensajeError("No hay Habitacion que mostrar");
				
			}else {
				this.vista.mostrarHabitacionEnComboBox(respuesta.getItems());
			}
			
		}catch (Exception error) {
			error.printStackTrace();
		}
		
	}
}
