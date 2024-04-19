package hn.uth.views.creserva;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import hn.uth.controller.InteractorImplReserva;
import hn.uth.controller.InteractorReserva;
import hn.uth.data.Reserva;
import hn.uth.views.MainLayout;
import hn.uth.views.reserva.ReservaView;



@PageTitle("Crear Reserva")
@Route(value = "CReserva", layout = MainLayout.class)
@Uses(Icon.class)
public class CReservaView extends Composite<VerticalLayout> {

	private final Grid<Reserva> grid = new Grid<>(Reserva.class, false);
	private Reserva ReservaS;

	private InteractorReserva controlador;
	
    public CReservaView() {
    	
    	controlador = new InteractorImplReserva();
    	
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();     
        
        TextField ticket = new TextField();
        TextField precioTotal = new TextField();
        TextField idHabitacion = new TextField();
        TextField idCliente = new TextField();
        TextField fechaInicio = new TextField();
        TextField fechaFinal = new TextField();
        
        ticket.setPrefixComponent(VaadinIcon.TICKET.create());
        precioTotal.setPrefixComponent(VaadinIcon.MONEY.create());
        idHabitacion.setPrefixComponent(VaadinIcon.HOME.create());
        idCliente.setPrefixComponent(VaadinIcon.USERS.create());
        fechaInicio.setPrefixComponent(VaadinIcon.CALENDAR.create());
        fechaFinal.setPrefixComponent(VaadinIcon.CALENDAR.create());
        
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button Guardar = new Button();
        Guardar.setId("btn_guardar");
        Button Cancelar = new Button();
        Cancelar.setId("btn_Cancelar");
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Reserva");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        ticket.setLabel("Ticket");
        ticket.setId("txt_ticket");
        precioTotal.setLabel("Precio Total");
        precioTotal.setId("txt_precioTotal");
        idHabitacion.setLabel("id de Habitacion");
        idHabitacion.setId("txt_idHabitacion");
        idCliente.setLabel("id de Cliente");
        idCliente.setId("txt_idCliente");
        fechaInicio.setLabel("Fecha de Inicio");
        fechaInicio.setId("txt_fechaInicio");
        fechaFinal.setLabel("Fecha Final");
        fechaFinal.setId("txt_fechaFinal");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        Guardar.setText("Guardar");
        Guardar.setWidth("min-content");
        Guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Cancelar.setText("Cancelar");
        Cancelar.setWidth("min-content");
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(ticket);
        formLayout2Col.add(precioTotal);
        formLayout2Col.add(idHabitacion);
        formLayout2Col.add(idCliente);
        formLayout2Col.add(fechaInicio);
        formLayout2Col.add(fechaFinal);
        layoutColumn2.add(layoutRow);
        layoutRow.add(Guardar);
        layoutRow.add(Cancelar);
        
        Guardar.addClickListener(e -> {
        	try {
            this.ReservaS = new Reserva();
            this.ReservaS.setTicket(ticket.getValue());
            this.ReservaS.setPreciototal(Double.parseDouble(precioTotal.getValue()));
            this.ReservaS.setIdhabitacion(idHabitacion.getValue());
            this.ReservaS.setIdcliente(idCliente.getValue());
            this.ReservaS.setFechainicio(fechaInicio.getValue().toString());
            this.ReservaS.setFechafinal(fechaFinal.getValue().toString());
            
            this.controlador.CrearReserva(ReservaS);
            
            

            
		        //refreshGrid();
		       // Notification.show("Data updated");
		        UI.getCurrent().navigate(ReservaView.class);
		    } catch (ObjectOptimisticLockingFailureException exception) {
		        Notification n = Notification.show(
		                "Error updating the data. Somebody else has updated the record while you were making changes.");
		        n.setPosition(Position.MIDDLE);
		        n.addThemeVariants(NotificationVariant.LUMO_ERROR);
		    } 
		});
        
        Cancelar.addClickListener(e -> {
        	ticket.setValue("");
        	precioTotal.setValue("");
        	idHabitacion.setValue("");
        	idCliente.setValue("");  
        	fechaInicio.setValue("");
        	fechaFinal.setValue("");
        });
        
    }
    
    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();

        this.controlador.consultarReserva();
    }
}
